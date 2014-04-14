package com.zhiyin.android.storage.dbimpl;

import android.content.ContentValues;
import android.database.Cursor;

import com.zhiyin.android.storage.BaseTable;

/**
 * 消息表
 * 
 * @version 1.0.0
 * @date 2014-3-30
 * @author S.Kei.Cheung
 */
public class MessageTableImpl extends BaseTable {

	// Message表名
	private static final String TABLE_NAME = "message";
	// 表版本号
	private static final int TABLE_VERSION = 1;
	// 创建表和索引
	private static final String[] arrayOfString = {
    "CREATE TABLE IF NOT EXISTS message ( msgId INTEGER PRIMARY KEY, msgSvrId INTEGER , type INT, status INT, isSend INT, isShowTimer INTEGER, createTime INTEGER, talker TEXT, content TEXT, imgPath TEXT, reserved TEXT, lvbuffer BLOB )",
    "CREATE INDEX IF NOT EXISTS  messageIdIndex ON message ( msgId )",
    "CREATE INDEX IF NOT EXISTS  messageSvrIdIndex ON message ( msgSvrId )",
    "CREATE INDEX IF NOT EXISTS  messageTalkerIndex ON message ( talker )",
    "CREATE INDEX IF NOT EXISTS  messageTalkerStatusIndex ON message ( talker,status )",
    "CREATE INDEX IF NOT EXISTS  messageCreateTimeIndex ON message ( createTime )",
    "CREATE INDEX IF NOT EXISTS  messageCreateTaklerTimeIndex ON message ( talker,createTime )",
    "CREATE INDEX IF NOT EXISTS  messageSendCreateTimeIndex ON message ( status,isSend,createTime )",
    "CREATE INDEX IF NOT EXISTS  messageTalkerCreateTimeIsSendIndex ON message ( talker,isSend,createTime )"};

	@Override
	public Cursor dbGetQuery(QueryContentValues params) {
		Cursor cursor = null;
		dbReadableOpen();
		cursor = getQuery(TABLE_NAME, params);
		if (db != null)
			dbClose();
		
		return cursor;
	}
	
	@Override
	public Cursor dbGetRawQuery(String sql, String[] params) {
		Cursor cursor = null;
		dbReadableOpen();
		cursor = getRawQuery(sql, params);
		if (db != null)
			dbClose();
		
		return cursor;
	}

	@Override
	public void dbInsert(ContentValues... params) {
		dbWriteableOpen();
		
		for(ContentValues cv:params)
			db.insert(TABLE_NAME, null, cv);
		if (db != null)
			dbClose();
	}

	@Override
	public int dbDelete(QueryContentValues params) {
		dbWriteableOpen();
		
		int count = delete(TABLE_NAME,params);
		if (db != null)
			dbClose();
		return count;
	}

	@Override
	public int dbUpdate(ContentValues value,QueryContentValues params) {
		dbWriteableOpen();
		int count = update(TABLE_NAME,value,params);
		if (db != null)
			dbClose();
		return count;
	}

	@Override
	public int dbDeleteAll() {
		dbWriteableOpen();
		int num = db.delete(TABLE_NAME, null, null);
		
		if (db != null) {
			dbClose();
		}
		return num;
	}

	@Override
	public int dbGetCount(QueryContentValues params) {
		dbReadableOpen();
		Cursor cursor = getQuery(TABLE_NAME, params);;
		int num = cursor.getCount();
		if (db != null)
			dbClose();
		
		return num;
	}

	@Override
	public String[] createTableSQL() {
		
		return arrayOfString;
	}

	@Override
	public String[] getAlterSQL(int paramInt1, int paramInt2) {
		
		return null;
	}

	@Override
	public String tableName() {
		
		return TABLE_NAME;
	}

	@Override
	public int tableVersion() {
		
		return TABLE_VERSION;
	}

}
