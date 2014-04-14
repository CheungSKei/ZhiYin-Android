package com.zhiyin.android.im.task;

import java.util.Map;

import com.zhiyin.android.constant.Constants;
import com.zhiyin.android.im.model.AbstractRequest;
import com.zhiyin.android.im.task.socket.AsyncCallBack;
import com.zhiyin.android.network.socket.MessageConnectorManager;

/**
 * Socket通讯任务类
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheung
 */
public abstract class BaseSocketTask {

	private AsyncCallBack asyncCallBack;
	
	public BaseSocketTask(AsyncCallBack asyncCallBack){
		this.asyncCallBack = asyncCallBack;
	}
	
	/**
	 * 记录回调接口
	 * @param msgId
	 */
	protected void putAsyncCallBack(long msgId){
		MessageConnectorManager.getManager().putAsyncCallBack(
				msgId, asyncCallBack);
	}
	
	/**
	 * 文本类命令传送
	 */
	public void commit(String cmd, long msgId, Map<String, Object> data) {
		AbstractRequest _AbstractRequest = new AbstractRequest();
		_AbstractRequest.setCmd(cmd);
		_AbstractRequest.setMsgId(msgId);
		_AbstractRequest.setData(data);
		_AbstractRequest.setFrom(Constants.FROM_CLIENT);
		_AbstractRequest.setCookieValue(MessageConnectorManager.getManager().getCookieValue());
		_AbstractRequest.setDeviceId(MessageConnectorManager.getManager().getDeviceId());
		// 插入回调接口
		putAsyncCallBack(_AbstractRequest.getMsgId());
		// 消息发送
		sendMessage(_AbstractRequest);
	}
	
	/**
	 * 发送消息
	 * @param message
	 */
	private void sendMessage(AbstractRequest message){
		MessageConnectorManager.getManager().send(message);
	}
	
}
