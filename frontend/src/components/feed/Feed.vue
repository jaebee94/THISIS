<template>
  <div class="feed wrap">
    <div v-if="this.isPostHidden" class="post" ref="post">
      <div class="post-header">
        <table>
          <tr>
            <td>
              <img class="profile-image" src="../../assets/images/icon/icon_default_image.png" />
            </td>
            <td>
              <a class="name">{{ postInfo.post.nickname }}</a>
            </td>
            <td>
              <a class="time">{{ postInfo.post.post_date }}</a>
            </td>
          </tr>
        </table>
      </div>
      <div class="post-main">{{ postInfo.post.posts_main }}</div>
      <div class="comment-wrap">
        <div class="comment" v-for="comment in comments" v-bind:key="comment.post_id">
          <div class="comment-header">
            <table>
              <tr>
                <td>
                  <img class="profile-image" src="../../assets/images/icon/icon_default_image.png" />
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
            v-if="comment.posts_id == postInfo.post_id"
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

    <div class="feed" v-for="postInfo in posts" v-bind:key="postInfo.posts_id">
      <div class="feed-header">
        <table>
          <tr>
            <td>
              <img
                class="profile-image"
                @click="goProfile(postInfo.post.user_id)"
                src="../../assets/images/icon/icon_default_image.png"
              />
            </td>
            <td>
              <a class="name" @click="goProfile(postInfo.post.user_id)">{{ postInfo.post.nickname }}</a>
            </td>
            <td>
              <a class="time">{{ postInfo.post.post_date }}</a>
            </td>
          </tr>
        </table>
      </div>
      <div class="feed-main">{{ postInfo.post.posts_main }}</div>
      <div class="feed-footer">
        <table>
          <tr>
            <td>
              <span v-if="postInfo.healths.user_id"></span>
              <img v-show="postInfo.health" :src="isHealth" @click="clickHealth(postInfo)" />
              <img v-show="!postInfo.health" :src="isNotHealth" @click="clickHealth(postInfo)" />
              <!-- <img :src="postInfo.health?isHealth:isNotHealth" @click="clickHealth(postInfo)"> -->
              <!-- <span>{{ postInfo.healths.length }}</span> -->
              <span class="health-count">{{ postInfo.post.health_count }}</span>
            </td>
            <td>
              <img @click="showPost(postInfo)" src="../../assets/images/icon/icon_talk.png" />
            </td>
            <td>
              <img
                v-show="postInfo.scrap"
                @click="clickScrap(postInfo)"
                src="../../assets/images/icon/icon_scrap_select.png"
              />
              <img
                v-show="!postInfo.scrap"
                @click="clickScrap(postInfo)"
                src="../../assets/images/icon/icon_scrap_unselect.png"
              />
            </td>
            <td>
              <img
                v-if="loginData.user_id == postInfo.post.user_id"
                @click="showModify(postInfo)"
                src="../../assets/images/icon/icon_edit_unselect.png"
              />
            </td>
          </tr>
        </table>
      </div>
    </div>
    <infinite-loading @infinite="infiniteHandler"></infinite-loading>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import axios from "axios";
import InfiniteLoading from "vue-infinite-loading";
import SERVER from "@/api/RestApi.js";

export default {
  name: "Feed",
  components: {
    InfiniteLoading,
  },
  data() {
    return {
      isHealth: require("../../assets/images/icon/icon_like_select.png"),
      isNotHealth: require("../../assets/images/icon/icon_like_unselect.png"),
      isScraped: false,
      isPostHidden: false,
      isModifyHidden: false,
      postInfo: {},
      commentData: {
        posts_id: null,
        comment_main: "",
        user_nickname: null,
      },
      healthData: {
        posts_id: null,
        user_id: null,
      },
      page: 0,
      posts: [],
    };
  },
  computed: {
    ...mapState('userStore', ['loginData']),
    ...mapState('postStore', ['comments', 'checkScrap']),
  },
  methods: {
    ...mapActions('postStore', [
      'fetchPosts',
      'updatePost',
      'createComment',
      'fetchComment',
      'updateComment',
      'health',
      'fetchHealths',
      'scrap',
      'goCheckScrap',
      'deleteScrap,',
    ]),
    ...mapActions('profileStore', [
      'goProfile'
    ]),

    // Infinite Scrolling
    infiniteHandler($state) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.posts, {
          params: {
            num: this.page,
          },
        })
        .then(({ data }) => {
          if (data.length) {
            this.page += 1;
            data.forEach((element) => {
              console.log("eleement", element);
              element.health = false;
              if (element.healths.length > 0) console.log(element);
              element.scrap = false;
              axios.get(SERVER.URL + SERVER.ROUTES.scrap, {
                params: {
                  posts_id: element.post_id,
                  user_id: this.loginData.user_id,
                },
              })
                .then((res) => {
                  if (res.data > 0) element.scrap = true;
                })
                .catch((err) => console.log(err));
              element.post.health_count = element.healths.length;
              element.healths.forEach((ele) => {
                if (ele.nickname == this.loginData.nickname) {
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

    showPost(postInfo) {
      this.postInfo = postInfo;
      this.$parent.$parent.isHidden = true;
      this.isPostHidden = true;
      this.fetchComments(postInfo.post_id);
      this.commentData.posts_id = postInfo.post_id;
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
    clickHealth(post) {
      if (post.health == true) {
        post.health = false;
        post.post.health_count -= 1;
      } else {
        post.health = true;
        post.post.health_count += 1;
      }

      this.healthData.posts_id = post.post.posts_id;
      this.healthData.user_id = this.loginData.user_id;
      this.health(this.healthData);
    },
    clickScrap(post) {
      if (post.scrap == true) {
        post.scrap = false;
        this.deleteScrap(post.post_id);
      } else {
        post.scrap = true;
        this.scrap(post.post_id);
      }
    },
    showModify(postInfo) {
      this.postInfo = postInfo;
      this.$parent.$parent.isHidden = true;
      this.isModifyHidden = true;
    },
    closeModify() {
      this.$parent.$parent.isHidden = false;
      this.isModifyHidden = false;
    },
    updatePostAndClose(postInfo) {
      this.updatePost(postInfo);
      this.$parent.$parent.isHidden = false;
      this.isModifyHidden = false;
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
</style>