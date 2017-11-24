<head>
    <title>
        edit | BestWonderful
    </title>
</head>
<style>
    #myEditor {
        margin-left: 80px;
    }
</style>
<link
rel="stylesheet"
type="text/css"
href="/resources/statics/ueditor1_2_1/themes/default/ueditor.css"/>
<script type="text/javascript" charset="utf-8" src="/resources/statics/ueditor1_2_1/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="/resources/statics/ueditor1_2_1/editor_all.js"></script>
<div class="main container">
    <div id="vue-container">
        <el-form :label-position="'left'" label-width="80px" label-suffix="：">
            <el-form-item label="">
                <el-button type="warning" @click="cancel">取消</el-button>
                <el-button type="primary" @click="save">保存</el-button>
            </el-form-item>
            <el-form-item label="标题">
                <el-input v-model="article.title" placeholder="请输入标题" :maxlength="50"></el-input>
            </el-form-item>
            <el-form-item label="概述">
                <el-input v-model="article.memo" placeholder="请输入概述" :maxlength="50"></el-input>
            </el-form-item>
            <el-form-item label="分类">
                <el-select v-model="article.categoryId" placeholder="请选择分类">
                    <el-option
                            v-for="item in categories"
                            :key="item.categoryId"
                            :label="item.name"
                            :value="item.categoryId">
                    </el-option>
                </el-select>
            </el-form-item>

        </el-form>
        </div>
        <div class="el-form-item">
            <label class="el-form-item__label" style="width: 80px;text-align: left;">
                文章：
            </label>
            <script id="myEditor" name="content" type="text/plain">${content}</script>
        </div>
</div>

<script type="text/javascript" charset="utf-8">
    var myEditor = new baidu.editor.ui.Editor({
        fontfamily : [
            ['宋体',['宋体', 'SimSun']],
            ['楷体',['楷体', '楷体_GB2312', 'SimKai']],
            ['黑体',['黑体', 'SimHei']],
            ['隶书',['隶书', 'SimLi']],
            ['andale mono',['andale mono']],
            ['arial',['arial', 'helvetica', 'sans-serif']],
            ['arial black',['arial black', 'avant garde']],
            ['comic sans ms',['comic sans ms']],
            ['impact',['impact', 'chicago']],
            ['times new roman',['times new roman']],
            ['微软雅黑',['Microsoft YaHei']]
        ],
        imageUrl : "/upload/imageEditor"
    });
    myEditor.render('myEditor');

//    myEditor.ready(function () {
//        myEditor.setContent($("#content").val());
//    })

    $(document).ready(function () {
//        myEditor.setContent($("#content").val(), false);
    });


    var mainVue = new Vue({
        el: "#vue-container",
        data: function () {
            return {
                article: ${article},
                articleId: ${articleId},
                categories: []
            }
        },
        created: function () {
            var _this = this;
            $.ajax({
                type: 'get',
                url: basePath + '/getCategoryList',
                success: function (data) {
                    _this.categories = data.data;
                }

            });
            if (_this.articleId == 0) {
                return false;
            }
        },
        methods: {
            save: function () {
                var _this = this;
                if (!_this.article.title || _this.article.title == "") {
                    _this.$message({
                        showClose: true,
                        message: '请输入标题',
                        type: 'error'
                    });
                    return false;
                }
                if (!_this.article.memo || _this.article.memo == "") {
                    _this.$message({
                        showClose: true,
                        message: '请输入概述',
                        type: 'error'
                    });
                    return false;
                }
                if (!_this.article.categoryId || _this.article.categoryId == 0) {
                    _this.$message({
                        showClose: true,
                        message: '请选择分类',
                        type: 'error'
                    });
                    return false;
                }
                _this.article.content = $.trim(myEditor.getContent());
                if (!myEditor.getContentTxt() || !$.trim(myEditor.getContentTxt())) {
                    _this.$message({
                        showClose: true,
                        message: '请输入内容',
                        type: 'error'
                    });
                    return false;
                }
                var contentLen = myEditor.getContentTxt().length;
                if (contentLen == 0) {
                    _this.$message({
                        showClose: true,
                        message: '请输入内容',
                        type: 'error'
                    });
                    return false;
                }
                if (contentLen < 20) {
                    _this.$message({
                        showClose: true,
                        message: '内容过短',
                        type: 'error'
                    });
                    return false;
                }
                if (contentLen > 10000) {
                    _this.$message({
                        showClose: true,
                        message: '内容过长',
                        type: 'error'
                    });
                    return false;
                }
                _this.article.id = _this.articleId;
                $.ajax({
                    type: 'post',
                    url: basePath + '/saveArticle',
                    contentType: 'application/json;charset=UTF-8',
                    data: JSON.stringify(_this.article),
                    success: function (data) {
                        if (!data) {
                            _this.$message({
                                showClose: true,
                                message: '服务器异常',
                                type: 'error'
                            });
                            return false;
                        }
                        if (data.status != 1) {
                            _this.$message({
                                showClose: true,
                                message: data.message,
                                type: 'error'
                            });
                            return false;
                        }
                        window.location.href = 'articleDetail?articleEid=' + data.data;
                    }
                });
            },
            cancel: function () {
                location.href = document.referrer;
            }
        }


    });

</script>
