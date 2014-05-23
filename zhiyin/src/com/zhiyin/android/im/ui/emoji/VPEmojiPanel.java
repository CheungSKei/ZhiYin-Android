package com.zhiyin.android.im.ui.emoji;

import com.zhiyin.android.util.DebugUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class VPEmojiPanel extends ChatFooterPanel {

	public static int VERSION = 0;
	private final String TAG = "MicroMsg.EmojiPanel.Main";
	private EmojiPanelDialog mEmojiPanelDialog;
	
	public VPEmojiPanel(Context context)
	{
		super(context, null);
		init();
	}
	
	public VPEmojiPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init()
	{
		this.mEmojiPanelDialog = new EmojiPanelDialog(getContext());
	}

	@Override
	public void dealOrientationChange() {
		DebugUtils.debug(TAG, "dealOrientationChange");
	}

	@Override
	public void reflesh() {
		DebugUtils.debug(TAG, "vpemoji ----- reflesh");
		VERSION = 1 + VERSION;
		// TODO:刷新 EmojiPanelDialog 初始化
	}

	@Override
	public void hideCustomBtn() {
		DebugUtils.debug(TAG, "vpemoji ----- hideCustomBtn");
		this.mEmojiPanelDialog.hideZYSmoothHorizontalScrollView();
	}

	@Override
	public void setSendBtnEnabled(boolean enabled) {
		this.mEmojiPanelDialog.setSendBtnEnabled(enabled);
	}

	@Override
	public void hideEmoji(boolean isHide) {
		DebugUtils.debug(TAG, "vpemoji ----- hideQQSmiley: %B, hideEmojiSmiley: %B", new Boolean[]{isHide,false});
	}

	@Override
	public void hideSendButton(boolean isHide) {
		DebugUtils.debug(TAG, "vpemoji ----- hideSendButton");
		this.mEmojiPanelDialog.goneSendBtnAnimation(isHide);
	}

	@Override
	public void onPause() {
		DebugUtils.debug(TAG, "onPause");
		// TODO:保存现场
	}

	@Override
	public void onResume() {
		DebugUtils.debug(TAG, "onResume");
		
	}

	@Override
	public void reset() {
		DebugUtils.debug(TAG, "vpemoji ----- reset");
	}
	
	@Override
	public final void destroy()
	{

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if (visibility == View.VISIBLE) {
			this.mEmojiPanelDialog.initViewGroup(this);
		}
	}
}
