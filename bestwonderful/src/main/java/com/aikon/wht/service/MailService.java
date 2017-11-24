package com.aikon.wht.service;

import com.aikon.wht.entity.IndividualLog;
import com.aikon.wht.model.Response;
import com.aikon.wht.utils.IdEncrypter;
import com.aikon.wht.utils.MailUtil;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class MailService {

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    MailUtil mailUtil;

    @Value("${domain}")
    String domain;

    @Value("${port}")
    String port;

    @Autowired
    IndividualLogService individualLogService;


    private static final String TIME_CODE_KEY = "timeCode";

    public void sendActiveMail(String mailAddr, String name, String mailMd5Hash, String url) {
        String mailTimeCode = IdEncrypter.encodeId(System.currentTimeMillis());
        String subject = "账号激活--BestWonderful";
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>BestWonderful</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"\">\n" +
                "    <div style=\"margin: 50px auto;padding: 20px;height: 500px;width: 500px;box-shadow: 0 0 8px 0 rgba(0,0,0,.16);\">\n" +
                "    <h3>亲爱的%s 您好：</h3>\n" +
                "        <div>请在 24 小时内点击此链接(或复制该链接粘贴到浏览器地址栏)以完成激活...</div>\n" +
                "        <a href=\"%s\">%s</a>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        String activeLink = "http://" + domain + ":" + port + "/mailActive/" + mailMd5Hash + "/" + mailTimeCode + "?link=" + url;
        String content = String.format(html, name, activeLink, activeLink);
        this.sendMail(mailAddr, subject, content);
    }

    public boolean checkCanMailSend(Integer id) {
        IndividualLog individualLog = individualLogService.getLastSendMailLog(id);
        if (individualLog == null) {
            return true;
        }
        return new DateTime(individualLog.getCreateTime()).plus(2 * 60 * 1000).isBeforeNow();
    }

    public void sendArticleCreateMail(String mailAddr, String fanName, String articleTitle, String individualName, String articleLink) {
        String subject = String.format("您关注的%s有新的文章--BestWonderful", individualName);
        StringBuilder content = new StringBuilder(300);
        content.append("<!DOCTYPE html>\n");
        content.append("<html lang=\"en\">\n");
        content.append("<head>\n");
        content.append("    <meta charset=\"UTF-8\">\n");
        content.append("    <title>BestWonderful</title>\n");
        content.append("</head>\n");
        content.append("<body>\n");
        content.append("<div style=\"\">\n");
        content.append("    <div style=\"margin: 50px auto;padding: 20px;height: 500px;width: 500px;box-shadow: 0 0 8px 0 rgba(0,0,0,.16);\">\n");
        content.append("    <h3>亲爱的");
        content.append(fanName);
        content.append("您好：</h3>\n");
        content.append("        <div>您关注的");
        content.append(individualName);
        content.append("发布了新文章：</div>\n");
        content.append("        <a href=\"");
        content.append(articleLink);
        content.append("\">");
        content.append(articleTitle);
        content.append("</a>\n");
        content.append("<div>请点击前往查看...或复制下列链接粘贴到浏览器地址栏前往查看</div>");
        content.append("<div><a href=\"");
        content.append(articleLink);
        content.append("\">");
        content.append(articleLink);
        content.append("</a></div>");
        content.append("    </div>\n");
        content.append("</div>\n");
        content.append("</body>\n");
        content.append("</html>");
        this.sendMail(mailAddr, subject, content.toString());
    }

    private void sendMail(String mailAddr, String subject, String content) {
        threadPoolTaskExecutor.execute(() -> {
            try {
                mailUtil.sendMail(mailAddr, subject, content, null, null);
            } catch (MessagingException e) {
                log.error("Error Sending Active Mail To {}", mailAddr);
            } finally {
            }
        });
    }
}
