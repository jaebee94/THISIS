<template>
    <div>
    <div> 
        <h1>NEWS API Test</h1>
    </div>
    <img :src="this.imgsrc">
    <img :src="this.imgsrc2">
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
            imgsrc: "",
            imgsrc2: "",
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
            var params2 = {
                query: this.keyword,
                display: 2,
                start: 1,
                sort: 'sim',
                filter: 'small'
            }
            axios.request({
                url: proxyurl + 'https://openapi.naver.com/v1/search/image',
                headers: {
                    'X-Naver-Client-Id' : 'VmhwDszuy_Em4wjSyKBs',
                    'X-Naver-Client-Secret' : 'u1VmQ08Ai6'
                },
                params: params2
            })
            .then((res) => {
                console.log(res.data.items[0].link);
                this.imgsrc = res.data.items[0].link;
                this.imgsrc2 = res.data.items[1].link;
            })
            axios.request({
                url: proxyurl + 'https://openapi.naver.com/v1/search/news.json',
                headers: {
                    'X-Naver-Client-Id' : 'VmhwDszuy_Em4wjSyKBs',
                    'X-Naver-Client-Secret' : 'u1VmQ08Ai6'
                },
                params: params
            })
            .then((res) => {
                res.data.items.forEach((item) => {
                    item.description = String(item.description).replace(/<br\/>/ig, "\n");
                    item.description = String(item.description).replace(/&quot;/ig, "");
                    item.description = String(item.description).replace(/<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/ig, "");

                    item.title = String(item.title).replace(/<br\/>/ig, "\n");
                    item.title = String(item.title).replace(/&quot;/ig, "");
                    item.title = String(item.title).replace(/<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/ig, "");
                });
                this.items = res.data.items;
                console.log(res);
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