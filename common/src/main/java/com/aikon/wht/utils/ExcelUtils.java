package com.aikon.wht.utils;

import com.aikon.wht.annotation.ExportExcelPar;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author haitao.wang
 */
public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static <T> void export(HttpServletResponse response, String[] arr, List<T> list, Class<T> clzz, String preFileName) throws IllegalArgumentException, IllegalAccessException {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        if (clzz == null) {
            return;
        }
        Field[] fields = clzz.getDeclaredFields();
        if (fields.length == 0) {
            return;
        }

        Workbook wb = new XSSFWorkbook();
        CreationHelper creationHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet();

        wb.setSheetName(0, "default");
        Row firstRow = sheet.createRow(0);
        Font fontHeader = wb.createFont();
        CellStyle cellStyleHeader = wb.createCellStyle();
        fontHeader.setBold(true);
        cellStyleHeader.setFont(fontHeader);
        for (int i = 0; i < arr.length; i++) {
            sheet.setColumnWidth(i, 18 * 256);
            Cell cell = firstRow.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(arr[i]);
            cell.setCellStyle(cellStyleHeader);
        }

        int columnIndex = 0;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if ("serialVersionUID".equalsIgnoreCase(field.getName())) {
                continue;
            }
            ExportExcelPar notpar = field.getAnnotation(ExportExcelPar.class);
            if (notpar != null && !notpar.ifExport()) {
                continue;
            }
            field.setAccessible(true);

            for (int j = 0; j < list.size(); j++) {
                StringBuffer sb = new StringBuffer();
                Object obj = "";
                try {
                    obj = field.get(list.get(j));
                } catch (IllegalAccessException e) {
                    logger.info("Failed Getting Field {}", field);
                }
                if (notpar != null && !"".equals(notpar.prefix())) {
                    sb.append(notpar.prefix());
                }
                sb.append(obj == null ? "" : obj.toString());
                if (notpar != null && !"".equals(notpar.postfix())) {
                    sb.append(notpar.postfix());
                }
                Row row;
                if (columnIndex == 0) {
                    row = sheet.createRow(j + 1);
                } else {
                    row = sheet.getRow(j + 1);
                }
                Cell cell = row.createCell(columnIndex);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(sb.toString());
            }
            columnIndex++;

        }

        String fileName;
        if (StringUtils.isNotEmpty(preFileName)) {
            fileName = preFileName.trim();
        } else {
            fileName = "" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        }
        OutputStream os = null;
        try {
            response.setContentType("application\\x-msdownloadoctet-stream;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xlsx");
            // 写入到 客户端response
            os = response.getOutputStream();
            wb.write(os);

        } catch (FileNotFoundException e) {
            logger.error("responseExcel error{}", e);
        } catch (IOException e) {
            logger.error("responseExcel error{}", e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }
}
