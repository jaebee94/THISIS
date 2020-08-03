<template>
    <div class="user join complete wrapC">
        <div class="img-wrap">
            <img src="https://image.flaticon.com/icons/png/128/3199/3199830.png">
        </div>
        <div class="welcome-wrap">
            <h1>{{$store.state.userName}}님</h1>
            <h1>환영합니다</h1>
        </div>
        <div class="email-wrap">
            <a>인증번호가 발송되었습니다</a><br>
            <a>이메일을 확인해주세요</a>
        </div>
        <div class="button-wrap">
            <button @click="goLogin()">
                로그인
            </button>
        </div>
    </div>
</template>
<style scoped>
    .user.join.complete.wrapC {
        width: 100%;
        height: 100%;
    }
    .img-wrap {
        margin-top: 15%;
        padding-top: 5%;
        text-align: center;
    }
    .welcome-wrap {
        margin-top: 15%;
    }
    .welcome-wrap h1:nth-child(1) {
        font-size: 40px;
        margin-bottom: 10px;
    }
    .welcome-wrap h1:nth-child(2) {
        font-size: 30px;
    }
    .email-wrap a {
        font-size: 20px;
        color: gray;
        text-decoration: none;
    }
    .button-wrap {
        width: 100%;
        height: 8%;
        position: fixed;
        bottom: 10px;
        left: 0;
    }
    .button-wrap button {
        margin-left: 5%;
        width: 90%;
        height: 100%;
        color: white;
        font-size: 25px;
        border-radius: 5px;
        background-color: orange;
    }
</style>
<script>
import store from '../../vuex/store';

export default {
    methods: {
        goLogin(){
            this.$router.push("/");
        }
    }
}
</script>