<template>
  <div class="main wrap">
    <div class="logo wrap">
      <img src="../../assets/images/icon/logo_green.png" />
    </div>
    <div class="input-with-label">
      <input v-model="username" id="username" type="text" placeholder="이름을 입력해주세요" />
    </div>
    <div class="input-with-label">
      <input v-model="email" id="email" type="text" placeholder="이메일을 입력해주세요"/>
      <button @click="checkEmail()">중복확인</button>
      <div class="error-text" v-if="error.email">{{error.email}}</div>
      <!-- <div class="confirm-text" v-if="!error.emailConfirm">{{error.emailConfirm}}</div> -->
    </div>
    <div class="input-with-label">
      <input v-model="nickname" id="nickname" type="text" placeholder="닉네임을 입력해주세요"/>
      <button @click="checkNickname()">중복확인</button>
      <!-- <div class="confirm-text" v-if="!error.nicknameConfirm">{{error.nicknameConfirm}}</div> -->
    </div>
    <div class="input-with-label">
      <input v-model="password" id="password" type="password" placeholder="비밀번호를 입력해주세요"/>
      <div class="error-text" v-if="error.password">{{error.password}}</div>
    </div>
    <div class="input-with-label">
      <input
        v-model="passwordConfirm"
        id="passwordConfirm"
        type="password"
        placeholder="비밀번호를 한 번 더 입력해주세요"
      />
      <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
    </div>
    <div class="button wrap">
      <button @click="signup(signupData)" :disabled="!isSubmit" :class="{disabled : !isSubmit}">회원가입</button>
    </div>
  </div>
</template>

<script>
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import { mapActions } from "vuex";

import axios from 'axios'
import SERVER from '@/api/RestApi.js'


export default {
  name: "Signup",
  data() {
    return {
      username: null,
      email: null,
      nickname: null,
      password: null,
      passwordConfirm: null,
      passwordSchema: new PV(),
      confirm: {
        email: false,
        nickname: false,
      },
      error: {
        username: false,
        email: false,
        emailConfirm: true,
        nickname: true,
        nicknameConfirm: true,
        password: false,
        passwordConfirm: false,
      },
      isSubmit: false,
      signupData: {
        email: null,
        nickname: null,
        password: null,
        username: null,
      },
    };
  },
  created() {
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    username: function () {
      this.checkForm();
    },
    email: function () {
      this.checkForm();
    },
    password: function () {
      this.checkForm();
    },
    passwordConfirm: function () {
      this.checkForm();
    },
    nickname: function () {
      this.checkForm();
    },
  },

  methods: {
    ...mapActions('userStore', ['signup' ]),
    checkForm() {
      if (this.email.length >= 0) {
        if (!EmailValidator.validate(this.email)) {
          this.error.email = "이메일 형식이 아닙니다.";
        } else {
          this.error.email = false;
        }
      }
      if (this.username.length == 0) {
        this.error.username = "이름을 입력해주세요.";
      } else {
        this.error.username = false;
      }
      if (this.password.length >= 0) {
        if (!this.passwordSchema.validate(this.password)) {
          this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
        } else {
          this.error.password = false;
        }
      }
      if (this.passwordConfirm.length >= 0) {
        if (this.password !== this.passwordConfirm) {
          this.error.passwordConfirm = "비밀번호가 다릅니다.";
        } else {
          this.error.passwordConfirm = "비밀번호가 일치합니다.";
          this.error.passwordConfirm = false;
        }
      }
      if (this.nickname.length > 0) {
        this.error.nickname = false;
      }
      if (this.nickname.length > 8) {
        //alert('8자리를 넘으면 안됩니다')
        this.nickname = this.nickname.substr(0, 8);
      }

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
      if (this.isSubmit) {
        this.signupData.username = this.username;
        this.signupData.email = this.email;
        this.signupData.nickname = this.nickname;
        this.signupData.password = this.password;
      }
    },
    async checkEmail() {
       await axios.get(SERVER.URL + SERVER.ROUTES.email, {
        params: {
          email: this.email
        }
      })
        .then(() => {
          this.error.emailConfirm = false;
          alert("사용가능한 E-mail 입니다.")
        })
        .catch(err => {
          if (err.response.data.data == "wrong email") {
            this.confirm.email = true;
            alert("이미 사용중인 E-mail 입니다.")
          }
        })

        this.checkForm();
    },
    async checkNickname() {
      await axios.get(SERVER.URL + SERVER.ROUTES.nickname, {
        params: {
          nickname: this.nickname
        }
      })
        .then(() => {
          this.error.nicknameConfirm = false;
          alert("사용가능한 닉네임 입니다.")
        })
        .catch(err => {
          if (err.response.data.data == "wrong nickname") {
            this.confirm.nickname = true;
            alert("이미 사용중인 닉네임 입니다.")
          }
        })

        this.checkForm();
    },


  },

};
</script>

<style scoped>
.main.wrap {
  width: 100%;
  height: 100%;
}
.logo.wrap {
  width: 90%;
  height: 5%;
  text-align: left;
  margin-bottom: 10%;
  padding: 5% 5%;
}
.logo.wrap img {
  width: 20%;
}

.input-with-label {
  padding: 0% 5%;
  /* padding-top: 0%;
  padding-bottom: 0%; */
  width: 90%;
  /* height: 20%; */
  text-align: left;
  /* margin-bottom: 5%; */
 
}

.input-with-label input {
  width: 65%;
  border-radius: 5px;
  border: none;
  height: 40px;
  padding: 1% 1%;
  background-color: rgb(247, 247, 247);
  font-size: 14px;
  outline: none;
  background-color: rgb(247, 247, 247); 
  margin-top: 10%;
  transition-duration: 300ms;
}
.input-with-label:nth-child(2) input {
  width: 100%;
}
.input-with-label:nth-child(5) input {
  width: 100%;
}
.input-with-label:nth-child(6) input {
  width: 100%;
}

.input-with-label input:focus {
  background-color: white;
  color: rgb(0, 171, 132);
  font-weight: 600;
}
.input-with-label button {
  margin-left: 8%;
  width: 25%;
  height: 40px;
  border: none;
  border-radius: 5px;
  outline: none;
  background-color: rgb(0, 171, 132);
  color: white;
  font-weight: 600;
}

input#email {
  width: 65%;
  background-color: rgb(247, 247, 247);
  transition-duration: 300ms;
}

input#email:focus {
  background-color: white;
}

input#nickname {
  width: 65%;
}

.error-text {
  color: red;
  font-size: 12px;
  font-weight: 600;
}

.confirm-text {
  color: green;
  font-size: 12px;
  font-weight: 600;
}

.button.wrap {
  margin-top: 30%;
  width: 90%;
  height: 10%;
  padding: 5%;
}
.button.wrap button {
  width: 100%;
  height: 100%;
  /* color: white; */
  padding: 5px 5px;
  font-size: 30px;
  color: rgb(0, 171, 132);
  border-radius: 5px;
  border: none;
  background-color: rgb(247, 247, 247);
  outline: none;
  transition-duration: 0.5s;
  margin-left: 0px;
}

.button.wrap button:enabled {
  background-color: rgba(0, 171, 132, 1);
  color: rgb(247, 247, 247);
}
</style>