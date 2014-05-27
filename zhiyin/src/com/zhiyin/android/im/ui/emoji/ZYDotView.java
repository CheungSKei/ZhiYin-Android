package com.zhiyin.android.im.ui.emoji;

import com.zhiyin.android.im.R;
import com.zhiyin.android.util.DebugUtils;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 小圆点
 * 
 * @version 1.0.0
 * @date 2014-05-21
 * @author S.Kei.Cheung
 */
public class ZYDotView extends LinearLayout {

	private final String TAG = "MicroMsg.ZYDotView";
	
	/**
	 * 最大个数
	 */
	private int mMaxCount = 9;
	
	public ZYDotView(Context context) {
		super(context);
	}

	public ZYDotView(Context context, AttributeSet paramAttributeSet) {
		super(context, paramAttributeSet);
		setDot(context, paramAttributeSet);
	}

	@TargetApi(11)
	public ZYDotView(Context context, AttributeSet paramAttributeSet,
			int paramInt) {
		super(context, paramAttributeSet, paramInt);
		setDot(context, paramAttributeSet);
	}

	/**
	 * 设置所有圆点
	 * @param context
	 * @param paramAttributeSet
	 */
	private void setDot(Context context, AttributeSet paramAttributeSet) {
		setDotImageView(context.obtainStyledAttributes(paramAttributeSet,
				new int[] { R.attr.dot_count, R.attr.dot_selected })
				.getResourceId(0, 0));
	}

	/**
	 * 设置当前ImageView和ImageView的所有状态
	 * @param paramInt	首选项和状态
	 */
	public final void setDotImageView(int paramInt) {
		DebugUtils.info(TAG, "setDotCount:%d",
				new Integer[] { paramInt });
		if (paramInt < 0) {
			return;
		}

		if (paramInt > this.mMaxCount) {
			DebugUtils.info(TAG, "large than max count");
			paramInt = this.mMaxCount;
		}

		removeAllViews();

		while (paramInt != 0) {
			ImageView dotImageView = (ImageView) View.inflate(getContext(),
					R.layout.mmpage_control_image, null);
			dotImageView.setImageResource(R.drawable.page_normal);
			addView(dotImageView);
			paramInt--;
		}

		ImageView firstDot = (ImageView) getChildAt(0);
		if (firstDot != null) {
			firstDot.setImageResource(R.drawable.page_active);
		}
	}

	/**
	 * 设置最大个数
	 * 
	 * @param paramInt
	 */
	public final void setMaxCount(int paramInt) {
		DebugUtils.warn(TAG, "setMaxCount:%d",
				new Integer[] { paramInt });
		this.mMaxCount = paramInt;
	}
	
	/**
	 * 设置当前选中项
	 * @param paramInt
	 */
	public final void setSelectedDot(int paramInt) {
		DebugUtils.info(TAG, "setSelectedDot:target index is %d",new Integer[] { paramInt });
		if (paramInt >= getChildCount()) {
			paramInt = -1 + getChildCount();
		}

		DebugUtils.info(TAG,"setSelectedDot:after adjust index is %d", new Integer[] { paramInt });
		for (int i = 0; i < getChildCount(); i++) {
			((ImageView) getChildAt(i)).setImageResource(R.drawable.page_normal);
		}
		
		if (paramInt < 0) {
			paramInt = 0;
		}
		
		ImageView selectedImageView = (ImageView) getChildAt(paramInt);
		if (selectedImageView != null) {
			selectedImageView.setImageResource(R.drawable.page_active);
		}
	}

}
