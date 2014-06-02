package com.zhiyin.android.im.platformtools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.zhiyin.android.im.R;

public class EmojiImageSpan {

	private static final LruCacheImpl gda = new LruCacheImpl(500);
	private static final LruCacheImpl gdb = new LruCacheImpl(500);

	public static SpannableString replaceEmojiToImageSpan(Context paramContext, String textValue, int textSize, boolean paramBoolean)
	{
		SpannableString spannableString;
		if ((textValue == null) || (textValue.equals(""))) {
			spannableString = new SpannableString("");
			return spannableString;
		}

		String str1;
		String str2;

		if (paramBoolean) {
			textValue = replace(textValue).toString();
		}
		
		switch (textSize)
		{
			default: 
			str1 = textValue + "@" + textSize;
			str2 = textValue;
			spannableString = (SpannableString)gda.get(str1);
			break;
		}

		SpannableString localSpannableString = new SpannableString(str2);
		
		textSize = paramContext.getResources().getDimensionPixelSize(R.dimen.BigTextSize);
		boolean bool1 = SmileyManager.getInsertEmojiSuccessful(paramContext, localSpannableString, textSize);
		
		textSize = paramContext.getResources().getDimensionPixelSize(R.dimen.HintTextSize);
		boolean bool2 = insertEmoji(paramContext, localSpannableString, textSize);
		
		boolean bool3 = true;
		if (bool1 || bool2) {
			bool3 = false;
		}

		gdb.putItem(str1, Boolean.valueOf(bool3));
		gda.putItem(str1, localSpannableString);
		spannableString = localSpannableString;
		return spannableString;
	}
	
	/**
	 * 换行字符替换
	 * @param charSequence
	 * @return
	 */
	private static CharSequence replace(CharSequence charSequence){
		if(charSequence == null){
			return charSequence;
		}
		
		if (charSequence.toString().contains("\n")) {
			charSequence = charSequence.toString().replace("\n", "");
		}
		return charSequence;
	}
	
	public static boolean insertEmoji(Context paramContext, SpannableString spannableString, int textSize)
	{
		boolean isChangeEmojiSuccessful = false;
		
		if ((spannableString == null) || (spannableString.length() == 0)) {
			return false;
		}

		char[] arrayOfChar = spannableString.toString().toCharArray();
		
		for(int i = 0 ;i < arrayOfChar.length;i++)
		{
			int charValue = charExchange(arrayOfChar[i]);
			
			if (charValue != -1)
			{
				Drawable drawable = getDrawable(paramContext, charValue);
				
				if (drawable != null)
				{
					drawable.setBounds(0, 0, (int)(1.3D * textSize), (int)(1.3D * textSize));
					spannableString.setSpan(new ImageSpan(drawable, 0), i, i + 1, 33);
					isChangeEmojiSuccessful = true;
				}
			}
		}
		return isChangeEmojiSuccessful;
	}
	
	private static int charExchange(char paramChar)
	{
		int i = -1;
		
		if ((paramChar < 57345) || (paramChar > 58679)) {
			return i;
		}

		if ((paramChar >= 57345) && (paramChar <= 57434)) {
			i = paramChar - 57345;
		} else if ((paramChar >= 57601) && (paramChar <= 57690)) {
			i = paramChar + 'Z' - 57601;
		} else if ((paramChar >= 57857) && (paramChar <= 57939)) {
			i = paramChar + '´' - 57857;
		} else if ((paramChar >= 58113) && (paramChar <= 58189)) {
			i = paramChar + 'ć' - 58113;
		} else if ((paramChar >= 58369) && (paramChar <= 58444)) {
			i = paramChar + 'Ŕ' - 58369;
		} else if ((paramChar >= 58625) && (paramChar <= 58679)) {
			i = paramChar + 'Ơ' - 58625;
		}
		return i;
	}
	
	public static Drawable getDrawable(Context context, int paramInt)
	{
		Drawable drawable = null;
		if (context == null) {
			return  null;
		}

		int resourceId = context.getResources().getIdentifier("emoji_" + paramInt, "drawable", context.getPackageName());
		
		if (resourceId != 0) {
			drawable = context.getResources().getDrawable(resourceId);
		}
		
		return drawable;
	}
}
