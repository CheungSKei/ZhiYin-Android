package com.zhiyin.android.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;

/**
 * 数据库操作类
 * 
 * @version 1.0.0
 * @date 2014-3-30
 * @author S.Kei.Cheung
 */
public abstract class SqliteHelper extends SQLiteOpenHelper {

	public SqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	/**
	 * 取得所有表类
	 * @return
	 */
	public abstract Class<ITableBase>[] getTables();
	/**
	 * 数据库版本
	 * @return
	 */
	public abstract int getDBVersion();
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		
		super.onOpen(db);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	/**
	 * 创建表
	 * @param paramSQLiteDatabase
	 */
	private void createTable(SQLiteDatabase paramSQLiteDatabase){
		Class<ITableBase>[] arrayOfClass = getTables();
	    int i = arrayOfClass.length;
	    for (int j = 0; j < i; j++)
	    {
	      Class<ITableBase> localClass = arrayOfClass[j];
	      String[] strCeateTab = null;
	      try
	      {
	    	  strCeateTab = ((ITableBase)localClass.newInstance()).createTableSQL();
	      }
	      catch (InstantiationException localInstantiationException)
	      {
	    	  strCeateTab = null;
	      }
	      catch (IllegalAccessException localIllegalAccessException)
	      {
	    	  strCeateTab = null;
	      }
	      if ((strCeateTab != null) && (strCeateTab.length > 0)) {
	    	  for(String str:strCeateTab){
	    		  if(str.length()>0)
	    			  paramSQLiteDatabase.execSQL(str);
	    	  }
	      }
	    }
	}
	
	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
	    SQLiteDatabase _localSQLiteDatabase = super.getWritableDatabase();
	    while (_localSQLiteDatabase.isDbLockedByCurrentThread() || _localSQLiteDatabase.isDbLockedByOtherThreads()) {
	      SystemClock.sleep(10L);
	    }
	    return _localSQLiteDatabase;
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		SQLiteDatabase _localSQLiteDatabase = super.getReadableDatabase();
	    while (_localSQLiteDatabase.isDbLockedByCurrentThread() || _localSQLiteDatabase.isDbLockedByOtherThreads()) {
	      SystemClock.sleep(10L);
	    }
	    return _localSQLiteDatabase;
	}

}
