package com.zhiyin.android.im.application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhiyin.android.constant.Constants;
import com.zhiyin.android.im.BuildConfig;
import com.zhiyin.android.im.task.http.TaskObserver;
import com.zhiyin.android.network.http.HttpClient;
import com.zhiyin.android.util.ZaLocationListener;

/**
 * 自定义Application.
 * 
 * @version 2.0.0
 * @date 2013-11-17
 * @author S.Kei.Cheueng
 */
public class BaseApplication extends Application {

	/** 应用上下文 */
	private static Context mContext;
	/** 异步任务管理器 */
	private static TaskObserver mTaskObserver;
	/** 网络请求管理器 */
	private static HttpClient mHttpClient;
	/** 除地区外的通用数据字典 */
	private static HashMap<String, TreeMap<Integer, String>> mCommonDics = null;

	/** 记录用户是否已登录 */
	private static boolean mIsUserLogin = false;

	/** 客户端升级URL */
	private static String mAppUpdateUrl = null;

	/** 上次上传定位信息的时间 */
	private static long mLastUpdateLocationTime;

	private static LinkedList<Activity> mActivityList = new LinkedList<Activity>();

	/** */
	/** 异常捕捉实例 */
	private CrashHandler mcrashHandler;

	/** 百度定位 */
	public static LocationClient mLocationClient = null;
	public static BDLocationListener myListener = new ZaLocationListener();
	/** 定位的省份、城市 */
	public static String mProvince = "";
	public static String mCity = "";
	public static String mDistrict = "";
	/** 保存Applicaton信息 */
	public static final String APPLICATIONFILE = "/data/data/com.zhenai.freelove.android/files/welove.bat";

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		if (BuildConfig.DEBUG) {
			// 异常捕捉
			mcrashHandler = CrashHandler.getInstance();
			// 注册crashHandler
			mcrashHandler.init(mContext);
		}
		initImageLoader(getApplicationContext());
		initLocation();
	}

	/**
	 * 初始图片加载管理实例
	 * */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory()
				 .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
				.discCacheSize(50 * 1024 * 1024)
				//.writeDebugLogs()
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	/** 初始化百度定位 */
	public static void initLocation() {
		mLocationClient = new LocationClient(mContext);
		mLocationClient.registerLocationListener(myListener);
	}

	/** 定位Location */
	public static LocationClient getLocationClient() {
		if (mLocationClient == null) {
			initLocation();
		}
		return mLocationClient;
	}

	/** 定位Listener */
	public BDLocationListener getLocationListener() {
		return myListener;
	}

	/**
	 * 返回屏幕DisplayMetrics
	 * 
	 * @return 当前屏幕DisplayMetrics
	 * */
	public static DisplayMetrics screenDisplayMetrics() {
		WindowManager mWm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		mWm.getDefaultDisplay().getMetrics(metrics);
		// 有时候密度出错，所以通过Dpi来判断
		return metrics;
	}

	/**
	 * 返回屏幕 密度
	 * 
	 * @return 当前屏幕密度
	 * */
	public static float screenDensity() {
		// 有时候密度出错，所以通过Dpi来判断
		return screenDisplayMetrics().density;
	}

	/**
	 * @return 应用上下文
	 */
	public static Context getContext() {
		return mContext;
	}

	/**
	 * @return 异步任务管理器
	 */
	public static TaskObserver getTaskObserver() {
		if (mTaskObserver == null) {
			mTaskObserver = new TaskObserver();
		}
		return mTaskObserver;
	}

	/**
	 * @return 网络请求管理器
	 */
	public static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new HttpClient(mContext);
		}
		return mHttpClient;
	}

	/**
	 * 释放网络操作
	 */
	public static void releaseHttpClient() {
		if (mHttpClient != null) {
			mHttpClient.clearCookie();
			mHttpClient.shutdown();
			mHttpClient = null;
		}
	}

	/** 使用JDK1.5以后的线程池技术, 提高线程操作性能 */
	private static ExecutorService executor = null;
	private static final Object LOCK = new Object();
	// 线程队列
	private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue<Runnable>(
			10);
	// 线程工厂
	private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
		private final AtomicInteger counter = new AtomicInteger(0);

		public Thread newThread(Runnable runnable) {
			return new Thread(runnable, "zhenaiFreeLove"
					+ counter.incrementAndGet());
		}
	};

	/**
	 * 用法:<br>
	 * BaseApplication.getExecutorService().execute(Runnable);
	 * 
	 * @return
	 */
	public static ExecutorService getExecutorService() {
		synchronized (LOCK) {
			if (executor == null) {
				executor = new ThreadPoolExecutor(
						Constants.DEFAULT_CORE_POOL_SIZE,
						Constants.DEFAULT_MAXIMUM_POOL_SIZE,
						Constants.DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS,
						DEFAULT_WORK_QUEUE, DEFAULT_THREAD_FACTORY);
			}
		}
		return executor;
	}

	/**
	 * 关闭线程池
	 */
	public static void releaseExecutorService() {
		synchronized (LOCK) {
			if (executor != null) {
				executor.shutdown();
				executor = null;
			}
		}
	}

	/**
	 * 获取通用数据字典
	 * 
	 * @return
	 */
	public static HashMap<String, TreeMap<Integer, String>> getCommonDics() {
		return mCommonDics;
	}

	public static void setCommonDics(
			HashMap<String, TreeMap<Integer, String>> mCommonDics) {
		BaseApplication.mCommonDics = mCommonDics;
	}

	/**
	 * 获取用户是否已登录.
	 * 
	 * @return 当登录标识为true并且cookieName和cookieValue不为空时, 返回true; 否则返回false.
	 */
	public static boolean isUserLogin() {
		return mIsUserLogin;
	}

	public static void setUserLogin(boolean isUserLogin) {
		BaseApplication.mIsUserLogin = isUserLogin;
	}

	public static String getAppUpdateUrl() {
		return mAppUpdateUrl;
	}

	public static void setAppUpdateUrl(String appUpdateUrl) {
		BaseApplication.mAppUpdateUrl = appUpdateUrl;
	}

	public static long getLastUpdateLocationTime() {
		return mLastUpdateLocationTime;
	}

	public static void setLastUpdateLocationTime(long lastUpdateLocationTime) {
		mLastUpdateLocationTime = lastUpdateLocationTime;
	}

	/**
	 * 添加Activity到队列里面
	 * 
	 * @param act
	 */
	public static void addToActivityList(Activity act) {
		mActivityList.add(act);
	}

	/**
	 * 把Activity从队列里面退出
	 * 
	 * @param act
	 */
	public static Activity removeFromActivityList_Last() {
		if (getActivityList_size() <= 0) {
			return null;
		} else {
			return mActivityList.removeLast();
		}
	}

	public static int getActivityList_size() {
		return mActivityList.size();
	}

	/**
	 * 返回把有列表
	 * 
	 * @return Activity 列表
	 */
	public static LinkedList<Activity> getActivityList() {
		return mActivityList;
	}

}
