package com.zhiyin.android.im.ui.emoji;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.zhiyin.android.im.R;

public class EmojiSubGrid extends EmojiGrid{

	private WindowManager mWindowManager;
	private int mScaledTouchSlop;
	private LayoutInflater mLayoutInflater;
	Rect fjO = new Rect();
	boolean fjP;
	private EmojiView mEmojiView;
	private ProgressBar mProgressBar;
	private View mSmileyDialogPopview;
	private WindowManager.LayoutParams mLayoutParams;
	private int mScreenWidth;
	private int mScreenHeight;
	private int mLongPressTimeout;
	private int mPressedStateDuration;
	private int mOrientation;
	private int mColumnWidth;
	private int mEmojiPreviewImageSize;
	
	public EmojiSubGrid(Context context) {
		this(context,null);
		
	}

	public EmojiSubGrid(Context context, AttributeSet attrs) {
		super(context,attrs);
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mWindowManager = ((WindowManager)context.getSystemService("window"));
		this.mSmileyDialogPopview = this.mLayoutInflater.inflate(R.layout.smiley_dialog_popview, null);
		this.mEmojiView = (EmojiView)this.mSmileyDialogPopview.findViewById(R.id.image);
		this.mProgressBar = ((ProgressBar)this.mSmileyDialogPopview.findViewById(R.id.loading_tips));
		this.mLayoutParams = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
		this.mEmojiPreviewImageSize = context.getResources().getDimensionPixelSize(R.dimen.emoji_preview_image_size);
		this.mLayoutParams.width = this.mEmojiPreviewImageSize;
		this.mLayoutParams.height = this.mEmojiPreviewImageSize;
		this.mLayoutParams.gravity = Gravity.CENTER;
		this.mLongPressTimeout = getLongPressTimeout();
		this.mPressedStateDuration = ViewConfiguration.getPressedStateDuration();
		this.mOrientation = getResources().getConfiguration().orientation;
		// 横屏
		if (this.mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
			this.mScreenWidth = this.mWindowManager.getDefaultDisplay().getHeight();
			this.mScreenHeight = this.mWindowManager.getDefaultDisplay().getWidth();
		}else{
			this.mScreenWidth = this.mWindowManager.getDefaultDisplay().getWidth();
			this.mScreenHeight = this.mWindowManager.getDefaultDisplay().getHeight();
		}

		setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setBackgroundResource(0);
		setStretchMode(STRETCH_COLUMN_WIDTH);
		this.mColumnWidth = dipToPx(context, 80.0F);
		setColumnWidth(this.mColumnWidth);
		setNumColumns(this.mScreenWidth / this.mColumnWidth);
		this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}
	
	private void W(View paramView)
	{
		this.fjO.set(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
		this.fjO.set(this.fjO.left - getPaddingLeft(), this.fjO.top - getPaddingTop(), this.fjO.right + getPaddingRight(), this.fjO.bottom + getPaddingBottom());
		if (paramView.isEnabled() != this.fjP && this.fjP) {
			this.fjP = false;
			refreshDrawableState();
		}
	}

	/**
	 * 取得长按时间
	 * @return
	 */
	protected int getLongPressTimeout()
	{
		return ViewConfiguration.getLongPressTimeout();
	}
	
	/**
	 * dip转px
	 * @param paramContext
	 * @param paramFloat
	 * @return
	 */
	public static int dipToPx(Context paramContext, float paramFloat)
	{
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
		return Math.round(paramFloat * localDisplayMetrics.densityDpi / 160.0F);
	}
}
