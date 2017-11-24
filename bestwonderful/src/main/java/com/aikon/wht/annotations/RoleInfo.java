package com.aikon.wht.annotations;

import java.lang.annotation.*;

/**
 * @author haitao.wang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleInfo {
    String value() default "";
}
