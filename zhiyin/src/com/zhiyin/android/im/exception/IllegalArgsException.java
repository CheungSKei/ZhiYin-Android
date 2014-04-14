package com.zhiyin.android.im.exception;

/**
 * 非法参数异常.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class IllegalArgsException extends Exception {

	private static final long serialVersionUID = -9017556730133766462L;

	public IllegalArgsException() {
		super();
	}

	public IllegalArgsException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public IllegalArgsException(String detailMessage) {
		super(detailMessage);
	}

	public IllegalArgsException(Throwable throwable) {
		super(throwable);
	}

}
