package com.zhiyin.android.im.booter;

import android.graphics.Bitmap;


/**
 * 通知管理接口.
 * 
 * @version 2.0.0
 * @date 2014-05-05
 * @author S.Kei.Cheueng
 */
public interface INotification {

	/**
	 * 信息通知
	 * 当只有一个用户/群,同时只有一条消息时,显示消息实体内容 </br>
	 * 当只有一个用户/群,同时有多条消息时,只显示"发来%d条消息" </br>
	 * 当有多个用户/群,同时有多条消息时,显示"%d个联系人发来%d条消息" </br>
	 * @param paramString1
	 * @param paramString2
	 * @param paramInt
	 */
	public abstract void startNotification(String paramString1, String paramString2, int paramInt);

	/**
	 * 信息通知
	 * 当只有一个用户/群,同时只有一条消息时,显示消息实体内容 </br>
	 * 当只有一个用户/群,同时有多条消息时,只显示"发来%d条消息" </br>
	 * 当有多个用户/群,同时有多条消息时,显示"%d个联系人发来%d条消息" </br>
	 * @param iconResource 图标资源ID
	 * @param defaultValue Notification.DEFAULT_ALL
	 * @param soundResource	音频URI
	 * @param isPriority_min 优先权
	 * @param ticker		 标题
	 * @param contentTitle   内容标题
	 * @param contentText    内容
	 * @param paramBitmap	 大图片
	 */
	public abstract void startNotification(int iconResource, int defaultValue, String soundResource,
			boolean isPriority_min, String ticker, String contentTitle,
			String contentText, Bitmap paramBitmap);
	
	public abstract void startNotification(String paramString);
	
	public abstract void startNotification(int paramInt);
	
	/**
	 * 取消所有通知
	 */
	public abstract void cancelNotification();
}
