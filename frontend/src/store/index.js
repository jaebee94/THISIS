import Vue from 'vue'
import Vuex from 'vuex'
import cookies from 'vue-cookies'

import postStore from './modules/postStore.js'
import profileStore from './modules/profileStore.js'
import userStore from './modules/userStore.js'
import followStore from './modules/followStore.js'
import notificationStore from './modules/notificationStore.js'
import diseaseStore from './modules/diseaseStore.js'
import adminStore from './modules/adminStore.js'


import axios from 'axios'
import SERVER from '@/api/RestApi.js'

import createPersistedState from 'vuex-persistedstate';


Vue.use(Vuex)

export default new Vuex.Store({

  state: {
    searchList: {},
  },

  getters: {
    config: () => ({ headers: { accessToken:  cookies.get('access-token') } }),
  },

  mutations: {
    SET_SEARCHLIST(state, searchList) {
      state.searchList = searchList
    }
  },

  actions: {
    getSearchList({ getters }, keyword) {
      axios.get(SERVER.URL + SERVER.ROUTES.searchUser, getters.config.headers, {
        params: {
          keyword: keyword,
        }
      })
        .then(res => {
          //commit('SET_SEARCHLIST', res)
          console.log(res)
        })
        .catch(err => console.log(err))
    },
  },

  modules: {
    postStore: postStore,
    profileStore: profileStore,
    userStore: userStore,
    followStore: followStore,
    notificationStore: notificationStore,
    diseaseStore: diseaseStore,
    adminStore: adminStore,
  },

  plugins: [
    createPersistedState()
  ]
})