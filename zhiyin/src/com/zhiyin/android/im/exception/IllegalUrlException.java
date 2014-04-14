package com.zhiyin.android.im.exception;

/**
 * URL格式不正确异常.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class IllegalUrlException extends Exception {

	private static final long serialVersionUID = -7690450132057759857L;

	public IllegalUrlException() {
		super();
	}

	public IllegalUrlException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public IllegalUrlException(String detailMessage) {
		super(detailMessage);
	}

	public IllegalUrlException(Throwable throwable) {
		super(throwable);
	}

}
