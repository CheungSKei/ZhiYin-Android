package com.zhiyin.android.im.booter;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.zhiyin.android.util.DebugUtils;
import com.zhiyin.android.util.StringUtils;

/**
 * 通知广播接收.
 * 
 * @version 2.0.0
 * @date 2014-05-03
 * @author S.Kei.Cheueng
 */
public class NotifyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context paramContext, Intent paramIntent) {
		if ((paramContext == null) || (paramIntent == null) || (StringUtils.isEmpty(paramIntent.getAction())))
			return;
		
		Intent localIntent = new Intent(paramContext, NotifyService.class);
	    if (paramIntent.getBooleanExtra("intent_from_shoot_key", false))
	        localIntent.putExtra("notify_option_type", 3);
	    localIntent.putExtras(paramIntent);
	    paramContext.startService(localIntent);
	}
	
	public class NotifyService extends Service{
		
		public final int STARTID = -1111;

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
		
		public void onCreate()
		{
			if (Build.VERSION.SDK_INT < 18)
				startForeground(STARTID, new Notification());
			super.onCreate();
		}

		public void onStart(Intent paramIntent, int paramInt)
		{
			//receiveImp(paramIntent);
		}

		public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
		{
			DebugUtils.debug("NotifyService onStartCommand flags :" + paramInt1 + "startId :" + paramInt2 + " intent " + paramIntent);
			//receiveImp(paramIntent);
			return 1;
		}
	}

}
