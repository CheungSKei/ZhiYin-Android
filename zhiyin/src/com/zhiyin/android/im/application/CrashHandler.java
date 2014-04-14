package com.zhiyin.android.im.application;


import java.io.File;
import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;

/**
 * �����쳣��Ϣ�����ࣨ����ץȡ�����쳣��Ϣ�����ύ���������ˣ�����BUG���٣�
 * @author liulinbo
 * @date 2013-3-15
 * @version 1.0.0
 * */

public class CrashHandler implements UncaughtExceptionHandler{

	/**
	 * �Ƿ�����־���,��Debug״̬�¿���, ��Release״̬�¹ر�����ʾ��������
	 * */
	public static final boolean DEBUG = false;
	/** ϵͳĬ�ϵ�UncaughtException������ */
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	/** CrashHandlerʵ�� */
	private static CrashHandler INSTANCE;

	/** ��ֻ֤��һ��CrashHandlerʵ�� */
	private CrashHandler() {
	}

	/** ��ȡCrashHandlerʵ�� ,����ģʽ */
	public static CrashHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler();
		}
		return INSTANCE;
	}

	/**
	 * ��ʼ��,ע��Context����, ��ȡϵͳĬ�ϵ�UncaughtException������, ���ø�CrashHandlerΪ�����Ĭ�ϴ�����
	 * 
	 * @param ctx
	 */
	public void init(Context ctx) {
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
   
	/**
	 * ��UncaughtException����ʱ��ת��ú���������
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		if (!handleException(ex) && mDefaultHandler != null) {
			// ����û�û�д�������ϵͳĬ�ϵ��쳣������������
			mDefaultHandler.uncaughtException(thread, ex);
		} else { 
			// ����Լ��������쳣���򲻻ᵯ������Ի�������Ҫ�ֶ��˳�app
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(10);
		}
	}

	/**
	 * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����. �����߿��Ը����Լ���������Զ����쳣�����߼�
	 * 
	 * @return true��������쳣�������������쳣��
	 *         false����������쳣(���Խ���log��Ϣ�洢����)Ȼ�󽻸��ϲ�(����͵���ϵͳ���쳣����)ȥ����
	 *         ����˵����true���ᵯ���Ǹ�������ʾ��false�ͻᵯ��
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		final StackTraceElement[] stack = ex.getStackTrace();
		final String message = ex.getMessage();
		// ʹ��Toast����ʾ�쳣��Ϣ
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
				Date curDate=new Date(System.currentTimeMillis());
				String strCurTime=formatter.format(curDate);
				// ����ֻ����һ���ļ����Ժ�ȫ��������appendȻ���ͣ������ͻ����ظ�����Ϣ�����˲��Ƽ�
				String fileName = "ZaLog_" + strCurTime
						+ ".log";
				
				File dir = new File(Environment.getExternalStorageDirectory().toString() + "/ZhenaiFreeLove/Log");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				
				File file = new File(dir,
						fileName);
				try {
					FileOutputStream fos = new FileOutputStream(file, true);
					fos.write(message.getBytes());
					for (int i = 0; i < stack.length; i++) {
						fos.write(stack[i].toString().getBytes());
					}
					fos.flush();
					fos.close();
				} catch (Exception e) {
				}
				Looper.loop();
			}

		}.start();
		return false;
	}
}