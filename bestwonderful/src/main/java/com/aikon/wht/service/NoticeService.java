package com.aikon.wht.service;

import com.aikon.wht.dao.extend.NoticeExtendMapper;
import com.aikon.wht.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author haitao.wang
 */
@Service
public class NoticeService {

    @Autowired
    NoticeExtendMapper noticeExtendMapper;

    public void saveNotice(Notice notice) {
        noticeExtendMapper.insertSelective(notice);
    }
}
