<template>
    <div>
    <div> 
        <h1>NEWS API Test</h1>
    </div>
    <div> 
        <input v-model="keyword" id="keyword" placeholder="키워드">
        <button @click="findNews()">찾아보기</button>
        <ul v-for="item in items" v-bind:key="item">
            <li><a @click="readNews(item.link)">{{item.title}}</a></li>
        </ul>
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
            items:[],
        }
    },
    methods: {
        findNews() {
            console.log(this.keyword);
            var params = {
                query: this.keyword,
                display: 100,
                start: 1,
                sort: 'sim'
            }
            axios.request({
                url: proxyurl + 'https://openapi.naver.com/v1/search/news.json',
                headers: {
                    'X-Naver-Client-Id' : 'VmhwDszuy_Em4wjSyKBs',
                    'X-Naver-Client-Secret' : 'u1VmQ08Ai6'
                },
                params: params
            })
            .then((res) => {
                console.log(res.data.items);
                for(var item in res.data.items) {
                    item.title = item.title.replace(/(<([^>]+)>)/ig,"");
                }
                this.items = res.data.items;
            })
            .catch((err) => {
                console.error(err);
            })
        },
        readNews(link) {
            window.open(link);
        }
    }
}
</script>

<style>

</style>