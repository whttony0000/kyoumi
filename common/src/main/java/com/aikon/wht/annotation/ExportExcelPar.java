package com.aikon.wht.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExportExcelPar {
	/**
	 * 是否导出 默认 true, 设置false 该字段 不导出
	 */
	boolean ifExport() default true;
	/**
	 * 前缀
	 */
	String prefix() default  "";
	/**
	 * 后缀
	 */
	String postfix() default "";
	
	//表头
	String title() default "";
}
