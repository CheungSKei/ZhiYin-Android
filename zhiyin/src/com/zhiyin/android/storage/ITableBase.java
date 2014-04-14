package com.zhiyin.android.storage;

/**
 * 数据表接口
 * 
 * @version 1.0.0
 * @date 2014-3-30
 * @author S.Kei.Cheung
 */
public abstract interface ITableBase {

	/**
	 * 创建表/索引表
	 * @return 返回表结构和索引表
	 */
	public abstract String[] createTableSQL();
	
	/**
	 * 取得个性Sql
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract String[] getAlterSQL(int paramInt1, int paramInt2);
	/**
	 *  取得sqlite
	 * @return
	 */
	public abstract SqliteHelper getHelper();
	/**
	 * 取得表名
	 * @return
	 */
	public abstract String tableName();
	/**
	 * 表版本号
	 * @return
	 */
	public abstract int tableVersion();
	
}
