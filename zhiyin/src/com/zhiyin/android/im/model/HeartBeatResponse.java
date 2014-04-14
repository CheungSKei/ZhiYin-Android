package com.zhiyin.android.im.model;

import com.zhiyin.android.im.model.Entity.Builder;

/**
 * �����ظ����ݰ����ݽṹ
 * @author S.Kei.Cheung
 *
 */
public class HeartBeatResponse extends Entity implements Builder{

	private static final long serialVersionUID = 1L;
	// ��Ϣ����
	private String cmd;
	// ��Ϣ������
	private String from;
	// ��Ϣ����
	private String type;
	// �ظ�����
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