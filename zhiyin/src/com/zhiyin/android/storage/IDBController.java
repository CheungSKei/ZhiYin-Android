package com.zhiyin.android.storage;

import com.zhiyin.android.storage.BaseTable.QueryContentValues;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * 数据库操作接口
 * 
 * @version 1.0.0
 * @date 2014-3-28
 * @author S.Kei.Cheung
 */
public interface IDBController {

	/**
	 * 数据查询返回
	 * 
	 * @param params
	 *            QueryContentValues数组(参数类型) </br> false,true --distinct; </br>
	 *            "id,name" --columns</br> "id>? and name<>?"
	 *            --selection</br> "0,roiding.com" --selectionArgs</br> null
	 *            --groupBy</br> null --having</br> "id desc" --orderBy</br> "1"
	 *            --limit</br>
	 * @return
	 */
	public Cursor dbGetQuery(QueryContentValues params);
	
	/**
	 * dbGetRawQuery Sql数据查询返回
	 * @param sql 查询语句 </br>
	 * 				"select * from person where personid=?" </br>
	 * 				"select * from person limit ?,?" </br>
	 * 				"select count(*) from person" </br>
	 * @param params 参数变量 </br>
	 * 				new String[]{id.toString()} </br>
	 * 				new String[] {offset.toString(), maxResult.toString()} </br>
	 * 				null </br>
	 * @return
	 */
	public Cursor dbGetRawQuery(String sql, String[] params);
	
	/**
	 * dbSave 保存数据
	 * @param params TaskParams参数
	 */
	public void dbInsert(ContentValues... params);
	/**
	 * dbDelete 删除数据
	 * @param table_name	表名
	 * @param params		条件参数
	 * 			params	 --提供以下参数</br> 
	 * 			"id>? and name<>?" --selection</br> 
	 * 			"0,roiding.com" --selectionArgs</br>
	 * @return
	 */
	public int dbDelete(QueryContentValues params);
	/**
	 * 修改数据
	 * @param value			更新的数据
	 * @param params		条件参数
	 * 			params	 --提供以下参数</br> 
	 * 			"id>? and name<>?" --selection</br> 
	 * 			"0,roiding.com" --selectionArgs</br>
	 * @return
	 */
	public int dbUpdate(ContentValues value,QueryContentValues params);
	/**
	 * dbDeleteAll 删除所有数据
	 * @return 删除条数
	 */
	public int dbDeleteAll();
	/**
	 * dbGetCount 数据条数
	 * @return 数据总条数
	 */
	public int dbGetCount(QueryContentValues params);
	
	/**
	 * 写打开
	 */
	public void dbWriteableOpen();
	
	/**
	 * 读打开
	 */
	public void dbReadableOpen();
	
	/**
	 * 关闭数据库
	 */
	public void dbClose();
}
