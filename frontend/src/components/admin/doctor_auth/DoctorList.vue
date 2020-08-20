<template>
  <div class="">
    <div>
      <table>
        <tr>
          <td>이름: </td>
          <td>{{ doctor.userinfo.username }}</td>
        </tr>
        <tr>
          <td>닉네임: </td>
          <td>{{ doctor.userinfo.nickname }}</td>
        </tr>
        <tr>
          <td>E-mail: </td>
          <td>{{ doctor.userinfo.email }}</td>
        </tr>
      </table>
      <img :src="doctor.doctor.doctorimg" alt="">
    </div>
    <div v-if="doctor.doctor.doctorauth == 'unread'">
      <button @click="onClickDicideButton('accept')">승인</button>
      <button @click="onClickDicideButton('reject')">거절</button>
    </div>
    <div v-if="doctor.doctor.doctorauth == 'accept'">
      승인됨
    </div>
    <div v-if="doctor.doctor.doctorauth == 'reject'">
      거절됨
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex"

export default {

  name: "DoctorList",
  components: {
    // PostListItem,
  },
  props: {
    doctor: {
      default: void 0,
    },
  },
  computed: {},
  data() {
    return {};
  },
  methods: {
    ...mapActions("adminStore", ["decideDoctorauth"]),
    onClickDicideButton(check) {
      var requestdoctor = {
        check: check,
        doctor_id: this.doctor.doctor.doctor_id
      }
      this.decideDoctorauth(requestdoctor)
    }
  },
};
</script>