<template>
  <div class="notify">
    <div class="tabs">
      <div class="tab-container">
        <div
          class="tab"
          v-for="(tab, index) in tabs"
          v-bind:key="tab"
          v-bind:class="{active: currentTab === index}"
          @click="clickNoti(index)"
        >
          <a>{{tab}}</a>
          <span class="notify-num" v-if="index == 0 && noti_count != 0">{{noti_count}}</span>
          <span class="notify-num" v-if="index == 1 && req_count != 0">{{req_count}}</span>
        </div>
      </div>
      <div class="notify-panel">
        <div v-show="currentTab == 0">
          <div v-for="(noti, index) in this.notification" v-bind:key="noti.notification.id">
            <div class="notifications" :class="{new : index < noti_count}">
              <div :class="{new : index < noti_count}">
                <table>
                  <td
                    v-if="noti.notification.approval === 0"
                  ><strong>{{noti.userInfo.nickname}}</strong>님에게 팔로우 요청하셨습니다</td>
                  <td
                    v-if="noti.notification.approval === 1"
                  ><strong>{{noti.userInfo.nickname}}</strong>님이 팔로우 요청을 승인하셨습니다</td>
                  <td
                    v-if="noti.notification.approval === 2"
                  ><strong>{{noti.userInfo.nickname}}</strong>님이 팔로우 요청을 거절하셨습니다</td>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div v-show="currentTab == 1">
          <div v-for="(noti, index) in this.requests" v-bind:key="noti.notification.id">
            <div
              class="notifications"
              :class="{new : index < req_count} "
              v-if="noti.notification.approval === 0"
            >
              <div class="notification" :class="{new : index < req_count} ">
                <table>
                  <tr>
                    <td><strong>{{noti.userInfo.nickname}}</strong>님의 팔로우 요청</td>
                    <td>
                      <button @click="accessFollow(noti)">승낙</button>
                    </td>
                    <td>
                      <button @click="rejectFollow(noti)">거절</button>
                    </td>
                  </tr>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import db from "../../firebaseInit";
import { mapActions, mapState } from "vuex";
import firebase from "firebase";
import router from "@/router";
const increment = firebase.firestore.FieldValue.increment(1);
//const decrement = firebase.firestore.FieldValue.increment(-1);
export default {
  computed: {
    ...mapState("notificationStore", ["notification", "requests"]),
    ...mapState("userStore", ["loginData", "profileData"]),
  },
  data() {
    return {
      currentTab: 0,
      tabs: ["알림", "요청"],
      noti_count: 0,
      req_count: 0,
      tab1: 0,
      tab2: 0,
    };
  },
  methods: {
    ...mapActions("followStore", ["createFollow"]),
    ...mapActions("notificationStore", [
      "fetchNotification",
      "putNotification",
      "fetchRequests",
    ]),

    accessFollow(noti) {
      noti.notification.approval = 1;
      let params = {
        follower: noti.notification.follower_id,
        followee: noti.notification.followee_id,
      };
      this.$store.dispatch(
        "notificationStore/putNotification",
        noti.notification
      );
      this.$store.dispatch("followStore/createFollow", params);
      this.save(noti);
      this.deleteFromFirebase(noti);
      this.req_count -= 1;
    },
    rejectFollow(noti) {
      noti.notification.approval = 2;
      this.$store.dispatch(
        "notificationStore/putNotification",
        noti.notification
      );
      this.save(noti);
      this.deleteFromFirebase(noti);
      this.req_count -= 1;
    },
    save(noti) {
      let instance = {
        notification: increment, //1올려줌
      };
      db.collection("notification")
        .doc(String(noti.notification.follower_id)) //승낙했을 때 상대방 알림count+1
        .update(instance)
        .catch(function (error) {
          console.error("Error setting document: ", error);
        });
    },
    deleteFromFirebase(noti) {
      //내가 승낙했을 시 나의 doc에 팔로우 건 아이디 필드 삭제
      let instance = {};
      instance[
        noti.notification.follower_id
      ] = firebase.firestore.FieldValue.delete();
      db.collection("notification")
        .doc(String(noti.notification.followee_id))
        .update(instance)
        .catch(() => {
          console.error("FIREBASE DELETION UNEXECUTED");
        });
    },
    clickNoti(idx) {
      this.currentTab = idx;
      if (idx == 0) this.tab1++;
      if (idx == 1) this.tab2++;

      if (idx == 0) {
        if (this.tab1 > 0) this.initNoti();
      } else {
        if (this.tab2 > 1) this.initRequest();
      }
    },
    initNoti() {
      db.collection("notification").doc(String(this.loginData.user_id))
        .update({
          notification: 0,
        })
        .catch(function (err) {
          console.error(err);
        });
      this.noti_count = 0;
    },
    initRequest() {
      db.collection("notification").doc(String(this.loginData.user_id))
        .update({
          request: 0,
        })
        .catch(function (err) {
          console.error(err);
        });
      this.req_count = 0;
    },
  },
  created() {
    const noti = db.collection("notification").doc(String(this.loginData.user_id));
    if (this.loginData == null) router.push({ name: "Landing" });
    window.addEventListener("beforeunload", this.clickNoti(0));
    let vueInstance = this;
    
    noti.onSnapshot(
      {
        // Listen for document metadata changes
        includeMetadataChanges: true,
      },
      function () {
        //이벤트 발생시 카운트 재정립
        vueInstance.fetchNotification(vueInstance.loginData.user_id);
        vueInstance.fetchRequests(vueInstance.loginData.user_id);
        noti
          .get()
          .then(function (doc) {
            if (doc.exists) {
              vueInstance.noti_count = doc.data().notification;
              vueInstance.req_count = doc.data().request;
            } else {
              console.log("No such document!");
            }
          })
          .catch(function (error) {
            console.log("Error getting document:", error);
          });
      }
    );

    
    // this.$store.dispatch("fetchNotification", this.loginData.user_id); //내가 요청한 것
    // this.$store.dispatch("fetchRequests", this.loginData.user_id); //내가 요청 받은것 followee
  },
};
</script>

<style scoped>
.notify {
  text-align: center;
  background-color: white;
  /* padding: 20px 20px 60px 20px; */
}
.notify-panel {
  width: 100%;
  /* height: 600px; */
  /* background-color: rgb(247, 247, 247); */
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
  border-bottom: 3px rgb(247, 247, 247) solid;
}
.tab a {
  font-size: 18px;
  font-weight: 600;
  line-height: 2;
  height: 20px;
}
.tab.active {
  border-bottom: 3px rgb(0, 171, 132) solid;
}

.notify-num {
  background-color: red;
  color: white;
  font-size: 11px;
  border-radius: 70%;
  padding: 1px 4px;
  padding-right: 5px;
  position: absolute;
  margin-top: 3px;
  /* margin-left: 25px; */
}

.notifications {
  padding: 1% 1%;
  width: 92%;
  margin: 2% 3%;
  margin-bottom: 10px;
  background-color: rgb(240, 240, 240);
  color: black;
  font-size: 15px;
  font-weight: 500;
  border: none;
  border-radius: 5px;
}

.notification {
  display: flex;
  background-color: rgb(240, 240, 240);
  text-align: left;
  font-size: 15px;
  width: 100%;
  height: 40px;
  border: none;
  border-radius: 5px;
  vertical-align: middle;
}

.new {
  color: white;
  font-size: 15px;
  font-weight: 500;
  background-color: rgb(0, 171, 132);
}

.notification table {
  text-align: center;
  width: 100%;
}

.notification table tr td {
  height: 30px;
}

.notification table tr td:nth-child(2) {
  text-align: right;
}

.notification table tr td:nth-child(2) button {
  color: rgb(0, 171, 132);
}

.notification table tr td:nth-child(3) {
  padding-right: 10px;
  text-align: center;
}

.notification table tr td:nth-child(3) button {
  color: red;
}


.notification table button {
  background-color: rgb(240,240,240);
  font-weight: 600;
  border : none;
  border-radius: 5px;
}
.profile-image {
  background-color: white;
  border-radius: 70%;
}
</style>