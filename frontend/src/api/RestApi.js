export default {
  URL: 'http://i3a301.p.ssafy.io:8089/THISIS', // 서버 포트
  // URL: 'https://72dd9fcfa098.ngrok.io/THISIS',
  //URL: 'https://41fa72ea95de.ngrok.io/THISIS',
  //  URL: 'http://localhost:8088/THISIS',
  ROUTES: {
    myprofile: '/account/0',
    user: '/account/',
    updateProfile: '/account',
    profile: '/account/profile/',
    signup: '/account/signup',
    email: '/account/email',
    nickname: '/account/nickname',
    login: '/account/login',
    logout: '/account/',
    withdrawal: '/account/{user_id}',
    search : '/account/search',
    uploadProfile: '/account/upload',

    // posts: '/articles',
    post : '/articles',
    posts: '/articles/new',
    qnas: '/articles/qna',
    upload: '/articles/upload',
    scrap: '/scrap',
    health: '/health',
    healthCount: '/health/post/',
    comment: '/comment',

    notification: '/notification/follower/',  // 해당 user_id 가 follower 인 레코드 목록 조회
    requests: '/notification/followee/',  // 해당 user_id 가 followee 인 레코드 목록 조회
    following : '/notification',  // 
    

    follow : '/follow',
    followee: '/follow/followee/',
    follower: '/follow/follower/',

    disease: '/disease/user',
    subscribe: '/subscribe'

  }
}