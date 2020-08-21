<template>
  <div class="search wrap">
    <!-- 질병 설명 -->
    <div class="disease-wrap" v-if="!this.isDiseaseHidden">
      <div>
        <h2>{{this.selectedDisease.name}}</h2>
      </div>
      <!-- <div class="post-content">
        <textarea v-model="selectedDisease.description"></textarea>
      </div>-->
      <div class="post-content">
        <carousel class="carousel wrap" :per-page="1" v-bind:pagination-enabled="true">
          <slide v-for="item in searchedItems" v-bind:key="item.index" class="myslide">
            <div>
              <strong>{{item.title}}</strong>
            </div>
            <div>
              <img v-show="item.thumbnail != null" :src="item.thumbnail" />
            </div>
            <div>
              <a>{{item.description}}</a>
            </div>
            <div class="morebtn">
              <a @click="openLink(item.link)">
                <i style="color:skyblue">더보기</i>
              </a>
              <img src="../../assets/images/icon/icon_search_select.png" />
            </div>
          </slide>
        </carousel>
      </div>
      <div class="modify-footer">
        <img @click="close()" src="../../assets/images/icon/icon_close.png" />
      </div>
    </div>
    <div class="tab-container">
      <div
        class="tab"
        v-for="(tab, index) in tabs"
        v-bind:key="tab"
        v-bind:class="{active: currentTab === index}"
        @click="switchTab(index)"
      >
        <a>{{tab}}</a>
      </div>
    </div>

    <div class="search-panel">
      <div>
        <input
          type="text"
          v-model="keyword"
          placeholder="검색어를 입력하세요"
          v-on:keyup.enter="getSearchList(keyword)"
        />
        <img
          id="search"
          @click="getSearchList(keyword)"
          src="../../assets/images/icon/icon_search_unselect.png"
        />
      </div>
    </div>
    <div id="search-main">
      <div v-if="currentTab == 0">
        <div
          class="search-disease"
          v-for="(item,index) in this.items"
          v-bind:key="index"
          v-bind:disease="diseases"
          :value="item.sickCd + ':' + item.sickNm"
        >
          <div class="search-item">
            <div class="search-text2" @click="selectDisease(item)">{{item.sickNm}}</div>
            <div class="following-button">
              <button v-if="item.check" @click="deleteFollow(index)">팔로우 취소</button>
              <button
                v-else
                @click="createDisease({diseasename : item.sickNm,diseasecode : item.sickCd})"
              >팔로우</button>
            </div>
          </div>
        </div>
        <infinite-loading ref="infiniteLoading" @infinite="infiniteHandler">
          <div slot="spinner"> </div>
          <div slot="no-more">더 이상 게시글이 없습니다 :)</div>
          <div slot="no-results"> </div>
          <div slot="error" slot-scope="{ trigger }">
            Error message, click
            <a href="javascript:;" @click="trigger">here</a> to retry
          </div>
        </infinite-loading>
      </div>
      <div v-else-if="currentTab == 1">
        <user-list class="searchedUser" v-bind:users="this.users" v-bind:sort="2" v-bind:keyword="this.keyword"></user-list>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import axios from "axios";
import SERVER from "@/api/RestApi.js";
import cookies from "vue-cookies";
import { Carousel, Slide } from "vue-carousel";
const proxyurl = "https://cors-anywhere.herokuapp.com/";
export default {
  name: "Search",
  components: {
    Carousel,
    Slide,
  },
  computed: {
    ...mapState("diseaseStore", ["diseases"]),
    ...mapState("userStore", ["loginData"]),
  },
  data() {
    return {
      tabs: ["질병", "사용자"],
      keyword: "",
      currentTab: 0,
      users: "",
      nickName: "",
      isSearched: false,
      items: [],
      check: false,
      selectedDisease: {
        name: "",
        description: "",
      },
      searchedItems: [],
      isDiseaseHidden: true,
      page:0
    };
  },
  watch: {
    items: function () {
      this.checkout(this.items);
    },
    diseases: function () {
      this.checkout(this.items);
    },
  },
  created() {
    this.page=0
    this.$store.dispatch("diseaseStore/getFollowingDisease");
  },
  methods: {
    ...mapActions("userStore", ["goProfile"]),
    ...mapActions("diseaseStore", [
      "getFolloingwDisease",
      "createDisease",
      "deleteDisease",
    ]), //add와 딜리트 할때마다 내부에서 disease업데이트함
    getSearchList(keyword) {
      if (this.currentTab == 0) {
        this.page=1;
        this.items=[];
        this.$refs.infiniteLoading.stateChanger.reset();
      } else {
        this.getUsers(keyword);
      }
    },
    getUsers(keyword) {
      this.$parent.$parent.isLoaded = false;
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
          this.$parent.$parent.isLoaded = true;
        })
        .catch((err) => console.log(err));
    },
    async infiniteHandler($state) {

      if(this.page == 0) return;
      this.$parent.$parent.isLoaded = false;
      // API에서 질병을 검색하고 셀렉트 박스로 보여줌
      var params = {
        pageNo: this.page,
        numOfRows: 10,
        sickType: 1,
        medTp: 2,
        diseaseType: "SICK_NM",
        searchText: this.keyword,
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
          var items = res.data.response.body.items.item;
          if(items.length){           
            this.isSearched = true;
            this.$parent.$parent.isLoaded = true;
            this.items.push(...items);
            $state.loaded();
            this.page += 1;
          }
          else if(typeof(items)=='object'){
            this.isSearched = false;
            this.$parent.$parent.isLoaded = true;
            this.items.push(items)
            $state.complete();
          }
          else{
            this.isSearched = false;
            this.$parent.$parent.isLoaded = true;
            $state.complete();
          } 
          
        })
        .catch((err) => {
          this.isSearched = false;
          this.$parent.$parent.isLoaded = true;
          console.log(err);
        });
    },
    checkout(items) {
      items.forEach((item) => {
        this.checkFollow(item);
      });
    },
    checkFollow(item) {
      //내가 갖고있는 질병 팔로우목록중 해당 아이템이 포함되어있나.
      this.diseases.forEach((disease) => {
        if (disease.diseasecode == item.sickCd) {
          this.check = true;
          item.check = true;
        }
      });
      this.check = false;
    },
    selectDisease(disease) {
      this.$parent.$parent.isHidden = true;
      this.isDiseaseHidden = false;
      this.selectedDisease.name = disease.sickNm;
      this.findDisease(disease.sickNm);
    },
    async findDisease(disease) {
      this.searchedItems = [];
      this.selectedDisease.description = "";
      this.$parent.$parent.isLoaded = false;
      var params = {
        query: disease,
        display: 10,
        start: 1,
      };
      await axios
        .request({
          url: proxyurl + "https://openapi.naver.com/v1/search/encyc.json",
          headers: {
            "X-Naver-Client-Id": "fTUNPODC3LOXXIgFgqfZ",
            "X-Naver-Client-Secret": "xqqkXHlwzG",
          },
          params: params,
        })
        .then((res) => {
          res.data.items.forEach((item) => {
            item.description = String(item.description).replace(
              /<br\/>/gi,
              "\n"
            );
            item.description = String(item.description).replace(/&quot;/gi, "");
            item.description = String(item.description).replace(
              /<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/gi,
              ""
            );

            item.title = String(item.title).replace(/<br\/>/gi, "\n");
            item.title = String(item.title).replace(/&quot;/gi, "");
            item.title = String(item.title).replace(
              /<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/gi,
              ""
            );

            this.searchedItems.push(item);
            // this.selectedDisease.description += item.description+"\n";
            // item.title = String(item.title).replace(/<br\/>/ig, "\n");
            // item.title = String(item.title).replace(/&quot;/ig, "");
            // item.title = String(item.title).replace(/<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/ig, "");
          });
          //this.selectedDisease.description = res.data.items;

          this.$parent.$parent.isLoaded = true;
        })
        .catch((err) => {
          console.error(err);
          this.$parent.$parent.isLoaded = true;
        });
    },
    switchTab(index) {
      this.currentTab = index;
      this.keyword = "";
    },
    close() {
      this.$parent.$parent.isHidden = false;
      this.isDiseaseHidden = true;
    },
    openLink(link) {
      window.open(link, "_parent");
    },
    // createFollow(idx){
    //   var item = this.items[idx]
    //   console.log(item)
    //   this.createDisease()
    //   this.items[idx].check=true;
    //
    // },
    async deleteFollow(idx) {
      var item = this.items[idx];
      this.items[idx].check = false;
      await this.deleteDisease(item.sickCd);
      
    },
  },
};
</script>

<style scoped>
.search.wrap {
  text-align: center;
  background-color: white;
}
.search-panel {
  width: 100%;
  height: 35px;
  margin-bottom: 10px;
}

.search-panel #search {
  height: 20px;
  position: absolute;
  margin-top: 10px;
  margin-left: -30px;
  /* left: -30px; */
  /* top: 3px;
  left: -40px;
  top: 3px; */
}

.tab {
  width: 50%;
  height: 40px;
  vertical-align: middle;
  border-radius: 0;
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
  background-color: rgb(247, 247, 247);
  border-bottom: 3px rgb(0, 171, 132) solid;
}
.disease-wrap {
  position: fixed;
  z-index: 98;
  top: 10%;
  width: 92%;
  height: 70%;
  background-color: white;
  border-radius: 5px;
  margin: auto 4%;
}

.post-content {
  width: 100%;
  max-height: 100%;
}

.post-content .carousel.wrap {
  width: 100%;
  height: 80%;
}

.post-content img {
  max-height: 70%;
  max-width: 80%;
}

.myslide strong {
  font-weight: 600;
}

.VueCarousel-wrapper {
  height: 70%;
}

.myslide div:nth-child(3) {
  padding-left: 10%;
  padding-right: 10%;
}

.myslide div:nth-child(4) {
  padding-left: 10%;
  padding-right: 10%;
}

/* .post-content textarea {
  padding: 5px 5px;
  font-size: 20px;
  margin-top: 10px;
  width: 90%;
  height: 350px;
  word-wrap: break-word;
  transition-duration: 300ms;
  border: none;
}
.post-content textarea:focus {
  outline: none;
  border: rgb(0, 171, 132) 3px solid;
} */
.modify-footer {
  position: absolute;
  width: 100%;
  height: 10%;
  bottom: -12%;
}

.modify-footer img {
  height: 80%;
}

.search-panel input {
  /* background-image: url("../../assets/images/icon/icon_search_unselect.png");
  background-size: 20px;
  background-repeat: no-repeat;
  background-position: 95% center; */

  background-color: rgb(247, 247, 247);
  padding-left: 3%;
  padding-right: 3%;
  margin-top: 5px;
  width: 84%;
  font-size: 15px;
  font-weight: 600;
  height: 30px;
  border: none;
  outline: none;
  border-radius: 3px;
}

.search-item {
  width: 90%;
  background-color: rgb(247, 247, 247);
  border-radius: 5px;
  border: none;
  transition-duration: 300ms;
  margin: 10px auto 0 auto;
  clear: both;

  display: flex;
  align-items: center;
  justify-content: center;
}

.search-item:hover {
  background-color: rgb(0, 171, 132);
  color: white;
}

/* .search-user-panel:hover {
  background-color: red;
} */

.search-user-panel {
  background-color: orange;
}

.search-item-user {
  width: 70%;
  height: 20px;
  background-color: orange;
}

.search-item-user {
  width: 90%;
  height: 20%;
}

.search-item-user:hover {
  background-color: rgb(0, 171, 132);
  color: red;
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
  margin-left: 10px;
  margin-right: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.search-img img {
  margin: 10% 10% auto;
  height: 80%;
  width: 80%;
  border-radius: 70%;
  object-fit: cover;
  /* background-size: cover; */
}

.search-text2 {
  padding: 10px;
  text-align: left;
  width: 60%;
  float: left;
  height: inherit;
  word-break: break-all;
}

.following-button {
  width: 30%;
  text-align: center;

  float: right;
}
.search-nickname {
  font-size: 18px;
}

.search-Introduction {
  font-size: 12px;
  color: slategray;
}

.search-item button {
  background-color: rgb(0, 171, 132);
  border: none;
  border-radius: 5px;
  width: 80px;

  padding: 5px;
  margin: 0 auto;
}
.searchU {
  width: 90%;
  margin-left: 5%;
  margin-top: 10px;
  height: 65px;
  border: none;
  border-radius: 5px;
  background-color: rgb(247, 247, 247);
}

.searchU:hover {
  background-color: rgb(0, 171, 132);
  color: white;
}

.searchU:hover .search-Introduction {
  color: rgb(247, 247, 247);
}

.searchU:last-child {
  margin-bottom: 30px;
}

.search-textU {
  padding: 10px;
  text-align: left;
  width: 70%;
  float: left;
  height: inherit;
  word-break: break-all;
}
.morebtn {
  height: 30px;
  float: right;
}

.doctor-image {
  width: 15px;
  height: 15px;
}

.searchedUser:last-child {
  margin-bottom: 50px;
}

</style>
