package com.zhiyin.android.im.ui.emoji;

import com.zhiyin.android.util.DebugUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Emoji ViewPager
 * 
 * @version 1.0.0
 * @date 2014-05-21
 * @author S.Kei.Cheung
 */
public class EmojiPanelVP extends CustomViewPager {

	public EmojiPanelVP(Context paramContext)
	{
		super(paramContext);
		setOverScrollMode();
	}
	
	public EmojiPanelVP(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		setOverScrollMode();
	}

	@SuppressLint("NewApi")
	private void setOverScrollMode()
	{
		if (Build.VERSION.SDK_INT >= 9)
			setOverScrollMode(OVER_SCROLL_NEVER);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		DebugUtils.warn("MicroMsg.EmojiPanel.VP", "w: %d, h: %d, oldw: %d, oldh: %d", new Integer[]{w,h,oldw,oldh});
	}
	
}
