<template>
    <div class="feed wrap">
        <div v-if="this.isPostHidden" class="post" ref="post">
            <div class="post-header">
                <table>
                    <tr>
                        <td><img class="profile-image" src="../../assets/images/icon/icon_default_image.png"></td>
                        <td><a class="name">{{this.post.name}}</a></td>
                        <td><a class="time">{{this.post.time}}</a></td>
                    </tr>
                </table>
            </div>
            <div class="post-main">
                {{this.post.content}}
            </div>
            <div class="comment-wrap">
                <div class="comment" v-for="comment in this.comments" v-bind:key="comment.post_id">
                    <div class="comment-header" v-if="comment.post_id == post.post_id">
                        <table>
                            <tr>
                                <td><img class="profile-image" src="../../assets/images/icon/icon_default_image.png"></td>
                                <td><a>{{comment.comment_id}}</a></td>
                                <td><a>{{comment.comment_time}}</a></td>
                            </tr>
                        </table>
                    </div>
                    <div class="comment-content" v-if="comment.post_id == post.post_id">
                        {{comment.comment_main}}
                    </div>
                </div>
            </div>
            <div class="comment-submit">
                <input placeholder="내용을 입력하세요">
                <button>댓글달기</button>
            </div>
            <div class="post-footer">
                <img @click="closePost()" src="../../assets/images/icon/icon_close.png">
            </div>
        </div>



        <div class="feed" v-for="item in this.items"  v-bind:key="item.post_id" >
            <div class="feed-header">
                <table>
                    <tr>
                        <td><img class="profile-image" @click="goProfile(item)" src="../../assets/images/icon/icon_default_image.png"></td>
                        <td><a @click="goProfile(item)" class="name">{{item.name}}</a></td>
                        <td><a class="time">{{item.time}}</a></td>
                    </tr>
                </table>
            </div>
            <div class="feed-main">
                {{item.content}}
            </div>
            <div class="feed-footer">
                <table>
                    <tr>
                        <td><img v-if="item.health" src="../../assets/images/icon/icon_like_select.png" @click="clickHealth(item)" >
                        <img v-if="!item.health" src="../../assets/images/icon/icon_like_unselect.png" @click="clickHealth(item)" ></td>
                        <td><img @click="showPost(item)" src="../../assets/images/icon/icon_talk.png"></td>
                    </tr>
                </table>
            </div>
        </div> 
       
    </div>
</template>
<style scoped>
    * {
        font-size: 15px;
    }

    .feed.wrap {
        text-align: center;
        background-color: white;
        padding-bottom: 20px;
    }

    .feed {
        padding: 1% 1%;
        width: 92%;
        margin: 2% 3%;
        margin-bottom: 5%;
        background-color: rgb(247, 247, 247);
        border: none;
        border-radius: 5px;
    }

    .feed-header {
        display: flex;
        background-color: rgb(0, 171, 132);
        text-align: left;
        width: 100%;
        height: 40px;
        border: none;
        border-radius: 5px;
        vertical-align: middle;
    }

    .feed-header table {
        text-align: center;
        width: 100%;
    }

    .feed-header table tr td {
        height: 40px;
    }

    .feed-header table tr td a {
        color: white;
        font-weight: 600;
    }

    .feed-header table tr td img {
        height: 80%;
    }

    .feed-header table tr td:nth-child(2){
        text-align: left;
    }

    .feed-header table tr td:nth-child(3){
        padding-right: 10px;
        text-align: right;
    }

    .profile-image {
        background-color: white;
        border-radius: 70%;
    }

    .feed-footer {
        width: 100%;
        height: 30px;
        text-align: left;
        background-color: rgb(200, 200, 200);
    }

    .feed-footer table {
        margin-left: 20px;
    }

    .feed-footer table tr td {
        width: 50px;
        height: 30px;
    }

    .feed-footer table tr td img {
        margin: 2% 5%;
        height: 60%;
    }

    .post {
        position: fixed;
        z-index: 99;
        top: 10%;
        width: 92%;
        height: 80%;
        background-color: white;
        border-radius: 5px;
    }

    .post-header {
        display: flex;
        background-color: rgb(0, 171, 132);
        text-align: left;
        width: 100%;
        height: 40px;
        border: none;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        vertical-align: middle;
    }

    .post-header table {
        text-align: center;
        width: 100%;
    }

    .post-header table tr td {
        height: 40px;
    }

    .post-header table tr td a {
        color: white;
        font-weight: 600;
    }

    .post-header table tr td img {
        height: 80%;
    }

    .post-header table tr td:nth-child(2){
        text-align: left;
    }

    .post-header table tr td:nth-child(3){
        padding-right: 10px;
        text-align: right;
    }

    .post-main {
        padding: 10px 10px;
        text-align: left;
    }

    .post-footer {
        position: absolute;
        width: 100%;
        height: 10%;
        bottom: -12%;
    }

    .post-footer img {
        height: 80%;
    }

   

    .comment-wrap {
        display: inherit;
        overflow: auto;
        height: 70%;
    }

     .comment {
        width: 90%;
        margin-left: 5%;
        margin-bottom: 5%;
    }

    .comment-header {
        height: 30px;
        background-color: rgb(0, 171, 132);
        border-radius: 5px;
    }

    /* .comment-header table tr td{
        height: 20px;
    } */

    .comment-header table {
        width: 100%;
        height: 100%;
    }

    .comment-header table tr {
        height: 20px;
    }

    .comment-header table tr td {
        padding: 2px;
    }

    .comment-header table tr td a {
        color: white;
        font-weight: 600;
    }

    .comment-header table tr td:nth-child(1) img {
        height: 20px;
    }

    .comment-content {
        text-align: left;
    }

    .comment-submit {
        position: absolute;
        height: 8%;
        width: 90%;
        margin-left: 5%;
        bottom: 1%;
    }

</style>
<script>
export default {
    data() {
        return {
            isPostHidden: false,
            post : {
                post_id: "",
                user_id: "",
                name: "",
                time: "",
                health: "",
                content: ""
            },
            items: [
                {
                    post_id: "1",
                    user_id: "seeung0305@naver.com",
                    name: "강세응",
                    time: "2020-07-24",
                    health: true,
                    content: "내용은 중요하지 않다고 생각해 이걸 굳이 일일이 쓰는 것도 살짝 멍청한 거 아님? 내용을 뭐하러 쓰냐 그 시간에 왜 안되는지 찾아보던가"
                },
                {  
                    post_id: "2",
                    user_id: "woqls8848@naver.com",
                    name: "조재빈",
                    time: "2020-07-24",
                    health: false,
                    content: "사람을 기분 나쁘게 하는 방법은 두 가지가 있다. 하나는 말을 하다가 마는 것이고"
                },
                {
                    post_id: "3",
                    user_id: "jieun2@naver.com",
                    name: "채지은",
                    time: "2020-07-24",
                    health: true,
                    content: "스프링 백엔드 어렵지 않아 김재현 보고좀 배우자 제발 그런데 나도 잘 못하긴 함ㅎ 지은이 하는거 잘 보고 배우자 재현 알갔지?"
                },
                {  
                    post_id: "4",
                    user_id: "ru6300@naver.com",
                    name: "김재현",
                    time: "2020-07-24",
                    health: true,
                    content: "세응 이거 어떻게 해? 세응 이거 다했어? 세응 오늘까지 이거 꼭 해야돼? 아 나 오늘 설문 안했네. 세응 프레임 그만 좀 씌워"
                },
                {
                    post_id: "5",
                    user_id: "jihyun96@naver.com",
                    name: "김지현",
                    time: "2020-07-24",
                    health: false,
                    content: "너 이 ssㅐ끼 인성에 문줴있어? 입수. 입수는 muri 부터 발끝까쥐 다 적셔야 입쑤야. 악!"
                },
                {  
                    post_id: "6",
                    user_id: "woqls8848@naver.com",
                    name: "조재빈",
                    time: "2020-07-24",
                    health: true,
                    content: "프론트 하드캐리는 갓직히 갓재빈이 하고 있다. 얼른 UI를 어느정도끝내고 버그잡아야 겠다고 생각하는 부분이다"
                },
            ],
            comments : [
                {
                    post_id: "1",
                    comment_id: "조재빈",
                    comment_main: "시발 새기야 무슨 문제가 있는 거지 무슨 문제 있 는 거 지 무슨 문제지",
                    comment_time: "2020-07-27"
                },
                {
                    post_id: "1",
                    comment_id: "강세응",
                    comment_main: "안녕미안해",
                    comment_time: "2020-07-27"
                },
                {
                    post_id: "1",
                    comment_id: "강세응",
                    comment_main: "안녕미안해",
                    comment_time: "2020-07-27"
                },
                {
                    post_id: "1",
                    comment_id: "강세응",
                    comment_main: "안녕미안해",
                    comment_time: "2020-07-27"
                },
                {
                    post_id: "1",
                    comment_id: "강세응",
                    comment_main: "안녕미안해",
                    comment_time: "2020-07-27"
                },
                {
                    post_id: "1",
                    comment_id: "강세응",
                    comment_main: "안녕미안해",
                    comment_time: "2020-07-27"
                },
                {
                    post_id: "1",
                    comment_id: "강세응",
                    comment_main: "안녕미안해",
                    comment_time: "2020-07-27"
                },
                {
                    post_id: "1",
                    comment_id: "강세응",
                    comment_main: "안녕미안해",
                    comment_time: "2020-07-27"
                },
                {
                    post_id: "1",
                    comment_id: "강세응",
                    comment_main: "안녕미안해",
                    comment_time: "2020-07-27"
                },
                {
                    post_id: "1",
                    comment_id: "강세응",
                    comment_main: "안녕미안해",
                    comment_time: "2020-07-27"
                },
                {
                    post_id: "1",
                    comment_id: "강세응",
                    comment_main: "안녕미안해",
                    comment_time: "2020-07-27"
                }
                
            ]
        }
    },
    created(){
        console.log(this.$refs);
    }, methods :{ 
        clickHealth (item) {
            item.health = !item.health;
            console.log(item);
        },
        showPost(item) {
            this.post = item;
            console.log(this.$parent.$parent);
            this.$parent.$parent.isHidden = true;
            this.isPostHidden = true;
            
            console.log(this.post);
        },
        closePost() {
            this.$parent.$parent.isHidden = false;
            this.isPostHidden = false;
        },
        goProfile(item) {
            window.location.href="/main/user/" + item.user_id;
        }
    }
}
</script>