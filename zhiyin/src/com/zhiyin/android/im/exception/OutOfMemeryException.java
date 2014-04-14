package com.zhiyin.android.im.exception;

/**
 * OOM.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class OutOfMemeryException extends Exception {

	private static final long serialVersionUID = 2436697075012815792L;

	public OutOfMemeryException() {
		super();
	}

	public OutOfMemeryException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public OutOfMemeryException(String detailMessage) {
		super(detailMessage);
	}

	public OutOfMemeryException(Throwable throwable) {
		super(throwable);
	}

}
