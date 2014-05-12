package com.zhiyin.android.im.ui.login;


import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Notification;
import android.app.NotificationManager;

import com.zhiyin.android.constant.Constants;
import com.zhiyin.android.im.R;
import com.zhiyin.android.im.booter.ZYNotificationManager;
import com.zhiyin.android.im.task.socket.AsyncCallBack;
import com.zhiyin.android.im.task.socket.MessageSocketTaskImpl;
import com.zhiyin.android.im.ui.BaseActivity;
import com.zhiyin.android.network.socket.MessageConnectorManager;
import com.zhiyin.android.network.socket.MessageConnectorService;
import com.zhiyin.android.platformtools.NotificationTools;
import com.zhiyin.android.util.StringUtils;

/**
 * 欢迎页.
 * 
 * @version 1.2.0
 * @date 2014-01-28
 * @author S.Kei.Cheung
 */
public class WelcomeActivity extends BaseActivity{

	private Button btn;
	private Button mLinkSocketbtn;
	private Button mNewPagebtn;
	private TextView msgContent;
	private EditText mSendContent;
	private ZYNotificationManager zyNotificationManager = new ZYNotificationManager(this);
	// TODO:测试数据
	private int i = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// /---------------------
		init();
		// /---------------------
		super.onCreate(savedInstanceState);
		System.setProperty("java.net.preferIPv6Addresses", "false");

		setContentView(R.layout.welcome_activity_layout);
		btn = (Button) findViewById(R.id.sendBtn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!StringUtils.isEmpty(mSendContent.getText().toString())){
					// AbstractRequest内容
					// FileRequest文件内容
//					FileRequest fileRequest = new FileRequest();
//					Map<String,Object> mapData = new HashMap<String,Object>();
//					mapData.put("filecount", 1);
//					mapData.put("msg", mSendContent.getText().toString());
//					fileRequest.setCmd(Constants.IMCmd.IM_VOICE_CMD);
//					fileRequest.setData(mapData);
//					MessageConnectorManager.getManager().send(fileRequest);
					Map<String, Object> mapData = new HashMap<String, Object>();
					mapData.put("username", "wenhsh");//假设其对应的userId为1000
					mapData.put("password", "123456");
					mapData.put("confirmPassword", "123456");
					new MessageSocketTaskImpl(new AsyncCallBack() {
						
						@Override
						public void onReplyReceived_OK(Object replyMessage) {
							
						}
						
						@Override
						public void onReplyReceived_ERROR(Object replyMessage) {
							
						}
						
						@Override
						public void onMessageSentSuccessful(Object message) {
							
						}
						
						@Override
						public void onMessageSentFailed(Exception e, Object message) {
							
						}
						
						@Override
						public void onMessageReceived(Object receivedMessage) {
							
						}
					}).commit(1001, mapData);
					
				}
			}
		});
		
		mLinkSocketbtn = (Button) findViewById(R.id.linkSocket);
		mLinkSocketbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				//连接服务端
//				Intent serviceIntent  = new Intent(WelcomeActivity.this, MessageConnectorService.class);
//				serviceIntent.putExtra(MessageConnectorManager.MESSAGE_SERVIER_HOST, Constants.MESSAGE_SERVER_HOST);
//				serviceIntent.putExtra(MessageConnectorManager.MESSAGE_SERVIER_PORT, Constants.MESSAGE_SERVER_PORT);
//				startService(serviceIntent);
				
				// TODO:测试Notification
				i++;
				zyNotificationManager.startNotification(getResources().getQuantityString(R.plurals.notification_fmt_multi_msg_and_one_talker,1,i),"2",0);
			}
		});
		
		mNewPagebtn = (Button) findViewById(R.id.newPageBtn);
		mNewPagebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent =  new Intent(WelcomeActivity.this, WelcomeActivity.class);
//				startActivity(intent);
				
				// TODO:测试Notification取消
				zyNotificationManager.cancelNotification();
				Object[] arrayOfObject = new Object[2];
			    arrayOfObject[0] = Integer.valueOf(2);
			    arrayOfObject[1] = Integer.valueOf(5);
				zyNotificationManager.startNotification(R.drawable.ic_launcher, Notification.DEFAULT_ALL, null, false, "测试", "波波会", getResources().getQuantityString(R.plurals.notification_fmt_multi_msg_and_talker,1,arrayOfObject), null);
			}
		});
		
		msgContent = (TextView) findViewById(R.id.msgTxt);
		mSendContent = (EditText) findViewById(R.id.sendContent);
		
		
		zyNotificationManager.startNotification(getResources().getQuantityString(R.plurals.notification_fmt_multi_msg_and_one_talker,1,1),"2",0);
	}
	
	/**
	 * TODO:更新消息
	 * @param msg
	 */
	protected final void sentMessage(String msg){
		msgContent.append(msg + "\r\n");
		final int maxY = (msgContent.getLineCount() * msgContent.getLineHeight() + msgContent.getPaddingTop() + msgContent.getPaddingBottom()) - msgContent.getHeight();
		msgContent.scrollTo(0, maxY);
		msgContent.invalidate();
	}
	
	public void onResume() {
	    super.onResume();
	
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	public void onBackPressed() {
		stopService(new Intent(this, MessageConnectorService.class));
		this.finish();
	}

	// 判断版本格式,如果版本 > 2.3,就是用相应的程序进行处理,以便影响访问网络
	@TargetApi(9)
	private static void init() {
		String strVer = android.os.Build.VERSION.RELEASE; // 获得当前系统版本
		strVer = strVer.substring(0, 3).trim(); // 截取前3个字符 2.3.3转换成2.3
		float fv = Float.valueOf(strVer);
		if (fv > 2.3) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
					.build());
		}
	}
	
	@Override
	public void finish() {
		super.finish();
		
	}
 
	@Override
	public void onRestart() {
		super.onRestart();
	}

	@Override
	public void onClick(View arg0) {
		
	}
}
