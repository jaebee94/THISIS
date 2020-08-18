<template>
  <div>
    <div class="searchU" v-for="user in users" v-bind:key="user.user_id">
      <div class="search-itemU" @click="goProfile(user.user_id)">
        <div class="search-img">
          <img :src="user.userimage" style="height:50px" />
        </div>
        <div class="search-textU">
          <div class="search-nickname">
            <span v-for="char in user.nickname" :key="char">
              <strong v-if="keyword.includes(char)">{{ char }}</strong>
              <span v-if="!keyword.includes(char)">{{ char }}</span>
            </span>
            <span>-</span>
            <span v-for="char in user.username" :key="char">
              <strong v-if="keyword.includes(char)">{{ char }}</strong>
              <span v-if="!keyword.includes(char)">{{ char }}</span>
            </span>
          </div>
          <div class="search-Introduction" v-if="user.introduction != null">{{user.introduction}}</div>
          <div class="search-Introduction" v-if="user.introduction == null">
            <br />
          </div>
        </div>
        <div v-if="sort==1" @click="deleteFollow(user.user_id)">팔로잉</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
export default {
  name: "UserList",
  props: {
    users: { type: Array },
    sort: { type: Number },
    keyword:{type:String}
  },
  methods: {
    ...mapActions("followStore", ["deleteFollow"]),
  },
};
</script>

<style>
</style>