<template>
  <div class="upload wrap">
    <div class="title-panel">
      <input v-model="posts_title" id="posts_title" placeholder="제목을 입력하세요" />
    </div>
    <div class="content-panel">
      <textarea v-model="posts_main" id="posts_main" placeholder="내용을 입력하세요" />
    </div>

    <div class="button-panel">
      <button @click="createPost(postData)" :disabled="!isFill" :class="{disabled : !isFill}">올리기</button>
    </div>
  </div>
</template>


<script>
import { mapActions } from "vuex";

export default {
  name: "Upload",
  data() {
    return {
      posts_title: "",
      posts_main: "",
      postData: {
        posts_title: null,
        posts_main: null,
      },
      isFill: false,
    };
  },
  watch: {
    posts_title: function () {
      this.checkForm();
    },
    posts_main: function () {
      this.checkForm();
    },
  },
  methods: {
    checkForm() {
      if (
        this.posts_title.length > 0 &&
        this.posts_main.length > 0
      ) {
        this.isFill = true;
        this.postData.posts_title = this.posts_title;
        this.postData.posts_main = this.posts_main;
      } else {
        this.isFill = false;
      }
    },
    ...mapActions(["createPost"]),
  },
};
</script>

<style scoped>
.title-panel {
  width: 90%;
  margin-top: 20px;
  margin-left: 5%;
}

.content-panel {
  width: 80%;
  height: 400px;
  margin-top: 20px;
  margin-left: 5%;
}

.title-panel input {
  width: 93%;
  height: 30px;
  padding-top: 5px;
  padding-bottom: 5px;
  padding-left: 7%;
  border: none;
  background-color: rgb(200, 200, 200);
  font-size: 20px;
  border-radius: 5px;
  outline: none;
  transition-duration: 300ms;
}

.title-panel input:focus {
  color: white;
  font-weight: 600;
  background-color: rgb(0, 171, 132);
  transition-duration: 300ms;
}

.content-panel textarea {
  width: 100%;
  height: 80%;
  background-color:rgb(247, 247, 247);
  border-radius: 10px;
  padding-top: 10px;
  padding-bottom: 10px;
  padding-left: 7%;
  padding-right: 7%;
  font-size: 15px;
  font-weight: 500;
  border: none;
  outline: none;
}

.content-panel textarea:focus {
  background-color:rgba(0, 171, 132);
  color: white;
}

.button-panel {
  width: 90%;
  height: 40px;
  margin-left: 5%;
}

.button-panel button {
  width: 100%;
  height: 100%;
  background-color: rgb(247, 247, 247);
  color: black;
  font-size: 18px;
  font-weight: 600;
  border: none;
  border-radius: 5px;
  outline: none;
  transition-duration: 0.5s;
}

.button-panel button:enabled {
  background-color: rgba(0, 171, 132, 1);
  color: rgb(247, 247, 247);
}
</style>
