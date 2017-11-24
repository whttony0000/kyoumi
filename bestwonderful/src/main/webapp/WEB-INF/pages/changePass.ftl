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

        <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="form.pass" auto-complete="off" placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="form.checkPass" auto-complete="off" placeholder="请再次输入密码"></el-input>
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
            <el-button type="primary" @click="submitForm('form')">提交</el-button>
            <el-button @click="resetForm('form')">重置</el-button>
        </el-form-item>
    </el-form>
</div>
<script src="/resources/statics/vuejs/components/imageComponents.js"></script>
<link rel=stylesheet href="/resources/statics/css/common.css">
<script>
    var editIndividualVue = new Vue({
        el: '#editIndividualContainer',
        data: function () {
            var validateName = (rule, value, callback) => {
            };
            var validatePass = (rule, value, callback) => {
                if (this.form.checkPass !== '') {
                    this.$refs.form.validateField('checkPass');
                }
                callback();
            };
            var validatePass2 = (rule, value, callback) => {
                if (value !== this.form.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                basePath: basePath,
                individualId:${individualId},
                form: {
                    name: '',
                    birthday: '',
                    description: '',
                    pass: '',
                    checkPass: '',
                    photos: []
                },
                rules: {
                    name: [
                        {required: true, message: '请输入名字', trigger: 'blur'}
                    ],
                    pass: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {required: true, message: '请再次输入密码', trigger: 'blur'},
                        {validator: validatePass2, trigger: 'blur'}
                    ]
                },
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() >= Date.now() - 8.64e7;
                    }
                }
            }
        },
        computed: {
            descriptionWordCnt: function () {
                return this.form.description.length;
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
                        individual.birthday = _this.form.birthday;
                        individual.passwd = _this.form.pass;
                        individual.description = _this.form.description;
                        individual.photoKey = photo;
                        $.ajax({
                           type:'post',
                            data:individual,
                            url:_this.basePath + '/saveIndividual',
                            success:function (response) {
                                console.log(response);
                            }
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        },
        components: {"image-upload": ImageComponents.imageUpload.component}


    });
</script>