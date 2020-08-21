<template>
  <div>
    <div> 
        <h1>Disease API Test</h1>
    </div>
    <div> 
        <input v-model="keyword" id="keyword" placeholder="키워드">
        <button @click="findDisease()">찾아보기</button>
        <ul v-for="item in items" v-bind:key="item">
            <li><a @click="chartDisease(item.sickCd)">{{item.sickNm}}</a></li>
        </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
const proxyurl = "https://cors-anywhere.herokuapp.com/";
export default {
    data () {
        return {
            keyword: "장염",
            items: []
        }
    },
    methods : {
        findDisease() {
            var instance = {
                pageNo : 1,
                numOfRows : 10,
                sickType : 1,
                medTp : 2,
                diseaseType : 'SICK_NM',
                searchText : this.keyword,
                ServiceKey : 'hhU4fvLXqUtlijp+SQxnotQgI7A4yLrBASX3GMofY45xyks9LOe05UKyCfH5gkyN1U+7YKFfujffwflXy4TzfA=='
            };
            axios.request({
                url: proxyurl + `http://apis.data.go.kr/B551182/diseaseInfoService/getDissNameCodeList`,
                headers: {
                    'Access-Control-Allow-Origin' : '*',
                    'Content-Type' : 'application/xml'
                },
                params: instance
            })
            .then((res) => {
                var items = res.data.response.body.items.item;
                this.items = items;
            })
            .catch((err) => console.log(err))
        },
        chartDisease(code) {
            var instance = {
                pageNo: 1,
                numOfRows: 10,
                sickType: 1,
                medTp : 2,
                year: 2014,
                sickCd: code,
                ServiceKey : 'hhU4fvLXqUtlijp+SQxnotQgI7A4yLrBASX3GMofY45xyks9LOe05UKyCfH5gkyN1U+7YKFfujffwflXy4TzfA=='
            }
            axios.request({
                url: proxyurl + `http://apis.data.go.kr/B551182/diseaseInfoService/getDissByGenderAgeStats`,
                headers: {
                    'Access-Control-Allow-Origin' : '*',
                    'Content-Type' : 'application/xml'
                },
                params: instance
            })
            .then((res) => {
                var items = res.data.response.body.items.item;
                console.log(items);
            })
            .catch((err) => console.log(err));
        }
    }
}
</script>

<style>

</style>