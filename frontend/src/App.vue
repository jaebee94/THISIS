<template>
  <div id="app">
    <head>
      <title>THISIS</title>
    </head>
    <div v-if="this.isHidden" ref="layer" class="layer"></div>
    <router-view/>
  </div>
</template>

<script>
export default {
  el: '#app',
  data () {
    return {
      isHidden: false
    }
  }
}
</script>

<style>
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
