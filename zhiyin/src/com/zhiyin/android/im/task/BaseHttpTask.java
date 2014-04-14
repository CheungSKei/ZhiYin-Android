package com.zhiyin.android.im.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;

import com.zhiyin.android.im.application.BaseApplication;
import com.zhiyin.android.im.task.http.TaskListener;
import com.zhiyin.android.im.task.http.TaskObserver;
import com.zhiyin.android.im.task.http.TaskParams;
import com.zhiyin.android.im.task.http.TaskResult;

import android.content.Context;
import android.os.AsyncTask;

/**
 * �첽�������, ͨ��ʵ��Observer�ṩȡ��������. ���е�ҵ����������̳д���, 
 * ������ִ�к���Ҫ�������� ��ӵ�{@link TaskObserver}����ͳһ����.
 * 
 * @param <T>  ���ؽ��ʵ������
 * @version 2.0.0
 * @date 2013-11-17
 * @author S.Kei.Cheung
 */
public abstract class BaseHttpTask<T> extends
		AsyncTask<TaskParams, Object, TaskResult<T>> implements Observer {

	/** ����ID */
	protected Integer mTaskKey = -1;
	/** ����������context */
	protected Context mContext = null;
	/** ��������� */
	protected TaskListener<T> mTaskListener = null;
	
	// ȡ��executeOnExecutor�����������̳߳�
	private static Method executeOnExecutorMethod;
	
	// ȡ��AsyncTask��executeOnExecutor����,�����̳߳�
	static {
        for (Method method : AsyncTask.class.getMethods()) {
            if ("executeOnExecutor".equals(method.getName())) {
                Class<?>[] parameters = method.getParameterTypes();
                if ((parameters.length == 2) && (parameters[0] == Executor.class) && parameters[1].isArray()) {
                    executeOnExecutorMethod = method;
                    break;
                }
            }
        }
    }

	/**
	 * �����첽����ʵ��.
	 * 
	 * @param activity ����������Activityʵ��
	 * @param taskListener ���������
	 * @param taskKey ����ID
	 */
	public BaseHttpTask(Context context, TaskListener<T> taskListener,
			Integer taskKey) {
		this.mContext = context;
		this.mTaskListener = taskListener;
		this.mTaskKey = taskKey;
	}
	
	/**
	 * ��ȡContextʵ��.
	 * @return
	 */
	protected Context getContext() {
		return mContext;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// �������false, ˵������ͬ����������ִ��, ��ȡ��������
		if (mTaskListener != null) {
			if (!mTaskListener.preExecute(this, mTaskKey)) {
				this.cancel(true);
			}
		}
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		if(isCancelled()) {
			return;
		}
		super.onProgressUpdate(values);
	}
	
	/**
	 * �����̳߳�
	 * @return
	 */
	protected BaseHttpTask<T> executeOnSettingsExecutor(TaskParams... params) {
        try {
            if (executeOnExecutorMethod != null) {
                executeOnExecutorMethod.invoke(this, BaseApplication.getExecutorService(), null);
                return this;
            }
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        }

        this.execute(params);
        return this;
    }
	
	@Override
	protected TaskResult<T> doInBackground(TaskParams... params) {
		if(isCancelled()) return null;
		return doInBackgroundForChildren(params);
	}
	
	protected abstract TaskResult<T> doInBackgroundForChildren(TaskParams... params);
	
	@Override
	protected void onPostExecute(TaskResult<T> result) {
		super.onPostExecute(result);
		// �����ؽ��
		if (result != null) {
			result.setTaskKey(mTaskKey);
		}
		if (mTaskListener != null) {
			mTaskListener.onResult(result);
		}
		release();
	}
	
	@Override
	protected void onCancelled() {
		release();
		super.onCancelled();
	}

	@Override
	public void update(Observable observable, Object data) {
		if (mTaskKey == (Integer) data) {
			if (this.getStatus() == AsyncTask.Status.RUNNING) {
				this.cancel(true);
			}
		}
	}
	
	private void release() {
		mContext = null;
		mTaskListener = null;
		BaseApplication.getTaskObserver().deleteObserver(this);
	}

}
