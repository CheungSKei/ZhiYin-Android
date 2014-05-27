package com.zhiyin.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/**
 * ÷™“Ù ‰»ÎøÚ
 * 
 * @version 1.0.0
 * @date 2014-05-25
 * @author S.Kei.Cheung
 */
public class ZYEditText extends EditText {

	private InputConnection mInputConnection;
	
	public ZYEditText(Context context)
	{
		super(context);
	}
	
	public ZYEditText(Context context, AttributeSet attrs)
	{
	super(context, attrs);
	}
	
	public ZYEditText(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	
	public final InputConnection getInputConnection()
	{
		return this.mInputConnection;
	}

	@Override
	public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
		this.mInputConnection = super.onCreateInputConnection(outAttrs);
		return this.mInputConnection;
	}

	@Override
	public boolean onTextContextMenuItem(int id) {
		return super.onTextContextMenuItem(id);
	}
	
}
