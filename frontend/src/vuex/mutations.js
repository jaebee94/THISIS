export default {
    transferString (state, payload){
        console.log("store.js 파일에서 받은  txtInput값 : " + payload)
        state.userName = payload
    }  
}