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
        <span v-if="tab=='팔로워'">{{ profileData.profileInfo.followernum }}</span>
        <span v-else>{{ profileData.profileInfo.followeenum }}</span>
      </div>
    </div>

    <div>
    <div class="search-panel">
      <div>
        <input type="text" v-model="keyword" placeholder="검색어를 입력하세요" v-on:keyup.enter="getSearchList(keyword)"/>
        <img id="search" @click="getSearchList(keyword)" src="../../assets/images/icon/icon_search_unselect.png">
      </div>
    </div>
      <user-list 
      v-bind:users="this.users"
      v-bind:sort="this.currentTab"
      v-bind:keyword="this.keyword"
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
      currentTab: 0,
      tabs: ["팔로워", "팔로잉"],
      users: [],
      keyword:"",
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