<template>
  <div class="main wrap">
      <div class="logo wrap">
        <img src="../../assets/images/icon/logo_green.png" alt="logo_green" />
      </div>

      <div class="email wrap">
        <input 
          v-model="loginData.email" 
          id="email"
          type="text" 
          placeholder="E-mail" 
        />
      </div>

      <div class="password wrap">
        <input 
          v-model="loginData.password" 
          id="password"
          type="password" 
          placeholder="Password" 
        />
      </div>
      <div class="button wrap">
        <button @click="login(loginData)">LOGIN</button>
      </div>
      <div class="help password">
        <p>비밀번호를 잊으셨나요?
          <router-link to="/account/findPassword" class="btn--text">비밀번호찾기</router-link>
        </p>
      </div>
      <div class="help join">
        <p>아직 회원이 아니신가요?
          <router-link to="/account/signup" class="btn--text">가입하기</router-link>
        </p>
      </div>

  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'Login',
  data() {
    return {
      loginData: {
        email: null,
        password: null
      }
    }
  },
  beforeCreate() {
    document.body.className = "whitebody";
  },
  methods: {
    ...mapActions(['login'])
    // ,login(loginData){
    //   this.$store.dispatch("login",loginData);
    // }
  },
  created() {
    console.log(this)
    console.log(this.$cookies)
  }
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
    width: 100%;
    margin-top: 25%;
    margin-bottom: 0%;
  }
  .password.wrap {
    width: 100%;
    margin-top: 5%;
  }

  input {
    padding: 1% 1%;
    width: 80%;
    height: 100%;
    text-align: center;
    color: rgb(0, 171, 132);
    outline: none;
    font-size: 25px;
    font-weight: 700;
    background-color: rgb(247, 247, 247);
    border: none;
    border-radius: 5px;
  }

  .button.wrap {
    margin-top: 5%;
    width: 100%;
    height: 10%;
  }
  
  button {
    width: 82%;
    height: 100%;
    font-size: 25px;
    padding: 1% 1%;
    outline: none;
    background-color: rgb(0, 171, 132);
    color: rgb(247, 247, 247);
    border: none;
    border-radius: 5px;
  }
</style>