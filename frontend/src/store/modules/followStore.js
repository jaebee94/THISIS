import axios from 'axios'
import SERVER from '@/api/RestApi.js'

const followStore = {
  namespaced: true,

  state: {
    followee_list: false,
  },

  getters: {
  },

  mutations: {
    SET_FOLLOWEE(state, followee) {
      state.followee_list = followee;
    },
  },

  actions: {
    // 팔로우 요청 수락 (팔로우가 맺어짐)
    createFollow({ rootGetters }, params) {
      axios.post(SERVER.URL + SERVER.ROUTES.follow, params, rootGetters.config.headers)
        .then(res => {
          console.log(res)
        })
        .catch(err => console.log(err))
    },
    // 내가 클릭해서 요청 보내는 작업
    createFollowing({ rootGetters }, params) {
      axios.post(SERVER.URL + SERVER.ROUTES.following, params, rootGetters.config.headers)
        .then((res) => {
          console.log(res);
          console.log(params)
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 내가 클릭해서 요청 보냈던거 취소하는 작업
    deleteFollowing({ rootGetters }, params) {
      axios.delete(SERVER.URL + SERVER.ROUTES.following, { data: params , headers : rootGetters.config.headers })
        .then((res) => {
          console.log(res);
          console.log(params);
        })
        .catch(err => {
          console.log(err)
        })
    },
    // 프로필 유저를 내가 팔로우 한 상태인지 확인하기 위해 그 사람을 팔로우 하는 유저 목록 조회
    getFollowee({rootGetters, commit }, params) {
      axios.get(SERVER.URL + SERVER.ROUTES.followee + params.follower_id, rootGetters.config.headers)
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



