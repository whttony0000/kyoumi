<style>
    .article-item {
        padding: 24px;
        border-bottom: 1px solid #ccc;
        word-wrap: break-word;
    }
</style>

<head>
    <title>
        articles | BestWonderful
    </title>
</head>
<div id="articleListContainer" class="main container">
    <article-list
            :param="{url : 'getArticleList',currentPage : 1,pageSize : 10}"></article-list>
</div>
<link href="/resources/statics/css/articleList.css" rel="stylesheet"/>
<script src="/resources/statics/vuejs/components/articleList.js"></script>
<script src="/resources/statics/jQuery/identicon5.min.js"></script>
<script>
    var articleListVue = new Vue({
        el:"#articleListContainer",
        data:function () {
            return {
                articleList:[
                ]
            }
        },
        created : function () {
            var _this = this;
          $.ajax({
              type:'post',
              url:basePath +'/getArticleList',
              success:function (response) {
                  if (!response) {
                      _this.$message({
                          showClose: true,
                          message: '服务器异常',
                          type: 'error'
                      });
                      return false;
                  }
                  _this.articleList = response.data;
              }
          });
        },
       components:{
           "article-list":articleListComponent.articleList.component
       }
    });
</script>