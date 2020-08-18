import axios from "axios"
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
      console.log(rootGetters.config)
      axios.get(SERVER.URL + SERVER.ROUTES.reportedPosts, params)
        .then(data => {
          console.log(data)
          commit('SET_TEMP_DATA', data)
          console.log(state.page)
          if (data) {
            commit('INCREASE_PAGE')
            commit('SET_POST_LIST', data)
            // console.log(state.postList)
            $state.loaded() 
          } else {

            // commit('RESET_PAGE')
            $state.complete()
          }
          
        })
    },
    fetchReportedUsers({ rootGetters }) {
      axios.get(SERVER.URL + SERVER.ROUTES.reportedUsers, rootGetters.config)
    },
    fetchDoctors({ rootGetters }) {
      axios.get(SERVER.URL + SERVER.ROUTES.doctors, rootGetters.config)
    }
  },
}

export default adminStore