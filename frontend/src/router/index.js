import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'

import Landing from '../views/tutorial/Landing.vue'

import Signup from '../views/user/Signup.vue'
import JoinComplete from '../components/user/JoinComplete.vue'

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

import Main_KANG from '../views/main/Main_KANG.vue'
import Profile_KANG from '../components/feed/Profile_KANG.vue'
import Notify_KANG from '../components/feed/Notify_KANG.vue'

import UserProfile from '../components/feed/UserProfile.vue'
import ChangeProfile from '../components/feed/ChangeProfile.vue'
//import { component } from 'vue/types/umd'

import ModifyPost from '../components/feed/ModifyPost.vue'

import DiseaseTest from '../views/main/DiseaseTest.vue'
import NewsTest from '../views/main/NewsTest.vue'

Vue.use(VueRouter)

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
        component: Feed
      },
      {
        path: 'search',
        name: 'Search',
        component: Search
      },
      {
        path: 'upload',
        name: 'Upload',
        component: Upload
      },
      {
        path: 'notify',
        name: 'Notify',
        component: Notify
      },
      {
        path: 'profile',
        name: 'Profile',
        component: Profile
      },
      {
        path: 'user/:id',
        component: UserProfile
      },
      {
        path: 'change',
        name: 'ChangeProfile',
        component: ChangeProfile
      }
    ]
  },
  
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
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
    component: Logout
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
  {
    path: '/main_kang',
    name: 'Main_KANG',
    component: Main_KANG,
    children: [
      {
        path: 'feed',
        component: Feed
      },
      {
        path: 'search',
        component: Search
      },
      {
        path: 'upload',
        component: Upload
      },
      {
        path: 'notify',
        component: Notify_KANG
      },
      {
        path: 'profile',
        component: Profile_KANG
      },
      {
        path: 'user/:id',
        component: UserProfile,
        children: [
          {
            path: 'modify',
            component: {modify : ModifyPost}
          }
        ]
      },
      {
        path: 'change',
        component: ChangeProfile
      },
      {
        path: 'modify',
        component: ModifyPost
      }
    ]
  },
]


const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
