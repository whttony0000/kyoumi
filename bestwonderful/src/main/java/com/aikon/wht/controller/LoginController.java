package com.aikon.wht.controller;

import com.aikon.wht.annotations.IndividualInfo;
import com.aikon.wht.entity.Individual;
import com.aikon.wht.enums.IndividualLogTypeEnum;
import com.aikon.wht.enums.IndividualStatusEnum;
import com.aikon.wht.enums.RoleEnum;
import com.aikon.wht.enums.StatusEnum;
import com.aikon.wht.model.Response;
import com.aikon.wht.service.IndividualLogService;
import com.aikon.wht.service.IndividualService;
import com.aikon.wht.service.MailService;
import com.aikon.wht.utils.IdEncrypter;
import com.aikon.wht.utils.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

/**
 * 注册登录controller.
 *
 * @author haitao.wang
 */
@Controller
@Slf4j
public class LoginController {


    @Autowired
    private Producer producer;

    @Autowired
    SecurityManager securityManager;

    @Autowired
    IndividualService individualService;

    @Autowired
    MailService mailService;

    @Autowired
    IndividualLogService individualLogService;

    /**
     * 注册页.
     *
     * @param modelMap
     * @return String
     */
    @RequestMapping("signUpPage")
    String signUpPage(ModelMap modelMap) {
        modelMap.addAttribute("inner_page", "signUp.ftl");
        return "template";
    }

    /**
     * 登录页.
     *
     * @param modelMap
     * @return String
     */
    @RequestMapping("signInPage")
    String signInPage(ModelMap modelMap) {
        modelMap.addAttribute("inner_page", "signIn.ftl");
        return "template";
    }

    /**
     * 主页.
     *
     * @param modelMap
     * @param individual
     * @return String
     */
    @RequestMapping("/index")
    String index(ModelMap modelMap,@IndividualInfo Individual individual) {
        modelMap.addAttribute("inner_page", "index.ftl");
        modelMap.addAttribute("individualEid", IdEncrypter.encodeId(individual.getId()));
        modelMap.addAttribute("mailCode",individual.getMailMd5Hash());
        return "template";
    }

    /**
     * 主页.
     *
     * @param modelMap
     * @param individual
     * @return String
     */
    @RequestMapping("/")
    String toIndex(ModelMap modelMap,@IndividualInfo Individual individual) {
        return "redirect:/index";
    }

    /**
     * 获取验证码.
     *
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }


    /**
     * 注册登录页.
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/loginPage")
    public ModelAndView loginPage(){
        return new ModelAndView("login");
    }

    /**
     * 注册操作.
     *
     * @param email
     * @param password
     * @param checkPass
     * @param captcha
     * @param name
     * @return Response
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public Response signUp(String email, String password, String checkPass, String captcha,String name) {
        if (individualService.mailExist(email)) {
            return new Response(0, String.format("邮箱%s已被注册",email));
        }

        if (!password.equals(checkPass)) {
            return new Response(0, "两次输入密码不一致");
        }
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!captcha.equalsIgnoreCase(kaptcha)) {
            return new Response(0, "验证码不正确");
        }

        if (individualService.mailExist(email)) {
            return new Response(0, String.format("邮箱%s已被注册",email));
        }
        if (individualService.nameExist(name)) {
            return new Response(0, String.format("名称%s已被注册",name));
        }
        Individual individual = new Individual();
        individual.setMail(email);
        individual.setName(name);
        individual.setPasswd(new Sha256Hash(password).toHex());
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(email, Charsets.UTF_8);
        String mailMd5Hash = hasher.hash().toString();
        individual.setMailMd5Hash(mailMd5Hash);
        individual.setStatus(IndividualStatusEnum.VALID.getCode());
        individual.setRole(RoleEnum.TMP_USER.getCode());
        // 保存个人信息.
        Response<Individual> response = individualService.saveIndividual(individual);
        if (response.getStatus() != 1) {
            return response;
        }
        Individual individualFromDB = response.getData();
        // 发送验证邮件.
        mailService.sendActiveMail(email, name, mailMd5Hash, "/index");
        // 记录个人操作日志.
        individualLogService.createIndividualLog(individualFromDB.getId(),null, StatusEnum.VALID, IndividualLogTypeEnum.CREATED,individualFromDB.getId());
        return Response.SUCCESS;

    }

    /**
     * 登录操作.
     */
    @ResponseBody
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public Response signIn(String email, String password,String checkPass, String captcha) throws IOException {
        if (StringUtils.isEmpty(checkPass)) {
            String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
            if (!captcha.equalsIgnoreCase(kaptcha)) {
                return new Response(0, "验证码不正确");
            }
        }
        Subject subject = null;
        try {
            subject = ShiroUtils.getSubject();
            //sha256加密
            password = new Sha256Hash(password).toHex();
            UsernamePasswordToken token = new UsernamePasswordToken(email, password);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return new Response(0, e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return new Response(0, e.getMessage());
        } catch (LockedAccountException e) {
            return new Response(0, e.getMessage());
        } catch (AuthenticationException e) {
            return new Response(0, "账户验证失败");
        }
        return Response.SUCCESS;
    }

    /**
     * 激活跳转页.
     *
     * @param mailMd5Hash
     * @param timeCode
     * @param link
     * @param modelMap
     * @return String
     */
    @RequestMapping("/mailActive/{mailMd5Hash}/{timeCode}")
    public String activeAccountPage(@PathVariable String mailMd5Hash,@PathVariable String timeCode,String link, ModelMap modelMap) {
        Long minus = IdEncrypter.decodeId2Long(timeCode) - new DateTime().minus(24*60*60*1000).getMillis();
        if (minus < 0) {
            modelMap.addAttribute("error","该验证链接已失效，请登录后按照提示重新获取验证邮件...");
            return "activeJump";
        }
        Response<Individual> response = individualService.activeAccount(mailMd5Hash);
        if (response.getStatus() != 1) {
            modelMap.addAttribute("errorCode", response.getStatus());
            modelMap.addAttribute("error", response.getMessage());
            return "activeJump";
        }
        Subject subject = ShiroUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(response.getData().getMail(), response.getData().getPasswd());
        subject.login(token);
        modelMap.addAttribute("link", link);
        return "activeJump";
    }

    /**
     * 登出操作.
     *
     * @param modelMap
     * @return String
     */
    @RequestMapping("/logout")
    public String logout(ModelMap modelMap) {
        ShiroUtils.logout();
        return "login";
    }

    /**
     * 关于页.
     *
     * @param modelMap
     * @return String
     */
    @RequestMapping("/about")
    public String about(ModelMap modelMap) {
        modelMap.addAttribute("inner_page","about.ftl");
        return "template";
    }

    /**
     * 联系页.
     *
     * @param modelMap
     * @return String
     */
    @RequestMapping("/contact")
    public String contact(ModelMap modelMap) {
        modelMap.addAttribute("inner_page","contact.ftl");
        return "template";
    }

    /**
     * mock.
     *
     * @return String
     */
    @RequestMapping("/mock")
    public String mock() {
        Subject subject = ShiroUtils.getSubject();
        //sha256加密
        String password = new Sha256Hash("123").toHex();
        UsernamePasswordToken token = new UsernamePasswordToken("982980292@qq.com", password);
        subject.login(token);
        return "redirect:/index";
    }

}

