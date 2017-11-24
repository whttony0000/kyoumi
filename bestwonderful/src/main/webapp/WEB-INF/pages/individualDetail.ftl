<head>
    <title>
        me | BestWonderful
    </title>
</head>

<style>
    .event-tabs {
        margin: 30px 0 0 20px;
        padding: 5px;
        width: 800px;
        box-shadow: 0 0 8px 0 rgba(0, 0, 0, .16);
        background: #FFFFFF;
    }

    .article-item {
        width: 780px;
    }

    .pagination {
    }

    .text {
        font-size: 14px;
    }

    .item {
        padding: 18px 0;
    }

    .box-card {
        width: 290px;
    }

    .i-info {
        float: right;
        margin: 30px 0 0 15px;
    }

    .i-cnt {
        float: right;
        clear: both;
        margin-right: 30px 0 0 5px;
    }

    .el-table__empty-block {
        max-height: 100px;
    }
</style>
<link rel="stylesheet" href="/resources/statics/css/individualDetail.css">
<link href="/resources/statics/css/articleList.css" rel="stylesheet"/>
<link href="/resources/statics/css/index.css" rel="stylesheet"/>
<script src="/resources/statics/vuejs/components/articleList.js"></script>
<script src="/resources/statics/jQuery/identicon5.min.js"></script>
<script src="/resources/statics/vuejs/components/gridComponents.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/statics/css/dialogWrapper.css"/>
<script type="text/javascript" charset="utf-8"
        src="/resources/statics/vuejs/components/dialogWrapperComponents.js"></script>
<div id="iDetailContainer" class="main container">
    <div id="ProfileHeader" class="ProfileHeader" data-za-module="Unknown"
         data-za-module-info="{&quot;card&quot;:{&quot;content&quot;:{&quot;type&quot;:&quot;User&quot;,&quot;token&quot;:&quot;wang-wang-8-95&quot;}}}">
        <div class="Card">
            <div class="ProfileHeader-userCover">
                <div class="UserCoverEditor">
                    <div class="UserCoverGuide">
                        <div class="UserCoverGuide-inner">
                            <div class="UserCoverGuide-buttonContainer">
                            <#--<button class="Button DynamicColorButton" type="button">-->
                                    <#--<svg viewBox="0 0 20 16" class="Icon Icon--camera Icon--left" width="14" height="16" aria-hidden="true" style="height: 16px; width: 14px;"><title></title></svg><!-- react-text: 1845 &ndash;&gt;上传封面图片<!-- /react-text &ndash;&gt;-->
                                <#--</button>-->
                            </div>
                        </div>
                        <ul class="UserCoverGuide-items">
                        </ul>
                    </div>
                    <div class="UserCover UserCover--colorBlock">
                    </div>
                </div>
            </div>
            <div class="ProfileHeader-wrapper">
                <div class="ProfileHeader-main">
                    <div class="UserAvatarEditor ProfileHeader-avatar" style="top: -74px;">
                        <div class="UserAvatar" id="individualPhotoWrapper">
                            <img id="individualPhoto" class="Avatar Avatar--large UserAvatar-inner"
                                 :src="individualDetail.photoUrl" width="160" height="160">
                        </div>
                    </div>
                    <div class="ProfileHeader-content">
                        <div class="ProfileHeader-contentHead">
                            <h1 class="ProfileHeader-title">
                                <span class="ProfileHeader-name">{{individualDetail.name}}</span>
                                <span class="RichText ProfileHeader-headline">{{individualDetail.description}}</span>
                            </h1>
                        </div>
                        <div class="ProfileHeader-contentBody" style="height: 20px;">
                            <span>
                                <div class="ProfileHeader-info">
                                    <div class="ProfileHeader-infoItem">
                                        <div class="ProfileHeader-iconWrapper">
                                            <svg v-if="individualDetail.sex == 0" t="1502270495333" class="icon"
                                                 width="14" height="16" style="" viewBox="0 0 1024 1024" version="1.1"
                                                 xmlns="http://www.w3.org/2000/svg" p-id="1047"
                                                 xmlns:xlink="http://www.w3.org/1999/xlink" width="200" height="200"><defs><style
                                                    type="text/css"></style></defs><path
                                                    d="M941.307 121.479c-1.308-19.881-18.355-37.067-38.075-38.385L594.05 71.945c-19.72-1.318-34.645 13.728-33.337 33.608 1.309 19.879 18.355 37.063 38.075 38.385l221.13 7.973L649.709 323.5c-61.168-51.789-139.93-83.056-225.977-83.056-194.503 0-352.178 159.317-352.178 355.846 0 196.527 157.675 355.844 352.178 355.844 194.505 0 352.178-159.317 352.178-355.844 0-82.079-27.775-157.449-73.979-217.652l171.14-172.528 7.885 222.28c1.309 19.879 18.355 37.067 38.075 38.385 19.722 1.32 34.647-13.726 33.337-33.606l-11.061-311.69zM423.731 878.765c-153.736 0-278.807-126.718-278.807-282.474 0-155.758 125.072-282.476 278.807-282.476s278.807 126.718 278.807 282.476c0 155.756-125.071 282.474-278.807 282.474z"
                                                    p-id="1048"></path></svg>
                                            <svg v-if="individualDetail.sex == 1" t="1502270980325" class="icon"
                                                 width="14" height="16" style="" viewBox="0 0 1026 1024" version="1.1"
                                                 xmlns="http://www.w3.org/2000/svg" p-id="1831"
                                                 xmlns:xlink="http://www.w3.org/1999/xlink" width="200.390625"
                                                 height="200"><defs><style type="text/css"></style></defs><path
                                                    d="M277.188964 752.674649c14.92556 14.92556 14.92556 36.247788 0 51.173347L61.834461 1012.80583c-14.92556 14.92556-36.247788 14.92556-51.173347 0s-14.92556-36.247788 0-51.173347l215.354503-211.090057c14.92556-12.793337 36.247788-12.793337 51.173347 2.132223z"
                                                    fill="" p-id="1832"></path><path
                                                    d="M253.734513 978.690265c-14.92556 14.92556-36.247788 14.92556-51.173347 0l-153.520042-153.520041c-14.92556-14.92556-14.92556-36.247788 0-51.173347s36.247788-14.92556 51.173348 0l153.520041 153.520041c12.793337 14.92556 12.793337 36.247788 0 51.173347zM556.510151 76.760021c100.214472 0 198.29672 38.38001 275.056741 113.007808 151.387819 151.387819 151.387819 396.593441 0 550.113483-76.760021 76.760021-174.84227 113.007808-275.056741 113.007808s-198.29672-38.38001-275.056741-113.007808c-151.387819-151.387819-151.387819-396.593441 0-550.113483 76.760021-74.627798 176.974492-113.007808 275.056741-113.007808m0-76.760021c-123.668922 0-240.941176 49.041124-330.494534 136.462259-85.288912 87.421135-134.330036 204.693389-134.330037 330.494534s49.041124 240.941176 136.46226 330.494534c87.421135 87.421135 204.693389 136.462259 330.494534 136.46226s240.941176-49.041124 330.494534-136.46226c87.421135-87.421135 136.462259-204.693389 136.462259-330.494534s-49.041124-240.941176-136.462259-330.494534C799.58355 49.041124 682.311296 0 556.510151 0z"
                                                    fill="" p-id="1833"></path></svg>
                                        </div>
                                    </div>
                                </div>
                            </span>
                        </div>
                        <div class="ProfileHeader-contentFooter">
                            <#if isSelf == 0>
                            <div class="MemberButtonGroup ProfileButtonGroup ProfileHeader-buttons">
                                <button v-if="individualDetail.onWatch" class="Button FollowButton Button--primary Button--grey" type="button"
                                        @click="watchIndividual" @mouseenter="watchHover" @mouseleave="watchLeave">
                                    {{watchText}}
                                </button>
                                <button v-if="!individualDetail.onWatch" class="Button FollowButton Button--primary Button--blue" type="button"
                                        @click="watchIndividual">
                                    <svg viewBox="0 0 12 12" class="Icon FollowButton-icon Icon--plus" width="12"
                                         height="16" aria-hidden="true" style="height: 16px; width: 12px;">
                                        <title></title>
                                        <g>
                                            <path d="M6.994 4.994s-.008-3.593 0-4C6.97-.33 4.97-.33 4.996.994c-.025 1.275 0 4 0 4H1C-.334 5-.334 7 1 7c1.332 0 3.996-.006 3.996-.006v4c0 1.346 2.004 1.346 1.998 0-.006-1.347 0-4 0-4S9.658 7 10.997 7c1.338 0 1.338-2-.007-2.006H6.994z"></path>
                                        </g>
                                    </svg>
                                    {{'关注'+individualSexStr}}
                                </button>
                                <#--<button class="Button" type="button" @click="messageToIndividual">-->
                                    <#--<svg viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"-->
                                         <#--class="Icon Button-icon Icon--comments" width="15" height="16"-->
                                         <#--aria-hidden="true" style="height: 16px; width: 15px;"><title></title>-->
                                        <#--<g>-->
                                            <#--<g>-->
                                                <#--<path d="M9 0C3.394 0 0 4.13 0 8c0 1.654.522 3.763 2.014 5.566.314.292.518.82.454 1.17-.165 1.488-.842 1.905-.842 1.905-.328.332.105.67.588.582 1.112-.2 2.07-.58 3.526-1.122.4-.202.464-.147.78-.078C11.524 17.764 18 14 18 8c0-3.665-3.43-8-9-8z"></path>-->
                                                <#--<path d="M19.14 9.628c.758.988.86 2.01.86 3.15 0 1.195-.62 3.11-1.368 3.938-.21.23-.354.467-.308.722.12 1.073.614 1.5.614 1.5.237.24-.188.563-.537.5-.802-.145-1.494-.42-2.545-.81-.29-.146-.336-.106-.563-.057-2.043.712-4.398.476-6.083-.926 5.964-.524 8.726-3.03 9.93-8.016z"></path>-->
                                            <#--</g>-->
                                        <#--</g>-->
                                    <#--</svg>-->
                                    <#--<span>发私信</span>-->
                                <#--</button>-->
                            </div>
                            </#if>
                            <#if isSelf == 1>
                            <div class="ProfileButtonGroup ProfileHeader-buttons">

                                    <button class="Button Button--blue" type="button" @click="editIndividual">编辑个人资料
                                    </button>
                            </div>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="i-info">
        <el-card class="box-card">
            <div class="text item">
                <a href="javascript:void(0)" @click="activeTab='follow'"><span>关注的主题</span><span class="i-cnt">{{individualDetail.watchCategoryCnt}}</span></a>
            </div>
            <div class="text item">
                <a href="javascript:void(0)" @click="activeTab='follow'"><span>关注的人</span><span class="i-cnt">{{individualDetail.watchIndividualCnt}}</span></a>
            </div>
            <div class="text item">
                <a href="javascript:void(0)" @click="activeTab='follow'"><span>关注者</span><span class="i-cnt">{{individualDetail.fanCnt}}</span></a>
            </div>
        </el-card>
    </div>

    <div class="event-tabs">
        <el-tabs v-model="activeTab">
            <el-tab-pane label="文章" name="article">
                <article-list ref="articleList"
                              :param="{url : 'getArticleList',currentPage : 1,pageSize : 10,individualEid:individualEid,isSelf:isSelf}"
                              :type="'individual'"></article-list>
            </el-tab-pane>
            <el-tab-pane label="关注" name="follow">
                <el-collapse v-model="activeCollapses">
                    <el-collapse-item title="关注的主题" name="category">
                        <grid ref="onWatchCategoryGrid" :url="'getOnWatchCategoryList'" :emptytext="'暂无关注的主题'"  :columns="
                            [
                                {prop:'name',label:'主题'},
                                {prop:'description',label:'描述'},
                                {label:'操作',type:'link',links:[{text:'详情',action:toOnWatchCategoryDetail}]}
                            ]" ></grid>
                    </el-collapse-item>
                    <el-collapse-item title="关注的人" name="watched">
                        <grid ref="onWatchIndividualGrid" :url="'getOnWatchIndividualList'" :emptytext="'暂无关注的人'"  :columns="
                            [
                                {prop:'name',label:'关注的人'},
                                {prop:'description',label:'简介'},
                                {label:'操作',type:'link',links:[{text:'详情',action:toOnWatchIndividualDetail}]}
                            ]" ></grid>
                    </el-collapse-item>
                    <el-collapse-item title="关注者" name="watcher">
                        <grid ref="fanGrid" :url="'getFanList'" :emptytext="'暂无关注者'"  :columns="
                            [
                                {prop:'name',label:'关注者'},
                                {prop:'description',label:'简介'},
                                {label:'操作',type:'link',links:[{text:'详情',action:toFanDetail}]}
                            ]" ></grid>
                    </el-collapse-item>
                </el-collapse>
            </el-tab-pane>
            <el-tab-pane label="收藏" name="bookmark">
                <article-list ref="articleList"
                              :param="{url : 'getIndividualBookmarkList',currentPage : 1,pageSize : 10,individualEid:individualEid,isSelf:isSelf}"
                              :type="'individual'"></article-list>
            </el-tab-pane>
        </el-tabs>
    </div>
    <div>
        <dialog-wrapper :kdialogclass="activeDialogClass" :html="activeDialogHtml"
                        @closekdialog="closeActiveDialog"></dialog-wrapper>
    </div>

</div>
<script>
    var individualDetailVue = new Vue({
        el: '#iDetailContainer',
        data: function () {
            return {
                individualEid: '${individualEid}',
                mailCode : '${mailCode}',
                individualDetail: {},
                isSelf: ${isSelf},
                activeTab: 'article',
                individualSexStr: '',
                watchText : '已关注',
                activeCollapses:['category','watched','watcher'],
                activeDialogClass: 'k-dialog-hide',
                activeDialogHtml: ''
            }
        },
        computed :  {
            owclass : function () {
                return 'Button FollowButton Button--primary ' + (this.individualDetail.onWatch ? "Button--grey" : "Button--blue");
            }
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
                        _this.loadPhoto();
                    }
                    _this.individualSexStr = _this.individualDetail.sex == 0 ? '他' : '她';
                },
                error : function (response) {
                    if (response.status == 401) {
                        _this.loadPhoto();
                        _this.showActiveDialog(response.responseText);
                        return false;
                    }
                }
            });
//            _this.$refs.onWatchCategoryGrid.loadData({watcherEid:_this.individualEid},1);
        },
        mounted : function () {
            this.$refs.onWatchCategoryGrid.loadData({watcherEid:this.individualEid},1);
            this.$refs.onWatchIndividualGrid.loadData({watcherEid:this.individualEid},1);
            this.$refs.fanGrid.loadData({watchedEid:this.individualEid},1);
        },
        methods: {
            loadPhoto : function () {
                $("#individualPhoto").remove();
                $("#individualPhotoWrapper").append("<span id='individualPhoto' width='160' height='160'>" + this.mailCode + "</span>");
                $("#individualPhoto").identicon5({size: 160});
            },
            showActiveDialog: function (html) {
                this.activeDialogClass = "k-dialog-show";
                this.activeDialogHtml = html;
            },
            closeActiveDialog: function () {
                this.activeDialogClass = 'k-dialog-hide';
            },
            editIndividual: function () {
                location.href = '/editIndividual';
            },
            watchIndividual: function () {
                var _this = this;
                $.ajax({
                    type: 'post',
                    data: {individualEid: _this.individualEid,onWatch : !_this.individualDetail.onWatch},
                    url: 'watchIndividual',
                    success : function (response) {
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
                        _this.individualDetail.onWatch = !_this.individualDetail.onWatch;
                    },
                    error : function (response) {
                        if (response.status == 401) {
                            _this.showActiveDialog(response.responseText);
                            return false;
                        }
                    }

                });
            },
            messageToIndividual: function () {
                alert("building");
            },
            watchHover : function () {
                    this.watchText = "取消关注";
            },
            watchLeave : function () {
                this.watchText = "已关注";
            },
            toOnWatchCategoryDetail : function (row) {
                location.href = '/categoryDetail?categoryEid=' + row.categoryEid;
            },
            toOnWatchIndividualDetail : function (row) {
                location.href = "/individualDetail?individualEid=" + row.individualEid;
            },
            toFanDetail : function (row) {
                location.href = "/individualDetail?individualEid=" + row.individualEid;

            }

        },
        components: {
            "article-list": articleListComponent.articleList.component,
            "grid":GridComponent.gridList.component,
            "dialog-wrapper": DialogWrapper.component
        }




    });

//    $(function () {
//        $(".Button--grey").on("hover",function(){$(this).text("取消关注")});
//    })

</script>