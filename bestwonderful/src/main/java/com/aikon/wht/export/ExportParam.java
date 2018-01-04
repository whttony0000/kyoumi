package com.aikon.wht.export;

import lombok.Data;

/**
 * 导出参数.
 *
 */
@Data
public class ExportParam {
    protected Integer currentPage = 0;
    protected Integer maxRows = 30;

}
