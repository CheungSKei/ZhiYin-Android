package com.zhiyin.android.im.ui.emoji;

import com.zhiyin.android.im.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Emoji��ԴID�����ƻ�ת
 * 
 * @version 1.0.0
 * @date 2014-05-19
 * @author S.Kei.Cheung
 */
public class EmojiResourceReChange {
	
	private static EmojiResourceReChange gdc = null;
	private String[] mMerge_smiley_nameArray;
	private String[] gde;
	
	private EmojiResourceReChange(Context paramContext)
	{
		this.mMerge_smiley_nameArray = paramContext.getResources().getStringArray(R.array.merge_smiley_code_smiley);
		this.gde = paramContext.getResources().getStringArray(R.array.merge_smiley_code_emoji);
	}
	
	/**
	 * ����Smiley����
	 * @param paramContext
	 * @return
	 */
	public static int getSmileyCodeLength(Context paramContext)
	{
		int contentLength = getInstance(paramContext).mMerge_smiley_nameArray.length;
		if (contentLength <= 0) {
			contentLength = paramContext.getResources().getStringArray(R.array.merge_smiley_code_smiley).length;
		}
		return contentLength;
	}
	
	/**
	 * ����Emoji����
	 * @param paramContext
	 * @return
	 */
	public static int getEmojiCodeLength(Context paramContext)
	{
		int i = getInstance(paramContext).gde.length;
		if (i <= 0) {
			i = paramContext.getResources().getStringArray(R.array.merge_smiley_code_emoji).length;
		}
		return i;
	}
	
	/**
	 * ��ʼ��
	 * @param paramContext
	 * @return
	 */
	private static EmojiResourceReChange getInstance(Context paramContext)
	{
		if (gdc == null) {
			gdc = new EmojiResourceReChange(paramContext);
		}
		return gdc;
	}
	
	/**
	 * �����ţ�������ӦDrawable
	 * @param paramContext
	 * @param paramInt		��Ӧ���
	 * @return
	 */
	public static Drawable getSmileyDrawable(Context paramContext, int paramInt)
	{
		getInstance(paramContext);
		int resourceId = getSmileyEmojiResourceId(paramContext, paramInt);
		if (resourceId == 0) {
			return null;
		}
		
		Drawable localDrawable = paramContext.getResources().getDrawable(resourceId);
		return localDrawable;
	}
	
	/**
	 * ȡ��EmojiͼƬ
	 * @param paramContext
	 * @param paramInt		��Ӧ���
	 * @return
	 */
	public static Drawable getEmojiDrawable(Context paramContext, int paramInt)
	{
		if (paramInt < 100) {
			paramInt += 100;
		}
		
		getInstance(paramContext);
		int resourceId = getSmileyEmojiResourceId(paramContext, paramInt);
		if (resourceId == 0) { return null; }
		return paramContext.getResources().getDrawable(resourceId);
	}
	
	/**
	 * ȡ��Emoji��ԴId
	 * @param paramContext
	 * @param paramInt		��Ӧ���
	 * @return
	 */
	private static int getSmileyEmojiResourceId(Context paramContext, int paramInt)
	{
		int resourceId = 0;
		if ((paramInt >= 0) && (paramInt <= 99))
		{
			// ͨ��getIdentifier�õ�drawable��"smiley_" + paramInt��Ӧ����ԴId
			resourceId = paramContext.getResources().getIdentifier("smiley_" + paramInt, "drawable", paramContext.getPackageName());
			return resourceId;
		}
		
		getInstance(paramContext);
		switch (paramInt)
		{
			case 360:
			case 351:
			case 357:
			case 348:
			case 355:
			case 352:
			case 96:
			case 342:
			case 344:
			case 349:
			case 353:
			case 115:
			case 116:
			case 394:
			case 368:
			case 165:
			case 136:
			case 337:
			case 280:
			case 107:
				resourceId = paramContext.getResources().getIdentifier("emoji_" + paramInt, "drawable", paramContext.getPackageName());
				break;
		}
		return resourceId;
	}
	
	/**
	 * ȡ��Smily��Ӧ�ı�
	 * @param paramContext
	 * @param paramInt
	 * @return
	 */
	public static String getSmileyText(Context paramContext, int paramInt)
	{
		String str = "";
		if (paramInt < 0)
		{
			Log.w("MicroMsg.MergerSmileyManager", "get text, error index");
			return str;
		}

		if (getInstance(paramContext).mMerge_smiley_nameArray.length <= paramInt) {
			str = getEmojiText(paramContext, paramInt - 100);
		} else {
			str = getInstance(paramContext).mMerge_smiley_nameArray[paramInt];
		}
		return str;
	}
	
	/**
	 * ȡ��Emoji��Ӧ�ı�
	 * @param paramContext
	 * @param paramInt
	 * @return
	 */
	public static String getEmojiText(Context paramContext, int paramInt)
	{
		String str = "";
		
		if (paramInt < 0)
		{
			Log.w("MicroMsg.MergerSmileyManager", "get emoji text, error index down");
			return str;
		}
		
		if (getInstance(paramContext).gde.length <= paramInt)
		{
			Log.w("MicroMsg.MergerSmileyManager", "get emoji text, error index up");
			str = "";
		}
		else
		{
			str = getInstance(paramContext).gde[paramInt];
		}
		
		return str;
	}
}
