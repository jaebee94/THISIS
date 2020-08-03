<template>
  <div class="feed_newsfeed">
    <div class="wrapB">
      <h1>페이지를
          찾을 수
          없습니다
      </h1>
      <img src="../../assets/images/crying.png"/>
      <h3>잘못된 접근입니다</h3>

      <button
        class="temp-btn"
        @click="MoveMain"
      >메인화면 가기</button>
    </div>
  </div>
</template>

<script>
export default {
    created() {
    this.component = this;
  },
  methods : {
      MoveMain(){
          //this.$router.push("/error");
          this.$router.push("/feed/main");
      }
  }
};
</script>

<style scoped>
.wrapB{
    padding-top: 30%;
    vertical-align: middle;
    text-align: center;
    align-items: center;
    align-content: center;
}
.temp-btn {
  width: 100%;
  display: block;
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
 width: 100%;
  display: block;
  padding : 5px;
  font-weight: 600;
  text-align: center;
  -webkit-transform: rotate(0.1deg);
          transform: rotate(0.1deg);
  color: #0a252b;
  border: 1px solid lightgrey;
  background-color: lightgray;
}
</style>