<html>
<head>
    <title>jump | BestWonderful</title>
    <script src="/resources/statics/vuejs/vue.min.js"></script>
    <script src="/resources/statics/jQuery/jquery.js"></script>
    <script src="/resources/statics/element/element.js"></script>
    <script src="/resources/statics/element/zh-CN.js"></script>
    <link href="/resources/statics/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/statics/bootstrap/css/font-awesome.min.css">
    <script src="/resources/statics/bootstrap/js/bootstrap.min.js"></script>
    <link rel=stylesheet href="/resources/statics/element/index.css"/>

    <script>ELEMENT.locale(ELEMENT.lang.zhCN)</script>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
</head>
<body>
<div id="activeContainer">
<#if error>
    <div>
        <h2>${error}</h2>
        <div>
            <#if errorCode == 0>
                <el-button @click="toLoginPage" type="primary">登录</el-button>
            <#else >
                <el-button @click="toLoginPage" type="primary">登录</el-button>
            </#if>
        </div>
    </div>
<#else >
    <div>
        <h2>验证成功 正在跳转...</h2>
        <el-button @click="jump(link)" type="primary">跳转</el-button>
    </div>
</#if>

</div>
<#include "footer.ftl">
</body>
<script>
    function jumpBack(link) {
        console.log("yyyyyyyyyyy");
        setTimeout((link) => {
            console.log("zzzzzzzzzz");
            location.href = activeVue.link;
        }, 5000);
    }


    var activeVue = new Vue({
        el: "#activeContainer",
        data: function () {
            return {
                error: '${error}',
                link: '${link}'
            }
        },
        created: function () {
            var _this = this;
            if (!_this.error && _this.link) {
                console.log("xxxxxxxxxxxxx");
                _this.jb(_this.link);
            }

        },
        methods: {
            jb: function (link) {
                jumpBack(link);
            },
            jump : function (link) {
                location.href = link;
            },
            toLoginPage : function () {
                this.jump("/loginPage");
            }
        }
    });

</script>
</html>