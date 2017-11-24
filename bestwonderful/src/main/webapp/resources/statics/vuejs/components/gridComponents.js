/**
 * 用途：基于vuejs及element的Grid组件.
 * 使用方法：
 *   准备：引入gridComponents.js
 *   实装：组件命名，用于外部调用
 *              ref='myRef'
 *         绑定数据源url
 *              :url='myUrl'
 *         绑定数据源过滤器
 *              :filter='myFilter'
 *                  myFilter为自定义function 入参：数据源返回数组 返回值：过滤后数组
 *         绑定列元素
 *              :columns='myColumns'
 *                  myColumns为列元素对象数组
 *                      列元素属性：prop 元素名
 *                                 label 列名
 *                                 width 列宽
 *                                 filter 当前列过滤器function 入参：当前列当前行元素 返回值：过滤后当前列当前行
 *                                 type 列元素类型 默认textColComponent
 *                                 links 链接对象数组
 *                                  链接对象属性 text 链接名
 *                                              action 链接function 入参当前行对象 返回值：无
 *  调用：父组件vue对象.$refs.myRef.loadData(param,currentPage)
 *          param 加载数据的请求参数
 *          currentPage 指定开始页
 *  关于数据源：request
 *              类型 post
 *              分页 currentPage pageSize
 *             response
 *              总数 total
 *              数据列表 rows
 *  扩展：需要不同的列元素可在columnItem中新增 并修改typeColumn属性 例：linkColComponent
 *
 * @author haitao.wang
 * @type {{gridList}}
 */
var GridComponent = function () {

    var _gridList = function () {
        var _columnItem = function () {
            var linkSpecifyTextComponent = Vue.extend({
                template : "\
               <el-button\
                    @click.native.prevent='link.action(row)'\
                    type='text'\
                    size='small'>\
                    {{row.text}}\
                </el-button>\
               ",
                props : ['link','row']
            });

            var linkComponent = Vue.extend({
               template : "\
               <el-button\
                    @click.native.prevent='link.action(row)'\
                    type='text'\
                    size='small'>\
                    {{link.text}}\
                </el-button>\
               ",
                props : ['link','row']
            });

            var textColComponent = Vue.extend({
                template : "\
                <el-table-column :prop='column.prop' :label='column.label' :width='column.width' :fixed='column.fixed'>\
                </el-table-column>\
                ",
                props : ['column']
            });

            var linkSpecifyTextColComponent = Vue.extend({
                template : "\
                <el-table-column :prop='column.prop' :label='column.label' :width='column.width' :fixed='column.fixed'>\
                        <template scope='scope'>\
                        <link-specify-text-component v-for='link in column.links' :link='link' :row='scope.row'></link-specify-text-component>\
                        </el-button>\
                        </template>\
                </el-table-column>\
                ",
                props : ['column'],
                components : {
                    'link-specify-text-component' : linkSpecifyTextComponent
                }
            });

            var linkColComponent = Vue.extend({
                template : "\
                <el-table-column :prop='column.prop' :label='column.label' :width='column.width' :fixed='column.fixed'>\
                        <template scope='scope'>\
                        <link-component v-for='link in column.links' :link='link' :row='scope.row'></link-component>\
                        </el-button>\
                        </template>\
                </el-table-column>\
                ",
                props : ['column'],
                components : {
                    'link-component' : linkComponent
                }
            });

            var _component = Vue.extend(
                {
                    template: "\
                    <div>\
                    <component :is='typeColumn' :column='column'></component>\
                    </div>\
                    ",
                    data: function () {
                        return {
                            typeColumn : 'text-column'
                        }
                    },
                    created: function () {
                        if (this.column.type && this.column.type == 'link') {
                            this.typeColumn = "link-column";
                        }
                        if (this.column.type && this.column.type == 'linkSpecify') {
                            this.typeColumn = "link-specify-text-column";
                        }
                    },
                    updated: function () {
                    },
                    computed: {},
                    props: ['column'],
                    components : {
                        "text-column" : textColComponent,
                        "link-column" : linkColComponent,
                        "link-specify-text-column" : linkSpecifyTextColComponent
                    }

                }
            )
            return {
                component: _component
            }
        }()
            // <col-item v-for='grid in grids' :grid='grid'></col-item>
        var _component = Vue.extend(
            {
                template: "\
                <div ref='grid-list'>\
                <el-table :data='grids' :empty-text='et' border>\
                    <col-item v-for='column in cols' :column='column'></col-item>\
                </el-table>\
                <br>\
                    <el-pagination\
                          @size-change='handleSizeChange'\
                          @current-change='handleCurrentChange'\
                          :current-page='currentPage'\
                          :page-sizes='pageSizes'\
                          :page-size='pageSize'\
                          layout='total, sizes, prev, pager, next, jumper'\
                          :total='total'>\
                    </el-pagination>\
                </div>\
                ",
                data: function () {
                    return {
                        total: 0,
                        currentPage: 1,
                        pageSize: 10,
                        grids: [],
                        param: {},
                        pageSizes: [],
                        et : '暂无数据'
                    }
                },
                props: ['columns','url','filter','emptytext'],
                created: function () {
                    var newPageSizes = [];
                    newPageSizes.push(this.pageSize, 2 * this.pageSize, 3 * this.pageSize, 4 * this.pageSize);
                    this.pageSizes = newPageSizes;
                    if (this.emptytext) {
                        this.et = this.emptytext;
                    }
                },
                computed: {
                    cols:function () {
                        this.columns.unshift(this.columns.pop());
                        return this.columns;
                    }
                },
                updated: function () {
                },
                methods: {
                    handleSizeChange: function (val) {
                        this.pageSize = val;
                        this.loadData(this.param);
                    },
                    handleCurrentChange(val) {
                        this.currentPage = val;
                        this.loadData(this.param);
                    },
                    loadData: function (param,currentPage) {
                        var _this = this;
                        _this.param = param;
                        if (currentPage) {
                            _this.currentPage = currentPage;
                        }
                        param.currentPage = _this.currentPage;
                        param.pageSize = _this.pageSize;
                        $.ajax({
                            type: 'post',
                            data: param,
                            url: _this.url,
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
                                _this.grids = response.data.rows;
                                if (!_this.grids) {
                                    return false;
                                }
                                if (!Array.isArray(_this.grids)) {
                                    _this.grids = [_this.grids];
                                }
                                if (_this.filter) {
                                    _this.grids = _this.filter(_this.grids);
                                }
                                _this.grids = _this.rowFilter(_this.grids);
                                _this.total = response.data.total;
                            }
                        });
                    },
                    rowFilter : function (param) {
                        if (!param) {
                            return param;
                        }
                        if (!Array.isArray(param)) {
                            param = [param];
                        }
                        var _this = this;
                        param.forEach(item => {
                            _this.columns.forEach( (column)=>{
                                if (column.filter && column.prop) {
                                    item[column.prop] = column.filter(item[column.prop]);
                                }
                            })
                        });
                        return param;
                    }
                },
                filters : {
                },
                components: {
                    "col-item": _columnItem.component
                }

            }
        )
        return {
            component: _component,
            columnItem: _columnItem
        }
    }()

    return {
        gridList: _gridList
    }
}()