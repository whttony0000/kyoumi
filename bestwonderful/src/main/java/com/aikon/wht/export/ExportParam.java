package com.aikon.wht.export;

import lombok.Data;

@Data
public class ExportParam {
    protected Integer currentPage = 0;
    protected Integer maxRows = 30;

}
