package com.zhiyin.android.storage;

/**
 * ���ݱ�ӿ�
 * 
 * @version 1.0.0
 * @date 2014-3-30
 * @author S.Kei.Cheung
 */
public abstract interface ITableBase {

	/**
	 * ������/������
	 * @return ���ر�ṹ��������
	 */
	public abstract String[] createTableSQL();
	
	/**
	 * ȡ�ø���Sql
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract String[] getAlterSQL(int paramInt1, int paramInt2);
	/**
	 *  ȡ��sqlite
	 * @return
	 */
	public abstract SqliteHelper getHelper();
	/**
	 * ȡ�ñ���
	 * @return
	 */
	public abstract String tableName();
	/**
	 * ��汾��
	 * @return
	 */
	public abstract int tableVersion();
	
}
