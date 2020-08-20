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
        <span v-else>{{profileData.profileInfo.followeenum}}</span>
      </div>
    </div>

    <div>
    <div class="search-panel">
      <div>
        <input type="text" v-model="keyword" placeholder="검색어를 입력하세요" v-on:keyup.enter="searchUser(keyword)"/>
        <img id="search" @click="searchUser(keyword)" src="../../assets/images/icon/icon_search_unselect.png">
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
    searchUser(keyword){
      if(keyword==''){
         this.getFollower(this.loginData.user_id);
    this.getFollowing(this.loginData.user_id);
      }else{
        var newUsers=[]
        this.users.forEach(user => {
          if(user.username.includes(keyword)||user.nickname.includes(keyword)){
            newUsers.push(user);
          }
        });
        this.users=newUsers;
      }
    }
  },
  created() {
    //이 계정의 팔로워와 팔로잉 리스트 setting함
     this.currentTab=this.$route.params.tab
    this.getFollower(this.loginData.user_id);
    this.getFollowing(this.loginData.user_id);
     if (this.currentTab == 0) this.users = this.follower;
  },
};
</script>

<style>

.tab {
  width: 50%;
  height: 40px;
  vertical-align: middle;
  border-radius: 0;
  background-color: rgb(247, 247, 247);
  border-bottom: 3px rgb(247, 247, 247) solid;
}

.tab.active {
  background-color: rgb(247, 247, 247);
  border-bottom: 3px rgb(0, 171, 132) solid;
}
.tab a {
  font-size: 18px;
  font-weight: 600;
  line-height: 2;
  height: 20px;
}
.tab span {
  margin: 2px;
  font-size: 15px;
  font-weight: 500;
  line-height: 2;
  height: 20px;
  color:rgb(0, 171, 132);
}

.search-panel {
  width: 100%;
  height: 35px;
  margin-bottom: 10px;
}
.search-panel #search {
  height: 20px;
  position: absolute;
  margin-top: 10px;
  margin-left: -30px;
  /* left: -30px; */
  /* top: 3px;
  left: -40px;
  top: 3px; */
}
.search-panel input {
  /* background-image: url("../../assets/images/icon/icon_search_unselect.png");
  background-size: 20px;
  background-repeat: no-repeat;
  background-position: 95% center; */

  background-color: rgb(247, 247, 247);
  padding-left: 3%;
  padding-right: 3%;
  margin-top: 5px;
  width: 84%;
  font-size: 15px;
  font-weight: 600;
  height: 30px;
  border: none;
  outline: none;
  border-radius: 3px;
}
.search-panel input:focus {
  background-color: rgb(0, 171, 132);
  color: white;
}
</style>