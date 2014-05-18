package com.zhiyin.android.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.zhiyin.android.im.application.BaseApplication;

/**
 * ��Ļ����
 * 
 * @version 1.0.0
 * @date 2013-1-29
 * @author S.Kei.Cheung
 */
public class ScreenUtils {

	private static float density = -1.0F;
	
	public static int fromDPToPix(int paramInt)
	{
		return Math.round(getDensity() * paramInt);
	}
	
	/**
	 * ��Ļ�ܶ�
	 * @param paramContext
	 * @return
	 */
	public static float getDensity()
	{
		if (density < 0.0F) {
			density = BaseApplication.getContext().getResources().getDisplayMetrics().density;
		}
		return density;
	}
	
	/**
	 * ͨ����ɫ��ԴIDȡ����ɫ״̬�б�
	 * @param paramInt
	 * @return
	 */
	public static ColorStateList getColorStateList(int paramInt)
	{
		return BaseApplication.getContext().getResources().getColorStateList(paramInt);
	}
	
	/**
	 * ͨ��ͼƬ��ԴIDȡ��Drawable
	 * @param paramInt
	 * @return
	 */
	public static Drawable getDrawable(int paramInt)
	{
		return BaseApplication.getContext().getResources().getDrawable(paramInt);
	}
	
	/**
	 * ����DP��С
	 * @param paramInt
	 * @return
	 */
	public static int getDimensionPixelSize(int paramInt)
	{
		return BaseApplication.getContext().getResources().getDimensionPixelSize(paramInt);
	}
	
	/**
	 * ȡ���ַ���
	 * @param paramInt
	 * @return
	 */
	public static String getString(int paramInt)
	{
		return BaseApplication.getContext().getResources().getString(paramInt);
	}
	
	/**
	 * ȡ��Dip��С
	 * @param paramInt
	 * @return
	 */
	public static int getDipSize(int paramInt)
	{
		return Math.round(paramInt / getDensity());
	}
}
