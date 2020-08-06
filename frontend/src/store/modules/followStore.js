import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '@/api/RestApi.js'

const followStore = {
  namespaced: true,

  state: {
    followee_list: false,
    accessToken: cookies.get('access-token'),
  },

  getters: {
    config: state => ({ headers: { ACCESS_TOKEN: `${state.accessToken}` } }),
  },

  mutations: {
    SET_FOLLOWEE(state, followee) {
      state.followee_list = followee;
    },
  },

  actions: {
    // 팔로우 요청 수락 (팔로우가 맺어짐)
    createFollow({ getters }, params) {
      axios.post(SERVER.URL + SERVER.ROUTES.follow, params, getters.config.headers)
        .then(res => {
          console.log(res)
        })
        .catch(err => console.log(err))
    },
    // 내가 클릭해서 요청 보내는 작업
    createFollowing({ getters }, params) {
      axios.post(SERVER.URL + SERVER.ROUTES.following, params, getters.config.headers)
        .then((res) => {
          console.log(res);
          console.log(params)
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 내가 클릭해서 요청 보냈던거 취소하는 작업
    deleteFollowing({ getters }, params) {
      axios.delete(SERVER.URL + SERVER.ROUTES.following, { data: params }, getters.config.headers)
        .then((res) => {
          console.log(res);
          console.log(params);
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 프로필 유저를 내가 팔로우 한 상태인지 확인하기 위해 그 사람을 팔로우 하는 유저 목록 조회
    getFollowee({ getters, commit }, params) {
      axios.get(SERVER.URL + SERVER.ROUTES.followee + params.follower_id, getters.config.headers)
        .then((res) => {
          var flag = false;
          res.data.object.forEach((element) => {
            if (String(element["followee"]) == params.followee_id) {
              flag = true;
              // commit('SET_FOLLOWEE', true);
            }
          });
          if (flag) commit('SET_FOLLOWEE', true);
          else commit('SET_FOLLOWEE', false);
        })
        .catch(err => {
          commit('SET_FOLLOWEE', false);
          console.log(err)
        })
    },
  },
}

export default followStore



