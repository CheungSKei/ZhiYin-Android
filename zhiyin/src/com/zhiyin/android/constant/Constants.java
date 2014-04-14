package com.zhiyin.android.constant;

/**
 * 固定常数
 * 
 * @version 1.0.0
 * @date 2014-01-29
 * @author S.Kei.Cheueng
 */
public class Constants {
	// 线程池分配
	public static final int DEFAULT_CORE_POOL_SIZE = 5;
	public static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
	public static final int DEFAULT_KEEP_ALIVE = 1;
	
	// "知音"默认文件夹
	public static final String DEFAULT_IMAGE_FOLDER_NAME = "Zhiyin";
	// 通信结束符
	public static byte  MESSAGE_SEPARATE='\b';
	//缓冲区默认大小
	public static final int DEFAULT_BUFFER_SIZE = 320;
	
	/** 30秒后超时 */
	public static final int IDELTIMEOUT = 30;
	/** 15秒发送一次心跳包 */
	public static final int HEARTBEATRATE = 15;
	/** 心跳包内容 */
	public static final byte HEARTBEATREQUEST = 0x10;
	public static final byte HEARTBEATRESPONSE = 0x11;
	
	/**
	 * 服务器返回状态
	 */
	public static class ReturnCode{
		
		public static final String CODE_404 = "404";
		
		public static final String CODE_403 = "403";
		
		public static final String CODE_405 = "405";
		
		public static final String CODE_200 = "200";
		
		public static final String CODE_206 = "206";
		
		public static final String CODE_500 = "500";
		
		public static final String CODE_USER_OFFLINE = "9992";//用户已离线
		
		public static final String CODE_JSON_FORMAT_ERROR = "9993";//data json格式错误
		
		public static final String CODE_DEVICE_ID_IS_EMPTY = "9994";//设置id为空
		
		public static final String CODE_CMD_NOT_VALID = "9995";//命令非法
		
		public static final String CODE_MESSAGE_NOT_VALID = "9996";//数据包非法
		
		public static final String CODE_USER_INVALID = "9997";//用户非法
		
		public static final String CODE_USERID_EMPTY = "9998";//userId不能为空状态码
		
		public static final String CODE_IN_BLACKLIST = "9999";//黑名单状态码
		
		public static final String MSG_IN_BLACKLIST = "用户id在黑名单中";
	}
	
	public static int  DEFAULT_MESSAGE_ORDER=1;
	
	/**
	 * 为 服务端处理对应的handlers
	 */
   public static class RequestKey{

		public static String CLIENT_AUTH ="client_auth";
		
		public static String CLIENT_HEARTBEAT="client_heartbeat";
		
		public static String CLIENT_LOGOUT ="client_logout";
		
		public static String CLIENT_DIY ="client_diy";
		
		public static String CLIENT_OFFLINE_MESSAGE ="client_get_offline_message";
   }
   
   /**
    * 操作指令
    */
   public static class IMCmd{
	   
	   public static final String IM_LOGIN_CMD ="imLogin";	//im登录命令
	   
	   public static final String IM_LOGOUT_CMD ="imLogout";	//im登出命令
	   
	   public static final String IM_AUTH_CMD ="imAuth";	//im鉴权命令
	   
	   public static final String IM_TEXT_CMD ="imText";	//发送文本消息命令
	   
	   public static final String IM_VOICE_CMD ="imVoice";	//发送声音消息命令
	   
	   public static final String IM_VIDEO_CMD ="imVideo";	//发送视频消息命令
	   
	   public static final String IM_IMAGE_CMD ="imImage";	//发送图片消息命令
	   
	   public static final String IM_STATUS_CHANGE_CMD ="imStatusChange";	//用户状态改变命令
	   
	   public static final String IM_RESPONSE_CMD ="imResponse";	//服务端状态回复回复命令
	   
	   public static final String IM_HEARTBEAT_CMD ="imHeartBeat";	//发送心跳命令

   }
   // cookie key
   public static final String COOKIE_VALUE_KEY = "cookieValue";
   // 设备ID
   public static final String DEVICE_ID_KEY = "deviceId";
   // client
   public final static String FROM_CLIENT = "client";
   // server
   public final static String FROM_SERVER = "server";
   // 心跳请求
   public final static String REQUEST_TYPE = "request";
   // 心跳回复
   public final static String RESPONSE_TYPE = "response";
   
   // 服务器连接端口
   public static final int MESSAGE_SERVER_PORT = 58891;
   // 服务器地址
   // public static final String MESSAGE_SERVER_HOST = "10.10.121.192";
   // public static final String MESSAGE_SERVER_HOST = "192.168.100.6";
   public static final String MESSAGE_SERVER_HOST = "10.10.122.132";
   
}
