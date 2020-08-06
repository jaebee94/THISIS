<template>
  <div class="search wrap">
    <div class="tab-panel">
      <div @click="clickDisease()">질병</div>
      <div @click="clickUser()">사용자</div>
    </div>
    <div class="search-panel">
      <div @click="getSearchList(carName)">
        <input type="text" v-model="carName" placeholder="검색어를 입력하세요" />
      </div>
      <!-- <ul v-for="car in this.people" v-bind:key="car.name">
                <li v-if="car.name.includes(carName)">
                <i class = "searching">{{carName}}</i>{{car.name.replace(carName,'')}}
                </li>
      </ul>-->
    </div>
    <div id="search-main">
      <div class="search" v-for="user in this.people" v-bind:key="user.nickname">
        <div
          class="search-item"
          v-if="user.nickname.includes(carName) || user.username.includes(carName) || user.introduction.includes(carName)"
          @click="goProfile(user.user_id)"
        >
          <div class="search-img">
            <img src="../../assets/user.png" style="height:50px" />
          </div>
          <div class="search-text2">
            <div class="search-nickname">
              <span v-for="char in user.nickname" :key="char">
                <strong v-if="carName.includes(char)">{{ char }}</strong>
                <span v-if="!carName.includes(char)">{{ char }}</span>
              </span>
              <span>-</span>
              <span v-for="char in user.username" :key="char">
                <strong v-if="carName.includes(char)">{{ char }}</strong>
                <span v-if="!carName.includes(char)">{{ char }}</span>
              </span>
            </div>
            <div class="search-Introduction" v-if="user.introduction != null">{{user.introduction}}</div>
            <div class="search-Introduction" v-if="user.introduction == null">
              <br />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store/index.js";
import { mapActions } from "vuex";
export default {
  name: "Search",
  computed: {
    //...mapState(['searchList'])
  },
  data() {
    return {
      people: [],
      car: {
        name: "",
      },
      nickName: "",
    };
  },
  methods: {
    ...mapActions('profileStore', ['goProfile']),
    
    getSearchList(keyword) {
      axios
        .get("http://i3a301.p.ssafy.io:8088/THISIS/account/search", {
          params: {
            keyword: keyword,
          },
          headers: { ACCESS_TOKEN: store.state.accessToken },
        })
        .then((res) => {
          this.people = res.data;
        })
        .catch((err) => console.log(err));
    },
  },
};
</script>

<style scoped>
.search.wrap {
  text-align: center;
  background-color: white;
  padding-bottom: 20px;
}
.search-panel {
  width: 100%;
  height: 35px;
}
.tab-panel {
  display: flex;
  width: 100%;
}
.tab-panel div:nth-child(1) {
  float: left;
  background-color: rgb(247, 247, 247);
  width: 50%;
  font-size: 20px;
  color: rgb(0, 171, 132);
  font-weight: 600;
}
.tab-panel div:nth-child(2) {
  float: right;
  background-color: rgb(0, 171, 132);
  width: 50%;
  font-size: 20px;
  color: white;
  font-weight: 600;
}
.tab-panel div {
  padding: 4px;
  border-radius: 5px;
  border: none;
  outline: none;
}
.search-panel input {
  background-image: url("../../assets/images/icon/icon_search_unselect.png");
  background-size: 20px;
  background-repeat: no-repeat;
  background-position: 95% center;

  background-color: rgb(247, 247, 247);
  padding-left: 15px;
  margin-top: 5px;
  width: 90%;
  font-size: 15px;
  font-weight: 600;
  height: 30px;
  border: none;
  outline: none;
  border-radius: 3px;
}
.search-item {
  background-color: rgb(247, 247, 247);
  border-radius: 5px;
  border: none;
  transition-duration: 300ms;
}

.search-item:hover {
  background-color: rgb(0, 171, 132);
  color: white;
}

.search-panel input:focus {
  background-color: rgb(0, 171, 132);
  color: white;
}
.searching {
  font-weight: bold;
}
ul {
  list-style: none;
  padding-left: 0px;
}
.search-img {
  float: left;
  height: 80%;
  width: 60px;
  margin-right: 10px;
}

.search-img img {
  margin: 10% 10% auto;
  height: 80%;
}

.search-item {
  margin: 10px;
  clear: both;
}
.search-text2 {
  padding: 10px;
  text-align: left;
}
.search-nickname {
  font-size: 20px;
}
.search-Introduction {
  font-size: 14px;
}
</style>
