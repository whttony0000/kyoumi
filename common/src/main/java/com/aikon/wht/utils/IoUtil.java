package com.aikon.wht.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @author haitao.wang
 */
public class IoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IoUtil.class);

    public static byte[] inputStream2Bytes(InputStream in) {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int count = -1;
        try {
            while ((count = in.read(data, 0, 4096)) != -1) {
                outStream.write(data, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }

    public static void alert(HttpServletResponse response, String msg) {
        responseCommon(response, "<script>alert('" + msg + "');</script>", "text/html", "UTF-8");
    }

    public static void responseCommon(HttpServletResponse response, Object obj, String contentType, String encoding) {
        response.setContentType(contentType);
        response.setCharacterEncoding(encoding);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            LOGGER.info("ERROR PRINTING TO PAGE");
        }
        out.print(obj);
        out.flush();
        out.close();

    }
}

