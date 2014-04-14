package com.zhiyin.android.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.text.TextUtils;
import android.util.Base64;

/**
 * 字符串工具类.
 * 
 * @version 2.0.0
 * @date 2014-01-29
 * @author S.Kei.Cheung
 */
public class StringUtils {
	
	private StringUtils() {}

	/**
	 * 判断字符是否为null或空串.
	 * 
	 * @param src 待判断的字符
	 * @return
	 */
	public static boolean isEmpty(String src) {
		return src == null || "".equals(src.trim())
				|| "null".equalsIgnoreCase(src);
	}
	
	/**
	 * 将指定的字符串进行Base64编码.
	 * 
	 * @param src 待编码的字符串
	 * @return
	 */
	public static String base64Encode(String src) {
		return Base64.encodeToString(src.getBytes(), Base64.DEFAULT);
	}
	
	/**
     * 验证输入的邮箱格式是否符合
     * 
     * @param email
     * @return 是否合法
     */
	public static boolean emailFormat(String email) {
        boolean tag = true;
        final String pattern1 = "\\w+@(\\w+\\.){1,3}\\w+";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }
	
	/**
	 * String是否有特殊字符
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static boolean StringSpecial(String str) throws PatternSyntaxException {      
		boolean tag = true;
		final String regEx = "[^a-zA-Z0-9]";
		final Pattern pattern = Pattern.compile(regEx);
		final Matcher mat = pattern.matcher(str);
		if (mat.find()) {
			tag = false;
		}
		return tag;
    }
	
	/**
	 * 反转义
	 * @param url
	 * @return
	 */
	public static String unescapeUnicode(String url) {
		if (isEmpty(url)) {
			return "";
		}
		String result = url;
		String[][] unescape = new String[][]{
				new String[]{"\\\\", ""},
				new String[]{"\\\"", "\""},
				new String[]{"\\'", "'"},
		};
		for (String[] item : unescape) {
			result = result.replaceAll(item[0], item[1]);
		}
		
		return result;
	}
	
	/**
	 * 将字符串解析成double类型
	 * 
	 * @param value
	 * @return
	 */
	public static double convertStringToDouble(String value) {
		double rlt = 0;
		try {
			rlt = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			// ignore.
		}
		return rlt;
	}
	
	/**
	 * 转换生日格式为 yyyy-MM-dd.
	 * 
	 * @param birthday
	 * @return
	 */
	public static String replaceBirthday(String birthday) {
		if (isEmpty(birthday)) {
			return "";
		}
		
		return birthday.replaceAll("(\\d{4}-\\d{1,2}-\\d{1,2}).*", "$1");
	}
	
	/**
	 * 下拉列表null值处理
	 * 
	 * @param value 需要处理的值
	 * @return 处理后的值
	 * */
	public static String transformNullValue(String value) {
		return isEmpty(value) ? "请选择" : value; 
	}
	
	/**
	 * 字符串大写转换小写处理
	 * 
	 * @param str 需要处理的值
	 * @return 处理后的值
	 * */
	public static String changeAa(String str) {
    	
    	StringBuffer sb = new StringBuffer();
    	for(int i=0; i<str.length(); i++) {
    		
    		char c = str.charAt(i); 
    		if(c >= 'A' && c <= 'Z') c = (char)(c - 'A' + 'a'); 
    		sb.append(c);
    	}
    	
    	return sb.toString();
    }
	
	/**
	 * 将字符串转换为整形
	 * 
	 * @param value
	 * @return
	 */
	public static int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 得到中英文混合字符串长度.
	 * 
	 * @param src
	 * @param endcoding
	 * @return
	 */
	public static int getChineseLength(String src, String endcoding) {
		// 定义返回的字符串长度
		int len = 0;
		try {
			int j = 0;
			// 按照指定编码得到byte[]
			byte[] byteValue = src.getBytes(endcoding);
			while (true) {
				short tmpst = (short) (byteValue[j] & 0xF0);
				if (tmpst >= 0xB0) {
					if (tmpst < 0xC0) {
						j += 2;
						len += 2;
					} else if ((tmpst == 0xC0) || (tmpst == 0xD0)) {
						j += 2;
						len += 2;
					} else if (tmpst == 0xE0) {
						j += 3;
						len += 2;
					} else if (tmpst == 0xF0) {
						short tmpst0 = (short) (((short) byteValue[j]) & 0x0F);
						if (tmpst0 == 0) {
							j += 4;
							len += 2;
						} else if ((tmpst0 > 0) && (tmpst0 < 12)) {
							j += 5;
							len += 2;
						} else if (tmpst0 > 11) {
							j += 6;
							len += 2;
						}
					}
				} else {
					j += 1;
					len += 1;
				}
				if (j > byteValue.length - 1) {
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			return 0;
		}
		return len;
	}
	
	/**
	 * 将指定的字符串进行Base64解码.
	 * 
	 * @param src 待解码的字符串
	 * @return
	 */
	public static String base64Decode(String src) {
		return new String(Base64.decode(src, Base64.DEFAULT));
	}
	
	/**
	 * 得到UTF-8编码的中英文混合字符串长度.
	 * 
	 * @param src
	 * @return
	 */
	public static int getChineseLength(String src) {
		return getChineseLength(src, "UTF-8");
	}
	
	public static int getLength(String src) {
		if (src == null) {
			return 0;
		}
		return src.length();
	}
	
	/**
	 * 移除字符串数组中的第一个元素.
	 * 
	 * @param original
	 * @return
	 */
	public static String[] removeFirstItem(String[] original) {
        if (original == null || original.length == 0) {
        	return new String[]{};
        }
		int newLength = original.length - 1;
        String[] copy = new String[newLength];
        System.arraycopy(original, 1, copy, 0, newLength);
        return copy;
	}
	
	/**
	 * 手机号码正则表达式
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){        
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");        
        Matcher m = p.matcher(mobiles);    
        return m.matches();        
    }
	
	/**
	 * 密码正则表达式
	 * @param password
	 * @return
	 */
	public static boolean isPasswordFormat(String password){        
        Pattern p = Pattern.compile("^[A-Za-z0-9]*$");      
        Matcher m = p.matcher(password);    
        return m.matches();        
    }
	
	/**
	 * 昵称和手机号正则表达式
	 * @param nickNameID
	 * @return
	 */
	public static boolean isNumberFormat(String nickNameID){        
        Pattern p = Pattern.compile("^[0-9]*$");      
        Matcher m = p.matcher(nickNameID);    
        return m.matches();        
    }
	
	/**
	 * 中英文数字正则表达式(4-16位)
	 * @param str
	 * @return
	 */
	public static boolean isChineseEnglishFormat(String str){        
        Pattern p = Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2DA-Za-z0-9]+$");      
        Matcher m = p.matcher(str); 
        str=str.replaceAll("[^\\x00-\\xff]", "**");	//匹配双字节字符
        int length = str.length();
        return m.matches()&&(length>=4&&length<=16);
    }
	
	/** 
     * 截取一段字符的长度(汉、日、韩文字符长度为2),不区分中英文,如果数字不正好，则少取一个字符位 
     *  
     * @param str 原始字符串 
     * @param specialCharsLength 截取长度(汉、日、韩文字符长度为2) 
     * @return 
     */  
    public static String trim(String str, int specialCharsLength) {  
        if (str == null || "".equals(str) || specialCharsLength < 1) {  
            return "";  
        }  
        char[] chars = str.toCharArray();  
        int charsLength = getCharsLength(chars, specialCharsLength);  
        return new String(chars, 0, charsLength);  
    }
    
    /** 
     * 获取一段字符的长度，输入长度中汉、日、韩文字符长度为2，输出长度中所有字符均长度为1 
     * @param chars 一段字符 
     * @param specialCharsLength 输入长度，汉、日、韩文字符长度为2 
     * @return 输出长度，所有字符均长度为1 
     */  
    private static int getCharsLength(char[] chars, int specialCharsLength) {  
        int count = 0;  
        int normalCharsLength = 0;  
        for (int i = 0; i < chars.length; i++) {  
            int specialCharLength = getSpecialCharLength(chars[i]);  
            if (count <= specialCharsLength - specialCharLength) {  
                count += specialCharLength;  
                normalCharsLength++;  
            } else {  
                break;  
            }  
        }  
        return normalCharsLength;  
    }  
    
    
    /** 
     * 获取一段字符的长度，输入长度中汉、日、韩文字符长度为2，输出长度中所有字符均长度为1 
     * @param str 一段字符 
     * @return 输出长度，所有字符均长度为1 
     */  
    public static int getStringLength(String str) {  
        int normalCharsLength = 0;  
        char[] chars = str.toCharArray();  
        for (int i = 0; i < chars.length; i++) {  
          normalCharsLength+=getSpecialCharLength(chars[i]);  
        }  
        return normalCharsLength;
    }  
    
    /** 
     * 字符串截取，输入长度中汉、日、韩文字符长度为2，输出长度中所有字符均长度为1，超过长度尾部加上<b>...</b>
     * @param str 一段字符 
     * @param limitLength 长度限制，超出截取尾部加上<b>...</b>
     * @return 输出长度，所有字符均长度为1 
     */  
    public static String subLength(String str,int limitLength) {  
    	final int length=getStringLength(str);
    	String content=str;
    	if(length>limitLength){
    		content=StringUtils.trim(str,limitLength)+"...";
    	}
    	return content;
    }  
  
    /** 
     * 获取字符长度：汉、日、韩文字符长度为2，ASCII码等字符长度为1 
     * @param c 字符 
     * @return 字符长度 
     */  
    private static int getSpecialCharLength(char c) {  
        if (isLetter(c)) {  
            return 1;  
        } else {  
            return 2;  
        }  
    }  
  
    /** 
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符） 
     *  
     * @param char c, 需要判断的字符 
     * @return boolean, 返回true,Ascill字符 
     */  
    private static boolean isLetter(char c) {  
        int k = 0x80;  
        return c / k == 0 ? true : false;  
    }

    /**
     * 字符串用符号隔开
     * @param strs
     * @return
     */
    public static String apartString(CharSequence s, String[] strs) {
    	return TextUtils.join(s, strs);
    }

    /**
     * 为字符串添加双引号
     * @param obj 字符串
     * @return 加入双引号后的完整字符串
     */
    public static String addQuotationMarks(String obj){
    	return "\""+obj+"\"";
    }
    
    /**
     * checks if a String is whitespace,empty("") or null
     * @param str
     * @return
     */
    public static boolean isBlacnk(String str){
    	int strLen;
    	if(str == null || (strLen = str.length()) == 0){
    		return true;
    	}
    	for(int i = 0; i < strLen; i++){
    		if((Character.isWhitespace(str.charAt(i)) == false)){
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * checks if a String is not empty (""),not null and whitespace only.
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str){
    	return !StringUtils.isBlacnk(str);
    }
}
