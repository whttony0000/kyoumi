package com.aikon.wht.annotations;

import java.lang.annotation.*;

/**
 * 用于取当前登录者信息的标识.
 *
 * @author haitao.wang
 */
@Target(ElementType.PARAMETER)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface IndividualInfo {
    String value() default "";
}
