package com.zhiyin.android.util;

import com.zhiyin.android.im.BuildConfig;

import android.util.Log;

/**
 * 调试信息工具类.
 * 
 * @version 1.0.0
 * @date 2013-1-29
 * @author S.Kei.Cheung
 */
public class DebugUtils {
	
	private static final String DEBUG_TAG = "ZhiYin";
	
	private DebugUtils() {}

	/**
	 * 打印调试信息.
	 * 
	 * @param msg
	 */
	public static void debug(String msg) {
		debug(DEBUG_TAG, msg);
	}

	/**
	 * 打印调试信息.
	 * 
	 * @param tag
	 * @param msg
	 * @param args
	 */
	public static void debug(String tag,String msg,Object[] args) {
		debug(DEBUG_TAG, String.format(msg,args));
	}
	
	/**
	 * 打印调试信息.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void debug(String tag,String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, msg);
		}
	}

	/**
	 * 打印警告信息.
	 * 
	 * @param tag
	 * @param msg
	 * @param args
	 */
	public static void warn(String tag,String msg,Object[] args) {
		warn(DEBUG_TAG, String.format(msg,args));
	}
	
	/**
	 * 打印警告信息.
	 * 
	 * @param msg
	 */
	public static void warn(String msg) {
		warn(DEBUG_TAG, msg);
	}
	
	/**
	 * 打印警告信息.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void warn(String tag,String msg) {
		if (BuildConfig.DEBUG) {
			Log.w(tag, msg);
		}
	}
	
	/**
	 * 打印提示信息.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void info(String tag,String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(tag, msg);
		}
	}
	
	/**
	 * 打印提示信息.
	 * 
	 * @param msg
	 */
	public static void info(String msg) {
		info(DEBUG_TAG, msg);
	}

	/**
	 * 打印提示信息.
	 * 
	 * @param tag
	 * @param msg
	 * @param args
	 */
	public static void info(String tag,String msg,Object[] args) {
		info(tag, String.format(msg,args));
	}
	
	/**
	 * 打印错误信息(黄色错误提示warn).
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void error(String tag,String msg, Exception e) {
		if (BuildConfig.DEBUG) {
			Log.w(tag, msg, e);
		}
	}
	
	/**
	 * 打印错误信息(黄色错误提示warn).
	 * 
	 * @param msg
	 */
	public static void error(String msg, Exception e) {
		error(DEBUG_TAG, msg, e);
	}
	
	/**
	 * 打印错误信息(黄色错误提示warn).
	 * 
	 * @param tag
	 * @param msg
	 * @param args
	 */
	public static void error(String tag,String msg,Object[] args, Exception e) {
		error(tag, String.format(msg,args), e);
	}
	
	/**
	 * 打印错误信息(红色错误提示error).
	 * 
	 * @param tag
	 * @param msg
	 * @param args
	 */
	public static void error(String tag,String msg,Object[] args) {
		Log.e(tag, String.format(msg,args));
	}
	
}
