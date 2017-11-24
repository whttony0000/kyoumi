<style>
    .text {
        font-size: 14px;
    }

    .item {
        padding: 18px 0;
    }

    .box-card {
        width: 480px;
    }
</style>
<head>
    <title>
        about | BestWonderful
    </title>
</head>
<div class="container main" id="aboutContainer">

    <el-collapse v-model="activeNames">
        <el-collapse-item title="todo" name="todo">
            <el-card class="box-card">
                <div class="text item">
                    markdown editor
                </div>
                <div class="text item">
                    active chart
                </div>
                <div class="text item">
                    notice message
                </div>
                <div class="text item">
                    bookmark
                </div>
                <div class="text item">
                    fan
                </div>
                <div class="text item">
                    change pass
                </div>
                <div class="text item">
                    search engine
                </div>
                <div class="text item">
                    detail of author
                </div>
                <div class="text item">
                    crawler for data service
                </div>
                <div class="text item">
                    draft
                </div>
                <div class="text item">
                    remember me
                </div>
            </el-card>
        </el-collapse-item>
    </el-collapse>
</div>
<script type="text/javascript">
    var aboutVue = new Vue({
        el : "#aboutContainer",
       data : function () {
           return {
               activeNames : ['todo']
           }
       }
    });

</script>