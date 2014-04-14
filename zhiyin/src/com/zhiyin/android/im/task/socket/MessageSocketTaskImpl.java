package com.zhiyin.android.im.task.socket;

import java.util.Map;

import com.zhiyin.android.constant.Constants;
import com.zhiyin.android.im.task.BaseSocketTask;

/**
 * �ı�����Ϣ����
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheueng
 * 
 */
public class MessageSocketTaskImpl extends BaseSocketTask {

	/**
	 * ���캯��
	 * @param asyncCallBack �ӿڻص�
	 */
	public MessageSocketTaskImpl(AsyncCallBack asyncCallBack) {
		super(asyncCallBack);
	}

	/**
	 * �ı���Ϣ���� {@link Constants.IMCmd.IM_TEXT_CMD}
	 */
	public void commit(long msgId, Map<String, Object> data) {
		commit(Constants.IMCmd.IM_LOGIN_CMD, msgId, data);
	}

}