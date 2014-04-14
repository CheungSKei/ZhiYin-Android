package com.zhiyin.android.im.task.socket;


/**
 * 异步数据回调
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheung
 */
public interface AsyncCallBack {

	/**
	 * 数据发送成功
	 * @param message 发送数据
	 */
	public void onMessageSentSuccessful(Object message);
	
	/**
	 * 数据发送失败
	 * @param e
	 * @param message	发送数据
	 */
	public void onMessageSentFailed(Exception e, Object message);
	
	/**
	 * 服务器回调数据
	 * @param session	会话
	 * @param message	接收数据	
	 * @throws Exception
	 */
	public void onMessageReceived(Object receivedMessage);
	
	/**
	 * 当客户端收到请求的回复时调用(成功 200)
	 * @param reply	回复数据
	 */
	public void onReplyReceived_OK(Object replyMessage);
	
	/**
	 * 当客户端收到请求的回复时调用(失败)
	 * @param reply	回复数据
	 */
	public void onReplyReceived_ERROR(Object replyMessage);
}
