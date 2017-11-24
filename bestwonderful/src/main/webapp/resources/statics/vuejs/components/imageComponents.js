/**
 * Created by haitao.wang on 2017/4/12.
 */
var ImageComponents = function () {
    var _imageTable = function () {
        var _imageBox = function () {
            return {
                component: Vue.extend({
                    template: '\
                    <div class="ui-medium-min img-div">\
                    <a href="javascript:void(0)" class="ui-medium-min-cover shadow-box" @click="imgView">\
                    <img :src="src" alt="" :class="imgclass">\
                    <span class="ui-medium-title">{{type}}</span>\
                    </a>\
                    </div>\
                    '
                    ,
                    props: ['src', 'type', 'imgclass'],
                    methods: {
                        imgView: function () {
                            window.open(this.src);
                        }
                    }
                })
            }
        }()

        var _imageTableComponent = Vue.extend({
            template: '\
            <table class="pure-table pure-table-bordered" v-if="imageTableVisable">\
            <colgroup>\
            <col width="10%">\
            </colgroup>\
            <tbody>\
            <tr>\
            <td>\
            <div class="img-header">\
            <span>{{type}}</span>\
            </div>\
            </td></tr><tr><td>\
            <imageBox v-for="src in images" :src="src" :type="type" :imgclass="imgclass"></imageBox>\
            </td></tr></tbody></table>'
            ,
            props: ['images', 'type', 'imgclass'],
            computed: {
                imageTableVisable: function () {
                    return this.images && this.images.length > 0
                }
            },
            components: {
                "imageBox": _imageBox.component
            }
        })

        return {
            component: _imageTableComponent,
            imageBox: _imageBox
        }
    }()

    var _imageUpload = function () {

        var imageBox = function () {
            var component = Vue.extend({
                template: '\
                <ul class="el-upload-list el-upload-list--picture-card">\
                <li class="el-upload-list__item is-success">\
                <img :src="item.url" alt="" :class="imgclass">\
                <input type="hidden" v-model="item.key">\
                <span class="el-upload-list__item-actions">\
                <span class="el-upload-list__item-preview"><i class="el-icon-view" @click="overview"></i></span>\
                <span class="el-upload-list__item-delete"><i class="el-icon-delete2" @click="remove"></i></span></span>\
                </li>\
                </ul>\
                ',
                props: ['item', 'type'],
                computed: {
                    imgclass: function () {
                        return 'el-upload-list__item-thumbnail' + ' ' + this.type;
                    }
                },
                methods: {
                    overview: function () {
                        window.open(this.item.url);
                    },
                    remove: function () {
                        this.$emit('remove');
                    }
                }
            })

            return {component: component,}
        }()

        var component = Vue.extend({

            template: '\
            <div>\
            <image-box v-for="(item,index) in imagesComputed" :item="item" :type="type" @remove="remove(index)"></image-box>\
            <div class="el-upload el-upload--picture-card image-upload-model" @click="addImage" v-if="imagesComputed.length < max">\
            <i class="el-icon-plus"></i>\
            </div>\
            <el-upload\
            class="upload-image" style="display:none;"\
            :action="uploadurl"\
            :file-list="imagesComputed" :on-success="uploadSuccess" :on-error="uploadFail">\
            <el-button size="small" type="primary" class="uploadBtn"></el-button>\
            </el-upload>\
            </div>\
            ',
            props: ['images', 'uploadurl', 'type', 'max'],
            data: function () {
                return {}
            },
            computed: {
                imagesComputed: function () {
                    console.log(this.images[0]);
                    var imagesComputed = [];
                    if (!this.images) {
                        return imagesComputed;
                    }
                    if (this.images instanceof Array) {
                        return this.images;
                    }
                    imagesComputed.push(images);
                    return imagesComputed;
                }
            },
            mounted: function () {
            },
            updated: function () {
            },
            methods: {
                addImage: function () {
                    $(this.$el).find(".uploadBtn").trigger('click');
                },
                remove: function (index) {
                    this.imagesComputed.splice(index, 1);
                },
                uploadSuccess: function (response) {
                    if (this.imagesComputed.length >= this.max) {
                        return false;
                    }
                    var _this = this;
                    if (!response) {
                        _this.$message({
                            showClose: true,
                            message: '服务器异常',
                            type: 'error'
                        });
                        return false;
                    }
                    if (response.msg) {
                        _this.$message({
                            showClose: true,
                            message: response.msg,
                            type: 'error'
                        });
                        return false;
                    }
                    _this.imagesComputed.push(response);
                },
                uploadFail: function (err) {
                    this.$message({
                        showClose: true,
                        message: '服务器异常',
                        type: 'error'
                    });
                    return false;
                }
            },
            components: {
                "image-box": imageBox.component
            }
        })

        return {
            component: component,
            imageBox: imageBox
        }
    }()
    var _imageTablePreview = function () {
        var _imageTablePreviewComponent = Vue.extend({
            template: '\
            <div>\
            <table class="pure-table pure-table-bordered" v-if="imageTableVisable">\
            <colgroup>\
            <col width="10%">\
            </colgroup>\
            <tbody>\
            <tr>\
            <td>\
            <div class="img-header">\
            <span>{{type}}</span>\
            </div>\
            </td></tr><tr><td>\
            <imageBox v-for="src in images" :src="src" :type="type" :imgclass="imgclass"></imageBox>\
            </td></tr></tbody></table>\
            <div class="wrapper">\
            <div :id="viewerId" style="display: none;"></div>\
            </div>\
            </div>\
            '
            ,
            props: ['images', 'type', 'imgclass'],
            computed: {
                imageTableVisable: function () {
                    return this.images && this.images.length > 0
                },
                viewerId: function () {
                    return this.imgclass + 'ViewerContainer';
                }
            },
            mounted: function () {
                $('.' + this.imgclass).PreImageBox({container: '#' + this.viewerId});
            },
            updated: function () {
                $('.' + this.imgclass).PreImageBox({container: '#' + this.viewerId});
            },
            components: {
                "imageBox": _imageTable.imageBox.component
            }
        });
        return {
            component: _imageTablePreviewComponent
        }
    }();
    return {
        //图片表格
        imageTable: _imageTable,
        //图片上传 可预览
        imageUpload: _imageUpload,
        //图片表格 可预览
        imageTablePreview: _imageTablePreview
    }
}()