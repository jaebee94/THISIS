import Vue from 'vue'
import Vuex from 'vuex'

import postStore from './modules/postStore.js'
import profileStore from './modules/profileStore.js'
import userStore from './modules/userStore.js'

import axios from 'axios'
import SERVER from '@/api/RestApi.js'

import createPersistedState from 'vuex-persistedstate';


Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    profileData: {
      userInfo: {},
      profileInfo: {},
      postInfo: {},
      scrapInfo: {},
    },
    notification: {
      notification: {},
      requests: {}
    },
    checkScrap: 0,
    followee_list: false,
    searchList: {},
  },

  getters: {
  },

  mutations: {
    SET_CHECK_SCRAPS(state, checkScrap) {
      state.checkScrap = checkScrap
    },
    SET_FOLLOWEE(state, followee) {
      state.followee_list = followee;
    },
    SET_SEARCHLIST(state, searchList) {
      state.searchList = searchList
    }
  },

  actions: {
    getCheckScrap({state,getters, commit},posts_id){
      axios.get(SERVER.URL + SERVER.ROUTES.scrap,
        {params: {
         posts_id:  posts_id,
          user_id : state.loginData.user_id
        }}, getters.config)
      .then(res => {
        console.log("data", res.data);
        commit('SET_CHECK_SCRAPS', res.data);
      }).catch(err => console.log(err))
      
    },
    getUserScraps({ getters, commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.scrap + "/" + userId, getters.config)
      .then(res => {
        console.log("data", res.data);
        commit('SET_SCRAPS', res.data);
        console.log(res.data.hits)
      }).catch(err => console.log(err))
    },
    createFollowing({ getters }, params) {
      axios.post(SERVER.URL + SERVER.ROUTES.following, params, getters.config.headers)
        .then((res) => {
          console.log(res);
          console.log(params)
        })
        .catch(err => {
          console.log(err)
        })
    },
    deleteFollowing({ getters }, params) {
      axios.delete(SERVER.URL + SERVER.ROUTES.following, { data: params }, getters.config.headers)
        .then((res) => {
          console.log(res);
          console.log(params);
        })
        .catch(err => {
          console.log(err)
        })
    },
    getFollowee({ getters, commit }, params) {
      axios.get(SERVER.URL + SERVER.ROUTES.followee + params.follower_id, getters.config.headers)
        .then((res) => {
          var flag = false;
          res.data.object.forEach((element) => {
            console.log("확인", element);
            if (String(element["followee"]) == params.followee_id) {
              flag = true;
              console.log("팔로우 찾음")
              // commit('SET_FOLLOWEE', true);
            }
          });
          if (flag) commit('SET_FOLLOWEE', true);
          else commit('SET_FOLLOWEE', false);
        })
        .catch(err => {
          commit('SET_FOLLOWEE', false);
          console.log(err)
        })
    },
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
    createFollow({ getters }, params) {
      axios.post(SERVER.URL + SERVER.ROUTES.follow, params, getters.config.headers)
        .then(res => {
          console.log(res)
        })
        .catch(err => console.log(err))
    }
  },

  modules: {
    postStore,
    profileStore,
    userStore,
  },
  plugins: [
    createPersistedState()
  ]
})