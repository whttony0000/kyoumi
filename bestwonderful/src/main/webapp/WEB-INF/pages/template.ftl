<#import "spring.ftl" as s />
<#assign basePath=request.contextPath />
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <script src="/resources/statics/vuejs/vue.min.js"></script>
    <script src="/resources/statics/jQuery/jquery.js"></script>
    <script src="/resources/statics/element/element.js"></script>
    <script src="/resources/statics/element/zh-CN.js"></script>
    <link href="/resources/statics/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/statics/bootstrap/css/font-awesome.min.css">
    <script src="/resources/statics/bootstrap/js/bootstrap.min.js"></script>
    <link rel=stylesheet href="/resources/statics/element/index.css"/>

    <script>ELEMENT.locale(ELEMENT.lang.zhCN)</script>
    <script type="text/javascript">
        var basePath = "${basePath}";
    </script>
    <base id="basePath" href="${basePath}">
    <style>
        .main {
            margin-top: 30px;
        }

        body {
            height: 100%;
        }

        footer {
            text-align: center;
        }
    </style>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />

</head>
<#include "header.ftl">
<body>
<#include "${inner_page}">
<#include "footer.ftl">
</body>
<footer></footer>