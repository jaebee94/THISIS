import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Post from './components/feed/Post.vue'
import Feed from './components/feed/Feed.vue'
import Modal from './components/feed/Modal.vue'
import InfiniteLoading from "vue-infinite-loading";
require('@/assets/style.css')
Vue.config.productionTip = false

Vue.component('Post', Post)
Vue.component('InfiniteLoading', InfiniteLoading)
Vue.component('Feed', Feed)
Vue.component('Modal', Modal)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')



