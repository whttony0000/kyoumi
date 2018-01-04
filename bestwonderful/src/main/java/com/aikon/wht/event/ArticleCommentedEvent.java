package com.aikon.wht.event;

import com.aikon.wht.model.ArticleCommentedModel;

/**
 * 文章评论事件.
 *
 * @author haitao.wang
 */
public class ArticleCommentedEvent extends AbstractNoticeEvent<ArticleCommentedModel> {

    /**
     * 构造器.
     *
     * @param articleCommentedModel
     */
    public ArticleCommentedEvent(ArticleCommentedModel articleCommentedModel) {
        super(articleCommentedModel);
    }
}
