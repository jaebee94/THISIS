<template>
  <div class="post wrap">
    <PostListItem
      v-for="post in posts"
      :key="post.posts_id"
    ></PostListItem>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import PostListItem from './PostListItem'

export default {
  name: "PostList",
  components: {
    PostListItem,
  },
  props: {
    posts: Array
  },
  computed: {
    ...mapState("userStore", ["loginData"]),
  },
  data() {
    return {
      selectedQnA: {},
    };
  },
  methods: {
    ...mapActions("profileStore", ["goProfile"]),
    ...mapActions("postStore", [
      "fetchComments",
      "health",
      "scrap",
      "deleteScrap",
    ]),
    changeSelectQnA(qna, sort) {
      this.selectedQnA = qna;
      console.log("QNA에서 찍는 로그", this.selectedQnA);
      let info = {
        qnaInfo: this.selectedQnA,
        isHidden: true,
        isModifyHidden: false,
        isQnAHidden: false,
      };
      console.log("qna", qna);
      if (sort == "comment") {
        info.isQnAHidden = true;
        this.fetchComments(qna.posts_id);
        console.log("comment");
      } else if (sort == "modify") {
        info.isModifyHidden = true;
        console.log("modify");
      }
      this.$emit("send-modify-qna", info);
    },
    timeForToday(time) {
      const today = new Date();
      var timeValue = new Date(time);
      timeValue.setHours(timeValue.getHours() + 9);
      const betweenTime = Math.floor(
        (today.getTime() - timeValue.getTime()) / 1000 / 60
      );
      if (betweenTime < 1) return "방금전";
      if (betweenTime < 60) {
        return `${betweenTime}분전`;
      }
      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간전`;
      }
      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
        return `${betweenTimeDay}일전`;
      }
      return `${Math.floor(betweenTimeDay / 365)}년전`;
    },
  },
  created() {
    console.log(this.selectedQnA);
  },
};
</script>

<style scoped>
.qna.wrap {
  width: 100%;
  height: 80px;
  margin-bottom: 10px;
  background-color: white;
  /* border-top: 2px slategray solid; */
  border-bottom: 1px slategray solid;
}

.qna-header {
  height: 60px;
}

.qna-title {
  text-align: left;
}

.qna-title a {
  font-size: 18px;
  font-weight: 600;
  margin-left: 5%;
}

.qna-tag {
  text-align: left;
  color: slategray;
}

.qna-tag a {
  margin-left: 5%;
}

.qna-header img {
  height: 20px;
  position: relative;
  top: -45px;
  left: 45%;
}

.qna-footer {
  font-size: 15px;
  text-align: left;
}

.qna-footer .qna-time {
  color: slategray;
}

.qna-footer strong,
a {
  margin-left: 5%;
}

.qna-reply {
  margin-left: 3px;
}

.qna-footer span img {
  margin-left: 5%;
  height: 10px;
  z-index: 80;
}
</style>