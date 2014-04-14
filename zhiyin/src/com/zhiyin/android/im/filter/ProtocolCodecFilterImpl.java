package com.zhiyin.android.im.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

public class ProtocolCodecFilterImpl extends ProtocolCodecFilter {

	public ProtocolCodecFilterImpl(ProtocolCodecFactory factory) {
		super(factory);
		
	}

	@Override
	public void filterWrite(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		
		super.filterWrite(nextFilter, session, writeRequest);
		
	}

}
