<template>
  <div></div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'LogoutView',
    methods: {
        ...mapActions('userStore', ['logout'])
    },
    created() {
        this.logout()
    }
}
</script>