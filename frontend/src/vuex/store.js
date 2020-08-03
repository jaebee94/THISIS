import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'

Vue.use(Vuex)

const state = {
    isUser: false,
    userName: '초기 이름'
}

export default new Vuex.Store({
    state,
    mutations,
    getters,
    actions
})