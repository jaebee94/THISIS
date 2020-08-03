<template>
  <div class="main wrap">
    <div class="logo wrap">
      <img src="../../assets/images/icon/logo_green.png" />
    </div>
    <router-view></router-view>
    <input v-model="counter" type="number" />
    <button @click="change()">버튼</button>
    <div class="footer wrap">
      <table class="footer table">
        <tr>
          <td>
            <router-link to="/main/home">
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
            </router-link>
          </td>
          <td>
            <router-link to="/main/profile">
              <img @click="checkProfile()" src="../../assets/sample.jpg" />
            </router-link>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>
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
        width: 30%;
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
        outline:none;
        width: 30%;
    }
    .footer.table td:nth-child(5) img {
        margin-top: 3px;
        width: 30px;
        height: 30px;
        border-radius: 70%;
    }
</style>
<script>
// const icon = {
//     home: require('../../assets/images/icon/icon_home_select.png'),
//     homeno: require('../../assets/images/icon/icon_home_unselect.png')
// }
import db from "../../firebaseInit";
export default {
  created() {
    var vueInstance = this;
    db.collection("notification")
      .doc(this.id)
      .onSnapshot(function (doc) {
        console.log(
          `${doc.id} => ${doc.data().request + doc.data().notification}`
        );
        vueInstance.counter = doc.data().request + doc.data().notification;
        //console.log(this.counter);
      });
    console.log("counter : " + this.counter);
    // console.log("count : " + count);

    //회원가입시 doc 생성 부분
    /*db.collection("notification")
      .doc("4")
      .set({
        notification: 0,
        request: 0
      })
      .then(function () {
        console.log("Document successfully written!");
      })
      .catch(function (error) {
        console.error("Error writing document: ", error);
      });*/
  },
  data() {
    return {
      id: "1",
      counter: 0,
      selectPage: {
        home: require("../../assets/images/icon/icon_home_select.png"),
        search: require("../../assets/images/icon/icon_search_unselect.png"),
        upload: require("../../assets/images/icon/icon_upload.png"),
        notify: require("../../assets/images/icon/icon_bell_unselect.png"),
        // //profile: require('../../assets/images/icon/icon_upload.png')
        // profile: require('../../assets/sample.jpg')
      },
    };
  },
  methods: {
    checkHome() {
      this.selectPage.home = require("../../assets/images/icon/icon_home_select.png");
      this.selectPage.search = require("../../assets/images/icon/icon_search_unselect.png");
      this.selectPage.notify = require("../../assets/images/icon/icon_bell_unselect.png");
    },
    checkSearch() {
      this.selectPage.home = require("../../assets/images/icon/icon_home_unselect.png");
      this.selectPage.search = require("../../assets/images/icon/icon_search_select.png");
      this.selectPage.notify = require("../../assets/images/icon/icon_bell_unselect.png");
    },
    checkUpload() {
      this.selectPage.home = require("../../assets/images/icon/icon_home_unselect.png");
      this.selectPage.search = require("../../assets/images/icon/icon_search_unselect.png");
      this.selectPage.notify = require("../../assets/images/icon/icon_bell_unselect.png");
    },
    checkNotify() {
      this.selectPage.home = require("../../assets/images/icon/icon_home_unselect.png");
      this.selectPage.search = require("../../assets/images/icon/icon_search_unselect.png");
      this.selectPage.notify = require("../../assets/images/icon/icon_bell_select.png");
    },
    checkProfile() {
      this.selectPage.home = require("../../assets/images/icon/icon_home_unselect.png");
      this.selectPage.search = require("../../assets/images/icon/icon_search_unselect.png");
      this.selectPage.notify = require("../../assets/images/icon/icon_bell_unselect.png");
    },
  },
};
</script>