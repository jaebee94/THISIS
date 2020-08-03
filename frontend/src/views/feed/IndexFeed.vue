<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>뉴스피드</h1>

       <button
        class="temp-btn"
        @click="changePassword"
      
      >비밀번호 변경</button>

      <button
        class="temp-btn"
        @click="onLogin"
      >로그아웃</button>

      <button
        class="temp-btn"
        @click="onError"
      >에러발생</button>

      <FeedItem />
      <FeedItem />
      <FeedItem />
      <FeedItem />
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItem from "../../components/feed/FeedItem.vue";

export default {
  props: ["keyword"],

  components: { FeedItem },
  methods : {
    changePassword(){
      this.$router.push("/user/change")
    },
    onError(){
      this.$router.push("/error");
    }
  }
};
</script>

<style scoped>
.temp-btn {
  width: 20%;
  float: right;
  display: block;
  margin: 10px;
  padding : 5px;
  font-weight: 600;
  text-align: center;
  -webkit-transform: rotate(0.1deg);
          transform: rotate(0.1deg);
  color: #3893A8;
  border: 1px solid lightgrey;
  background-color: lightgray;
  
}

.temp-btn:hover{
  width: 20%;
  float: right;
  display: block;
  margin: 10px;
  padding : 5px;
  font-weight: 600;
  text-align: center;
  -webkit-transform: rotate(0.1deg);
          transform: rotate(0.1deg);
  color: #0a252b;
  border: 1px solid lightgrey;
  background-color: lightgray;
  
}
</style