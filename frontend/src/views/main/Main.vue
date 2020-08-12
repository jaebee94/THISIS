<template>
  <div class="main wrap">
    <div class="logo wrap">
      <img src="../../assets/images/icon/logo_green.png" />
    </div>
    <router-view></router-view>

    <div class="footer wrap">
      <table class="footer table">
        <tr>
          <td>
            <router-link to="/main/feed">
              <img @click="checkHome()" v-bind:src="selectPage.home" />
            </router-link>
          </td>
          <td>
            <router-link to="/main/search">
              <img @click="checkSearch()" v-bind:src="selectPage.search" />
            </router-link>
          </td>
          <td>
            <router-link to="/main/upload">
              <img @click="checkUpload()" v-bind:src="selectPage.upload" />
            </router-link>
          </td>
          <td>
            <router-link to="/main/notify">
              <img @click="checkNotify()" v-bind:src="selectPage.notify" />
              <span class="notify-num" v-show="this.noti != 0">{{noti}}</span>
            </router-link>
          </td>
          <td>
            <!-- <router-link to="/main/profile"> -->
              <img @click="checkProfile()" :src="loginData.userimage" />
            <!-- </router-link> -->
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import db from "../../firebaseInit";
import { mapState, mapActions } from "vuex";
export default {
  created() {
    this.getNoti(this.loginData.user_id);
    console.log("메인에서 로그인", this.loginData)
  },
  data() {
    return {
      id: 'default',
      noti: 0,
      selectPage: {
        home: require("../../assets/images/icon/icon_home_select.png"),
        search: require("../../assets/images/icon/icon_search_unselect.png"),
        upload: require("../../assets/images/icon/icon_upload.png"),
        notify: require("../../assets/images/icon/icon_bell_unselect.png"),
        // //profile: require('../../assets/images/icon/icon_upload.png')
        // profile: require('../../assets/sample.jpg'),
      },
    };
  },
  watch: {
    noti: function () {
      this.getNoti(this.loginData.user_id);
    }
  },
  computed: {
    ...mapState('userStore', ['loginData', 'profileData']),
    // ...mapState('profileStore', ['profileData']),
  },
  methods: {
    // ...mapActions('profileStore', ['goProfile']),
    ...mapActions('userStore', ['goProfile']),
    ...mapActions('postStore', ['getUserScraps']),

    getNoti(id) {
      const noti = db.collection("notification").doc(String(id));
      let vueInstance = this;
      noti.get()
      .then(function(doc){
        if(doc.exists) {
          vueInstance.noti = doc.data().notification + doc.data().request;
        } else {
          console.log("No Such Document!");
        }
      })
      .catch(function(err){
        console.log("ERROR OCCURED : ", err);
      });
    },
    checkHome() {
      this.selectPage.home = require("../../assets/images/icon/icon_home_select.png");
      this.selectPage.search = require("../../assets/images/icon/icon_search_unselect.png");
      this.selectPage.notify = require("../../assets/images/icon/icon_bell_unselect.png");
      this.getNoti(this.loginData.user_id);
    },
    checkSearch() {
      this.selectPage.home = require("../../assets/images/icon/icon_home_unselect.png");
      this.selectPage.search = require("../../assets/images/icon/icon_search_select.png");
      this.selectPage.notify = require("../../assets/images/icon/icon_bell_unselect.png");
      this.getNoti(this.loginData.user_id);
    },
    checkUpload() {
      this.selectPage.home = require("../../assets/images/icon/icon_home_unselect.png");
      this.selectPage.search = require("../../assets/images/icon/icon_search_unselect.png");
      this.selectPage.notify = require("../../assets/images/icon/icon_bell_unselect.png");
      this.getNoti(this.loginData.user_id);
    },
    checkNotify() {
      this.selectPage.home = require("../../assets/images/icon/icon_home_unselect.png");
      this.selectPage.search = require("../../assets/images/icon/icon_search_unselect.png");
      this.selectPage.notify = require("../../assets/images/icon/icon_bell_select.png");
      this.getNoti(this.loginData.user_id);
    },
    checkProfile() {
      this.selectPage.home = require("../../assets/images/icon/icon_home_unselect.png");
      this.selectPage.search = require("../../assets/images/icon/icon_search_unselect.png");
      this.selectPage.notify = require("../../assets/images/icon/icon_bell_unselect.png");
      this.getNoti(this.loginData.user_id);
      this.goProfile(this.loginData.user_id);
      this.getUserScraps(this.loginData.user_id);
    },
  },
};
</script>

<style scoped>
.main.wrap {
  width: 100%;
  height: 100%;
}
.logo.wrap {
  width: 100%;
  height: 10%;
  background-color: rgb(240, 240, 240);
}
.logo.wrap img {
  margin: 5px 5px auto;
  width: 20%;
}
.footer.wrap {
  position: fixed;
  bottom: 0px;
  width: 100%;
  height: 40px;
  background-color: rgb(240, 240, 240);
}
.footer.table {
  width: 100%;
  height: 100%;
}
.footer.table td {
  width: 20%;
}
.footer.table td img {
  width: 30%;
}
.footer.table td:nth-child(5) img {
  margin-top: 3px;
  width: 30px;
  height: 30px;
  border-radius: 70%;
}

.notify-num {
  padding: 2px 2px;
  position: fixed;
  margin-left: -5px;
  text-decoration: none;
  background-color: red;
  border-radius: 70%;
  font-size: 10px;
  color: white;
}
</style>