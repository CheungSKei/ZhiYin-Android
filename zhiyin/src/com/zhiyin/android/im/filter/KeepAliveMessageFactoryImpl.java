package com.zhiyin.android.im.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.zhiyin.android.constant.Constants;
import com.zhiyin.android.im.model.HeartBeatRequest;
import com.zhiyin.android.im.model.HeartBeatResponse;
import com.zhiyin.android.util.StringUtils;

/**
 * 心跳工厂实现
 * 
 * @version 1.0.0
 * @date 2014-02-18
 * @author S.Kei.Cheueng
 */
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {

	/*
	 * 向服务器发送心跳请求包 non-null(发送)
	 * 
	 * @see
	 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getRequest
	 * (org.apache.mina.core.session.IoSession)
	 */
	@Override
	public Object getRequest(IoSession session) {
		//直接返回你要发送的心跳消息的数据包
		HeartBeatRequest request = new HeartBeatRequest();
		request.setCmd(Constants.IMCmd.IM_HEARTBEAT_CMD);
		request.setFrom(Constants.FROM_CLIENT);
		request.setType(Constants.REQUEST_TYPE);
		return request;
	}

	/*
	 * 服务器不会给客户端发送心跳请求，客户端当然也不用反馈  该方法返回null(发送)
	 * 
	 * @see
	 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getResponse
	 * (org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public Object getResponse(IoSession session, Object request) {
		System.out.println("得到返回");
		return null;
	}

	/*
	 * 服务器不会给客户端发送请求包，因此不关注请求包，直接返回false(获取)
	 * 
	 * @see
	 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isRequest
	 * (org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public boolean isRequest(IoSession session, Object message) {
		boolean rtnBoolean = false;
		if(message != null && message instanceof HeartBeatRequest){
			
			HeartBeatRequest request = (HeartBeatRequest) message;
			//检测是否是心跳消息
			String cmd = request.getCmd();
			String from = request.getFrom();
			String type = request.getType();
			if(StringUtils.isNotBlank(cmd)
					&& Constants.IMCmd.IM_HEARTBEAT_CMD.equals(cmd)
					&& StringUtils.isNotBlank(from)
					&& Constants.FROM_CLIENT.equals(from)
					&& StringUtils.isNotBlank(type)
					&& Constants.REQUEST_TYPE.equals(type)){
				System.out.println("心跳请求");
				rtnBoolean = true; 
			}
		}
		
		return rtnBoolean;
	}

	/*
	 * 客户端关注请求反馈，因此判断mesaage是否是反馈包(获取)
	 * 
	 * @see
	 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isResponse
	 * (org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public boolean isResponse(IoSession session, Object message) {
		boolean rtnBoolean = false;
		if(message != null && message instanceof HeartBeatResponse){
			HeartBeatResponse response = (HeartBeatResponse) message;
			String cmd = response.getCmd();
			String from = response.getFrom();
			String type = response.getType();
			if(StringUtils.isNotBlank(cmd)
					&& Constants.IMCmd.IM_HEARTBEAT_CMD.equals(cmd)
					&& StringUtils.isNotBlank(from)
					&& Constants.FROM_SERVER.equals(from)
					&& StringUtils.isNotBlank(type)
					&& Constants.RESPONSE_TYPE.equals(type)){
				
				//表示是来自服务端的心跳消息的响应数据包
				System.out.println("心跳回复");
				rtnBoolean = true;
			}
		}
		
		return rtnBoolean;
	}

}
