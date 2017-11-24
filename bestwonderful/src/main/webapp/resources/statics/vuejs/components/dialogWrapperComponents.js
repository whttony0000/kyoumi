var DialogWrapper = function () {
    var component = Vue.extend({
        template: '\
        <div :class="kdialogclass" style="z-index: 2003;">\
            <div class="k-dialog">\
                <a href="javascript:void(0)" class="k-close-btn" @click="closeKDialog"><i class="el-dialog__close el-icon el-icon-close"></i></a>\
                <div class="dialogWrapper"></div>\
            </div>\
        </div>\
        ',
        props: ['kdialogclass', 'html'],
        updated: function () {
            var dialogWrapper = $(this.$el).find(".dialogWrapper");
            if (dialogWrapper.text()) {
                return false;
            }
            dialogWrapper.append(this.html);
        },
        methods: {
            closeKDialog: function () {
                this.$emit("closekdialog");
            }
        }
    });
    return {
        component: component
    }
}()