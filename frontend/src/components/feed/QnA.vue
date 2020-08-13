<template>
  <div class="qna wrap">
    <div class="qna-header">
        <table style="width: 100%;">
            <tr >
                <td>
                    <div class="qna-title">
                        <a>{{qnaInfo.post.posts_title}}</a>
                    </div>
                </td>
                <td>
                    <img v-if="loginData.user_id == qnaInfo.userinfo.user_id" @click="changeSelectQnA(qnaInfo, 'modify')" src="../../assets/images/icon/icon_edit_unselect.png">
                </td>
            </tr>
        </table>
        <div  class="qna-tag" @click="changeSelectQnA(qnaInfo, 'comment')">
            <a v-if="qnaInfo.diseasename != ''">#{{qnaInfo.diseasename}}</a>
        </div>
    </div>
    <div class="qna-footer" @click="changeSelectQnA(qnaInfo, 'comment')">
        <span>
            <strong class="qna-writer">{{qnaInfo.userinfo.nickname}}</strong>
            <a class="qna-time">{{timeForToday(qnaInfo.post.post_date) }}</a>
            <img  src="../../assets/images/icon/icon_talk.png">
            <a class="qna-reply">{{qnaInfo.comments.length}}</a>
        </span>
    </div>
  </div>
</template>

<script>
import { mapActions,mapState } from "vuex";
export default {
    name: 'qna',
    computed: {
        ...mapState('userStore', ["loginData"]),
    },
    data() {
        return {
            selectedQnA: {},
        }
    },
    methods : {
        ...mapActions('profileStore', [
        'goProfile',
        ]),
        ...mapActions('postStore', [
        "fetchComments",
        'health',
        "scrap",
        "deleteScrap"
        ]),
        changeSelectQnA(qna, sort) {
            this.selectedQnA = qna;
            console.log("QNA에서 찍는 로그", this.selectedQnA);
            let info = {
                qnaInfo: this.selectedQnA,
                isHidden: true,
                isModifyHidden : false,
                isQnAHidden: false,
            }
            console.log('qna', qna);
            if (sort == 'comment') {
                info.isQnAHidden = true;
                this.fetchComments(qna.posts_id);
                console.log('comment')
            } else if (sort == 'modify') {
                info.isModifyHidden = true;
                console.log('modify')
            }
            this.$emit('send-modify-qna', info);
        },
        timeForToday(time) {
            const today = new Date();
            var timeValue = new Date(time);
            timeValue.setHours(timeValue.getHours() + 9);
            const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
            if (betweenTime < 1) return '방금전';
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
        }
    },
    created() {
        console.log(this.selectedQnA);
    },
    props: {
        qnaInfo: {
            default: void 0,
        },
    }
}
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

    .qna-header table {
        width: 100%;
    }

    .qna-header table tr td:nth-child(1) {
        width: 90%;
    }

    .qna-header table tr td:nth-child(2) {
        width: 10%;
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

    .qna-footer strong, a {
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
</style>