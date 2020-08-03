<template>
    <div class="main wrap">
        <div class="logo wrap">
            <img src="../../assets/images/icon/logo_green.png" alt="logo_green" />
        </div>
        <div class="email wrap">
            <input 
                v-model="findData.email" 
                id="email"
                type="text" 
                placeholder="이메일을 입력해주세요" 
            />
        </div>

        <div class="name wrap">
            <input 
              v-model="findData.nickName" 
              id="nickName"
              type="text" 
              placeholder="닉네임을 입력해주세요" 
            />
        </div>

        <div class="button wrap">
            <button>비밀번호 찾기</button>
        </div>
    </div>
</template>
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
  .name.wrap {
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
    font-size: 15px;
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
    font-size: 15px;
    padding: 1% 1%;
    outline: none;
    background-color: rgb(0, 171, 132);
    color: rgb(247, 247, 247);
    border: none;
    border-radius: 5px;
  }
</style>
<script>
export default {
    data () {
        return {
            findData: {
                email: null,
                nickName: null
            }
        }
    }
}
</script>