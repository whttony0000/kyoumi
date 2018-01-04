package com.aikon.wht.service;

import com.aikon.wht.dao.extend.NoticeExtendMapper;
import com.aikon.wht.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通知service.
 *
 * @author haitao.wang
 */
@Service
public class NoticeService {

    @Autowired
    NoticeExtendMapper noticeExtendMapper;

    /**
     * 保存通知.
     *
     * @param notice
     */
    public void saveNotice(Notice notice) {
        noticeExtendMapper.insertSelective(notice);
    }
}
