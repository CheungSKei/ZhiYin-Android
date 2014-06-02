package com.zhiyin.android.im.platformtools;

import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.zhiyin.android.im.R;
import com.zhiyin.android.im.application.BaseApplication;
import com.zhiyin.android.util.DebugUtils;
import com.zhiyin.android.util.StringUtils;

/**
 * Emoji������
 * 
 * @version 1.0.0
 * @date 2014-06-01
 * @author S.Kei.Cheung
 */
public class SmileyManager {
	
	private static SmileyManager mEmojiTools = null;
	private static Pattern mPattern;
	public static int EMOJIARRAY_COUNT = 6;
	private static final Comparator<EmojiObject> gdp = new EmojiObjectComparator();
	private String[] mSmiley_values = null;
	private String[] mSmiley_values_old = null;
	private String[] mSmiley_values_ch = null;
	private String[] mSmiley_values_tw = null;
	private String[] mSmiley_values_en = null;
	private String[] mSmiley_values_th = null;
	private EmojiObject[] mAllEmojiObjectArray = null;

	private SmileyManager() {
		
	}

	private SmileyManager(Context paramContext) {
		
		this.mSmiley_values = paramContext.getResources().getStringArray(
				R.array.smiley_values);
		this.mSmiley_values_old = paramContext.getResources().getStringArray(
				R.array.smiley_values_old);
		this.mSmiley_values_ch = paramContext.getResources().getStringArray(
				R.array.smiley_values_ch);
		this.mSmiley_values_tw = paramContext.getResources().getStringArray(
				R.array.smiley_values_tw);
		this.mSmiley_values_en = paramContext.getResources().getStringArray(
				R.array.smiley_values_en);
		this.mSmiley_values_th = paramContext.getResources().getStringArray(
				R.array.smiley_values_th);
		
		this.mAllEmojiObjectArray = new EmojiObject[EMOJIARRAY_COUNT * this.mSmiley_values.length];
		
		int emojiCount = 0;

		for (int j = 0; j < this.mSmiley_values.length; j++) {
			this.mAllEmojiObjectArray[emojiCount] = new EmojiObject(j,
					this.mSmiley_values[j]);
			emojiCount++;
		}

		for (int j = 0; j < this.mSmiley_values_old.length; j++) {
			this.mAllEmojiObjectArray[emojiCount] = new EmojiObject(j,
					this.mSmiley_values_old[j]);
			emojiCount++;
		}

		for (int j = 0; j < this.mSmiley_values_ch.length; j++) {
			this.mAllEmojiObjectArray[emojiCount] = new EmojiObject(j,
					this.mSmiley_values_ch[j]);
			emojiCount++;
		}

		for (int j = 0; j < this.mSmiley_values_tw.length; j++) {
			this.mAllEmojiObjectArray[emojiCount] = new EmojiObject(j,
					this.mSmiley_values_tw[j]);
			emojiCount++;
		}

		for (int j = 0; j < this.mSmiley_values_en.length; j++) {
			this.mAllEmojiObjectArray[emojiCount] = new EmojiObject(j,
					this.mSmiley_values_en[j]);
			emojiCount++;
		}

		for (int j = 0; j < this.mSmiley_values_th.length; j++) {
			this.mAllEmojiObjectArray[emojiCount] = new EmojiObject(j,
					this.mSmiley_values_th[j]);
			emojiCount++;
		}

		Arrays.sort(this.mAllEmojiObjectArray, gdp);
	}

	
	/**
	 * a(int start, Context context,SpannableString textValue, int emojiRect)
	 * ��SpannableString�а�Emoji��Ӧ���ַ���ת����ImageSpan
	 * @param context
	 * @param spannableString	�����ı�
	 * @param emojiRect			Emoji���/�߶�
	 * @return
	 */
	public static SpannableString insertEmoji(Context context,SpannableString spannableString, int emojiRect) {
		
		// ���ؿ�SpannableString
		if ((spannableString == null) || (spannableString.length() == 0)) {
			
			spannableString = new SpannableString("");
			
			return spannableString;
			
		}

		String str = spannableString.toString();
		
		int i = 0;
		int indexOfFirst = -1;
		
		do{
			indexOfFirst = str.indexOf('/', i);
			
			if(indexOfFirst == -1)
				break;
			
			if (indexOfFirst != -1 && indexOfFirst < -1 + str.length()) {
				 insertEmoji(indexOfFirst, context, spannableString, emojiRect);
			}
			
			i=indexOfFirst+1;
			
		}while(i<str.length());
		
		i = 0;
		do{
			indexOfFirst = str.indexOf('[', i);
		
			if(indexOfFirst == -1)
				break;
			
			if (indexOfFirst != -1 && indexOfFirst < -1 + str.length()) {
				insertEmoji(indexOfFirst, context, spannableString, emojiRect);
			}
			
			i=indexOfFirst+1;
		
		}while(i<str.length());
		
		return spannableString;
	}

	/**
	 * ͨ��ImageSpan����Drawable��SpannableString����
	 * 
	 * @param start
	 *            ���뿪ʼλ��
	 * @param context
	 *            ��ǰ������
	 * @param textValue
	 * @param emojiRect
	 *            Emoji���/�߶�
	 * @return
	 */
	private static boolean insertEmoji(int start, Context context,SpannableString textValue, int emojiRect) {
		
		boolean isInsertSuccess = false;
		
		EmojiObject emojiObject = searchEmojiObject(context,textValue.subSequence(start, textValue.length()).toString());
		
		int emojiResourceId;
		
		if (emojiObject != null) {
			
			emojiResourceId = context.getResources().getIdentifier("smiley_" + emojiObject.pos, "drawable",context.getPackageName());
			
			if (emojiResourceId != 0) {
				
				Drawable localDrawable = context.getResources().getDrawable(emojiResourceId);
				
				if (localDrawable != null) {
					
					localDrawable.setBounds(0, 0, (int) (1.3D * emojiRect),(int) (1.3D * emojiRect));
					
					textValue.setSpan(new ImageSpan(localDrawable, 0), start,start + emojiObject.text.length(), 33);
					
					isInsertSuccess = true;
				}
			}
		}

		return isInsertSuccess;
	}

	/**
	 * ͨ��Emoji�ַ���ֵ����EmojiObject����
	 * @param context
	 * @param textValue Emoji�ַ���ֵ
	 * @return
	 */
	private static EmojiObject searchEmojiObject(Context context, String textValue) {
		if (mEmojiTools == null) {
			mEmojiTools = new SmileyManager(context);
		}

		EmojiObject[] arrayOff = mEmojiTools.mAllEmojiObjectArray;

		// binarySearch:����������������У��򷵻������������������򷵻� (-(�����)- 1),ǰ������Ҫ�Ȱ�array��������
		int index = Arrays.binarySearch(arrayOff, new EmojiObject(0, textValue),gdp);

		if (index < 0) {
			index = -2 + -index;
		}

		// startWith:ȷ�����ַ���ʵ���Ŀ�ͷ�Ƿ���ָ�����ַ���ƥ��
		if (index >= 0 && textValue.startsWith(arrayOff[index].text)) {
			return arrayOff[index];
		}

		return null;
	}

	/**
	 * b(Context context,SpannableString spannableString, int emojiRect)
	 * 
	 * ͨ��ImageSpan����Drawable��SpannableString����
	 * @param context
	 * @param spannableString
	 * @param emojiRect
	 *            Emoji���/�߶�
	 * @return true:����ɹ�,false:����ʧ��
	 */
	public static boolean getInsertEmojiSuccessful(Context context,SpannableString spannableString, int emojiRect) {
		
		if (spannableString == null || spannableString.length() == 0) {
			return false;
		}

		String str = spannableString.toString();
		
		boolean insertSuccessful = false;
		int i = 0;
		int indexOfFirst = -1;
		
		do{
			indexOfFirst = str.indexOf('/', i);
			
			if(indexOfFirst == -1)
				break;
			
			if (indexOfFirst != -1 && indexOfFirst < -1 + str.length() && insertEmoji(indexOfFirst, context, spannableString, emojiRect)) {
				insertSuccessful = true;
			}
			
			i=indexOfFirst+1;
			
		}while(i<str.length());
		
		i = 0;
		do{
			indexOfFirst = str.indexOf('[', i);
		
			if(indexOfFirst == -1)
				break;
			
			if (indexOfFirst != -1 && indexOfFirst < -1 + str.length() && insertEmoji(indexOfFirst, context, spannableString, emojiRect)) {
				insertSuccessful = true;
			}
			
			i=indexOfFirst+1;
		
		}while(i<str.length());
			
		
		return insertSuccessful;
	}

	/**
	 * ���ػ�ȡ���ı���ѡ�����ȷλ��(��Emoji)
	 * @param context
	 * @param str		�ı�����
	 * @param position	ѡ��λ��
	 * @return
	 */
	public static int getSelectionPosition(Context context, String str, int position) {
		int i = 0;
		if (StringUtils.isEmpty(str)) {
			return 0;
		}

		// ���ı�ͷ���ı�β����ֱ�ӷ���
		int strLength = str.length();
		if (position == 0 || position == strLength) {
			return position;
		}
		
		// ��¼�ı�����±�
		int strMaxPosition = strLength;

		if (context == null) {
			
			DebugUtils.warn("MicroMsg.EmojiManager","setEmojiFailed, null context");

		}

		String[] arrayOfString1 = context.getResources().getStringArray(
				R.array.smiley_values);
		String[] arrayOfString2 = context.getResources().getStringArray(
				R.array.smiley_values_old);
		String[] arrayOfString3 = context.getResources().getStringArray(
				R.array.smiley_values_ch);
		String[] arrayOfString4 = context.getResources().getStringArray(
				R.array.smiley_values_tw);
		String[] arrayOfString5 = context.getResources().getStringArray(
				R.array.smiley_values_en);
		String[] arrayOfString6 = context.getResources().getStringArray(
				R.array.smiley_values_th);
		StringBuilder recordAllEmoji = new StringBuilder();

		int emojicount = arrayOfString1.length;
		for (int k = 0; k < emojicount; k++) {
			recordAllEmoji.append(Pattern.quote(arrayOfString1[k]));
			if (k != emojicount - 1) {
				recordAllEmoji.append('|');
			}
		}

		emojicount = arrayOfString2.length;
		for (int k = 0; k < emojicount; k++) {
			recordAllEmoji.append(Pattern.quote(arrayOfString2[k]));
			if (k != emojicount - 1) {
				recordAllEmoji.append('|');
			}
		}

		emojicount = arrayOfString3.length;
		for (int k = 0; k < emojicount; k++) {
			recordAllEmoji.append(Pattern.quote(arrayOfString3[k]));
			if (k != emojicount - 1) {
				recordAllEmoji.append('|');
			}
		}

		emojicount = arrayOfString4.length;
		for (int k = 0; k < emojicount; k++) {
			recordAllEmoji.append(Pattern.quote(arrayOfString4[k]));
			if (k != emojicount - 1) {
				recordAllEmoji.append('|');
			}
		}

		emojicount = arrayOfString5.length;
		for (int k = 0; k < emojicount; k++) {
			recordAllEmoji.append(Pattern.quote(arrayOfString5[k]));
			if (k != emojicount - 1) {
				recordAllEmoji.append('|');
			}
		}

		emojicount = arrayOfString6.length;
		for (int k = 0; k < emojicount; k++) {
			recordAllEmoji.append(Pattern.quote(arrayOfString6[k]));
			if (k != emojicount - 1) {
				recordAllEmoji.append('|');
			}
		}

		mPattern = Pattern.compile(recordAllEmoji.toString());

//		if (position >= EMOJIARRAY_COUNT || position + EMOJIARRAY_COUNT < strLength) {
//			strMaxPosition = position + EMOJIARRAY_COUNT;
//		}
//
//		String value = str.substring(i, strMaxPosition);
//		int emojiPostion = EMOJIARRAY_COUNT;
//
//		Matcher localMatcher = mPattern.matcher(value);
//		while (localMatcher.find()) {
//			if (emojiPostion > localMatcher.start() && emojiPostion < localMatcher.end()) {
//				emojiPostion = localMatcher.start();
//			}
//		}
//		TODO:
//		position += emojiPostion - EMOJIARRAY_COUNT;
//
//		i = position - EMOJIARRAY_COUNT;

		return position;
	}

	/**
	 * ���Emoji
	 * @param paramString �ı�value
	 * @return
	 */
	public static String removeEmoji(String paramString) {
		
		StringBuilder localStringBuilder = new StringBuilder(paramString.length());

		for (int j = 0; j < paramString.length(); j++) {
			
			char _char = paramString.charAt(j);
			
			if (_char == '/' || _char == '[') {
				
				EmojiObject emojiObject = searchEmojiObject(BaseApplication.getContext(),paramString.substring(j));
				
				if (emojiObject != null) {
					
					for (int k = 0; k < emojiObject.text.length(); k++) {
						localStringBuilder.append("");
					}
					
					j += emojiObject.text.length();
					
					continue;
				}
			}
			
			localStringBuilder.append(_char);
		}
		
		return localStringBuilder.toString();
	}

	static final class EmojiObject {
		public int pos;
		public String text;

		EmojiObject() {
		}

		EmojiObject(int pos, String text) {
			this.pos = pos;
			this.text = text;
		}
	}

	static final class EmojiObjectComparator implements Comparator<EmojiObject> {
		@Override
		public int compare(EmojiObject lhs, EmojiObject rhs) {
			return lhs.text.compareTo(rhs.text);
		}

	}
}
