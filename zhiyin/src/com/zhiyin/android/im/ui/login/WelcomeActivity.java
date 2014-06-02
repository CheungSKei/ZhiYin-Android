package com.zhiyin.android.im.ui.login;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhiyin.android.im.R;
import com.zhiyin.android.im.ui.BaseActivity;
import com.zhiyin.android.network.socket.MessageConnectorService;

/**
 * ��ӭҳ.
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// /---------------------
		init();
		// /---------------------
		super.onCreate(savedInstanceState);
		System.setProperty("java.net.preferIPv6Addresses", "false");

		setContentView(R.layout.chatting);
//		setContentView(R.layout.welcome_activity_layout);
//		btn = (Button) findViewById(R.id.sendBtn);
//		btn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(!StringUtils.isEmpty(mSendContent.getText().toString())){
					// AbstractRequest����
					// FileRequest�ļ�����
//					FileRequest fileRequest = new FileRequest();
//					Map<String,Object> mapData = new HashMap<String,Object>();
//					mapData.put("filecount", 1);
//					mapData.put("msg", mSendContent.getText().toString());
//					fileRequest.setCmd(Constants.IMCmd.IM_VOICE_CMD);
//					fileRequest.setData(mapData);
//					MessageConnectorManager.getManager().send(fileRequest);
//					Map<String, Object> mapData = new HashMap<String, Object>();
//					mapData.put("username", "wenhsh");//�������Ӧ��userIdΪ1000
//					mapData.put("password", "123456");
//					mapData.put("confirmPassword", "123456");
//					new MessageSocketTaskImpl(new AsyncCallBack() {
//						
//						@Override
//						public void onReplyReceived_OK(Object replyMessage) {
//							
//						}
//						
//						@Override
//						public void onReplyReceived_ERROR(Object replyMessage) {
//							
//						}
//						
//						@Override
//						public void onMessageSentSuccessful(Object message) {
//							
//						}
//						
//						@Override
//						public void onMessageSentFailed(Exception e, Object message) {
//							
//						}
//						
//						@Override
//						public void onMessageReceived(Object receivedMessage) {
//							
//						}
//					}).commit(1001, mapData);
//					
//				}
//			}
//		});
//		
//		mLinkSocketbtn = (Button) findViewById(R.id.linkSocket);
//		mLinkSocketbtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//���ӷ����
//				Intent serviceIntent  = new Intent(WelcomeActivity.this, MessageConnectorService.class);
//				serviceIntent.putExtra(MessageConnectorManager.MESSAGE_SERVIER_HOST, Constants.MESSAGE_SERVER_HOST);
//				serviceIntent.putExtra(MessageConnectorManager.MESSAGE_SERVIER_PORT, Constants.MESSAGE_SERVER_PORT);
//				startService(serviceIntent);
//			}
//		});
//		
//		mNewPagebtn = (Button) findViewById(R.id.newPageBtn);
//		mNewPagebtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent =  new Intent(WelcomeActivity.this, WelcomeActivity.class);
//				startActivity(intent);
//			}
//		});
//		
//		msgContent = (TextView) findViewById(R.id.msgTxt);
//		mSendContent = (EditText) findViewById(R.id.sendContent);
		
	}
	
	/**
	 * TODO:������Ϣ
	 * @param msg
	 */
	protected final void sentMessage(String msg){
		msgContent.append(msg + "\r\n");
		final int maxY = (msgContent.getLineCount()
				* msgContent.getLineHeight() + msgContent.getPaddingTop() + msgContent
					.getPaddingBottom()) - msgContent.getHeight();
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

	// �жϰ汾��ʽ,����汾 > 2.3,��������Ӧ�ĳ�����д���,�Ա�Ӱ���������
	@TargetApi(9)
	private static void init() {
		String strVer = android.os.Build.VERSION.RELEASE; // ��õ�ǰϵͳ�汾
		strVer = strVer.substring(0, 3).trim(); // ��ȡǰ3���ַ� 2.3.3ת����2.3
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
