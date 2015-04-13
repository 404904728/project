package org.cq.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author hejian 2014-11-20
 */
// ①声明注解的保留期限
@Retention(RetentionPolicy.RUNTIME)
// 定义注解的目标类型（方法，类等）
@Target(ElementType.TYPE)
public @interface Pojo {// 定义注解名称

	String name() default "";
	
	boolean table() default false;

}
