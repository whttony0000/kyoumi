package com.aikon.wht.service;

import com.aikon.wht.enums.ImageFormatQiniuEnum;
import com.aikon.wht.model.UploadFileModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 图片上传service。
 *
 * @author haitao.wang
 */
public interface UploadImageService extends UploadFileService {

    /**
     * 校验图片类型和大小.
     *
     * @param file
     * @param allowedTypes
     * @param maxSize
     * @return String
     */
    String checkFileTypeAndSize(MultipartFile file, List<String> allowedTypes, int maxSize);

    /**
     * 上传.
     *
     * @param file
     * @param imageFormatQiniuEnum
     * @return UploadFileModel
     */
    UploadFileModel upload(MultipartFile file, ImageFormatQiniuEnum imageFormatQiniuEnum);
}
