<template>
  <div class="upload wrap">
    <div class="title-panel">
      <input v-model="posts_title" id="posts_title" placeholder="제목을 입력하세요" />
    </div>
    <div class="content-panel">
      <div class="content-checkbox-panel">
        <input type="radio" id="checkPost" name="category" :checked="isPost" @click="checkPost()"/>
        <label for="checkPost">포스트 게시물</label>
        <input type="radio" id="checkQnA" name="category" :checked="isQnA" @click="checkQnA()"/>
        <label for="checkQnA">Q&A 게시물</label>
      </div>
      <div class="content-image-panel"> 
        <img v-show="imgsrc != ''" :src="imgsrc">
      </div>
      <div> 
        <input ref="imageInput" type="file" hidden @change="onChangeImages">
      </div>
      <div class="upload-btn-panel">
        <button @click="onClickImageUpload">이미지 업로드</button>
      </div>
      <!-- 질병 태그하는 부분 START -->
      <div class="tutorial-input-wrap">
        <input list="disease-list" id="keyword" v-model="keyword" placeholder="태그할 질병을 검색하세요">
      </div>
      <div class="tutorial-select-wrap">
        <a v-show="this.isSearched">추가 버튼을 누르시면 태그됩니다</a>
        <select v-model="nowItem" v-show="this.isSearched" id="disease-list" class="custom-select sources" required>
          <option value="" hidden>태그할 질병을 선택하세요</option>
          <option v-for="item in this.items" v-bind:key="item.sickCd" :value="item.sickCd + ':' + item.sickNm">{{item.sickNm}}</option>
        </select>
        <button v-show="this.isSearched" @click="checkItem(nowItem)">추가</button>
      </div>
      <div v-if="checkedItem" class="tutorial-show-wrap">
        <div>
        <span v-if="checkedItem">{{checkedItem[1]}}<img @click="deleteItem()" src="../../assets/images/icon/icon_mini_close.png"></span>
        </div>
      </div>
      <!-- 질병 태그하는 부분 END -->
      <div class="custom-tag-wrap"> 
        <input v-model="customTag" placeholder="태그를 직접 입력하세요">
        <button @click="addTag">추가</button>
      </div>
      <div class="custom-tag-show">
        <span v-for="tag in this.tags" v-bind:key="tag">{{tag}} <img @click="deleteTag(tag)" src="../../assets/images/icon/icon_mini_close.png"></span>
      </div>
      <div class="post-content-panel">
        <textarea v-model="posts_main" id="posts_main" placeholder="내용을 입력하세요" />
      </div>


    </div>

    <div class="button-panel">
      <button @click="sendPhoto(), createPost(uploadData)" :disabled="!isFill" :class="{disabled : !isFill}">올리기</button>
      <!-- <button @click="createPost(postData)" :disabled="!isFill" :class="{disabled : !isFill}">올리기</button> -->
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import { mapActions } from "vuex";

const proxyurl = "https://cors-anywhere.herokuapp.com/";

export default {
  name: "Upload",
  data() {
    return {
      isPost: true,
      isQnA: false,
      posts_title: "",
      posts_main: "",
      file: null,
      uploadData: {
        formData: {},
        postData: {
          post: {
            category: 0,
            diseasecode: null,
            // diseasename: null,
            imgsrc: null,
            posts_main: null,
            posts_title: null,
          },
          tags: []
        },
      },
      isFill: false,
      imgsrc: null,

      keyword: "",
      nowItem: "",
      items: [],
      checkedItem: null,
      isSelected: false,
      isSearched: false,

      image: null,

      customTag: null,
      tags: [],
    };
  },
  watch: {
    posts_title: function () {
      this.checkForm();
    },
    posts_main: function () {
      this.checkForm();
    },
    keyword: function () {
      this.isSearched = false;
      this.getDisease(this.keyword);
    },
  },
  methods: {
    ...mapActions('postStore', ['createPost']),
    checkPost() {
      this.isPost = true;
      this.isQnA = false;
      this.uploadData.postData.post.category = 0
    },
    checkQnA() {
      this.isQnA = true;
      this.isPost = false;
      this.uploadData.postData.post.category = 1
    },
    checkForm() {
      if (
        this.posts_title.length > 0 &&
        this.posts_main.length > 0
      ) {
        this.isFill = true;
        this.uploadData.postData.post.posts_title = this.posts_title;
        this.uploadData.postData.post.posts_main = this.posts_main;
      } else {
        this.isFill = false;
      }
    },
    onClickImageUpload(){
      this.$refs.imageInput.click();
    },
    onChangeImages(e){
      const file = e.target.files[0];
      console.log(file)
/////////////////////////////////////////////////////////////////
      var formData = new FormData();
      formData.append('upload_file', file);
      this.uploadData.formData = formData
      // axios({
      //   method: 'post',
      //   url: 'http://4bc06cd8b4fc.ngrok.io/THISIS/articles/upload',
      //   data: formData,
      //   header: {
      //     'Accept': 'application/json',
      //     'Content-Type': 'multipart/form-data',
      //   },
      // })      
//////////////////////////////////////////////////////////////////
      this.imgsrc = URL.createObjectURL(file);
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
        if(len == 0) {this.isSearched = false;return;}
        else if(len == 1) this.items.push(items);
        else this.items = items;
        this.isSearched = true;
      })
      .catch((err) => {
        this.isSearched = false;
        console.log(err)
      })
    },
    checkItem(item) {
      this.checkedItem = item.split(":");
      this.uploadData.postData.post.diseasecode = this.checkedItem[0]
      // this.uploadData.postData.diseasename = this.checkedItem[1]
    },
    deleteItem() {
      // x표를 눌러 선택한 질병을 삭제하는 내용
      this.checkedItem = null;
    },
    addTag(){
      // 커스텀 태그를 추가하는 부분
      if(this.tags.length == 5) {
        alert("태그는 5개까지만 가능합니다");
        this.customTag = "";
        return;
      }
      for(var i = 0; i < this.tags.length; i++) {
        if(this.tags[i] == this.customTag) {
          alert("태그가 중복되었네요?")
          this.customTag = "";
          return;
        }
      }
      if(this.customTag.search(/\s/) != -1) {
        alert("태그에는 공백이 있으면 안되요!")
        this.customTag = "";
        return;
      }
      this.tags.push(this.customTag);
      this.uploadData.postData.tags = this.tags
      console.log(this.uploadData.postData)
      this.customTag = "";
    },
    deleteTag(tag) {
      // 커스탬 태그를 삭제하는 부분
      for(var i = 0 ; i < this.tags.length; i++) {
        if(this.tags[i] == tag) {
          this.tags.splice(i, 1);
          return;
        }
      }
    },
    sendPhoto() {
      console.log(this.$refs.imageInput);
      console.log(this.imgsrc);
    },
    // ...mapActions(["createPost"]),
  },
};
</script>

<style scoped>
.upload.wrap {
  width: 100%;
  height: 100%;
}
.title-panel {
  width: 90%;
  margin-top: 20px;
  margin-left: 5%;
}

.content-panel {
  width: 90%;
  background-color: rgb(200, 200, 200);
  margin-top: 20px;
  margin-left: 5%;
  border-radius: 5px;
}
/* ////////////////////////////////////////// */
.content-checkbox-panel {
  height: 40px;
  width: 100%;
}

.content-checkbox-panel input {
	position: absolute !important;
	clip: rect(0, 0, 0, 0);
	height: 1px;
	width: 1px;
	border: 0;
	overflow: hidden;
}

.content-checkbox-panel label {
	background-color: #e4e4e4;
	color: rgba(0, 0, 0, 0.6);
	font-size: 14px;
	line-height: 4;
	text-align: center;
	padding: 8px 16px;
	margin-right: -1px;
	border: 1px solid rgba(0, 0, 0, 0.2);
	/* box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.3), 0 1px rgba(255, 255, 255, 0.1); */
	transition: all 0.1s ease-in-out;
}

.content-checkbox-panel label:hover {
	cursor: pointer;
}

.content-checkbox-panel input:checked + label {
	background-color: rgb(0, 171, 132);
  font-weight: 600;
  color: white;
	box-shadow: none;
}

.content-checkbox-panel label:first-of-type {
	border-radius: 4px 0 0 4px;
}

.content-checkbox-panel label:last-of-type {
	border-radius: 0 4px 4px 0;
}

/* ////////////////////////////////////////// */
.title-panel input {
  width: 93%;
  height: 30px;
  padding-top: 5px;
  padding-bottom: 5px;
  padding-left: 7%;
  border: none;
  background-color: rgb(200, 200, 200);
  font-size: 20px;
  border-radius: 5px;
  outline: none;
  transition-duration: 300ms;
}

.title-panel input:focus {
  color: white;
  font-weight: 600;
  background-color: rgb(0, 171, 132);
  transition-duration: 300ms;
}

.content-image-panel {
  text-align: center;
  width : 100%;
}
.content-image-panel img {
  margin-top: 5%;
  width:90%;
}

.upload-btn-panel {
  width: 100%;
  margin-bottom: 10px;
}
.upload-btn-panel button {
  width: 90%;
  height: 30px;
  border: none;
  border-radius: 5px;
  color: white;
  background-color: rgb(0, 171, 132);
  font-size: 15px;
  font-weight: 600;
  outline: none;
}

.custom-tag-wrap { 
  width: 100%;
}

.custom-tag-wrap input {
  height: 30px;
  width: 67%;
  padding-left: 3%;
  border: none;
  border-radius: 5px;
  outline: none;
}

.custom-tag-wrap button {
  height: 30px;
  width: 18%;
  background-color: white;
  color: rgb(0, 171, 132);
  font-weight: 600;
  border: none;
  border-radius: 5px;
  outline: none;
  margin-left: 2%;
}

.custom-tag-show span {
  background-color: rgb(0, 171, 132);
  padding: 5px 10px;
  margin: 3px 5px auto;
  border: none;
  font-size: 11px;
  font-weight: 600;
  height: 15px;
  border-radius: 5px;
  color: white;
  display: inline-block;
}

.custom-tag-show img {
  display: inline-block;
  height: 10px;
  margin-left: 10px;
}

.post-content-panel {
  margin-top: 20px;
}

.post-content-panel textarea {
  width: 84%;
  height: 200px;
  background-color:rgb(247, 247, 247);
  border-radius: 10px;
  padding-top: 10px;
  padding-bottom: 10px;
  font-size: 15px;
  font-weight: 500;
  padding-left: 3%;
  padding-right: 3%;
  border: none;
  outline: none;
  margin-bottom: 5%;
}

.post-content-panel textarea:focus {
  font-weight: 600;
}

.button-panel {
  width: 90%;
  height: 40px;
  margin-left: 5%;
  margin-top: 10px;
  margin-bottom: 50px;
}

.button-panel button {
  width: 100%;
  height: 100%;
  background-color: rgb(247, 247, 247);
  color: black;
  font-size: 18px;
  font-weight: 600;
  border: none;
  border-radius: 5px;
  outline: none;
  transition-duration: 0.5s;
}

.button-panel button:enabled {
  background-color: rgba(0, 171, 132, 1);
  color: rgb(247, 247, 247);
}

#uploadImg {
  visibility: hidden;
}
</style>
