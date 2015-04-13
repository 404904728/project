package org.cq.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Administrator
 *
 */
// ①声明注解的保留期限
@Retention(RetentionPolicy.RUNTIME)
// 定义注解的目标类型（字段）
@Target(ElementType.FIELD)
public @interface Column {

	String name() default "";
	
	int len() default 1;
	
	boolean isNull() default true;

}
