<template>
  <div class="qna wrap">
    <table>
      <tr>
        <td>이름: </td>
        <td>{{ user.username }}</td>
      </tr>
      <tr>
        <td>닉네임: </td>
        <td>{{ user.nickname }}</td>
      </tr>
      <tr>
        <td>E-mail: </td>
        <td>{{ user.email }}</td>
      </tr>
    </table>
    <div v-if="user.disabled == 1">
      <strong>정지된 계정입니다.</strong>
    </div>
    <form class="user-manage-panel" v-on:submit.prevent="onClickSubmit">
      <select name="user-option" id="user-option" v-model="userOption">
        <option value="" hidden></option>
        <option value="delete">삭제</option>
        <option value="disable">정지</option>
        <option value="able">정지 해제</option>
      </select>
      <button type="submit">확인</button>
    </form>
    <hr>
  </div>
</template>

<script>
import { mapActions } from "vuex";

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
    return {
      userOption: null,
    };
  },
  methods: {
    ...mapActions("adminStore", ["manageUser"]),
    onClickSubmit() {
      var data = {
        check: this.userOption,
        user_id: this.user.user_id
      }
      this.manageUser(data)
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