package org.cq.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author hejian 2013-12-2
 */
// ①声明注解的保留期限
@Retention(RetentionPolicy.RUNTIME)
// 定义注解的目标类型（方法，类等）
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ActionAnn {// 定义注解名称

	/** 请求类型 默认 GET POST都可以请求 */
	String method() default "";

	/** 是否需要登录 */
	boolean toLogon() default true;

	/** 权限验证 */
	long[] auth() default {};

	/** 该方法是否需要刷新权限 */
	boolean refPermission() default false;

	/** 装饰类型 整型 类型自己设定 默认0 */
	int dcrType() default DECORATION;

	/** 不需要主页装饰 */
	public final static int NO_DECORATION = 1;

	/** 需要主页装饰 */
	public final static int DECORATION = 0;
}
