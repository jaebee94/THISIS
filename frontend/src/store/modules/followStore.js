import axios from 'axios'
import SERVER from '@/api/RestApi.js'

const followStore = {
  namespaced: true,

  state: {
    followee_list: false,
    follower: [],
    follwing: []
  },

  getters: {
  },

  mutations: {
    SET_FOLLOWEE(state, followee) {
      state.followee_list = followee;
    },
    SET_FOLLOWER(state, follower) {
      state.follower = follower;
    },
    SET_FOLLOWING(state, following) {
      state.following = following;
    },
  },

  actions: {

    // 내가 클릭해서 요청 보내는 작업
    createFollowing({ rootGetters }, params) {
      axios.post(SERVER.URL + SERVER.ROUTES.following, params, rootGetters.config.headers)
        .catch(err => {
          console.log(err)
        })
    },
    // 내가 클릭해서 요청 보냈던거 취소하는 작업
    deleteFollowing({ rootGetters }, params) {
      axios.delete(SERVER.URL + SERVER.ROUTES.following, { data: params, headers: rootGetters.config.headers })
        .catch(err => {
          console.log(err)
        })
    },
    // 팔로우 요청 수락 (팔로우가 맺어짐)
    createFollow({ rootGetters }, params) {
      axios.post(SERVER.URL + SERVER.ROUTES.follow, params, rootGetters.config.headers)
        .catch(err => console.log(err))
    },
    //팔로우 끊기
    deleteFollow({ rootGetters }, params) {
      var conf = confirm("정말 팔로우를 끊으시겠습니까?");
      if (conf) {
        axios.delete(SERVER.URL + SERVER.ROUTES.follow, { data: params, headers: rootGetters.config.headers })
          .catch(err => {
            console.log(err)
          })
      }

    },
    // 프로필 유저를 내가 팔로우 한 상태인지 확인하기 위해 그 사람을 팔로우 하는 유저 목록 조회
    async getFollowee({ rootGetters, commit }, params) {
      await axios.get(SERVER.URL + SERVER.ROUTES.followee + params.follower_id, rootGetters.config.headers)
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
    async getFollowing({ rootGetters, commit }, id) {
      var followingArray = [];
      await axios.get(SERVER.URL + SERVER.ROUTES.followee + id, rootGetters.config.headers)
        .then((res) => {
          //팔로이 아이디 통해서 유저정보 획득
          res.data.object.forEach(following => {
            axios.get(SERVER.URL + SERVER.ROUTES.user + `/${following.followee}`, rootGetters.config)
              .then((res) => {
                followingArray.push(res.data);
              })
              .catch(err => {
                console.log("followee user 정보 획득 에러",err)
              })
          });

          commit('SET_FOLLOWING', followingArray);
        })
        .catch(err => {
          console.log(err)
        })
    },
    async getFollower({ rootGetters, commit }, id) {
      var followerArray = [];
      await axios.get(SERVER.URL + SERVER.ROUTES.follower + id, rootGetters.config.headers)
      .then((res) => {
        //팔로이 아이디 통해서 유저정보 획득
        res.data.object.forEach(followerlist => {
          axios.get(SERVER.URL + SERVER.ROUTES.user + `/${followerlist.follower}`, rootGetters.config)
            .then((res) => {
              followerArray.push(res.data);
            })
            .catch(err => {
              console.log("follower user 정보 획득 에러",err)
            })
        });

        commit('SET_FOLLOWER', followerArray);
      })
      .catch(err => {
        console.log(err)
      })
    }
  },
}

export default followStore



