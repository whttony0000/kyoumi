<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>login | BestWonderful</title>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/resources/statics/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/statics/bootstrap/css/font-awesome.min.css">
    <script src="/resources/statics/jQuery/jquery.js"></script>
    <script src="/resources/statics/vuejs/vue.min.js"></script>
    <script src="/resources/statics/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resources/statics/element/element.js"></script>
    <script src="/resources/statics/element/zh-CN.js"></script>
    <link rel=stylesheet href="/resources/statics/element/index.css"></link>

    <script>ELEMENT.locale(ELEMENT.lang.zhCN)</script>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style>
        .login-box {
            width: 300px;;
            margin: 7% auto;
        }

        .signInButtons {
            border-top: 1px solid #ddd;
            padding-top: 10px;
        }
    </style>
</head>
<body class="hold-transition login-page">
<div class="login-box" id="loginContainer" v-cloak>
    <div class="login-logo">
        <b></b>
    </div>
    <el-tabs v-model="activeName">
        <el-tab-pane label="登录" name="signIn">

            <el-form :model="signInForm" ref="signInForm" :rules="rules">

                <el-form-item
                        prop="email"
                        label="">
                    <el-input v-model="signInForm.email" placeholder="邮箱" :maxlength="30"></el-input>

                </el-form-item>

                <el-form-item label="" prop="password">
                    <el-input type="password" v-model="signInForm.password" auto-complete="off"
                              placeholder="密码" :maxlength="20"></el-input>
                </el-form-item>

                <el-form-item label="" prop="captcha">
                    <el-input type="captcha" v-model="signInForm.captcha" placeholder="验证码"
                              @keyup.enter="signIn" :maxlength="10"></el-input>
                </el-form-item>

                <el-form-item>
                    <img alt="如果看不清楚，请单击图片刷新！" class="pointer" :src="src" @click="refreshCode">
                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" @click="refreshCode">点击刷新</a>
                </el-form-item>

            </el-form>

            <div class="signInButtons">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                    </div>
                </div>
                <div class="col-xs-4">
                    <button type="button" class="btn btn-primary btn-block btn-flat" @click="signIn">登录</button>
                </div>
            </div>

        </el-tab-pane>
        <el-tab-pane label="注册" name="signUp">
            <el-form :model="signUpForm" ref="signUpForm" :rules="rules">

                <el-form-item
                        prop="name"
                        label="">
                    <el-input v-model="signUpForm.name" placeholder="名称" :maxlength="8"></el-input>

                </el-form-item>

                <el-form-item
                        prop="email"
                        label="">
                    <el-input v-model="signUpForm.email" placeholder="邮箱" :maxlength="30"></el-input>

                </el-form-item>

                <el-form-item label="" prop="password">
                    <el-input type="password" v-model="signUpForm.password" auto-complete="off"
                              placeholder="密码" :maxlength="20"></el-input>
                </el-form-item>

                <el-form-item label="" prop="checkPass">
                    <el-input type="password" v-model="signUpForm.checkPass" auto-complete="off"
                              placeholder="请再次输入密码" :maxlength="20"></el-input>
                </el-form-item>

                <el-form-item label="" prop="captcha">
                    <el-input type="captcha" v-model="signUpForm.captcha" placeholder="验证码"
                              @keyup.enter="signUp" :maxlength="10"></el-input>
                </el-form-item>

                <el-form-item>
                    <img alt="如果看不清楚，请单击图片刷新！" class="pointer" :src="src" @click="refreshCode">
                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" @click="refreshCode">点击刷新</a>
                </el-form-item>

            </el-form>

            <div class="signInButtons">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                    </div>
                </div>
                <div class="col-xs-4">
                    <button type="button" class="btn btn-primary btn-block btn-flat" @click="signUp">注册</button>
                </div>
            </div>
        </el-tab-pane>
    </el-tabs>
    <!-- /.login-logo -->

    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
<#--<script src="statics/libs/jquery.slimscroll.min.js"></script>-->
<#--<script src="statics/libs/fastclick.min.js"></script>-->
<#--<script src="statics/libs/app.js"></script>-->
<script type="text/javascript">
    var vm = new Vue({
        el: '#loginContainer',
        data: function () {
            var validatePass = (rule, value, callback) => {
                if (this.signUpForm.checkPass !== '') {
                    this.$refs.signUpForm.validateField('checkPass');
                }
                callback();
            };
            var validateCheckPass = (rule, value, callback) => {
                if (value !== this.signUpForm.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            var validateName = (rule,value,callback) => {
                if (!/^[\u2E80-\u9FFF\_\da-zA-Z]+$/g.test(value)) {
                    callback(new Error('名称只能由英文字母汉字数字或下划线构成!'));
                }
                callback();
            };
            return {
                signInForm: {
                    email: '',
                    password: '',
                    captcha: ''
                },
                signUpForm: {
                    email: '',
                    name:'',
                    password: '',
                    checkPass: '',
                    captcha: ''
                },
                src: 'captcha.jpg',
                activeName: 'signIn',
                rules: {
                    email: [
                        {required: true, message: '请输入邮箱地址', trigger: 'blur'},
                        {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change'}
                    ],
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'},
                        { min: 3, max: 8, message: '名称长度必须3-8个字符', trigger: 'blur' },
                        {validator: validateName, trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {required: true, message: '请再次输入密码', trigger: 'blur'},
                        {validator: validateCheckPass, trigger: 'blur'}
                    ],
                    captcha: [
                        {required: true, message: '请输入验证码', trigger: 'blur'}
                    ]
                }
            }

        },
        beforeCreate: function () {
            if (self != top) {
                top.location.href = self.location.href;
            }
        },
        methods: {
            refreshCode: function () {
                this.src = "captcha.jpg?t=" + $.now();
            },
            signIn: function (event) {
                var _this = this;
                _this.$refs.signInForm.validate(
                        (valid) => {
                            if (valid) {
                                _this.submit(_this.signInForm);
                            } else {
                                console.log('error submit!!');
                                return false;
                            }
                        }
                );
            },
            submit: function (data) {
                var _this = this;
                $.ajax({
                    type: "POST",
                    url: "/signIn",
                    data: data,
                    dataType: "json",
                    success: function (result) {
                        if (!result) {
                            _this.$message.error("服务器异常");
                            _this.refreshCode();
                            return;
                        }
                        if (result.status == 1) {
                            parent.location.href = '/index';
                        } else {
                            _this.$message.error(result.message);
                            _this.refreshCode();
                            return;
                        }
                    }
                });
            },
            signUp: function (event) {
                var _this = this;
                _this.$refs.signUpForm.validate(
                        (valid) => {
                            if (valid) {
                                $.ajax({
                                    type: "POST",
                                    url: "/signUp",
                                    data: _this.signUpForm,
                                    dataType: "json",
                                    success: function (result) {
                                        if (!result) {
                                            _this.$message.error("服务器异常");
                                            _this.refreshCode();
                                            return;
                                        }
                                        if (result.status == 1) {
                                            _this.submit(_this.signUpForm);
                                        } else {
                                            _this.$message.error(result.message);
                                            _this.refreshCode();
                                            return;
                                        }
                                    }
                                });
                            } else {
                                console.log('error submit!!');
                                return false;
                            }
                        }
                );


            }
        }
    });
</script>
</body>
</html>
