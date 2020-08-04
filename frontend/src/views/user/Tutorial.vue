<template>
  <div class="tutorial wrap">
    <div>
        <h1>TUTORIAL</h1>
    </div>
    <div> 
        <div>
            <input list="disease-list" id="keyword" v-model="keyword">
        </div>
        <div>
            <select v-model="nowItem" v-if="this.keyword != ''" id="disease-list" class="custom-select sources">
                <option v-for="item in this.items" v-bind:key="item.sickCd" :value="item.sickCd + ':' + item.sickNm">{{item.sickNm}}</option>
            </select>

        </div>
        
        <button @click="checkItem(nowItem)">추가</button>  
    </div>
    <div class="button wrap">
        <button @click="onStart()" :disabled="!isSelected" :class="{disabled : !isSelected}">시작하기</button>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
const proxyurl = "https://cors-anywhere.herokuapp.com/";
export default {
    data() {
        return {
            keyword: "",
            nowItem: "",
            items: [],
            isSelected: false,
            checkedItems : [],
        }
    },
    created() {
        document.body.className = "greenbody";
    },
    watch : {
        keyword: function () {
            this.getDisease(this.keyword);
        },
        checkedItems : function () {
            this.checkForm();
        },
    },
    methods: {
        checkForm() {
            if(this.checkedItems.length >= 3) {
                this.isSelected = true;
            }
        },
        getDisease (keyword) {
            var params = {
                pageNo : 1,
                numOfRows : 10,
                sickType : 1,
                medTp : 2,
                diseaseType : 'SICK_NM',
                searchText : keyword,
                ServiceKey : 'hhU4fvLXqUtlijp+SQxnotQgI7A4yLrBASX3GMofY45xyks9LOe05UKyCfH5gkyN1U+7YKFfujffwflXy4TzfA=='
            };
            axios.request({
                url: proxyurl + `http://apis.data.go.kr/B551182/diseaseInfoService/getDissNameCodeList`,
                headers: {
                    'Access-Control-Allow-Origin' : '*',
                    'Content-Type' : 'application/xml'
                },
                params: params
            })
            .then((res) => {
                this.items = [];
                // console.log("result : ", res);
                var len = res.data.response.body.totalCount;
                var items = res.data.response.body.items.item;
                if(len == 1) this.items.push(items);
                else this.items = items;
                // console.log(items);
                
                // console.log("로컬", this.items);
            })
            .catch((err) => console.log(err))
        },
        checkItem(now) {
            console.log(now);
            var str = now.split(":");
            this.checkedItems.push(str);
        },
        onStart() {
            console.log(this.checkedItems)
        }
    }
}
</script>

<style>
    .tutorial.wrap {
        width: 100%;
        height: 100%;
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
        background-color: rgb(0, 201, 142);
        color: white;
    }

    #keyword {
        width: 90%;
        border: none;
        border-radius: 5px;
        height: 30px;
    }
</style>