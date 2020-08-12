<template>
  <div class="news-wrap">
    <div class="news-search-input">
        <input v-model="keyword" placeholder="키워드를 입력하세요" v-on:keyup.enter="findNews">
        <span><img @click="findNews" src="../../assets/images/icon/icon_search_unselect.png"></span>
        <!-- <button >검색</button> -->
    </div>
    <div v-if="sample_images.one"  class="news-search-image">
        <img :src="sample_images.one"> 
        <img :src="sample_images.two">
    </div>
    <div class="news-content"> 
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
    name : 'news',
    data() {
        return {
            keyword: "",
            items: [],
            sample_images: [
                {one: ""},
                {two: ""},
            ],
        }
    },
    methods: {
        async findNews() {
            console.log(this.keyword);
            var params = {
                query: this.keyword,
                display: 10,
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
            await axios.request({
                url: proxyurl + 'https://openapi.naver.com/v1/search/image',
                headers: {
                    'X-Naver-Client-Id' : 'VmhwDszuy_Em4wjSyKBs',
                    'X-Naver-Client-Secret' : 'u1VmQ08Ai6'
                },
                params: params2
            })
            .then((res) => {
                console.log(res.data.items[0].link);
                this.sample_images.one = res.data.items[0].link;
                this.sample_images.two = res.data.items[1].link;
            })
            await axios.request({
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
    .news-wrap {
        width: 100%;
        height: 100%;
    }

    .news-search-input {
        margin-top: 5px;
        width: 100%;
        height: 30px;
    }

    .news-search-input input {
        width: 87%;
        margin-left: 4%;
        padding-left: 3%;
        height: 30px;
        border-radius: 5px;
        border: none;
        background-color: rgb(240, 240, 240);
        color: black;
        font-weight: 700;
        outline: none;
    }

    .news-search-input input:focus {
        background-color: rgb(0, 171, 132);
        color: white;
    }

    .news-search-input span{
        position: relative;
        right: 8%;
        top: 5px;
    }

    .news-search-input span img {
        height: 20px;
    }

    .news-search-image {
        width: 100%;
        height: 100px;
        margin-top: 20px;
        display: flex;

    }
    .news-search-image img {
        width: 45.5%;
        margin-left: 3%;
        border-radius: 5px;
        border: none;
        object-fit: scale-down;
    }

    .news-content ul {
        list-style: none;
        padding-left: 0;
    }
    
    .news-content li {
        color: slategray;
        font-size: 13px;
        font-weight: 600;
    }
</style>