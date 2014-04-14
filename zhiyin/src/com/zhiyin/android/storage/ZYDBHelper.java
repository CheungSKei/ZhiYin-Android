package com.zhiyin.android.storage;

import com.zhiyin.android.im.application.BaseApplication;
import com.zhiyin.android.storage.dbimpl.MessageTableImpl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * 数据库工具类
 * 
 * @version 1.0.0
 * @date 2014-3-28
 * @author S.Kei.Cheung
 */
public class ZYDBHelper extends SqliteHelper {

	private static final String DB_NAME = "zy_Database.db";
	private static final int DB_VERSION = 1;
	@SuppressWarnings("rawtypes")
	private static final Class[] TABLESS = {MessageTableImpl.class};
	private static SqliteHelper mInstance = null;
	
	private ZYDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, factory, version);
	}
	
	public synchronized static SqliteHelper getInstance()
	{
		if (mInstance == null)
			mInstance = new ZYDBHelper(BaseApplication.getContext(), DB_NAME, null, DB_VERSION);
		return mInstance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<ITableBase>[] getTables() {
		return TABLESS;
	}

	@Override
	public int getDBVersion() {
		
		return DB_VERSION;
	}

}
