<template>
    <div class="main wrap">
        <div class="logo wrap">
            <img src="../../assets/images/icon/logo_white.png">
        </div>
    </div>
</template>
<style scoped>
    .main.wrap {
        width: 100%;
        height: 100%;
    }
    .logo.wrap {
        margin-top: 50%;
        width: 100%;
        height: 30%;
    }
    .logo.wrap img {
        width: 60%;
    }
</style>
<script>
export default {
    beforeCreate() {
        document.body.className = "greenbody";
    },
    created () {
        // document.querySelector('html').addEventListener('click', function () {
        //     // console.log("!!!!");
        //     // this.$router.push('/account/join');

        // });
        setTimeout( () => (this.$router.push({ name: 'Login' })), 1500)
    }
}
</script>