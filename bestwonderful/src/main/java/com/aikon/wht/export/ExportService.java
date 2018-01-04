package com.aikon.wht.export;

import javax.servlet.http.HttpServletResponse;

/**
 * 导出服务.
 *
 * @author haitao.wang
 */
public interface ExportService {

    <T> void export(HttpServletResponse httpServletResponse,T t,String fileName);
}
