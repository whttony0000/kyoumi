<head>
    <title>
        data | BestWonderful
    </title>
</head>
<style>
    .r-t-b-o-div {
        width: 600px;
        height: 400px;
    }

    .gdp-year-div {
        width: 1100px;
        height: 600px;
    }

    .data-tabs {
        padding: 20px;
        box-shadow: 0 0 8px 0 rgba(0, 0, 0, .16);
    }

    .f-l-n-header {
        padding: 0 0 5px 5px;
    }

    .f-c-title {
        font-size: 18px;
        font-weight: 600;
    }
</style>
<div id="dataContainer" class="main container">
    <div class="data-tabs">
        <el-tabs v-model="activeTab">
            <el-tab-pane label="电影" name="boxOffice">
                <div id="realTimeBoxOffice" class="r-t-b-o-div"></div>

                <#--<grid ref="doubanMovieTop250Grid" :url="'getDoubanMovieTop250'" :filter="doubanMovieTop250Filter"-->
                      <#--:emptytext="'暂无排行'"-->
                      <#--:columns="-->
                            <#--[-->
                                <#--{prop:'ranking',label:'排名',width:80},-->
                                <#--{prop:'name',label:'名称',width:150},-->
                                <#--{prop:'member',label:'详情'}-->
                            <#--]"></grid>-->

            </el-tab-pane>
            <el-tab-pane label="财经" name="finance">
            <#--<div class="f-l-n-header">-->
            <#--<h2>最新资讯</h2>-->
            <#--</div>-->
                <el-collapse v-model="financeActives">
                    <el-collapse-item name="latestNews">
                        <template slot="title">
                            <span class="f-c-title">最新资讯</span>
                        </template>
                        <div id="latestNews">
                            <grid ref="latestNewsGrid" :url="'getLatestNews'" :filter="latestNewsGridFilter"
                                  :emptytext="'暂无财经新闻'"
                                  :columns="
                            [
                                {prop:'classify',label:'类型',width:80},
                                {label:'新闻',type:'linkSpecify',links:[{action:toNewsDetail}]},
                                {prop:'time',label:'发布时间',width:150}
                            ]"></grid>
                        </div>
                    </el-collapse-item>
                    <el-collapse-item name="stockTopList">
                        <template slot="title">
                            <span class="f-c-title">最新龙虎榜</span>
                        </template>
                        <div id="stockTopList">
                            <grid ref="stockTopListGrid" :url="'getStockTopList'" :filter="stockTopGridFilter"
                                  :emptytext="'暂无龙虎榜'"
                                  :columns="
                            [
                                {prop:'code',label:'代码',width:100},
                                {prop:'name',label:'名称',width:120},
                                {prop:'pchange',label:'涨跌幅',width:110},
                                {prop:'amount',label:'成交额(万)',width:120},
                                {prop:'buy',label:'买入额(万)',width:120},
                                {prop:'sell',label:'卖出额(万)',width:120},
                                {prop:'date',label:'日期',width:120},
                                {prop:'reason',label:'上榜原因'},
                            ]"></grid>
                        </div>
                    </el-collapse-item>
                </el-collapse>
            </el-tab-pane>
            <el-tab-pane label="宏观经济" name="gdp">
                <div id="gdp_year" class="gdp-year-div"></div>
            </el-tab-pane>
        </el-tabs>
    </div>

</div>
<script src="/resources/statics/vuejs/components/gridComponents.js"></script>
<script type="text/javascript" src="/resources/statics/echarts/echarts.common.min.js"></script>
<script>
    var dataVue = new Vue({
        el: "#dataContainer",
        data: function () {
            return {
                activeTab: "boxOffice",
                financeActives: ['latestNews', 'stockTopList']
            }
        },
        created: function () {

        },
        methods: {
            toNewsDetail: function (row) {
                window.open(row.url);
            },
            doubanMovieTop250Filter : function (data) {
                var list2Return = [];
                data.forEach(item => {
                    item = JSON.parse(item);
                    var nameArr = item.name;
                    if (!Array.isArray(nameArr )) {
                        nameArr = [nameArr];
                    }
                    var name = '';
                    nameArr.forEach(obj=> {
                        name = name + obj;
                    })
                    item.name = name;
                    list2Return.push(item);
                });
                return list2Return;
            },
            latestNewsGridFilter: function (data) {
                var list2Return = [];
                data.forEach(item => {
                    item = JSON.parse(item);
                    list2Return.push({classify: item.classify, text: item.title, url: item.url, time: item.time});
                });
                return list2Return;
            },
            stockTopGridFilter: function (data) {
                var list2Return = [];
                data.forEach(item => {
                    item = JSON.parse(item);
                    list2Return.push(item);
                });
                return list2Return;
            }
        },
        mounted: function () {
            this.$refs.latestNewsGrid.loadData({}, 1);
            this.$refs.stockTopListGrid.loadData({}, 1);
//            this.$refs.doubanMovieTop250Grid.loadData({}, 1);
        },
        components: {
            "grid": GridComponent.gridList.component,
        }

    });


    var realTimeBoxOfficeChart = echarts.init(document.getElementById("realTimeBoxOffice"));
    realTimeBoxOfficeChart.setOption({
        title: {
            text: '今日实时电影票房',
            subtext: '大陆票房',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{b}<br/>{c}" + "万" + " ({d}%)"
        },
        series: []

    });

    $.get('/data/realTimeBoxOffice').done(function (response) {
        if (!response) {
            dataVue.$message({
                showClose: true,
                message: '服务器异常',
                type: 'error'
            });
            return false;
        }
        if (response.status != 1) {
            dataVue.$message({
                showClose: true,
                message: response.message,
                type: 'error'
            });
            return false;
        }
        var data = response.data;
        if (!Array.isArray(data)) {
            data = [data];
        }
        var realTimeBoxOfficeData = [];
        data.forEach(item => {
            item = JSON.parse(item);
            realTimeBoxOfficeData.push({name: item.MovieName, value: item.BoxOffice});
        });
        realTimeBoxOfficeChart.setOption({
            series: [
                {
                    name: '票房占比',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0,0,0,0.5)'
                        }
                    },
                    data: realTimeBoxOfficeData
                }
            ]
        });
    });


    var gdpYearChart = echarts.init(document.getElementById("gdp_year"));

    $.get('/data/getGDPYear').done(function (response) {
        if (!response) {
            dataVue.$message({
                showClose: true,
                message: '服务器异常',
                type: 'error'
            });
            return false;
        }
        if (response.status != 1) {
            dataVue.$message({
                showClose: true,
                message: response.message,
                type: 'error'
            });
            return false;
        }
        var data = response.data;
        if (!Array.isArray(data)) {
            data = [data];
        }
        var yearArr = [];
        var gdpArr = [];
        var piArr = [];
        var siArr = [];
        var tiArr = [];
        data.forEach(item => {
            item = JSON.parse(item);
            yearArr.push(item.year);
            gdpArr.push(item.gdp);
            piArr.push(item.pi);
            siArr.push(item.si);
            tiArr.push(item.ti);
        });
        gdpYearChart.setOption({
            title: {
                text: '历年GDP统计',
                subtext: '单位：亿元',
                x: 'left'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            legend: {
                data: ['GDP', '第一产业', '第二产业', '第三产业']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    axisLabel : {
                        rotate : 90,
                        fontSize : 8,
                        formatter : '{value}年'
                    },
                    data: yearArr
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: 'GDP',
                    type: 'bar',
                    data: gdpArr
                },
                {
                    name: '第一产业',
                    type: 'bar',
                    stack: '广告',
                    data: piArr
                },
                {
                    name: '第二产业',
                    type: 'bar',
                    stack: '广告',
                    data: siArr
                },
                {
                    name: '第三产业',
                    type: 'bar',
                    stack: '广告',
                    data: tiArr
                }
            ]
        });
    });


</script>