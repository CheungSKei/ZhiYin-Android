package com.zhiyin.android.im.ui.emoji;

import com.zhiyin.android.im.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Emoji资源ID与名称互转
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
	 * 返回Smiley个数
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
	 * 返回Emoji个数
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
	 * 初始化
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
	 * 传入编号，返回相应Drawable
	 * @param paramContext
	 * @param paramInt		对应编号
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
	 * 取得Emoji图片
	 * @param paramContext
	 * @param paramInt		对应编号
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
	 * 取得Emoji资源Id
	 * @param paramContext
	 * @param paramInt		对应编号
	 * @return
	 */
	private static int getSmileyEmojiResourceId(Context paramContext, int paramInt)
	{
		int resourceId = 0;
		if ((paramInt >= 0) && (paramInt <= 99))
		{
			// 通过getIdentifier得到drawable下"smiley_" + paramInt对应的资源Id
			resourceId = paramContext.getResources().getIdentifier("smiley_" + paramInt, "drawable", paramContext.getPackageName());
			return resourceId;
		}
		
		getInstance(paramContext);
		
		int emojiNum = 0;
		switch(paramInt){
			case 100:
				emojiNum = 360;
				break;
			case 101:
				emojiNum = 351;
				break;
			case 102:
				emojiNum = 357;
				break;
			case 103:
				emojiNum = 348;
				break;
			case 104:
				emojiNum = 355;
				break;
			case 105:
				emojiNum = 352;
				break;
			case 106:
				emojiNum = 96;
				break;
			case 107:
				emojiNum = 342;
				break;
			case 108:
				emojiNum = 344;
				break;
			case 109:
				emojiNum = 349;
				break;
			case 110:
				emojiNum = 353;
				break;
			case 111:
				emojiNum = 115;
				break;
			case 112:
				emojiNum = 116;
				break;
			case 113:
				emojiNum = 394;
				break;
			case 114:
				emojiNum = 368;
				break;
			case 115:
				emojiNum = 165;
				break;
			case 116:
				emojiNum = 136;
				break;
			case 117:
				emojiNum = 337;
				break;
			case 118:
				emojiNum = 280;
				break;
			case 119:
				emojiNum = 107;
				break;
			default:
				emojiNum = 0;
				break;
		}
		resourceId = paramContext.getResources().getIdentifier("emoji_" + emojiNum, "drawable", paramContext.getPackageName());
		return resourceId;
	}
	
	/**
	 * 取得Smily相应文本
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
	 * 取得Emoji相应文本
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
