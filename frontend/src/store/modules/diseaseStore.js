
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
        createDisease({rootGetters,dispatch} ,params){ //질병 구독
            console.log(params)
            axios.post(SERVER.URL + SERVER.ROUTES.subscribe,params ,rootGetters.config)
            .then((res) => {
                console.log(res);
                dispatch('getFollowingDisease')
            })
        },
        // addDisease({ rootGetters }, diseasecode) { //이부분 아직 백엔드에 구현 안되어 있음
        //     axios.put(SERVER.URL + SERVER.ROUTES.disease,
        //         {
        //             diseasecode: diseasecode
        //         }
        //         , rootGetters.config)
        //         .then(res => {
        //             console.log("result", res);
        //             this.getFolloingwDisease(rootGetters.config) //다시 업데이트
        //         }).catch(err => console.log(err))

        // },
        deleteDisease({ rootGetters, dispatch }, diseasecode) {
            axios.delete(SERVER.URL + SERVER.ROUTES.subscribe,
                {
                    data:{
                        diseasecode: diseasecode
                    }
                }
                , rootGetters.config)
                .then(res => {
                    console.log("result", res);
                    dispatch('getFollowingDisease') 
                }).catch(err => console.log(err))
        }
    }
}

export default diseaseStore