package com.aikon.wht.exception;

import com.aikon.wht.entity.Individual;
import com.aikon.wht.model.Response;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 事件处理类.
 *
 * @author haitao.wang
 */
@ControllerAdvice
public class MyExceptionHandler {
    private static final Logger logger = Logger.getLogger(MyExceptionHandler.class);

    @ExceptionHandler
    public
    @ResponseBody
    Response exceptionHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error("server exception:", ex);
        return new Response(-1, "服务器异常");
    }


    @ExceptionHandler
    public
    @ResponseBody
    Response exceptionHandler(HttpMediaTypeNotSupportedException ex) {
        logger.error("server exception:", ex);
        return new Response(-1, "参数转换错误");
    }

    @ExceptionHandler({NoPermissionException.class})
    public void exceptionHandler(NoPermissionException ex, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("/loginPage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler({InactiveException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, InactiveException e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("mail", ((Individual)e.getParam()).getMail());
        mv.setViewName("inactive");
        return mv;
    }

    @ExceptionHandler({InvalidInputException.class})
    @ResponseBody
    public Response invalidInputExceptionHandler(InvalidInputException ex, HttpServletRequest request, HttpServletResponse response) {
        return new Response(-1, ex.getMessage());
    }


}