package com.zhiyin.android.im.ui.emoji;

import com.zhiyin.android.im.R;
import com.zhiyin.android.util.DebugUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * Emoji ImageView
 * 
 * @version 1.0.0
 * @date 2014-05-21
 * @author S.Kei.Cheung
 */
public class EmojiView extends ImageView{

	private final String TAG = "MicroMsg.EmojiView";
	
	private static int mEmojiViewImageSize;
	
	private Bitmap mEmojiBitmap = null;
	
	public EmojiView(Context context) {
		this(context,null);
	}
	
	public EmojiView(Context context, AttributeSet paramAttributeSet)
	{
		super(context, paramAttributeSet);
		mEmojiViewImageSize = context.getResources().getDimensionPixelSize(R.dimen.emoji_view_image_size);
	}
	
	@Override
	protected void onDraw(Canvas paramCanvas)
	{
		if (this.mEmojiBitmap != null && !this.mEmojiBitmap.isRecycled())
		{
			int viewWidth = getRight() - getLeft() - getPaddingRight() - getPaddingLeft();
			int viewHeight = getBottom() - getTop() - getPaddingBottom() - getPaddingTop();
			double numRows = viewWidth / this.mEmojiBitmap.getWidth();
			double nuColoumns = viewHeight / this.mEmojiBitmap.getHeight();
			if (numRows < 1.0D || nuColoumns < 1.0D) {
				setScaleType(ImageView.ScaleType.FIT_CENTER);
			}
			setImageBitmap(this.mEmojiBitmap);
			super.onDraw(paramCanvas);
		}
	}
	
	@Override
	protected void onMeasure(int paramInt1, int paramInt2)
	{
		int emojiBitmapScaledWidth = 0;
		int emojiBitmapScaledHeight = 0;
		
		if (this.mEmojiBitmap != null)
		{
			emojiBitmapScaledWidth = resolveSize(this.mEmojiBitmap.getScaledWidth(getResources().getDisplayMetrics().densityDpi), paramInt1);
			emojiBitmapScaledHeight = resolveSize(this.mEmojiBitmap.getScaledHeight(getResources().getDisplayMetrics().densityDpi), paramInt2);
		}

		if (emojiBitmapScaledWidth > mEmojiViewImageSize)
		{
			emojiBitmapScaledHeight = mEmojiViewImageSize;
			emojiBitmapScaledWidth = mEmojiViewImageSize;
		}
		
		setMeasuredDimension(emojiBitmapScaledWidth, emojiBitmapScaledHeight);
	}
	
	/**
	 * Í¼Æ¬ÊÍ·Å
	 */
	private void recycle()
	{
		if (this.mEmojiBitmap != null && !this.mEmojiBitmap.isRecycled())
			this.mEmojiBitmap.recycle();
	}
	
	/**
	 * @deprecated
	 */
	public final void i(Bitmap paramBitmap)
	{
		if (paramBitmap != null)
			this.mEmojiBitmap = paramBitmap;

		if (Build.VERSION.SDK_INT >= 19)
		{
			DebugUtils.debug(TAG, "user emo_loading_bg");
			this.mEmojiBitmap = ((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.emo_loading_bg)).getBitmap();
			//this.mEmojiBitmap = ((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.image_download_fail_icon)).getBitmap();
		}
	}
}
