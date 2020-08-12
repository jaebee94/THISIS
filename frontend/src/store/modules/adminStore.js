import axios from "axios"
import SERVER from '@/api/RestApi.js'

const adminStore = {
  namespaced: true,

  state: {

  },

  getters: {

  },

  mutations: {

  },

  actions: {
    fetchReportedPosts({ rootGetters }) {
      axios.get(SERVER.URL + SERVER.ROUTES.reportedPosts, rootGetters.config)
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