package com.aikon.wht.export;

import com.aikon.wht.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.property.PropertyCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 默认导出实现.
 *
 * @author haitao.wang
 */
@Slf4j
public class ExportServiceImpl implements ExportService {

    private static final int MAX_ROWS = 500;

    @Autowired
    ThreadPoolTaskExecutor simpleTaskExecutor;

    @Override
    public <T> void export(HttpServletResponse httpServletResponse, T t, String fileName) {

    }

    /**
     * 导出excel.
     *
     * @param servletResponse
     * @param request
     * @param exportExcelCallBack
     * @param <S>
     * @param <T>
     */
    public <S, T extends ExportParam> void exportExcel(HttpServletResponse servletResponse, T request, ExportExcelCallBack<S, T> exportExcelCallBack) {
        request.setCurrentPage(1);
        request.setMaxRows(MAX_ROWS);
        ExportPage<S> page = exportExcelCallBack.getPage(request);
        if (page == null) {
            return;
        }
        final Map<Integer, ExportPage> map = new ConcurrentHashMap<>();
        map.put(1, page);
        if (page.getAllPage() > 5) {
            return;
        }

        if (page.getAllPage() > 1) {
            final CountDownLatch countDownLatch = new CountDownLatch(page.getAllPage() - 1);
            for (int i = 2; i <= page.getAllPage(); i++) {
                final int currentPage = i;
                request.setCurrentPage(currentPage);
                Class<T> requestClass = exportExcelCallBack.getRequestClass();
                T finalRequest = null;
                try {
                    finalRequest = requestClass.newInstance();
                } catch (Exception e) {
                    log.info("Failed To Copy Request,{}", e);
                }
                if (finalRequest == null) {
                    log.info("Export Excel Request Null");
                    continue;
                }
                PropertyCopier.copyBeanProperties(requestClass, request, finalRequest);
                finalRequest.setCurrentPage(currentPage);
                T finalRequest1 = finalRequest;
                simpleTaskExecutor.submit(() -> {
                    map.put(currentPage, exportExcelCallBack.getPage(finalRequest1));
                    countDownLatch.countDown();
                });

            }
            try {
                countDownLatch.await(2, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                log.info("Error Exporting Excel:{}", e);
            }
        }

        String[] columnNameArr = exportExcelCallBack.getColumnNameArr();
        List<S> list = new ArrayList<>();
        for (int i = 1; i <= page.getAllPage(); i++) {
            list.addAll(map.get(i).getData());
        }
        try {
            ExcelUtils.export(servletResponse, columnNameArr, list, exportExcelCallBack.getExportModelType(), exportExcelCallBack.getFileName());
        } catch (IllegalAccessException e) {
            log.info("Error Exporting Excel:{}", e);
        }

    }
}
