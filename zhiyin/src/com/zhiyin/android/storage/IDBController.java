package com.zhiyin.android.storage;

import com.zhiyin.android.storage.BaseTable.QueryContentValues;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * ���ݿ�����ӿ�
 * 
 * @version 1.0.0
 * @date 2014-3-28
 * @author S.Kei.Cheung
 */
public interface IDBController {

	/**
	 * ���ݲ�ѯ����
	 * 
	 * @param params
	 *            QueryContentValues����(��������) </br> false,true --distinct; </br>
	 *            "id,name" --columns</br> "id>? and name<>?"
	 *            --selection</br> "0,roiding.com" --selectionArgs</br> null
	 *            --groupBy</br> null --having</br> "id desc" --orderBy</br> "1"
	 *            --limit</br>
	 * @return
	 */
	public Cursor dbGetQuery(QueryContentValues params);
	
	/**
	 * dbGetRawQuery Sql���ݲ�ѯ����
	 * @param sql ��ѯ��� </br>
	 * 				"select * from person where personid=?" </br>
	 * 				"select * from person limit ?,?" </br>
	 * 				"select count(*) from person" </br>
	 * @param params �������� </br>
	 * 				new String[]{id.toString()} </br>
	 * 				new String[] {offset.toString(), maxResult.toString()} </br>
	 * 				null </br>
	 * @return
	 */
	public Cursor dbGetRawQuery(String sql, String[] params);
	
	/**
	 * dbSave ��������
	 * @param params TaskParams����
	 */
	public void dbInsert(ContentValues... params);
	/**
	 * dbDelete ɾ������
	 * @param table_name	����
	 * @param params		��������
	 * 			params	 --�ṩ���²���</br> 
	 * 			"id>? and name<>?" --selection</br> 
	 * 			"0,roiding.com" --selectionArgs</br>
	 * @return
	 */
	public int dbDelete(QueryContentValues params);
	/**
	 * �޸�����
	 * @param value			���µ�����
	 * @param params		��������
	 * 			params	 --�ṩ���²���</br> 
	 * 			"id>? and name<>?" --selection</br> 
	 * 			"0,roiding.com" --selectionArgs</br>
	 * @return
	 */
	public int dbUpdate(ContentValues value,QueryContentValues params);
	/**
	 * dbDeleteAll ɾ����������
	 * @return ɾ������
	 */
	public int dbDeleteAll();
	/**
	 * dbGetCount ��������
	 * @return ����������
	 */
	public int dbGetCount(QueryContentValues params);
	
	/**
	 * д��
	 */
	public void dbWriteableOpen();
	
	/**
	 * ����
	 */
	public void dbReadableOpen();
	
	/**
	 * �ر����ݿ�
	 */
	public void dbClose();
}
