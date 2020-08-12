import axios from 'axios'
import SERVER from '@/api/RestApi.js'

const notificationStore = {
  namespaced: true,

  state: {
    notification: {
      notification: {},
      requests: {}
    },
  },

  getters: {
  },

  mutations: {
    SET_NOTIFICATION(state, notification) {
      state.notification.notification = notification
    },
    SET_REQUESTS(state, requests) {
      state.notification.requests = requests
    },
  },

  actions: {
    fetchNotification({ rootGetters, commit }, id) {
      axios.get(SERVER.URL + SERVER.ROUTES.notification + id, rootGetters.config.headers)
        .then(res => {
          console.log("notification", res.data)
          commit('SET_NOTIFICATION', res.data)
        })
        .catch(err => console.log(err))
    },
    putNotification({ rootGetters }, params) {
      axios.put(SERVER.URL + SERVER.ROUTES.following, params,rootGetters.config.headers)
        .then(res => {
          console.log(res)
        })
        .catch(err => console.log(err))
    },

    fetchRequests({ rootGetters, commit }, id) {
      axios.get(SERVER.URL + SERVER.ROUTES.requests + id, rootGetters.config.headers)
        .then(res => {
          commit('SET_REQUESTS', res.data)
          console.log(res)
        })
        .catch(err => console.log(err))
    },
  },
}

export default notificationStore