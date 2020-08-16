<template>
    <div class="comment-module-wrap">
        <div v-if="!isModify">
            <div class="comment-module-header">
                <a class="name" @click="goProfile(comment.userinfo.user_id)"><strong>{{comment.userinfo.nickname}}</strong></a>
                <a class="time">{{timeForToday(comment.comment.comment_date)}}</a>
            </div>
            <div class="comment-module-content">
                {{comment.comment.comment_main}}
            </div>
            <span class="comment-module-footer">
                <img @click="isModify = !isModify" class="comment-module-health" v-if="comment.userinfo.user_id == loginData.user_id" :src="modifyImage">
                <!-- <img class="comment-module-health" v-if="true" :src="isHealth" @click="clickHealth()">
                <img class="comment-module-health" v-if="true == false" :src="isNotHealth" @click="clickHealth()">
                <span class="comment-health-count">12</span> -->
            </span>
            <span class="comment-module-footer">
                <img @click="deleteCheckComment(comment.comment)" class="comment-module-health" v-if="comment.userinfo.user_id == loginData.user_id" :src="deleteImage"/>
            </span>
        </div>
        <div v-if="isModify">
            <div class="comment-module-header">
                <a class="name">{{comment.userinfo.nickname}}</a>
                <a class="time">{{timeForToday(comment.comment.comment_date)}}</a>
            </div>
            <div class="comment-module-modify">
                <input v-model="comment.comment.comment_main" placeholder="수정할 댓글 내용을 적어주세요">
                <button @click="modifyComment(comment)">수정</button>
            </div>
        </div>
    </div>
</template>

<script>
import {mapState, mapActions} from "vuex";
export default {
    name: 'comment',
    computed: {
        ...mapState('userStore', ['loginData']),
    },
    data () {
        return {
            isModify: false,
            isHealth: require("../../assets/images/icon/icon_like_select.png"),
            isNotHealth: require("../../assets/images/icon/icon_like_unselect.png"),
            modifyImage: require("../../assets/images/icon/icon_edit_unselect.png"),
            deleteImage: require("../../assets/images/icon/icon_eraser.png")
        }
    },
    props: {
        comment: {
            default: void 0,
        },
    },
    methods: {
        ...mapActions('userStore', ["goProfile"]),
        ...mapActions('postStore', ['updateComment','deleteComment']),
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
        },
        modifyComment(comment) {
            console.log('현재의 코멘트', comment);
            if(comment.comment.comment_main.length == 0) {
                alert("수정 내용을 입력하세요!");
                return;
            }
            let commentData = {
                "comment_date": comment.comment.comment_date,
                "comment_id": comment.comment.comment_id,
                "comment_main": comment.comment.comment_main,
                "hide": comment.comment.hide,
                "posts_id": comment.comment.posts_id,
                "user_id": comment.comment.user_id
            }
            console.log('commentData', commentData);
            this.updateComment(commentData)
            this.isModify = !this.isModify;
        },
        deleteCheckComment(comment){
            //확인 부분
            this.$emit("check-delete","댓글을 정말 지우시겠습니까?")
            console.log(comment)
            //this.deleteComment(comment)
        }
    }
}
</script>

<style scoped>
    .comment-module-wrap {
        width: 99%;
        height: 60px;
        background-color: white;
        border-bottom: 1px slategray solid;
    }

    .comment-module-header {
        margin : 7px 0px 7px 0px;
        background-color : white;
        text-align: left;
        padding-left: 5%;
        height: 20px;
    }

    .comment-module-header .name {
        margin-right: 10px;
        font-size : 18px;
    }

    .comment-module-header .time {
        color: slategray;
        font-size: 10px;
    }

    .comment-module-modify {
        width: 100%;
        margin-top: 5px;
    }

    .comment-module-modify input {
        width: 62%;
        border: none;
        border-radius: 5px;
        padding: 1%;
        outline: none;
        background-color: rgb(247, 247, 247);
        padding-left: 3%;
        transition-duration: 300ms;
    }

    .comment-module-modify input:focus {
        background-color: rgb(220, 220, 220);
    }

    .comment-module-modify button {
        width: 20%;
        margin-left: 5%;
        border: none;
        background-color: rgb(0, 171, 132);
        color: white;
        font-weight: 600;
        border-radius: 5px;
        padding: 1%;
    }

    .comment-module-content {
        text-align: left;
        width: 80%;
        padding-left: 5%;
        padding-right: 5%;
        font-size: 15px;
        margin-bottom: 5px;
    }

    .comment-module-footer {
        margin : 8px;
        position: relative;
        top: -30px;
        right: -35%;
    }

     .comment-module-footer img{
       width : 25px;
       height: 25px;
    }
    
    .comment-module-health {
        height: 20px;
    }

    .comment-health-count {
        position: relative;
        left: -5px;
        top: -15px;
        background-color: red;
        color: white;
        border: none;
        font-size: 10px;
        border-radius: 70%;
        padding:1px 3px;
    }
</style>