import axios from 'axios'
import router from '@/router'
import SERVER from '@/api/RestApi.js'


const profileStore = {
  namespaced: true,

  state: {
    profileData: {},
  },

  getters: {
   
  },

  mutations: {
    SET_USER_INFO(state, userInfo) {
      state.profileData.userInfo = userInfo
    },
    SET_PROFILE_INFO(state, profileInfo) {
      state.profileData.profileInfo = profileInfo
    },
    SET_POST_INFO(state, postInfo) {
      state.profileData.postInfo = postInfo
    },

  },

  actions: {
    async goProfile({ state, commit, rootGetters }, userId) {
      if (userId == null) {
        userId = state.loginData.user_id
      }
      await axios.get(SERVER.URL + SERVER.ROUTES.user + userId, rootGetters.config)
        .then(res => {          
          commit('SET_USER_INFO', res.data);
        })
        .catch(err => console.log(err))
      await axios.get(SERVER.URL + SERVER.ROUTES.profile + userId, rootGetters.config)
        .then(res => {
          commit('SET_PROFILE_INFO', res.data)
        })
        router.push({ name: 'Profile' })
    },

  }
}

export default profileStore