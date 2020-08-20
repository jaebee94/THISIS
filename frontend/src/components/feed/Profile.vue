<template>
  <div class="profile wrap">
    <div class="intro-wrap">
      <div class="left-content">
        <div class="profile-image" v-if="this.profileData.userInfo.userimage != null">
          <img :src="profileData.userInfo.userimage" />
        </div>
        <div class="profile-image" v-else>
          <img src="../../assets/user2.png" />
        </div>
        <div class="profile-name">
          <a>{{ profileData.userInfo.nickname }}</a>
          <!-- 의사 뱃지 보여주는 부분 -->
          <img v-if ="profileData.userInfo.role == 'doctor'" src='../../assets/images/icon/icon_doctor_mark.png'/> 
        </div>
        <div class="profile-intro">
          <a>{{ profileData.userInfo.introduction }}</a>
        </div>
      </div>
      <div class="right-content">
        <table>
          <tr>
            <td>게시물</td>
            <td>{{ profileData.profileInfo.postnum }}</td>
          </tr>
          
          <tr>
            <td><router-link :to="{ name: 'FollowList', params: { tab: 0 }}" >팔로워</router-link></td>
            <td><router-link :to="{ name: 'FollowList', params: { tab: 0 }}" >{{ profileData.profileInfo.followernum }}</router-link></td>
          </tr>

         
          <tr>
            <td> <router-link :to="{ name: 'FollowList', params: { tab: 1 }}">팔로잉 </router-link></td>
            <td> <router-link :to="{ name: 'FollowList', params: { tab: 1 }}">{{ profileData.profileInfo.followeenum }} </router-link></td>
          </tr>
         
        </table>
        <button
          v-if="loginData.user_id == profileData.userInfo.user_id"
          id="logout-btn"
          @click="logout()"
        >로그아웃</button>
      </div>
    </div>
    <div class="profile-modify">
      <!-- 나의 아이디와 보고 있는 페이지의 유저 아이디가 같을 경우 -->
      <router-link to="/main/change">
        <button v-show="loginData.user_id == profileData.userInfo.user_id">프로필 수정</button>
      </router-link>
      <!-- 나의 아이디와 보고 있는 페이지의 유저 아이디가 다른 경우 -->
      <!-- 1. 팔로우 요청도 안 보낸 상태(data의 followSend가 false일 경우 + isFollowing이 false일 경우) -->
      <button
        @click="follow()"
        v-show="loginData.user_id != profileData.userInfo.user_id && !this.followSend && !this.followee_list"
      >팔로우 요청</button>
      <!-- 2. 팔로우 요청을 보냈지만 승인을 받지 못한 상태(followSend가 true일 경우 + isFollowing이 false일 경우) -->
      <button
        @click="followCancel()"
        v-show="loginData.user_id != profileData.userInfo.user_id && this.followSend && !this.followee_list"
      >팔로우 요청 취소</button>
      <!-- 3. 팔로우 요청을 보냈고 승인을 받은 상태(followSend가 true + isFollowing이 true) -->
      <button
        @click="followingCancel()"
        v-show="loginData.user_id != profileData.userInfo.user_id && this.followee_list"
      >팔로잉</button>
    </div>
    <div class="tabs">
      <div class="tab-container">
        <div
          class="tab"
          v-for="(tab, index) in tabs"
          v-bind:key="tab"
          v-bind:class="{active: currentTab === index}"
          @click="currentTab = index"
        >
          <img :src="tab" />
        </div>
      </div>

      <div>
        <feed
          v-bind:profile_data="{user_id : this.profileData.userInfo.user_id,
       tab : this.currentTab}"
        ></feed>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import db from "../../firebaseInit";
import firebase from "firebase";
import router from "@/router";

const increment = firebase.firestore.FieldValue.increment(1);
const decrement = firebase.firestore.FieldValue.increment(-1);

export default {
  name: "Profile",
  data() {
    return {
      followSend: false, // followSend - true : 팔로우 신청을 한 상태 / false : 팔로우 신청을 하지 않은 상태
      isFollowing: false, // isFollowing - true : 팔로우 하는 중 / false : 팔로우를 하고 있지 않음
      isPostHidden: false,
      postInfo: {},
      user: {
        user_id: this.$route.params.id,
        user_name: "달린다응쎄",
        user_intro: "안녕하세요 ㅎㅎ",
      },
      commentData: {
        posts_id: null,
        comment_main: "",
        user_nickname: null,
      },
      isPostModify: false,
      mPost: {
        post_id: "1",
        user_id: "1",
        name: "1",
        time: "1",
        health: "1",
        content: "1",
      },
      healthData: {
        posts_id: null,
        user_id: null,
      },
      follower: 303,
      followee: 101,
      currentTab: 0,
      tabs: [
        require("../../assets/images/icon/icon_post.png"),
        require("../../assets/images/icon/icon_scrap_unselect.png"),
      ],
      isModifyHidden: false,
    };
  },
  mounted() {
    this.user.user_id = this.$route.params.id;
  },
  computed: {
    ...mapState("userStore", ["loginData", "profileData"]),
    ...mapState("followStore", ["followee_list"]),
    ...mapState("postStore", ["comments"]),
  },
  methods: {
    ...mapActions("userStore", ["goProfile"]),
    ...mapActions("postStore", [
      "updatePost",
      "createComment",
      "fetchComments",
      "updateComment",
      "health",
      "deleteScrap",
      "getUserScraps",
    ]),
    ...mapActions("followStore", [
      "createFollowing",
      "deleteFollowing",
      "deleteFollow",
      "getFollowee",
    ]),
    ...mapActions("notificationStore", ["createNotification"]),
    follow() {
      let vueInstance = this;
      this.followSend = true;
      // 요청을 받은 사람의 request 증가 + 요청을 보낸 사람의 id 값을 가지기
      let instance = {
        request: increment,
      };
      instance[this.loginData.user_id] = false;
      // 보내는 사람의 notification(알림) 1 증가 -> 그냥 승낙받았을 때만 1증가
      // db.collection("notification")
      //   .doc(String(this.loginData.user_id))
      //   .update({
      //     notification: increment,
      //   })
      //   .then(function () {
      //     vueInstance.$parent.getNoti(vueInstance.loginData.user_id);
      //   })
      //   .catch(function (error) {
      //     console.error("Error setting document: ", error);
      //   });
      // 받는 사람의 request(요청) 1 증가
      db.collection("notification")
        .doc(String(this.profileData.userInfo.user_id))
        .update(instance)
        .then(function () {
          vueInstance.$parent.getNoti(vueInstance.id);
        })
        .catch(function (error) {
          console.error("Error setting document: ", error);
        });

      let params = {};
      params["follower_id"] = this.loginData.user_id;
      params["followee_id"] = this.profileData.userInfo.user_id;
      params["approval"] = 0;

      this.$store.dispatch("followStore/createFollowing", params);
    },
    followCancel() {
      let vueInstance = this;
      this.followSend = false;

      // 보내는 사람의 notification(알림) 1 감소
      // db.collection("notification")
      //   .doc(String(this.loginData.user_id))
      //   .update({
      //     notification: decrement,
      //   })
      //   .then(function () {
      //     vueInstance.$parent.getNoti(vueInstance.loginData.user_id);
      //   })
      //   .catch(function (error) {
      //     console.error("Error setting document: ", error);
      //   });
      // 받는 사람의 request(요청) 1 감소

      // 이미 상대편에서 알림을 다 읽어서 감소할게 없을 때는 지우지 않는다
      let flag = true;
      db.collection("notification")
        .doc(String(vueInstance.profileData.userInfo.user_id))
        .get()
        .then(function (doc) {
          if (doc.exists) {
            if (doc.data().request == 0) flag = false;
          } else {
            console.log("No such document!");
          }
        })
        .catch(function (err) {
          console.log("ERROR OCCURED : ", err);
        });

      if (flag) {
        let instance = {
          request: decrement,
        };
        instance[
          this.loginData.user_id
        ] = firebase.firestore.FieldValue.delete();
        db.collection("notification")
          .doc(String(vueInstance.profileData.userInfo.user_id))
          .update(instance)
          .then(function () {
            vueInstance.$parent.getNoti(vueInstance.loginData.user_id);
          })
          .catch(function (error) {
            console.error("Error setting document: ", error);
          });
      }
      let params = {};
      params["follower_id"] = this.loginData.user_id;
      params["followee_id"] = this.profileData.userInfo.user_id;
      params["approval"] = 1;
      this.$store.dispatch("followStore/deleteFollowing", params);
    },
    followingCancel() { //팔로우 끊기
      var res = confirm("팔로우를 끊으시겠어요?");
      if(!res) {
        alert("팔로잉 상태를 유지할게요~");
        return;
      }
      this.isFollowing = false;
      this.followee_list = false;
      let params = {
        follower: this.loginData.user_id, //본인
        followee: this.profileData.userInfo.user_id, //상대방
      };
      this.$store.dispatch("followStore/deleteFollow", params);
      params = {
        follower: this.loginData.user_id, //본인
        followee: this.profileData.userInfo.user_id, //상대방
      };
      this.getFollowee(params);
    },
    logout() {
      var result = confirm("로그아웃하시겠습니까?");
      if (result) {
        router.push({ name: "Logout" });
      } else {
        alert("그래요! 좀만 더 놀다가세요");
      }
    },
  },
  created() {
    this.getUserScraps(this.profileData.userInfo.user_id);
    var vueInstance = this;
    let params = {
      followee_id: this.profileData.userInfo.user_id,
      follower_id: this.loginData.user_id,
    };
    this.getFollowee(params);

    const noti = db
      .collection("notification")
      .doc(String(vueInstance.profileData.userInfo.user_id));
    let instance = {};

    noti.onSnapshot(
      {
        // Listen for document metadata changes
        includeMetadataChanges: true,
      },
      function () {
        //이벤트 발생시 카운트 재정립
        noti
          .get()
          .then(function (doc) {
            instance = doc.data();
            if (instance[vueInstance.loginData.user_id] == false) {
              vueInstance.followSend = true;
            } else vueInstance.followSend = false;
          })
          .catch(function (err) {
            console.log("ERROR OCCURED : ", err);
          });
      }
    );

  },

  
};
</script>
<style scoped>
.profile.wrap {
  width: 100%;
  height: 100%;
}
.intro-wrap {
  display: flex;
  width: 100%;
  height: 200px;
}
.left-content {
  padding: 20px;
  float: left;
  width: 50%;
  text-align: center;
}

.profile-image {
  width: 100px;
  height: 100px;
  margin: 0 auto;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.profile-image img {
  border: rgb(0, 171, 132) 3px solid;
  border-radius: 70%;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.profile-name a {
  font-size: 20px;
  font-weight: 600;
}
.profile-name img {
  width: 15px;
  height: 15px;
  margin-left : 2px
}

.profile-intro a {
  font-size: 12px;
}
.right-content {
  float: right;
  width: 50%;
}
.right-content table {
  width: 100%;
  margin-top: 20%;
  height: 60%;
}
.right-content table tr td:nth-child(1), td:nth-child(1) a {
  font-size: 15px;
  font-weight: 500;
  width: 60%;
  color:black;
}

.right-content table tr td:nth-child(2), td:nth-child(2) a {
  font-size: 20px;
  font-weight: 600;
  color: rgb(0, 171, 132);
  text-align: left;
}

td a{
  text-decoration: none;
}
.profile-modify {
  width: 90%;
  height: 5%;
  margin-left: 5%;
  margin-bottom: 5%;
}
.profile-modify button {
  width: 100%;
  background-color: rgb(200, 200, 200);
  color: white;
  font-size: 15px;
  font-weight: 500;
  height: 30px;
  border-radius: 5px;
  border: none;
  transition-duration: 300ms;
}

.profile-modify button:hover {
  background-color: rgb(150, 150, 150);
}

.tabs {
  width: 100%;
  height: 70%;
}
.tab-container {
  display: flex;
}
.tab {
  width: 50%;
  height: 40px;
  vertical-align: middle;
  background-color: rgb(247, 247, 247);
  border-bottom: 3px rgb(247, 247, 247) solid;
  border-radius: 0;
}
.tab img {
  margin-top: 10px;
  height: 20px;
}
.tab.active {
  background-color: rgb(247, 247, 247);
  border-bottom: 3px rgb(0, 171, 132) solid;
}

/* 피드 관련 */
.feed.wrap {
  text-align: center;
  background-color: white;
  padding-bottom: 20px;
}

.feed {
  padding: 1% 1%;
  width: 92%;
  margin: 2% 3%;
  margin-bottom: 5%;
  background-color: rgb(247, 247, 247);
  border: none;
  border-radius: 5px;
}

.feed:last-child {
  margin-bottom: 40px;
}

.feed-header {
  display: flex;
  background-color: rgb(0, 171, 132);
  text-align: left;
  width: 100%;
  height: 40px;
  border: none;
  border-radius: 5px;
  vertical-align: middle;
}

.feed-header table {
  text-align: center;
  width: 100%;
}

.feed-header table tr td {
  height: 40px;
}

.feed-header table tr td a {
  color: white;
  font-weight: 600;
}

.feed-header table tr td img {
  height: 80%;
}

.feed-header table tr td:nth-child(2) {
  text-align: left;
}

.feed-header table tr td:nth-child(3) {
  padding-right: 10px;
  text-align: right;
}

.feed-main {
  padding: 10px 5px;
}

/* .profile-image {
  background-color: white;
  border-radius: 70%;
} */

.feed-footer {
  width: 100%;
  height: 30px;
  text-align: left;
  background-color: rgb(200, 200, 200);
}

.feed-footer table {
  margin-left: 20px;
}

.feed-footer table tr td {
  width: 50px;
  height: 30px;
}

.feed-footer table tr td img {
  margin: 2% 5%;
  height: 60%;
}

#logout-btn {
  width: 100px;
  height: 30px;
  border: none;
  background-color: rgb(220, 0, 27);
  font-weight: 600;
  border-radius: 5px;
  outline: none;
  color: white;
  transition-duration: 300ms;
}

#logout-btn:focus {
  background-color: rgb(189, 22, 44);
}

.health-count {
  position: absolute;
  margin-left: -5px;
  margin-top: -5px;
  background-color: red;
  color: white;
  border: none;
  font-size: 10px;
  border-radius: 70%;
  padding: 1px 3px;
}

.post {
  position: fixed;
  z-index: 99;
  top: 10%;
  width: 92%;
  height: 80%;
  background-color: white;
  border-radius: 5px;
}

.post-header {
  display: flex;
  background-color: rgb(0, 171, 132);
  text-align: left;
  width: 100%;
  height: 40px;
  border: none;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  vertical-align: middle;
}

.post-header table {
  text-align: center;
  width: 100%;
}

.post-header table tr td {
  height: 40px;
}

.post-header table tr td a {
  color: white;
  font-weight: 600;
}

.post-header table tr td img {
  height: 80%;
}

.post-header table tr td:nth-child(2) {
  text-align: left;
}

.post-header table tr td:nth-child(3) {
  padding-right: 10px;
  text-align: right;
}

.post-main {
  height: 30%;
  overflow: auto;
  padding: 10px 10px;
  text-align: left;
}

.post-footer {
  position: absolute;
  width: 100%;
  height: 10%;
  bottom: -12%;
}

.post-footer img {
  height: 80%;
}

.comment-wrap {
  display: inherit;
  overflow: auto;
  height: 50%;
}

.comment {
  width: 90%;
  margin-left: 5%;
  margin-bottom: 5%;
}

.comment-header {
  height: 30px;
  background-color: rgb(0, 171, 132);
  border-radius: 5px;
}

/* .comment-header table tr td{
        height: 20px;
    } */

.comment-header table {
  width: 100%;
  height: 100%;
}

.comment-header table tr {
  height: 20px;
}

.comment-header table tr td {
  padding: 2px;
}

.comment-header table tr td a {
  color: white;
  font-weight: 600;
}

.comment-header table tr td:nth-child(1) img {
  height: 20px;
}

.comment-content {
  text-align: left;
}

.comment-submit {
  position: absolute;
  height: 8%;
  width: 90%;
  margin-left: 5%;
  bottom: 1%;
}

/* 수정할 포스트 내용 보이기 */
.modify-wrap {
  position: fixed;
  z-index: 99;
  top: 10%;
  margin-left: 4%;
  width: 92%;
  height: 70%;
  background-color: white;
  border-radius: 5px;
}

.post-content textarea {
  padding: 5px 5px;
  font-size: 20px;
  margin-top: 10px;
  width: 90%;
  height: 350px;
  word-wrap: break-word;
  transition-duration: 300ms;
  border: none;
}

.post-content textarea:focus {
  outline: none;
  border: rgb(0, 171, 132) 3px solid;
}

.post-modify-btn {
  width: 100%;
  height: 50px;
}

.post-modify-btn button {
  width: 90%;
  height: 90%;
  border: none;
  border-radius: 5px;
  background-color: rgb(0, 171, 132);
  outline: none;
  color: white;
  font-size: 20px;
}

.modify-footer {
  position: absolute;
  width: 100%;
  height: 10%;
  bottom: -12%;
}

.modify-footer img {
  height: 80%;
}



</style>