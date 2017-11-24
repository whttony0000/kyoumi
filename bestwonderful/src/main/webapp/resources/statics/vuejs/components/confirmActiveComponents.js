var ConfirmActiveComponent = function () {

    var component = Vue.extend({
        template: '\
        <el-dialog title="账号激活" v-model="visible">\
            {{txt}}\
            <el-button @click="sendActiveMail" type="primary" :disabled="!canResend">重新发送</el-button>\
        </el-dialog>\
        ',
        props: ['visible', 'txt'],
        data : function () {
            return {
                canResend : true,

            }
        },
        methods: {
            sendActiveMail: function () {
                var url = location.pathname + location.search + location.hash;
                var _this = this;
                $.ajax({
                    type: 'post',
                    data: {url: url},
                    url: '/task/sendActiveMail',
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
                        _this.$message({
                            showClose: true,
                            message: '验证邮件已发送至您的邮箱，请前往验证...',
                            type: 'success'
                        });
                        _this.disableReSend();
                        setTimeout(_this.enableReSend,2*60*1000);
                    }
                });
            },
            enableReSend : function () {
                this.canResend = true;
            },
            disableReSend : function () {
                this.canResend = false;
            }
        }
    });
    return {
        component: component
    }
}()