package com.aikon.wht.service;

import com.aikon.wht.enums.ImageFormatQiniuEnum;
import com.aikon.wht.model.UploadFileModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author haitao.wang
 */
public interface UploadImageService extends UploadFileService {
    String checkFileTypeAndSize(MultipartFile file, List<String> allowedTypes, int maxSize);

    UploadFileModel upload(MultipartFile file, ImageFormatQiniuEnum imageFormatQiniuEnum);
}
