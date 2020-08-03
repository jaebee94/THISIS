<template>
    <div class="post-content-modify">
        <div class="feed-header">
            <table>
                <tr>
                    <td><img class="profile-image" src="../../assets/images/icon/icon_default_image.png"></td>
                    <td><a class="name">{{mPost.name}}</a></td>
                    <td><a class="time">{{mPost.time}}</a></td>
                </tr>
            </table>
        </div>
        <div class="post-content"> 
            <textarea v-model="mPost.content"></textarea>
        </div>
        <div class="post-modify-btn">
            <button @click="modifyPostClick()">수정하기</button>
        </div>
        <div class="modify-footer">
            <img @click="closeModify(mPost.post_id)" src="../../assets/images/icon/icon_close.png">
        </div>
    </div>
</template>

<script>
export default {
    modifyPost(post) {
        this.mPost = post;
        this.$parent.$parent.isHidden = true;
        this.isPostModify = true;
    },
    modifyPostClick(post_id) {
        console.log(this.mPost);
        this.posts.forEach(function (element){
            if(element.post_id == post_id) {
                element = this.mPost;
            }
        })
        this.closeModify();
    },
    closeModify(){
        this.$parent.$parent.isHidden = false;
        this.isPostModify = false;
    },
}
</script>

<style>
    /* 수정할 포스트 내용 보이기 */
    .post-content-modify {
        position: fixed;
        z-index: 99;
        margin-left: 4%;
        top: 20%;
        width: 92%;
        height: 400px;
        background-color: white;
        border-radius: 5px;
    }

    .post-content textarea {
        padding: 5px 5px;
        font-size: 20px;
        margin-top: 10px;
        width: 90%;
        height: 280px;
        word-wrap: break-word;
        transition-duration: 300ms;
        border: none;
    }

    .post-content textarea:focus {
        outline: none;
        border:rgb(0, 171, 132) 3px solid;
    }

    .post-modify-btn {
        width: 100%;
        height: 50px;
    }

    .post-modify-btn button {
        width: 90%;
        height: 90%;
        border: none;
        border-radius: 5px;
        background-color: rgb(0, 171, 132);
        outline: none;
        color: white;
        font-size: 20px;
    }

    .modify-footer {
        position: absolute;
        width: 100%;
        height: 10%;
        bottom: -12%;
    }

    .modify-footer img {
        height: 80%;
    }
</style>