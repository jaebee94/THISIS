<template>
  <div class="feed wrap">

    <div class="tabs" v-if="profile_data == undefined">
      <div class="tab-container">
        <div
          class="tab"
          v-for="(tab, index) in tabs"
          v-bind:key="tab"
          v-bind:class="{active: currentTab === index}"
          @click="currentTab = index">
          <img :src="tab"/>
        </div>
      </div> 
    </div>

    <div v-if="this.isQnAHidden" class="post" ref="qna">
      <div class="qna-header">
        <div class="title"> 
          <a>{{qnaInfo.post.posts_title}}</a>
        </div>
        <div class="nickname">
          <a>{{qnaInfo.userinfo.nickname}}</a>
        </div>
        <div class="time">
          <a>{{ timeForToday(qnaInfo.post.post_date) }}</a>
        </div>
      </div>
      <div v-if="qnaInfo.post.imgsrc != null" class="qna-photo-wrap">
        <img :src="qnaInfo.post.imgsrc">
      </div>
      <div class="qna-tag-wrap">
        <div>
          <span class="disease-tag">#{{qnaInfo.diseasename}}</span>
        </div>
        <a class="custom-tag" v-for="tag in qnaInfo.tags" v-bind:key="tag.tagid">#{{tag.tagname}}</a>
      </div>
      <div class="qna-content-wrap">
        {{qnaInfo.post.posts_main}}
      </div>
      <div :class="{'wide' : qnaInfo.post.imgsrc == null}" class="qna-comment-wrap">
        <comment v-for="comment in comments" v-bind:key="comment.comment_id"
         v-bind:comment = "comment"></comment>
      </div>
      <div class="qna-comment-write-wrap">
       <input v-model="commentData.comment_main" placeholder="내용을 입력하세요" />
        <button @click="createComment(commentData), clearCommentData()">댓글</button>
      </div>
      <div class="post-footer">
        <img @click="closeQnA()" src="../../assets/images/icon/icon_close.png" />
      </div>
    </div>

    <div v-if="this.isPostHidden" class="post" ref="post">
      <div class="post-header">
        <table>
          <tr>
            <td>
              <img class="profile-image" src="../../assets/images/icon/icon_default_image.png" />
            </td>
            <td>
              <a class="name">{{ postInfo.userinfo.nickname }}</a>
            </td>
            <td>
              <a class="time">{{timeForToday(postInfo.post.post_date)}}</a>
            </td>
          </tr>
        </table>
      </div>
      <carousel class="carousel wrap" :per-page="1" v-bind:pagination-enabled="false">
        <slide v-if="postInfo.post.imgsrc != null" class="myslide">
            <div>
                <img :src="postInfo.post.imgsrc">
                <!-- <img src="../../assets/sample.jpg"> -->
            </div>
        </slide>
        <slide class="myslide" >
          <div class="post-main">
            <strong>{{postInfo.post.posts_title}}</strong> <br>
            <a class="post-disease-tag">#{{postInfo.diseasename}}</a><br>
            <a class="post-custom-tag" v-for="tag in postInfo.tags" v-bind:key="tag.tagid">#{{tag.tagname}} </a> <br>
            {{ postInfo.post.posts_main }}
          </div>
        </slide>
      </carousel>
     
      <div class="comment-wrap">
        <comment v-for="comment in comments" v-bind:key="comment.comment_id"
         v-bind:comment = "comment"></comment>
      </div>
      <div class="qna-comment-write-wrap">
        <input v-model="commentData.comment_main" placeholder="내용을 입력하세요" />
        <button @click="createComment(commentData), clearCommentData()">댓글</button>
      </div>
      <div class="post-footer">
        <img @click="closePost()" src="../../assets/images/icon/icon_close.png" />
      </div>
    </div>

    <div class="modify-wrap" v-if="this.isModifyHidden">
      <div>
        <h2>제목 : {{ postInfo.post.posts_title }}</h2>
      </div>
      <div class="post-content">
        <textarea v-model="postInfo.post.posts_main"></textarea>
      </div>
      <div class="post-modify-btn">
        <button @click="updatePostAndClose(postInfo.post)">수정하기</button>
      </div>
      <div class="modify-footer">
        <img @click="closeModify()" src="../../assets/images/icon/icon_close.png" />
      </div>
    </div>




    <div v-show="currentTab == 0">
      <div>
      <post
        v-for="postInfo in posts"
        v-bind:key="postInfo.posts_id"
        v-bind:postInfo="postInfo"
        @send-modify="showModify"
      ></post>
      </div>
      <!-- <infinite-loading @infinite="infiniteHandler"></infinite-loading> -->
    </div>

    <infinite-loading v-show="this.currentTab == 0" ref="infiniteLoadingPost" @infinite="infiniteHandler"></infinite-loading>



    <div v-show="currentTab == 1"> 
      <!-- <h1>Q&A게시판</h1> -->
      <input placeholder="검색어를 입력하세요">
      <button>검색</button>
      <input id="check-mine" type="checkbox">
      <label for="check-mine">내꺼 보기</label>
      <qna v-for="qnaInfo in qnas" v-bind:key="qnaInfo.posts_id"
      v-bind:qnaInfo="qnaInfo" @send-modify-qna="showModifyQnA">
      </qna> 
      <!-- <infinite-loading @infinite="infiniteHandlerQnA"></infinite-loading> -->
    </div>
    <infinite-loading ref="infiniteLoadingQnA" @infinite="infiniteHandlerQnA"></infinite-loading>

    <div v-show="currentTab == 2">
      <news></news>
    </div>
    <!-- infinite-loading의 위치를 tab 마다 넣어야 할지 고민중 -->
    
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import axios from "axios";
import SERVER from "@/api/RestApi.js";

import qna from '../feed/QnA.vue';
import comment from '../feed/Comment.vue';
import news from '../feed/News.vue';

import {Carousel, Slide} from 'vue-carousel';

export default {
  name: "Feed",
  components:{
    qna, comment, news, Carousel, Slide
  },
  data() {
    return {
      isPostHidden: false,
      isModifyHidden: false,
      isQnAHidden: false,
      postInfo: {},
      commentData: {
        posts_id: null,
        comment_main: "",
        user_nickname:"",
      },
      
      page: 0,
      posts: [],

      qnaPage: 0,
      qnas:[],
      qnaInfo: {},

      currentTab: 0,
      tabs: [
        require("../../assets/images/icon/icon_post.png"),
        require("../../assets/images/icon/icon_qna.png"),
        require("../../assets/images/icon/icon_news.png"),
      ],
    };
  },
  props: {
    profile_data: {
      default: void 0,
    },
  },
  computed: {
    ...mapState('userStore', ['loginData']),
    ...mapState('postStore', ['comments', 'checkScrap']),
  },
  watch: {
    profile_data : function () {
      this.page = 0
      this.qnaPage = 0
      this.qnas = []
      this.posts = []
      this.$refs.infiniteLoadingPost.stateChanger.reset();
      this.$refs.infiniteLoadingQnA.stateChanger.reset();
      // this.infiniteHandler(this);
      // this.infiniteHandlerQnA(this);
    }
  },
  methods: {
    ...mapActions('postStore', [
      'updatePost',
      'createComment',
      'updateComment',
      //'fetchHealths',
      'goCheckScrap',
    ]),

    infiniteHandlerQnA ($state) {
      let params = {
        params: {
          num: this.qnaPage,
        },
      };
      let url = SERVER.URL + SERVER.ROUTES.qnas;
      console.log('url', url)
      //통신부분
      axios.get(url, params).then(({ data }) => {
          if (data.length) {
            this.qnaPage += 1;
           
            data.forEach((element) => {
              element.health = false;
              element.scrap = false;
               console.log(element)
              // axios
              //   .get(SERVER.URL + SERVER.ROUTES.scrap, {
              //     params: {
              //       user_id: this.loginData.user_id,
              //       posts_id: element.posts_id,
              //     },
              //   })
              //   .then((res) => {
              //     if (res.data > 0) element.scrap = true;
              //   })
              //   .catch((err) => console.log(err));
              element.post.health_count = element.healths.length;
              element.healths.forEach((ele) => {
                console.log(ele)
                if (ele.user_id == this.loginData.user_id) {
                  element.health = true;
                }
              });
            });
            console.log("data",data)
            this.qnas.push(...data);
            $state.loaded();
          } else {
            $state.complete();
          }
        });
      
    },

   // Infinite Scrolling
    infiniteHandler($state) {
      let params = {
        params: {
          num: this.page,
        },
      };
      let url = SERVER.URL;

      if(this.profile_data== undefined || this.profile_data.tab == 0) {
        url += SERVER.ROUTES.posts
        params.params.user_id= -1 //-1일 경우 전체 게시물
        if (this.profile_data != undefined) { // 만약 다른 사람 계정피드에서의 포스트들이면
          var profile_id = this.profile_data.user_id;
          params.params.user_id = profile_id;
          //if (profile_id == this.loginData.user_id) params.params.user_id = 0; //0일 경우 내 게시물
        }
      }
      else if(this.profile_data.tab == 1){ //스크랩 보여주기
        url += SERVER.ROUTES.scrap + "/" + this.profile_data.user_id;
      } 
      console.log(params);
      //통신부분
      axios.get(url, params).then(({ data }) => {
          if (data.length) {
            this.page += 1;
           
            data.forEach((element) => {
              element.health = false;
              element.scrap = false;
               console.log(element)
              axios
                .get(SERVER.URL + SERVER.ROUTES.scrap, {
                  params: {
                    user_id: this.loginData.user_id,
                    posts_id: element.posts_id,
                  },
                })
                .then((res) => {
                  if (res.data > 0) element.scrap = true;
                })
                .catch((err) => console.log(err));
              element.post.health_count = element.healths.length;
              element.healths.forEach((ele) => {
                if (ele.user_id == this.loginData.user_id) {
                  element.health = true;
                }
              });
            });
            this.posts.push(...data);
            $state.loaded();
          } else {
            $state.complete();
          }
        });

    },

    clearCommentData() {
      setTimeout(() => {
        this.commentData = {};
      }, 300);
    },
    closePost() {
      this.$parent.$parent.isHidden = false;
      this.$parent.$parent.$parent.isHidden = false;
      this.isPostHidden = false;
      /*------ 피드 스크롤 unlock ------*/
      document.body.className = "";
      /*------ 피드 스크롤 unlock ------*/
    },
    closeModify() {
      this.$parent.$parent.isHidden = false;
      this.isModifyHidden = false;
      /*------ 피드 스크롤 unlock ------*/
      document.body.className = "";
      /*------ 피드 스크롤 unlock ------*/
    },
    updatePostAndClose(postInfo) {
      this.updatePost(postInfo);
      this.$parent.$parent.isHidden = false;
      this.isModifyHidden = false;
    },
    showModify(info) {
      //댓글이나 글 수정시 부르는 함수
      console.log('showInfo', info)
      this.postInfo = info.postInfo;
      this.$parent.$parent.isHidden = info.isHidden;
      this.$parent.$parent.$parent.isHidden = info.isHidden;
      this.isModifyHidden = info.isModifyHidden;
      document.body.className = "lockbody";
      //댓글창 필요 변수
      this.isPostHidden = info.isPostHidden;
      this.commentData.posts_id = info.postInfo.posts_id;
      this.commentData.user_id = this.loginData.user_id;
    },
    // showModify(info) {
    //   //댓글이나 글 수정시 부르는 함수
    //   console.log('showInfo', info)
    //   this.postInfo = info.postInfo;
    //   this.commentData.posts_id = info.postInfo.posts_id;
    //   this.commentData.user_id = this.loginData.user_id;
    //   // this.$router.push({name: 'Login'});
    //   this.$router.push({name: 'Detail', params: info});
    // },
    showModifyQnA(info){
      console.log(info);
      this.qnaInfo = info.qnaInfo;
      this.$parent.$parent.isHidden = info.isHidden;
      this.$parent.$parent.$parent.isHidden = info.isHidden;
      this.isQnAHidden = info.isQnAHidden;
      document.body.className= "lockbody";

      this.isModifyHidden = info.isModifyHidden;
      this.commentData.posts_id = info.qnaInfo.posts_id;
      this.commentData.user_id = this.loginData.user_id;
    },
    closeQnA() {
      this.$parent.$parent.isHidden = false;
      this.$parent.$parent.$parent.isHidden = false;
      this.isQnAHidden = false;
      /*------ 피드 스크롤 unlock ------*/
      document.body.className = "";
      /*------ 피드 스크롤 unlock ------*/
    },
    timeForToday(time) {
      const today = new Date();
      var timeValue = new Date(time);
      timeValue.setHours(timeValue.getHours() + 9);
      const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
      if (betweenTime < 1) return '방금전';
      if (betweenTime < 60) {
          return `${betweenTime}분전`;
      }
      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
          return `${betweenTimeHour}시간전`;
      }
      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
          return `${betweenTimeDay}일전`;
      }
      return `${Math.floor(betweenTimeDay / 365)}년전`;
    }
  },
  created() {
    this.$store.dispatch("getCheckScrap");
  },
};
</script>

<style scoped>
* {
  font-size: 15px;
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
  border-top-left-radius: 0;
  border-top-right-radius: 0;
  border-bottom: 3px rgb(247, 247, 247) solid;
}

.tab.active {
  background-color:rgb(247, 247, 247);
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

/* --------------------------------------------- */

</style>