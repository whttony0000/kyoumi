package com.aikon.wht.annotations;

import java.lang.annotation.*;

/**
 * @author haitao.wang
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidInput {
    String value() default "";
}
