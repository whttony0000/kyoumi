package com.aikon.wht.utils;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件工具类.
 *
 * @author haitao.wang
 */
@Component
public class MailUtil {

	private static Logger logger = LoggerFactory.getLogger(MailUtil.class);
	public static final String DEFAULT_CHARSET = "UTF-8";

	@Value("${sendMailFrom}")
	public String from;

	@Autowired
	private JavaMailSenderImpl senderImpl;

	@Autowired
	@Qualifier("mailRateLimiter")
	RateLimiter rateLimiter;


    /**
     * 发送邮件.
     *
     * @param sendTo
     * @param subject
     * @param content
     * @param attachName
     * @param file
     * @throws MessagingException
     */
	public void sendMail(String sendTo, String subject, String content, String attachName, File file) throws MessagingException {
	    // 限流.
		rateLimiter.acquire();
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, DEFAULT_CHARSET);
        // 设置收件人，寄件人
        messageHelper.setTo(sendTo);
        messageHelper.setFrom(from);
        messageHelper.setSubject("" + subject);
        // true 表示启动HTML格式的邮件
        messageHelper.setText("" + content, true);

        if (file != null) {
            if (file.exists() && file.isFile()) {
                if (file.length() > 1024 * 1024 * 32) {
                    logger.error("file size is too big {}", file.getAbsolutePath());
                } else {
                    messageHelper.addAttachment(attachName, file);
                }
            }
        }
        senderImpl.send(mailMessage);
        logger.info("Mail Success");
    }

}
