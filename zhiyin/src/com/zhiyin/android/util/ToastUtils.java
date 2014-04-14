package com.zhiyin.android.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * ToastUtils.java
 * 自定义Toast.
 *
 * @版本号 1.0
 * @date 2012-9-10
 * @author S.Kei.Cheung
 */
public class ToastUtils extends Toast {

	private static Handler handler = new Handler(Looper.getMainLooper());
	
	private static Toast toast = null;
	
	private int mDuration=0;
	
	public ToastUtils(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getDuration()
	{
		return this.mDuration;
	}

	@Override
	public void setDuration(int duration) {
		this.mDuration = duration;
	}
	
	public static void showMessage(final Context context, final String msg) {
		showMessage(context, msg, Toast.LENGTH_SHORT);
	}

	public static void showMessage(final Context context, final String msg,
			final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (this) {
							if (toast != null) {
								toast.cancel();
								toast.setText(msg);
								toast.setDuration(len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							} else {
								toast = Toast.makeText(context, msg, len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	public static void showMessage(final Context context, final int msg) {
		showMessage(context, msg, Toast.LENGTH_SHORT);
	}

	public static void showMessage(final Context context, final int msg,
			final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (this) {
							if (toast != null) {
								toast.cancel();
								toast.setText(msg);
								toast.setDuration(len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							} else {
								toast = Toast.makeText(context, msg, len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}
	
	
}
