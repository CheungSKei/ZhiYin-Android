package com.zhiyin.android.im.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import com.zhiyin.android.im.ui.login.WelcomeActivity;

/**
 * Ê×Ò³Æô¶¯Runnable.
 * 
 * @version 1.0.0
 * @date 2014-05-05
 * @author S.Kei.Cheung
 */
public class LauncherUIRunnable implements Runnable {

	private ActionBarActivity mContext;
	
	LauncherUIRunnable(LauncherUIProxy paramLauncherUIProxy){
		mContext = paramLauncherUIProxy;
	}
	
	@Override
	public void run() {
		Intent localIntent = new Intent(this.mContext, WelcomeActivity.class);
	    if ("new_msg_nofification".equals(this.mContext.getIntent().getStringExtra("nofification_type")))
	    {
	      localIntent.putExtra("nofification_type", this.mContext.getIntent().getStringExtra("nofification_type"));
	      localIntent.putExtra("talkerCount", this.mContext.getIntent().getIntExtra("talkerCount", 0));
	      localIntent.putExtra("Intro_Is_Muti_Talker", this.mContext.getIntent().getBooleanExtra("Intro_Is_Muti_Talker", true));
	      localIntent.putExtra("Intro_Bottle_unread_count", this.mContext.getIntent().getIntExtra("Intro_Bottle_unread_count", 0));
	      localIntent.putExtra("Main_User", this.mContext.getIntent().getStringExtra("Main_User"));
	      localIntent.putExtra("MainUI_User_Last_Msg_Type", this.mContext.getIntent().getIntExtra("MainUI_User_Last_Msg_Type", 0));
	    }

		localIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		localIntent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
		this.mContext.startActivity(localIntent);
		this.mContext.finish();
		
		if (this.mContext.getIntent().getBooleanExtra("Intro_Notify", false))
		{
			localIntent.putExtra("Intro_Notify", this.mContext.getIntent().getBooleanExtra("Intro_Notify", false));
			localIntent.putExtra("Intro_Notify_User", this.mContext.getIntent().getStringExtra("Intro_Notify_User"));
		}
		  
		if ("update_nofification".equals(this.mContext.getIntent().getStringExtra("nofification_type")))
		{
			localIntent.putExtra("nofification_type", this.mContext.getIntent().getStringExtra("nofification_type"));
			localIntent.putExtra("show_update_dialog", this.mContext.getIntent().getBooleanExtra("show_update_dialog", false));
			localIntent.putExtra("update_type", this.mContext.getIntent().getIntExtra("update_type", 0));
		}
		  
		if ("pushcontent_notification".equals(this.mContext.getIntent().getStringExtra("nofification_type"))){
			localIntent.putExtra("nofification_type", this.mContext.getIntent().getStringExtra("nofification_type"));
			localIntent.putExtra("Intro_Is_Muti_Talker", this.mContext.getIntent().getBooleanExtra("Intro_Is_Muti_Talker", true));
			localIntent.putExtra("Main_FromUserName", this.mContext.getIntent().getStringExtra("Main_FromUserName"));
			localIntent.putExtra("MainUI_User_Last_Msg_Type", this.mContext.getIntent().getIntExtra("MainUI_User_Last_Msg_Type", 0));
		}
	}

}
