import axios from 'axios'
import router from '@/router'
import SERVER from '@/api/RestApi.js'
const postStore = {
  namespaced: true,

  state: {
    comments: {},
    healths: {},
    checkScrap: 0,
  },

  getters: {
   },
  
  mutations: {
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
            axios.post(SERVER.URL + SERVER.ROUTES.posts, uploadData.postData, rootGetters.config)
              .then(() => {
                alert('작성이 완료되었습니다.')
                router.push({ name: 'Feed' })
              })
              .catch(err => console.log('게시글 작성 에러: ', err))
          })
          .catch(err => console.log('사진 업로드 에러: ', err))
      }
    },
    updatePost({ rootGetters }, postInfo) {
      axios.put(SERVER.URL + SERVER.ROUTES.modify + postInfo.user_id, postInfo, rootGetters.config)
        .then(() => {
          alert('변경이 완료되었습니다.')
        })
        .catch(err => console.log(err))
    },
    deletePost({ rootGetters }, postInfo) {
      if(postInfo.user_id != postInfo.postInfo.userinfo.user_id) {
        alert("너꺼 아니잖아");
        return;
      } else {
        var con = confirm("진짜 지울꺼야?");
        if(con){ 
          axios.delete(SERVER.URL + SERVER.ROUTES.post + postInfo.postInfo.posts_id ,rootGetters.config)
          .then(() => {
            alert('게시글이 삭제되었습니다.')
          })
          .catch(err => console.log(err))
        }
        else alert("안 지웠어")
      }
      
    },

    // Comment
    createComment({ commit, rootGetters }, commentData) {
      axios.post(SERVER.URL + SERVER.ROUTES.comment, commentData, rootGetters.config)
        .then(
          setTimeout(() => {
            axios.get(SERVER.URL + SERVER.ROUTES.comment + '/' + commentData.posts_id)
            .then(res => {  
              commit('SET_COMMENTS', res.data)
            })
          }, 100)
        )
    },
    fetchComments({ commit }, posts_id) {
      axios.get(SERVER.URL + SERVER.ROUTES.comment + '/' + posts_id)
        .then(res => {
          console.log(res.data)
          commit('SET_COMMENTS', res.data)
        })
    },
    updateComment({ rootGetters }, commentData) {
      console.log('server 요청 전', rootGetters)
      axios.put(SERVER.URL + SERVER.ROUTES.comment + '/' + commentData.comment_id, commentData, rootGetters.config)
      .then((res) => {console.log(res)})
      .catch((err) => {console.log(err)})
    },
    deleteComment() {     // 삭제 로직 개발 필요
    },

    // Health
    health({ rootGetters }, healthData) {
      axios.post(SERVER.URL + SERVER.ROUTES.health + `/${healthData.posts_id}`,healthData,rootGetters.config)
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
    scrap({rootGetters},params) {
      axios.post(SERVER.URL + SERVER.ROUTES.scrap, {
        posts_id: params.posts_id
      }, rootGetters.config)
    },
    deleteScrap({ rootGetters }, params){
      axios.delete(SERVER.URL + SERVER.ROUTES.scrap+`/${params.posts_id}`,rootGetters.config)
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
          }
        }, rootGetters.config)
        .then(res => {
          commit('SET_CHECK_SCRAPS', res.data);
        }).catch(err => console.log(err))
    },
    getUserScraps({ rootGetters, commit }, userId) {
      console.log("rootGetters.config",rootGetters.config)
      axios.get(SERVER.URL + SERVER.ROUTES.scrap + "/" + userId, rootGetters.config)
        .then(res => {
          commit('SET_SCRAPS', res.data);
        }).catch(err => console.log(err))
    },

    // Disease
    // createDisease() {
    //   axios.post(SERVER.URL + SERVER.ROUTES.disease, diseaseData, rootGetters.config)
    //     .then(() => {

    //     })
    // }
  }
}

export default postStore