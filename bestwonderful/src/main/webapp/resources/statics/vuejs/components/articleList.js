var articleListComponent = function () {

    var _articleList = function () {
        var _articleItem = function () {
            var _component = Vue.extend(
                {
                    template: "\
                    <div class='article-item'>\
                        <h3>\
                        <a :href='articlehref' class='article-link'>{{article.title}}</a>\
                        </h3>\
                        <div class='article-memo'>{{article.memo}}</div>\
                        <div class='article-category'>\
                            <a :href='categoryhref' class='post-category'>{{article.categoryName}}</a>\
                        </div>\
                        <div class='article-statistics'>\
                            <span class='a-s-span-1'><i class='icon-heart'></i><span class='a-s-s-span'>{{article.articleLikeCnt}}</span></span>\
                            <span class='a-s-span-2'><i class='icon-bookmark'></i><span class='a-s-s-span'>{{article.articleBookmarkCnt}}</span></span>\
                            <span class='a-s-span-3'><i class='icon-comment'></i><span class='a-s-s-span'>{{article.articleCommentCnt}}</span></span>\
                        </div>\
                        <div class='article-individual'>\
                            <div class='user-info '>\
                                <div class='user-action-time'>\
                                    created <span class='relativetime'>{{article.createTime}}</span>\
                                </div>\
                                <div class='user-gravatar32'>\
                                    <a :href='individualhref'>\
                                        <div class='gravatar-wrapper-32'>\
                                            <img class='i-photo' :src='article.individualPhotoUrl' alt='' width='32' height='32'>\
                                        </div>\
                                    </a>\
                                </div>\
                                <div class='user-details'>\
                                    <a :href='individualhref'>{{article.individualName}}</a>\
                                    <div class='-flair'>\
                                        <span class='reputation-score' title='reputation score ' dir='ltr'>{{article.individualScore}}</span>\
                                    </div>\
                                </div>\
                            </div>\
                        </div>\
                    </div>\
                    ",
                    data: function () {
                        return {
                        }
                    },
                    mounted : function () {
                        this.loadPhoto();
                    },
                    updated:function () {
                        this.loadPhoto();
                    },
                    computed: {
                        articlehref: function () {
                            return basePath + "/articleDetail?articleEid=" + this.article.articleEid;
                        },
                        categoryhref: function () {
                            return basePath + '/categoryDetail?categoryEid=' + this.article.categoryEid;
                        },
                        individualhref : function () {
                            return basePath + '/individualDetail?individualEid=' + this.article.individualEid;
                        }
                    },
                    methods : {
                      loadPhoto : function () {
                          $(this.$el).find(".identicon").remove();
                          $(this.$el).find(".gravatar-wrapper-32").append("<span class='identicon'>"+this.article.individualMailCode+"</span>");
                          if (this.article.individualPhotoUrl) {
                              $(this.$el).find(".identicon").hide();
                              $(this.$el).find(".i-photo").show();
                          } else {
                              $(this.$el).find(".identicon").identicon5();
                              $(this.$el).find(".identicon").show();
                              $(this.$el).find(".i-photo").hide();
                          }
                      }
                    },
                    props: ['article']

                }
            )
            return {
                component: _component
            }
        }()

        var _iArticleItem = function () {
            var _component = Vue.extend(
                {
                    template: "\
                    <div class='article-item'>\
                        <h3>\
                        <a :href='articlehref' class='article-link'>{{article.title}}</a>\
                        </h3>\
                        <a :href='editArticle' class='edit-article-link' v-if='isSelf==1'>编辑</a>\
                        <div class='article-memo'>{{article.memo}}</div>\
                        <div class='article-category'>\
                            <a :href='categoryhref' class='post-category'>{{article.categoryName}}</a>\
                        </div>\
                    </div>\
                    ",
                    data: function () {
                        return {
                        }
                    },
                    created:function () {
                    },
                    mounted : function () {
                    },
                    updated:function () {
                    },
                    computed: {
                        articlehref: function () {
                            return "/articleDetail?articleEid=" + this.article.articleEid;
                        },
                        categoryhref: function () {
                            return '/categoryDetail?categoryEid=' + this.article.categoryEid;
                        },
                        editArticle : function () {
                            return '/editArticle?articleEid=' + this.article.articleEid;
                        }
                    },
                    methods : {
                    },
                    props: ['article','isSelf']

                }
            )
            return {
                component: _component
            }
        }()

        var _component = Vue.extend(
            {
                template: "\
                <div class='article-list'>\
                <div class='article-box'>\
                <component ref='item' :is='item' v-for='article in articles' :article='article' :isSelf='isSelf'></component>\
                <div class='pagination'>\
                    <el-pagination\
                          @size-change='handleSizeChange'\
                          @current-change='handleCurrentChange'\
                          :current-page='currentPage'\
                          :page-sizes='pageSizes'\
                          :page-size='pageSize'\
                          layout='total, sizes, prev, pager, next, jumper'\
                          :total='total'>\
                    </el-pagination>\
                    </div>\
                    </div>\
                </div>\
                ",
                data: function () {
                    return {
                        total: 0,
                        currentPage: 1,
                        pageSize: 10,
                        pageSizes: [10, 20, 40, 80],
                        articles: {},
                        item : 'article-item',
                        isSelf : 0
                    }
                },
                props: ['param','type'],
                created: function () {
                    if (this.param.currentPage) {
                        this.currentPage = this.param.currentPage;
                    }
                    if (this.param.pageSize) {
                        this.pageSize = this.param.pageSize;
                        var newPageSizes = [];
                        newPageSizes.push(this.pageSize, 2 * this.pageSize, 3 * this.pageSize, 4 * this.pageSize);
                        this.pageSizes = newPageSizes;
                    }
                    if (this.type && this.type == 'individual') {
                        this.item = 'i-article-item';
                    }
                    if (this.param.isSelf) {
                        this.isSelf = this.param.isSelf;
                    }
                    this.loadData();
                },
                updated() {
                },
                methods: {
                    handleSizeChange: function (val) {
                        this.param.pageSize = val;
                        this.loadData();
                    },
                    handleCurrentChange(val) {
                        this.param.currentPage = val;
                        this.loadData();
                    },
                    loadData: function () {
                        var _this = this;
                        $.ajax({
                            type: 'get',
                            data: _this.param,
                            url: basePath + "/" + _this.param.url,
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
                                _this.articles = response.data.rows;
                                _this.total = response.data.total;
                            }
                        });
                    }
                },
                components: {
                    "article-item": _articleItem.component,
                    "i-article-item":_iArticleItem.component
                }

            }
        )
        return {
            component: _component,
            articleItem: _articleItem,
            iArticleItem:_iArticleItem
        }
    }()

    return {
        articleList: _articleList
    }
}()