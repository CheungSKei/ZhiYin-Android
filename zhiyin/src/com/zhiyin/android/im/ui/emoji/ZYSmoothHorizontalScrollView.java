package com.zhiyin.android.im.ui.emoji;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;

/**
 * Ë³»¬ºáÏò»®¶¯HorizontalScrollView
 * 
 * @version 1.0.0
 * @date 2014-05-21
 * @author S.Kei.Cheung
 */
public class ZYSmoothHorizontalScrollView extends HorizontalScrollView {

	private Rect mRect = new Rect();
	private TranslateAnimation mTranslateAnimation;
	private Interpolator mInterpolator = new DecelerateInterpolator();
	private float mCurentX;
	private View mFirstChildView;

	public ZYSmoothHorizontalScrollView(Context paramContext) {
		this(paramContext, null);
	}

	public ZYSmoothHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFadingEdgeLength(0);
	}

	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			this.mFirstChildView = getChildAt(0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int i = 1;
		int j = 0;
		
		if (this.mFirstChildView == null) {
			return super.onTouchEvent(event);
		}
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.mCurentX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			float f = event.getX();
			
			if (this.mCurentX == 0.0F) {
				this.mCurentX = f;
			}
			
			int moveHalfX = (int) (this.mCurentX - f) / 2;
			scrollBy(moveHalfX, 0);
			this.mCurentX = f;
			int m = this.mFirstChildView.getMeasuredWidth() - getWidth();
			int n = getScrollX();
			if ((n == 0) || (n == m)) {
				j = i;
			}
			
			if (j != 0) {
				if (this.mRect.isEmpty()) {
					this.mRect.set(this.mFirstChildView.getLeft(), this.mFirstChildView.getTop(),this.mFirstChildView.getRight(), this.mFirstChildView.getBottom());
				}
				
				this.mFirstChildView.layout(this.mFirstChildView.getLeft() - moveHalfX, this.mFirstChildView.getTop(),this.mFirstChildView.getRight() - moveHalfX, this.mFirstChildView.getBottom());
			}
			break;
		case MotionEvent.ACTION_UP:
			this.mCurentX = 0.0F;
			if (!this.mRect.isEmpty()) {
				this.mTranslateAnimation = new TranslateAnimation(this.mFirstChildView.getLeft(),this.mRect.left, 0.0F, 0.0F);
				this.mTranslateAnimation.setInterpolator(this.mInterpolator);
				this.mTranslateAnimation.setDuration(Math.abs(this.mFirstChildView.getLeft()- this.mRect.left));
				this.mFirstChildView.startAnimation(this.mTranslateAnimation);
				this.mFirstChildView.layout(this.mRect.left, this.mRect.top,this.mRect.right, this.mRect.bottom);
				this.mRect.setEmpty();
			}
			break;
		}
		return super.onTouchEvent(event);
	}

}
