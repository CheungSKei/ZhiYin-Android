package com.zhiyin.android.im.booter;

import android.graphics.Bitmap;


/**
 * ֪ͨ����ӿ�.
 * 
 * @version 2.0.0
 * @date 2014-05-05
 * @author S.Kei.Cheueng
 */
public interface INotification {

	/**
	 * ��Ϣ֪ͨ
	 * ��ֻ��һ���û�/Ⱥ,ͬʱֻ��һ����Ϣʱ,��ʾ��Ϣʵ������ </br>
	 * ��ֻ��һ���û�/Ⱥ,ͬʱ�ж�����Ϣʱ,ֻ��ʾ"����%d����Ϣ" </br>
	 * ���ж���û�/Ⱥ,ͬʱ�ж�����Ϣʱ,��ʾ"%d����ϵ�˷���%d����Ϣ" </br>
	 * @param paramString1
	 * @param paramString2
	 * @param paramInt
	 */
	public abstract void startNotification(String paramString1, String paramString2, int paramInt);

	/**
	 * ��Ϣ֪ͨ
	 * ��ֻ��һ���û�/Ⱥ,ͬʱֻ��һ����Ϣʱ,��ʾ��Ϣʵ������ </br>
	 * ��ֻ��һ���û�/Ⱥ,ͬʱ�ж�����Ϣʱ,ֻ��ʾ"����%d����Ϣ" </br>
	 * ���ж���û�/Ⱥ,ͬʱ�ж�����Ϣʱ,��ʾ"%d����ϵ�˷���%d����Ϣ" </br>
	 * @param iconResource ͼ����ԴID
	 * @param defaultValue Notification.DEFAULT_ALL
	 * @param soundResource	��ƵURI
	 * @param isPriority_min ����Ȩ
	 * @param ticker		 ����
	 * @param contentTitle   ���ݱ���
	 * @param contentText    ����
	 * @param paramBitmap	 ��ͼƬ
	 */
	public abstract void startNotification(int iconResource, int defaultValue, String soundResource,
			boolean isPriority_min, String ticker, String contentTitle,
			String contentText, Bitmap paramBitmap);
	
	public abstract void startNotification(String paramString);
	
	public abstract void startNotification(int paramInt);
	
	/**
	 * ȡ������֪ͨ
	 */
	public abstract void cancelNotification();
}
