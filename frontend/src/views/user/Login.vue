<template>
  <div class="main wrap">
      <div class="logo wrap">
        <img src="../../assets/images/icon/logo_green.png" alt="logo_green" />
        <div> 
            <a style="color: rgb(0, 171, 132); font-size: 15px; font-weight: 600;">세상의 모든 질병</a>
        </div>
      </div>

      <div class="email wrap">
        <input 
          v-model="loginData.email" 
          id="email"
          type="text" 
          placeholder="이메일을 입력해주세요"
        />
      </div>
      <div class="password wrap">
        <input 
          v-model="loginData.password" 
          id="password"
          type="password" 
          placeholder="비밀번호를 입력해주세요" 
          v-on:keyup.enter="login(loginData)"
        />
      </div>
      <div class="login-button-wrap"> 
        <button @click="login(loginData)">로그인</button>
      </div>

      
      <!-- <div class="button wrap">
        <button @click="login(loginData)">LOGIN</button>
      </div> -->
      <div class="help password">
        <router-link to="/account/findPassword" class="btn--text"><a>비밀번호를 잊으셨나요?</a></router-link>
      </div>
      <div class="help join">
        <router-link to="/account/signup" class="btn--text"><a>아직 회원이 아니신가요?</a></router-link>
      </div>
      <!-- <loading></loading> -->
  </div>
</template>

<script>
import { mapActions } from 'vuex'
// import loading from '../../components/loading/Loading.vue';

export default {
  name: 'Login',
  components: {
    // loading,
  },
  data() {
    return {
      loginData: {
        email: null,
        password: null
      }
    }
  },
  mounted() {
    this.$parent.isLoaded = true;
  },
  beforeCreate() {
    document.body.className = "whitebody";
    this.$parent.isLoaded = false;
  },
  methods: {
    ...mapActions('userStore', ['login']),
  },
};
</script>

<style scoped>
  .main.wrap {
    width: 100%;
    height: 100%;
  }

  .logo.wrap {
    margin-top: 25%;
    width: 100%;
    height: 30%;
  }

  .logo.wrap img{
    width: 60%;
  }

  .email.wrap {
    width: 80%;
    margin-top: 40%;
    margin-left: 10%;
    margin-bottom: 15px;
    height: 30px;
    /* margin-top: 25%;
    margin-bottom: 0%; */
  }
  .password.wrap {
    width: 80%;
    margin-left: 10%;
    height: 30px;
    /* margin-top: 5%; */
  }

  .login-button-wrap{
    margin-top: 15px;
    width: 80%;
    height: 40px;
    margin-left: 10%;
  }

  .login-button-wrap button {
    width: 100%;
    height: 100%;
    font-size: 15px;
    font-weight: 600;
    padding-left : 3%;
    padding-right: 3%;
    outline: none;
    background-color: rgb(0, 171, 132);
    color: rgb(247, 247, 247);
    border: none;
    border-radius: 5px;
  }

  input {
    padding: 1% 3%;
    width: 94%;
    height: 100%;
    text-align: left;
    color: rgb(0, 171, 132);
    outline: none;
    font-size: 15px;
    font-weight: 700;
    background-color: rgb(247, 247, 247);
    border: 1px rgb(230, 230, 230) solid;
    border-radius: 5px;
  }

  input:focus {
    background-color: white;
  }

  .help.password {
    margin-top: 10px;
  }

  .help.password a {
    color: slategray;
    text-decoration: none;
  }

  .help.join a {
    color: slategray;
    text-decoration: none;
  }

</style>