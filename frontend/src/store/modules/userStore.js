import axios from 'axios'
import router from '@/router'
import SERVER from '@/api/RestApi.js'
import cookies from 'vue-cookies'
import db from "../../firebaseInit";

const userStore = {
  namespaced: true,

  state: {
    loginData: null,
    profileData: {},
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
    getAccessData({ commit,rootGetters }, info) {
      axios.get(SERVER.URL + info.location, {
        params: {
          email: info.params.email,
          password: info.params.password
        }
      })
        .then(res => {
          
          console.log("로그인", res)
          if(res.data.disabled == 1) {
            alert("운영자에 의해 정지된 사용자입니다.")
            return;
          }
          commit('SET_TOKEN', res.data.accessToken)
          commit('SET_LOGIN_DATA', res.data)
          rootGetters.config.headers = { accessToken:  cookies.get('access-token') };
          if (res.data.subscribeCount > 0) {
            router.push({ name: 'Feed' })
          } else {
            router.push({ name: 'Tutorial' })
          }

        })
        .catch(() => {
          alert("회원이 아니거나 로그인 정보가 일치하지 않습니다.")
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
      window.$cookies.remove('access-token')
      cookies.remove('access-token')
      window.localStorage.clear();
      router.push({ name: 'Login' })
    },
    signup({rootGetters},signupData) {
      axios.post(SERVER.URL + SERVER.ROUTES.signup, signupData,rootGetters.config)
        .then((res) => {
          var id = res.data.object;
          let instance = {
            notification: 0,
            request: 0
          }
          db
            .collection("notification")
            .doc(String(id))
            .set(instance);

          alert('회원가입이 완료되었습니다.')
          router.push({ name: 'Login' })
        })
        .catch(err => console.log(err))
    },
    withdraw({ state,rootGetters, dispatch }) {
      var con = confirm("탈퇴하시겠습니까?");
      if (con) {
        axios.delete(SERVER.URL + SERVER.ROUTES.user, rootGetters.config)
          .then(() => {

            //firebase 삭제 로직
            db.collection("notification").doc(String(state.loginData.user_id)).delete().then(function () {
            }).catch(function (error) {
              console.error("Error removing document: ", error);
            });
            dispatch("logout");
          })
          .catch(
            err => console.log('회원 탈퇴 에러: ', err)
          )
        
      }
    },
    async changeUserInfo({ rootGetters, dispatch, state }, changeInfo) {
      await axios.put(SERVER.URL + SERVER.ROUTES.user, changeInfo.userInfo, { headers: { accessToken: cookies.get('access-token') } })
        .then(() => {
          alert('변경이 완료되었습니다.')
          dispatch('goProfile', changeInfo.userInfo.user_id)
        })
        .catch(err => console.log('프로필 변경 에러: ', err))
      if (changeInfo.formData) {
        let config = { headers: {} }
        config.headers = rootGetters.config.headers
        config.headers['Accept'] = 'application/json'
        //config.headers['Content-Type'] = 'multipart/form-data'
        await axios.post(SERVER.URL + SERVER.ROUTES.uploadProfile, changeInfo.formData, config)
          .then(async (res) => {
            state.loginData.userimage = res.data;
            dispatch('goProfile', changeInfo.userInfo.user_id)
            // router.push({ name: 'Profile' })
          })
          .catch(err => console.log('사진 변경 에러: ', err))
      }
    },
    async goProfile({ state, commit }, userId) {
      if (userId == null) {
        userId = state.loginData.user_id
      }
      await axios.get(SERVER.URL + SERVER.ROUTES.user + `/${userId}`, { headers: { accessToken: cookies.get('access-token') } })
        .then(res => {
          commit('SET_USER_INFO', res.data)
        })
        .catch(err => console.log(err))
      await axios.get(SERVER.URL + SERVER.ROUTES.profile + userId, { headers: { accessToken: cookies.get('access-token') } })
        .then(res => {
          commit('SET_PROFILE_INFO', res.data)
          router.push({ name: 'Profile' })
        })
        .then(() => { router.go() })
      
    },
    async UploadDoctorAuth({ rootGetters,state }, Doctorimgsrc) {
      if (Doctorimgsrc) {
        let config = { headers: {} }  
        config.headers = rootGetters.config.headers
        config.headers['Accept'] = 'application/json'
        //config.headers['Content-Type'] = 'multipart/form-data'
        await axios.post(SERVER.URL + SERVER.ROUTES.UploadDoctorAuth, Doctorimgsrc, config)
        .then(async (res) => {
          state.loginData.userimage=res.data;
        })
        .catch(err => console.log('사진 변경 에러: ', err))
      }
    },//upload
  },
}

export default userStore