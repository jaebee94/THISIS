<template>
    <div class="comment-module-wrap">
        <div v-if="isDelete" @click="isDelete = !isDelete" class="black-layer"></div>
        <div v-if="!isModify">
            <div class="comment-module-header">
                <a class="name" @click="goProfile(comment.userinfo.user_id)">
                    <strong>{{comment.userinfo.nickname}}</strong>
                    <img class = "doctor-image" v-if ="comment.userinfo.role == 'doctor'" src='../../assets/images/icon/icon_doctor_mark.png'/> 
                    </a>
                <a class="time">{{timeForToday(comment.comment.comment_date)}}</a>
                <img class="dropmenu" v-if="loginData.user_id == comment.userinfo.user_id" @click="isDelete = !isDelete"  src="../../assets/images/icon/icon_3dots.png" />
                <div v-show="isDelete" class="dropdown-content">
                    <a href="#" v-if="loginData.user_id == comment.userinfo.user_id" @click="modifyComment(comment)">수정</a>
                    <a href="#" v-if="loginData.user_id == comment.userinfo.user_id" @click="deleteCheckComment(comment.comment)">삭제</a>
                </div>
            </div>
            <div class="comment-module-content">
                {{comment.comment.comment_main}}
            </div>
        </div>
        <div v-if="isModify">
            <div class="comment-module-header">
                <a class="name">{{comment.userinfo.nickname}}</a>
                <a class="time">{{timeForToday(comment.comment.comment_date)}}</a>
            </div>
            <div class="comment-module-modify">
                <input v-model="comment.comment.comment_main" placeholder="수정할 댓글 내용을 적어주세요" v-on:keyup.enter="modifyComment(comment)">
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
            deleteImage: require("../../assets/images/icon/icon_eraser.png"),
            isDelete: false,
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
            this.isDelete = false;
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
            this.updateComment(commentData)
            this.isModify = !this.isModify;
        },
        deleteCheckComment(comment){
            //확인 부분
            
            var result = confirm('댓글을 지우시겠습니까?');
            if(result) {
                this.deleteComment(comment);
                alert('삭제했습니다')
            } 

            // var modalInfo={
            //     msg : "댓글을 정말 지우시겠습니까?",
            //     data : comment
            // }
            // this.$emit("check-delete",modalInfo)


            //this.deleteComment(comment)
        }
    }
}
</script>

<style scoped>
    .comment-module-wrap {
        /* height: 60px; */
        width: 99%;
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
        font-size : 14px;
    }

    .comment-module-header .time {
        color: slategray;
        font-size: 10px;
    }

    .comment-module-modify {
        width: 100%;
        margin-top: 5px;
        margin-bottom: 10px;
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
        width: 90%;
        padding-left: 5%;
        padding-right: 5%;
        font-size: 12px;
        margin-bottom: 5px;
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

    /* ======= dropdown css ======= */
    .dropdown {
        position: relative;
        display: inline-block;
        padding-top: 8px;
    }

    .dropmenu {
        width: 18px;
        float: right;
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
        position: relative;
        left: calc(100% - 100px);
        background-color: rgb(247, 247, 247);
        outline: none;
        width: 100px;
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

    
.doctor-image {
  width: 15px;
  height: 15px;
}
/* ======= dropdown css ======= */
</style>