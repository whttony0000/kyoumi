package com.aikon.wht.controller.task;

import com.aikon.wht.annotations.IndividualInfo;
import com.aikon.wht.entity.Individual;
import com.aikon.wht.enums.IndividualLogTypeEnum;
import com.aikon.wht.enums.StatusEnum;
import com.aikon.wht.model.Response;
import com.aikon.wht.service.IndividualLogService;
import com.aikon.wht.service.MailService;
import com.aikon.wht.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.File;

/**
 * @author haitao.wang
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    MailUtil mailUtil;

    @Autowired
    MailService mailService;

    @Autowired
    IndividualLogService individualLogService;

    private static final String LAST_SEND_TIME = "lastSendTime";


    @RequestMapping("/testMail")
    @ResponseBody
    @Scheduled(cron = "0 0 10 ? * *")
    public void testMail() {
        try {
            mailUtil.sendMail("982980292@qq.com", "subject", "<html><head></head><body><h1>Hi:</h1><br><h1> 请参见附件(系统邮件 无需回复)</h1><br><h1>Best Regards</h1></body></html>", "1.txt", new File("D:\\1.txt"));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/sendActiveMail")
    @ResponseBody
    public Response<Object> sendActiveMail(@IndividualInfo Individual individual,String url) {
        if (!mailService.checkCanMailSend(individual.getId())) {
            return new Response<>(0, "两分钟之内不能重复发送验证邮件，请您稍后再试...");
        }
        mailService.sendActiveMail(individual.getMail(),individual.getName(),individual.getMailMd5Hash(),url);
        individualLogService.createIndividualLog(individual.getId(),null, StatusEnum.VALID, IndividualLogTypeEnum.SEND_ACTIVE_MAIL,individual.getId());
        return Response.SUCCESS;
    }
}
