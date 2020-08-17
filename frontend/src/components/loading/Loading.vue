<template>
    <div>
        <div class="loading-logo"> 
            <img class="heartbeat" src="https://image.flaticon.com/icons/png/128/883/883356.png">
        </div>
        <div class="loading-layer"> 
        
        </div>
    </div>
</template>

<script>
export default {
    name: 'loading'
}
</script>

<style>
/* ========================== 로딩화면 ============================*/

.loading-logo {
    position: fixed;
    top: 45%;
    left: 45%;
    z-index: 100;
}

.loading-layer {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0);
  opacity: 0.5;
  z-index:  99;
}

@-webkit-keyframes heartbeat {
  0% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
  14% {
    -moz-transform: rotate(0deg) scale(1.3);
    -ms-transform: rotate(0deg) scale(1.3);
    -webkit-transform: rotate(0deg) scale(1.3);
    transform: rotate(0deg) scale(1.3);
  }
  28% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
  42% {
    -moz-transform: rotate(0deg) scale(1.3);
    -ms-transform: rotate(0deg) scale(1.3);
    -webkit-transform: rotate(0deg) scale(1.3);
    transform: rotate(0deg) scale(1.3);
  }
  70% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
}
@-moz-keyframes heartbeat {
  0% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
  14% {
    -moz-transform: rotate(0deg) scale(1.3);
    -ms-transform: rotate(0deg) scale(1.3);
    -webkit-transform: rotate(0deg) scale(1.3);
    transform: rotate(0deg) scale(1.3);
  }
  28% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
  42% {
    -moz-transform: rotate(0deg) scale(1.3);
    -ms-transform: rotate(0deg) scale(1.3);
    -webkit-transform: rotate(0deg) scale(1.3);
    transform: rotate(0deg) scale(1.3);
  }
  70% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
}
@-o-keyframes heartbeat {
  0% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
  14% {
    -moz-transform: rotate(0deg) scale(1.3);
    -ms-transform: rotate(0deg) scale(1.3);
    -webkit-transform: rotate(0deg) scale(1.3);
    transform: rotate(0deg) scale(1.3);
  }
  28% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
  42% {
    -moz-transform: rotate(0deg) scale(1.3);
    -ms-transform: rotate(0deg) scale(1.3);
    -webkit-transform: rotate(0deg) scale(1.3);
    transform: rotate(0deg) scale(1.3);
  }
  70% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
}
@keyframes heartbeat {
  0% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
  14% {
    -moz-transform: rotate(0deg) scale(1.3);
    -ms-transform: rotate(0deg) scale(1.3);
    -webkit-transform: rotate(0deg) scale(1.3);
    transform: rotate(0deg) scale(1.3);
  }
  28% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
  42% {
    -moz-transform: rotate(0deg) scale(1.3);
    -ms-transform: rotate(0deg) scale(1.3);
    -webkit-transform: rotate(0deg) scale(1.3);
    transform: rotate(0deg) scale(1.3);
  }
  70% {
    -moz-transform: rotate(0deg) scale(1);
    -ms-transform: rotate(0deg) scale(1);
    -webkit-transform: rotate(0deg) scale(1);
    transform: rotate(0deg) scale(1);
  }
}
/* Styles for old versions of IE */
.heartbeat {
  font-family: sans-serif;
  font-weight: 100;
}

/* :not(:required) hides this rule from IE9 and below */
.heartbeat:not(:required) {
  -webkit-animation: heartbeat 1300ms ease 0s infinite normal;
  -khtml-animation: heartbeat 1300ms ease 0s infinite normal;
  -moz-animation: heartbeat 1300ms ease 0s infinite normal;
  -ms-animation: heartbeat 1300ms ease 0s infinite normal;
  -o-animation: heartbeat 1300ms ease 0s infinite normal;
  animation: heartbeat 1300ms ease 0s infinite normal;
  display: inline-block;
  position: relative;
  overflow: hidden;
  text-indent: -9999px;
  width: 45px;
  height: 45px;
  -moz-transform: rotate(0deg) scale(1);
  -ms-transform: rotate(0deg) scale(1);
  -webkit-transform: rotate(0deg) scale(1);
  transform: rotate(0deg) scale(1);
  -moz-transform-origin: 50% 50%;
  -ms-transform-origin: 50% 50%;
  -webkit-transform-origin: 50% 50%;
  transform-origin: 50% 50%;
}
.heartbeat:not(:required):after, .heartbeat:not(:required):before {
  position: absolute;
  content: "";
  background: #e87;
}
.heartbeat:not(:required):before {
  -moz-border-radius-topleft: 12px;
  -webkit-border-top-left-radius: 12px;
  border-top-left-radius: 12px;
  -moz-border-radius-bottomleft: 12px;
  -webkit-border-bottom-left-radius: 12px;
  border-bottom-left-radius: 12px;
  top: 12px;
  left: 0;
  width: 36px;
  height: 24px;
}
.heartbeat:not(:required):after {
  -moz-border-radius-topleft: 12px;
  -webkit-border-top-left-radius: 12px;
  border-top-left-radius: 12px;
  -moz-border-radius-topright: 12px;
  -webkit-border-top-right-radius: 12px;
  border-top-right-radius: 12px;
  top: 0;
  left: 12px;
  width: 24px;
  height: 12px;
}





/* ========================== 로딩화면 ============================ */
</style>