 
package com.zhiyin.android.network.socket;

import android.net.NetworkInfo;

/**
 * 消息会话事件 接口
 *
 * @version 1.0.0
 * @date 2014-2-13
 * @author S.Kei.Cheung
 */
public interface OnMessageListener {

	/**
	 * 当客户端和服务端连接断开时调用	 
	 */
	public void onConnectionClosed();
	/**
	 * 当客户端和服务端连接成功时调用
	 */
	public void onConnectionSuccessful();
	/**
	 * 当客户端和服务端连接失败时调用
	 */
	public void onConnectionFailed(Exception e);
	/**
	 * 当客户端和服务端通信过程中出现异常调用
	 */
	public void onExceptionCaught(Throwable throwable);
	/**
	 * 当客户端收到消息后调用，通过{@link #onReplyReceived(ReplyBody reply)}取得
	 */
	public void onMessageReceived(Object message);
	/**
	 * 当客户端收到请求的回复时调用
	 */
	public void onReplyReceived(Object reply);
	/**
	 * 当客户端发送请求成功时
	 */
	public void onSentSuccessful(Object message);
	/**
	 * 当客户端发送请求失败时
	 */
	public void onSentFailed(Exception e,Object message);
	
	/**
	 * 当手机网络状态发生变化
	 */
	public void onNetworkChanged(NetworkInfo info);
}
