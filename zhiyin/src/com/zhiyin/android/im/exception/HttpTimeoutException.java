package com.zhiyin.android.im.exception;

/**
 * 网络连接超时异常.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class HttpTimeoutException extends Exception {

	private static final long serialVersionUID = 2234391926164009203L;

	public HttpTimeoutException() {
		super();
	}

	public HttpTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpTimeoutException(String message) {
		super(message);
	}

	public HttpTimeoutException(Throwable cause) {
		super(cause);
	}

}
