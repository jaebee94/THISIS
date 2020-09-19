import axios from "axios"
import router from '@/router'
import SERVER from '@/api/RestApi.js'

const adminStore = {
  namespaced: true,

  state: {
    page: 0,
    postList: [],
    userList: [],
    doctorList: [],
    tempData: null,
  },

  getters: {

  },

  mutations: {
    // INCREASE_PAGE(state) {
    //   state.page += 1
    // },
    // RESET_PAGE(state) {
    //   state.page = 0
    // },
    SET_POST_LIST(state, data) {
      state.postList.push(...data)
    },
    SET_TEMP_DATA(state, data) {
      state.tempData = data
    }
  },

  actions: {
    fetchReportedPosts({ commit, state, rootGetters }, $state) {
      var params = {
        params: {
          num: state.page
        },
        headers: rootGetters.config.headers
      }
      axios.get(SERVER.URL + SERVER.ROUTES.reportedPosts, params)
        .then(data => {

          commit('SET_TEMP_DATA', data)
          if (data) {
            commit('INCREASE_PAGE')
            commit('SET_POST_LIST', data)
            $state.loaded() 
          } else {

            // commit('RESET_PAGE')
            $state.complete()
          }
          
        })
    },
    managePost({ rootGetters }, decidepost) {
      axios.post(SERVER.URL + SERVER.ROUTES.managePost, decidepost, rootGetters.config)
        .then(() => {
          alert('요청이 처리되었습니다.')
          router.go()
        })
    },
    fetchReportedUsers({ rootGetters }) {
      axios.get(SERVER.URL + SERVER.ROUTES.reportedUsers, rootGetters.config)
    },
    manageUser({ rootGetters }, decideaccount) {
      axios.post(SERVER.URL + SERVER.ROUTES.manageUser, decideaccount, rootGetters.config)
        .then(() => {
          alert('요청이 처리되었습니다.')
          router.go()
        })
    },
    fetchDoctors({ rootGetters }) {
      axios.get(SERVER.URL + SERVER.ROUTES.doctors, rootGetters.config)
    },
    decideDoctorauth({ rootGetters }, requestdoctor) {
      axios.put(SERVER.URL + SERVER.ROUTES.doctorauth, requestdoctor, rootGetters.config)

    }
  },
}

export default adminStore