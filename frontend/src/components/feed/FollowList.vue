<template>
  <div class="tabs">
    <div class="tab-container">
      <div
        class="tab"
        v-for="(tab, index) in tabs"
        v-bind:key="tab"
        v-bind:class="{active: currentTab === index}"
        @click="currentTab = index"
      >
        <a>{{tab}}</a>
        <span v-if="currentTab==0">{{ profileData.profileInfo.followernum }}</span>
        <span v-else>{{ profileData.profileInfo.followeenum }}}</span>
      </div>
    </div>

    <div>
      <user-list 
      v-bind:users="this.users"
      v-bind:sort="this.currentTab"
      >
      </user-list>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
export default {
  name: "FollowList",
  data() {
    return {
      follower: 0,
      followee: 0,
      currentTab: 0,
      tabs: ["팔로워", "팔로잉"],
      users: [],
    };
  },
  watch: {
    currentTab: function () {
      if (this.currentTab == 0) this.users = this.follower;
      else this.users = this.following;
    },
  },
  computed: {
    ...mapState("userStore", ["loginData", "profileData"]),
    ...mapState("followStore", ["follower", "following"]),
  },
  methods: {
    ...mapActions("followStore", [
      "deleteFollow",
      "getFollower",
      "getFollowing",
    ]),
  },
  created() {
    console.log('followlist')
    //이 계정의 팔로워와 팔로잉 리스트 setting함
    this.getFollower(this.loginData.user_id);
    this.getFollowing(this.loginData.user_id);
    this.currentTab=this.$route.params.tab
  },
};
</script>

<style>
</style>