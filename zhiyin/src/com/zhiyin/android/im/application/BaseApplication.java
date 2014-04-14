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
 * �Զ���Application.
 * 
 * @version 2.0.0
 * @date 2013-11-17
 * @author S.Kei.Cheueng
 */
public class BaseApplication extends Application {

	/** Ӧ�������� */
	private static Context mContext;
	/** �첽��������� */
	private static TaskObserver mTaskObserver;
	/** ������������� */
	private static HttpClient mHttpClient;
	/** ���������ͨ�������ֵ� */
	private static HashMap<String, TreeMap<Integer, String>> mCommonDics = null;

	/** ��¼�û��Ƿ��ѵ�¼ */
	private static boolean mIsUserLogin = false;

	/** �ͻ�������URL */
	private static String mAppUpdateUrl = null;

	/** �ϴ��ϴ���λ��Ϣ��ʱ�� */
	private static long mLastUpdateLocationTime;

	private static LinkedList<Activity> mActivityList = new LinkedList<Activity>();

	/** */
	/** �쳣��׽ʵ�� */
	private CrashHandler mcrashHandler;

	/** �ٶȶ�λ */
	public static LocationClient mLocationClient = null;
	public static BDLocationListener myListener = new ZaLocationListener();
	/** ��λ��ʡ�ݡ����� */
	public static String mProvince = "";
	public static String mCity = "";
	public static String mDistrict = "";
	/** ����Applicaton��Ϣ */
	public static final String APPLICATIONFILE = "/data/data/com.zhenai.freelove.android/files/welove.bat";

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		if (BuildConfig.DEBUG) {
			// �쳣��׽
			mcrashHandler = CrashHandler.getInstance();
			// ע��crashHandler
			mcrashHandler.init(mContext);
		}
		initImageLoader(getApplicationContext());
		initLocation();
	}

	/**
	 * ��ʼͼƬ���ع���ʵ��
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

	/** ��ʼ���ٶȶ�λ */
	public static void initLocation() {
		mLocationClient = new LocationClient(mContext);
		mLocationClient.registerLocationListener(myListener);
	}

	/** ��λLocation */
	public static LocationClient getLocationClient() {
		if (mLocationClient == null) {
			initLocation();
		}
		return mLocationClient;
	}

	/** ��λListener */
	public BDLocationListener getLocationListener() {
		return myListener;
	}

	/**
	 * ������ĻDisplayMetrics
	 * 
	 * @return ��ǰ��ĻDisplayMetrics
	 * */
	public static DisplayMetrics screenDisplayMetrics() {
		WindowManager mWm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		mWm.getDefaultDisplay().getMetrics(metrics);
		// ��ʱ���ܶȳ�������ͨ��Dpi���ж�
		return metrics;
	}

	/**
	 * ������Ļ �ܶ�
	 * 
	 * @return ��ǰ��Ļ�ܶ�
	 * */
	public static float screenDensity() {
		// ��ʱ���ܶȳ�������ͨ��Dpi���ж�
		return screenDisplayMetrics().density;
	}

	/**
	 * @return Ӧ��������
	 */
	public static Context getContext() {
		return mContext;
	}

	/**
	 * @return �첽���������
	 */
	public static TaskObserver getTaskObserver() {
		if (mTaskObserver == null) {
			mTaskObserver = new TaskObserver();
		}
		return mTaskObserver;
	}

	/**
	 * @return �������������
	 */
	public static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new HttpClient(mContext);
		}
		return mHttpClient;
	}

	/**
	 * �ͷ��������
	 */
	public static void releaseHttpClient() {
		if (mHttpClient != null) {
			mHttpClient.clearCookie();
			mHttpClient.shutdown();
			mHttpClient = null;
		}
	}

	/** ʹ��JDK1.5�Ժ���̳߳ؼ���, ����̲߳������� */
	private static ExecutorService executor = null;
	private static final Object LOCK = new Object();
	// �̶߳���
	private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue<Runnable>(
			10);
	// �̹߳���
	private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
		private final AtomicInteger counter = new AtomicInteger(0);

		public Thread newThread(Runnable runnable) {
			return new Thread(runnable, "zhenaiFreeLove"
					+ counter.incrementAndGet());
		}
	};

	/**
	 * �÷�:<br>
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
	 * �ر��̳߳�
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
	 * ��ȡͨ�������ֵ�
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
	 * ��ȡ�û��Ƿ��ѵ�¼.
	 * 
	 * @return ����¼��ʶΪtrue����cookieName��cookieValue��Ϊ��ʱ, ����true; ���򷵻�false.
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
	 * ���Activity����������
	 * 
	 * @param act
	 */
	public static void addToActivityList(Activity act) {
		mActivityList.add(act);
	}

	/**
	 * ��Activity�Ӷ��������˳�
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
	 * ���ذ����б�
	 * 
	 * @return Activity �б�
	 */
	public static LinkedList<Activity> getActivityList() {
		return mActivityList;
	}

}
