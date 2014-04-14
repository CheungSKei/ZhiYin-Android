package com.zhiyin.android.im.model;

import com.zhiyin.android.im.model.Entity.Builder;

/**
 * heartbeat心跳消息请求消息体
 * @author S.Kei.Cheung
 *
 */
public class HeartBeatRequest extends Entity implements Builder{
	
	private static final long serialVersionUID = 1L;
	// 消息命令
	private String cmd;
	// 消息发送者
	private String from;
	// 消息类型
	private String type;
	// cookie
	private String cookieValue;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	public String getCookieValue() {
		return cookieValue;
	}
	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

}
