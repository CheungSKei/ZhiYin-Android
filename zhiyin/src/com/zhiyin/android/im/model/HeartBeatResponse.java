package com.zhiyin.android.im.model;

import com.zhiyin.android.im.model.Entity.Builder;

/**
 * 心跳回复数据包数据结构
 * @author S.Kei.Cheung
 *
 */
public class HeartBeatResponse extends Entity implements Builder{

	private static final long serialVersionUID = 1L;
	// 消息命令
	private String cmd;
	// 消息发送者
	private String from;
	// 消息类型
	private String type;
	// 回复编码
	private String code;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
