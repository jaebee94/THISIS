import axios from 'axios'
import router from '@/router'
import SERVER from '@/api/RestApi.js'
import cookies from 'vue-cookies'

const userStore = {
  namespaced: true,

  state: {
    loginData: {},
  },

  getters: {
  },

  mutations: {
    SET_TOKEN(state, token) {
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
          console.log(res)
          commit('SET_TOKEN', res.data.accessToken)
          commit('SET_LOGIN_DATA', res.data)
          router.push({ name: 'Feed' })
        })
        .catch(() => {
          alert("로그인에 실패하였습니다.")
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
      commit('SET_TOKEN', null)
      commit('SET_LOGIN_DATA', null)
      cookies.remove('access-token')
      window.localStorage.clear(); 
      router.push({ name: 'Login' })
    },
    // findPassword(email) {
    //   axios.get(SERVER.URL + SERVER.ROUTES.email, { params: {
    //     email: email
    //   }})
    //     .then(res => {console.log('성공', res)
    //      alert("이메일을 전송하였습니다.")
    //      this.$router.push('/account/login')
    //      }
    //      )
    //     .catch(err => {console.log(err)
    //      alert("이메일 전송에 실패하였습니다.")})
    // },
     changeUserInfo({rootGetters,dispatch}, changeInfo) {
      console.log(changeInfo)
      axios.put(SERVER.URL + SERVER.ROUTES.user + changeInfo.user_id, changeInfo,rootGetters.config)
      .then(async () => {
        alert('변경이 완료되었습니다.')
        dispatch('profileStore/goProfile', changeInfo.user_id,{root:true}) // root로 보내서 이동
      })
      .catch(err => console.log(err))

    },
  },
}

export default userStore