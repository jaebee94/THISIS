<template>
  <div class="qna wrap">
    <div class="qna-header">
      <div class="qna-title">
        <a>{{ user.email }}</a>
      </div>
      <div class="qna-tag">
        <a>{{ user.username }}</a>
      </div>
    </div>
    <div class="qna-footer">
      <span>
        <strong class="qna-writer">{{user.nickname}}</strong>

        <a href="">{{ user.count }}</a>
      </span>
    </div>
  </div>
</template>

<script>

export default {
  name: "UserList",
  components: {
    // PostListItem,
  },
  props: {
    user: {
      default: void 0,
    },
  },
  computed: {},
  data() {
    return {};
  },
  methods: {
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
    // console.log(this.selectedQnA);
  },
};
</script>

<style scoped>
.post.wrap {
  width: 100%;
  height: 80px;
  margin-bottom: 10px;
  background-color: white;
  /* border-top: 2px slategray solid; */
  border-bottom: 1px slategray solid;
}

.post-header {
  height: 60px;
}

.post-title {
  text-align: left;
}

.post-title a {
  font-size: 18px;
  font-weight: 600;
  margin-left: 5%;
}

.post-tag {
  text-align: left;
  color: slategray;
}

.post-tag a {
  margin-left: 5%;
}

.post-header img {
  height: 20px;
  position: relative;
  top: -45px;
  left: 45%;
}

.post-footer {
  font-size: 15px;
  text-align: left;
}

.post-footer .post-time {
  color: slategray;
}

.post-footer strong,
a {
  margin-left: 5%;
}

.post-reply {
  margin-left: 3px;
}

.post-footer span img {
  margin-left: 5%;
  height: 10px;
  z-index: 80;
}
</style>