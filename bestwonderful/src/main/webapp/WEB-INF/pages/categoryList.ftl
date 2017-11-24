<style>
    .el-card {
        width: 86px;
    }

    .button {
        padding: 0;
        float: right;
    }

    .image {
        width: 86px;
        height: 86px;
        display: block;
    }

    .thumbnail {
        height: 336px;
    }

    .thumbnail img {
        height: 50% !important;
        width: 100%;
    }

    .category-container {
        display: table;
        margin: 50px auto !important;
    }
    .c-description {
        height: 40px;
    }
</style>
<head>
    <title>
        categories | BestWonderful
    </title>
</head>
<link rel="stylesheet" type="text/css" href="/resources/statics/css/dialogWrapper.css"/>
<script type="text/javascript" charset="utf-8"
        src="/resources/statics/vuejs/components/dialogWrapperComponents.js"></script>
<div id="container" class="text-center container main category-container">
    <div class="">
        <category-item v-for="(category, index) in categories" :category="category" @show-k-dialog="showKDialog"></category-item>
    </div>
    <dialog-wrapper :kdialogclass="kDialogClass" :html="kDialogHtml" @closekdialog="closeKDialog"></dialog-wrapper>
</div>
<script>
    var categoryItem = Vue.extend({
        template: '\
        <div class="col-md-3 col-sm-6 hero-feature">\
            <div class="thumbnail">\
                <img :src="category.imageModel.url" alt="">\
                <div class="caption">\
                    <h3>{{category.name}}</h3>\
                    <p class="c-description">{{description}}</p>\
                    <p>\
                        <el-button type="primary" @click="watchCategory">{{category.onWatch ? "取消关注":"关注"}}</el-button>\
                        <el-button @click="toCategoryDetail">详情</el-button>\
                    </p>\
                </div>\
            </div>\
        </div>\
        ',
        props: ['category'],
        computed: {
            description: function () {
                var _this = this;
                if (!_this.category || !_this.category.description) {
                    return '';
                }
                if (_this.category.description.length > 20) {
                    return _this.category.description.substring(0, 19) + '...';
                }
                return _this.category.description;
            }
        },
        methods: {
            toCategoryDetail : function () {
              location.href =   '/categoryDetail?categoryEid=' + this.category.categoryEid;
            },
            watchCategory: function () {
                var _this = this;
                $.ajax({
                    type: 'post',
                    data: {categoryEid: _this.category.categoryEid, watch: !_this.category.onWatch},
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
                            _this.$emit("show-k-dialog", response.responseText);

                        }
                    }
                });
            }
        }
    })

    var mainVue = new Vue({
        el: '#container',
        data: function () {
            return {
                basePath: basePath,
                categories: [],
                kDialogClass : 'k-dialog-hide',
                kDialogHtml : ''
            }
        },
        created: function () {
            var _this = this;
            $.ajax({
                type: 'get',
                url: _this.basePath + '/getCategoryListWithImage',
                success: function (data) {
                    if (!data) {
                        _this.$message({
                            showClose: true,
                            message: '服务器异常',
                            type: 'error'
                        });
                    }
                    _this.categories = data.data;
                }
            })
        },
        methods : {
            showKDialog : function (html) {
                this.kDialogClass = "k-dialog-show";
                this.kDialogHtml = html;
            },
            closeKDialog :function () {
                this.kDialogClass = 'k-dialog-hide';
            }
        },
        components: {
            'category-item': categoryItem,
            "dialog-wrapper": DialogWrapper.component
        }


    });
</script>