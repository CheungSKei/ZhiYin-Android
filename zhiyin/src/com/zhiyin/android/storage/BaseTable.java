package com.zhiyin.android.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 表抽象类
 * 
 * @version 1.0.0
 * @date 2014-3-30
 * @author S.Kei.Cheung
 */
public abstract class BaseTable implements ITableBase, IDBController {

	protected SQLiteDatabase db;

	@Override
	public void dbWriteableOpen() {
		if (!db.isOpen())
			db = getHelper().getWritableDatabase();
	}

	@Override
	public void dbReadableOpen() {
		if (!db.isOpen())
			db = getHelper().getReadableDatabase();
	}

	@Override
	public void dbClose() {
		if (db.isOpen())
			db.close();
	}

	@Override
	public SqliteHelper getHelper() {
		return ZYDBHelper.getInstance();
	}

	/**
	 * 数据查询返回
	 * 
	 * @param params
	 *            ContentValues数组(参数类型) </br> false,true --distinct; </br>
	 *            "id,name" --columns</br> "id>? and name<>?"
	 *            --selection</br> "0,roiding.com" --selectionArgs</br> null
	 *            --groupBy</br> null --having</br> "id desc" --orderBy</br> "1"
	 *            --limit</br>
	 * @return
	 */
	protected Cursor getQuery(String table_name, QueryContentValues params) {
		Cursor cursor = null;
		cursor = db.query(params.getAsBoolean("distinct"),table_name,
				params.getAsString("columns") == null ? null : params.getAsString("columns").split(","),
				params.getAsString("selection"),params.getAsString("selectionArgs") == null ? null : params.getAsString("selectionArgs").split(","),
				params.getAsString("groupBy"), params.getAsString("having"),
				params.getAsString("orderBy"), params.getAsString("limit"));
		return cursor;
	}

	/**
	 * Sql数据查询返回
	 * 
	 * @param sql
	 *            查询语句 </br> "select * from person where personid=?" </br>
	 *            "select * from person limit ?,?" </br>
	 *            "select count(*) from person" </br>
	 * @param params
	 *            参数变量 </br> new String[]{id.toString()} </br> new String[]
	 *            {offset.toString(), maxResult.toString()} </br> null </br>
	 * @return
	 */
	protected Cursor getRawQuery(String sql, String[] params) {
		return db.rawQuery(sql, params);
	}
	
	/**
	 * 数据删除
	 * @param table_name	表名
	 * @param params		条件参数
	 * 			params	 --提供以下参数</br> 
	 * 			"id>? and name<>?" --selection</br> 
	 * 			"0,roiding.com" --selectionArgs</br>
	 * @return
	 */
	protected int delete(String table_name, QueryContentValues params){
		return db.delete(table_name, 
				params.getAsString("selection"), 
				params.getAsString("selectionArgs") == null ? null : params.getAsString("selectionArgs").split(","));
	}
	
	/**
	 * 数据删除
	 * @param table_name	表名
	 * @param values		更新的数据
	 * @param params		条件参数
	 * 			params	 --提供以下参数</br> 
	 * 			"id>? and name<>?" --selection</br> 
	 * 			"0,roiding.com" --selectionArgs</br>
	 * @return
	 */
	protected int update(String table_name,ContentValues values, QueryContentValues params){
		return db.update(table_name, values,
				params.getAsString("selection"), 
				params.getAsString("selectionArgs") == null ? null : params.getAsString("selectionArgs").split(","));
	}

	/**
	 * 查询类型ContentValues,内部设置Query查询参数默认值
	 * 
	 */
	public class QueryContentValues {
		private ContentValues contentValues = null;
		public static final String DISTINCT = "distinct";
		public static final String COLUMNS = "columns";
		public static final String SELECTION = "selection";
		public static final String SELECTIONARGS = "selectionArgs";
		public static final String GROUPBY = "groupBy";
		public static final String HAVING = "having";
		public static final String ORDERBY = "orderBy";
		public static final String LIMIT = "limit";

		public QueryContentValues() {
			contentValues = new ContentValues();
			contentValues.put(DISTINCT, false);
			contentValues.putNull(COLUMNS);
			contentValues.putNull(SELECTION);
			contentValues.putNull(SELECTIONARGS);
			contentValues.putNull(GROUPBY);
			contentValues.putNull(HAVING);
			contentValues.putNull(ORDERBY);
			contentValues.putNull(LIMIT);
		}

		/**
		 * 获取值.
		 * 
		 * @param key
		 *            参数名
		 * @return
		 */
		public Object get(String key) {
			return contentValues.get(key);
		}

		/**
		 * 添加参数项.
		 * 
		 * @param key
		 *            参数名
		 * @param value
		 *            参数值
		 */
		public void put(String key, String value) {
			contentValues.put(key, value);
		}

		/**
		 * 修改distinct值
		 * 
		 * @param value
		 *            参数值
		 */
		public void putDistinct(boolean value) {
			contentValues.put(DISTINCT, value);
		}

		/**
		 * 返回ContentValues对象
		 * 
		 * @return
		 */
		public ContentValues getContentValues() {
			return contentValues;
		}
		
		/**
		 * Gets a value and converts it to a Boolean.
		 * @param key
		 * @return
		 */
		public boolean getAsBoolean(String key){
			return contentValues.getAsBoolean(key);
		}
		
		/**
		 * Gets a value and converts it to a String.
		 * @param key
		 * @return
		 */
		public String getAsString(String key){
			return contentValues.getAsString(key);
		}
	}
}
