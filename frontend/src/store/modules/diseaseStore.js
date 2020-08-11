
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
        getFolloingwDisease({ rootGetters, commit }) {
            axios.get(SERVER.URL + SERVER.ROUTES.userDisease, rootGetters.config)
                .then(res => {
                    commit('SET_DISEASE', res.data)
                })
        },
        createDisease({rootGetters, commit} , diseasename,diseasecode){
            axios.get(SERVER.URL + SERVER.ROUTES.userDisease,
                {
                    diseasecode : diseasecode,
                    diseasename : diseasename
                    
                }
                ,rootGetters.config)
            .then(res => {
                commit('SET_DISEASE', res.data)
            })
        },
        addDisease({ rootGetters }, diseasecode) { //이부분 아직 백엔드에 구현 안되어 있음
            axios.put(SERVER.URL + SERVER.ROUTES.userDisease,
                {
                    diseasecode: diseasecode
                }
                , rootGetters.config)
                .then(res => {
                    console.log("result", res);
                    this.getFolloingwDisease(rootGetters.config) //다시 업데이트
                }).catch(err => console.log(err))

        },
        deleteDisease({ rootGetters }, diseasecode) {
            axios.delete(SERVER.URL + SERVER.ROUTES.userDisease,
                {
                    params: {
                        diseasecode: diseasecode
                    }
                }
                , rootGetters.config)
                .then(res => {
                    console.log("result", res);
                    this.getFolloingwDisease(rootGetters.config) //다시 업데이트
                }).catch(err => console.log(err))
        }
    }
}

export default diseaseStore