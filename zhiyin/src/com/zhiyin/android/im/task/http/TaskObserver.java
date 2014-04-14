package com.zhiyin.android.im.task.http;

import java.util.Observable;

/**
 * 异步任务统一管理类.
 * 所有的异步任务实例都需要调用 TaskObserver.addObserver() 添加到此管理器中进行统一管理.
 * 
 * @version 1.0.0
 * @date 2012-9-12
 * @author DengZhaoyong
 */
public class TaskObserver extends Observable {

	/**
	 * 取消,中断一个正在执行的异常任务.
	 * 
	 * @param taskKey 任务ID
	 */
	public void cancelTask(Integer taskKey) {
		setChanged();
		notifyObservers(taskKey);
	}
	
}
