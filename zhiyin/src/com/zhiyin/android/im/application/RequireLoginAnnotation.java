package com.zhiyin.android.im.application;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注某Activity是否需要登录.
 * 
 * @version 1.0.2
 * @date 2012-12-27
 * @author DengZhaoyong
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLoginAnnotation {
	
	/**
	 * 是否需要登录, 默认为需要登录.
	 * @return
	 */
	boolean require() default true;
	
	/**
	 * 当require()返回true时执行的操作类型, 默认为只结束当前Activity.
	 * @return
	 */
	OperateType operateType() default OperateType.FINISH;

	/**
	 * 是否需要登录数据类.
	 */
	public static class RequireLogin {
		/**
		 * 是否需要登录
		 */
		public boolean require;
		/**
		 * 当require()返回true时执行的操作类型
		 */
		public OperateType operateType;
		
		public RequireLogin(boolean require, OperateType operateType) {
			this.require = require;
			this.operateType = operateType;
		}
	}
	
	public static enum OperateType {
		/**
		 * 结束当前Activity
		 */
		FINISH,
		/**
		 * 跳转到登录页, 并结束当前Activity
		 */
		LOGIN_AND_FINISH,
		/**
		 * 正常处理
		 */
		NORMAL
	}
	
}
