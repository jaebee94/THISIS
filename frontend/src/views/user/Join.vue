
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="user join wrapC">
    <h1>가입하기</h1>
    <div class="form-wrap">
      <div class="input-with-label">
        <input v-model="nickName" id="nickname" placeholder="닉네임을 입력하세요." type="text" />
        <label for="nickname">닉네임</label>
      </div>

      <div class="input-with-label">
        <input v-model="email" id="email" placeholder="이메일을 입력하세요." type="text" autocapitalize/>
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <div class="input-with-label">
        <input v-model="password" id="password" :type="passwordType" placeholder="비밀번호를 입력하세요." />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          id="password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
        />
        <label for="password-confirm">비밀번호 확인</label>
        <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
      </div>
    </div>

    <label>
      <input v-model="isTerm" type="checkbox" id="term" />
      <span>약관을 동의합니다.</span>
    </label>

    <span @click="termPopup=true">약관보기</span>

    <button 
      class="btn-bottom"
      @click="onJoin"
      :disabled="!isSubmit"
      :class="{disabled : !isSubmit}"
    >가입하기</button>
  </div>
</template>

<script>
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import UserApi from '../../api/UserApi';
import store from '../../vuex/store';

export default {
  created() {
    // this.component = this;

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
    nickName: function(v) {
      this.checkNick();
    },
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    }
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
      nickName: "",
      isTerm: false,
      isLoading: false,
      error: {
        email: false,
        password: false,
        nickName: false,
        passwordConfirm: false,
        term: false
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false
    };
  },
  methods: {
    checkNick() {
      if(this.nickName.length > 8) {
        //alert('8자리를 넘으면 안됩니다')
        this.nickName = this.nickName.substr(0, 8);
      }
    },
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onJoin() {
      if(this.password !== this.passwordConfirm) {
        alert("비밀번호가 일치하지 않습니다요우~");
        return;
      }
      if(!this.isTerm) {
        alert("약관에 동의하라구요우~")
        return;
      }
      let{email, password, nickName} = this;
      let data = {
        email,
        password,
        nickName
      }
      //this.isSubmit = false;

      UserApi.requestJoin(
        data,
        res => {
          // 통신하기
          // 통신 끝나면 로그인 페이지로 넘겨야 함
          console.log(res.data.data);
          this.isSubmit = true;
          this.$store.commit('transferString', this.nickName);
          this.$router.push("/user/joinComplete");
        },
        error => {
          this.isSubmit = true;
          window.location.href="/error";
        }
      )
    }
  }
};
</script>