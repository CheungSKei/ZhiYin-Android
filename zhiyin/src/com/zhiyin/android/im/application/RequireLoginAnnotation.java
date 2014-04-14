package com.zhiyin.android.im.application;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��עĳActivity�Ƿ���Ҫ��¼.
 * 
 * @version 1.0.2
 * @date 2012-12-27
 * @author DengZhaoyong
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLoginAnnotation {
	
	/**
	 * �Ƿ���Ҫ��¼, Ĭ��Ϊ��Ҫ��¼.
	 * @return
	 */
	boolean require() default true;
	
	/**
	 * ��require()����trueʱִ�еĲ�������, Ĭ��Ϊֻ������ǰActivity.
	 * @return
	 */
	OperateType operateType() default OperateType.FINISH;

	/**
	 * �Ƿ���Ҫ��¼������.
	 */
	public static class RequireLogin {
		/**
		 * �Ƿ���Ҫ��¼
		 */
		public boolean require;
		/**
		 * ��require()����trueʱִ�еĲ�������
		 */
		public OperateType operateType;
		
		public RequireLogin(boolean require, OperateType operateType) {
			this.require = require;
			this.operateType = operateType;
		}
	}
	
	public static enum OperateType {
		/**
		 * ������ǰActivity
		 */
		FINISH,
		/**
		 * ��ת����¼ҳ, ��������ǰActivity
		 */
		LOGIN_AND_FINISH,
		/**
		 * ��������
		 */
		NORMAL
	}
	
}
