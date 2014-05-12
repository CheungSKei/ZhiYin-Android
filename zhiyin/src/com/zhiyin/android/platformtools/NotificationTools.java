package com.zhiyin.android.platformtools;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

/**
 * 自定义Notification
 * 
 * @version 1.2.0
 * @date 2014-05-11
 * @author S.Kei.Cheung
 */
public final class NotificationTools {
	// 持续毫秒数
	private static final int MCONTINUED_MS = 300;

	// 停顿毫秒数
	private static final int MSTOPED_MS = 1000;

	@SuppressWarnings("deprecation")
	public static Notification startNotification(Context paramContext,
			int iconResource, int defaultValue, String soundResource,
			boolean isPriority_min, String ticker, String contentTitle,
			String contentText, Bitmap paramBitmap,
			PendingIntent paramPendingIntent) {

		if (Build.VERSION.SDK_INT >= 11) {

			Notification.Builder localBuilder = new Notification.Builder(paramContext);

			localBuilder.setLights(Color.GREEN, MCONTINUED_MS, MSTOPED_MS)
					.setSmallIcon(iconResource).setTicker(ticker)	//设置状态栏的显示的信息
					.setContentTitle(contentTitle).setContentText(contentText)
					.setContentIntent(paramPendingIntent);

			if (isPriority_min)
				defaultValue &= Notification.PRIORITY_MIN;

			localBuilder.setDefaults(defaultValue);

			if (paramBitmap != null)
				localBuilder.setLargeIcon(paramBitmap);

			if (((defaultValue & Notification.FLAG_SHOW_LIGHTS) == 0) || (soundResource == null))
				defaultValue &= Notification.PRIORITY_MIN;
			else
				localBuilder.setSound(Uri.parse(soundResource));

			return localBuilder.getNotification();
		}

		Notification localNotification = new Notification();
		localNotification.ledARGB = Color.GREEN;
		localNotification.ledOnMS = MCONTINUED_MS;
		localNotification.ledOffMS = MSTOPED_MS;
		localNotification.flags = (Notification.FLAG_SHOW_LIGHTS | localNotification.flags);
		localNotification.icon = iconResource;
		localNotification.tickerText = ticker;	//设置状态栏的显示的信息

		if (isPriority_min)
			defaultValue &= Notification.PRIORITY_MIN;

		localNotification.defaults = defaultValue;
		localNotification.setLatestEventInfo(paramContext, contentTitle,contentText, paramPendingIntent);

		if ((defaultValue & Notification.FLAG_SHOW_LIGHTS) == 0  || (soundResource == null))
			defaultValue &= Notification.PRIORITY_MIN;
		else
			localNotification.sound = Uri.parse(soundResource);

		return localNotification;
	}

}
