package com.zhiyin.android.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.zhiyin.android.im.application.BaseApplication;

/**
 * 屏幕工具
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
	 * 屏幕密度
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
	 * 通过颜色资源ID取得颜色状态列表
	 * @param paramInt
	 * @return
	 */
	public static ColorStateList getColorStateList(int paramInt)
	{
		return BaseApplication.getContext().getResources().getColorStateList(paramInt);
	}
	
	/**
	 * 通过图片资源ID取得Drawable
	 * @param paramInt
	 * @return
	 */
	public static Drawable getDrawable(int paramInt)
	{
		return BaseApplication.getContext().getResources().getDrawable(paramInt);
	}
	
	/**
	 * 返回DP大小
	 * @param paramInt
	 * @return
	 */
	public static int getDimensionPixelSize(int paramInt)
	{
		return BaseApplication.getContext().getResources().getDimensionPixelSize(paramInt);
	}
	
	/**
	 * 取得字符串
	 * @param paramInt
	 * @return
	 */
	public static String getString(int paramInt)
	{
		return BaseApplication.getContext().getResources().getString(paramInt);
	}
	
	/**
	 * 取得Dip大小
	 * @param paramInt
	 * @return
	 */
	public static int getDipSize(int paramInt)
	{
		return Math.round(paramInt / getDensity());
	}
}
