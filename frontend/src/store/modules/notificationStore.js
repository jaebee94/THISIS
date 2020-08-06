import axios from 'axios'
// import router from '@/router'
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
    fetchNotification({ getters, commit }, id) {
      axios.get(SERVER.URL + SERVER.ROUTES.notification + id, getters.config.headers)
        .then(res => {
          commit('SET_NOTIFICATION', res.data)
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

    fetchRequests({ getters, commit }, id) {
      axios.get(SERVER.URL + SERVER.ROUTES.requests + id, getters.config.headers)
        .then(res => {
          commit('SET_REQUESTS', res.data)
          console.log(res)
        })
        .catch(err => console.log(err))
    },
  },
}

export default notificationStore