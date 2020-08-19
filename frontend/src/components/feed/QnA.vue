<template>
  <div class="qna wrap">
    <div v-if="isDelete" @click="isDelete = !isDelete" class="black-layer"></div>
    <div class="qna-header">
        <img class="qna-preview" v-if="qnaInfo.post.imgsrc" :src="qnaInfo.post.imgsrc">
        <img class="qna-preview" v-else src="../../assets/images/icon/icon_qna.png">
        <table>
            <tr >
                <td>
                    <div class="qna-title">
                        <a>{{qnaInfo.post.posts_title}}</a>
                    </div>
                </td>
                <td>
                  <div class="dropdown" >
                    <img class="dropmenu" @click="isDelete = !isDelete"  src="../../assets/images/icon/icon_3dots.png" />
                  </div>
                  <div v-show="isDelete" class="dropdown-content">
                    <a href="#" v-if="loginData.user_id == qnaInfo.post.user_id" @click="changeSelectQnA(qnaInfo,'modify')">수정</a>
                    <a href="#" v-if="loginData.user_id == qnaInfo.post.user_id" @click="showModal(qnaInfo)">삭제</a>
                    <a href="#" v-if="loginData.user_id != qnaInfo.post.user_id"  @click="showModal(qnaInfo)">신고</a>
                  </div>
                    <!-- <img v-if="loginData.user_id == qnaInfo.userinfo.user_id" @click="changeSelectQnA(qnaInfo, 'modify')" src="../../assets/images/icon/icon_edit_unselect.png"> -->
                </td>
            </tr>
        </table>
        <div  class="qna-tag" @click="changeSelectQnA(qnaInfo, 'comment')">
            <a v-if="qnaInfo.diseasename != ''">#{{qnaInfo.diseasename}}</a>
        </div>
    </div>
    <div class="qna-footer" @click="changeSelectQnA(qnaInfo, 'comment')">
      <!-- <img
        class="profile-image"
        @click="goProfile(qnaInfo.userinfo.user_id)"
        :src="qnaInfo.userinfo.userimage"
      /> -->
      <span>
        <strong class="qna-writer">{{qnaInfo.userinfo.nickname}}</strong>
        <a class="qna-time">{{timeForToday(qnaInfo.post.post_date) }}</a>
        <img src="../../assets/images/icon/icon_talk.png" />
        <a class="qna-reply">{{qnaInfo.comments.length}}</a>
      </span>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
export default {
  name: "qna",
  computed: {
    ...mapState("userStore", ["loginData"]),
  },
  data() {
    return {
      selectedQnA: {},
      isDelete: false,
    };
  },
  methods: {
    ...mapActions("profileStore", ["goProfile"]),
    ...mapActions("postStore", [
      "fetchComments",
      "health",
      "scrap",
      "deleteScrap",
      "deletePost",
      "setPost",
    ]),
    changeSelectQnA(qna, sort) {
      this.selectedQnA = qna;
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
        this.$emit("send-modify-qna", info);
        console.log("comment");
      } else if (sort == "modify") {
        //info.isModifyHidden = true;
        this.setPost(qna);
        this.$router.push({ name: "Upload" });
        console.log("modify");
      }
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
    showModal(qnaInfo){
      this.isDelete = false;
      console.log(qnaInfo.userinfo.user_id)
      if(qnaInfo.userinfo.user_id == this.loginData.user_id) this.deletePost({qnaInfo:qnaInfo,user_id: this.loginData.user_id});
      else {
        // var reason = prompt("신고 내용은요?");
        // let params = {
        //   posts_id: postInfo.post.posts_id,
        //   reason: reason,
        //   user_id: this.loginData.user_id
        // }
        this.makeReport();
        // axios.post(SERVER.URL + '/police', params, { headers: { accessToken : cookies.get('access-token')}})
        // .then((res) => {
        //   console.log(res);
        //   // 신고 성공
        //   alert('게시물을 신고했습니다')
        // })
        // .catch((err) => {
        //   console.log(err);
        //   // 신고 실패
        //   alert('게시물 신고에 실패했습니다')
        // })
      }
    },
    makeReport() {
      this.$emit("make-report", this.qnaInfo);
    }
  },
  created() {
    console.log(this.selectedQnA);
  },
  props: {
    qnaInfo: {
      default: void 0,
    },
  },
};
</script>

<style scoped>
.qna.wrap {
  width: 100%;
  height: 80px;
  padding-bottom: 10px;
  background-color: white;
  /* border-top: 2px slategray solid; */
  border-bottom: 1px slategray solid;
}

.qna-header {
  height: 60px;
  width: 100%;
}

.qna-preview {
  width: 50px;
  height: 50px;
  position: absolute;
  left: 5%;
  margin-top: 5px;
}

.qna-header table {
  width: 80%;
  margin-top: 5px;
  margin-left: calc(5% + 50px);
}

.qna-header table tr td:nth-child(1) {
  width: 90%;
}

.qna-header table tr td:nth-child(2) {
  width: 10%;
}

.qna-title {
  max-height: 20px;
  overflow: hidden;
  word-wrap: break-word;
  text-overflow: ellipsis;
  text-align: left;
}

.qna-title a {
  font-size: 15px;
  font-weight: 600;
  margin-left: 5%;
}

.qna-tag {
  text-align: left;
  color: slategray;
  margin-left: calc(5% + 50px);
}

.qna-tag a {
  font-size: 12px;
  margin-left: 5%;
}

.qna-header table img {
  /* height: 20px;
        position: relative;
        top: -45px;
        left: 45%; */
  width: 15px;
  height: 15px;
  right: 0;
}

.qna-footer {
  font-size: 15px;
  text-align: left;
}

.qna-footer .qna-time {
  color: slategray;
  font-size: 10px;
}

.qna-footer strong {
  font-size: 13px;
}

.qna-footer strong,
a {
  margin-left: 5%;
}

.qna-reply {
  font-size: 12px;
  font-weight: 600;
  margin-left: 3px;
}
.qna-footer span img {
  margin-left: 5%;
  height: 10px;
  z-index: 80;
}

.profile-image {
    z-index: 120;
  width: 30px;
  height: 30px;
  object-fit: cover;
}



/* ======= dropdown css ======= */
.dropdown {
  position: relative;
  display: inline-block;
  padding-top: 8px;
}

.dropmenu {
  width: 18px;
}

.black-layer {
  position: fixed;
  /* background-color: black;
  opacity: 0.1; */
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  z-index: 54;
}

.dropdown-content {
  position: absolute;
  right: 5px;
  background-color: rgb(247, 247, 247);
  outline: none;
  min-width: 100px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 55;
  display: block;

}

.dropdown-content a{
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  
}

.dropdown-content a:hover{
  background-color:rgb(191, 181, 181);
}
/* ======= dropdown css ======= */
</style>