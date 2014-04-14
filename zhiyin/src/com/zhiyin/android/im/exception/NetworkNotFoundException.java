package com.zhiyin.android.im.exception;

/**
 * 无网络连接异常.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class NetworkNotFoundException extends Exception {

	private static final long serialVersionUID = 3950892238891940033L;

	public NetworkNotFoundException() {
		super();
	}

	public NetworkNotFoundException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public NetworkNotFoundException(String detailMessage) {
		super(detailMessage);
	}

	public NetworkNotFoundException(Throwable throwable) {
		super(throwable);
	}

}
