package com.aikon.wht.service;

import java.io.InputStream;

/**
 * 文件上传service.
 *
 * @author haitao.wang
 */
public interface UploadFileService {

    String upload(InputStream in);

}
