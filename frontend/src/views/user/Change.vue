<template>
  <div class="user join wrapC">
    <h1>비밀번호 변경</h1>
    <div class="form-wrap">
      <div class="input-with-label">
        <input v-model="email" id="email" placeholder="이메일을 입력하세요." type="text" />
        <label for="email">이메일</label>
      </div>

      <div class="input-with-label">
        <input v-model="password" id="password" :type="passwordType" placeholder="현재 비밀번호를 입력하세요." />
        <label for="password">현재 비밀번호</label>
      </div>

      <div class="input-with-label">
        <input v-model="newPassword" id="new-password" :type="passwordType" placeholder="새 비밀번호를 입력하세요." />
        <label for="new-password">새 비밀번호</label>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          id="new-password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
        />
        <label for="password-confirm">비밀번호 확인</label>
      </div>
    </div>

    <button
        class="btn btn--back btn--login"
        @click="onChange"
        :disabled="!isSubmit"
        :class="{disabled : !isSubmit}"
      >저장</button>
  </div>
</template>

<script>
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import UserApi from '../../api/UserApi';
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
      newPassword: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
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
    onChange() {
       if(this.password==this.newPassword){
         alert("이미 사용하신 비밀번호입니다! ");
         return;
       }
       if(this.newPassword !== this.passwordConfirm) {
        alert("새 비밀번호를 확인해주세요.");
        return;
      }

      let{email, password, newPassword} = this;
      let data = {
        email,
        password,
        newPassword,
      }
      this.isSubmit = false;

      UserApi.requestChange(
        data,
        res => {
          // 통신하기
          // 통신 끝나면 로그인 페이지로 넘겨야 함
          console.log('변경 성공', res)
          this.isSubmit = true;

          this.$router.push("/feed/main");
        },
        error => {
          console.log('변경 실패', error)
          //alert('변경에 실패했네요ㅠㅠ')
          this.isSubmit = true;
          window.location.href="/error";
        }
      )
    }
  }
};
</script>
