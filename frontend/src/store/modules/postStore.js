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
    accessToken: cookies.get('access-token'),
  },

  getters: {
    config: state => ({ headers: { accessToken: `${state.accessToken}` } }),
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
    createPost({ getters }, postData) {
      axios.post(SERVER.URL + SERVER.ROUTES.posts, postData, getters.config)
        .then(() => {
          router.push({ name: 'Feed' })
        })
        .catch(err => console.log(err))
    },
    updatePost({ getters }, postInfo) {
      axios.put(SERVER.URL + SERVER.ROUTES.modify + postInfo.user_id, postInfo, getters.config)
        .then(() => {
          alert('변경이 완료되었습니다.')
        })
        .catch(err => console.log(err))
    },
    deletePost() {

    },

    // Comment
    createComment({ commit, getters }, commentData) {
      axios.post(SERVER.URL + SERVER.ROUTES.comment, commentData, getters.config)
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
    updateComment({ getters }, commentData) {
      axios.post(SERVER.URL + SERVER.ROUTES.comment + `/`, commentData, getters.config)
    },
    deleteComment() {     // 삭제 로직 개발 필요
    },

    // Health
    health({ getters }, healthData) {
      axios.post(SERVER.URL + SERVER.ROUTES.health + `/${healthData.posts_id}`, healthData, getters.config)
        .then(res => {
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
    scrap({getters},params) {
      axios.post(SERVER.URL + SERVER.ROUTES.scrap, {
        posts_id: params.posts_id,
        user_id: params.user_id
      }, getters.config)
    },
    deleteScrap({ getters }, params){
      axios.delete(SERVER.URL + SERVER.ROUTES.scrap+`/${params.posts_id}/${params.user_id}`,getters.config)
      .then(res => {
        console.log("result", res);
      }).catch(err => console.log(err))
    },
    getCheckScrap({ state, getters, commit }, posts_id) {
      axios.get(SERVER.URL + SERVER.ROUTES.scrap,
        {
          params: {
            posts_id: posts_id,
            user_id: state.loginData.user_id
          }
        }, getters.config)
        .then(res => {
          commit('SET_CHECK_SCRAPS', res.data);
        }).catch(err => console.log(err))
    },
    getUserScraps({ getters, commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.scrap + "/" + userId, getters.config)
        .then(res => {
          commit('SET_SCRAPS', res.data);
        }).catch(err => console.log(err))
    },
  }
}

export default postStore