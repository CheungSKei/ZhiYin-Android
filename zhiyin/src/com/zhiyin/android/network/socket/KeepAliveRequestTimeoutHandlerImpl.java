package com.zhiyin.android.network.socket;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

import com.zhiyin.android.util.DebugUtils;

/**
 * ������ʱ����
 * 
 * @version 1.0.0
 * @date 2014-02-18
 * @author S.Kei.Cheueng
 */
public class KeepAliveRequestTimeoutHandlerImpl implements
		KeepAliveRequestTimeoutHandler {

	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter filter,
			IoSession session) throws Exception {
		DebugUtils.info("������ʱ��");
	}

}
