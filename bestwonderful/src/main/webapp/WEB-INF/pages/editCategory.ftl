<div id="container"  class="main">
    <el-form ref="form" :model="form" label-width="80px" label-suffix="：">
        <el-form-item label="图片">
            <image-upload ref="imageUpload" :images="photos"
                          :type="'category'"
                          :uploadurl="basePath + '/upload/image'"
                          :max="1">
            </image-upload>
        </el-form-item>
        <el-form-item label="名称">
            <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="描述">
            <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">{{isEdit==0 ? '立即创建':'保存'}}</el-button>
            <el-button>取消</el-button>
        </el-form-item>
    </el-form>
</div>
<script src="/resources/statics/vuejs/components/imageComponents.js"></script>
<link rel=stylesheet href="/resources/statics/css/common.css"></link>
<script>
    var mainVue = new Vue({
        el: '#container',
        data: function () {
            return {
                basePath : basePath,
                form: {
                    name: '',
                    description: ''
                },
                photos: [],
                isEdit: ${isEdit},
                categoryId: ${categoryId}
            }
        },
        created : function () {
          var _this = this;
            if (_this.isEdit == 0) {
                return false;
            }
            if (!_this.categoryId) {
                _this.$message({
                    showClose: true,
                    message: '分类ID为空',
                    type: 'error'
                });
                return false;
            }
            $.ajax({
                url : basePath + '/getCategoryDetail',
                type : 'post',
                data : {categoryId : _this.categoryId},
                success : function (data) {
                    if (!data) {
                        _this.$message({
                            showClose: true,
                            message: '服务器异常',
                            type: 'error'
                        });
                        return false;
                    }
                    if (data.status == 0) {
                        _this.$message({
                            showClose: true,
                            message: data.message,
                            type: 'error'
                        });
                        return false;
                    }
                    _this.form = data.data;
                    _this.photos.push(data.data.imageModel);

                }
            })
        },
        methods: {
            onSubmit:function() {
                var _this = this;
                if (_this.photos.length == 0) {
                    _this.$message({
                        showClose: true,
                        message: '请选择图片',
                        type: 'warning'
                    });
                }
                if (!_this.form.name) {
                    _this.$message({
                        showClose: true,
                        message: '请输入名称',
                        type: 'warning'
                    });
                }
                if (!_this.form.description) {
                    _this.$message({
                        showClose: true,
                        message: '请输入描述',
                        type: 'warning'
                    });
                }
                _this.form.imageModel = _this.photos[0];
                $.ajax({
                   type : 'post',
                    data : JSON.stringify(_this.form),
                    url : _this.basePath + '/upsertCategory',
                    dataType:'json',
                    contentType:'application/json;charset=utf-8',
                    success : function (data) {
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
                        _this.$message({
                            showClose: true,
                            message: "保存成功！",
                            type: 'success'
                        });
                    }
                });
            }
        },
        components: {"image-upload": ImageComponents.imageUpload.component}


    });
</script>