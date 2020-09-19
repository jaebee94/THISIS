<template>
  <div class="feed wrap">
    <div class="tabs" v-if="profile_data == undefined">
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

    <div>
      <Modal class="modal" v-if="this.isShowModal" v-on:close="deleteCommentData">
        <template v-slot:modal-text>{{ modalText }}</template>
      </Modal>
    </div>

    <div v-if="this.isQnAHidden" class="post" ref="qna">
      <div class="qna-wrapper">
        <div class="qna-header">
          <div class="title">
            <strong>{{qnaInfo.post.posts_title}}</strong>
          </div>
          <span class="nickname">{{qnaInfo.userinfo.nickname}}</span>
          <img class="doctor-image" v-if ="qnaInfo.userinfo.role == 'doctor'" src='../../assets/images/icon/icon_doctor_mark.png'/> 
          <span class="time">{{ timeForToday(qnaInfo.post.post_date) }}</span>
        </div>
        <div v-if="qnaInfo.post.imgsrc != null" class="qna-photo-wrap">
          <img :src="qnaInfo.post.imgsrc" />
        </div>
        <div class="qna-main-content-wrap">
          <div class="qna-tag-wrap">
            <div v-if="qnaInfo.diseasename != ''">
              <strong>관련 질병 :</strong>
              <span class="disease-tag">#{{qnaInfo.diseasename}}</span>
            </div>
            <br />
            <!-- <div class="qna-custom-tag-wrap">
              <div><strong>관련 태그 :</strong>
              <a
                class="custom-tag"
                v-for="tag in qnaInfo.tags"
                v-bind:key="tag.tagid"
              >#{{tag.tagname}}</a></div>
            </div>-->
          </div>
          <div class="qna-content-wrap" :class="{active: isActive}" @click="isActive = !isActive">
            {{qnaInfo.post.posts_main}}
            <a
              class="custom-tag"
              v-for="tag in qnaInfo.tags"
              v-bind:key="tag.tagid"
            > #{{tag.tagname}} </a>
          </div>
        </div>
        <div :class="{'wide' : qnaInfo.post.imgsrc == null}" class="qna-comment-wrap">
          <comment
            v-for="comment in comments"
            v-bind:key="comment.comment_id"
            v-bind:comment="comment"
            @check-delete="showModal"
          ></comment>
        </div>
        <div class="qna-comment-write-wrap">
          <input v-on:keyup.enter="commentInfo(qnaInfo), createComment(commentData), clearCommentData()" v-model="commentData.comment_main" placeholder="내용을 입력하세요" />
          <button @click="commentInfo(qnaInfo), createComment(commentData), clearCommentData()">댓글</button>
        </div>
        <div class="post-footer">
          <img @click="closeQnA()" src="../../assets/images/icon/icon_close.png" />
        </div>
      </div>
    </div>

    <div v-if="this.isPostHidden" class="post" ref="post">
      <div class="post-header">
        <table>
          <tr>
            <td>
              <img v-if="postInfo.userinfo.userimage==''" class="profile-image" src="../../assets/images/icon/icon_default_image.png" />
              <img v-else class="profile-image" :src="postInfo.userinfo.userimage" />
            </td>
            <td>
              <a class="name">{{ postInfo.userinfo.nickname }}</a>
              <img class="doctor-image" v-if ="postInfo.userinfo.role == 'doctor'" src='../../assets/images/icon/icon_doctor_mark.png'/> 
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
            <img :src="postInfo.post.imgsrc" />
            <!-- <img src="../../assets/sample.jpg"> -->
          </div>
        </slide>
        <slide class="myslide">
          <div class="post-main">
            <strong>{{postInfo.post.posts_title}}</strong> <br>
            <a v-if="postInfo.diseasename != ''" class="post-disease-tag">#{{postInfo.diseasename}}</a><br>
            <a class="post-custom-tag" v-for="tag in postInfo.tags" v-bind:key="tag.tagid">#{{tag.tagname}} </a> <br>
            {{ postInfo.post.posts_main }}
          </div>
        </slide>
      </carousel>

      <div class="comment-wrap">
        <comment
          v-for="comment in comments"
          v-bind:key="comment.comment_id"
          v-bind:comment="comment"
          @check-delete="showModal"
        ></comment>
      </div>
      <div class="qna-comment-write-wrap">
        <input v-on:keyup.enter="commentInfo(postInfo), createComment(commentData), clearCommentData()" v-model="commentData.comment_main" placeholder="내용을 입력하세요" />
        <button @click="commentInfo(postInfo), createComment(commentData), clearCommentData()">댓글</button>
      </div>
      <div class="post-footer">
        <img @click="closePost()" src="../../assets/images/icon/icon_close.png" />
      </div>
    </div>

    <!-- 아예 업로드에서 수정하기로 바꿈
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
    </div>-->

    <div class="report-modal" v-if="this.isReportHidden">
      <table class ="police-table">
        <tr >
          <td><strong>신고하기</strong></td>
        </tr>
        <tr>
          <td>
            <div class="radio">
              <input type="radio" id="radio-A" name="report" value="A" v-model="selectedReason" checked/>
              <label for="radio-A" class="radio-label">성적인 내용 포함</label>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="radio">
              <input type="radio" id="radio-B" name="report" v-model="selectedReason" value="B" />
              <label for="radio-B" class="radio-label">폭력적 또는 혐오스러운 내용 포함</label>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="radio">
              <input type="radio" id="radio-C" name="report" v-model="selectedReason" value="C" />
              <label for="radio-C" class="radio-label">증오 또는 악의적인 내용 포함</label>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="radio">
              <input type="radio" id="radio-D" name="report" v-model="selectedReason" value="D" />
              <label for="radio-D" class="radio-label">유해한 위험 내용 포함</label>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="radio">
              <input type="radio" id="radio-E" name="report" v-model="selectedReason" value="E" />
              <label for="radio-E" class="radio-label">불확실한 의학 내용 전파</label>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="radio">
              <input type="radio" id="radio-F" name="report" v-model="selectedReason" value="F" />
              <label for="radio-F" class="radio-label">권리 침해</label>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div id="police-confirm" @click="policeConfirm()">확인</div>
            <div id="police-cancel" @click="policeCancel()">취소</div>
          </td>
        </tr>
        <!-- <tr><td>확인</td><td>취소</td></tr> -->
      </table>
    </div>

    <div v-show="currentTab == 0">
      <div>
        <post
          v-for="postInfo in posts"
          v-bind:key="postInfo.posts_id"
          v-bind:postInfo="postInfo"
          @send-modify="showModify"
          @make-report="makeReport"
          @delete-post="deletePost"
        ></post>
      </div>
      <infinite-loading
        v-if="this.currentTab == 0"
        ref="infiniteLoadingPost"
        @infinite="infiniteHandler"
      >
        <div slot="spinner">Loading...</div>
        <div slot="no-more">더 이상 게시글이 없습니다 :)</div>
        <div slot="no-results">게시글이 존재하지 않습니다.</div>
      </infinite-loading>
    </div>

    <div v-show="currentTab == 1">
      <!-- <h1>Q&A게시판</h1> -->
      <form class="qna-search-panel" v-on:submit.prevent="searchQna">
        <select name="qnaoption" id="qnaoption" v-model="qnaoption">
          <option value="" hidden>카테고리</option>
          <option value="all">전체보기</option>
          <option value="text">글내용</option>
          <option value="title">글제목</option>
          <option value="disease">질병명</option>
        </select>
        <input v-model="qnakeyword" id="qnakeyword" v-on:keyup.enter="searchQna()" placeholder="검색어를 입력하세요" />
        <button type="submit"><img src='../../assets/images/icon/icon_search_unselect.png'></button>
        <!-- <input type="image" name="submit" border="0" alt="" src="../../assets/images/icon/icon_search_unselect.png"> -->
      </form>
      <qna
        v-for="qnaInfo in qnas"
        v-bind:key="qnaInfo.posts_id"
        v-bind:qnaInfo="qnaInfo"
        @send-modify-qna="showModifyQnA"
        @make-report="makeReport"
        @delete-qna="deleteQnA"
      ></qna>
      <infinite-loading
        v-if="this.currentTab == 1"
        ref="infiniteLoadingQnA"
        @infinite="infiniteHandlerQnA"
      >
        <div slot="spinner">Loading...</div>
        <div slot="no-more">더 이상 게시글이 없습니다 :)</div>
        <div slot="no-results">게시글이 존재하지 않습니다.</div>
        <div slot="error" slot-scope="{ trigger }">
          Error message, click
          <a href="javascript:;" @click="trigger">here</a> to retry
        </div>
      </infinite-loading>
    </div>

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

import qna from "../feed/QnA.vue";
import comment from "../feed/Comment.vue";
import news from "../feed/News.vue";
import { Carousel, Slide } from "vue-carousel";
import cookies from "vue-cookies";

export default {
  name: "Feed",
  components: {
    qna,
    comment,
    news,
    Carousel,
    Slide,
  },
  data() {
    return {
      isPostHidden: false,
      isModifyHidden: false,
      isQnAHidden: false,
      isReportHidden: false,
      postInfo: {},
      commentData: {
        posts_id: null,
        comment_main: "",
        user_nickname: "",
      },

      page: 0,
      posts: [],

      qnaPage: 0,
      qnas: [],
      qnaInfo: {},
      qnakeyword: "",
      qnaoption: "",
      searchType: "nonsearch",
      isShowModal: false,
      selectedComment: null,
      modalText: "",
      currentTab: 0,
      tabs: [
        require("../../assets/images/icon/icon_post.png"),
        require("../../assets/images/icon/icon_qna.png"),
        require("../../assets/images/icon/icon_news.png"),
      ],

      isActive: false,

      selectedReason: '',
    };
  },
  props: {
    profile_data: {
      default: void 0,
    },
  },
  beforeCreate() {
    this.$parent.$parent.isLoaded = false;
  },
  mounted() {
    this.$parent.$parent.isLoaded = true;
  },
  computed: {
    ...mapState("userStore", ["loginData"]),
    ...mapState("postStore", ["comments", "checkScrap"]),
    ...mapState("diseaseStore", ["diseases"]),
  },
  watch: {
    profile_data: function () {
      this.page = 0;
      this.qnaPage = 0;
      this.qnas = [];
      this.posts = [];
      this.$refs.infiniteLoadingPost.stateChanger.reset();
      this.$refs.infiniteLoadingQnA.stateChanger.reset();
    },
    selectedReason: function(newRole) {
      this.selectedReason = newRole;
    }
  },
  methods: {
    ...mapActions("postStore", [
      "updatePost",
      "createComment",
      "updateComment",
      "goCheckScrap",
      "deleteComment",
    ]),
    ...mapActions("diseaseStore", ["getFollowingDisease"]),

    searchQna() {
      if(this.qnaoption == '') {
        alert('검색할 카테고리를 선택해주세요!')
        return;
      }
      this.$refs.infiniteLoadingQnA.stateChanger.reset();
      this.qnaPage = 0;
      this.qnas = [];
      if (this.qnaoption != "all") {
        this.searchType = "search";
      } else {
        this.searchType = "nonsearch";
        this.qnakeyword = "";
      }
    },

    async infiniteHandlerQnA($state) {
      if (this.searchType == "search") {
        //검색일 경우
        if (this.qnaoption == "text") {
          var params2 = {
            params: {
              num: this.qnaPage,
              keyword: this.qnakeyword,
            },
            headers: { accessToken: cookies.get("access-token") },
          };
          let url = SERVER.URL + SERVER.ROUTES.qnasmain;
          //통신부분
          await axios.get(url, params2).then(({ data }) => {
            if (data.length) {
              this.qnaPage += 1;

              data.forEach((element) => {
                element.health = false;
                element.scrap = false;
                element.post.health_count = element.healths.length;
                element.healths.forEach((ele) => {
                  if (ele.user_id == this.loginData.user_id) {
                    element.health = true;
                  }
                });
              });
              this.qnas.push(...data);
              $state.loaded();
            } else {
              $state.complete();
            }
          });
        } //글내용 검색
        else if (this.qnaoption == "title") {
          //글제목 검색
          var params3 = {
            params: {
              num: this.qnaPage,
              keyword: this.qnakeyword,
            },
            headers: { accessToken: cookies.get("access-token") },
          };
          let url = SERVER.URL + SERVER.ROUTES.qnastitle;
          //통신부분
          await axios.get(url, params3).then(({ data }) => {
            if (data.length) {
              this.qnaPage += 1;

              data.forEach((element) => {
                element.health = false;
                element.scrap = false;
                element.post.health_count = element.healths.length;
                element.healths.forEach((ele) => {
                  if (ele.user_id == this.loginData.user_id) {
                    element.health = true;
                  }
                });
              });
              this.qnas.push(...data);
              $state.loaded();
            } else {
              $state.complete();
            }
          });
        } //글제목 검색
        else if (this.qnaoption == "disease") {
          //질병명 검색
          var params4 = {
            params: {
              num: this.qnaPage,
              keyword: this.qnakeyword,
            },
            headers: { accessToken: cookies.get("access-token") },
          };
          let url = SERVER.URL + SERVER.ROUTES.qnasdisease;
          //통신부분
          await axios.get(url, params4).then(({ data }) => {
            if (data.length) {
              this.qnaPage += 1;

              data.forEach((element) => {
                element.health = false;
                element.scrap = false;
                element.post.health_count = element.healths.length;
                element.healths.forEach((ele) => {
                  if (ele.user_id == this.loginData.user_id) {
                    element.health = true;
                  }
                });
              });
              this.qnas.push(...data);
              $state.loaded();
            } else {
              $state.complete();
            }
          });
        } //질병명 검색
      } else {
        //검색이 아닐때
        var params = {
          params: {
            num: this.qnaPage,
          },
          headers: { accessToken: cookies.get("access-token") },
        };

        let url = SERVER.URL + SERVER.ROUTES.qnas;
        //통신부분
        await axios.get(url, params).then(({ data }) => {
          if (data.length) {
            this.qnaPage += 1;

            data.forEach((element) => {
              element.health = false;
              element.scrap = false;
              element.post.health_count = element.healths.length;
              element.healths.forEach((ele) => {
                if (ele.user_id == this.loginData.user_id) {
                  element.health = true;
                }
              });
            });
            this.qnas.push(...data);
            $state.loaded();
          } else {
            $state.complete();
          }
        });
      } //else
    },

    // Infinite Scrolling
    infiniteHandler($state) {
      var params = {
        params: {
          num: this.page,
        },
        headers: { accessToken: cookies.get("access-token") },
      };
      let url = SERVER.URL;
      if (this.profile_data == undefined || this.profile_data.tab == 0) {
        url += SERVER.ROUTES.posts;
        params.params.user_id = -1; //-1일 경우 전체 게시물
        if (this.profile_data != undefined) {
          // 만약 다른 사람 계정피드에서의 포스트들이면
          var profile_id = this.profile_data.user_id;
          params.params.user_id = profile_id;
          //if (profile_id == this.loginData.user_id) params.params.user_id = 0; //0일 경우 내 게시물
        }
      } else if (this.profile_data.tab == 1) {
        //스크랩 보여주기
        url += SERVER.ROUTES.scrap + "/" + this.profile_data.user_id;
      }
      //통신부분
      axios.get(url, params).then(({ data }) => {
        if (data.length) {
          this.page += 1;
          data.forEach((element) => {
            element.health = false;
            element.scrap = false;
            axios
              .get(SERVER.URL + SERVER.ROUTES.scrap, {
                params: {
                  user_id: this.loginData.user_id,
                  posts_id: element.posts_id,
                },
                headers: { accessToken: cookies.get("access-token") },
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
    deleteCommentData(flag) {
      if (flag) {
        this.deleteComment(this.selectedComment);
      }
      this.isShowModal = false;
    },
    commentInfo(info) {
      this.commentData.posts_id = info.posts_id;
    },
    closePost() {
      this.posts = [];
      this.page = 0;
      this.$refs.infiniteLoadingPost.stateChanger.reset();

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
    showModifyQnA(info) {
      this.qnaInfo = info.qnaInfo;
      this.$parent.$parent.isHidden = info.isHidden;
      this.$parent.$parent.$parent.isHidden = info.isHidden;
      this.isQnAHidden = info.isQnAHidden;
      document.body.className = "lockbody";

      this.isModifyHidden = info.isModifyHidden;
      this.commentData.posts_id = info.qnaInfo.posts_id;
      this.commentData.user_id = this.loginData.user_id;
    },
    showModal(modalInfo) {
      this.$parent.$parent.isHidden = true;
      this.$parent.$parent.$parent.isHidden = true;
      document.body.className = "lockbody";
      //this.isQnAHidden= false;
      //this.isPostHidden= false;
      this.isShowModal = true;
      this.selectedComment = modalInfo.data;
      this.modalText = modalInfo.msg;
    },
    closeQnA() {
      this.qnas = [];
      this.qnaPage = 0;
      this.$refs.infiniteLoadingQnA.stateChanger.reset();
      this.$parent.$parent.isHidden = false;
      this.$parent.$parent.$parent.isHidden = false;
      this.isQnAHidden = false;
      this.isActive = false;
      /*------ 피드 스크롤 unlock ------*/
      document.body.className = "";
      /*------ 피드 스크롤 unlock ------*/
    },
    timeForToday(time) {
      const today = new Date();
      var timeValue = new Date(time);
      timeValue.setHours(timeValue.getHours() + 9);
      const betweenTime = Math.floor(
        (today.getTime() - timeValue.getTime()) / 1000 / 60
      );
      if (betweenTime < 1) return "방금전";
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
    },
    makeReport(postInfo) {
      this.postInfo = postInfo;
      this.$parent.$parent.isHidden = true;
      this.$parent.$parent.$parent.isHidden = true;
      this.isReportHidden = true;
      document.body.className = "lockbody";
      this.selectedReason = 'A';
    },
    deletePost() {
      this.page = 0;
      this.posts = [];
      this.$refs.infiniteLoadingPost.stateChanger.reset();
    },
    deleteQnA() {
      this.qnaPage = 0;
      this.qnas = [];
      this.$refs.infiniteLoadingQnA.stateChanger.reset();
    },
    policeConfirm() {
      var reason = '';
      if(this.selectedReason == 'A'){
        reason = '성적인 내용 포함';
      } else if (this.selectedReason == 'B') {
        reason = '폭력적 또는 혐오스러운 내용 포함';
      } else if (this.selectedReason == 'C') {
        reason = '증오 또는 악의적인 내용 포함';
      } else if (this.selectedReason == 'D') {
        reason = '유해한 위험 내용 포함';
      } else if (this.selectedReason == 'E') {
        reason = '불확실한 의학 내용 전파';
      } else if (this.selectedReason == 'F') {
        reason = '권리 침해';
      } 
      let params = {
        posts_id: this.postInfo.post.posts_id,
        reason: reason,
        user_id: this.loginData.user_id
      }
      axios.post(SERVER.URL + '/police', params, { headers: { accessToken : cookies.get('access-token')}})
        .then(() => {
          // 신고 성공
          alert('게시물을 신고했습니다')
          this.$parent.$parent.isHidden = false;
          this.isReportHidden = false;
          /*------ 피드 스크롤 unlock ------*/
          document.body.className = "";
          /*------ 피드 스크롤 unlock ------*/
        })
        .catch((err) => {
          console.log(err);
          // 신고 실패
          alert('게시물 신고에 실패했습니다')
          this.$parent.$parent.isHidden = false;
          this.isReportHidden = false;
          /*------ 피드 스크롤 unlock ------*/
          document.body.className = "";
          /*------ 피드 스크롤 unlock ------*/
        })
    },
    policeCancel() {
      this.$parent.$parent.isHidden = false;
      this.$parent.$parent.$parent.isHidden = false;
      this.isReportHidden = false;
      /*------ 피드 스크롤 unlock ------*/
      document.body.className = "";
      /*------ 피드 스크롤 unlock ------*/
    },
  },
  created() {

    this.$refs.infiniteLoadingPost.stateChanger.reset();
    this.$refs.infiniteLoadingQnA.stateChanger.reset();
    this.$store.dispatch("getCheckScrap");
    this.$store.dispatch("diseaseStore/getFollowingDisease");
    this.searchType = "nonsearch";
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
  width : 30px;
  height: 30px;
  object-fit: cover;
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

.modal {
  position: fixed;
  z-index: 100;
  width: 70%;
  height: 20%;
  background-color: white;
}

.post {
  position: fixed;
  z-index: 99;
  top: 10%;
  width: 92%;
  height: 80%;
  background-color: white;
  border-radius: 5px;
   margin-left: 4%;
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
.post-header table tr td:nth-child(1) {
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
  height: 250px;
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
/* .modify-wrap {
  position: fixed;
  z-index: 99;
  top: 10%;
  width: 92%;
  height: 70%;
  background-color: white;
  border-radius: 5px;
} */

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

/* ========== qna 검색하는 부분 ============*/

.qna-search-panel {
  width: 100%;
  margin-top: 10px;
  margin-bottom: 10px;
  /* margin: 10px 5% auto; */
}

.qna-search-panel select {
  border: none;
  position: absolute;
  outline: none;
  background-color: rgb(240, 240, 240);
  margin-top: 5px;
  width: 80px;
  padding-left: 5px;
  font-size: 13px;
}

.qna-search-panel option {
  border: none;
  margin: 4px 2px auto;
  font-size: 13px;
}

.qna-search-panel input {
  width: calc(90% - 95px);
  height: 30px;
  border: none;
  border-radius: 5px;
  outline: none;
  background-color: rgb(240, 240, 240);
  padding-left: 100px;
  font-weight: 600;
  font-size: 13px;
}

.qna-search-panel button {
  position: absolute;
  right: 10%;
  margin-top: 5px;
  width: 20px;
  height: 25px;
  border: none;
  outline: none;
  background-color: rgb(240, 240, 240);
}

.qna-search-panel button img {
  width: 20px;
  height: 20px;
}


/* ========== qna 검색하는 부분 ============*/


/* ----- qna 내용 자세히 보기 모달창 코드 -----  */

.post .qna-wrapper {
  margin: 15px 0px 15px 0px;
  height: 95%;
  overflow: auto;
}

.qna-header {
  text-align: left;
  padding-left: 5%;
  margin-bottom: 10px;
}

.qna-header .title strong {
  font-size: 20px;
}

.qna-header .nickname {
  font-size: 15px;
  /* position: relative; */
  /* margin: 10px; */
  left: 60%;
}

.qna-header .time {
  color: slategray;
  font-size: 10px;
  float: right;
  margin: 5px 10px 5px 10px;
}

.qna-photo-wrap {
  width: 100%;
  /* height: 40%; */
  /* margin: 0 auto; */
  margin-top: 10px;
  margin-bottom: 10px;
}

.qna-photo-wrap img {
  width: 100%;
  height: 95%;
  object-fit: cover;
}

.qna-main-content-wrap {
  width: 90%;
  margin-left: 5%;
  /* height: 80px; */
  /* overflow: auto; */
}

.qna-tag-wrap {
  width: 100%;
}

.qna-tag-wrap div {
  float: left;
  margin-bottom: 5px;
}

.qna-tag-wrap .disease-tag {
  background-color: rgb(0, 171, 132);
  color: white;
  font-size: 14px;
  font-weight: 600;
  border: none;
  border-radius: 5px;
  padding: 3px 8px;
  margin-left: 5px;
}

.qna-custom-tag-wrap {
  width: 100%;
  float: left;
}

.custom-tag {
  color: rgb(0, 171, 132);
}

.qna-content-wrap {
  width: 100%;
  max-height: 100px;
  text-align: left;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  word-wrap: break-word;
  text-overflow: ellipsis;
  -webkit-transition: all 0.3s linear 0s;
  transition: all 0.3s linear 0s;
}

.qna-content-wrap.active {
  display: inline-block;
  max-height: 1000px;
  overflow: auto;
  white-space: normal;
  word-wrap: unset;
  text-overflow: unset;
  -webkit-transition: all 0.3s linear 0s;
  transition: all 0.3s linear 0s;
}

.post:nth-child(1) {
  background-color: red;
}

.qna-comment-wrap {
  width: 100%;
  /* height: 200px;
  overflow: auto; */
  margin-bottom: 50px;
  margin-top: 10px;
  border-top: 1px slategray solid;
}

.comment-module-wrap:last-child {
  margin-bottom: 50px;
}
/* 
.qna-comment-wrap.wide {
  height: 400px;
  margin-bottom: 50px;
} */

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

.report-modal {
  position: fixed;
  z-index: 99;
  top: 25%;
  width: 92%;
  height: 50%;
  background-color: white;
  border-radius: 5px;
  margin-left: 4%;
}

.police-table{
  margin-top: 20px;
  margin-left: auto; margin-right: auto;
  /* height: 100%; */
  text-align: left;
}

.police-table tr:first-child {
  height: 40px;
}

.police-table tr:nth-child(1) td {
  text-align: center;
}

.police-table tr:last-child td {
  display: flex;
  width: 100%;
}

.police-table tr:last-child td div {
  width: 40%;
  text-align: center;
  margin-top: 20px;
}

.police-table tr td {
  padding: 0;
}

#police-confirm {
  margin-left: 5%;
  background-color: rgb(0, 171, 132);
  color: white;
  border:none;
  outline: none;
  font-weight: 600;
  border-radius: 5px;
  height: 30px;
  line-height: 2;
}

#police-cancel {
  margin-left: 10%;
  background-color: rgb(200, 200, 200);
  color: slategray;
  border:none;
  outline: none;
  font-weight: 600;
  border-radius: 5px;
  height: 30px;
  line-height: 2;
}

/* ======================= radio button css ============================ */
.radio {
  margin: 0.5rem;
}
.radio input[type="radio"] {
  position: absolute;
  opacity: 0;
}
.radio input[type="radio"] + .radio-label:before {
  content: '';
  background: #f4f4f4;
  border-radius: 100%;
  border: 1px solid #b4b4b4;
  display: inline-block;
  width: 1.4em;
  height: 1.4em;
  position: relative;
  top: -0.2em;
  margin-right: 1em;
  vertical-align: top;
  cursor: pointer;
  text-align: center;
  -webkit-transition: all 250ms ease;
  transition: all 250ms ease;
}
.radio input[type="radio"]:checked + .radio-label:before {
  background-color: #3197EE;
  box-shadow: inset 0 0 0 4px #f4f4f4;
}
.radio input[type="radio"]:focus + .radio-label:before {
  outline: none;
  border-color: #3197EE;
}
.radio input[type="radio"]:disabled + .radio-label:before {
  box-shadow: inset 0 0 0 4px #f4f4f4;
  border-color: #b4b4b4;
  background: #b4b4b4;
}
.radio input[type="radio"] + .radio-label:empty:before {
  margin-right: 0;
}

.post .doctor-image {
  width: 15px;
  height: 15px;
  margin-left : 2px;
}

/* ======================= radio button css ============================ */

/* --------------------------------------------- */
</style>