import axios from 'axios'
import router from '@/router'
import SERVER from '@/api/RestApi.js'

import cookies from 'vue-cookies'

const userStore = {
  namespaced: true,

  state: {
    accessToken: cookies.get('access-token'),
    loginData: {},
  },

  getters: {
    config: state => ({ headers: { ACCESS_TOKEN: `${state.accessToken}` } }),
  },

  mutations: {
    SET_TOKEN(state, token) {
      state.accessToken = token
      cookies.set('access-token', token)
    },
    SET_LOGIN_DATA(state, loginData) {
      state.loginData = loginData
    },
  },

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
  },
}

export default userStore