package com.aikon.wht.export;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ExportPage<T> {

    private long total; //总数
    private int page; //当前页，分页从1开始
    private int pageSize; //每页条数
    private int allPage; //总页数
    private boolean isEnd; //是否是最后一页
    private List<T> data; //返回的数据封装


    public ExportPage(int page, int pageSize, long total ) {
        if(pageSize == 0 ){
            pageSize =1;
        }
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.allPage = (int) ((total + pageSize - 1) / pageSize);
        this.isEnd = page >= allPage;
    }

    public ExportPage(long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public static ExportPage empty(){
        ExportPage page = new ExportPage(0, 0, 0);
        return page;
    }


}
