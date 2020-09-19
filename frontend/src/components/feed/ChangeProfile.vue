<template>
  <div class="change wrap">
    <div class="tabs">
      <div class="tab-container">
        <div
          class="tab"
          v-for="(tab, index) in profileTabs"
          v-bind:key="tab"
          v-bind:class="{active: profileTab === index}"
          @click="profileTab = index"
        >
          <img :src="tab" />
        </div>
      </div>
      <div v-if="profileTab == 0">
        <div class="profile-photo">
          <div class="profile-modify-image">
            <img :src="imgsrc" />
          </div>
          <div class="profile-image-button">
            <button @click="onClickImageUpload">프로필 사진 변경</button>
          </div>
          <input ref="profileImg" type="file" hidden @change="onChangeImages" />
        </div>
        <div class="input-with-label">
          <input v-model="email" id="email" type="text" disabled />
        </div>
        <div class="input-with-label">
          <input v-model="nickname" id="nickname" type="text" placeholder="닉네임을 입력해주세요" />
          <button @click="checkNickname()">중복확인</button>
          <div class="confirm-text" v-if="!error.nicknameConfirm">{{ error.nicknameConfirm }}</div>
        </div>
        <div class="input-with-label">
          <input v-model="introduction" id="introduction" type="text" />
        </div>

        <div class="button-wrap">
          <button
            @click="changeUserInfo(changeInfo)"
            :disabled="!isSubmit"
            :class="{disabled : !isSubmit}"
          >프로필 변경</button>
        </div>
      </div>
      <div v-else-if="profileTab == 1">
        <!--모달 시작 (의사 업로드) -->
        <div class="doctor-wrap" v-if="!this.isDoctorHidden">
          <div class="doctor-title">
            <a id="doctor1">의료진 인증 화면입니다.</a>
            <br />
            <a id="doctor2">증빙 자료를 첨부해주세요</a>
          </div>
          <div class="doctor-content">
            <div class="doctor-photo">
              <div class="doctor-modify-image" v-if="this.Doctorimgsrc != null">
                <img :src="Doctorimgsrc" />
              </div>
              <div class="doctor-image-button">
                <button @click="onClickDoctorImageUpload">사진 첨부하기</button>
              </div>
              <input ref="DoctorImg" type="file" hidden @change="onChangeDoctorImages" />
            </div>
          </div>
          <button class="doctor-submit-button" @click="doctorSubmit">인증 제출하기</button>
          <div class="doctor-footer">
            <img @click="close()" src="../../assets/images/icon/icon_close.png" />
          </div>
        </div>
        <!--모달 끝-->

        <div class="input-with-label">
          <input v-model="password" id="password" type="password" placeholder="비밀번호를 입력해주세요" />
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
        <div class="password-button-wrap">
          <button
            @click="changeUserInfo(changeInfo)"
            :disabled="!isSubmitPassword"
            :class="{disabled: !isSubmitPassword}"
          >비밀번호 변경</button>
        </div>
        <div class="important-auth-wrap">
          <div class="doctor-auth-wrap" @click="showDoctor">
            <img src="../../assets/images/icon/icon_doctor.png">
            <div><a>의료진 인증하기</a></div>
          </div>
          <div class="signout-wrap" @click="signOut">
            <img src="../../assets/images/icon/icon_signout.png">
            <div><a>THISIS 떠나기</a></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PV from "password-validator";
import axios from "axios";
import SERVER from "@/api/RestApi.js";
import { mapState, mapActions } from "vuex";

export default {
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

    this.email = this.profileData.userInfo.email;
    this.nickname = this.profileData.userInfo.nickname;
    this.introduction = this.profileData.userInfo.introduction;
    if (this.profileData.userInfo.userimage != null)
      this.imgsrc = this.profileData.userInfo.userimage;
  },

  computed: {
    ...mapState("userStore", ["loginData", "profileData"]),
    // ...mapState("profileStore", ["profileData"]),
  },
  data() {
    return {
      // store에서 나중에 UserInfo 받아서 넣어야 함
      email: "",
      nickname: "",
      introduction: "",
      username: null,
      password: null,
      passwordConfirm: null,
      passwordSchema: new PV(),
      changeInfo: {
        formData: null,
        userInfo: {
          email: "",
          nickname: "",
          introduction: "",
          userimage: "",
        },
      },
      DoctorFormData: null,
      confirm: {
        email: false,
        nickname: false,
      },
      error: {
        nickname: false,
        intro: false,
        // nicknameConfirm: true,
        password: false,
        passwordConfirm: false,
      },
      isSubmit: false,
      isSubmitPassword: false,
      profileTab: 0,
      profileTabs: [
        require("../../assets/images/icon/icon_info.png"),
        require("../../assets/images/icon/icon_key.png"),
      ],
      isDoctorHidden: true,
      imgsrc: require("../../assets/user2.png"),
      Doctorimgsrc: null,
    };
  },

  watch: {
    nickname: function () {
      this.checkFormInfo();
    },
    introduction: function () {
      this.checkFormInfo();
    },
    password: function () {
      this.checkFormPassword();
    },
    passwordConfirm: function () {
      this.checkFormPassword();
    },
  },
  methods: {
    ...mapActions("userStore", [
      "changeUserInfo",
      "getAccessData",
      "UploadDoctorAuth",
    ]),
    checkFormInfo() {
      if (this.nickname.length == 0) {
        this.error.nickname = false;
      } else {
        this.error.nickname = true;

        if (this.nickname.length > 8) {
          this.nickname = this.nickname.substr(0, 8);
        }
      }

      if (this.introduction.length == 0) {
        this.error.intro = false;
      } else {
        this.error.intro = true;

        if (this.introduction.length > 15) {
          this.intro = this.intro.substr(0, 15);
        }
      }

      if (this.error.nickname && this.error.intro) {
        this.isSubmit = true;
        this.changeInfo.userInfo.user_id = this.loginData.user_id;
        this.changeInfo.userInfo.email = this.email;
        this.changeInfo.userInfo.nickname = this.nickname;
        this.changeInfo.userInfo.introduction = this.introduction;
      } else {
        this.isSubmit = false;
      }
    },
    checkFormPassword() {
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
          this.error.passwordConfirm = false;
        }
      }
      if (!this.error.password && !this.error.passwordConfirm) {
        this.isSubmitPassword = true;
        this.changeInfo.userInfo.password = this.passwordConfirm;
      } else {
        this.isSubmitPassword = false;
      }
    },
    checkNickname() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.nickname, {
          params: {
            nickname: this.nickname,
          },
        })
        .then(() => {
          this.error.nicknameConfirm = true;
          alert("사용가능한 닉네임 입니다.");
        })
        .catch((err) => {
          if (err.response.data.data == "wrong nickname") {
            this.confirm.nickname = false;
            alert("이미 사용중인 닉네임 입니다.");
          }
        });
    },
    onClickImageUpload() {
      this.$refs.profileImg.click();
    },
    onChangeImages(e) {
      const file = e.target.files[0];
      var formData = new FormData();
      formData.append("upload_file", file);
      this.changeInfo.formData = formData;
      this.imgsrc = URL.createObjectURL(file);
    },
    signOut() {
      var res = confirm("정말로 탈퇴하시겠습니까?");
      if (res) {
        alert("개새끼...");
      } else {
        alert("그래요 잘 생각했어요");
      }
    },
    showDoctor() {
      this.$parent.$parent.isHidden = true;
      this.isDoctorHidden = false;
    },
    close() {
      this.$parent.$parent.isHidden = false;
      this.isDoctorHidden = true;
    },
    onChangeDoctorImages(e) {
      const file = e.target.files[0];
      var formData = new FormData();
      formData.append("upload_file", file);
      this.DoctorFormData = formData;
      this.Doctorimgsrc = URL.createObjectURL(file);
    },
    onClickDoctorImageUpload() {
      this.$refs.DoctorImg.click();
    },
    doctorSubmit() {
      this.UploadDoctorAuth(this.DoctorFormData);
      alert("성공적으로 서버에 등록되었습니다.");
      this.close();
    },
  },
};
</script>

<style>
.change.wrap {
  width: 100%;
  height: 100%;
}

.profile-photo {
  width: 100%;
}

.profile-modify-image {
  width: 100%;
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 15px;
  margin-bottom: 15px;
}

.profile-modify-image img {
  position: relative;
  width: 150px;
  height: 150px;
  object-fit: cover;
  border-radius: 70%;
}

.profile-image-button button {
  width: 40%;
  height: 30px;
  border: none;
  border-radius: 5px;
  background-color: rgb(0, 171, 132);
  color: white;
  font-weight: 600;
  outline: none;
  transition-duration: 300ms;
}

.profile-image-button button:focus {
  background-color: rgb(247, 247, 247);
  color: black;
}

.input-with-label input {
  width: 90%;
  border-radius: 5px;
  margin-top: 5%;
  border: none;
  height: 40px;
  padding: 1% 1%;
  background-color: rgb(247, 247, 247);
  font-size: 14px;
  outline: none;
  transition-duration: 300ms;
}
.input-with-label input:focus {
  background-color: rgb(0, 171, 132);
  color: white;
  font-weight: 600;
}

.input-with-label button {
  margin-left: 5%;
  width: 25%;

  height: 40px;
  border: none;
  border-radius: 5px;
  outline: none;
  background-color: rgb(0, 171, 132);
  color: white;
  font-weight: 600;
}

.input-with-label #nickname {
  width: 60%;
}

.input-with-label #email {
  margin-top: 10%;
  background-color: lightgray;
}

.input-with-label #password {
  margin-top: 10%;
}

.tabs {
  width: 100%;
  height: 70%;
}
.tab-container {
  display: flex;
}
.tab {
  width: 50%;
  height: 40px;
  vertical-align: middle;
  background-color: rgb(247, 247, 247);
  border-radius: 0;
  border-bottom: 3px rgb(247, 247, 247) solid;
  transition-duration: 300ms;
}

.tab img {
  margin-top: 8px;
  height: 60%;
}

.tab.active {
  border-bottom: 3px rgb(0, 171, 132) solid;
  background-color: rgb(247, 247, 247);
}

.button-wrap {
  margin-top: 10%;
}

.password-button-wrap {
  margin-top: 10%;
}

.button-wrap button {
  width: 90%;
  height: 40px;
  border: none;
  background-color: rgb(0, 171, 132);
   color: white;
  font-size: 20px;
  font-weight: 600;
  border-radius: 5px;
}

.password-button-wrap button {
  width: 90%;
  height: 40px;
  border: none;
  background-color: rgb(138, 135, 135);
   color: black; 
  font-size: 20px;
  font-weight: 600;
  border-radius: 5px;
}

.password-button-wrap button:enabled {
  background-color: rgba(0, 171, 132, 1);
  color: rgb(247, 247, 247);
}

.error-text {
  color: rgb(220, 0, 27);
}

.important-auth-wrap {
  width: 100%;
  height: 120px;
  margin-top: 10%;
  display: flex;
  font-size: 13px;
  font-weight: 600;
}

.important-auth-wrap .doctor-auth-wrap img {
  /* width: 50%; */
  height: 80px;
}

.important-auth-wrap .signout-wrap img {
  height: 70px;
  margin-top: 5px;
  margin-bottom: 5px;
}

.important-auth-wrap .doctor-auth-wrap {
  width: 40%;
  height: 100%;
  margin-left: 5%;
  padding-top: 5%;
  border: none;
  border-radius: 15px;
  outline: none;
  color: rgb(0, 171, 132);
  background-color: rgb(247, 247, 247);
  transition-duration: 300ms;
  /* color: white;
  background-color: rgb(79, 158, 129); */
}

.important-auth-wrap .doctor-auth-wrap:hover {
  background-color: rgb(200, 200, 200);
}

.important-auth-wrap .signout-wrap {
  width: 40%;
  height: 100%;
  margin-left: 10%;
  padding-top: 5%;
  border: none;
  border-radius: 15px;
  outline: none;
  color: rgb(220, 0, 27);
  background-color: rgb(247, 247, 247);
  transition-duration: 300ms;
  /* color: white;
  background-color: rgb(220, 0, 27); */
}

.important-auth-wrap .signout-wrap:hover {
  background-color: rgb(200, 200, 200);
}

.doctor-wrap {
  position: fixed;
  z-index: 98;
  top: 10%;
  width: 92%;
  height: 70%;
  background-color: white;
  border-radius: 5px;
  margin: auto 4%;
}

.doctor-footer {
  position: absolute;
  width: 100%;
  height: 10%;
  bottom: -12%;
}

.doctor-footer img {
  height: 80%;
}

.doctor-submit-button {
  width: 90%;
  height: 40px;
  border: none;
  background-color: rgb(0, 171, 132);
  color: white;
  font-size: 20px;
  font-weight: 600;
  border-radius: 5px;
}

.doctor-modify-image {
  width: 100%;
  height: 330px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 15px;
  margin-bottom: 15px;
}

.doctor-modify-image img {
  width: 100%;
  max-height: 350px;
  object-fit: cover;
}

.doctor-photo {
  width: 100%;
  height: 380px;
}

#doctor1 {
  font-size: 20px;
  font-weight: bold;
}

.doctor-title {
  padding: 10px;
}
</style>