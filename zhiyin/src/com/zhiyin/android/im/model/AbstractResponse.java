package com.zhiyin.android.im.model;

import java.util.Map;

import com.zhiyin.android.im.model.Entity.Builder;
/**
 * 服务器返回数据结构
 *
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public class AbstractResponse extends Entity implements Builder {

	private static final long serialVersionUID = 3229813618596480768L;

	/**
	 * 返回命令
	 */
	private String cmd;
	
	/**
	 * 请求返回码 200、404、500、502
	 * */
	private String code;
	
	/**
	 * 返回说明
	 */
	private String resMsg;

	/**
	 * 时间戳
	 */
	private long timestamp;
	
	//返回的json数据,实体也以Map的形式保存
	private Map<String,Object> data;
	
	// 消息id
	private long msgId;
	
	// 消息服务器id
	private long msgSvrId;
	
	public AbstractResponse()
	{
		timestamp = System.currentTimeMillis();
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	
	public Map<String,Object> getData() {
		return data;
	}
	
	public void setData(Map<String,Object> data) {
		this.data = data;
	}
	
	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	
	public long getMsgSvrId() {
		return msgSvrId;
	}

	public void setMsgSvrId(long msgSvrId) {
		this.msgSvrId = msgSvrId;
	}
}
