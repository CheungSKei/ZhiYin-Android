package com.zhiyin.android.im.exception;

/**
 * 没有SDCard或者SDCard不可写异常.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class SDCardNotFoundException extends Exception {

	private static final long serialVersionUID = -214555480695052352L;

	public SDCardNotFoundException() {
		super();
	}

	public SDCardNotFoundException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public SDCardNotFoundException(String detailMessage) {
		super(detailMessage);
	}

	public SDCardNotFoundException(Throwable throwable) {
		super(throwable);
	}

}
