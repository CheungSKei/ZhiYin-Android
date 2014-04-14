package com.zhiyin.android.network.socket;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.telephony.TelephonyManager;

import com.zhiyin.android.constant.Constants;
import com.zhiyin.android.im.application.BaseApplication;
import com.zhiyin.android.im.filter.ClientMessageCodecFactory;
import com.zhiyin.android.im.filter.KeepAliveMessageFactoryImpl;
import com.zhiyin.android.im.filter.ProtocolCodecFilterImpl;
import com.zhiyin.android.im.model.AbstractRequest;
import com.zhiyin.android.im.model.AbstractResponse;
import com.zhiyin.android.im.model.Entity;
import com.zhiyin.android.im.ssl.BogusSslContextFactory;
import com.zhiyin.android.im.task.socket.AsyncCallBack;
import com.zhiyin.android.util.DebugUtils;

/**
 * 连接服务端管理，处理消息接收分发activity处理
 * 
 * @version 1.0.0
 * @date 2014-2-13
 * @author S.Kei.Cheung
 */
public class MessageConnectorManager extends MessageEventHandlerContainer {

	private NioSocketConnector connector;
	private ConnectFuture connectFuture;
	private IoSession session;

	// cookie
	private String mCookieValue = null;
	// deviceId id
	private String mDeviceId = null;
	// context
	Context context;
	// 用户是否采用ssl加密方式
	private static boolean useSsl = false;

	SharedPreferences messageServerInfo;

	public static String MESSAGE_SERVIER_HOST = "MESSAGE_SERVIER_HOST";

	public static String MESSAGE_SERVIER_PORT = "MESSAGE_SERVIER_PORT";

	/**
	 * 消息监听
	 */
	private ArrayList<OnMessageListener> messageListeners;

	/**
	 * 记录所有请求服务器需回调异步数据
	 * 
	 * @param Key
	 *            msgId
	 * @param Value
	 *            回调接口
	 */
	private static Map<Long, AsyncCallBack> mAsyncCallBackMap = new HashMap<Long, AsyncCallBack>();
	/**
	 * 单例对象 {@link #getManager(Context)}
	 */
	static MessageConnectorManager manager;

	// 消息广播action
	static final String NEW_MSG_ACTION = "com.zhiyi.im.message.RECEIVED";

	private ExecutorService executor;

	private MessageConnectorManager() {
		this.context = BaseApplication.getContext();
		// device id
		TelephonyManager TelephonyMgr = (TelephonyManager)this.context.getSystemService(Context.TELEPHONY_SERVICE); 
		this.mDeviceId = TelephonyMgr.getDeviceId();
		messageListeners = new ArrayList<OnMessageListener>();
		executor = Executors.newFixedThreadPool(1);
		messageServerInfo = context.getSharedPreferences("SERVER_INFO", Context.MODE_PRIVATE);
		// 非阻塞Socket连接
		connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(10 * 1000);
		DefaultIoFilterChainBuilder filterChainBuilder = connector.getFilterChain();
		filterChainBuilder.addLast("mdc", new MdcInjectionFilter());
		// 日志Filter
		filterChainBuilder.addLast("logger", new LoggingFilter());
		// 自定义消息Filter
		filterChainBuilder.addLast("codec", new ProtocolCodecFilterImpl(new ClientMessageCodecFactory()));
		
		try{
			if(useSsl){
				SSLContext sslContext = BogusSslContextFactory.getInstance(false);
				SslFilter sslFilter = new SslFilter(sslContext);
				sslFilter.setUseClientMode(true);
				filterChainBuilder.addFirst("sslFilter", sslFilter);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// 线程池分发执行Handler任务(业务逻辑的任务).
		filterChainBuilder.addLast("threadPool",
				new ExecutorFilter(Executors.newCachedThreadPool()));
		// 收发超时时间
		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, Constants.IDELTIMEOUT);
		/** 心跳 */
		KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
		KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl();
		KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
				IdleStatus.BOTH_IDLE, heartBeatHandler);
		/** 是否回发 */
		heartBeat.setForwardEvent(true);
		/** 发送频率 */
		heartBeat.setRequestInterval(Constants.HEARTBEATRATE);
		connector.getSessionConfig().setKeepAlive(true);
		connector.getFilterChain().addLast("heartbeat", heartBeat);

		connector.setHandler(iohandler);
	}

	/**
	 * 注册消息监听
	 * 
	 * @param listener
	 */
	public void registerMessageListener(OnMessageListener listener) {

		if (!messageListeners.contains(listener)) {
			messageListeners.add(listener);
			// 按照接收顺序倒序
			Collections.sort(messageListeners, new MessageReceiveComparator(
					context));
		}
	}

	/**
	 * 移除消息监听
	 * 
	 * @param listener
	 */
	public void removeMessageListener(OnMessageListener listener) {
		for (int i = 0; i < messageListeners.size(); i++) {
			if (listener.getClass() == messageListeners.get(i).getClass()) {
				messageListeners.remove(i);
			}
		}
	}

	/**
	 * 取得MessageConnectorManager对象实例
	 * 
	 * @param context
	 * @return
	 */
	public static MessageConnectorManager getManager() {
		if (manager == null) {
			manager = new MessageConnectorManager();
		}
		return manager;

	}

	/**
	 * 异步连接
	 * 
	 * @param serverHost
	 *            IP
	 * @param serverPort
	 *            端口
	 */
	private void syncConnection(final String serverHost,
			final int serverPort) {
		try {
			InetSocketAddress remoteSocketAddress = new InetSocketAddress(
					serverHost, serverPort);
			connectFuture = connector.connect(remoteSocketAddress);
			connectFuture.awaitUninterruptibly();
			session = connectFuture.getSession();
		} catch (Exception e) {

			android.os.Message msg = new android.os.Message();
			msg.getData().putSerializable("e", e);
			e.printStackTrace();
			connectionFailedHandler.sendMessage(msg);

		}

	}

	/**
	 * 连接服务器
	 * 
	 * @param serverHost
	 *            IP
	 * @param serverPort
	 *            端口
	 */
	protected void connect(final String serverHost, final int serverPort) {
		messageServerInfo.edit().putInt(MESSAGE_SERVIER_PORT, serverPort)
				.putString(MESSAGE_SERVIER_HOST, serverHost).commit();
		if (!netWorkAvailable()) {
			return;
		}
		executor.execute(new Runnable() {
			@Override
			public void run() {
				syncConnection(serverHost, serverPort);
			}
		});
	}

	/**
	 * 发送数据
	 * 
	 * @param body
	 *            SentBody结构体数据{@link AbstractRequest}
	 */
	public void send(final Entity message) {

		executor.execute(new Runnable() {
			@Override
			public void run() {

				if (session == null || !session.isConnected()) {
					syncConnection(messageServerInfo.getString(
							MESSAGE_SERVIER_HOST, null), messageServerInfo
							.getInt(MESSAGE_SERVIER_PORT, 1234));
				}

				if (session != null) {
					WriteFuture wf = session.write(message);

					// 消息发送超时 10秒
					wf.awaitUninterruptibly(10, TimeUnit.SECONDS);

					if (!wf.isWritten()) {
						android.os.Message msg = new android.os.Message();
						msg.getData().putSerializable("message", message);
						msg.getData().putSerializable("e", wf.getException());
						messageSentFailedHandler.sendMessage(msg);
					}
				}
			}
		});
	}

	public void destroy() {
		if (session != null) {
			session.close(false);
			session.removeAttribute("account");
		}

		if (connector != null && !connector.isDisposed()) {
			connector.dispose();
		}
		executor.shutdown();
		manager = null;
	}

	/**
	 * 是否断开连接
	 * 
	 * @return
	 */
	public boolean isConnected() {
		if (session == null || connector == null) {
			return false;
		}
		return session.isConnected() && session.getRemoteAddress() != null
				&& connectFuture.isConnected();
	}

	/**
	 * Handler数据处理
	 */
	IoHandlerAdapter iohandler = new IoHandlerAdapter() {

		@Override
		public void sessionCreated(IoSession session) throws Exception {

			System.out.println("******************连接服务器成功:"
					+ session.getLocalAddress());
			sessionCreatedHandler.sendEmptyMessage(0);
		}

		@Override
		public void sessionOpened(IoSession session) throws Exception {
			session.getConfig().setBothIdleTime(180);
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception {

			System.out.println("******************与服务器断开连接:"
					+ session.getLocalAddress());
			if (MessageConnectorManager.this.session.getId() == session.getId()) {
				sessionClosedHandler.sendEmptyMessage(0);
			}

		}

		@Override
		public void sessionIdle(IoSession session, IdleStatus status)
				throws Exception {

			System.out.println("******************与服务器连接空闲:"
					+ session.getLocalAddress());
		}

		@Override
		public void exceptionCaught(IoSession session, Throwable cause)
				throws Exception {

			cause.printStackTrace();
			android.os.Message msg = new android.os.Message();
			msg.getData().putSerializable("e", cause);
			exceptionCaughtHandler.sendMessage(msg);
		}

		@Override
		public void messageReceived(IoSession session, Object message)
				throws Exception {

			try {
				if (isInBackground()) {
					sendBroadcast(message);
				}

				android.os.Message msg = new android.os.Message();
				msg.getData().putSerializable("replyMessage", (Serializable) message);
				replyReceivedHandler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void messageSent(IoSession session, Object message)
				throws Exception {

			android.os.Message msg = new android.os.Message();
			msg.getData().putSerializable("message", (Serializable) message);
			messageSentSuccessfulHandler.sendMessage(msg);
		}
	};

	@Override
	public void createMessageReceivedHandler() {
		// 消息接收处理
		super.messageReceivedHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				Object message = (Object) msg.getData().getSerializable(
						"message");
				if (message instanceof AbstractResponse) {
					AsyncCallBack _asyncCallBack = mAsyncCallBackMap
							.get(((AbstractResponse) message).getMsgId());
					if (_asyncCallBack != null) {
						// 直接对应回调处理
						_asyncCallBack.onMessageReceived(message);
					} else {
						for (OnMessageListener listener : messageListeners) {
							listener.onMessageReceived(message);
						}
					}
				}
			}
		};
	}

	@Override
	public void createReplyReceivedHandler() {
		super.replyReceivedHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				Object reply = msg.getData().getSerializable("replyMessage");
				if (reply instanceof AbstractResponse) {
					AbstractResponse abReply = (AbstractResponse) reply;
					AsyncCallBack _asyncCallBack = mAsyncCallBackMap
							.get(abReply.getMsgId());
					if (_asyncCallBack != null) {
						// 直接对应回调处理
						if(abReply.getCode().equals(Constants.ReturnCode.CODE_200)){
							_asyncCallBack.onReplyReceived_OK(reply);
						}else{
							_asyncCallBack.onReplyReceived_ERROR(reply);
						}
					} else {
						for (OnMessageListener listener : messageListeners) {
							listener.onReplyReceived(reply);
						}
					}
				}
			}
		};
	}

	/**
	 * 消息发送成功
	 */
	@Override
	public void createMessageSentSuccessfulHandler() {
		super.messageSentSuccessfulHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				Serializable message = (Serializable) msg.getData()
						.getSerializable("message");
				if (message instanceof AbstractRequest) {
					AsyncCallBack _asyncCallBack = mAsyncCallBackMap
							.get(((AbstractRequest) message).getMsgId());
					if (_asyncCallBack != null) {
						// 直接对应回调处理
						_asyncCallBack.onMessageSentSuccessful(message);
					} else {
						for (OnMessageListener listener : messageListeners) {
							listener.onSentSuccessful(message);
						}
					}
				}
			}
		};
	}

	@Override
	public void createExceptionCaughtHandler() {
		exceptionCaughtHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				Throwable e = (Throwable) msg.getData().getSerializable("e");
				for (OnMessageListener listener : messageListeners) {
					listener.onExceptionCaught(e);
				}
			}
		};
	}

	/**
	 * Session创建关闭
	 */
	@Override
	public void createSessionClosedHandler() {
		super.sessionClosedHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				for (OnMessageListener listener : messageListeners) {
					listener.onConnectionClosed();
				}

			}
		};
	}

	/**
	 * Session创建失败
	 */
	@Override
	public void createSessionCreatedHandler() {
		super.sessionCreatedHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				for (OnMessageListener listener : messageListeners) {
					listener.onConnectionSuccessful();
				}
			}
		};
	}

	/**
	 * 消息发送失败
	 */
	@Override
	public void createMessageSentFailedHandler() {
		super.messageSentFailedHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				Exception e = (Exception) msg.getData().getSerializable("e");
				Object message = msg.getData().getSerializable("message");
				if (message instanceof AbstractRequest) {
					AsyncCallBack _asyncCallBack = mAsyncCallBackMap
							.get(((AbstractRequest) message).getMsgId());
					if (_asyncCallBack != null) {
						_asyncCallBack.onMessageSentFailed(e, message);
					} else {
						for (OnMessageListener listener : messageListeners) {
							listener.onSentFailed(e, message);
						}
					}
				}
			}
		};
	}

	protected void onNetworkChanged(NetworkInfo info) {
		for (OnMessageListener listener : messageListeners) {
			listener.onNetworkChanged(info);
		}
	}

	@Override
	public void createConnectionFailedHandler() {
		super.connectionFailedHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				Exception e = (Exception) msg.getData().getSerializable("e");
				for (OnMessageListener listener : messageListeners) {
					listener.onConnectionFailed(e);
				}
			}
		};
	};

	/**
	 * 是否在后台进行
	 * 
	 * @return
	 */
	private boolean isInBackground() {
		List<RunningTaskInfo> tasksInfo = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			if (context.getPackageName().equals(
					tasksInfo.get(0).topActivity.getPackageName())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 网络状况是否开启
	 * 
	 * @return
	 */
	public boolean netWorkAvailable() {
		try {
			ConnectivityManager nw = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = nw.getActiveNetworkInfo();
			return networkInfo != null;
		} catch (Exception e) {
			DebugUtils.error("netWorkAvailable", e);
		}

		return false;
	}

	/**
	 * 服务器Host
	 * 
	 * @return
	 */
	public String getServerHost() {
		return messageServerInfo.getString(MESSAGE_SERVIER_HOST, null);
	}

	/**
	 * 服务器端口
	 * 
	 * @return
	 */
	public int getServerPort() {
		return messageServerInfo.getInt(MESSAGE_SERVIER_PORT, 0);
	}

	/**
	 * 添加回调对象
	 * 
	 * @param msgId
	 *            消息Id
	 * @param asyncCallBackMap
	 *            回调对象
	 */
	public void putAsyncCallBack(long msgId, AsyncCallBack asyncCallBack) {
		if (!mAsyncCallBackMap.containsKey(msgId)) {
			mAsyncCallBackMap.put(msgId, asyncCallBack);
		}
	}

	/**
	 * 移除msgId的回调
	 * 
	 * @param msgId
	 *            消息Id
	 */
	public void removeAsyncCallBack(long msgId) {
		if (mAsyncCallBackMap.containsKey(msgId)) {
			mAsyncCallBackMap.remove(msgId);
		}
	}

	/**
	 * 发送广播
	 * 
	 * @param message
	 *            消息
	 */
	private void sendBroadcast(Object message) {
		Intent intent = new Intent();
		intent.setAction(NEW_MSG_ACTION);
		intent.putExtra("replyMessage", (Serializable) message);
		context.sendBroadcast(intent);
	}
	
	/**
	 * 设置CookieValue
	 * @param cookieValue
	 */
	public void setCookieValue(String cookieValue){
		this.mCookieValue = cookieValue;
	}
	
	/**
	 * 取得cookieValue
	 * @return
	 */
	public String getCookieValue(){
		this.mCookieValue="93af05e5f6cb83a1";
		return this.mCookieValue;
		//return this.mCookieValue;
	}
	
	/**
	 * 取得Device Id
	 * @return
	 */
	public String getDeviceId(){
		return this.mDeviceId;
	}

}