package com.aikon.wht.serviceImpl;

import com.aikon.wht.enums.ImageFormatQiniuEnum;
import com.aikon.wht.model.UploadFileModel;
import com.aikon.wht.service.UploadImageService;
import com.aikon.wht.service.CommonService;
import com.aikon.wht.utils.IoUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author haitao.wang
 */
@Slf4j
@Service
public class UploadImageQiniuServiceImpl implements UploadImageService {

    @Value("${ossAK}")
    String ossAK;

    @Value("${ossSK}")
    String ossSK;

    @Value("${bucket}")
    String bucket;

    @Autowired
    CommonService commonService;

    @Override
    public String upload(InputStream in) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String key = null;
        byte[] uploadBytes = IoUtil.inputStream2Bytes(in);
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
        Auth auth = Auth.create(ossAK, ossSK);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            if (putRet == null) {
                log.error("Error uploading file:{}", "result null");
                return null;
            }
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("Error uploading file:{}", r);
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                log.error("Error uploading file:{}", ex2);
                return null;
            }
        }
        return null;
    }

    @Override
    public UploadFileModel upload(MultipartFile file, ImageFormatQiniuEnum imageFormatQiniuEnum) {
        UploadFileModel uploadFileModel = new UploadFileModel();
        String key = null;
        try {
            key = this.upload(file.getInputStream());
        } catch (IOException e) {
            log.error("Error uploading file:{}", e);
            return null;
        }
        if (key == null) {
            return null;
        }
        uploadFileModel.setKey(key);
        uploadFileModel.setUrl(commonService.getImageUrlByKey(key,imageFormatQiniuEnum));
        uploadFileModel.setName(file.getOriginalFilename());
        return uploadFileModel;
    }

    @Override
    public String checkFileTypeAndSize(MultipartFile file, List<String> allowedTypes, int maxSize) {
        if (file == null) {
            return "上传文件不能为空,请重新上传";
        }
        String fileName = file.getOriginalFilename();
        if (file.getSize() > (1024L * 1024 * maxSize)) {
            return "请重新上传 单个文件最大" + String.valueOf(maxSize) + "M";
        }
        Boolean isAllowedType = false;
        String typeErrMsg = "请选择文件(";
        for (String allowedType : allowedTypes) {
            if (fileName.endsWith(allowedType)) {
                isAllowedType = true;
            }
            typeErrMsg = typeErrMsg + " " + allowedType;
        }
        if (!isAllowedType) {
            return typeErrMsg + " )";
        }
        return "";
    }
}
