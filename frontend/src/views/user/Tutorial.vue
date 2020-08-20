<template>
  <div class="tutorial wrap">
    <div class="tutorial-logo-wrap">
        <img src="../../assets/images/logo/logo_white.png">
    </div>
    <div class="tutorial-main-wrap"> 
        <div class="tutorial-input-wrap">
            <div>
                <a>관심있는 질병을 검색하여 1개 이상 구독을 눌러주세요!</a>
            </div>
            <input list="disease-list" id="keyword" v-model="keyword" v-on:keyup.enter="getDisease(keyword)">
            <img src="../../assets/images/icon/icon_search_unselect.png" @click="getDisease(keyword)">
        </div>
        <div class="tutorial-select-wrap">
            <a v-show="this.isSearched">추가 버튼을 누르시면 구독됩니다</a>
            <select v-model="nowItem" v-show="this.isSearched" id="disease-list" class="custom-select sources" required>
                <option value="" hidden>구독할 병명을 선택하세요</option>
                <option v-for="item in this.items" v-bind:key="item.sickCd" :value="item.sickCd + ':' + item.sickNm">{{item.sickNm}}</option>
            </select>
            <button v-show="this.isSearched" @click="checkItem(nowItem)">추가</button>
        </div>
        <div class="tutorial-show-wrap">
            <div v-for="cItem in this.checkedItems" v-bind:key="cItem.diseasecode">
                <span >{{cItem.diseasename}}<img @click="deleteItem(cItem)" src="../../assets/images/icon/icon_mini_close.png"></span>
            </div>
            
        </div>
    </div>
    <div class="button wrap">
        <button @click="onStart()" :disabled="!isSelected" :class="{disabled : !isSelected}">시작하기</button>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
import { mapState, mapActions } from "vuex";
const proxyurl = "https://cors-anywhere.herokuapp.com/";
export default {
    data() {
        return {
            keyword: "",
            nowItem: "",
            items: [],
            isSelected: false,
            isSearched: false,
            checkedItems : [],
        }
    },
    computed: {
    ...mapState('userStore', ['loginData']),
    },
    created() {
        document.body.className = "greenbody";
    },
    watch : {
        // keyword: function () {
        //     this.isSearched = false;
        //     this.getDisease(this.keyword);
        // },
        checkedItems : function () {
            this.checkForm();
        },
    },
    methods: {
        ...mapActions("diseaseStore", ["createDisease"]),
        checkForm() {
            // 현재 선택한 구독 질병의 갯수로 다음으로 넘어갈 수 있는지 체크
            if(this.checkedItems.length >= 1) {
                this.isSelected = true;
            } else {
                this.isSelected = false;
            }
        },
        getDisease (keyword) {
            // API에서 질병을 검색하고 셀렉트 박스로 보여줌
            var params = {
                pageNo : 1,
                numOfRows : 10,
                sickType : 1,
                medTp : 2,
                diseaseType : 'SICK_NM',
                searchText : keyword,
                ServiceKey : 'hhU4fvLXqUtlijp+SQxnotQgI7A4yLrBASX3GMofY45xyks9LOe05UKyCfH5gkyN1U+7YKFfujffwflXy4TzfA=='
            };
            this.$parent.isLoaded = false;
            axios.request({
                // 서버에 마운트하면서 바꿔야 할 부분 :
                // proxyurl 지우고 올려야 함
                // proxyurl은 일정 request 하면 자체 차단해버림
                url: proxyurl + `http://apis.data.go.kr/B551182/diseaseInfoService/getDissNameCodeList`,
                headers: {
                    'Access-Control-Allow-Origin' : '*',
                    'Content-Type' : 'application/xml',
                    'Access-Control-Max-Age': 600
                },
                params: params,
            })
            .then((res) => {
                this.items = [];
                var len = res.data.response.body.totalCount;
                var items = res.data.response.body.items.item;
                if(len == 0) {this.isSearched = false; this.$parent.isLoaded = true;return;}
                else if(len == 1) this.items.push(items);
                else this.items = items;
                this.isSearched = true;
                this.$parent.isLoaded = true;
            })
            .catch((err) => {
                this.isSearched = false;
                console.log(err)
                this.$parent.isLoaded = true;
            })
        },
        checkItem(now) {
            // 선택한 질병을 구독 설정함 (여기서 서버 DB로 보내는 것은 아님)
            var str = now.split(":");
            var flag = true;
            for(var i = 0; i < this.checkedItems.length; i++) {
                var item = this.checkedItems[i];
                if(item.diseasecode == str[0]) flag = false;
            }
            if(flag)
                this.checkedItems.push({diseasecode : str[0], diseasename : str[1]});
        },
        deleteItem(item) {
            // x표를 눌러 선택한 질병을 삭제하는 내용
            const idx = this.checkedItems.indexOf(item);
            if(idx > -1) this.checkedItems.splice(idx, 1);
        },
        onStart() {
            this.checkedItems.forEach(chkItem => {
                this.createDisease(chkItem); //db에 보내기
            });
            this.$router.push("/main/feed")
        }
    }
}
</script>

<style>
.tutorial.wrap {
  width: 100%;
  height: 100%;
}

.tutorial-logo-wrap {
  width: 100%;
  height: 60px;
}

.tutorial-logo-wrap img {
  height: 48px;
  margin-top: 6px;
}

.tutorial-main-wrap {
  margin-top: 40px;
  width: 100%;
  height: 60%;
}

.tutorial-input-wrap div a {
  color: white;
  font-size: 12px;
}

.tutorial-select-wrap {
  width: 100%;
  height: 100%;
  text-align: left;
}

.tutorial-select-wrap a {
  font-size: 10px;
  margin-left: 5%;
  color: slategray;
}

.tutorial-select-wrap select {
  width: 70%;
  height: 30px;
  margin-left: 5%;
  border: none;
  background-color: white;
  border-radius: 5px;
}

.tutorial-select-wrap select:focus {
  outline: none;
}

.tutorial-select-wrap button {
  background-color: white;
  color: rgb(0, 171, 132);
  font-weight: 600;
  margin-left: 2%;
  width: 18%;
  height: 30px;
  border: none;
  border-radius: 5px;
  outline: none;
}

.tutorial-show-wrap {
  width: 100%;
  margin-top: 7px;
}

.tutorial-show-wrap div {
  margin: 5px;
  height: 100%;
}

.tutorial-show-wrap div span {
  background-color: white;
  border: none;
  font-size: 14px;
  font-weight: 600;
  height: 20px;
  border-radius: 5px;
  color: black;
  display: inline-block;
  text-align: center;
}

.tutorial-show-wrap div img {
  display: inline-block;
  height: 10px;
  margin-left: 10px;
}

.button.wrap {
  position: absolute;
  bottom: 30px;
  width: 100%;
}

.button.wrap button {
  border: none;
  border-radius: 5px;
  width: 80%;
  height: 40px;
  color: black;
  font-size: 25px;
  font-weight: 600;
  transition-duration: 300ms;
}
.button.wrap button:disabled {
  background-color: rgb(200, 200, 200);
  color: white;
}

.button.wrap button:focus {
  outline: none;
  background-color: rgb(0, 171, 132);
  color: white;
}

#keyword {
  width: 87%;
  border: none;
  border-radius: 5px;
  height: 30px;
  padding-left: 3%;
  outline: none;
}

.tutorial-input-wrap img {
  position: absolute;
  width: 20px;
  height: 20px;
  right: 25px;
  margin-top: 5px;
}
</style>