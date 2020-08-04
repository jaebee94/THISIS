<template>
  <div class="profile wrap">
    <div class="intro-wrap">
      <div class="left-content">
        <div class="profile-image">
          <img src="../../assets/sample.jpg" />
        </div>
        <div class="profile-name">
          <a>{{ profileData.userInfo.nickname }}</a>
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
            <td>팔로워</td>
            <td>{{ profileData.profileInfo.followernum }}</td>
          </tr>
          <tr>
            <td>팔로잉</td>
            <td>{{ profileData.profileInfo.followeenum }}</td>
          </tr>
        </table>
      </div>
    </div>
    <div class="profile-modify">
      <!-- 나의 아이디와 보고 있는 페이지의 유저 아이디가 같을 경우 -->
      <router-link to="/main/change">
        <button
          v-show="loginData.user_id == profileData.userInfo.user_id"
        >프로필 수정</button>
      </router-link>
      <!-- 나의 아이디와 보고 있는 페이지의 유저 아이디가 다른 경우 -->
      <!-- 1. 팔로우 요청도 안 보낸 상태(data의 followSend가 false일 경우 + isFollowing이 false일 경우) -->
      <button
        @click="follow()"
        v-show="loginData.user_id != profileData.userInfo.user_id && !this.followee_list && !this.followSend"
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

      <div v-show="currentTab == 0">
        <div class="feed wrap">
          <div v-if="this.isPostHidden" class="post" ref="post">
          <div class="post-header">
            <table>
              <tr>
                <td>
                  <img class="profile-image" src="../../assets/images/icon/icon_default_image.png" />
                </td>
                <td>
                  <a class="name">{{ postInfo.nickname }}</a>
                </td>
                <td>
                  <a class="time">{{ postInfo.post_date }}</a>
                </td>
              </tr>
            </table>
          </div>
          <div class="post-main">{{ postInfo.posts_main }}</div>
          <div class="comment-wrap">
            <div class="comment" v-for="comment in comments" v-bind:key="comment.post_id">
              <div class="comment-header">
                <table>
                  <tr>
                    <td>
                      <img
                        class="profile-image"
                        src="../../assets/images/icon/icon_default_image.png"
                      />
                    </td>
                    <td>
                      <a>{{ comment.user_nickname }}</a>
                    </td>
                    <td>
                      <a>{{ comment.comment_date }}</a>
                    </td>
                  </tr>
                </table>
              </div>
              <div
                class="comment-content"
              >{{ comment.comment_main }}</div>
            </div>
          </div>
          <div class="comment-submit">
            <input v-model="commentData.comment_main" placeholder="내용을 입력하세요" />
            <button @click="createComment(commentData), clearCommentData()">댓글달기</button>
          </div>
          <div class="post-footer">
            <img @click="closePost()" src="../../assets/images/icon/icon_close.png" />
          </div>
        </div>
        <div class="feed" v-for="post in profileData.postInfo" v-bind:key="post.posts_id">
          <div class="feed-header">
            <table>
              <tr>
                <td>
                  <img class="profile-image" src="../../assets/images/icon/icon_default_image.png" />
                </td>
                <td>
                  <a class="name">{{ post.nickname }}</a>
                </td>
                <td>
                  <a class="time">{{ post.post_date }}</a>
                </td>
              </tr>
            </table>
          </div>
          <div class="feed-main">{{ post.posts_main }}</div>
          <div class="feed-footer">
            <table>
              <tr>
                <td>
                  <img
                    v-if="post.health"
                    src="../../assets/images/icon/icon_like_select.png"
                    @click="clickHealth(post)"
                  />
                  <img
                    v-if="!post.health"
                    src="../../assets/images/icon/icon_like_unselect.png"
                    @click="clickHealth(post)"
                  />
                  <span class="health-count">{{ post.health_count }}</span>
                </td>
                <td>
                  <img @click="showPost(post)" src="../../assets/images/icon/icon_talk.png" />
                </td>
                <td>
                <img
                v-show="post.scrap"
                @click="clickScrap(post)"
                src="../../assets/images/icon/icon_scrap_select.png"
              />
              <img
                v-show="!post.scrap"
                @click="clickScrap(post)"
                src="../../assets/images/icon/icon_scrap_unselect.png"
              />
                </td>
                <td>
                  <img
                    v-if="loginData.user_id == post.user_id"
                     @click="showModify(post)"
                    src="../../assets/images/icon/icon_edit_unselect.png"
                  />
                </td>
              </tr>
            </table>
          </div>
        </div>
        </div>
        
        <!-- <Feed/> -->
      </div>
      <div v-show="currentTab == 1">
        <div class="feed" v-for="scrap in profileData.scrapInfo" v-bind:key="scrap.post_id">
          <div class="feed-header">
            <table>
              <tr>
                <td>
                  <img class="profile-image" src="../../assets/images/icon/icon_default_image.png" />
                </td>
                <td>
                  <a class="name">{{ scrap.nickname }}</a>
                </td>
                <td>
                  <a class="time">{{ scrap.post_date }}</a>
                </td>
              </tr>
            </table>
          </div>
          <div class="feed-main">{{scrap.posts_main}}</div>
          <div class="feed-footer">
            <table>
              <tr>
                <td>
                  <img
                    v-if="scrap.health"
                    src="../../assets/images/icon/icon_like_select.png"
                    @click="clickHealth(scrap)"
                  />
                  <img
                    v-if="!scrap.health"
                    src="../../assets/images/icon/icon_like_unselect.png"
                    @click="clickHealth(scrap)"
                  />
                  <span class="health-count">{{ scrap.health_count }}</span>
                </td>
                <td>
                  <img @click="showPost(scrap)" src="../../assets/images/icon/icon_talk.png" />
                </td>
                <td>
                  <button v-if="id == scrap.user_id" @click="showModify(scrap)">수정</button>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import db from "../../firebaseInit";
import firebase from "firebase";
// import Feed from "./Feed.vue"

const increment = firebase.firestore.FieldValue.increment(1);
const decrement = firebase.firestore.FieldValue.increment(-1);

export default {
  name: "Profile",
  // components: {
  //   Feed,
  // },
  data() {
    return {
      id: 2, // 현재 나의 아이디 -> store에 저장되거나 서버에서 가져오거나
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
    ...mapState(["followee_list"]),
    ...mapState(["profileData"]),
    ...mapState(["loginData"]),
    ...mapState(["comments"]),
  },
  methods: {
    ...mapActions(["fetchComments"]),
    ...mapActions(["goProfile", "getUserScraps","deleteScrap"]),
    ...mapActions(["getFollowee", "createFollowing", "deleteFollowing"]),
    ...mapActions(["updatePost", "createComment", "updateComment"]),
    ...mapActions(["health"]),

    showModify(postInfo) {
      console.log(postInfo);
      this.postInfo = postInfo;
      this.$parent.$parent.isHidden = true;
      this.isModifyHidden = true;
      console.log(postInfo);
    },
    modifyPostClick(post_id) {
      console.log(this.mPost);
      this.posts.forEach(function (element) {
        if (element.post_id == post_id) {
          element = this.mPost;
        }
      });
      this.closeModify();
    },
    closeModify() {
      this.$parent.$parent.isHidden = false;
      this.isModifyHidden = false;
    },
    follow() {
      let vueInstance = this;
      this.followSend = true;
      // 요청을 받은 사람의 request 증가 + 요청을 보낸 사람의 id 값을 가지기
      let instance = {
        request: increment,
      };
      instance[this.loginData.user_id] = false;
      // 보내는 사람의 notification(알림) 1 증가
      db.collection("notification")
        .doc(String(this.loginData.user_id))
        .update({
          notification: increment,
        })
        .then(function () {
          vueInstance.$parent.getNoti(vueInstance.loginData.user_id);
        })
        .catch(function (error) {
          console.error("Error setting document: ", error);
        });
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

      this.$store.dispatch("createFollowing", params);
    },
    followCancel() {
      let vueInstance = this;
      this.followSend = false;
      let instance = {
        request: decrement,
      };
      instance[this.loginData.user_id] = firebase.firestore.FieldValue.delete();
      // 보내는 사람의 notification(알림) 1 감소
      db.collection("notification")
        .doc(String(this.loginData.user_id))
        .update({
          notification: decrement,
        })
        .then(function () {
          vueInstance.$parent.getNoti(vueInstance.loginData.user_id);
        })
        .catch(function (error) {
          console.error("Error setting document: ", error);
        });
      // 받는 사람의 request(요청) 1 감소
      db.collection("notification")
        .doc(String(vueInstance.profileData.userInfo.user_id))
        .update(instance)
        .then(function () {
          vueInstance.$parent.getNoti(vueInstance.loginData.user_id);
        })
        .catch(function (error) {
          console.error("Error setting document: ", error);
        });
      let params = {};
      params["follower_id"] = this.loginData.user_id;
      params["followee_id"] = this.profileData.userInfo.user_id;
      params["approval"] = 1;

      this.$store.dispatch("deleteFollowing", params);
    },
    followingCancel() {
      this.isFollowing = false;
    },
    clickHealth(post) {
      if (post.health == true) {
        post.health = false;
        post.health_count -= 1;
      } else {
        post.health = true;
        post.health_count += 1;
      }
      console.log('post', post)
      this.healthData.posts_id = post.posts_id;
      this.healthData.user_id = this.loginData.user_id;
      console.log(post)
      console.log("id", this.healthData.user_id)
      //this.healthData.user_id = this.loginData.user_id; // user_id
      this.health(this.healthData);
    },
    clickScrap(post) {
      if (post.scrap == true) {
        post.scrap = false;
        this.$store.dispatch("deleteScrap", post.posts_id);
      } else {
        post.scrap = true;
        this.$store.dispatch("scrap", post.posts_id);
      }
      console.log("click_post", post);
    },
    showPost(postInfo) {
      this.postInfo = postInfo;
      console.log('postInfo', postInfo)
      this.$parent.$parent.isHidden = true;
      this.isPostHidden = true;
      // this.commentData.posts_id = post.posts_id;
      // this.fetchHealths(post.posts_id);
      this.fetchComments(postInfo.posts_id);
      this.commentData.posts_id = postInfo.posts_id;
      this.commentData.user_nickname = this.loginData.nickname;
    },
    clearCommentData() {
      setTimeout(() => {
        this.commentData = {};
      }, 300);
    },
    closePost() {
      this.$parent.$parent.isHidden = false;
      this.isPostHidden = false;
    },
    updatePostAndClose(postInfo) {
      this.updatePost(postInfo);
      this.$parent.$parent.isHidden = false;
      this.isModifyHidden = false;
    },
  },
  created() {
    this.getUserScraps(this.profileData.userInfo.user_id);
  
    console.log("---", this.loginData);
    console.log("----", this.profileData);
    var vueInstance = this;
    let params = {
      followee_id: this.profileData.userInfo.user_id,
      follower_id: this.loginData.user_id
    };
    this.getFollowee(params);

    console.log("get Follower", this.followee_list);
    const noti = db.collection("notification")
    .doc(String(vueInstance.profileData.userInfo.user_id));
    let instance = {};

    noti
      .get()
      .then(function (doc) {
        instance = doc.data();
        console.log(instance);
        if (instance[vueInstance.loginData.user_id] == false) {
          console.log("!!");
          vueInstance.followSend = true;
        }
      })
      .catch(function (err) {
        console.log("ERROR OCCURED : ", err);
      });
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
}
.profile-image img {
  border: rgb(0, 171, 132) 3px solid;
  border-radius: 70%;
  width: 60%;
}
.profile-name a {
  font-size: 20px;
  font-weight: 600;
}

.profile-intro a {
  font-size: 15px;
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
.right-content table tr td:nth-child(1) {
  font-size: 15px;
  font-weight: 500;
  width: 60%;
}

.right-content table tr td:nth-child(2) {
  font-size: 20px;
  font-weight: 600;
  color: rgb(0, 171, 132);
  text-align: left;
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
  font-size: 18px;
  font-weight: 600;
  height: 30px;
  border-radius: 5px;
  border:none;
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
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}
.tab img {
  margin-top: 10px;
  height: 20px;
}
.tab.active {
  background-color: rgb(0, 171, 132);
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

.profile-image {
  background-color: white;
  border-radius: 70%;
}

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

.health-count {
  position: absolute;
  margin-left: -5px;
  margin-top: -5px;
  background-color: red;
  color: white;
  border: none;
  font-size: 10px;
  border-radius: 70%;
  padding:1px 3px;
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