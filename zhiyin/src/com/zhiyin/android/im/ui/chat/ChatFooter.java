package com.zhiyin.android.im.ui.chat;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.zhiyin.android.im.R;
import com.zhiyin.android.im.ui.emoji.ChatFooterPanel;
import com.zhiyin.android.im.ui.emoji.IEmojiChickedListener;
import com.zhiyin.android.im.ui.emoji.VPEmojiPanel;
import com.zhiyin.android.util.DebugUtils;
import com.zhiyin.android.util.ScreenUtils;
import com.zhiyin.android.widget.ZYEditText;

/**
 * 聊天页面底部输入框和其它功能控制
 * 
 * @version 1.0.0
 * @date 2014-05-25
 * @author S.Kei.Cheung
 */
public class ChatFooter extends LinearLayout implements View.OnTouchListener,View.OnClickListener,IEmojiChickedListener{
	
	private final String TAG = "MicroMsg.ChatFooter";

	private ZYEditText mEditText = null;
	private Button mSendBtn = null;
	private ChatFooterPanel mEmojiPanel;
	private InputMethodManager mInputMethodManager;
	private View mChatFooterView = null;
	private LinearLayout mTextPanelLL;
	private FrameLayout mChattingBottomPanel;
	private ImageButton mChattingAttachBtn;
	private Button mChattingSendBtn;
	private Button mVoiceRecordBt;
	private ImageButton mChattingModeBtn;
	private ImageButton mChattingSmileyBtn;
	private volatile boolean mIsDoingSomthing = false;
	private boolean mIsChattingSmileyBtn = false;
	
	public ChatFooter(Context context) {
		this(context, null);
	}
	
	public ChatFooter(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ChatFooter(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		this.mInputMethodManager = ((InputMethodManager)context.getSystemService("input_method"));
		this.mChatFooterView = inflate(context, R.layout.chatting_footer, this);
		this.mTextPanelLL = (LinearLayout)this.mChatFooterView.findViewById(R.id.text_panel_ll);
		this.mEditText = (ZYEditText)this.mChatFooterView.findViewById(R.id.chatting_content_et);
		this.mChattingBottomPanel = (FrameLayout)findViewById(R.id.chatting_bottom_panel);
		this.mChattingAttachBtn = (ImageButton)this.mChatFooterView.findViewById(R.id.chatting_attach_btn);
		this.mChattingSendBtn = (Button)this.mChatFooterView.findViewById(R.id.chatting_send_btn);
		setBtnVisibility(false);
		this.mVoiceRecordBt = (Button)this.mChatFooterView.findViewById(R.id.voice_record_bt);
		this.mChattingModeBtn = (ImageButton)findViewById(R.id.chatting_mode_btn);
		this.mEmojiPanel = (VPEmojiPanel)findViewById(R.id.chatting_app_panel);
		this.mEmojiPanel.setEmojiChickedListener(this);
		DebugUtils.info(TAG, "send edittext ime option %s", new Integer[]{this.mEditText.getImeOptions()});
//		this.mEditText.setOnEditorActionListener(new af(this));
		this.mEditText.setOnTouchListener(this);
//		this.mEditText.setOnLongClickListener(new ah(this));
//		this.mChattingSendBtn.setOnClickListener(new ai(this));
//		this.mVoiceRecordBt.setOnTouchListener(new q(this));
//		this.mVoiceRecordBt.setOnKeyListener(new r(this));
//		this.mVoiceRecordBt.setOnClickListener(new p(this));
		onResume();
	}
	
	public final void onResume()
	{
		this.mEmojiPanel.onResume();
		this.mEditText.setImeOptions(EditorInfo.IME_ACTION_UNSPECIFIED);
		this.mEditText.setInputType(0x40 | this.mEditText.getInputType());
		this.mChatFooterView.findViewById(R.id.chatting_send_group).setVisibility(View.VISIBLE);
		initChattingSmileyBtn();
	}
	
	/**
	 * 设置按钮是否可见
	 * @param paramBoolean
	 */
	private void setBtnVisibility(boolean paramBoolean)
	{
		if (this.mChattingSendBtn == null || this.mChattingAttachBtn == null) {
			return;
		}
		
		if ((this.mChattingSendBtn.getVisibility() != View.VISIBLE || !paramBoolean) && ((this.mChattingAttachBtn.getVisibility() != View.VISIBLE) || (paramBoolean)))
		{

			if (!paramBoolean) {
				this.mChattingAttachBtn.setVisibility(View.VISIBLE);
				this.mChattingSendBtn.setVisibility(View.GONE);
			}else{
				this.mChattingSendBtn.setVisibility(View.VISIBLE);
				this.mChattingAttachBtn.setVisibility(View.GONE);
			}

			this.mChattingSendBtn.getParent().requestLayout();
		}
	}
	
	/**
	 * 设置EditText是否获得焦点
	 * @param paramBoolean
	 */
	private void setEditTextFocus(boolean paramBoolean)
	{
		if (paramBoolean)
		{
			this.mEditText.requestFocus();
			this.mTextPanelLL.setEnabled(true);
		}
		else{
			this.mEditText.clearFocus();
			this.mTextPanelLL.setEnabled(false);
		}
	}

	private void b(int paramInt1, int paramInt2, boolean paramBoolean)
	{
		if ((paramBoolean && paramInt2 != 21 && this.mChattingSmileyBtn != null) ||
				(this.mChattingSmileyBtn != null && !paramBoolean && 
				(paramInt2 == 21 ||paramInt2 == 20))) {
			setChattingSmileyState(false);
		}
		if ((paramInt1 == 0) && (!paramBoolean))
		{
			setChattingSmileyState(false);
		}
		
		setEditTextFocus(true);
		this.mInputMethodManager.showSoftInput(this.mEditText, InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		setEditTextColor(true);
		if (isChattingBottomPanelVisiable()){
			// 隐藏Emoji
			this.mChattingBottomPanel.setVisibility(View.GONE);
		}
		asF();
		return false;
	}
	
	@TargetApi(11)
	public final void setEditTextColor(boolean paramBoolean)
	{
		if (paramBoolean)
		{
			this.mEditText.setTextColor(getResources().getColor(R.color.zy_edit_text_color));
		}
		else
		{
			this.mEditText.setTextColor(getResources().getColor(R.color.half_alpha_black));
			setEditTextFocus(false);
		}
	}
	
	/**
	 * 设置键盘的回车按钮
	 */
	public final void setEnterButtonSend()
	{
		DebugUtils.debug(TAG, "chatting footer enable ener button send");
		this.mEditText.setImeOptions(View.INVISIBLE);
		this.mEditText.setInputType(0xFFFFFFBF & this.mEditText.getInputType());
	}
	
	public final void asF()
	{
		b(2, 20, false);
	}

	/**
	 * 初始化笑脸按钮
	 */
	public final void initChattingSmileyBtn()
	{
		this.mChattingSmileyBtn = (ImageButton)this.mChatFooterView.findViewById(R.id.chatting_smiley_btn);
		this.mChattingSmileyBtn.setVisibility(View.VISIBLE);
		this.mChattingSmileyBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.chatting_smiley_btn:
			if (isChattingBottomPanelVisiable()){
				// TODO:隐藏Emoji，显示输入法
				this.mChattingBottomPanel.setVisibility(View.GONE);
				asF();
				return;
			}
			
			setHideSoftInput();
			this.mChattingBottomPanel.setVisibility(View.VISIBLE);
			setChattingSmileyState(true);
			DebugUtils.info("MicroMsg.ChatFooter", "refresh smiley panel size");
			this.mEmojiPanel.reflesh();
			
			break;
		}
	}
	
	// 隐藏键盘
	public final void setHideSoftInput()
	{
		this.mIsDoingSomthing = true;
		hideSoftInputFromWindow(this);
		this.mIsDoingSomthing = false;
	}
	
	/**
	 * asz() 方法
	 * 返回ChattingBottomPanel是否已经显示
	 * @return 
	 */
	public final boolean isChattingBottomPanelVisiable()
	{
		if (this.mChattingBottomPanel.getVisibility() == View.VISIBLE) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 隐藏输入法
	 * @param paramView
	 */
	public static void hideSoftInputFromWindow(View paramView)
	{
		try
		{
			InputMethodManager localInputMethodManager = (InputMethodManager)paramView.getContext().getSystemService("input_method");
			if (localInputMethodManager != null) {
				localInputMethodManager.hideSoftInputFromWindow(paramView.getApplicationWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
			}
		}
		catch (Exception localException) {}
	}
	
	/**
	 * 返回键盘高度
	 * @return
	 */
	public static final int getKeybord_height()
	{
		return ScreenUtils.fromDPToPix(230);
	}
	
	/**
	 * 设置当前笑脸状态
	 * @param enable true:显示选中状态 false:显示默认状态
	 */
	private void setChattingSmileyState(boolean enable)
	{
		if (this.mChattingSmileyBtn == null) {
			return;
		}

		if ((!this.mIsChattingSmileyBtn || (!enable)) && (this.mIsChattingSmileyBtn || (enable)))
		{
			this.mIsChattingSmileyBtn = enable;
			if (enable) {
				this.mChattingSmileyBtn.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chatting_biaoqing_btn_enable));
			} else {
				this.mChattingSmileyBtn.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chatting_biaoqing_btn_normal));
			}
		}
	}

	@Override
	public void ahL() {
	
	}

	@Override
	public void deleteEmoji() {
		 mEditText.getInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, 67));
		 mEditText.getInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, 67));
	}

	@Override
	public void append(String paramString) {
		mEditText.putEmoji(paramString);
	}

	@Override
	public void bA(boolean paramBoolean) {
		
	}
}
