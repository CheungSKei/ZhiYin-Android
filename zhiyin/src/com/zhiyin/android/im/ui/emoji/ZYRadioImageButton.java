package com.zhiyin.android.im.ui.emoji;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageButton;

/**
 * ·ÂRadioButton
 * 
 * @version 1.0.0
 * @date 2014-05-21
 * @author S.Kei.Cheung
 */
public class ZYRadioImageButton extends ImageButton implements Checkable {

	private IZYRadioImageButtonCheckable mZYRadioImageButtonCheckableLeft;
	private IZYRadioImageButtonCheckable mZYRadioImageButtonCheckableRight;
	private boolean mIsSetChecking = false;
	private boolean mIsCheckable = true;
	private boolean mIsChecked;
	
	public ZYRadioImageButton(Context paramContext, AttributeSet paramAttributeSet)
	{
		this(paramContext, paramAttributeSet, -1);
	}
	
	public ZYRadioImageButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
	{
		super(paramContext, paramAttributeSet, paramInt);
	}
	
	public final void setZYRadioImageButtonCheckable(IZYRadioImageButtonCheckable paramcs)
	{
		this.mZYRadioImageButtonCheckableRight = paramcs;
	}
	
	public final boolean isCheckable()
	{
		return this.mIsCheckable;
	}
	
	@Override
	public boolean isChecked()
	{
		return this.mIsChecked;
	}
	
	public boolean performClick()
	{
		toggle();
		return false;
	}
	
	/**
	 * ÊÇ·ñ¿Éµã»÷
	 * @param paramBoolean
	 */
	public final void setCheckable(boolean paramBoolean)
	{
		this.mIsCheckable = paramBoolean;
	}
	
	@Override
	public void setChecked(boolean paramBoolean)
	{
		if (this.mIsChecked != paramBoolean)
		{
			this.mIsChecked = paramBoolean;
			setSelected(this.mIsChecked);
			refreshDrawableState();
			if (!this.mIsSetChecking) {
				this.mIsSetChecking = true;
				if (this.mZYRadioImageButtonCheckableLeft != null)
				{
					this.mZYRadioImageButtonCheckableLeft.setChecked(this);
				}
				
				if (this.mZYRadioImageButtonCheckableRight != null)
				{
					this.mZYRadioImageButtonCheckableRight.setChecked(this);
				}
				
				this.mIsSetChecking = false;
			}
		}
	}
	
	@Override
	public void toggle()
	{
		if (this.mIsCheckable && !isChecked()) {
			if (this.mIsChecked) {
				setChecked(false);
			}else{
				setChecked(true);
			}
		}
		
		if (this.mZYRadioImageButtonCheckableLeft != null) {
			this.mZYRadioImageButtonCheckableLeft.toggle(this);
		}
		
		if (this.mZYRadioImageButtonCheckableRight != null) {
			this.mZYRadioImageButtonCheckableRight.toggle(this);
		}
	}
}
