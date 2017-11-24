package com.aikon.wht.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author haitao.wang
 *
 */
@Slf4j
public class DownloadUtil {

	public void downloadFile(String url, String fileName, HttpServletResponse response) {
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(fileName)) {
            return;
        }
		InputStream in = null;
		OutputStream out = null;
		try {
			HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url)).openConnection();
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
			response.addHeader("Content-Length", "" + httpURLConnection.getContentLength());
			response.setContentType("application/octet-stream");
			out = response.getOutputStream();
			byte[] bytes = new byte[2048];
			int length;
			in = httpURLConnection.getInputStream();
			while ((length = in.read(bytes)) > 0) {
				out.write(bytes, 0, length);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log.warn("Error occured when downloading file", e);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}

	}


}
