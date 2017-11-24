package com.aikon.wht.event;

import com.aikon.wht.model.ArticleCommentedModel;

/**
 * @author haitao.wang
 */
public class ArticleCommentedEvent extends AbstractNoticeEvent<ArticleCommentedModel> {
    public ArticleCommentedEvent(ArticleCommentedModel articleCommentedModel) {
        super(articleCommentedModel);
    }
}
