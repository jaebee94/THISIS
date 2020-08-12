import axios from 'axios'
import router from '@/router'
import SERVER from '@/api/RestApi.js'
import cookies from 'vue-cookies'
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
    SET_TOKEN(state, token) {
      cookies.set('access-token', token)
    },
  },

  actions: {
    // Post
    createPost({ rootGetters }, postData) {
      axios.post(SERVER.URL + SERVER.ROUTES.posts, postData, rootGetters.config)
        .then(() => {
          router.push({ name: 'Feed' })
        })
        .catch(err => console.log(err))
    },
    updatePost({ rootGetters }, postInfo) {
      axios.put(SERVER.URL + SERVER.ROUTES.modify + postInfo.user_id, postInfo, rootGetters.config)
        .then(() => {
          alert('변경이 완료되었습니다.')
        })
        .catch(err => console.log(err))
    },
    deletePost() {

    },

    // Comment
    createComment({ commit, rootGetters }, commentData) {
      console.log(commentData)
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
          commit('SET_COMMENTS', res.data)
        })
    },
    updateComment({ rootGetters }, commentData) {
      axios.post(SERVER.URL + SERVER.ROUTES.comment + `/`, commentData, rootGetters.config)
    },
    deleteComment() {     // 삭제 로직 개발 필요
    },

    // Health
    health({ rootGetters, commit }, healthData) {
      console.log(healthData)
      axios.post(SERVER.URL + SERVER.ROUTES.health + `/${healthData.posts_id}`,healthData,rootGetters.config)
        .then(res => {
          console.log(rootGetters.config)
          console.log(res.data)
          console.log(res)
        }).catch((error) =>{
          console.log("ERR!!" + error.response.status) 
          if(error.response.status == 401){ //인증 에러
            alert("인증에러")
            console.log(rootGetters.config)
           axios.get(SERVER.URL + SERVER.ROUTES.refreshAccessToken, rootGetters.config)
           .then(res => {//액세스 토큰만 만료
              console.log("new RES" + res.data.accessToken)
              commit('SET_TOKEN', res.data.accessToken)
              axios.post(SERVER.URL + SERVER.ROUTES.health + `/${healthData.posts_id}`,healthData, {
                headers : {
                  'accessToken' : res.data.accessToken
                }
              })
              .then(res =>{
                console.log(res.data)
              })
             })
             .catch() //리프레시 토큰 만료 -> LOGIN
          }
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
        posts_id: params.posts_id,
        user_id: params.user_id
      }, rootGetters.config)
    },
    deleteScrap({ rootGetters }, params){
      axios.delete(SERVER.URL + SERVER.ROUTES.scrap+`/${params.posts_id}/${params.user_id}`,rootGetters.config)
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
      axios.get(SERVER.URL + SERVER.ROUTES.scrap + "/" + userId, rootGetters.config)
        .then(res => {
          commit('SET_SCRAPS', res.data);
        }).catch(err => console.log(err))
    },
  }
}

export default postStore