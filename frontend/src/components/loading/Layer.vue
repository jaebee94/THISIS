<template>
    <div class="loading-layer"> 
        
    </div>
</template>

<script>
export default {
    name: 'loading'
}
</script>

<style>
.loading-layer {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0);
  opacity: 0.7;
  z-index:  99;
}
</style>