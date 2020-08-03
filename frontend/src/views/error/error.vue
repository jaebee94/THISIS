<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>에러가<br>
          발생했네요<br>
          왜<br>이럴까요...?
      </h1>
    </div>

    <button
        class="temp-btn"
        @click="MoveBack"
      >뒤로가기</button>
  </div>
</template>

<script>
export default {
    created() {
    this.component = this;
  },
  methods : {
      MoveBack(){
          //this.$router.push("/error");
          this.$router.go(-1);
      }
  }
};
</script>

<style scoped>
.temp-btn {
  width: 50%;
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
  width: 50%;
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
