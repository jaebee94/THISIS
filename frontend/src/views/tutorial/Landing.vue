<template>
    <div class="main wrap">
        <div class="logo wrap">
            <img ref="logo" src="../../assets/images/icon/logo_white.png">
        </div>
        <div class="intro"> 
            <a ref="intro">세상의 모든 질병</a>
        </div>
    </div>
</template>
<script>
export default {
    beforeCreate() {
        this.$parent.isLoaded = false;
        document.body.className = "greenbody";
    },
    created () {
        this.$parent.isLoaded = true;

        setTimeout( () => {
            document.body.className = "whitebodyfadeout";
        }, 2500);

        setTimeout( () => (this.$router.push({ name: 'Login' })), 4800)
    }
}
</script>
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
        -webkit-animation: fadein 2s;
    }
    .main.wrap .intro a {
        font-size: 15px;
        font-weight: 600;
        color: white;
        -webkit-animation: fadein 2s;
    }
    @-webkit-keyframes fadein { /* Safari and Chrome */
        from {
            opacity:0;
        }
        to {
            opacity:1;
        }
    }
</style>