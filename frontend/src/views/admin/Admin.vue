<template>
  <div class="main wrap">
    <div class="logo wrap">
      <!-- <img src="../../assets/images/icon/logo_green.png" /> -->
      <span>ADMIN</span>
      <router-link to="/main/feed">MAIN</router-link>
    </div>

    <div class="feed wrap">
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
      </div>

      <div v-show="currentTab == 0">
        <div>
          <PostList v-for="post in postList" :key="post.posts_id" :post="post"></PostList>
          <!-- <div v-for="post in postList" :key="post.posts_id">
            {{ post.postpolice.posts_title }}
          </div> -->
          <infinite-loading
            v-if="this.currentTab == 0"
            ref="infiniteLoading"
            @infinite="infiniteHandler"
          ></infinite-loading>
        </div>
      </div>

      <div v-show="currentTab == 1">
        <div>
          <UserList v-for="user in userList" :key="user.user_id" :user="user"></UserList>
          <infinite-loading
            v-if="this.currentTab == 1"
            ref="infiniteLoading"
            @infinite="infiniteHandler"
          ></infinite-loading>
        </div>
      </div>

      <div v-show="currentTab == 2">
        <div>
          <DoctorList v-for="doctor in doctorList" :key="doctor.doctor_id" :doctor="doctor"></DoctorList>
          <!-- <infinite-loading
            v-if="this.currentTab == 2"
            ref="infiniteLoading"
            @infinite="infiniteHandler"
          ></infinite-loading> -->
          <div v-if="doctorList.length == 0">
            인증을 요청한 회원이 없습니다.
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import { mapState, mapActions } from "vuex";

import SERVER from "@/api/RestApi"
import axios from "axios"
import cookies from "vue-cookies"

import PostList from "@/components/admin/post/PostList.vue";
import UserList from "@/components/admin/user/UserList.vue";
import DoctorList from "@/components/admin/doctor_auth/DoctorList.vue";

export default {
  name: "Admin",
  components: {
    PostList,
    UserList,
    DoctorList,
  },

  data() {
    return {
      tabs: [
        require("../../assets/images/icon/icon_post.png"),
        require("../../assets/images/icon/icon_default_image.png"),
        require("../../assets/images/icon/icon_cert.png"),
      ],
      currentTab: 0,
      page: 0,
      postList: [],
      userList: [],
      doctorList: [],
    };
  },
  watch: {
    currentTab: function () {
      this.page = 0
      this.postList = []
      this.userList = []
      this.doctorList = []
      if (this.currentTab == 2) {
        console.log('asdasd')
        axios.get(SERVER.URL + SERVER.ROUTES.doctors, {headers: { accessToken: cookies.get('access-token') }})
          .then(({data}) => {
            this.doctorList = data
            console.log(this.doctorList)
          })
      }
    },
  },

  computed: {
    // ...mapState("userStore", ["loginData", "profileData"]),
    // ...mapState("adminStore", ["tempData"]),
  },
  mounted() {
    // console.log(this.postList)
  },
  created() {
    // this.fetchReportedPosts()
    // console.log(this.postList)
  },

  methods: {
    infiniteHandler($state) {
      var params = {
        params: {
          num: this.page,
        },
        headers: { accessToken:  cookies.get('access-token') }
      };

      //통신부분
      if (this.currentTab == 0) {
        axios.get(SERVER.URL + SERVER.ROUTES.reportedPosts, params)
          .then(({data}) => {
            console.log(data)
            if (data.length) {
              this.page += 1;
              this.postList.push(...data);
              console.log(this.postList)
              $state.loaded();
            } else {
              $state.complete();
            }
          })
      } else if (this.currentTab == 1) {
        axios.get(SERVER.URL + SERVER.ROUTES.reportedUsers, params)
          .then(({data}) => {
            console.log(data)
            if (data.length) {
              this.page += 1;
              this.userList.push(...data);
              $state.loaded();
            } else {
              $state.complete();
            }
          })
      }
    },
  },
};
</script>

<style scoped>
* {
  font-size: 15px;
}

.main.wrap {
  width: 100%;
  height: 100%;
}

.logo.wrap {
  width: 100%;
  height: 10%;
  background-color: rgb(240, 240, 240);
}
.logo.wrap img {
  margin: 5px 5px auto;
  width: 20%;
}
.footer.wrap {
  position: fixed;
  bottom: 0px;
  width: 100%;
  height: 40px;
  background-color: rgb(240, 240, 240);
}
.footer.table {
  width: 100%;
  height: 100%;
}
.footer.table td {
  width: 20%;
}
.footer.table td img {
  width: 30%;
}
.footer.table td:nth-child(5) img {
  margin-top: 3px;
  width: 30px;
  height: 30px;
  border-radius: 70%;
}

.feed.wrap {
  text-align: center;
  background-color: white;
  padding-bottom: 20px;
  margin-top: 0;
  margin-left: 0;
  width: 100%;
  padding: 0 0;
  margin-bottom: 50px;
}

.tabs {
  width: 100%;
  margin-top: 0;
}

.tab {
  border-radius: 0;
  border-bottom: 3px rgb(247, 247, 247) solid;
}

.tab.active {
  background-color: rgb(247, 247, 247);
  border-bottom: 3px rgb(0, 171, 132) solid;
}

.tab img {
  margin-top: 10px;
  height: 20px;
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

div.feed {
  margin: 0;
  width: 98%;
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
  background-color: rgb(247, 247, 247);
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
.post-header table tr td:nth-child(1){
  width: 50px;
}

.post-header a.time {
    font-size: 10px;
    /* color: rgb(247, 247, 247); */
    color: slategray;
    font-weight: 400;
  }

.post-header table tr td a {
  color: black;
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

.carousel.wrap {
  width: 100%;
  height: 40%;
}

.myslide {
  width: 100%;
  height: 100%;
}

.myslide div img {
  max-width: 100%;
  max-height: 100%;
  min-width: 50%;
  background-size: contain;
}

.myslide .post-main strong {
  font-size: 17px;
}

.myslide .post-disease-tag {
  color: rgb(0, 171, 132);
  font-weight: 600;
}

.myslide .post-custom-tag {
  font-size: 12px;
  color: slategray;
  font-weight: 500;
}

.post-main {
  height: 100%;
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
  height: 40%;
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



/* ----- qna 내용 자세히 보기 모달창 코드 -----  */
.post {
  margin-left: 4%;
}

.qna-header {
  text-align: left;
  padding-left: 5%;
}

.qna-header .title a {
  font-size: 20px;
  font-weight: 600;
}

.qna-header .nickname a {
  font-size: 15px;
  font-weight: 500;
}

.qna-header .time a {
  color: slategray;
  font-size: 10px;
}

.qna-photo-wrap {
  width: 90%;
  margin-left: 5%;
}

.qna-photo-wrap img {
  width: 100%;
}

.qna-tag-wrap {
  width: 100%;
}

.qna-tag-wrap div {
  margin-bottom: 5px;
}

.qna-tag-wrap .disease-tag {
  background-color: rgb(0, 171, 132);
  color: white;
  font-size: 16px;
  font-weight: 600;
  border: none;
  border-radius: 5px;
  padding: 3px 8px;
}

.qna-tag-wrap .custom-tag {
  color: rgb(0, 171, 132);
  margin-left: 5px;
}

.qna-content-wrap {
  width: 90%;
  margin-left: 5%;
}

.qna-comment-wrap {
  width: 100%;
  height: 200px;
  overflow: auto;
}

.qna-comment-wrap.wide {
  height: 400px;
}

.qna-comment-write-wrap {
  position: absolute;
  bottom: 10px;
  width: 90%;
  margin-left: 5%;
}

.qna-comment-write-wrap input {
  width: 70%;
  height: 25px;
  background-color: rgb(240, 240, 240);
  border: none;
  border-radius: 5px;
  padding-left: 5%;
  outline: none;
  transition-duration: 300ms;
}

.qna-comment-write-wrap input:focus {
  background-color: rgb(220, 220, 220);
}

.qna-comment-write-wrap button {
  width: 20%;
  height: 25px;
  border: none;
  background-color: rgb(0, 171, 132);
  font-weight: 600;
  color: white;
  line-height: 1;
  padding-top: 2px;
  padding-bottom: 2px;
  outline: none;
  border-radius: 5px;
  margin-left: 1%;
}
</style>