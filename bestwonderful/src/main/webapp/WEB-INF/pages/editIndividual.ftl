<head>
    <title>
        edit | BestWonderful
    </title>
</head>

<style>
    .f-r {
        float: right;
    }
</style>
<div id="editIndividualContainer"  class="main container">
    <el-form :model="form" ref="form" :rules="rules" label-width="100px"
             class="demo-dynamic">
        <el-form-item label="头像" prop="photo">
            <image-upload ref="imageUpload" :images="form.photos"
                          :type="'individual'"
                          :uploadurl="basePath + '/upload/image'"
                          :max="1">
            </image-upload>
        </el-form-item>

        <el-form-item
                prop="name"
                label="名字">
            <el-input v-model="form.name" placeholder="请输入名字" name="name" :maxlength="8"></el-input>

        </el-form-item>

        <el-form-item
                prop="sex"
                label="性别">
            <el-radio class="radio" v-model="form.sex" label="0">男</el-radio>
            <el-radio class="radio" v-model="form.sex" label="1">女</el-radio>

        </el-form-item>

        <el-form-item
                prop="birthday"
                label="生日">
            <el-date-picker
                    v-model="form.birthday"
                    type="date"
                    placeholder="选择日期"
                    :editable=false
                    :picker-options="pickerOptions">
            </el-date-picker>

        </el-form-item>

        <el-form-item
                prop="description"
                label="个人简介" :rows="2">
            <el-input type="textarea" v-model="form.description" :maxlength="200"
                      placeholder="请输入个人简介(最多200字)"></el-input>
            <div>
                <span class="f-r">{{descriptionWordCnt}}/200</span>
            </div>
        </el-form-item>


        <el-form-item>
            <el-button type="warning" @click="cancel">取消</el-button>
            <el-button type="primary" @click="submitForm('form')">提交</el-button>
            <#--<el-button @click="resetForm('form')">重置</el-button>-->
        </el-form-item>
    </el-form>
</div>
<script src="/resources/statics/vuejs/components/imageComponents.js"></script>
<link rel=stylesheet href="/resources/statics/css/common.css">
<script>
    function formatDate(date, format) {
        var v = "";
        if (typeof date == "string" || typeof date != "object") {
            return date;
        }
        var year  = date.getFullYear();
        var month  = date.getMonth()+1;
        var day   = date.getDate();
        var hour  = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        var weekDay = date.getDay();
        var ms   = date.getMilliseconds();
        var weekDayString = "";

        if (weekDay == 1) {
            weekDayString = "星期一";
        } else if (weekDay == 2) {
            weekDayString = "星期二";
        } else if (weekDay == 3) {
            weekDayString = "星期三";
        } else if (weekDay == 4) {
            weekDayString = "星期四";
        } else if (weekDay == 5) {
            weekDayString = "星期五";
        } else if (weekDay == 6) {
            weekDayString = "星期六";
        } else if (weekDay == 7) {
            weekDayString = "星期日";
        }

        v = format;
        //Year
        v = v.replace(/yyyy/g, year);
        v = v.replace(/YYYY/g, year);
        v = v.replace(/yy/g, (year+"").substring(2,4));
        v = v.replace(/YY/g, (year+"").substring(2,4));

        //Month
        var monthStr = ("0"+month);
        v = v.replace(/MM/g, monthStr.substring(monthStr.length-2));

        //Day
        var dayStr = ("0"+day);
        v = v.replace(/dd/g, dayStr.substring(dayStr.length-2));

        //hour
        var hourStr = ("0"+hour);
        v = v.replace(/HH/g, hourStr.substring(hourStr.length-2));
        v = v.replace(/hh/g, hourStr.substring(hourStr.length-2));

        //minute
        var minuteStr = ("0"+minute);
        v = v.replace(/mm/g, minuteStr.substring(minuteStr.length-2));

        //Millisecond
        v = v.replace(/sss/g, ms);
        v = v.replace(/SSS/g, ms);

        //second
        var secondStr = ("0"+second);
        v = v.replace(/ss/g, secondStr.substring(secondStr.length-2));
        v = v.replace(/SS/g, secondStr.substring(secondStr.length-2));

        //weekDay
        v = v.replace(/E/g, weekDayString);
        return v;
    }

    var editIndividualVue = new Vue({
        el: '#editIndividualContainer',
        data: function () {
            var validateName = (rule, value, callback) => {
            };
            return {
                basePath: basePath,
                individualEid:'${individualEid}',
                form: {
                    name: '',
                    sex:'',
                    birthday: '',
                    description: '',
                    photos: []
                },
                rules: {
                    name: [
                        {required: true, message: '请输入名字', trigger: 'blur'}
                    ]
                },
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() >= Date.now() - 8.64e7;
                    }
                }
            }
        },
        created : function () {
            var _this = this;
            $.ajax({
                type: 'post',
                data: {individualEid: _this.individualEid},
                url: '/getIndividualDetail',
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
                    var detail = response.data;
                    _this.form.name = detail.name;
                    _this.form.description = detail.description;
                    _this.form.sex = String(detail.sex);
                    if (detail.birthday) {
                        _this.form.birthday = detail.birthday;
                    }
                    if (detail.photoUrl) {
                        _this.form.photos.push({url:detail.photoUrl,key:detail.photoKey});
                    }
                }
            });
        },
        computed: {
            descriptionWordCnt: function () {
                return this.form.description ? this.form.description.length : 0;
            }
        },
        mounted:function () {
        },
        methods: {
            submitForm(formName) {
                var _this = this;
                var photo = $(".individual").next().val();
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        var individual = {};
                        individual.name = _this.form.name;
                        individual.sex = _this.form.sex;
                        if (_this.form.birthday) {
                            individual.birthday = formatDate(_this.form.birthday,"yyyy-MM-dd");
                        }
                        individual.description = _this.form.description;
                        individual.photoKey = photo;
                        $.ajax({
                           type:'post',
                            data:JSON.stringify(individual),
                            dataType:'json',
                            contentType:'application/json;charset=utf-8',
                            url:_this.basePath + '/saveIndividual',
                            success:function (response) {
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
                                window.location.href='individualDetail?individualEid='+_this.individualEid;
                            }
                        });
                    } else {
                        _this.$message({
                            showClose: true,
                            message: '请检查输入项...',
                            type: 'error'
                        });
                        return false;
                    }
                });
            },
            cancel: function () {
                location.href = document.referrer;
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        },
        components: {"image-upload": ImageComponents.imageUpload.component}


    });
</script>