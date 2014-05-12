package com.zhiyin.android.im.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

public class LauncherUIProxy extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Object[] arrayOfObject = new Object[1];
	    arrayOfObject[0] = Integer.valueOf(getTaskId());
	    getSupportActionBar().hide();
	    new Handler().post(new LauncherUIRunnable(this));
	}
	
}
