<template>
  <div class="feed">
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
            <a
              class="name"
              @click="goProfile(postInfo.post.user_id)"
            >{{ postInfo.post.nickname }}</a>
          </td>
          <td>
            <a class="time">{{postInfo.post.post_date }}</a>
          </td>
        </tr>
      </table>
    </div>
    <div class="feed-main">{{postInfo.post.posts_main }}</div>
    <div class="feed-footer">
      <table>
        <tr>
          <td>
            <span v-if="postInfo.healths.user_id"></span>
            <img v-show="postInfo.health" :src="isHealth" @click="clickHealth(postInfo)" />
            <img v-show="!postInfo.health" :src="isNotHealth" @click="clickHealth(postInfo)" />
            <span class="health-count">{{ postInfo.post.health_count }}</span>
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
  name: "post",
  computed: {
    ...mapState(["loginData"]),
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
    ...mapActions(["goProfile","health"]),
    ...mapActions(["scrap",  "deleteScrap"]),
    ...mapActions(["fetchComments"]),
    changeSelectPost(post, sort){
     this.selectedPost = post;
     let info = {
      postInfo: this.selectedPost,
      isHidden : true,
      isModifyHidden : false,
      isPostHidden: false,
      };

      if(sort === 'modify') info.isModifyHidden = true;
      else if(sort ==='comment') {
          info.isPostHidden = true; 
          this.fetchComments(post.post_id);
      }
     console.log(info);
    this.$emit('send-modify',info)
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
      console.log(post);
      console.log("id", this.healthData.user_id);
      console.log(post.post.posts_id);
      //this.healthData.user_id = this.loginData.user_id; // user_id
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
      console.log("click_post", post);
    },

  },
};
</script>

<style>
</style>