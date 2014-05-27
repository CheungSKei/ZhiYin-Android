package com.zhiyin.android.widget;

import com.zhiyin.android.util.DebugUtils;
import com.zhiyin.android.util.ScreenUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 仿ViewFilpper 可左右切换ViewGroup
 * 
 * @version 1.0.0
 * @date 2014-05-26
 * @author S.Kei.Cheung
 */
public class ZYFlipper extends ViewGroup {

	private static final String TAG = "MicroMsg.ZYFlipper";
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;

	private int mCurScreen;
	private int mDefaultScreen = 0;

	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;

	private static final int SNAP_VELOCITY = 1000;

	private int mTouchState = TOUCH_STATE_REST;
	private int mTouchSlop;
	private float mLastMotionX;
	// 划动监听
	private onScrollScreenListener mOnScrollScreenListener;

	public ZYFlipper(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ZYFlipper(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		mScroller = new Scroller(context);

		mCurScreen = mDefaultScreen;
		
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childLeft + childWidth,
						childView.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		DebugUtils.info(TAG, "onMeasure");
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = MeasureSpec.getSize(widthMeasureSpec);
		
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		
		if (widthMode != MeasureSpec.EXACTLY) {
			
			throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
			
		}

		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		if (heightMode != MeasureSpec.EXACTLY) {
			
			throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!");
			
		}

		// The children are given the same width and height as the scrollLayout
		final int count = getChildCount();
		
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}

		scrollTo(mCurScreen * width, 0);
		
	}

	/**
	 * According to the position of current layout scroll to the destination
	 * page.
	 */
	public void snapToDestination() {
		
		final int screenWidth = getWidth();
		
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		
		snapToScreen(destScreen);
		
	}

	public void snapToScreen(int whichScreen) {
		
		// get the valid layout page
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		
		if (getScrollX() != (whichScreen * getWidth())) {
			
			final int delta = whichScreen * getWidth() - getScrollX();
			
			mScroller.startScroll(getScrollX(), 0, delta, 0,ScreenUtils.getDipSize(Math.abs(delta) * 2));
			
			if (this.mOnScrollScreenListener != null) {
				this.mOnScrollScreenListener.scroolScreen(mCurScreen, whichScreen);
			}
			
			mCurScreen = whichScreen;

			invalidate(); // Redraw the layout
			
		}
	}

	public void setToScreen(int whichScreen) {
		
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		
		if (this.mOnScrollScreenListener != null) {
			this.mOnScrollScreenListener.scroolScreen(mCurScreen, whichScreen);
		}
		
		mCurScreen = whichScreen;
		
		scrollTo(whichScreen * getWidth(), 0);
		
	}

	public int getCurScreen() {
		
		return mCurScreen;
		
	}

	@Override
	public void computeScroll() {
		
		if (mScroller.computeScrollOffset()) {
			
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			
			postInvalidate();
			
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mVelocityTracker == null) {
			
			mVelocityTracker = VelocityTracker.obtain();
			
		}

		mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		
		final float x = event.getX();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			
			DebugUtils.info(TAG, "event down!");
			
			if (!mScroller.isFinished()) {
				
				mScroller.abortAnimation();
				
			}
			
			mLastMotionX = x;
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			int deltaX = (int) (mLastMotionX - x);
			
			mLastMotionX = x;
			
			scrollBy(deltaX, 0);
			
			break;
		case MotionEvent.ACTION_UP:
			
			DebugUtils.info(TAG, "event : up");
			
			final VelocityTracker velocityTracker = mVelocityTracker;
			
			velocityTracker.computeCurrentVelocity(1000);
			
			int velocityX = (int) velocityTracker.getXVelocity();
			
			DebugUtils.info(TAG, "velocityX:" + velocityX);
			
			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
				
				DebugUtils.info(TAG, "snap left");
				
				snapToScreen(mCurScreen - 1);
				
			} else if (velocityX < -SNAP_VELOCITY&& mCurScreen < getChildCount() - 1) {
				
				DebugUtils.info(TAG, "snap right");
				
				snapToScreen(mCurScreen + 1);
				
			} else {
				
				snapToDestination();
				
			}
			
			if (mVelocityTracker != null) {
				
				mVelocityTracker.recycle();
				
				mVelocityTracker = null;
				
			}
			
			mTouchState = TOUCH_STATE_REST;
			
			break;
		case MotionEvent.ACTION_CANCEL:
			
			mTouchState = TOUCH_STATE_REST;
			
			break;
		}

		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();
		
		if ((action == MotionEvent.ACTION_MOVE)&& (mTouchState != TOUCH_STATE_REST)) {
			
			return true;
			
		}

		final float x = ev.getX();

		switch (action) {
		
		case MotionEvent.ACTION_MOVE:
			
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			
			if (xDiff > mTouchSlop) {
				
				mTouchState = TOUCH_STATE_SCROLLING;
				
			}
			
			break;
		case MotionEvent.ACTION_DOWN:
			
			mLastMotionX = x;
			
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST: TOUCH_STATE_SCROLLING;
			
			break;
		case MotionEvent.ACTION_CANCEL:
			
		case MotionEvent.ACTION_UP:
			
			mTouchState = TOUCH_STATE_REST;
			
			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}
	
	/**
	 * 划动监听回调
	 * @param oss
	 */
	public void setOnScrollScreenListener(onScrollScreenListener oss){
		this.mOnScrollScreenListener = oss;
	}

	/**
	 * 页面划动监听
	 */
	public abstract interface onScrollScreenListener
	{
		/**
		 * 页面切换回调下标
		 * @param oldIndex 旧页面下标
		 * @param newIndex 新页面下标
		 */
		public abstract void scroolScreen(int oldIndex, int newIndex);
	}
}
