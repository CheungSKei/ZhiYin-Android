package com.zhiyin.android.im.ui.emoji;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 仿RadioGroupView
 * 
 * @version 1.0.0
 * @date 2014-05-21
 * @author S.Kei.Cheung
 */
public class ZYRadioGroupView extends LinearLayout implements ViewGroup.OnHierarchyChangeListener,IZYRadioImageButtonCheckable{

	private int mCheckedRadioButtonId = -1;
	private IRadioButtonId mRadioButtonIdImpl;
	private ZYRadioImageButton mZYRadioImageButton;
	private SizeChangedListener mSizeChanged;
	
	public ZYRadioGroupView(Context paramContext)
	{
		this(paramContext, null);
	}
	
	public ZYRadioGroupView(Context paramContext, AttributeSet paramAttributeSet)
	{
		super(paramContext, paramAttributeSet);
		super.setOnHierarchyChangeListener(this);
	}
	
	/**
	 * 设置已点击按钮Id
	 * @param paramInt
	 */
	private void setCheckRadioButtonId(int radioButtonId)
	{
		this.mCheckedRadioButtonId = radioButtonId;
		if (this.mRadioButtonIdImpl != null) {
			this.mRadioButtonIdImpl.setCheckedRadioButtonId(this, this.mCheckedRadioButtonId);
		}
	}
	
	private void setRadioButtonCehcked(int radioButtonId, boolean paramBoolean)
	{
		View radioButton = findViewById(radioButtonId);
		if ((radioButton != null) && ((radioButton instanceof ZYRadioImageButton))) {
			((ZYRadioImageButton)radioButton).setChecked(paramBoolean);
		}
	}
	
	/**
	 * 设置RadioButtonID接口实现对象
	 * @param paramcp
	 */
	public final void setRadioButtonIDImpl(IRadioButtonId paramcp)
	{
		this.mRadioButtonIdImpl = paramcp;
	}
	
	public final void a(SizeChangedListener sizeChanged)
	{
		this.mSizeChanged = sizeChanged;
	}

	/**
	 * 取得当前选中RadioImageButton对象
	 * @return
	 */
	public final ZYRadioImageButton getCurentRadioImageButon()
	{
		return this.mZYRadioImageButton;
	}
	
	public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
	{
		if ((paramView instanceof ZYRadioImageButton))
		{
			ZYRadioImageButton localMMRadioImageButton = (ZYRadioImageButton)paramView;
			if (localMMRadioImageButton.isChecked())
			{
				if (this.mCheckedRadioButtonId != -1) {
					setRadioButtonCehcked(this.mCheckedRadioButtonId, false);
				}
				setCheckRadioButtonId(localMMRadioImageButton.getId());
				this.mZYRadioImageButton = localMMRadioImageButton;
			}
		}
		super.addView(paramView, paramInt, paramLayoutParams);
	}
	
	public final int getCheckedRadioButtonId()
	{
		return this.mCheckedRadioButtonId;
	}
	
	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		if (this.mCheckedRadioButtonId != -1)
		{
			setRadioButtonCehcked(this.mCheckedRadioButtonId, true);
			setCheckRadioButtonId(this.mCheckedRadioButtonId);
		}
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		if (((w > 0) || (h > 0)) && (this.mSizeChanged != null)) {
			this.mSizeChanged.onSizeChanged(w);
		}
	}
	
	@Override
	public final void setChecked(ZYRadioImageButton paramMMRadioImageButton)
	{
		if (mCheckedRadioButtonId != -1) {
			
		}
		mZYRadioImageButton =  paramMMRadioImageButton;
		setCheckRadioButtonId(paramMMRadioImageButton.getId());
	}
	
	@Override
	public final void toggle(ZYRadioImageButton paramMMRadioImageButton)
	{
		setCheckRadioButtonId(paramMMRadioImageButton.getId());
	}

	@Override
	public final void onChildViewAdded(View parent, View child)
	{
		if (parent == this && ((child instanceof ZYRadioImageButton)))
		{
			if (child.getId() == -1)
			{
				int child_hashCode = child.hashCode();
				if (child_hashCode < 0) {
					child_hashCode &= 0x7FFFFFFF;
				}
				child.setId(child_hashCode);
			}
			((ZYRadioImageButton)child).setZYRadioImageButtonCheckable(this);
		}
	}
	
	@Override
	public final void onChildViewRemoved(View parent, View child)
	{
		if ((parent == this) && ((child instanceof ZYRadioImageButton))) {
			((ZYRadioImageButton)child).setZYRadioImageButtonCheckable(null);
		}
	}

}
