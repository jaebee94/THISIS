<template>
  <div class="qna wrap">
    <div class="qna-header">
        <div class="qna-title">
            <a>제목입니다</a>
        </div>
        <div class="qna-tag">
            <a>#태그입니다</a>
        </div>
        <img @click="changeSelectQnA(qnaInfo, 'modify')"  src="../../assets/images/icon/icon_edit_unselect.png">
    </div>
    <div class="qna-footer">
        <span>
            <strong class="qna-writer">강세응</strong>
            <a class="qna-time">2020-08-06</a>
            <img @click="changeSelectQnA(qnaInfo, 'comment')" src="../../assets/images/icon/icon_talk.png">
            <a class="qna-reply">13</a>
        </span>
    </div>
  </div>
</template>

<script>
export default {
    name: 'qna',
    data() {
        return {
            selectedQnA: {},
        }
    },
    methods : {
        changeSelectQnA(qna, sort) {
            this.selectedQnA = qna;
            let info = {
                qnaInfo: this.selectedQnA,
                isHidden: true,
                isModifyHidden : false,
                isQnAHidden: false,
            }
            console.log(qna);
            if (sort == 'comment') {
                info.isQnAHidden = true;
                console.log('comment')
            } else if (sort == 'modify') {
                info.isModifyHidden = true;
                // fetch comments 필요
                console.log('modify')
            }
            this.$emit('send-modify-qna', info);
        },
    },
    props: {
        qnaInfo: {
            default: void 0,
        },
    }
}
</script>

<style scoped>
    .qna.wrap {
        width: 100%;
        height: 80px;
        margin-bottom: 10px;
        background-color: white;
        /* border-top: 2px slategray solid; */
        border-bottom: 1px slategray solid;
    }

    .qna-header {
        height: 60px;
    }

    .qna-title {
        text-align: left;
    }

    .qna-title a {
        font-size: 25px;
        font-weight: 600;
        margin-left: 5%;
    }
    
    .qna-tag {
        text-align: left;
        color: slategray;
    }

    .qna-tag a {
        margin-left: 5%;
    }

    .qna-header img {
        height: 20px;
        position: relative;
        top: -45px;
        left: 45%;
    }

    .qna-footer {
        font-size: 15px;
        text-align: left;
    }

    .qna-footer strong, a {
        margin-left: 5%;
    }

    .qna-reply {
        margin-left: 3px;
    }



    .qna-footer span img {
        margin-left: 5%;
        height: 10px;
        z-index: 80;
    }
</style>