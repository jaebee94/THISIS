<template>
  <div class="main wrap">
    <div class="logo wrap">
      <!-- <img src="../../assets/images/icon/logo_green.png" /> -->
      <span>ADMIN</span>
    </div>

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

      <div v-show="currentTab == 0">
        <div>
          <PostList></PostList>
        </div>
      </div>

      <div v-show="currentTab == 1">
        <div>
          <UserList></UserList>
        </div>
      </div>

      <div v-show="currentTab == 2">
        <div>
          <DoctorList></DoctorList>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

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
        require("../../assets/images/icon/icon_qna.png"),
        require("../../assets/images/icon/icon_news.png"),
      ],
      currentTab: 0,
    };
  },
  watch: {
    currentTab: function () {
      if (this.currentTab == 0) {
        this.fetchReportedPosts()
      }
      else if (this.currentTab == 1) {
        this.fetchReportedUsers()
      } else {
        this.fetchDoctors()
      }
    }
  },

  computed: {
    ...mapState("userStore", ["loginData", "profileData"]),
  },

  created() {
  },

  methods: {
    ...mapActions("userStore", ["goProfile"]),
    ...mapActions("postStore", ["getUserScraps"]),
    ...mapActions("adminStore", [
      "fetchReportedPosts",
      "fetchReportedUsers",
      "fetchDoctors"
    ])
  },
};
</script>

<style scoped>
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


</style>