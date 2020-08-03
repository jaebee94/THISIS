export default {
  URL: 'http://i3a301.p.ssafy.io:8088/THISIS', // 서버 포트
  // URL: 'https://72dd9fcfa098.ngrok.io/THISIS',
  //URL: 'https://41fa72ea95de.ngrok.io/THISIS',
  //  URL: 'http://localhost:8088/THISIS',
  ROUTES: {
    myprofile: '/account/0',
    user: '/account/',
    profile: '/account/profile/',
    signup: '/account/signup',
    email: '/account/email',
    nickname: '/account/nickname',
    login: '/account/login',
    logout: '/account/',
    withdrawal: '/account/{user_id}',

    posts: '/articles',
    scrap: '/scrap',
    health: '/health',
    healthCount: '/health/post/',
    comment: '/comment',

    notification: '/notification/follower/',
    requests: '/notification/followee/',
    following : '/notification',
    

    follow : '/follow',
    followee: '/follow/followee/',
    follower: '/follow/follower/',

    searchUser: '/account/search',
    modify:'/articles/'
  }
}