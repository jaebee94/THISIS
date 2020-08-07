<template>
    <div class="comment-module-wrap">
        <div class="comment-module-header">
            <a class="name">{{comment.nickname}}</a>
            <a class="time">{{comment.comment_date}}</a>
        </div>
        <div class="comment-module-content">
            {{comment.comment_main}}
        </div>
        <span class="comment-module-footer">
            <img class="comment-module-health" v-if="true" :src="isHealth" @click="clickHealth()">
            <img class="comment-module-health" v-if="true == false" :src="isNotHealth" @click="clickHealth()">
            <span class="comment-health-count">12</span>
        </span>
    </div>
</template>

<script>
export default {
    name: 'comment',
    data () {
        return {
            isHealth: require("../../assets/images/icon/icon_like_select.png"),
            isNotHealth: require("../../assets/images/icon/icon_like_unselect.png"),
        }
    },
    props: {
        comment: {
            default: void 0,
        },
    }
}
</script>

<style scoped>
    .comment-module-wrap {
        width: 100%;
        height: 60px;
        background-color: white;
        border-bottom: 1px slategray solid;
    }

    .comment-module-header {
        background-color : white;
        text-align: left;
        padding-left: 5%;
        height: 20px;
    }

    .comment-module-header .name {
        margin-right: 10px;
    }

    .comment-module-header .time {
        color: slategray;
        font-size: 10px;
    }

    .comment-module-content {
        text-align: left;
        width: 80%;
        padding-left: 5%;
        padding-right: 5%;
        font-size: 13px;
        margin-bottom: 5px;
    }

    .comment-module-footer {
        position: relative;
        top: -30px;
        right: -40%;
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