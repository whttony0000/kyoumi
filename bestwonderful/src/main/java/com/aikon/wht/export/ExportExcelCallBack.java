package com.aikon.wht.export;

import java.lang.reflect.ParameterizedType;

/**
 * @author haitao.wang
 *
 * <p>导出excel回调.</p>
 *
 */
public interface ExportExcelCallBack<S,T extends ExportParam> {

    /**
     * 分页数据(allPage data必需).
     *
     * @param t
     * @return Page
     */
    ExportPage<S> getPage(T t);

    /**
     * 表头.
     *
     * @return String[]
     */
    String[] getColumnNameArr();

    /**
     * 文件名.
     *
     * @return String
     */
    String getFileName();

    /**
     * 当前导出类型.
     *
     * @return Class<S>
     * @see org.springframework.core.GenericTypeResolver#resolveTypeArguments(java.lang.Class, java.lang.Class)
     */
    default Class<S> getExportModelType() {
        // 实现类class -> interfaceType -> genericArgType -> genericArgClass
        // spring core：GenericTypeResolver.resolveTypeArguments(this.getClass(), ExportExcelCallBack.class)[0]
        return (Class<S>) (((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0]);
    }

    default Class<T> getRequestClass() {
        return (Class<T>) (((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1]);
    }

}
