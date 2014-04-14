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
 * 异步任务基类, 通过实现Observer提供取消任务功能. 所有的业务处理任务都需继承此类, 
 * 当任务执行后需要将该任务 添加到{@link TaskObserver}进行统一管理.
 * 
 * @param <T>  返回结果实体类型
 * @version 2.0.0
 * @date 2013-11-17
 * @author S.Kei.Cheung
 */
public abstract class BaseHttpTask<T> extends
		AsyncTask<TaskParams, Object, TaskResult<T>> implements Observer {

	/** 任务ID */
	protected Integer mTaskKey = -1;
	/** 任务依附的context */
	protected Context mContext = null;
	/** 任务监听器 */
	protected TaskListener<T> mTaskListener = null;
	
	// 取得executeOnExecutor方法，设置线程池
	private static Method executeOnExecutorMethod;
	
	// 取得AsyncTask的executeOnExecutor方法,设置线程池
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
	 * 构建异步任务实例.
	 * 
	 * @param activity 任务依附的Activity实例
	 * @param taskListener 任务监听器
	 * @param taskKey 任务ID
	 */
	public BaseHttpTask(Context context, TaskListener<T> taskListener,
			Integer taskKey) {
		this.mContext = context;
		this.mTaskListener = taskListener;
		this.mTaskKey = taskKey;
	}
	
	/**
	 * 获取Context实例.
	 * @return
	 */
	protected Context getContext() {
		return mContext;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// 如果返回false, 说明有相同的任务正在执行, 则取消此任务
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
	 * 设置线程池
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
		// 处理返回结果
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
