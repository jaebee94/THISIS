<template>
  <div class="feed">
    <div v-if="isDelete" @click="isDelete = !isDelete" class="black-layer"></div>
    <div class="feed-header">
      <table class = "article-header">
        <tr>
          <td v-if ="postInfo.userinfo.userimage!=null">
            <img
              class="profile-image"
              @click="goProfile(postInfo.post.user_id)"
              :src="postInfo.userinfo.userimage"
            />
          </td>
          <td v-else>
             <img
              class="profile-image"
              @click="goProfile(postInfo.post.user_id)"
              src="../../assets/user2.png"
            />
          </td>
          <td>
            <a
              class="name"
              @click="goProfile(postInfo.post.user_id)"
            >{{ postInfo.userinfo.nickname }}</a>
          <a v-if="postInfo.post.category != 0">Q&A 게시물</a>
          </td>
          <td>
            <a class="time">{{ timeForToday(postInfo.post.post_date) }}</a>
          </td>
          <td  >
            <div class="dropdown" >
              <img class="dropmenu" @click="isDelete = !isDelete" src="../../assets/images/icon/icon_3dots.png" />
            </div>
            <div v-show="isDelete" class="dropdown-content">
              <a href="#" v-if="loginData.user_id == postInfo.post.user_id" @click="changeSelectPost(postInfo,'modify')">수정</a>
              <a href="#" v-if="loginData.user_id == postInfo.post.user_id" @click="showModal(postInfo)">삭제</a>
              <a href="#" v-if="loginData.user_id != postInfo.post.user_id"  @click="showModal(postInfo)">신고</a>
            </div>
             <!-- </div> -->
          </td>
        </tr>
      </table>
    </div>
    <div class="feed-main" >
      <img v-show="postInfo.post.imgsrc != null" :src="postInfo.post.imgsrc" />
      <div class = "tag-header" v-show="postInfo.diseasename !=''" >
       <span >{{postInfo.diseasename}}</span>
    </div>
      <div class="post-content" 
      :class="{active : isActive}" @click="isActive = !isActive"
      >
      <strong @click="goProfile(postInfo.post.user_id)">{{postInfo.userinfo.nickname}}</strong> {{postInfo.post.posts_main }}
      <a
        class="custom-tag"
        v-for="tag in postInfo.tags"
        v-bind:key="tag.tagid"
      >#{{tag.tagname}}</a>
      </div>
      <div v-if="postInfo.post.category == 0">
        <a v-show="postInfo.post.health_count != 0">
          <strong>{{postInfo.post.health_count}}명</strong>이 건강해요를 눌렀습니다
        </a>
        <a v-show="postInfo.post.health_count == 0">먼저 건강해요를 눌러보세요 </a>
        
        <!-- <a v-show="postInfo.comments.length > 0">
          <strong> {{postInfo.comments.length}}개</strong>의 댓글이 있습니다
        </a>
        <a v-show="postInfo.comments.length == 0"> 먼저 댓글을 달아보세요</a> -->
      </div>
    </div>
    <div class="feed-footer">
      <table>
        <tr>
          <td v-if="postInfo.post.category == 0">
            <span v-if="postInfo.healths.user_id"></span>
            <img v-show="postInfo.health" :src="isHealth" @click="clickHealth(postInfo)" />
            <img v-show="!postInfo.health" :src="isNotHealth" @click="clickHealth(postInfo)" />
            <!-- <span class="health-count">{{ postInfo.post.health_count }}</span> -->
          </td>
          <td>
            <img
              @click="changeSelectPost(postInfo,'comment')"
              src="../../assets/images/icon/icon_talk.png"
            />
            <a>{{postInfo.comments.length}}</a>
          </td>
          <td v-if="postInfo.post.category == 0">
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
          <!-- <td>
            <img
              @click="changeSelectPost(postInfo,'modify')"
              v-if="loginData.user_id == postInfo.post.user_id"
              src="../../assets/images/icon/icon_edit_unselect.png"
            />
          </td> -->
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import SERVER from "@/api/RestApi.js";
import axios from 'axios';
import cookies from "vue-cookies";
export default {
  name: "Post",
  computed: {
    ...mapState("userStore", ["loginData"]),
  },
  props: {
    postInfo: {
      default: void 0,
    },
  },
  data() {
    return {
      isHealth: require("../../assets/images/icon/icon_like_select.png"),
      isNotHealth: require("../../assets/images/icon/icon_like_unselect.png"),
      isScraped: false,
      healthData: {
        posts_id: null,
        user_id: null,
      },
      isDelete: false,
      selectedPost: {},
      isShow: false,
      isActive: false,
    };
  },

  methods: {
    ...mapActions('userStore', ["goProfile"]),
    ...mapActions('postStore', [
      "fetchComments",
      "health",
      "scrap",
      "deleteScrap",
      "deletePost",
      "setPost"
    ]),
    changeSelectPost(post, sort) {
      this.selectedPost = post;
      let info = {
        postInfo: this.selectedPost,
        isHidden: true,
        isModifyHidden: false,
        isPostHidden: false,
      };

      if (sort === "modify") {
        console.log("post",post)
         this.setPost(post)
         this.$router.push({name: 'Upload'});
      } else if (sort === "comment") {
        info.isPostHidden = true;
        this.fetchComments(post.posts_id);
         this.$emit("send-modify", info);
      }
     
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
      //this.healthData.user_id = this.loginData.user_id; // user_id
      this.health(this.healthData);
    },
    clickScrap(post) {
      let params = {
        posts_id: post.posts_id,
        user_id: this.loginData.user_id,
      };

      if (post.scrap == true) {
        post.scrap = false;
        this.deleteScrap(params);
      } else {
        post.scrap = true;
        this.scrap(params);
      }
      console.log("click_post", post);
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
    },
    showModal(postInfo){
      this.isDelete = false;
      console.log(postInfo.userinfo.user_id)
      if(postInfo.userinfo.user_id == this.loginData.user_id) this.deletePost({postInfo:postInfo,user_id: this.loginData.user_id});
      else {
        // var reason = prompt("신고 내용은요?");
        // let params = {
        //   posts_id: postInfo.post.posts_id,
        //   reason: reason,
        //   user_id: this.loginData.user_id
        // }
        this.makeReport();
        console.log(SERVER, axios, cookies)
        // axios.post(SERVER.URL + '/police', params, { headers: { accessToken : cookies.get('access-token')}})
        // .then((res) => {
        //   console.log(res);
        //   // 신고 성공
        //   alert('게시물을 신고했습니다')
        // })
        // .catch((err) => {
        //   console.log(err);
        //   // 신고 실패
        //   alert('게시물 신고에 실패했습니다')
        // })
      }
    },
    makeReport() {
      this.$emit("make-report", this.postInfo);
    }
  
  },
  created() {
    console.log(this.postInfo);
  },
};
</script>

<style>
.article-header {
  height: 45px;
}

.article-header tr td:nth-child(2) a:nth-child(2) {
  font-size: 10px;
  margin-left: 10px;
  color: slategray;
}
.article-header tr td:nth-child(3) {
  width : 20%;
}
.article-header tr td:nth-child(4) {
  width : 10px;
}

.article-header tr td:first-child {
  width: 45px;
  height: 45px;
}

.article-header .profile-image {
  width : 30px;
  height: 30px;
  object-fit: cover;
}

.tag-header span{
  background-color: rgb(0, 171, 132);
  border: 10px;
  font-size: 15px;
  font-weight: 600;
  border-radius: 20px;
  margin: 5px 10px 5px 0px ;
  color: white;
  display: inline-block;
  padding :5px  10px 5px 10px ;
  text-align:center;
}

.dropdown {
  position: relative;
  display: inline-block;
  padding-top: 8px;
}

.dropmenu {
  width: 18px;
}

.black-layer {
  position: fixed;
  /* background-color: black;
  opacity: 0.1; */
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  z-index: 54;
}

.dropdown-content {
  position: absolute;
  right: 5px;
  background-color: rgb(247, 247, 247);
  outline: none;
  min-width: 100px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 55;
  display: block;

}

.dropdown-content a{
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  
}

.dropdown-content a:hover{
  background-color:rgb(191, 181, 181);
}


.custom-tag {
  font-size: 13px;
  color: rgb(0, 171, 132);
}


.feed-footer table tr td:nth-child(2) a {
  font-size: 10px;
  font-weight: 600;
}
</style>