<template>
  <div class="feed wrap">

    <div class="tabs">
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
          <a>{{qnaInfo.post.post_date}}</a>
        </div>
      </div>
      <div class="qna-photo-wrap">
        <img :src="qnaInfo.post.imgsrc">
      </div>
      <div class="qna-tag-wrap">
        <div>
          <span class="disease-tag">#{{qnaInfo.post.diseasecode}}</span>
        </div>
        <a class="custom-tag">#이제야알았지</a>
        <a class="custom-tag">#아프지않다는걸</a>
      </div>
      <div class="qna-content-wrap">
        {{qnaInfo.post.posts_main}}
      </div>
      <div class="qna-comment-wrap">
        <comment v-for="comment in qnaInfo.comments" v-bind:key="comment.comment_id"
         v-bind:comment = "comment"  ></comment>
      </div>
      <div class="qna-comment-write-wrap">
        <input placeholder="댓글을 남겨보세요">
        <button>댓글</button>
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
              <a class="time">{{ postInfo.post.post_date }}</a>
            </td>
          </tr>
        </table>
      </div>
      <div class="post-main">{{ postInfo.post.posts_main }}</div>
      <div class="comment-wrap">
        <div class="comment" v-for="comment in postInfo.comments" v-bind:key="comment.post_id">
          <div class="comment-header">
            <table>
              <tr>
                <td>
                  <img class="profile-image" src="../../assets/images/icon/icon_default_image.png" />
                </td>
                <td>
                  <a>{{ comment.user_id }}</a>
                </td>
                <td>
                  <a>{{ comment.comment_date }}</a>
                </td>
              </tr>
            </table>
          </div>
          <div class="comment-content">{{ comment.comment_main }}</div>
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
    </div>




    <div v-show="currentTab == 1"> 
      <h1>Q&A게시판</h1>
      <qna v-for="qnaInfo in qnas" v-bind:key="qnaInfo.posts_id"
      v-bind:qnaInfo="qnaInfo" @send-modify-qna="showModifyQnA">
      </qna> 
    </div>

    <div v-show="currentTab == 2">
      <h1>뉴스게시판</h1>
    </div>
    <!-- infinite-loading의 위치를 tab 마다 넣어야 할지 고민중 -->
    <infinite-loading @infinite="infiniteHandler"></infinite-loading>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import axios from "axios";
import SERVER from "@/api/RestApi.js";

import qna from '../feed/QnA.vue';
import comment from '../feed/Comment.vue';

export default {
  name: "Feed",
  components:{
    qna, comment
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
///////////////////////// 강세응이 추가한 내용 (더미데이터) //////////////////////////////////
      qnaInfo: {},
      qnas: [
        {
          comments: [
            {
              posts_id: 25,
              comment_main : "그러게요 코로나 사라져라 제바알",
              hide : 0,
              comment_id: 1,
              comment_date: "2020-08-06 08:52:44",
              user_id: 2,
              // 실제로는 nickname이 없지만 이거 받아와야됨
              nickname: '김과장'
            }
          ],
          diseasename: "",
          health: false,
          healths: [
            {
              nickname: null,
              posts_id: 25,
              user_id: 1
            }
          ],
          post : {
            category: 1,
            diseasecode: "코로나",
            health_count: 1,
            imgsrc: 'https://image.dongascience.com/Photo/2020/01/008f1295bea0e575bdb0d8fcdd1a7390.jpg',
            post_date: "2020-08-06 08:51:55",
            posts_id: 25,
            posts_main: "코로나바이러스감염증(코로나 19)은 새롭게 발견된 건데 이거 많이 아플까요 많이 안아플까요? 너무 걱정입니다",
            posts_title: "코로나 바이러스 걱정이네요",
            user_id: 1,
          },
          posts_id: 25,
          scrap: false,
          userinfo: {
            email : "woqls8499@gmail.com",
            introduction: "한줄 소개를 작성해주세요",
            nickname: 'Jaebin',
            password: null,
            user_id: 1,
            username: "조재빈"
          }
        },
        {
          comments: [
            {
              posts_id: 26,
              comment_main : "님은 다리골절? 나는 팔골절임",
              hide : 0,
              comment_id: 2,
              comment_date: "2020-08-06 08:52:44",
              user_id: 1,
              // 실제로는 nickname이 없지만 이거 받아와야됨,
              nickname: 'Jaebin'
            },
             {
              posts_id: 26,
              comment_main : "다 비켜라 나는 목골절",
              hide : 0,
              comment_id: 3,
              comment_date: "2020-08-06 08:54:44",
              user_id: 3,
              // 실제로는 nickname이 없지만 이거 받아와야됨,
              nickname: '달린다응쎄'
            },
             {
              posts_id: 26,
              comment_main : "제발 조용히 좀 해라",
              hide : 0,
              comment_id: 4,
              comment_date: "2020-08-06 08:56:44",
              user_id: 1,
              // 실제로는 nickname이 없지만 이거 받아와야됨,
              nickname: '김과장'
            }
          ],
          diseasename: "",
          health: false,
          healths: [
            {
              nickname: null,
              posts_id: 26,
              user_id: 1
            }
          ],
          post : {
            category: 1,
            diseasecode: "다리 골절",
            health_count: 1,
            imgsrc: 'https://lh3.googleusercontent.com/proxy/qYfe36ux7YV3FJLNlLqu97WR0YTJkVSYpaO7J-7gQqQ8d3oDrFGHWwSN5tce9nJUc-63bkMJBoQdtiq2_8T3rO9hbTl9HC366CEVIIuciRD584RDLpktW-EuF7Mn4yuWRthKK4UkTWvx0KYGPlhBeXujKQ0BJK8',
            post_date: "2020-08-06 09:00:55",
            posts_id: 26,
            posts_main: "다리가 골절되면 많이 못 걷잖아요 근데 저는 지금 잘 걸을 수 있을 것 같은데 어떡하죠? ㅋㅋㅋㅋㅋㅋ",
            posts_title: "다리가 골절되면..",
            user_id: 2,
          },
          posts_id: 26,
          scrap: false,
          userinfo: {
            email : "wlgus@gmail.com",
            introduction: "한줄 소개를 작성해주세요",
            nickname: '김과장',
            password: null,
            user_id: 2,
            username: '김지현'
          }
        }
      ],
/////////////////////////////////////////////////////////////////////////////////////////
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
      this.posts = []
      this.infiniteHandler(this);
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

   // Infinite Scrolling
    infiniteHandler($state) {
      //TODO 해당 유저아이디의 포스트만 반환부분 분기!
      let params = {
        params: {
          num: this.page,
        },
      };

      let url = SERVER.URL;

      if(this.profile_data== undefined || this.profile_data.tab == 0) {
        url += SERVER.ROUTES.posts+"/new"
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
                console.log(ele)
                if (ele.user_id == this.loginData.user_id) {
                  element.health = true;
                }
              });
            });
            console.log("data",data)
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
      this.isModifyHidden = info.isModifyHidden;
      document.body.className = "lockbody";
      //댓글창 필요 변수
      this.isPostHidden = info.isPostHidden;
      this.commentData.posts_id = info.postInfo.posts_id;
      this.commentData.user_id = this.loginData.user_id;
    },
    showModifyQnA(info){
      this.qnaInfo = info.qnaInfo;
      this.$parent.$parent.isHidden = info.isHidden;
      this.isQnAHidden = info.isQnAHidden;
      document.body.className= "lockbody";

      this.isModifyHidden = info.isModifyHidden;
    },
    closeQnA() {
      this.$parent.$parent.isHidden = false;
      this.isQnAHidden = false;
      /*------ 피드 스크롤 unlock ------*/
      document.body.className = "";
      /*------ 피드 스크롤 unlock ------*/
    },
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
  margin-left: 0;
  width: 100%; 
  padding: 0 0;
  margin-bottom: 50px;
}

.tabs {
  width: 100%;
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