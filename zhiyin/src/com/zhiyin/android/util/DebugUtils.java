package com.zhiyin.android.util;

import com.zhiyin.android.im.BuildConfig;

import android.util.Log;

/**
 * ������Ϣ������.
 * 
 * @version 1.0.0
 * @date 2013-1-29
 * @author S.Kei.Cheung
 */
public class DebugUtils {
	
	private static final String DEBUG_TAG = "ZhiYin";
	
	private DebugUtils() {}

	/**
	 * ��ӡ������Ϣ.
	 * 
	 * @param msg
	 */
	public static void debug(String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(DEBUG_TAG, msg);
		}
	}

	/**
	 * ��ӡ������Ϣ.
	 * 
	 * @param msg
	 */
	public static void warn(String msg) {
		if (BuildConfig.DEBUG) {
			Log.w(DEBUG_TAG, msg);
		}
	}
	
	/**
	 * ��ӡ��ʾ��Ϣ.
	 * 
	 * @param msg
	 */
	public static void info(String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(DEBUG_TAG, msg);
		}
	}
	
	/**
	 * ��ӡ������Ϣ.
	 * 
	 * @param msg
	 */
	public static void error(String msg, Exception e) {
		if (BuildConfig.DEBUG) {
			Log.w(DEBUG_TAG, msg, e);
		}
	}
	
}
