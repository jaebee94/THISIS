<template>
  <div class="feed">
    <div class="feed-header">
      <table>
        <tr>
          <td>
            <img
              class="profile-image"
              @click="goProfile(postInfo.post.user_id)"
              :src="postInfo.userinfo.userimage"
            />
          </td>
          <td>
            <a
              class="name"
              @click="goProfile(postInfo.post.user_id)"
            >{{ postInfo.userinfo.nickname }}</a>
          </td>
          <td>
            <a class="time">{{ timeForToday(postInfo.post.post_date)  }}</a>
          </td>
        </tr>
      </table>
    </div>
    <div class="feed-main">
      <img v-show="postInfo.post.imgsrc != null" :src="postInfo.post.imgsrc">
      <strong @click="goProfile(postInfo.post.user_id)">{{postInfo.userinfo.nickname}}</strong> 
      {{postInfo.post.posts_main }}
      <div>
        <a v-show="postInfo.post.health_count != 0"><strong>{{postInfo.post.health_count}}명</strong>이 건강해요를 눌렀습니다</a>
        <a v-show="postInfo.post.health_count == 0">먼저 건강해요를 눌러보세요</a>
        </div>
    </div>
    <div class="feed-footer">
      <table>
        <tr>
          <td>
            <span v-if="postInfo.healths.user_id"></span>
            <img v-show="postInfo.health" :src="isHealth" @click="clickHealth(postInfo)" />
            <img v-show="!postInfo.health" :src="isNotHealth" @click="clickHealth(postInfo)" />
            <!-- <span class="health-count">{{ postInfo.post.health_count }}</span> -->
          </td>
          <td>
            <img @click="changeSelectPost(postInfo,'comment')" 
            src="../../assets/images/icon/icon_talk.png" />
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
             @click="changeSelectPost(postInfo,'modify')" 
              v-if="loginData.user_id == postInfo.post.user_id"
              src="../../assets/images/icon/icon_edit_unselect.png"
            />
          </td>
          
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import { mapActions,mapState } from "vuex";
export default {
  name: "Post",
  computed: {
    ...mapState('userStore', ["loginData"]),
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
      selectedPost:{},
    };
  },

  methods: {
    // ...mapActions('profileStore', [
    //   'goProfile',
    //   ]),
    ...mapActions('userStore', ["goProfile"]),
    ...mapActions('postStore', [
      "fetchComments",
      'health',
      "scrap",
      "deleteScrap"
      ]),
    changeSelectPost(post, sort){
     this.selectedPost = post;
     let info = {
      postInfo: this.selectedPost,
      isHidden : true,
      isModifyHidden : false,
      isPostHidden: false,
      };

      if(sort === 'modify') {
        info.isModifyHidden = true;
      }
      else if(sort ==='comment') {
          info.isPostHidden = true; 
          this.fetchComments(post.posts_id);
      }
      this.$emit('send-modify', info)
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
        posts_id : post.posts_id,
        user_id : this.loginData.user_id,
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
    }
  },
  created(){
  }
};
</script>

<style>
</style>