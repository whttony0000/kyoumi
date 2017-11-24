# kyoumi
一个ssm构架网站项目。官网：[www.bestwonderful.com](http://www.bestwonderful.com)

## 项目概况

### 模块
分为博客模块和数据服务模块
- 博客模块提供博客编辑，展示，分类，评论，积分，通知等功能。
- 数据服务模块提供公共数据，主要来自网络爬虫和[pythonCrawler](https://github.com/whttony0000/pythonCrawler)。

### 项目结构
- bestwonderful 网站主体。博客，数据服务展示。
- common 各模块公用工具类管理。
- mongodata 数据服务模块数据来源。
- solrdata 搜索引擎模块（待完善）。
- webspider 网络爬虫模块，数据服务部分数据提供者。

### 项目技术选型
- 基础 [springframework](https://spring.io/) [springMVC](https://spring.io/guides/gs/serving-web-content/) [Mybatis](http://www.mybatis.org/mybatis-3/getting-started.html) [Freemarker](https://freemarker.apache.org/)
- 权限 [shiro](https://shiro.apache.org/)
- 前端 [Vuejs](https://vuejs.org/) [elementui](http://element-cn.eleme.io/#/en-US/component/installation) [ueditor](http://ueditor.baidu.com/website/) [echarts](http://echarts.baidu.com/)

### 项目配置
博客模块
- mysql准备，DDL详见[kyoumi.sql](https://github.com/whttony0000/kyoumi/blob/master/docs/kyoumi.sql)
- 配置[jdbc.properties](https://github.com/whttony0000/kyoumi/blob/master/bestwonderful/src/main/resources/conf/dev/jdbc.properties)
- 邮件，七牛云准备，配置[other.properties](https://github.com/whttony0000/kyoumi/blob/master/bestwonderful/src/main/resources/conf/dev/other.properties)

数据模块（数据模块配置与否不会影响博客模块）
- mongoDB准备
- 配置[mongo.properties](https://github.com/whttony0000/kyoumi/blob/master/bestwonderful/src/main/resources/conf/dev/other.properties)
- 部分数据依赖[pythonCrawler](https://github.com/whttony0000/pythonCrawler)，所以需要配置PythonCrawler或者选择其他数据来源。








