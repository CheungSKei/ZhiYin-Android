package com.zhiyin.android.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * �������
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
	 * ���ݲ�ѯ����
	 * 
	 * @param params
	 *            ContentValues����(��������) </br> false,true --distinct; </br>
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
	 * Sql���ݲ�ѯ����
	 * 
	 * @param sql
	 *            ��ѯ��� </br> "select * from person where personid=?" </br>
	 *            "select * from person limit ?,?" </br>
	 *            "select count(*) from person" </br>
	 * @param params
	 *            �������� </br> new String[]{id.toString()} </br> new String[]
	 *            {offset.toString(), maxResult.toString()} </br> null </br>
	 * @return
	 */
	protected Cursor getRawQuery(String sql, String[] params) {
		return db.rawQuery(sql, params);
	}
	
	/**
	 * ����ɾ��
	 * @param table_name	����
	 * @param params		��������
	 * 			params	 --�ṩ���²���</br> 
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
	 * ����ɾ��
	 * @param table_name	����
	 * @param values		���µ�����
	 * @param params		��������
	 * 			params	 --�ṩ���²���</br> 
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
	 * ��ѯ����ContentValues,�ڲ�����Query��ѯ����Ĭ��ֵ
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
		 * ��ȡֵ.
		 * 
		 * @param key
		 *            ������
		 * @return
		 */
		public Object get(String key) {
			return contentValues.get(key);
		}

		/**
		 * ��Ӳ�����.
		 * 
		 * @param key
		 *            ������
		 * @param value
		 *            ����ֵ
		 */
		public void put(String key, String value) {
			contentValues.put(key, value);
		}

		/**
		 * �޸�distinctֵ
		 * 
		 * @param value
		 *            ����ֵ
		 */
		public void putDistinct(boolean value) {
			contentValues.put(DISTINCT, value);
		}

		/**
		 * ����ContentValues����
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
