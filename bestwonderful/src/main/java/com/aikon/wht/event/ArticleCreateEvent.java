package com.aikon.wht.event;

import com.aikon.wht.model.ArticleCreateEventModel;

/**
 * @author haitao.wang
 */
public class ArticleCreateEvent extends AbstractNoticeEvent<ArticleCreateEventModel> {

    public ArticleCreateEvent(ArticleCreateEventModel eventModel) {
        super(eventModel);
    }
}
