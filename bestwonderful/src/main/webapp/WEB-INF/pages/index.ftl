<style>
    .thumbnail {
        height: 336px;
    }

    .thumbnail img {
        height: 50% !important;
        width: 100%;
    }

    .article-list {
        width: 820px;
        margin-top:50px;
        box-shadow: 0 0 8px 0 rgba(0,0,0,.16);
    }

</style>
<head>
    <title>home | BestWonderful</title>
</head>

<link href="/resources/statics/css/articleList.css" rel="stylesheet"/>
<link href="/resources/statics/css/index.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="/resources/statics/css/dialogWrapper.css"/>
<script src="/resources/statics/jQuery/identicon5.min.js"></script>
<script src="/resources/statics/vuejs/components/articleList.js"></script>
<div class="container" class="main" id="indexContainer">

    <!-- Jumbotron Header -->
<#--<header class="jumbotron hero-spacer">-->
<#--<h1>A Warm Welcome!</h1>-->
<#--<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ipsa, ipsam, eligendi, in quo sunt possimus non-->
<#--incidunt odit vero aliquid similique quaerat nam nobis illo aspernatur vitae fugiat numquam repellat.</p>-->
<#--<p><a class="btn btn-primary btn-large">Call to action!</a>-->
<#--</p>-->
<#--</header>-->
<#--<hr>-->

    <div id="indexSubContainer">
    <div class="f-wrap">
        <div class="f-header" @click="toIndividualDetail">
            <div class="f-pp">
                <img class="f-photo" :src="individualDetail.photoUrl" alt="" style="" width="32"
                     height="32">
            </div>
            <div class="f-profile">
                <span>{{individualDetail.name}}</span>
                <span class="i-score">得分：<span>{{individualDetail.score ? individualDetail.score : 0}}</span></span>
            </div>
        </div>
        <div class="">
            <a class="f-item f-item-border-bottom f-item-border-right" href="/addArticle" target="_blank">
                    <span class="icon">
                        <i class="icon-edit"></i>
                                            </span>
                <span class="text">写文章</span>
            </a>
            <#--<a class="f-item f-item-border-bottom f-item-border-right" href="#">-->
                    <#--<span class="icon">-->
                        <#--<i class="icon-group"></i>-->
                    <#--</span>-->
                <#--<span class="text">好友</span>-->
            <#--</a>-->
            <#--<a class="f-item f-item-border-bottom" href="#">-->
                    <#--<span class="icon">-->
                        <#--<i class="icon-eye-open"></i>-->
                    <#--</span>-->
                <#--<span class="text">关注</span>-->
            <#--</a>-->
            <#--<a class="f-item f-item-border-bottom f-item-border-right" href="#">-->
                    <#--<span class="icon">-->
                        <#--<i class="icon-bookmark"></i>-->
                    <#--</span>-->
                <#--<span class="text">收藏</span>-->
            <#--</a>-->
            <#--<a class="f-item f-item-border-bottom f-item-border-right" href="#">-->
                    <#--<span class="icon">-->
                        <#--<i class="icon-reorder"></i>-->
                    <#--</span>-->
                <#--<span class="text">排行</span>-->
            <#--</a>-->
            <#--<a class="f-item f-item-border-bottom" href="#">-->
                    <#--<span class="icon">-->
                        <#--<i class="el-icon-share"></i>-->
                                            <#--</span>-->
                <#--<span class="text">邀请朋友</span>-->
            <#--</a>-->
            <#--<a class="f-item f-item-border-right" href="#">-->
                    <#--<span class="icon">-->
                        <#--<i class=" icon-leaf"></i>-->
                    <#--</span>-->
                <#--<span class="text">历史</span>-->
            <#--</a>-->
            <a class="f-item f-item-border-right" href="/data/index">
                    <span class="icon">
                        <i class="icon-bar-chart"></i>
                    </span>
                <span class="text">数据服务</span>
            </a>
            <a class="f-item" href="#">
            </a>
        </div>
    </div>

    <div id="articleListContainer">

        <article-list
                :param="{url : 'getArticleList',currentPage : 1,pageSize : 10}"></article-list>
    </div>

        <dialog-wrapper :kdialogclass="kDialogClass" :html="kDialogHtml" @closekdialog="closeKDialog"></dialog-wrapper>

    </div>


    <#--<div class="row">-->
        <#--<div class="col-lg-12">-->
            <#--<h3>分类</h3>-->
        <#--</div>-->
    <#--</div>-->
<#--<#include "categoryList.ftl">-->



</div>
<script type="text/javascript" charset="utf-8" src="/resources/statics/vuejs/components/dialogWrapperComponents.js"></script>
<script>
    var articleListVue = new Vue({
        el: "#indexSubContainer",
        data: function () {
            return {
                individualEid: '${individualEid}',
                mailCode : '${mailCode}',
                individualDetail: {},
                kDialogClass : 'k-dialog-hide',
                kDialogHtml : ''
            };
        },
        created: function () {
            var _this = this;
            $.ajax({
                type: 'post',
                data: {individualEid: _this.individualEid},
                url: '/getIndividualDetail',
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
                        _this.$message({
                            showClose: true,
                            message: response.message,
                            type: 'error'
                        });
                        return false;
                    }
                    _this.individualDetail = response.data;
                    if (!_this.individualDetail.photoUrl) {
                        _this.loadIdenticon();
                    }
                },
                error : function (response) {
                    if (response.status == 401) {
                        _this.loadIdenticon();
                        _this.showKDialog(response.responseText);
                        return false;
                    }

                }
            });
        },
        methods: {
            showKDialog : function (html) {
                this.kDialogClass = "k-dialog-show";
                this.kDialogHtml = html;
            },
            closeKDialog :function () {
                this.kDialogClass = 'k-dialog-hide';
            },
            loadIdenticon:function () {
                $(".f-photo").remove();
                $(".f-pp").append("<span class='f-photo' width='30' height='30'>"+this.mailCode + "</span>");
                $(".f-photo").identicon5({size:30});
            },
            toIndividualDetail: function () {
                location.href = "/individualDetail?individualEid=" + this.individualEid;
            }
        },
        components: {
            "article-list": articleListComponent.articleList.component,
            "dialog-wrapper":DialogWrapper.component
        }
    });
</script>



