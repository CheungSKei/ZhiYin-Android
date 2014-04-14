package com.zhiyin.android.im.task.http;

import java.util.HashMap;

import com.zhiyin.android.im.application.BaseApplication;
import com.zhiyin.android.im.task.BaseHttpTask;
import com.zhiyin.android.util.ToastUtils;

import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 任务监听器.
 * 
 * @param <T> 任务执行结果的返回类型
 * @version 1.0.0
 * @date 2012-9-21
 * @author DengZhaoyong
 */
@SuppressWarnings("all")
public abstract class TaskListener<T> {
	
	/** 关联的异步任务列表 */	
	protected HashMap<Integer, BaseHttpTask> mTaskMap;

	public TaskListener(HashMap<Integer, BaseHttpTask> taskMap) {
		this.mTaskMap = taskMap;
	}
	
	/**
	 * 提前任务前先添加到任务观察器管理中.
	 * 
	 * @param task 实现了Observer接口的任务实例
	 * @param taskKey 任务标识
	 * @return 正常情况下返回true, 如果返回false, 应该终止该任务.
	 */
	@SuppressWarnings("unchecked")
	public boolean preExecute(BaseHttpTask<T> task, Integer taskKey) {
		
		boolean flag = true;
		
		/*
		 * 如果当前Activity任务列表已存在对应任务，则Cancel之前任务，再添加新任务
		 * 返回 false时取消任务
		 */
		if (mTaskMap != null) {
			BaseHttpTask<T> taskInMap = mTaskMap.get(taskKey);
			
			if (taskInMap != null){
				taskInMap.cancel(true);
				BaseApplication.getTaskObserver().cancelTask(taskKey);
				mTaskMap.remove(taskKey);
			}
			// 将任务添加到TaskObserver进行统一管理
			BaseApplication.getTaskObserver().addObserver(task);
			// 将任务添加到异步任务列表中
			mTaskMap.put(taskKey, task);
		}
		
		return flag;
	}
	
	/**
	 * 任务返回后将要执行的操作.
	 * 
	 * @param result
	 */
	public void onResult(TaskResult<T> result) {
		switch (result.getCode()) {
		case TaskResult.ERROR:
			ToastUtils.showMessage(BaseApplication.getContext(),
					result.getErrorMsg());
			break;
		}
	}
	
	/**
	 * 释放资源.
	 */
	public void release() {
		this.mTaskMap = null;
	}
	
}
