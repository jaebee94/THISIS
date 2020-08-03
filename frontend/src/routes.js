

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import JoinComplete from './views/user/JoinComplete.vue'
import Change from './views/user/Change.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import ErrorPage from './views/error/error.vue'
import PageNotFound from './views/error/PageNotFound.vue'

export default [


    {
        path : '/',
        name : 'Login',
        component : Login
    },
    {
        path : '/user/join',
        name : 'Join',
        component : Join
    },
    {
        path : '/user/joinComplete',
        name : 'JoinComplete',
        component : JoinComplete
    },
    {
        path: '/user/change',
        name: 'Change',
        component: Change
    },
    {
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain
    },
    {
        path: '/error',
        name: 'Error',
        component: ErrorPage
    },
    {
        path: '*',
        redirect: '/404'
    },
    {
        path: '/404',
        name: 'PageNotFound',
        component: PageNotFound
    },
    {
        path : '/components',
        name : 'Components',
        component : Components
    }
    
]
