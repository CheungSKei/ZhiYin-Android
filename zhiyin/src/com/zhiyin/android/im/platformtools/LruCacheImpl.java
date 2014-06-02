package com.zhiyin.android.im.platformtools;

/**
 * LruCache 封装方法
 * 
 * @version 1.0.0
 * @date 2014-06-01
 * @author S.Kei.Cheung
 */
public class LruCacheImpl extends LruCache<Object,Object> {
	
	private OnEntryRemovedListener mOnEntryRemovedListener = null;
	
	public LruCacheImpl(int paramInt)
	{
		super(paramInt);
	}
	
	public LruCacheImpl(int paramInt, OnEntryRemovedListener onEntryRemovedListener)
	{
		super(paramInt);
		this.mOnEntryRemovedListener = onEntryRemovedListener;
	}
	
	/**
	 * 添除最旧一项
	 */
	public final void clear()
	{
		super.trimToSize(-1);
	}

	@Override
	protected Object create(Object key) {

		return super.create(key);
	}

	@Override
	protected void entryRemoved(boolean evicted, Object key, Object oldValue,Object newValue) {
		super.entryRemoved(evicted, key, oldValue, newValue);
		if ((this.mOnEntryRemovedListener != null) && (newValue == null)) {
			this.mOnEntryRemovedListener.entryRemoved(key, oldValue);
		}
	}
	
	/**
	 * 添加项
	 * @param key
	 * @param value
	 */
	public final void putItem(Object key, Object value)
	{
		if (value == null) {
			return;
		}
	
		put(key, value);
	}
	
	@Override
	public final void trimToSize(int maxSize) {
		super.trimToSize(maxSize);
	}
	
	/**
	 * 添除最旧一项
	 */
	public final void removeOneItem()
	{
		super.trimToSize(-1);
	}
	
	@Override
	protected int sizeOf(Object key, Object value) {
		return super.sizeOf(key, value);
	}
	
	public abstract interface OnEntryRemovedListener
	{
		public abstract void entryRemoved(Object key, Object value);
	}
}
