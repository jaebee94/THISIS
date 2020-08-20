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
        async getFollowingDisease({ rootGetters, commit }) {
            await axios.get(SERVER.URL + SERVER.ROUTES.disease, rootGetters.config)
                .then(async(res) => {
                    await commit('SET_DISEASE', res.data)
                })
                .catch(err => console.log(err))
        },
        async createDisease({rootGetters,dispatch} ,params){ //질병 구독
            await axios.post(SERVER.URL + SERVER.ROUTES.subscribe,params ,rootGetters.config)
            .then(async () => {
                await dispatch('getFollowingDisease')
            })
        },
        async deleteDisease({ rootGetters, dispatch }, diseasecode) {
            
            await axios.delete(SERVER.URL + SERVER.ROUTES.subscribe,
                {
                    data:{
                        diseasecode: diseasecode
                    }
                    , headers: rootGetters.config.headers
                })
                .then(async ()=> {
                    await dispatch('getFollowingDisease') 
                }).catch(err => console.log(err))
        }
    }
}

export default diseaseStore