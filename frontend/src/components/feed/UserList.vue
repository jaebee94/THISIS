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
            <img class ="doctor-image" v-if ="user.role == 'doctor'" src='../../assets/images/icon/icon_doctor_mark.png'/> 
          </div>
          <div class="search-Introduction" v-if="user.introduction != null">{{user.introduction}}</div>
          <div class="search-Introduction" v-if="user.introduction == null">
            <br />
          </div>
         
        </div>
        <div class = "following-state">
         <button  v-if="sort==1" @click="deleteFollow(user.user_id)">팔로잉</button>
          <!-- <button  v-else-if="sort==2" @click="deleteFollow(user.user_id)">팔로잉 요청</button> -->
         </div>
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
    ...mapActions("userStore", ["goProfile"]),
  },
};
</script>

<style scoped>
.searchU {
  width: 90%;
  margin-top: 10px;
  border: none;
  border-radius: 5px;
  background-color: rgb(247, 247, 247);
clear: both;
  display: flex;
  align-items: center;
  justify-content: center;
  margin:10px auto 0 auto;
}

.searchU:hover {
  background-color: rgb(0, 171, 132);
  color:white;
}

.searchU:hover .search-Introduction {
  color: rgb(247, 247, 247);
}

.searchU:last-child {
  margin-bottom: 30px;
}

.search-textU {
  padding: 10px;
  text-align: left;
  width : 50%;
  float: left;
  height : inherit;
  word-break:break-all;
}
.search-itemU{
  width : 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.search-itemU .following-state{
  margin: 0 auto;
  text-align: center;
  width : 20%;
  height : 100%;
  float: right;
  
}


.following-state button {
  background-color: rgb(0, 171, 132);
  border : none;
  border-radius: 5px;
  width :80%;
  
  padding : 5px;
}
.morebtn{
  height: 30px;
  float: right;
}

.searchU .search-img {
  float: left;
  width: 60px;
  height: 100%;
  vertical-align: middle;
  margin-left: 10px;
  /* transform: translate(0,25%); */
}

.searchU .search-img img {

  width: 80%;
  height:80%;
  border-radius: 70%;
  object-fit: cover;
  vertical-align: middle;
  /* background-size: cover; */
}

.search-item {
  margin: 10px;
  clear: both;
  height: auto;
}
.search-text2 {
  padding: 10px;
  text-align: left;
  width : 70%;
  float: left;
  height : inherit;
  word-break:break-all;
}
.search-nickname {
  font-size: 18px;
}

.search-Introduction {
  font-size: 12px;
  color: slategray;
}

.doctor-image {
  margin-left: 2px;
  width: 15px;
  height: 15px;
}
</style>