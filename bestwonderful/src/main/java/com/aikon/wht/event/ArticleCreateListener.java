package com.aikon.wht.event;

import com.aikon.wht.entity.Individual;
import com.aikon.wht.entity.Notice;
import com.aikon.wht.enums.NoticeTypeEnum;
import com.aikon.wht.enums.StatusEnum;
import com.aikon.wht.model.ArticleCreateEventModel;
import com.aikon.wht.service.ArticleService;
import com.aikon.wht.service.IndividualService;
import com.aikon.wht.service.MailService;
import com.aikon.wht.service.NoticeService;
import com.aikon.wht.utils.IdEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 文章创建事件监听器.
 *
 * @author haitao.wang
 */
public class ArticleCreateListener extends AbstractNoticeListener<ArticleCreateEvent> implements ApplicationListener<ArticleCreateEvent> {

    @Autowired
    ArticleService articleService;

    @Autowired
    IndividualService individualService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    MailService mailService;

    @Value("${domain}")
    String domain;

    @Value("${port}")
    String port;

    /**
     * 事件操作.
     *
     * @param articleCreateEvent
     */
    @Override
    public void onApplicationEvent(ArticleCreateEvent articleCreateEvent) {
        super.flow(articleCreateEvent);
    }

    /**
     * 事件操作.
     *
     * @param event
     */
    @Override
    public void execute(ArticleCreateEvent event) {
        ArticleCreateEventModel eventModel = (ArticleCreateEventModel) event.getSource();
        List<Individual> fanDetails = individualService.getFanDetails(eventModel.getIndividualId());
        Individual individual = individualService.getIndividualById(eventModel.getIndividualId());
        String articleLink = "http://" + domain + ":" + port + "/articleDetail?articleEid=" + IdEncrypter.encodeId(eventModel.getArticleId());
        if (CollectionUtils.isEmpty(fanDetails) || individual == null) {
            return;
        }
        // 生成通知入库并且邮件通知关注者.
        fanDetails.stream().forEach((fanDetail) -> {
            Notice notice = new Notice();
            notice.setStatus(StatusEnum.VALID.getCode());
            notice.setIndividualId(eventModel.getIndividualId());
            notice.setInvolvedId(fanDetail.getId());
            notice.setType(NoticeTypeEnum.CREATE_ARTICLE.getCode());
            notice.setObjectId(eventModel.getArticleId());
            noticeService.saveNotice(notice);
            mailService.sendArticleCreateMail(fanDetail.getMail(), fanDetail.getName(), eventModel.getArticleTitle(), individual.getName(), articleLink);
        });


    }
}
