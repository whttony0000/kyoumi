<style>
    #categoryDetailContainer {
        margin: 60px auto;
        display: table;
        width: 1000px;
    }

    .tag__info {
        background: #fff;
        box-shadow: 0 0 8px 0 rgba(0,0,0,.16);
        padding: 15px;
        margin-bottom: 20px;
    }

    .tag__info--thumbnai {
        margin-right: 15px;
    }

    .avatar-square-24 {
        width: 24px;
        height: 24px;
    }

    [class*="avatar-"] {
        border-radius: 2px;
    }

    .pull-left {
        float: left !important;
    }

    .pull-left {
        float: left;
    }

    img {
        vertical-align: middle;
    }

    img {
        border: 0;
    }

    .tag__info--title {
        margin-top: 0;
        margin-right: 15px;
        vertical-align: middle;
    }

    .tag__info--follow {
        display: inline-block;
    }

    .tag__info--desc {
        color: #666;
        margin-top: 15px;
        margin-bottom: 0;
    }
</style>
<head>
    <title>
        category | BestWonderful
    </title>
</head>
<link href="/resources/statics/css/articleList.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/resources/statics/css/dialogWrapper.css"/>
<script type="text/javascript" charset="utf-8" src="/resources/statics/vuejs/components/dialogWrapperComponents.js"></script>
<div id="categoryDetailContainer" class="main">
    <div>
        <section class="tag-info tag__info">
            <div>
                <img class="pull-left avatar-square-24 tag__info--thumbnai" :src="categoryImageUrl">
                <span class="h4 tag__info--title">{{category.name}}</span>

                <div class="tag__info--follow">
                    <el-button type="primary" size="small" @click="watchCategory">{{category.onWatch?'取消关注' : '关注'}}</el-button>
                </div>
            </div>
            <p class="tag__info--desc">{{category.description}}</p>
        </section>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <h3>文章</h3>
        </div>
    </div>
    <hr>
    <article-list
            :param="{url : 'getArticleList',categoryEid : categoryEid,currentPage : 1,pageSize : 10}"></article-list>
    <div>
        <dialog-wrapper :kdialogclass="kDialogClass" :html="kDialogHtml" @closekdialog="closeKDialog"></dialog-wrapper>
    </div>
</div>
<script src="/resources/statics/jQuery/identicon5.min.js"></script>
<script src="/resources/statics/vuejs/components/articleList.js"></script>
<script>
    var categoryDetailVue = new Vue({
        el: "#categoryDetailContainer",
        data: function () {
            return {
                categoryEid: '${categoryEid}',
                category: {},
                categoryImageUrl: '',
                articleList: {},
                kDialogClass : 'k-dialog-hide',
                kDialogHtml : ''
            }
        },
        created: function () {
            var _this = this;
            $.ajax({
                type: 'post',
                data: {categoryEid: _this.categoryEid},
                url: basePath + '/getCategoryDetail',
                success: function (response) {
                    if (!response) {
                        _this.$message({
                            showClose: true,
                            message: '服务器异常',
                            type: 'error'
                        });
                        return false;
                    }
                    _this.category = response.data;
                    _this.categoryImageUrl = _this.category.imageModel.url;
                }
            });
        },
        methods : {
            showKDialog : function (html) {
                this.kDialogClass = "k-dialog-show";
                this.kDialogHtml = html;
            },
            closeKDialog :function () {
                this.kDialogClass = 'k-dialog-hide';
            },
            watchCategory: function () {
                var _this = this;
                $.ajax({
                    type: 'post',
                    data: {categoryEid: _this.categoryEid, watch: !_this.category.onWatch},
                    url: basePath + 'watchCategory',
                    success: function (response) {
                        console.log(response);
                        if (!response) {
                            _this.$message({
                                showClose: true,
                                message: '服务器异常',
                                type: 'error'
                            });
                        }
                        _this.category.onWatch = !_this.category.onWatch;
                    },
                    error : function (response) {
                        if (response.status == 401) {
                            _this.showKDialog(response.responseText);
                            return false;
                        }
                    }
                });
            }
        },
        components: {
            "article-list": articleListComponent.articleList.component,
            "dialog-wrapper":DialogWrapper.component
        }
    });
</script>
