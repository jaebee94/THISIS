<template>
    <div class="profile wrap">
        <!-- <div v-if="this.isPostModify" class="post-content-modify">
            <div class="feed-header">
                <table>
                    <tr>
                        <td><img class="profile-image" src="../../assets/images/icon/icon_default_image.png"></td>
                        <td><a class="name">{{mPost.name}}</a></td>
                        <td><a class="time">{{mPost.time}}</a></td>
                    </tr>
                </table>
            </div>
            <div class="post-content"> 
                <textarea v-model="mPost.content"></textarea>
            </div>
            <div class="post-modify-btn">
                <button @click="modifyPostClick()">수정하기</button>
            </div>
            <div class="modify-footer">
                <img @click="closeModify(mPost.post_id)" src="../../assets/images/icon/icon_close.png">
            </div>
        </div> -->
        <router-view name="modify" v-if="this.isPostModify"></router-view>
        <div class="intro-wrap">
            <div class="left-content">
                <div class="profile-image">
                    <img src="../../assets/sample.jpg">
                </div>
                <div class="profile-name">
                    <a>{{user.user_name}}</a>
                </div>
                <div class="profile-intro">
                    <a>{{user.user_intro}}</a>
                </div>
            </div>
            <div class="right-content">
                <table>
                    <tr>
                        <td>게시물 수</td>
                        <td>{{posts.length}}</td>
                    </tr>
                    <tr>
                        <td>팔로워</td>
                        <td>{{follower}}</td>
                    </tr>
                    <tr>
                        <td>팔로잉</td>
                        <td>{{followee}}</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="profile-modify">
            <!-- 나의 아이디와 보고 있는 페이지의 유저 아이디가 같을 경우 -->
            <router-link to="/main_kang/change"><button @click="modifyProfile()" v-show="this.id == this.user.user_id">프로필 수정</button></router-link>
            <!-- 나의 아이디와 보고 있는 페이지의 유저 아이디가 다른 경우 -->
            <!-- 1. 팔로우 요청도 안 보낸 상태(data의 followSend가 false일 경우 + isFollowing이 false일 경우) -->
             <button @click="follow()" v-show="this.id != this.user.user_id && !this.isFollowing && !this.followSend">팔로우 요청</button>
            <!-- 2. 팔로우 요청을 보냈지만 승인을 받지 못한 상태(followSend가 true일 경우 + isFollowing이 false일 경우) -->
            <button @click="followCancel()" v-show="this.id != this.user.user_id && this.followSend && !this.isFollowing">팔로우 요청 취소</button>
            <!-- 3. 팔로우 요청을 보냈고 승인을 받은 상태(followSend가 true + isFollowing이 true) -->        
            <button @click="followingCancel()" v-show="this.id != this.user.user_id && this.isFollowing">팔로잉</button>
        </div>
        <div class="tabs">
            <div class="tab-container">
                <div class="tab" v-for="(tab, index) in tabs" v-bind:key="tab" v-bind:class="{active: currentTab === index}" 
                    @click="currentTab = index">
                    <img :src="tab">
                </div>
            </div>
            
            <div v-show="currentTab == 0">
                <div class="feed" v-for="post in this.posts"  v-bind:key="post.post_id" >
                    <div class="feed-header">
                        <table>
                            <tr>
                                <td><img class="profile-image" src="../../assets/images/icon/icon_default_image.png"></td>
                                <td><a class="name">{{post.name}}</a></td>
                                <td><a class="time">{{post.time}}</a></td>
                            </tr>
                        </table>
                    </div>
                    <div class="feed-main">
                        {{post.content}}
                    </div>
                    <div class="feed-footer">
                        <table>
                            <tr>
                                <td><img v-if="post.health" src="../../assets/images/icon/icon_like_select.png" @click="clickHealth(post)" >
                                <img v-if="!post.health" src="../../assets/images/icon/icon_like_unselect.png" @click="clickHealth(post)" ></td>
                                <td><img @click="showPost(post)" src="../../assets/images/icon/icon_talk.png"></td>
                                <td><img @click="modifyPost(post)" v-if="id == post.user_id" src="../../assets/images/icon/icon_edit_unselect.png"></td>
                            </tr>
                        </table>
                    </div>
                </div> 
            </div>
            <div v-show="currentTab == 1">
                <div class="feed" v-for="scrap in this.scraps"  v-bind:key="scrap.post_id" >
                    <div class="feed-header">
                        <table>
                            <tr>
                                <td><img class="profile-image" src="../../assets/images/icon/icon_default_image.png"></td>
                                <td><a class="name">{{scrap.name}}</a></td>
                                <td><a class="time">{{scrap.time}}</a></td>
                            </tr>
                        </table>
                    </div>
                    <div class="feed-main">
                        {{scrap.content}}
                    </div>
                    <div class="feed-footer">
                        <table>
                            <tr>
                                <td><img v-if="scrap.health" src="../../assets/images/icon/icon_like_select.png" @click="clickHealth(scrap)" >
                                <img v-if="!scrap.health" src="../../assets/images/icon/icon_like_unselect.png" @click="clickHealth(scrap)" ></td>
                                <td><img @click="showPost(scrap)" src="../../assets/images/icon/icon_talk.png"></td>
                                <td><button v-if="id == scrap.user_id">수정</button></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import db from '../../firebaseInit'
import firebase from 'firebase'

import { mapActions, mapState } from 'vuex'
//const increment = db.firebase.firestore.FieldValue.increment(1);
const increment = firebase.firestore.FieldValue.increment(1);
const decrement = firebase.firestore.FieldValue.increment(-1);

export default {
    computed: {
        ...mapState(["followee_list"])
    },
    data () {
        return {
            id: 1, // 현재 나의 아이디 -> store에 저장되거나 서버에서 가져오거나
            followSend: false, // followSend - true : 팔로우 신청을 한 상태 / false : 팔로우 신청을 하지 않은 상태
            isFollowing: false, // isFollowing - true : 팔로우 하는 중 / false : 팔로우를 하고 있지 않음
            user : {
                user_id: this.$route.params.id,
                user_name: '달린다응쎄',
                user_intro: '안녕하세요 ㅎㅎ',
            },
            isPostModify: false,
            mPost : {
                post_id: "1",
                user_id: "1",
                name: "1",
                time: "1",
                health: "1",
                content: "1"
            },
            follower : 303,
            followee: 101,
            currentTab: 0,
            tabs: [require('../../assets/images/icon/icon_post.png'), require('../../assets/images/icon/icon_scrap_unselect.png')],
            posts: [
                {
                    post_id: "1",
                    user_id: "2",
                    name: "강세응",
                    time: "2020-07-24",
                    health: true,
                    content: "내용은 중요하지 않다고 생각해 이걸 굳이 일일이 쓰는 것도 살짝 멍청한 거 아님? 내용을 뭐하러 쓰냐 그 시간에 왜 안되는지 찾아보던가"
                },
                {  
                    post_id: "2",
                    user_id: "2",
                    name: "강세응",
                    time: "2020-07-21",
                    health: false,
                    content: "사람을 기분 나쁘게 하는 방법은 두 가지가 있다. 하나는 말을 하다가 마는 것이고"
                },
                {
                    post_id: "3",
                    user_id: "2",
                    name: "강세응",
                    time: "2020-07-24",
                    health: true,
                    content: "스프링 백엔드 어렵지 않아 김재현 보고좀 배우자 제발 그런데 나도 잘 못하긴 함ㅎ 지은이 하는거 잘 보고 배우자 재현 알갔지?"
                },
                {  
                    post_id: "4",
                    user_id: "2",
                    name: "강세응",
                    time: "2020-07-24",
                    health: true,
                    content: "세응 이거 어떻게 해? 세응 이거 다했어? 세응 오늘까지 이거 꼭 해야돼? 아 나 오늘 설문 안했네. 세응 프레임 그만 좀 씌워"
                },
                {
                    post_id: "5",
                    user_id: "2",
                    name: "강세응",
                    time: "2020-07-24",
                    health: false,
                    content: "너 이 ssㅐ끼 인성에 문줴있어? 입수. 입수는 muri 부터 발끝까쥐 다 적셔야 입쑤야. 악!"
                },
                {  
                    post_id: "6",
                    user_id: "2",
                    name: "강세응",
                    time: "2020-07-24",
                    health: true,
                    content: "프론트 하드캐리는 갓직히 갓재빈이 하고 있다. 얼른 UI를 어느정도끝내고 버그잡아야 겠다고 생각하는 부분이다"
                },
            ],
            scraps: [
                {
                    post_id: "7",
                    user_id: "1",
                    name: "조재빈",
                    time: "2020-07-24",
                    health: true,
                    content: "내용은 중요하지 않다고 생각해 이걸 굳이 일일이 쓰는 것도 살짝 멍청한 거 아님? 내용을 뭐하러 쓰냐 그 시간에 왜 안되는지 찾아보던가"
                },
                {  
                    post_id: "8",
                    user_id: "3",
                    name: "김지현",
                    time: "2020-07-24",
                    health: false,
                    content: "사람을 기분 나쁘게 하는 방법은 두 가지가 있다. 하나는 말을 하다가 마는 것이고"
                },
                {
                    post_id: "9",
                    user_id: "4",
                    name: "채지은",
                    time: "2020-07-24",
                    health: true,
                    content: "스프링 백엔드 어렵지 않아 김재현 보고좀 배우자 제발 그런데 나도 잘 못하긴 함ㅎ 지은이 하는거 잘 보고 배우자 재현 알갔지?"
                },
                {  
                    post_id: "10",
                    user_id: "5",
                    name: "김재현",
                    time: "2020-07-24",
                    health: true,
                    content: "세응 이거 어떻게 해? 세응 이거 다했어? 세응 오늘까지 이거 꼭 해야돼? 아 나 오늘 설문 안했네. 세응 프레임 그만 좀 씌워"
                },
                {
                    post_id: "11",
                    user_id: "1",
                    name: "조재빈",
                    time: "2020-07-24",
                    health: false,
                    content: "너 이 ssㅐ끼 인성에 문줴있어? 입수. 입수는 muri 부터 발끝까쥐 다 적셔야 입쑤야. 악!"
                },
                {  
                    post_id: "12",
                    user_id: "3",
                    name: "김지현",
                    time: "2020-07-24",
                    health: true,
                    content: "프론트 하드캐리는 갓직히 갓재빈이 하고 있다. 얼른 UI를 어느정도끝내고 버그잡아야 겠다고 생각하는 부분이다"
                },
            ]
        }  
    },
    mounted() {
        this.user.user_id = this.$route.params.id;
    },
    created() { 
        //let data = [];
        
        let vueInstance = this;
        let params = {
            followee_id: this.user.user_id,
            follower_id: this.id
        }

        this.$store.dispatch("getFollowee", params);

        setTimeout( () => {this.isFollowing = this.followee_list; console.log(this.followee_list)}, 500);
        //this.isFollowing = false;
        

        const noti = db.collection("notification").doc(String(this.user.user_id));
        let instance = {};
        
        noti.get()
        .then(function(doc){
            instance = doc.data();
            console.log(instance)
            if(instance[vueInstance.id] == false) {
                console.log("!!");
                vueInstance.followSend = true;
            }
        })
        .catch(function(err){
            console.log("ERROR OCCURED : ", err);
        });

    },
    methods : {
        ...mapActions(["getFollowee", "createFollowing", "deleteFollowing", "getUserScraps"]),
        clickHealth(post) {
            post.health = !post.health;
        },
        modifyProfile() {
           // console.log("!!!");
           
        },
        modifyPost(post) {
            this.mPost = post;
            this.$parent.$parent.isHidden = true;
            this.isPostModify = true;
        },
        modifyPostClick(post_id) {
            console.log(this.mPost);
            this.posts.forEach(function (element){
                if(element.post_id == post_id) {
                    element = this.mPost;
                }
            })
            this.closeModify();
        },
        closeModify(){
            this.$parent.$parent.isHidden = false;
            this.isPostModify = false;
        },
        follow() {
            let vueInstance = this;
            this.followSend = true;
            // 요청을 받은 사람의 request 증가 + 요청을 보낸 사람의 id 값을 가지기
            let instance = {
                request : increment
            };
            instance[this.id] = false;
            // 보내는 사람의 notification(알림) 1 증가
            db
            .collection("notification")
            .doc(String(this.id))
            .update({
                notification: increment,
            })
            .then(function () {
                vueInstance.$parent.getNoti(vueInstance.id);
            })
            .catch(function (error) {
                console.error("Error setting document: ", error);
            });
            // 받는 사람의 request(요청) 1 증가
            db
            .collection("notification")
            .doc(String(this.user.user_id))
            .update(instance)
            .then(function () {
                vueInstance.$parent.getNoti(vueInstance.id);
            })
            .catch(function (error) {
                console.error("Error setting document: ", error);
            });
            let params = {
                follower_id: this.id,
                followee_id: this.user.user_id,
                approval : 0
            }
            this.$store.dispatch("createFollowing", params );
        },
        followCancel() {
            let vueInstance = this;
            this.followSend = false;
            let instance = {
                request: decrement
            }
            instance[this.id] = firebase.firestore.FieldValue.delete();
            // 보내는 사람의 notification(알림) 1 감소
            db
            .collection("notification")
            .doc(String(this.id))
            .update({
                notification: decrement,
            })
            .then(function () {
                vueInstance.$parent.getNoti(vueInstance.id)
            })
            .catch(function (error) {
                console.error("Error setting document: ", error);
            });
            // 받는 사람의 request(요청) 1 감소
            db
            .collection("notification")
            .doc(String(this.user.user_id))
            .update(instance)
            .then(function () {
                vueInstance.$parent.getNoti(vueInstance.id)
            })
            .catch(function (error) {
                console.error("Error setting document: ", error);
            });

            let params = {
                follower_id: this.id,
                followee_id: this.user.user_id,
                approval: 1
            }
            this.$store.dispatch("deleteFollowing", params );
        },
        followingCancel() {
            var result = confirm("팔로우를 취소하시겠습니까?");
            if(result) {
                this.isFollowing = false;
                // db에서 follower followee 관계 삭제
            } else {
                alert("팔로우 유지합니다")
            }
            
        }
    }
}
</script>
<style scoped>
    .profile.wrap {
        width: 100%;
        height: 100%;
    }
    .intro-wrap {
        display: flex;
        width: 100%;
        height: 200px;
    }
    .left-content {
        padding: 20px;
        float: left;
        width: 50%;
    }
    .profile-image img { 
        border:rgb(0, 171, 132) 3px solid;
        border-radius: 70%;
        width: 60%;
    }
    .profile-name a {
        font-size: 20px;
        font-weight: 600;
    }

    .profile-intro a {
        font-size: 15px;
    }
    .right-content {
        float: right;
        width: 50%;
    }
    .right-content table {
        width: 100%;
        margin-top: 20%;
        height: 60%;
    }

    .right-content table tr td:nth-child(1){
        font-size: 15px;
        font-weight: 500;
        width: 60%;
    }

    .right-content table tr td:nth-child(2) {
        font-size: 20px;
        font-weight: 600;
        color: rgb(0, 171, 132);
        text-align: left;
    }
    .profile-modify {
        width: 90%;
        height: 5%;
        margin-left: 5%;
        margin-bottom: 5%;
    }
    .profile-modify button {
        width: 100%;
    }

    .tabs {
        width: 100%;
        height: 70%;
    }
    .tab-container {
        display: flex;
    }
    .tab {
        width: 50%;
        height: 40px;
        vertical-align: middle;
        background-color: rgb(247, 247, 247);
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }
    .tab img {
        margin-top: 10px;
        height: 20px;
    }
    .tab.active {
        background-color: rgb(0, 171, 132);
    }




    /* 피드 관련 */
    .feed {
        padding: 1% 1%;
        width: 92%;
        margin: 2% 3%;
        margin-bottom: 5%;
        background-color: rgb(247, 247, 247);
        border: none;
        border-radius: 5px;
    }

    .feed:last-child {
        margin-bottom: 40px;
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

    /* 수정할 포스트 내용 보이기 */
    .post-content-modify {
        position: fixed;
        z-index: 99;
        margin-left: 4%;
        top: 20%;
        width: 92%;
        height: 400px;
        background-color: white;
        border-radius: 5px;
    }

    .post-content textarea {
        padding: 5px 5px;
        font-size: 20px;
        margin-top: 10px;
        width: 90%;
        height: 280px;
        word-wrap: break-word;
        transition-duration: 300ms;
        border: none;
    }

    .post-content textarea:focus {
        outline: none;
        border:rgb(0, 171, 132) 3px solid;
    }

    .post-modify-btn {
        width: 100%;
        height: 50px;
    }

    .post-modify-btn button {
        width: 90%;
        height: 90%;
        border: none;
        border-radius: 5px;
        background-color: rgb(0, 171, 132);
        outline: none;
        color: white;
        font-size: 20px;
    }

    .modify-footer {
        position: absolute;
        width: 100%;
        height: 10%;
        bottom: -12%;
    }

    .modify-footer img {
        height: 80%;
    }

    
</style>