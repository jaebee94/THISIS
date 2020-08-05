import axios from 'axios'
import router from '@/router'
import SERVER from '@/api/RestApi.js'

const postStore = {
  namespaced: true,

  state: {
    posts: {},
    comments: {},
    healths: {},
  },

  getters: {
  },
  
  mutations: {
    SET_POSTS(state, posts) {
      state.posts = posts
    },
    SET_COMMENTS(state, comments) {
      state.comments = comments
    },
    SET_HEALTHS(state, healths) {
      state.healths = healths
      console.log(healths)
    },
  },

  actions: {
    // Post
    createPost({ getters }, postData) {
      console.log(postData)
      console.log(getters.config)
      axios.post(SERVER.URL + SERVER.ROUTES.posts, postData, getters.config)
        .then(() => {
          router.push({ name: 'Feed' })
        })
        .catch(err => console.log(err))
    },
    fetchPosts({ commit }) {
      axios.get(SERVER.URL + SERVER.ROUTES.posts, {
        params: {
          num: 0
        }
      })
        .then(res => {
          commit('SET_POSTS', res.data)
          console.log(res.data.hits)
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
          console.log(res.data)
          commit('SET_COMMENTS', res.data)
        })
    },
    updateComment({ getters }, commentData) {
      axios.post(SERVER.URL + SERVER.ROUTES.comment + `/`, commentData, getters.config)
    },
    deleteComment() {

    },

    // Health
    health({ getters }, healthData) {
      console.log(SERVER.URL + SERVER.ROUTES.health + `/${healthData.posts_id}`)
      console.log('healthData', healthData)
      axios.post(SERVER.URL + SERVER.ROUTES.health + `/${healthData.posts_id}`, healthData, getters.config)
        .then(res => {
          console.log(res.data)
        })
    },
    fetchHealths({ commit }, posts_id) {
      axios.get(SERVER.URL + SERVER.ROUTES.healthCount + posts_id)
        .then(res => {
          console.log('health', res)
          commit('SET_HEALTHS', res.data)
        })
    },

    // Scrap
    createScrap({state,getters},post_id){
      axios.delete(SERVER.URL + SERVER.ROUTES.scrap+`/${post_id}/${state.loginData.user_id}`,getters.config)
      .then(res => {
        console.log("result", res);
      }).catch(err => console.log(err))
    },
    scrap({ state, getters }, post_id) {
      console.log(state.loginData);
      var data = {
        posts_id: post_id,
        user_id: state.loginData.user_id
      }
      console.log(data);
      axios.post(SERVER.URL + SERVER.ROUTES.scrap, data, getters.config)
    },
    deleteScrap({state,getters},post_id){
      axios.delete(SERVER.URL + SERVER.ROUTES.scrap+`/${post_id}/${state.loginData.user_id}`,getters.config)
      .then(res => {
        console.log("result", res);
      }).catch(err => console.log(err))
    },
  }
}

export default postStore