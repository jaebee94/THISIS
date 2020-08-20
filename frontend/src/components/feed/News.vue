<template>
  <div class="news-wrap">
    <div class="news-search-input">
        <input v-model="keyword" placeholder="키워드를 입력하세요" v-on:keyup.enter="findNews(keyword)">
        <span><img @click="findNews(keyword)" src="../../assets/images/icon/icon_search_unselect.png"></span>
        <!-- <button >검색</button> -->
    </div>
    <div class="tutorial-show-wrap">
        <span :selected="!disease.isSelected" :class="{selected : disease.isSelected}" 
        v-for="disease in this.diseases" v-bind:key="disease.diseasecode" 
        @click="findNews(disease.diseasename) ">{{disease.diseasename}}</span>
    </div>
    <!-- <div v-if="sample_images.one"  class="news-search-image">
        <img :src="sample_images.one"> 
        <img :src="sample_images.two">
    </div> -->
    <div class="news-ment">
        <span>
            <img src="../../assets/images/icon/quot_open.png">
            <strong>{{selectedDisease}}</strong>
            <img src="../../assets/images/icon/quot_close.png">
            <a>에 대한</a>
            <br>
            <a>뉴스 기사입니다</a>
        </span>
        
    </div>
    <div class="news-content"> 
        <ul v-for="(item,idx) in items" v-bind:key="idx">
            <li><a @click="readNews(item.link)">{{item.title}}</a></li>
        </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapActions,mapState } from "vuex";
const proxyurl = "https://cors-anywhere.herokuapp.com/";

export default {
    name : 'news',
     computed: {
    ...mapState("userStore", ["loginData"]),
    ...mapState('diseaseStore', ['diseases']),
    },
    data() {
        return {
            keyword: "",
            items: [],
            selectedDisease:"",
            sample_images: [
                {one: ""},
                {two: ""},
            ],
        }
    },
    methods: {
         ...mapActions("diseaseStore", ["getFolloingwDisease"]),
        async findNews(keyword) {
            this.selectedDisease = keyword;
            this.$parent.$parent.$parent.isLoaded = false;
            this.selectedDisease = keyword
            this.diseases.forEach(disease => {
                disease.isSelected = false;
                if(this.selectedDisease == disease.diseasename){
                    disease.isSelected = true;
                }
            });
            
            var params = {
                query: keyword,
                display: 10,
                start: 1,
                sort: 'sim'
            }
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
                this.$parent.$parent.$parent.isLoaded = true;
            })
            .catch((err) => {
                console.error(err);
                this.$parent.$parent.$parent.isLoaded = true;
            })
        },
        readNews(link) {
            window.open(link,"_parent");
        }
    },
    created(){
    this.$store.dispatch("diseaseStore/getFollowingDisease");
    this.selectedDisease == this.diseases[0].diseasename //안됨
    this.findNews(this.diseases[0].diseasename)
  },
}
</script>

<style>
    .news-wrap {
        width: 100%;
        height: 100%;
    }

    .news-search-input {
        margin-top: 10px;
        width: 100%;
        height: 30px;
        margin-bottom: 10px;
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
        font-weight: 600;
        outline: none;
    }

    /* .news-search-input input:focus {
        background-color: rgb(0, 171, 132);
        color: white;
    } */

    .news-search-input span{
        position: relative;
        right: 8%;
        top: 5px;
    }

    .news-ment {
        /* background-color: rgba(250, 200, 200, 0.3); */
        margin-top: 20px;
        width: 90%;
        margin-left: 5%;
        padding: 5px 0px;
        /* border: none; */
        /* border-radius: 15px; */
        /* border-top: 3px slategray solid; */
        border-bottom: 3px slategray solid;
    }

    .news-ment img {
        width: 12px;
        height: 12px;
        position: relative;
        top: -5px;
        margin: 0 5px auto;
    }

    .news-ment strong {
        font-size: 20px;
        font-weight: 600;
    }

    .news-ment a {
        font-size: 13px;
        font-weight: 600;
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

    .tutorial-show-wrap {
        width: 100%;
        margin-top: -4px;
        /* margin-top: 3px; */
        display: inline-block;
    }

    .tutorial-show-wrap span {
        background-color: rgb(200, 200, 200);
        color: black;
        font-size: 13px;
        font-weight: 600;
        padding: 4px 8px;
        border: none;
        border-radius: 5px;
        display: inline-block;
        margin: 5px 5px auto;
    }

    .tutorial-show-wrap span.selected {
        background-color: rgb(0, 171, 132);
        color: white;
    }

</style>