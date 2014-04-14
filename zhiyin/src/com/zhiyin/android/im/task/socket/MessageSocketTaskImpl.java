package com.zhiyin.android.im.task.socket;

import java.util.Map;

import com.zhiyin.android.constant.Constants;
import com.zhiyin.android.im.task.BaseSocketTask;

/**
 * 文本类消息命令
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheueng
 * 
 */
public class MessageSocketTaskImpl extends BaseSocketTask {

	/**
	 * 构造函数
	 * @param asyncCallBack 接口回调
	 */
	public MessageSocketTaskImpl(AsyncCallBack asyncCallBack) {
		super(asyncCallBack);
	}

	/**
	 * 文本消息传送 {@link Constants.IMCmd.IM_TEXT_CMD}
	 */
	public void commit(long msgId, Map<String, Object> data) {
		commit(Constants.IMCmd.IM_LOGIN_CMD, msgId, data);
	}

}
