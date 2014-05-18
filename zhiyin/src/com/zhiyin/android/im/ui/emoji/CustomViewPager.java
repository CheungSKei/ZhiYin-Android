package com.zhiyin.android.im.ui.emoji;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ViewPager¿ØÖÆ»®¶¯
 * @version 1.0.0
 * @date 2014-05-18
 * @author S.Kei.Cheung
 */
public class CustomViewPager extends ViewPager{

	private boolean isCustomViewPager = false;
	
	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context paramContext, AttributeSet paramAttributeSet)
	{
		super(paramContext, paramAttributeSet);
	}
	
	public final void setEmojiViewPager(boolean paramBoolean)
	{
		this.isCustomViewPager = paramBoolean;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
	{
		if (!this.isCustomViewPager) {
			return super.onInterceptTouchEvent(paramMotionEvent);
		}
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent paramMotionEvent)
	{
		if (!this.isCustomViewPager) {
			return super.onTouchEvent(paramMotionEvent);
		}
		return false;
	}
}
