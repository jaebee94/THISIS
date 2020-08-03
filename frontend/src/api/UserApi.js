/*
 User API 예시
 */

import axios from 'axios';

const requestLogin = (data,callback,errorCallback) => {
    //백앤드와 로그인 통신하는 부분
    axios({
        method : 'GET',
        url : `http://localhost:8080/account/login`,
        params : {
            email : data.email,
            password : data.password
        }
    }).then((response) => {
        console.log(response);
        callback();
    }).catch((ex)=>{
        console.log("ERR!!!!", ex)
        if(ex.response.data.data=="wrong password"){
            alert("비밀번호가 틀립니다.");
        }else if(ex.response.data.data=="wrong email"){
            alert("해당 이메일이 존재하지 않습니다.")
        } else {
            errorCallback();
        }
        
    })

}

const requestJoin = (data,callback,errorCallback) => {
    //백앤드와 회원가입 통신하는 부분
    axios({
        method : 'POST',
        url : `http://localhost:8080/account/signup`,
        data : {
            email : data.email,
            password : data.password,
            nickname : data.nickName
        }
    }).then((response) => {
        console.log(response);
        callback(response);
    }).catch((ex)=>{
        if(ex.response.data.data === 'nickFail'){
            alert("닉네임이 중복입니다.");
        } else if(ex.response.data.data === 'emailFail') {
            alert('이메일이 중복입니다')
        } else {
            console.log("ERR!!!!", ex)
            errorCallback();
        } 
        
    })

}

const requestChange = (data, callback, errorCallback) => {
    // 백엔드와 비밀번호 변경 통신
    axios({
        method: 'GET',
        url: `http://localhost:8080/account/change`, 
        params : {
            email: data.email,
            password: data.password,
            newPassword: data.newPassword
        }
    }).then((response) => {
        callback();
        console.log('CHANGE PASSWORD CONN SUCCESS', response);
    }).catch((ex) => {
        errorCallback();
        console.log('err', ex);
    })
}

const UserApi = {
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback),
    requestJoin:(data,callback,errorCallback)=>requestJoin(data,callback,errorCallback),
    requestChange: (data,callback,errorCallback)=>requestChange(data,callback,errorCallback)
}

export default UserApi
