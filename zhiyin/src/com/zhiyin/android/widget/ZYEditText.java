package com.zhiyin.android.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import com.zhiyin.android.im.platformtools.SmileyManager;
import com.zhiyin.android.im.platformtools.EmojiImageSpan;

/**
 * 知音输入框
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
		boolean rt = super.onTextContextMenuItem(id);
		if (id == R.id.paste)
	    {
			int selectStart = getSelectionStart();
			setText(EmojiImageSpan.replaceEmojiToImageSpan(getContext(), getText().toString(), (int)getTextSize(), false));
			setSelection(selectStart);
	    }
		return rt;
	}
	
	/**
	 * 添加Emoji内容
	 * @param emojiString
	 */
	public final void putEmoji(String emojiString)
	{
		int selectionStart = SmileyManager.getSelectionPosition(getContext(), getText().toString(), getSelectionStart());
		int selectionEnd = SmileyManager.getSelectionPosition(getContext(), getText().toString(), getSelectionEnd());
		StringBuffer localStringBuffer = new StringBuffer(getText());
		String str = localStringBuffer.substring(0, selectionStart) + emojiString + localStringBuffer.substring(selectionEnd, localStringBuffer.length());
		setText(EmojiImageSpan.replaceEmojiToImageSpan(getContext(), str, (int)getTextSize(), false));
		setSelection(selectionStart + emojiString.length());
	}
}
