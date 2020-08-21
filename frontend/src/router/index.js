import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'

import Landing from '../views/tutorial/Landing.vue'

import Signup from '../views/user/Signup.vue'
import JoinComplete from '../views/user/JoinComplete.vue'

import Login from '../views/user/Login.vue'
import Logout from '../views/user/Logout.vue'

import Tutorial from '../views/user/Tutorial.vue'

import FindPassword from '../views/user/FindPassword.vue'

import Main from '../views/main/Main.vue'

import Feed from '../components/feed/Feed.vue'
import Search from '../components/feed/Search.vue'
import Upload from '../components/feed/Upload.vue'
import Notify from '../components/feed/Notify.vue'
import Profile from '../components/feed/Profile.vue'
import FollowList from '../components/feed/FollowList.vue'

import PostDetail from '../components/feed/PostDetail.vue'

import ChangeProfile from '../components/feed/ChangeProfile.vue'

import DiseaseTest from '../views/main/DiseaseTest.vue'
import NewsTest from '../views/main/NewsTest.vue'

import Admin from '../views/admin/Admin.vue'

import store from '@/store'

Vue.use(VueRouter)

const requireAuth =(to, from, next) => {
  if (store.getters.config.headers!=null) return next()
  else return next('/')
}


const routes = [
  {
    path: '/',
    name: 'Landing',
    component: Landing
    
  },
  {
    path: '/main',
    name: 'Main',
    component: Main,
    children: [
      {
        path: 'feed',
        name: 'Feed',
        component: Feed,
        beforeEnter: requireAuth
      },
      {
        path: 'detail',
        name: 'Detail',
        component: PostDetail,
        props: true,
        beforeEnter: requireAuth
      },
      {
        path: 'search',
        name: 'Search',
        component: Search,
        beforeEnter: requireAuth
      },
      {
        path: 'upload',
        name: 'Upload',
        component: Upload,
        beforeEnter: requireAuth
      },
      {
        path: 'notify',
        name: 'Notify',
        component: Notify,
        beforeEnter: requireAuth
      },
      {
        path: 'profile',
        name: 'Profile',
        component: Profile,
        beforeEnter: requireAuth
      },
      {
        path: 'followlist',
        name: 'FollowList',
        component: FollowList,
        beforeEnter: requireAuth,
        props:true
      },
      {
        path: 'change',
        name: 'ChangeProfile',
        component: ChangeProfile,
        beforeEnter: requireAuth
      }
    ]
  },
  {
    path: '/account/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/account/joinComplete',
    name: 'JoinComplete',
    component: JoinComplete
  },
  {
    path: '/account/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/account/logout',
    name: 'Logout',
    component: Logout,
    beforeEnter: requireAuth
  },
  {
    path: '/account/findPassword',
    name: 'findPassword',
    component: FindPassword
  },
  {
    path:'*',
    redirect: '/404'
  },
  {
    path: '/404',
    name: 'Login2',
    component: Login
  },
  {
    path: '/account/tutorial',
    name: 'Tutorial',
    component: Tutorial
  },
  {
    path: '/DiseaseTest',
    name: 'DiseaseTest',
    component: DiseaseTest
  },
  {
    path: '/NewsTest',
    name: 'NewsTest',
    component: NewsTest
  },

  // Admin
  {
    path: '/admin',
    name: 'Admin',
    component: Admin,
  }
]


const router = new VueRouter({
  base: process.env.BASE_URL,
  routes
})

export default router
