<template>
  <div class="qna wrap">
    <table>
      <tr>
        <td>이름: </td>
        <td>{{ post.userinfo.username }}</td>
      </tr>
      <tr>
        <td>닉네임: </td>
        <td>{{ post.userinfo.nickname }}</td>
      </tr>
      <tr>
        <td>E-mail: </td>
        <td>{{ post.userinfo.email }}</td>
      </tr>
    </table>
    <table>
      <tr>
        <td>게시글 제목: </td>
        <td>{{ post.postpolice.posts_title }}</td>
      </tr>
      <tr>
        <td>작성일: </td>
        <td>{{ post.postpolice.post_date }}</td>
      </tr>
      <tr>
        <td>게시글 내용: </td>
        <td>{{ post.postpolice.posts_main }}</td>
      </tr>
      <tr>
        <td>신고받은 횟수: </td>
        <td>{{ post.postpolice.count }}</td>
      </tr>
    </table>
    <div v-if="post.postpolice.hidden == 1">
      <strong>블라인드 처리된 게시글입니다.</strong>
    </div>
    <form class="post-manage-panel" v-on:submit.prevent="onClickSubmit">
      <select name="post-option" id="post-option" v-model="postOption">
        <option value="" hidden></option>
        <option value="delete">삭제</option>
        <option value="hide">블라인드</option>
        <option value="show">블라인드 해제</option>
      </select>
      <button type="submit">확인</button>
    </form>
    <hr>
  </div>
</template>

<script>
import { mapActions } from "vuex";
// import PostListItem from './PostListItem'

export default {
  name: "PostList",
  components: {
    // PostListItem,
  },
  props: {
    post: {
      default: void 0,
    },
  },
  computed: {},
  data() {
    return {
      postOption: null,
    };
  },
  methods: {
    ...mapActions("adminStore", ["managePost"]),
    onClickSubmit() {
      var data = {
        check: this.postOption,
        posts_id: this.post.postpolice.posts_id
      }
      this.managePost(data)
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