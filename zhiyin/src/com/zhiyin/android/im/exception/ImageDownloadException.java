package com.zhiyin.android.im.exception;

/**
 * 下载图片时出现异常.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class ImageDownloadException extends Exception {

	private static final long serialVersionUID = -3063638267833281892L;

	public ImageDownloadException() {
		super();
	}

	public ImageDownloadException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ImageDownloadException(String detailMessage) {
		super(detailMessage);
	}

	public ImageDownloadException(Throwable throwable) {
		super(throwable);
	}

}
