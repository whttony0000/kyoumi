package com.aikon.wht.service;

import com.aikon.wht.enums.ImageFormatQiniuEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author haitao.wang
 */
@Service
public class CommonService {

    @Value("${imageDomainQiniu}")
    String imageDomainQiniu;

    public String getImageUrlByKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return imageDomainQiniu + key;

    }

    public String getImageUrlByKey(String key, ImageFormatQiniuEnum ifqe) {
        if (getImageUrlByKey(key) == null) {
            return null;
        }
        return getImageUrlByKey(key) + ifqe.getSuffix();
    }
}
