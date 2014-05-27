package com.zhiyin.android.im.ui.emoji;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyin.android.im.R;
import com.zhiyin.android.im.application.BaseApplication;
import com.zhiyin.android.util.DebugUtils;
import com.zhiyin.android.util.ScreenUtils;
import com.zhiyin.android.widget.ZYFlipper;

/**
 * Emoji弹出框
 * 
 * @version 1.0.0
 * @date 2014-05-22
 * @author S.Kei.Cheung
 */
public final class UIDeal implements IRadioButtonId,SizeChangedListener,View.OnClickListener,ZYFlipper.onScrollScreenListener{

	private final String TAG = "MicroMsg.EmojiPanel.UIDeal";
	
	private View mEmojiView;
	private ZYDotView mZYDotView;
	private ZYFlipper mEmojiPanelVP;
	private ZYSmoothHorizontalScrollView mZYSmoothHorizontalScrollView;
	private ZYRadioGroupView mZYRadioGroupView;
	private ZYRadioImageButton mZYRadioImageButton;
	private ImageButton mStyleTabButton;
	private TextView mSend_btn;
	private volatile boolean mIsInit = false;
	private volatile int mCurentPage = -1;
	private Context mContext;

	public UIDeal(Context paramContext)
	{
		this.mContext = paramContext;
	}
	
	public final Context getContext()
	{
		return this.mContext;
	}
	
	@Override
	public void onSizeChanged(int w) {
		
	}

	@Override
	public void setCheckedRadioButtonId(ZYRadioGroupView paramMMRadioGroupView,
			int radioButtonId) {
		
	}

	/**
	 * 设置按钮是否可用
	 * @param enabled
	 */
	public final void setSendBtnEnabled(boolean enabled)
	{
		if (this.mSend_btn != null) {
			this.mSend_btn.setEnabled(enabled);
		}
	}
	
	/**
	 * 初始化ViewGroup
	 * @param paramViewGroup
	 */
	public final void initViewGroup(ViewGroup paramViewGroup)
	{
		if (paramViewGroup == null) {
			return;
		}

		if ((this.mEmojiView == null) || (paramViewGroup.getChildCount() <= 0)) {
			
		}
		
		DebugUtils.debug(TAG, "already load view --- pass");
		this.mIsInit = false;
		DebugUtils.debug(TAG, "async load view");
		
		if (this.mEmojiView == null)
		{
			this.mEmojiView = View.inflate(this.mContext, R.layout.smiley_panel, null);
			this.mEmojiPanelVP = (ZYFlipper)findViewById(R.id.smiley_panel_flipper);
			this.mEmojiPanelVP.setOnScrollScreenListener(this);
			this.mZYDotView = (ZYDotView)findViewById(R.id.smiley_panel_dot);
			this.mZYDotView.setSelectedDot(1);
			this.mZYSmoothHorizontalScrollView = (ZYSmoothHorizontalScrollView)findViewById(R.id.smiley_scroll_view);
			this.mZYRadioGroupView = (ZYRadioGroupView)findViewById(R.id.smiley_panel_btn_group);
			this.mSend_btn = (TextView)findViewById(R.id.send_btn);
			this.mZYRadioGroupView.setRadioButtonIDImpl(this);
			this.mSend_btn.setOnClickListener(this);
		}

		// Emoji 页数
		int emojiTotal = EmojiResourceReChange.getEmojiCodeLength(mContext) + EmojiResourceReChange.getSmileyCodeLength(mContext);
		int numColumns = 7;
		int itemsPerPage = numColumns * 3;
		int pageSize = (int)Math.ceil((float)emojiTotal/itemsPerPage);
		for(int i =0 ;i< pageSize;i++){
			EmojiGrid emojiGrid = new EmojiGrid(this.mContext);
			emojiGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
			emojiGrid.setVerticalSpacing(ScreenUtils.fromDPToPix(80));
			emojiGrid.setPageInfo(20, i, emojiTotal, itemsPerPage, pageSize, numColumns, BaseApplication.screenDisplayMetrics().widthPixels);
			this.mEmojiPanelVP.addView(emojiGrid,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		this.mZYDotView.setMaxCount(pageSize);
		this.mZYDotView.setDotImageView(pageSize);
		this.mZYDotView.setSelectedDot(0);
		this.mSend_btn.setVisibility(View.GONE);
		paramViewGroup.addView(this.mEmojiView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		this.mIsInit = true;
	}
	
	/**
	 * 通过资源Id取得对象实例
	 * @param paramInt
	 * @return
	 */
	private View findViewById(int paramInt)
	{
		return this.mEmojiView.findViewById(paramInt);
	}
	
	/**
	 * 通过SmileyPanel个数滑动Scrollview
	 * @param tableNum
	 */
	private void setScrollX(int tableNum)
	{
		int tabAllWidth = this.mZYRadioGroupView.getMeasuredWidth();
		DebugUtils.debug(TAG, "tabAllWidth: %d", new Integer[]{tabAllWidth});
		int space = tabAllWidth - this.mZYSmoothHorizontalScrollView.getWidth();
		
		if (space <= 0) {
			return;
		}

		int emoji_panel_tab_width = this.mContext.getResources().getDimensionPixelSize(R.dimen.emoji_panel_tab_width);
		int scrollx = this.mZYSmoothHorizontalScrollView.getScrollX();
		
		if ((scrollx > 0) && (scrollx >= emoji_panel_tab_width * tableNum))
		{
			this.mZYSmoothHorizontalScrollView.scrollTo(tableNum * emoji_panel_tab_width, 0);
			DebugUtils.debug(TAG, "adjusting tab site --- to left.");
		}
		
		if ((scrollx < space) && (scrollx + this.mZYSmoothHorizontalScrollView.getWidth() < emoji_panel_tab_width * (tableNum + 1)))
		{
			this.mZYSmoothHorizontalScrollView.scrollTo(emoji_panel_tab_width * (tableNum + 1) - this.mZYSmoothHorizontalScrollView.getWidth(), 0);
			DebugUtils.debug(TAG, "adjusting tab site --- to right.");
		}
	}
	
	public final void adjustingTabSite(int paramInt)
	{
		if (paramInt > 0)
		{
			DebugUtils.info(TAG, "tab size changed ,so adjusting tab site.");
		}
	}
	
	/**
	 * 返回Style Tab Button
	 * @return
	 */
	private ImageButton getStyleTabButton()
	{
		if (this.mStyleTabButton == null)
		{
			this.mStyleTabButton = new ImageButton(this.mContext, null, R.style.ZYStyleTabButton);
			setImageViewType(this.mStyleTabButton);
			this.mStyleTabButton.setClickable(false);
			this.mStyleTabButton.setVisibility(View.GONE);
		}
		return this.mStyleTabButton;
	}
	
	/**
	 * 隐藏发送按钮动画
	 * @param showAnimation
	 */
	public final void goneSendBtnAnimation(boolean showAnimation)
	{
		getStyleTabButton().setVisibility(View.GONE);
		if (this.mSend_btn == null) {
			return;
		}

		if (this.mSend_btn.getVisibility() == View.VISIBLE)
		{
			if (showAnimation)
			{
				// 隐藏按钮动画
				TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.0F, this.mSend_btn.getWidth(), 0.0F, 0.0F);
				localTranslateAnimation.setDuration(250L);
				this.mSend_btn.startAnimation(localTranslateAnimation);
			}
			this.mSend_btn.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 设置ImageView属性
	 * @param imageView
	 * @return
	 */
	private ImageView setImageViewType(ImageView imageView)
	{
		imageView.setMaxHeight(this.mContext.getResources().getDimensionPixelSize(R.dimen.emoji_panel_tab_height));
		imageView.setMinimumHeight(this.mContext.getResources().getDimensionPixelSize(R.dimen.emoji_panel_tab_height));
		imageView.setMaxWidth(this.mContext.getResources().getDimensionPixelSize(R.dimen.emoji_panel_tab_width));
		imageView.setMinimumWidth(this.mContext.getResources().getDimensionPixelSize(R.dimen.emoji_panel_tab_width));
		imageView.setScaleType(ImageView.ScaleType.CENTER);
		imageView.setClickable(true);
		return imageView;
	}

	/**
	 * 返回EmojiPanelVP
	 * @return
	 */
	public final ZYFlipper getEmojiPanelVP()
	{
		return this.mEmojiPanelVP;
	}

	/**
	 * 隐藏ZYSmoothHorizontalScrollView
	 */
	public final void hideZYSmoothHorizontalScrollView()
	{
		if (this.mZYSmoothHorizontalScrollView != null) {
			this.mZYSmoothHorizontalScrollView.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 取得RadioImageButton
	 * @return
	 */
	private ZYRadioImageButton getRadioImageButton()
	{
		if (this.mZYRadioImageButton == null)
		{
			this.mZYRadioImageButton = new ZYRadioImageButton(this.mContext, null, R.style.ZYStyleTabButton);
			setImageViewType(this.mZYRadioImageButton);
			this.mZYRadioImageButton.setBackgroundResource(R.drawable.bottom_btn_bg);
			this.mZYRadioImageButton.setImageResource(R.drawable.emotionstore_emoji_icon);
			this.mZYRadioImageButton.setTag("TAG_DEFAULT_TAB");
			this.mZYRadioImageButton.setId(getId(this.mZYRadioImageButton));
			this.mZYRadioImageButton.setCheckable(true);
		}
		return this.mZYRadioImageButton;
	}
	
	/**
	 * 返回Id
	 * @param paramView
	 * @return
	 */
	public static int getId(View view)
	{
		int hashCode = view.hashCode();
		if (hashCode < 0) {
			hashCode &= 0x7FFFFFFF;
		}
		return hashCode;
	}
	
	/**
	 * 页面切换
	 * @param pageIndex
	 */
	public final void onPageSelected(int pageIndex)
	{
		if (this.mCurentPage == pageIndex) {
			return;
		}

		this.mCurentPage = pageIndex;
		DebugUtils.debug(TAG, "onPageSelected: %d", new Integer[]{pageIndex});
//		setDotViewParams(locale.atk(), pageIndex - locale.ath());
//		setScrollX(this.fqc.kH(pageIndex));
	}
	
	/**
	 * 设置圆点最大个数和当前项
	 * @param maxCount
	 * @param selectedDot
	 */
	private void setDotViewParams(int maxCount, int selectedDot)
	{
		if (maxCount <= 1) {
			this.mZYDotView.setVisibility(View.INVISIBLE);
		}else{
			this.mZYDotView.setVisibility(View.VISIBLE);
			this.mZYDotView.setMaxCount(maxCount);
			this.mZYDotView.setSelectedDot(selectedDot);
		}
	}
	
	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void scroolScreen(int oldIndex, int newIndex) {
		this.mZYDotView.setSelectedDot(newIndex);
	}

}
