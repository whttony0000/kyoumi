<style>
    #articleDetailContainer {
        margin: 60px auto;
        display: table;
        width: 1000px;
    }

    #title {
        display: inline-block;
    }

    .lead {
        font-size: 21px;
        margin-bottom: 20px;
        font-weight: 20px;
        line-height: 1.4;
    }

    #individualName {
        text-transform: none;
    }

    #content {
        margin: 0.5em 0 0.5em 0;
        font-size: 110%;
        width: 1000px;
        overflow: scroll;
    }

    .edit-btn {
        float: right;
        margin: 20px 20px auto;
    }

    .bdsharebuttonbox {
        float: right;
        margin: 0 20px 0 0;
    }

    .like {
        margin: 0 0 0 0;
        cursor: pointer;
        font-size: 24px;
        color: #D7191A;
        border-radius: 10px;
        border: 1px solid #D7191A;
        padding: 3px 10px;
    }

    .bookmark {
        float: right;
        font-size: 32px;
        color: #20A0FF;
        margin-right: 10px;
        cursor: pointer;
    }

    .share {
        float: right;
        font-size: 32px;
        color: #2dc2ce;
        cursor: pointer;
        margin-right: 15px;
    }

    .article-rate {
        display: inline-block;
    }

    a {
        color: #20A0FF;
    }

    #individualName {
        font-size: 16px;
    }

    .article-read-cnt {
       margin: 0 10px;
    }

    .article-memo {
        margin: 5px;
        display: inline-block;
        font-size: 16px;
        border-left: 3px solid #ccc;
        padding: 5px 10px;
    }

    #content img {
        cursor: pointer;
    }

    /*.k-dialog-show {*/
        /*margin-bottom: 50px;*/
        /*top: 15%;*/
        /*width: 50%;*/
        /*position: absolute;*/
        /*left: 50%;*/
        /*transform: translateX(-50%);*/
        /*background: #fff;*/
        /*border-radius: 2px;*/
        /*box-shadow: 0px 0px 30px 10px #666;*/
        /*box-sizing: border-box;*/
    /*}*/

</style>
<link rel="stylesheet" type="text/css" href="/resources/statics/css/comment.css"/>
<script src="/resources/statics/jQuery/identicon5.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/resources/statics/vuejs/components/commentComponents.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/statics/css/dialogWrapper.css"/>
<script type="text/javascript" charset="utf-8"
        src="/resources/statics/vuejs/components/dialogWrapperComponents.js"></script>
<div id="articleDetailContainer" class="main">
    <head>
        <title>
            {{article.title}}
        </title>
    </head>
    <h2 id="title">{{article.title}}</h2>
    <div v-if="article.memo">
        <span class="article-memo">{{article.memo}}</span>
    </div>
    <div class="edit-btn">
        <el-button v-if="article.permission" type="primary" @click="editArticle"><i
                class="icon-edit" ></i>编辑</el-button>
    </div>
    <p class="lead">
        by
        <a id="individualName" :href="'individualDetail?individualEid=' + article.individualEid">{{article.individualName}}</a>
    </p>
    <hr>
    <p>
        <i class="el-icon-time"></i>
        {{article.createTime}}
        <span class="article-read-cnt">
                  <svg t="1503566488643" width="14" height="16" class="icon" style="" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1501" xmlns:xlink="http://www.w3.org/1999/xlink" width="200" height="200"><defs><style type="text/css"></style></defs><path d="M681.908 902.805c-49.759 0-132.699 14.66-145.635 48.497v0.752c-28.836 0.752-35.657 0-48.545 0-12.913-33.885-95.876-48.545-145.635-48.545H75.095V199.604h290.3c62.647 0 116.823 32.186 146.582 79.686 29.758-47.502 83.958-79.686 146.63-79.686h290.3v703.201H681.909zM487.727 345.24c0-56.749-61.289-97.09-121.363-97.09H123.638v606.815h218.453c48.157-0.849 124.106 0.51 145.635 35.972v-27.185c-0.923-33.594-0.049-79.469 0-81.362V360.897 345.241z m412.635-97.14H657.636c-60.075 0-121.363 40.317-121.363 96.993v436.714c0.049 1.869 0.923 47.744 0 81.265v27.161c21.53-35.413 97.478-36.797 145.635-35.923h218.453V248.102z" fill="" p-id="1502"></path></svg>
                            {{article.articleReadCnt}}</span>

        <el-tag type="primary">
            <a href="javascript:void(0)" @click="toCategoryDetail">{{article.categoryName}}</a>
        </el-tag>
    </p>
    <hr>
    <div id="content"></div>
    <hr>
    <div class="block">
        <div class="like article-rate" @click="likeArticle">
            <el-tooltip :content="article.liked ? '不赞':'赞'" placement="bottom" effect="light">
                <i :class="articlelikeclass"></i>
                <span>{{article.articleLikeCnt}}</span>
            </el-tooltip>
        </div>


        <div class="bookmark article-rate" @click="bookmark">
            <el-tooltip :content="article.bookmarked ? '取消收藏':'收藏'" placement="bottom" effect="light">
                <i :class="bookmarkclass"></i>
            </el-tooltip>
        </div>

        <el-popover
                ref="popover-share"
                placement="bottom"
                width="60"
                trigger="hover">
            <div class="bdsharebuttonbox article-rate">
                <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                <a href="#" class="bds_youdao" data-cmd="youdao" title="分享到有道云笔记"></a>
            </div>
        </el-popover>
        <div class="share article-rate">
            <i class=" icon-share" v-popover:popover-share></i>
        </div>


    </div>

    <div class="comment-wrapper">
        <div>
            <div class='new-comment-box'>
                <span class='c-b-span'>写评论：</span>
                <div>
                    <el-input type='textarea' :autosize='{minRows:3,maxRows:4}' placeholder='请输入您的评论'
                              v-model='newComment' :maxlength='500'></el-input>
                    <div class='c-b-b-group'>
                        <el-button type='primary' @click='submitComment' size='small'>发表</el-button>
                    </div>
                </div>
            </div>
        </div>
        <div v-if='commentTotal && commentTotal > 0'>
            <comments :comments="comments" @reload-comment="reloadComment" @show-login="showKDialog" @show-active="showActiveDialog" :total="commentTotal"></comments>
            <br>
            <el-pagination
                    @current-change="commentPageChange"
                    :current-page="commentPage"
                    :page-size="10"
                    layout="total, prev, pager, next, jumper"
                    :total="commentTotal">
            </el-pagination>
        </div>
    </div>
    <div>
        <dialog-wrapper  :kdialogclass="kdialogclass" :html="loginPluginHtml"
                       @closekdialog="closeKDialog"></dialog-wrapper>
        <dialog-wrapper :kdialogclass="activeDialogClass" :html="activeDialogHtml"
                        @closekdialog="closeActiveDialog"></dialog-wrapper>
    </div>
</div>
<script>
    var articleDetailVue = new Vue({
        el: "#articleDetailContainer",
        data: function () {
            return {
                articleEid: '${articleEid}',
                article: {},
                comments: [],
                commentPage: 1,
                commentTotal: 0,
                newComment: '',
                kdialogclass: 'k-dialog-hide',
                activeDialogClass: 'k-dialog-hide',
                activeDialogHtml: '',
                loginPluginHtml: '',
                dialogTableVisible : false
            }
        },
        created: function () {
            var _this = this;
            $.ajax({
                type: 'post',
                data: {articleEid: _this.articleEid},
                url: basePath + '/getArticleDetail',
                success: function (response) {
                    if (!response) {
                        _this.$message({
                            showClose: true,
                            message: '服务器异常',
                            type: 'error'
                        });
                        return false;
                    }
                    _this.article = response;
                    $("#content").html(response.content);
                    $("img").on("click",function(){window.open($(this).attr("src"))});

                }
            });
            this.loadComments(1);
        },
        computed: {
            articlelikeclass: function () {
                return this.article.liked ? 'icon-heart' : 'icon-heart-empty';
            },
            bookmarkclass: function () {
                return this.article.bookmarked ? 'icon-bookmark' : 'icon-bookmark-empty';
            }
        },
        methods: {
            showKDialog: function (html) {
                this.kdialogclass = "k-dialog-show";
                this.loginPluginHtml = html;
            },
            closeKDialog: function () {
                this.kdialogclass = 'k-dialog-hide';
            },
            showActiveDialog: function (html) {
                this.activeDialogClass = "k-dialog-show";
                this.activeDialogHtml = html;
            },
            closeActiveDialog: function () {
                this.activeDialogClass = 'k-dialog-hide';
            },
            toCategoryDetail: function () {
                location.href = basePath + '/categoryDetail?categoryEid=' + this.article.categoryEid;
            },
            likeArticle: function () {
                var _this = this;
                $.ajax({
                    type: 'post',
                    data: {articleEid: _this.article.articleEid, like: !_this.article.liked},
                    url: basePath + '/likeArticle',
                    success: function (response) {
                        if (!response) {
                            _this.$message({
                                showClose: true,
                                message: '服务器异常',
                                type: 'error'
                            });
                            return false;
                        }
                        if (!response.status) {
                            _this.showKDialog(response);
                            return false;
                        }
                        if (response.status != 1) {
                            _this.$notify.info({
                                title: '消息',
                                message: response.message
                            });
                            return false;
                        }
                        _this.article.liked = !_this.article.liked;
                        _this.article.articleLikeCnt = _this.article.articleLikeCnt + (_this.article.liked ? 1 : -1);
                    },
                    error : function (response) {
                        if (response.status == 401) {
                            _this.showActiveDialog(response.responseText);
                            return false;
                        }
                    }

                })
            },
            editArticle: function () {
                location.href = '/editArticle?articleEid=' + this.article.articleEid;
            },
            bookmark: function () {
                var _this = this;
                $.ajax({
                    type: 'post',
                    data: {bookmark: !_this.article.bookmarked, articleEid: _this.articleEid},
                    url: 'bookmark',
                    success: function (response) {
                        if (!response) {
                            _this.$message({
                                showClose: true,
                                message: '服务器异常',
                                type: 'error'
                            });
                            return false;
                        }
                        if (!response.status) {
                            _this.showKDialog(response);
                            return false;
                        }
                        if (response.status != 1) {
                            _this.$notify.info({
                                title: '消息',
                                message: response.message
                            });
                            return false;
                        }
                        if (!_this.article.bookmarked) {
                            _this.$message({
                                showClose: true,
                                message: '收藏成功',
                                type: 'success'
                            });
                        } else {
                            _this.$message({
                                showClose: true,
                                message: '已取消收藏'
                            });
                        }
                        _this.article.bookmarked = !_this.article.bookmarked;
                        _this.article.bookmarkCnt = _this.article.bookmarkCnt + (_this.article.bookmarked ? 1 : -1);
                    },
                    error : function (response) {
                        if (response.status == 401) {
                            _this.showActiveDialog(response.responseText);
                            return false;
                        }
                    }

                });
            },
            loadComments: function (currentPage) {
                var _this = this;
                $.ajax({
                    type: 'post',
                    data: {articleEid: _this.articleEid, currentPage: currentPage, pageSize: 10},
                    url: '/getComments',
                    success: function (response) {
                        if (!response) {
                            _this.$message({
                                showClose: true,
                                message: '服务器异常',
                                type: 'error'
                            });
                            return false;
                        }
                        if (response.status != 1) {
                            _this.$notify.info({
                                title: '消息',
                                message: response.message
                            });
                            return false;
                        }
                        _this.comments = response.data.rows;
                        _this.commentTotal = response.data.total;
                    }
                });
            },
            commentPageChange: function (val) {
                this.loadComments(val);
            },
            reloadComment: function () {
                this.loadComments(this.commentPage);
            },
            submitComment: function () {
                var _this = this;
                $.ajax({
                    type: 'post',
                    data: {
                        articleEid: _this.articleEid,
                        content: _this.newComment
                    },
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    url: 'submitComment',
                    success: function (response) {
                        if (!response) {
                            _this.$message({
                                showClose: true,
                                message: '服务器异常',
                                type: 'error'
                            });
                            return false;
                        }
                        if (!response.status) {
                            _this.showKDialog(response);
                            return false;
                        }
                        if (response.status != 1) {
                            _this.$notify.info({
                                title: '消息',
                                message: response.message
                            });
                            return false;
                        }
                        _this.$message({
                            showClose: true,
                            message: '评论成功!',
                            type: 'success'
                        });
                        _this.newComment = '';
                        _this.comments.unshift(response.data);
                        _this.commentTotal += 1;
                    },
                    error : function (response) {
                        if (response.status == 401) {
                            _this.showActiveDialog(response.responseText);
                            return false;
                        }
                    }
                });
            }
        },
        components: {
            "comments": CommentCompoent.commentList,
            "dialog-wrapper": DialogWrapper.component
        }
    });

    $(document).ready(function () {
        window._bd_share_config = {
            "common": {
                "bdSnsKey": {},
                "bdText": "",
                "bdMini": "2",
                "bdMiniList": ["weixin", "tsina", "youdao"],
                "bdPic": "",
                "bdStyle": "1",
                "bdSize": "24"
            },
            "share": {}
//            ,
//            "image": {"viewList": ["weixin", "tsina", "youdao"]}
//            ,
//            "selectShare": {"bdContainerClass": null, "bdSelectMiniList": ["weixin", "tsina", "youdao"]}
        };
        with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];

    });

</script>
