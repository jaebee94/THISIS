<template>
    <div  class="post" ref="post">
      <div class="post-header">
        <table>
          <tr>
            <td>
              <img class="profile-image" src="../../assets/images/icon/icon_default_image.png" />
            </td>
            <td>
              <a class="name">{{ postInfo.userinfo.nickname }}</a>
            </td>
            <td>
              <a class="time">{{timeForToday(postInfo.post.post_date)}}</a>
            </td>
          </tr>
        </table>
      </div>
      <carousel class="carousel wrap" :per-page="1" v-bind:pagination-enabled="false">
        <slide class="myslide">
            <div>
                <img src="../../assets/sample.jpg">
            </div>
        </slide>
        <slide class="myslide" >
             <div class="post-main"><strong>{{postInfo.userinfo.nickname}}</strong> {{ postInfo.post.posts_main }}</div>
        </slide>
      </carousel>
     
      <div class="comment-wrap">
        <div class="comment" v-for="comment in comments" v-bind:key="comment.post_id">
          <div class="comment-header">
            <table>
              <tr>
                <td>
                  <img class="profile-image" src="../../assets/images/icon/icon_default_image.png" />
                </td>
                <td>
                  <a>{{ comment.userinfo.nickname }}</a>
                </td>
                <td>
                  <a>{{ comment.comment.comment_date }}</a>
                </td>
              </tr>
            </table>
          </div>
          <div class="comment-content">{{ comment.comment.comment_main }}</div>
        </div>
      </div>
      <div class="comment-submit">
        <input v-model="commentData.comment_main" placeholder="내용을 입력하세요" />
        <button @click="createComment(commentData), clearCommentData()">댓글달기</button>
      </div>
      <div class="post-footer">
        <img @click="closePost()" src="../../assets/images/icon/icon_close.png" />
      </div>
    </div>
</template>

<script>

export default {
    name: 'Detail',
    props : {
        info: { type: Object}
    },
    data() {
        return {
            postInfo: this.info.postInfo
        }
    },
    methods : {
        timeForToday(time) {
      const today = new Date();
      const timeValue = new Date(time);
      const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
      if (betweenTime < 1) return '방금전';
      if (betweenTime < 60) {
          return `${betweenTime}분전`;
      }
      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
          return `${betweenTimeHour}시간전`;
      }
      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
          return `${betweenTimeDay}일전`;
      }
      return `${Math.floor(betweenTimeDay / 365)}년전`;
    }
    }
}
</script>

<style>

</style>