import axios from 'axios'
import router from '@/router'
import SERVER from '@/api/RestApi.js'

const profileStore = {
  namespaced: true,

  state: {
    profileData: {},
    // profileData: {
    //   userInfo: {},
    //   profileInfo: {},
    //   postInfo: {},
    //   scrapInfo: {},
    // },
  },

  getters: {
  },

  mutations: {
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
  },

  actions: {
    async goProfile({ state, commit }, userId) {
      if (userId == null) {
        userId = state.loginData.user_id
      }

      await axios.get(SERVER.URL + SERVER.ROUTES.user + userId)
        .then(res => {
          commit('SET_USER_INFO', res.data)
        })
        .catch(err => console.log(err))
      await axios.get(SERVER.URL + SERVER.ROUTES.profile + userId)
        .then(res => {
          commit('SET_PROFILE_INFO', res.data)
        })
      await axios.get(SERVER.URL + SERVER.ROUTES.posts + `/${userId}`)
        .then(function (res) {
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
            axios.get(SERVER.URL + SERVER.ROUTES.scrap,
              {
                params: {
                  posts_id: element.posts_id,
                  user_id: state.loginData.user_id,
                },
              },
            )
              .then((res) => {
                if (res.data > 0) element.scrap = true;
                //router.push({ name: 'Profile' })
              })
              .catch((err) => console.log(err));
          })
          commit('SET_POST_INFO', res.data)

        })
      router.push({ name: 'Profile' })
    },

  }
}

export default profileStore