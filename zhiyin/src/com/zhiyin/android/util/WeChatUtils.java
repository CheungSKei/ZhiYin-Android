package com.zhiyin.android.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.os.Vibrator;

public class WeChatUtils {
  private static final long[] cGN;

  static
  {
    long[] arrayOfLong = new long[4];
    arrayOfLong[0] = 300L;
    arrayOfLong[1] = 200L;
    arrayOfLong[2] = 300L;
    arrayOfLong[3] = 200L;
    cGN = arrayOfLong;
  }

  public static boolean B(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 0))
    	return false;
    
    return true;
  }

  public static long FA()
  {
    return System.currentTimeMillis() / 1000L;
  }

  public static long FB()
  {
    return System.currentTimeMillis();
  }

  public static long FC()
  {
    return SystemClock.elapsedRealtime();
  }

  public static long FD()
  {
    return 86400000L * (System.currentTimeMillis() / 86400000L);
  }

  public static String FG()
  {
    return "android-" + Build.MODEL + "-" + Build.VERSION.SDK_INT;
  }

  public static String L(long paramLong)
  {
    float f = Math.round(10.0F * (float)paramLong / 1048576.0F) / 10.0F;
    return f + "MB";
  }

  public static long M(long paramLong)
  {
    return System.currentTimeMillis() / 1000L - paramLong;
  }

  public static long N(long paramLong)
  {
    return System.currentTimeMillis() - paramLong;
  }

  public static long O(long paramLong)
  {
    return SystemClock.elapsedRealtime() - paramLong;
  }

  public static String Q(String paramString1, String paramString2)
  {
    if (paramString1 != null)
      paramString2 = paramString1;
    
    return paramString2;
  }

  public static int R(int paramInt1, int paramInt2)
  {
    if (paramInt1 <= paramInt2)
      DebugUtils.warn("getIntRandom failed upLimit:" + paramInt1 + "<= downLimit:" + paramInt2);
    
    int i = 0;
    
    i = paramInt2 + new Random(System.currentTimeMillis()).nextInt(1 + (paramInt1 - paramInt2));
    
    return i;
  }

  public static boolean S(int paramInt1, int paramInt2)
  {
    if (paramInt2 > 2.0D * paramInt1)
    	return false;
    return true;
  }

  public static boolean T(int paramInt1, int paramInt2)
  {
    if (paramInt1 > 2.0D * paramInt2)
    	return false;
    
    return true;
  }

  public static void a(Context paramContext, boolean paramBoolean)
  {
    Vibrator localVibrator = (Vibrator)paramContext.getSystemService("vibrator");
    if (localVibrator == null)
    	return;
    
    if (paramBoolean)
    {
        localVibrator.vibrate(cGN, -1);
    }
    localVibrator.cancel();
  }

  public static boolean a(Boolean paramBoolean, boolean paramBoolean1)
  {
    if (paramBoolean == null)
    	return paramBoolean1;
    
    return paramBoolean.booleanValue();
  }

  public static boolean ag(Context paramContext)
  {
    String str1 = paramContext.getClass().getName();
    String str2 = ah(paramContext);
    DebugUtils.debug("top activity=" + str2 + ", context=" + str1);
    return str2.equalsIgnoreCase(str1);
  }

  public static String ah(Context paramContext)
  {
    try
    {
    	return ((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getClassName();
    }catch (Exception localException){
    	return "(null)";
    }
  }

  public static long b(Long paramLong)
  {
    if (paramLong == null)
      return 0L;
  
    return paramLong.longValue();
  }

  public static boolean b(Boolean paramBoolean)
  {
    if (paramBoolean == null)
    	return false;
    	
    return paramBoolean.booleanValue();
  }

  public static int IntegerToInt(Integer paramInteger)
  {
    if (paramInteger == null)
    	return 0;
    return paramInteger.intValue();
  }

  
  public static List<String> arrayToList(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    	return null;
    
    ArrayList<String> localArrayList = new ArrayList<String>();

    for (int i = 0; i < paramArrayOfString.length; i++)
    	localArrayList.add(paramArrayOfString[i]);
    
    return localArrayList;
  }

  public static boolean c(Boolean paramBoolean)
  {
    if (paramBoolean == null)
    return false;
    
    return paramBoolean.booleanValue();
  }

  public static String eQ(int paramInt)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Long.valueOf(paramInt / 60L);
    arrayOfObject[1] = Long.valueOf(paramInt % 60L);
    return String.format(Locale.getDefault(),"%d:%02d", arrayOfObject);
  }

  public static boolean eR(int paramInt)
  {
    long l1 = 1000L * paramInt;
    long l2 = l1 - System.currentTimeMillis();
    DebugUtils.debug("time " + l1 + "  systime " + System.currentTimeMillis() + " diff " + l2);
    if (l2 < 0L)
    	return true;
    
    return false;
  }

  /**
   * 替换特殊符号
   * @param paramString
   * @return
   */
  public static String replaceSpecial(String paramString)
  {
    if (paramString != null)
      paramString = paramString.replace("\\[", "[[]").replace("%", "").replace("\\^", "").replace("'", "").replace("\\{", "").replace("\\}", "").replace("\"", "");
    return paramString;
  }

  /**
   * 判断是否属于邮箱
   * @param paramString
   * @return
   */
  public static boolean isEmail(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0))
    	return false;
    return paramString.trim().matches("^[a-zA-Z0-9][\\w\\.-]*@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
  }


  public static String hU(String paramString)
  {
    if (paramString == null)
      paramString = "";
    return paramString;
  }

  public static boolean isEmpty(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0))
    	return true;
    	
    return false;
  }

  public static boolean isChinese(String paramString)
  {
    if (isEmpty(paramString))
    	return false;
    
    return Pattern.compile("[\\u4e00-\\u9fa5]+").matcher(paramString).find();
  }

  // GENERAL_PUNCTUATION 判断中文的“号  
  // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号  
  // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
  /**
   * 判断当前字符是否属于中文
   * @param paramChar
   * @return
   */
  public static boolean isChinese(char paramChar)
  {
    Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock.of(paramChar);
    
    if ((localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) 
    		|| (localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) 
    		|| (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) 
    		|| (localUnicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION) 
    		|| (localUnicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) 
    		|| (localUnicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS))
    	return true;
    
    return false;
  }

}
