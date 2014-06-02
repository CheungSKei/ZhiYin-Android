package com.zhiyin.android.im.ui.emoji;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 聊天底部Panel(Emoji)
 * 
 * @version 1.0.0
 * @date 2014-05-23
 * @author S.Kei.Cheung
 */
public abstract class ChatFooterPanel extends LinearLayout {

	// Emoji事件监听
	protected IEmojiChickedListener mEmojiChickedListener;
	
	public ChatFooterPanel(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	/**
	 * 设置点击Emoji事件监听
	 * @param iEmojiChickedListener
	 */
	public void setEmojiChickedListener(IEmojiChickedListener iEmojiChickedListener){
		this.mEmojiChickedListener = iEmojiChickedListener;
	}

	public abstract void dealOrientationChange();

	public abstract void reflesh();
	
	public abstract void hideCustomBtn();
	
	public abstract void setSendBtnEnabled(boolean enabled);
	
	public abstract void hideEmoji(boolean isHide);
	
	public abstract void hideSendButton(boolean isHide);
	
	public void destroy() {}

	public abstract void onPause();
	
	public abstract void onResume();
	
	public abstract void reset();
	
}
