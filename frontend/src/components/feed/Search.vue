<template>
  <div class="search wrap">
    <div class="tab-container">
      <div
        class="tab"
        v-for="(tab, index) in tabs"
        v-bind:key="tab"
        v-bind:class="{active: currentTab === index}"
        @click="currentTab = index"
      >
        <h3>{{tab}}</h3>
      </div>
    </div>

    <div class="search-panel">
      <div @click="getSearchList(keyword)">
        <input type="text" v-model="keyword" placeholder="검색어를 입력하세요" />
      </div>
    </div>
    <div id="search-main">
      <div v-show="currentTab == 0">
        <div class="search" v-for="item in this.items" v-bind:key="item.sickCd" :value="item.sickCd + ':' + item.sickNm">
          <div class="search-item" >
             <div class="search-text2">{{item.sickNm}}</div> 
             <button v-if = checkFollow(item) @click="deleteDisease(item.sickCd)"  > 팔로우 취소</button>
             <button v-else  @click="addDisease({ diseasecode : item.sickCd,  diseasename : item.sickNm})">팔로우</button>
          </div>         
        </div>
      </div>
      <div v-show="currentTab == 1">
        <div class="search" v-for="user in this.users" v-bind:key="user.nickname">
          <div
            class="search-item"
            v-if="user.nickname.includes(keyword) || user.username.includes(keyword) || user.introduction.includes(keyword)"
            @click="goProfile(user.user_id)"
          >
            <div class="search-img">
              <img src="../../assets/user.png" style="height:50px" />
            </div>
            <div class="search-text2">
              <div class="search-nickname">
                <span v-for="char in user.nickname" :key="char">
                  <strong v-if="keyword.includes(char)">{{ char }}</strong>
                  <span v-if="!keyword.includes(char)">{{ char }}</span>
                </span>
                <span>-</span>
                <span v-for="char in user.username" :key="char">
                  <strong v-if="keyword.includes(char)">{{ char }}</strong>
                  <span v-if="!keyword.includes(char)">{{ char }}</span>
                </span>
              </div>
              <div
                class="search-Introduction"
                v-if="user.introduction != null"
              >{{user.introduction}}</div>
              <div class="search-Introduction" v-if="user.introduction == null">
                <br />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions,mapState } from "vuex";
import axios from "axios";
import SERVER from "@/api/RestApi.js";
import cookies from "vue-cookies";
const proxyurl = "https://cors-anywhere.herokuapp.com/";
export default {
  name: "Search",
  computed: {
    ...mapState('diseaseStore', ['diseases']),
  },
  data() {
    return {
      tabs: ["질병", "사용자"],
      keyword: "",
      currentTab: 0,
      users: "",
      nickName: "",
      isSearched: false,
      checkedItems: [],
      items: [],
      nowItem: "",
    };
  },
  watch: {
    keyword: function () {
      if (this.currentTab == 0) {
        this.getDisease(this.keyword);
      } else {
        this.getUsers(this.keyword);
      }
    },
  },
  methods: {
    ...mapActions("profileStore", ["goProfile"]),
     ...mapActions("diseaseStore", ["getFolloingwDisease","addDisease","deleteDisease"]),
    getSearchList(keyword) {
      if (this.currentTab == 0) {
        this.getDisease(keyword);
      } else {
        this.getUsers(keyword);
      }
    },
    getUsers(keyword) {
      axios
        .get(
          SERVER.URL + SERVER.ROUTES.search,
          {
            params: {
              keyword: keyword,
            },
          },
          { headers: { accessToken: cookies.get("access-token") } }
        )
        .then((res) => {
          this.users = res.data;
          console.log(res.data);
        })
        .catch((err) => console.log(err));
    },
    getDisease(keyword) {
      // API에서 질병을 검색하고 셀렉트 박스로 보여줌
      var params = {
        pageNo: 1,
        numOfRows: 10,
        sickType: 1,
        medTp: 2,
        diseaseType: "SICK_NM",
        searchText: keyword,
        ServiceKey:
          "hhU4fvLXqUtlijp+SQxnotQgI7A4yLrBASX3GMofY45xyks9LOe05UKyCfH5gkyN1U+7YKFfujffwflXy4TzfA==",
      };
      axios
        .request({
          // 서버에 마운트하면서 바꿔야 할 부분 :
          // proxyurl 지우고 올려야 함
          // proxyurl은 일정 request 하면 자체 차단해버림
          url:
            proxyurl +
            `http://apis.data.go.kr/B551182/diseaseInfoService/getDissNameCodeList`,
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/xml",
            "Access-Control-Max-Age": 600,
          },
          params: params,
        })
        .then((res) => {
          this.items = [];
          var len = res.data.response.body.totalCount;
          var items = res.data.response.body.items.item;
          if (len == 0) { //찾은게 있음
            this.isSearched = false;
            return;
          } else if (len == 1) this.items.push(items);
          else this.items = items;
          this.isSearched = true;
        })
        .catch((err) => {
          this.isSearched = false;
          console.log(err);
        });
    },
    checkFollow(item){ //내가 갖고있는 질병 팔로우목록중 해당 아이템이 포함되어있나.
      this.diseases.forEach(disease => {
        if(disease.diseasecode===item.sickNm) return true
      });
      return false
    }
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
.tab {
  width: 50%;
  height: 40px;
  vertical-align: middle;
  background-color: rgb(247, 247, 247);
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}
.tab h3 {
  margin-top: 10px;
  height: 20px;
}
.tab.active {
  background-color: rgb(0, 171, 132);
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
  width : 70%;
  float: left;
  height : inherit;
}
.search-nickname {
  font-size: 20px;
}
.search-Introduction {
  font-size: 14px;
}

button {
  background-color: rgb(0, 171, 132);
  border : none;
  border-radius: 5px;
  width : 80px;
  
  padding : 5px;
  margin-top : 10px;
  margin-bottom : 10px;
}
</style>
