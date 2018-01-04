package com.aikon.wht.event;

import com.aikon.wht.model.ArticleCreateEventModel;

/**
 * 文章创建事件.
 *
 * @author haitao.wang
 */
public class ArticleCreateEvent extends AbstractNoticeEvent<ArticleCreateEventModel> {

    /**
     * 构造器.
     *
     * @param eventModel
     */
    public ArticleCreateEvent(ArticleCreateEventModel eventModel) {
        super(eventModel);
    }
}
