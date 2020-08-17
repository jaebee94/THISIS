import axios from 'axios'
import router from '@/router'
import SERVER from '@/api/RestApi.js'
import cookies from 'vue-cookies'
const postStore = {
  namespaced: true,

  state: {
    post: {},
    comments: {},
    healths: {},
    checkScrap: 0,
  },

  getters: {

  },

  mutations: {
    SET_POST(state, post) {
      state.post = post
    },
    SET_COMMENTS(state, comments) {
      state.comments = comments
    },
    SET_HEALTHS(state, healths) {
      state.healths = healths
    },
    SET_CHECK_SCRAPS(state, checkScrap) {
      state.checkScrap = checkScrap
    },
    SET_SCRAPS(state, scraps) {
      state.profileData.scrapInfo = scraps
    },
  },

  actions: {
    // Post
    createPost({ rootGetters }, uploadData) {
      console.log(uploadData)
      if (uploadData.formData == null) {
        let headers = rootGetters.config.headers
        headers['Accept'] = 'application/json'
        console.log('headers', headers)
        axios.post(SERVER.URL + SERVER.ROUTES.posts, uploadData.postData, rootGetters.config)
          .then(() => {
            alert('작성이 완료되었습니다.')
            router.push({ name: 'Feed' })
          })
          .catch(err => console.log('게시글 작성 에러: ', err))
      } else {

        axios.post(SERVER.URL + SERVER.ROUTES.upload, uploadData.formData, {
          header: {
            'Accept': 'application/json',
            'Content-Type': 'multipart/form-data',
          },
        })
          .then(res => {
            uploadData.postData.post.imgsrc = res.data
            console.log(uploadData.postData)
            axios.post(SERVER.URL + SERVER.ROUTES.posts, uploadData.postData,
              {headers: { accessToken:  cookies.get('access-token') }})
              .then(() => {
                alert('작성이 완료되었습니다.')
                router.push({ name: 'Feed' })
              })
              .catch(err => console.log('게시글 작성 에러: ', err))
          })
          .catch(err => console.log('사진 업로드 에러: ', err))
      }
    },
    setPost({ commit }, postInfo) {
      commit('SET_POST', postInfo);
    },
    updatePost({rootGetters},uploadData) {
      console.log("updatePost", uploadData)

      if (uploadData.formData == null) {
        axios.put(SERVER.URL + SERVER.ROUTES.post, 
          uploadData.postData,
          rootGetters.config
          )
          .then(() => {
            alert('게시글 변경이 완료되었습니다.')
            router.push({ name: 'Feed' })
          })
          .catch(err => console.log(err))
      } else {

        axios.post(SERVER.URL + SERVER.ROUTES.upload, uploadData.formData, {
          header: {
            'Accept': 'application/json',
            'Content-Type': 'multipart/form-data',
          },
        })
          .then(res => {
            uploadData.postData.post.imgsrc = res.data
            console.log(uploadData.postData)
            axios.put(SERVER.URL + SERVER.ROUTES.post, 
              uploadData.postData,
              rootGetters.config
              )
              .then(() => {
                alert('게시글 변경이 완료되었습니다.')
                router.push({ name: 'Feed' })
              })
              .catch(err => console.log(err))
          })
          .catch(err => console.log('사진 업로드 에러: ', err))
      }
     
    },
    deletePost({ rootGetters }, posts_id) {
        var con = confirm("진짜 지우시겠습니까?");
        if (con) {
          axios.delete(SERVER.URL + SERVER.ROUTES.post + `/${posts_id}`, rootGetters.config)
            .then(() => {
              alert('게시글이 삭제되었습니다.')
            })
            .catch(err => console.log(err))
        }     

    },

    // Comment
    createComment({ commit, rootGetters }, commentData) {
      console.log("createComment - commentData", commentData)
      if(commentData.comment_main == null || commentData.comment_main == "") {
        alert("댓글을 입력해주세요")
      }else{
        axios.post(SERVER.URL + SERVER.ROUTES.comment, commentData, rootGetters.config)
        .then(
          setTimeout(() => {
            axios.get(SERVER.URL + SERVER.ROUTES.comment + '/' + commentData.posts_id)
              .then(res => {
                commit('SET_COMMENTS', res.data)
              })
          }, 100)
        )
      }
      
    },
    fetchComments({ commit }, posts_id) {
      console.log('여기가 문제?')
      axios.get(SERVER.URL + SERVER.ROUTES.comment + '/' + posts_id)
        .then(res => {
          console.log('fetchComments', res.data)
          commit('SET_COMMENTS', res.data)
        })
    },
    updateComment({ rootGetters }, commentData) {
      console.log('server 요청 전', rootGetters)
      axios.put(SERVER.URL + SERVER.ROUTES.comment + '/' + commentData.comment_id, commentData, rootGetters.config)
        .then((res) => { console.log(res) })
        .catch((err) => { console.log(err) })
    },
    deleteComment({ rootGetters,dispatch }, commentData) {     // 삭제 로직 개발 필요
      axios.delete(SERVER.URL + SERVER.ROUTES.comment + `/${commentData.comment_id}`, rootGetters.config)
      .then(res => {
        console.log("result", res);
        dispatch('fetchComments',commentData.posts_id)
      }).catch(err => console.log(err))
    },

    // Health
    health({ rootGetters }, healthData) {
      axios.post(SERVER.URL + SERVER.ROUTES.health + `/${healthData.posts_id}`, healthData, rootGetters.config)
        .then(res => {
          console.log(rootGetters.config)
          console.log(res.data)
        })
    },
    fetchHealths({ commit }, posts_id) {
      axios.get(SERVER.URL + SERVER.ROUTES.healthCount + posts_id)
        .then(res => {
          commit('SET_HEALTHS', res.data)
        })
    },

    // Scrap
    scrap({ rootGetters }, params) {
      axios.post(SERVER.URL + SERVER.ROUTES.scrap, {
        posts_id: params.posts_id
      }, rootGetters.config)
    },
    deleteScrap({ rootGetters }, params) {
      axios.delete(SERVER.URL + SERVER.ROUTES.scrap + `/${params.posts_id}`, rootGetters.config)
        .then(res => {
          console.log("result", res);
        }).catch(err => console.log(err))
    },
    getCheckScrap({ state, rootGetters, commit }, posts_id) {
      axios.get(SERVER.URL + SERVER.ROUTES.scrap,
        {
          params: {
            posts_id: posts_id,
            user_id: state.loginData.user_id
          },
          headers: rootGetters.config.headers
        })
        .then(res => {
          commit('SET_CHECK_SCRAPS', res.data);
        }).catch(err => console.log(err))
    },
    getUserScraps({ rootGetters, commit }, userId) {
      console.log("rootGetters.config", rootGetters.config)
      axios.get(SERVER.URL + SERVER.ROUTES.scrap + "/" + userId, rootGetters.config)
        .then(res => {
          commit('SET_SCRAPS', res.data);
        }).catch(err => console.log(err))
    },
    deleteFile({ rootGetters }, deletefile) {
      axios.delete(SERVER.URL + SERVER.ROUTES.upload,
        {
          params: { delete_file: deletefile },
          headers:rootGetters.config.headers
        })
        .then(res => {
          console.log("result", res);
        }).catch(err => console.log(err))
    },
    deleteTagRelation({ rootGetters }, params) {

      axios.delete(SERVER.URL + SERVER.ROUTES.tagrelation, 
        {
          data: params,
          headers:rootGetters.config.headers
        })
        .then(res => {
          console.log("result", res);
        }).catch(err => console.log(err));

    },
    //신고하기
    CreatePolice({rootGetters},policeData){
      axios.post(SERVER.URL + SERVER.ROUTES.police, policeData, rootGetters.config)
          .then(() => {
            alert('신고가 완료되었습니다.')
          })
          .catch(err => console.log('신고 에러: ', err))
    }

  }
}

export default postStore