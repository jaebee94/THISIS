import Vue from 'vue'
import Vuex from 'vuex'

import cookies from 'vue-cookies'
import axios from 'axios'

import router from '@/router'
import SERVER from '@/api/RestApi.js'

import createPersistedState from 'vuex-persistedstate';


Vue.use(Vuex)

export default new Vuex.Store({
  // data 의 집합 (중앙 관리할 모든 데이터 === 상태)
  state: {
    accessToken: cookies.get('access-token'),
    loginData: {},
    profileData: {
      userInfo: {},
      profileInfo: {},
      postInfo: {},
      scrapInfo: {},
    },
    posts: {},
    healths: {},
    comments: {},
    notification: {
      notification: {},
      requests: {}
    },
    checkScrap: 0,
    followee_list: false,
    searchList: {},
  },
  // state 를 (가공해서 혹은 그대로) 가져올 함수들. === computed
  getters: {
    config: state => ({ headers: { ACCESS_TOKEN: `${state.accessToken}` } }),
  },
  // state 를 변경하는 함수들 (mutations 에 작성되지 않은 state 변경 코드는 모두 동작하지 않음)
  // 모든 mutation 함수들은 동기적으로 동작하는 코드.all
  // commit 을 통해 실행함
  mutations: {
    SET_TOKEN(state, token) {
      state.accessToken = token
      cookies.set('access-token', token)
    },
    SET_LOGIN_DATA(state, loginData) {
      state.loginData = loginData
    },
    SET_USER_INFO(state, userInfo) {
      state.profileData.userInfo = userInfo
    },
    SET_PROFILE_INFO(state, profileInfo) {
      state.profileData.profileInfo = profileInfo
    },
    SET_POST_INFO(state, postInfo) {
      state.profileData.postInfo = postInfo
    },
    SET_SCRAPS(state, scraps) {
      state.profileData.scrapInfo = scraps
    },
    SET_CHECK_SCRAPS(state, checkScrap) {
      state.checkScrap = checkScrap
    },
    SET_POSTS(state, posts) {
      state.posts = posts
    },
    SET_HEALTHS(state, healths) {
      state.healths = healths
      console.log(healths)
    },
    SET_COMMENTS(state, comments) {
      state.comments = comments
    },
    SET_NOTIFICATION(state, notification) {
      state.notification.notification = notification
    },
    SET_REQUESTS(state, requests) {
      state.notification.requests = requests
    },
    SET_FOLLOWEE(state, followee) {
      state.followee_list = followee;
    },
    SET_SEARCHLIST(state, searchList) {
      state.searchList = searchList
    }
  },
  // 범용적인 함수들. mutations 에 정의한 함수를 actions 에서 실행 가능
  // 비동기 로직은 actions 에서 정의.
  // dispatch 를 통해 실행함
  actions: {
    getAccessData({ commit }, info) {
      axios.get(SERVER.URL + info.location, {
        params: {
          email: info.params.email,
          password: info.params.password
        }
      })
        .then(res => {
          console.log('asdasd', res)
          commit('SET_TOKEN', res.data.accessToken)
          commit('SET_LOGIN_DATA', res.data)
          router.push({ name: 'Feed' })
        })
        .catch(err => {
          alert("로그인에 실패하였습니다.")
          console.log(err)
        })
    },
    login({ dispatch }, loginData) {
      const info = {
        params: loginData,
        location: SERVER.ROUTES.login
      }
      dispatch('getAccessData', info)
    },
    logout({ commit }) {  // 주석코드 사용할 때 getters 추가하고 사용하기
      console.log('로그아웃')
      commit('SET_TOKEN', null)
      commit('SET_LOGIN_DATA', null)
      cookies.remove('access-token')
      window.localStorage.clear(); 
      router.push({ name: 'Login' })
      // axios.post(SERVER.URL + SERVER.ROUTES.logout, null, getters.config.headers)
      //   .then(() => {
      //     commit('SET_TOKEN', null)
      //     commit('SER_LOGIN_DATA', null)
      //     cookies.remove('access-token')
      //     router.push({ name: 'Login' })
      //   })
      //   .catch(err => console.log(err))
    },
    async goProfile({ state, commit }, userId) {
      if (userId == null) {
        userId = state.loginData.user_id
      }
      console.log(SERVER.URL + SERVER.ROUTES.user + userId)
     
      await axios.get(SERVER.URL + SERVER.ROUTES.user + userId)
        .then(res => {
          commit('SET_USER_INFO', res.data)
          console.log(res.data)
        })
        .catch(err => console.log(err))
      await axios.get(SERVER.URL + SERVER.ROUTES.profile + userId)
        .then(res => {
          console.log("set_profile_info",res.data);
          commit('SET_PROFILE_INFO', res.data)
        })
      await axios.get(SERVER.URL + SERVER.ROUTES.posts + `/${userId}`)
        .then( function(res) {
           res.data.forEach(async (element) => {
            element.health = false;
            element.scrap = false;
            axios.get(SERVER.URL + SERVER.ROUTES.health + `/post/${element.posts_id}`)
              .then(count => element.health_count = count.data)
            axios.get(SERVER.URL + SERVER.ROUTES.health + `/${userId}`)
              .then(items => {
                items.data.forEach(item => {
                  if (item.posts_id == element.posts_id) {
                    element.health = true;
                  }
                })
              }
            )
           axios.get( SERVER.URL + SERVER.ROUTES.scrap,
              {
                params: {
                  posts_id: element.posts_id,
                  user_id: state.loginData.user_id,
                },
              },
            )
            .then((res) => {
              console.log("data", res.data);
              if(res.data > 0) element.scrap = true;
              //router.push({ name: 'Profile' })
            })
            .catch((err) => console.log(err));
          })
          commit('SET_POST_INFO', res.data)
          
        })
        router.push({ name: 'Profile' })
    },
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
    deleteScrap({state,getters},post_id){
      axios.delete(SERVER.URL + SERVER.ROUTES.scrap+`/${post_id}/${state.loginData.user_id}`,getters.config)
      .then(res => {
        console.log("result", res);
      }).catch(err => console.log(err))
    },
    createScrap({state,getters},post_id){
      axios.delete(SERVER.URL + SERVER.ROUTES.scrap+`/${post_id}/${state.loginData.user_id}`,getters.config)
      .then(res => {
        console.log("result", res);
      }).catch(err => console.log(err))
    },
     changeUserInfo({ dispatch,getters }, changeInfo) {
      console.log(changeInfo)
      axios.put(SERVER.URL + SERVER.ROUTES.user + changeInfo.user_id, changeInfo, getters.config)
        .then(() => {
          alert('변경이 완료되었습니다.')
          dispatch('goProfile', changeInfo.user_id)
          router.push({ name: "Profile" })
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
    fetchHealths({ commit }, posts_id) {
      axios.get(SERVER.URL + SERVER.ROUTES.healthCount + posts_id)
        .then(res => {
          console.log('health', res)
          commit('SET_HEALTHS', res.data)
        })
    },
    fetchComments({ commit }, posts_id) {
      axios.get(SERVER.URL + SERVER.ROUTES.comment + '/' + posts_id)
        .then(res => {
          console.log(res.data)
          commit('SET_COMMENTS', res.data)
        })
    },
    health({ getters }, healthData) {
      console.log(SERVER.URL + SERVER.ROUTES.health + `/${healthData.posts_id}`)
      console.log('healthData', healthData)
      axios.post(SERVER.URL + SERVER.ROUTES.health + `/${healthData.posts_id}`, healthData, getters.config)
        .then(res => {
          console.log(res.data)
        })
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
    createPost({ getters }, postData) {
      console.log(postData)
      console.log(getters.config)
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
    updateComment({ getters }, commentData) {
      axios.post(SERVER.URL + SERVER.ROUTES.comment + `/`, commentData, getters.config)
    },
    fetchNotification({ getters, commit }, id) {
      //TODO 유저 아이디 부분 수정
      axios.get(SERVER.URL + SERVER.ROUTES.notification + id, getters.config.headers)
        .then(res => {
          commit('SET_NOTIFICATION', res.data)
        })
        .catch(err => console.log(err))
    },
    fetchRequests({ getters, commit }, id) {
      //TODO 유저 아이디 부분 수정
      axios.get(SERVER.URL + SERVER.ROUTES.requests + id, getters.config.headers)
        .then(res => {
          commit('SET_REQUESTS', res.data)
          console.log(res)
        })
        .catch(err => console.log(err))
    },
    putNotification({ getters }, params) {
      axios.put(SERVER.URL + SERVER.ROUTES.following, params, getters.config.headers)
        .then(res => {
          console.log(res)
        })
        .catch(err => console.log(err))
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
  },
  plugins: [
    createPersistedState()
  ]
})