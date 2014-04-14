package com.zhiyin.android.im.exception;

/**
 * 执行HTTP请求时出现异常.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class HttpRequestException extends Exception {

	private static final long serialVersionUID = 1364844421724951340L;

	public HttpRequestException() {
		super();
	}

	public HttpRequestException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public HttpRequestException(String detailMessage) {
		super(detailMessage);
	}

	public HttpRequestException(Throwable throwable) {
		super(throwable);
	}

}
