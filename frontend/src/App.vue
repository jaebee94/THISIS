<template>
  <div id="app">
    <head>
      <title>THISIS</title>
    </head>
    <div v-if="this.isHidden" ref="layer" class="layer"></div>
    <loading v-if="!isLoaded"></loading>
    <router-view/>
  </div>
</template>

<script>
import loading from './components/loading/Loading.vue';

export default {
  el: '#app',
  components: {
    loading
  },
  data () {
    return {
      isHidden: false,
      isLoaded: false,
    }
  },
  mounted () {
    this.isLoaded = true;
  }
}
</script>

<style>
@import url(https://fonts.googleapis.com/earlyaccess/notosanskr.css); *{font-family: 'Noto Sans KR', sans-serif;}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
body {
  margin: 0px 0px auto;
}
.greenbody {
  background-color: rgb(0, 171, 132);
}
.whitebody {
  background-color: white;
}
.whitebodyfadeout {
  background-color: white;
  -webkit-animation: fadein 2s;
}
@-webkit-keyframes fadein { /* Safari and Chrome */
  from {
    background-color: rgb(0, 171, 132);
  }
  to {
    background-color: wthie;
  }
}
.lockbody {
  overflow-y: hidden;
}
.layer {
  z-index: 98;
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: black;
  opacity: 0.7;
}
</style>
