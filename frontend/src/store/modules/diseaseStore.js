import cookies from 'vue-cookies'
import axios from "axios";
import SERVER from "@/api/RestApi.js";
const diseaseStore = {
    namespaced: true,

    state: {
        diseases: [],
    },

    getters: {
    },

    mutations: {
        SET_DISEASE(state, diseases) {
            state.diseases = diseases;
        },

    },


    actions: {
        getFollowingDisease({ rootGetters, commit }) {
            axios.get(SERVER.URL + SERVER.ROUTES.disease, rootGetters.config)
                .then(res => {
                    console.log(res)
                    commit('SET_DISEASE', res.data)
                })
                .catch(err => console.log(err))
        },
        async createDisease({rootGetters,dispatch} ,params){ //질병 구독
            console.log(params)
            console.log(rootGetters.config)
            await axios.post(SERVER.URL + SERVER.ROUTES.subscribe,params ,{headers: { accessToken:  cookies.get('access-token') }})
            .then((res) => {
                console.log(res);
                dispatch('getFollowingDisease')
            })
        },
        deleteDisease({ rootGetters, dispatch }, diseasecode) {
            console.log(rootGetters.config)
            axios.delete(SERVER.URL + SERVER.ROUTES.subscribe,
                {
                    data:{
                        diseasecode: diseasecode
                    }
                    , headers: rootGetters.config.headers
                })
                .then(res => {
                    console.log("result", res);
                    dispatch('getFollowingDisease') 
                }).catch(err => console.log(err))
        }
    }
}

export default diseaseStore