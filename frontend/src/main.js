import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueAnalytics from 'vue-analytics'
import Post from './components/feed/Post.vue'
import Feed from './components/feed/Feed.vue'
import Modal from './components/feed/Modal.vue'
import UserList from './components/feed/UserList.vue'
import InfiniteLoading from "vue-infinite-loading";
require('@/assets/style.css')
Vue.config.productionTip = false

Vue.component('Post', Post)
Vue.component('InfiniteLoading', InfiniteLoading)
Vue.component('Feed', Feed)
Vue.component('UserList', UserList)
Vue.component('Modal', Modal)



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

Vue.use(VueAnalytics,{
  id: 'UA-175653164-1', // 콘솔에서 본인의 키를 확인하세요!
  router // 원활한 트래킹을 위해 router를 꼭 바인드 해 주어야 합니다!
})



