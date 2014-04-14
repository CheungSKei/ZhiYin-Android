package com.zhiyin.android.im.model;

import java.util.Map;

import com.zhiyin.android.im.model.Entity.Builder;
/**
 * 客户端发送数据结构
 *
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public class AbstractRequest extends Entity implements Builder {

	private static final long serialVersionUID = -8086345000697246490L;
	// 发送消息的时间戳
	private long timestamp;
	// 消息命令
	private String cmd;
	//消息发送者
	private String from;
	// 消息id
	private long msgId;
	// 发送消息自定义内容
	private Map<String,Object> data;
	// cookie
	private String cookieValue;
	// deviceId
	private String deviceId;

	public AbstractRequest() {
		timestamp = System.currentTimeMillis();
	}

	public String getCmd() {
		return cmd;
	}
	
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	
	public Map<String,Object> getData() {
		return data;
	}
	
	public void setData(Map<String,Object> data) {
		this.data = data;
	}
	
	public String getCookieValue() {
		return cookieValue;
	}

	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
