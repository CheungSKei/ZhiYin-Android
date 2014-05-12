package com.zhiyin.android.im.booter;

import java.util.Locale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Looper;

import com.zhiyin.android.im.R;
import com.zhiyin.android.im.ui.LauncherUIProxy;
import com.zhiyin.android.platformtools.NotificationTools;
import com.zhiyin.android.util.DebugUtils;

/**
 * 通知管理.
 * 
 * @version 2.0.0
 * @date 2014-05-04
 * @author S.Kei.Cheueng
 */
public class ZYNotificationManager implements INotification, IQueue {

	private Context mContext = null;

	private static final int NOTIFICATION_REQUESTCODE = 35;

	private static final int NOTIFICATION_UPDATE = 34;

	public ZYNotificationManager(Context paramContext) {
		this.mContext = paramContext;
	}

	@SuppressWarnings("deprecation")
	@Override
	public final void startNotification(String contentText, String userName,
			int msg_Type) {

		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = contentText;
		arrayOfObject[1] = userName;
		arrayOfObject[2] = Integer.valueOf(msg_Type);

		DebugUtils.info(String.format(Locale.getDefault(),
				"showPushContentNotification, pushContent = %s, fromUserName = %s, msgType = %d",arrayOfObject));
		Intent localIntent = new Intent(this.mContext, LauncherUIProxy.class);
		localIntent.putExtra("nofification_type", "pushcontent_notification");
		localIntent.putExtra("Intro_Is_Muti_Talker", true);
		localIntent.putExtra("Main_FromUserName", userName);
		localIntent.putExtra("MainUI_User_Last_Msg_Type", msg_Type);
		PendingIntent localPendingIntent = PendingIntent.getActivity(
				this.mContext, NOTIFICATION_REQUESTCODE, localIntent,
				PendingIntent.FLAG_ONE_SHOT);

		// 其中第一个参数代表图标，第二个参数代表提示的内容，第三个参数是指要显示的时间，一般是当即显示，故填入系统当前时间
		Notification localNotification = new Notification(
				R.drawable.ic_launcher, null, System.currentTimeMillis());
		localNotification.flags = (Notification.FLAG_AUTO_CANCEL | localNotification.flags);
		localNotification.setLatestEventInfo(this.mContext,
				this.mContext.getString(R.string.app_pushcontent_title),
				contentText, localPendingIntent);
		((NotificationManager) this.mContext.getSystemService("notification"))
				.notify(NOTIFICATION_REQUESTCODE, localNotification);
	}

	@SuppressWarnings("deprecation")
	@Override
	public final void startNotification(String contentTitle) {
		Notification localNotification = new Notification();
		localNotification.icon = R.drawable.ic_launcher;
		Intent localIntent = new Intent(this.mContext, LauncherUIProxy.class);
		localIntent.putExtra("Intro_Notify", true);
		localIntent.putExtra("Intro_Notify_User", true);
		localNotification.setLatestEventInfo(this.mContext, contentTitle, null,
				PendingIntent.getActivity(this.mContext, 0, localIntent,
						PendingIntent.FLAG_CANCEL_CURRENT));
		NotificationManager localNotificationManager = (NotificationManager) this.mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		localNotification.flags = Notification.FLAG_AUTO_CANCEL;
		if (localNotificationManager != null)
			localNotificationManager.notify(0, localNotification);
	}

	@SuppressWarnings("deprecation")
	@Override
	public final void startNotification(int update_type) {
		Intent localIntent = new Intent(this.mContext, LauncherUIProxy.class);
		localIntent.putExtra("nofification_type", "update_nofification");
		localIntent.putExtra("show_update_dialog", true);
		localIntent.putExtra("update_type", update_type);
		PendingIntent localPendingIntent = PendingIntent.getActivity(
				this.mContext, 0, localIntent, 0);
		Notification localNotification = new Notification(
				R.drawable.ic_launcher, null, System.currentTimeMillis());
		localNotification.flags = (0x10 | localNotification.flags);
		localNotification.setLatestEventInfo(this.mContext,
				this.mContext.getString(R.string.app_update_package_notify),
				this.mContext.getString(R.string.app_recommend_update),
				localPendingIntent);
		((NotificationManager) this.mContext.getSystemService("notification"))
				.notify(NOTIFICATION_UPDATE, localNotification);
	}

	@Override
	public Looper getLooper() {
		return Looper.getMainLooper();
	}

	private void cancel() {
		NotificationManager localNotificationManager = (NotificationManager) this.mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		if (localNotificationManager != null) {
			localNotificationManager.cancel(0);
		}
	}

	@Override
	public void cancelNotification() {
		cancel();
		NotificationManager localNotificationManager = (NotificationManager) this.mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		if (localNotificationManager != null)
			localNotificationManager.cancel(NOTIFICATION_REQUESTCODE);
	}

	@Override
	public void startNotification(int iconResource, int defaultValue,
			String soundResource, boolean isPriority_min, String ticker,
			String contentTitle, String contentText, Bitmap paramBitmap) {
		
		Intent localIntent = new Intent(this.mContext, LauncherUIProxy.class);
		
		PendingIntent localPendingIntent = PendingIntent.getActivity(
				this.mContext, NOTIFICATION_REQUESTCODE, localIntent,
				PendingIntent.FLAG_ONE_SHOT);
		
		Notification localNotification = NotificationTools.startNotification(mContext, iconResource,
				defaultValue, soundResource, isPriority_min, ticker,
				contentTitle, contentText, paramBitmap, localPendingIntent);
		
		((NotificationManager) this.mContext.getSystemService("notification"))
		.notify(NOTIFICATION_REQUESTCODE, localNotification);
	}

}
