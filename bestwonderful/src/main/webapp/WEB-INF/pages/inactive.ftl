<html>
<head>
    <title>verify | BestWonderful</title>
    <script src="/resources/statics/vuejs/vue.min.js"></script>
<#--<script src="/resources/statics/jQuery/jquery.js"></script>-->
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
<style>
    #sendMailContainer {
        margin: 10% auto;
        display: table;
        padding: 20px;
        box-shadow: 0 0 8px 0 rgba(0,0,0,.16);
    }

    .s-span {
        margin: 4px 0;
        display: block;
    }

</style>
<div>
    <div id="sendMailContainer">
        <span class="s-span">验证邮箱已发送至您的邮箱，请登录邮箱【${mail}】激活您的账号。</span>
        <el-button @click="sendActiveMail" type="primary" :disabled="!canResend">重新发送</el-button>
        <el-button @click="reloadPage" type="primary">已验证</el-button>
    </div>
</div>
</body>
<script>
    function ajax() {
        var ajaxData = {
            type: arguments[0].type || "GET",
            url: arguments[0].url || "",
            async: arguments[0].async || "true",
            data: arguments[0].data || null,
            dataType: arguments[0].dataType || "text",
            contentType: arguments[0].contentType || "application/x-www-form-urlencoded",
            beforeSend: arguments[0].beforeSend || function () {
            },
            success: arguments[0].success || function () {
            },
            error: arguments[0].error || function () {
            }
        }
        ajaxData.beforeSend()
        var xhr = createxmlHttpRequest();
        xhr.responseType = ajaxData.dataType;
        xhr.open(ajaxData.type, ajaxData.url, ajaxData.async);
        xhr.setRequestHeader("Content-Type", ajaxData.contentType);
        xhr.send(convertData(ajaxData.data));
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    ajaxData.success(xhr.response)
                } else {
                    ajaxData.error()
                }
            }
        }
    }

    function createxmlHttpRequest() {
        if (window.ActiveXObject) {
            return new ActiveXObject("Microsoft.XMLHTTP");
        } else if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        }
    }

    function convertData(data) {
        if (typeof data === 'object') {
            var convertResult = "";
            for (var c in data) {
                convertResult += c + "=" + data[c] + "&";
            }
            convertResult = convertResult.substring(0, convertResult.length - 1)
            return convertResult;
        } else {
            return data;
        }
    }


    var sendMailVue = new Vue({
        el: '#sendMailContainer',
        data: function () {
            return {
                canResend: true
            }
        },
        methods: {
            sendActiveMail: function () {
                var url = location.pathname + location.search + location.hash;
                var _this = this;
                ajax({
                    type: 'post',
                    data: {url: url},
                    url: '/task/sendActiveMail',
                    dataType : 'json',
                    success: function (response) {
                        console.log(JSON.stringify(response));
                        console.log(response);
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
                        _this.$message({
                            showClose: true,
                            message: '验证邮件已发送至您的邮箱，请前往验证...',
                            type: 'success'
                        });
                        _this.disableReSend();
                        setTimeout(_this.enableReSend, 2 * 60 * 1000);
                    }
                });
            },
            reloadPage : function () {
              location.href = location.href;
            },
            enableReSend: function () {
                this.canResend = true;
            },
            disableReSend: function () {
                this.canResend = false;
            }
        }

    });
</script>
</html>