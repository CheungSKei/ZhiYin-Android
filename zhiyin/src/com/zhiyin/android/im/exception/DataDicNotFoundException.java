package com.zhiyin.android.im.exception;

/**
 * 数据字典未找到异常.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class DataDicNotFoundException extends Exception {

	private static final long serialVersionUID = -9143830886160609702L;

	public DataDicNotFoundException() {
		super();
	}

	public DataDicNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataDicNotFoundException(String message) {
		super(message);
	}

	public DataDicNotFoundException(Throwable cause) {
		super(cause);
	}

}
