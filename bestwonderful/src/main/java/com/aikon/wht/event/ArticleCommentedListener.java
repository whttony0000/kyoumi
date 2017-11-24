package com.aikon.wht.event;

import org.springframework.context.ApplicationListener;

/**
 * @author haitao.wang
 */
public class ArticleCommentedListener extends AbstractNoticeListener<ArticleCommentedEvent> implements ApplicationListener<ArticleCommentedEvent> {
    @Override
    protected void execute(ArticleCommentedEvent event) {

    }

    @Override
    public void onApplicationEvent(ArticleCommentedEvent articleCommentedEvent) {
        super.flow(articleCommentedEvent);
    }
}
