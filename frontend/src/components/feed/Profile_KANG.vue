<template>
  <div class="profile wrap">
    <div class="intro-wrap">
        <div class="left-content">
            <div class="profile-image">
              <img src="../../assets/sample.jpg" />
            </div>
            <div class="profile-name">
                <a>응응쎄</a>
            </div>
            <div class="profile-intro">
                <a>클릭해주셔서 감사합니다</a>
            </div>
        </div>
        <div class="right-content">
                <table>
                    <tr>
                        <td>게시물 수</td>
                        <td>12</td>
                    </tr>
                    <tr>
                        <td>팔로워</td>
                        <td>43</td>
                    </tr>
                    <tr>
                        <td>팔로잉</td>
                        <td>30</td>
                    </tr>
                </table>
            </div>
    </div>
    <div class="profile-modify">
        <router-link to="change">
            <button>프로필 수정</button>
        </router-link>
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
                        </tr>
                    </table>
                </div>
            </div> 
        </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState } from "vuex";
export default {
//   name: "Profile",
  computed: {
    ...mapState(['profileData'])
  },
  methods: {
    ...mapActions(['fetchProfileData']),
  },
  created() {
    // console.log(this)
    this.fetchProfileData()
    /* TEST LINE */
    // var config = {
    //     apiKey: "AIzaSyA8-aDCAa84unCo_vaXjY14XHXMcVvoGzY",
    //     authDomain: "thisis-4b0f4.firebaseapp.com",
    //     databaseURL: "https://thisis-4b0f4.firebaseio.com",
    //     storageBucket: "bucket.appspot.com"
    // }
    // firebase.initializeApp(config);
    // var database= firebase.database();
    // console.log(database);
    ///////////////
  },
};
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
        border: rgb(0, 171, 132) 3px solid;
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

    .right-content table tr td:nth-child(1) {
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


</style>