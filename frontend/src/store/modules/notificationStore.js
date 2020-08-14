import axios from 'axios'
import SERVER from '@/api/RestApi.js'

const notificationStore = {
  namespaced: true,

  state: {
    notification: {},
    requests: {}
  },

  getters: {
  },

  mutations: {
    SET_NOTIFICATION(state, notification) {
      state.notification = notification
    },
    SET_REQUESTS(state, requests) {
      state.requests = requests
    },
  },

  actions: {
    putNotification({ rootGetters }, params) {
      console.log('putNotification Params', params)
      axios.put(SERVER.URL + SERVER.ROUTES.following, params,rootGetters.config.headers)
        .then(res => {
          console.log(res)
        })
        .catch(err => console.log(err))
    },

    fetchNotification({ rootGetters, commit }, id) {
      axios.get(SERVER.URL + SERVER.ROUTES.notification + id, rootGetters.config.headers)
        .then(res => {
          console.log("notification", res)
          commit('SET_NOTIFICATION', res.data)
        })
        .catch(err => console.log(err))
    },
  
    fetchRequests({ rootGetters, commit }, id) {
      axios.get(SERVER.URL + SERVER.ROUTES.requests + id, rootGetters.config.headers)
        .then(res => {
          console.log('fetchRequests 요청 URL', SERVER.URL + SERVER.ROUTES.requests + id)
          commit('SET_REQUESTS', res.data)
          console.log('fetchRequests', res)
        })
        .catch(err => console.log(err))
    },
  },
}

export default notificationStore