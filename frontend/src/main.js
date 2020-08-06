import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Post from './components/feed/Post.vue'
import Feed from './components/feed/Feed.vue'
import InfiniteLoading from "vue-infinite-loading";
require('@/assets/style.css')
Vue.config.productionTip = false

Vue.component('post', Post)
Vue.component('InfiniteLoading', InfiniteLoading)
Vue.component('Feed', Feed)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')



