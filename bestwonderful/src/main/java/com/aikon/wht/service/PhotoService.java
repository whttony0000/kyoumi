package com.aikon.wht.service;

import com.aikon.wht.dao.extend.PhotoExtendMapper;
import com.aikon.wht.entity.Photo;
import com.aikon.wht.entity.PhotoExample;
import com.aikon.wht.enums.PhotoTypeEnum;
import com.aikon.wht.enums.StatusEnum;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author haitao.wang
 */
@Service
public class PhotoService {

    @Autowired
    PhotoExtendMapper photoExtendMapper;

    public Map<Integer,String> getObjectIdPhotoKeyMap(List<Integer> objectIds, PhotoTypeEnum pte) {
        Map<Integer,String> objIdKeyMap = Maps.newHashMap();
        PhotoExample photoExample = new PhotoExample();
        photoExample.or().andObjectIdIn(objectIds).andTypeEqualTo(pte.getCode()).andStatusEqualTo(StatusEnum.VALID.getCode());
        List<Photo> photos =  photoExtendMapper.selectByExample(photoExample);
        if (CollectionUtils.isEmpty(photos)) {
            return objIdKeyMap;
        }
        return photos.stream().collect(Collectors.toMap(Photo::getObjectId, Photo::getImageKey));
    }
}
