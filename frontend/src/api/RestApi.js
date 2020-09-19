export default {

  URL: 'https://i3a301.p.ssafy.io:8089/THISIS', // 서버 포트
  //  URL: 'http://localhost:8089/THISIS',
  ROUTES: {
    myprofile: '/account/0',
    user: '/account',
    profile: '/account/profile/',
    signup: '/account/signup',
    email: '/account/email',
    nickname: '/account/nickname',
    login: '/account/login',
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
    tagrelation: '/tagrelation',

    notification: '/notification/follower/',  // 해당 user_id 가 follower 인 레코드 목록 조회
    requests: '/notification/followee/',  // 해당 user_id 가 followee 인 레코드 목록 조회
    following : '/notification',  // 
    

    follow : '/follow',
    followee: '/follow/followee/',
    follower: '/follow/follower/',

    searchUser: '/account/search',
    modify:'/articles/',

    refreshAccessToken : '/account/refreshAccessToken'
    disease: '/disease/user',
    subscribe: '/subscribe',

    // admin
    reportedPosts: '/admin/post',
    managePost: '/admin/post',

    reportedUsers: '/admin/user',
    manageUser: '/admin/user',
    
    doctors: '/admin/doctor-auth/check',
    doctorauth: '/admin/doctor-auth',

    qnasmain : '/articles/qna/main',
    qnastitle : '/articles/qna/title',
    qnasdisease : '/articles/qna/disease',

    police : '/police',
    UploadDoctorAuth : '/doctor/register'
  }
}