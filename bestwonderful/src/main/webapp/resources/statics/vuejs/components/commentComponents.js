var CommentCompoent = function () {
    var subCommentItem = Vue.extend({
        template: "\
        <div>\
            <div class='s-c-wrapper'>\
                <p>\
                    <a :href='subauthorhref'>{{sub.individualName}}</a>\
                    ：\
                    <span><a :href='subtargethref' class='s-c-target-a'>@{{sub.targetIndividualName}}</a>{{sub.content}}</span>\
                </p>\
                <div class='s-c-b-group'>\
                    <span>{{sub.createTime}}</span>\
                    <a href='javascript:void(0)' @click='subComment'>\
                        <i class='icon-comment-alt'></i>\
                        <span>回复</span>\
                    </a>\
                </div>\
            </div>\
        </div>\
        ",
        props: ['sub'],
        data: function () {
            return {}
        },
        computed: {
            subauthorhref: function () {
                return '/individualDetail?individualEid=' + this.sub.individualEid;
            },
            subtargethref: function () {
                return '/individualDetail?individualEid=' + this.sub.targetIndividualEid;
            }
        },
        methods: {
            subComment: function () {
                this.$emit("sub-comment", this.sub.commentId, this.sub.individualEid, this.sub.individualName);
            }
        }
    });
    var commentItem = Vue.extend({
        template: "\
        <div class='comment'>\
            <div>\
                <div class='comment-author'>\
                    <a :href='authorhref' class='comment-avatar'>\
                        <img class='comment-i-photo' :src='comment.photoUrl'>\
                    </a>\
                    <div class='c-a-info'>\
                        <a :href='authorhref'>\
                            {{comment.individualName}}\
                        </a>\
                        <div class='c-a-meta'>\
                            <span>{{comment.floor}}楼 · {{comment.createTime}}</span>\
                        </div>\
                    </div>\
                </div>\
                <div class='comment-wrap'>\
                    <p class='c-w-p'>{{comment.content}}</p>\
                    <div class='c-w-b-group'>\
                        <a class='' href='javascript:void(0)' @click='subComment(comment.id,comment.individualEid,comment.individualName)'>\
                            <i class='icon-comment-alt'></i> \
                            <span>回复</span>\
                        </a>\
                    </div>\
                </div>\
            </div>\
            <div class='sub-comment' v-if='comment.subComments'>\
                <sub-comment ref='sub' v-for='sub in comment.subComments' :sub='sub' @sub-comment='subComment'></sub-comment>\
                <div class='more-comment'><a class='add-comment-btn' @click='subComment(comment.id,comment.individualEid,comment.individualName)'><i class='icon-pencil'></i><span>添加评论</span></a></div>\
            </div>\
            <div class='new-comment' v-if='shownc'>\
                <div>\
                    <span class='n-c-t-span'>回复{{targetIndividualName}}：</span>\
                    <el-input type='textarea' :autosize='{minRows:3,maxRows:4}' placeholder='请输入您的评论' v-model='newComment' :maxlength='500'></el-input>\
                    <div class='c-b-group'>\
                        <el-button :plain='true' type='warning' size='small' @click='cancelComment'>取消</el-button>\
                        <el-button type='primary' size='small' @click='submitSubComment'>发表</el-button>\
                    </div>\
                </div>\
            </div>\
        </div>\
        ",
        data: function () {
            return {
                shownc: false,
                newComment: '',
                commentId: 0,
                targetIndividualEid: '',
                targetIndividualName: ''
            }
        },
        computed: {
            authorhref: function () {
                return "/individualDetail?individualEid=" + this.comment.individualEid;
            }
        },
        created: function () {

        },
        mounted : function () {
            this.loadPhoto();
        },
        updated : function () {
            this.loadPhoto();
        },
        props: ['comment'],
        methods: {
            loadPhoto : function () {
                $(this.$el).find(".identicon").remove();
                $(this.$el).find(".comment-avatar").append("<span class='identicon'>"+this.comment.mailMd5Hash+"</span>");
                if (this.comment.photoUrl) {
                    $(this.$el).find(".identicon").hide();
                    $(this.$el).find(".comment-i-photo").show();
                } else {
                    $(this.$el).find(".identicon").identicon5();
                    $(this.$el).find(".identicon").show();
                    $(this.$el).find(".comment-i-photo").hide();
                }
            },
            subComment: function (commentId, targetIndividualEid, targetIndividualName) {
                this.shownc = true;
                $(this.$el).find(".el-textarea__inner").focus();
                this.commentId = commentId;
                this.targetIndividualEid = targetIndividualEid;
                this.targetIndividualName = targetIndividualName;
            },
            submitSubComment: function () {
                var _this = this;
                // if (!_this.newComment) {
                //     _this.$notify.info({
                //         title: '消息',
                //         message: "请输入您的评论"
                //     });
                //     return false;
                // }
                $.ajax({
                    type: 'post',
                    data: {
                        commentId: _this.commentId,
                        targetIndividualEid: _this.targetIndividualEid,
                        content: _this.newComment
                    },
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    url: 'submitSubComment',
                    success: function (response) {
                        if (!response) {
                            _this.$message({
                                showClose: true,
                                message: '服务器异常',
                                type: 'error'
                            });
                            return false;
                        }
                        if (!response.status) {
                            _this.$emit("show-login-dialog",response);
                            return false;
                        }
                        if (response.status != 1) {
                            _this.$notify.info({
                                title: '消息',
                                message: response.message
                            });
                            return false;
                        }
                        _this.reInitParam();
                        if (_this.comment.subComments && Array.isArray(_this.comment.subComments)) {
                            _this.comment.subComments.push(response.data);
                        } else {
                            _this.comment.subComments = [response.data];
                        }
                    },
                    error : function (response) {
                        if (response.status == 401) {
                            _this.$emit("show-active-dialog",response.responseText);
                            return false;
                        }
                    }
                });
            },
            cancelComment: function () {
                this.newComment = '';
                this.shownc = false;
            },
            reInitParam: function () {
                this.shownc = false;
                this.newComment = '';
                this.commentId = 0;
                this.targetIndividualEid = '';
            }
        },
        components: {
            "sub-comment": subCommentItem
        }
    });

    var component = Vue.extend({
        template: "\
        <div>\
            <div v-if='total && total > 0'>\
                <div class='comment-total-div'>\
                    {{total}}条评论\
                </div>\
                <comment-item v-for='comment in comments' :comment='comment' @reload='reloadComment' @show-login-dialog='showLoginDialog' @show-active-dialog='showActiveDialog'></comment-item>\
            </div>\
        </div>\
        ",
        props: ['comments', 'total'],
        data: function () {
            return {}
        },
        methods: {
            reloadComment: function () {
                this.$emit("reload-comment");
            },
            showLoginDialog : function (response) {
                this.$emit("show-login",response)
            },
            showActiveDialog : function (response) {
                this.$emit("show-active", response);
            }
        },
        components: {
            "comment-item": commentItem
        }

    });
    return {
        commentList: component
    }
}()