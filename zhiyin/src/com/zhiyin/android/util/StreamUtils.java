package com.zhiyin.android.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * I/O流操作工具类.
 * 
 * @version 1.0.0
 * @date 2012-9-12
 * @author DengZhaoyong
 */
public class StreamUtils {
	
	private StreamUtils() {}

	/**
	 * 将输入流转换为字节数组.
	 * 
	 * @param is 输入流
	 * @return
	 */
	public static byte[] convertStreamToByteArray(InputStream is) {
		if (is == null) {
			return null;
		}
		
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			baos.close();
			return baos == null ? null : baos.toByteArray();
		} catch (IOException e) {
			return null;
		} finally {
			closeOutputStream(baos);
		}
	}
	
	/**
	 * 将输入流转换为字符串.
	 * 
	 * @param is 输入流
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		byte[] byteArray = convertStreamToByteArray(is);
		if (byteArray != null && byteArray.length > 0) {
			try {
				return new String(byteArray, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// ignore.
			}
		}
		return "";
	}

	/**
	 * 关闭输入流, 释放资源.
	 * 
	 * @param is 输入流
	 */
	public static void closeInputStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				// ignore.
			}
		}
	}
	
	/**
	 * 关闭输出流, 释放资源.
	 * 
	 * @param os 输出流
	 */
	public static void closeOutputStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
				os = null;
			} catch (IOException e) {
				// ignore.
			}
		}
	}
	
	/**
	 * 将文件输入流写到文件.
	 * 
	 * @param is 输入流
	 * @param fos 文件输出流
	 * @return
	 */
	public static boolean saveStreamToFile(InputStream is, FileOutputStream fos) {
		if (is == null || fos == null) {
			return false;
		}
		
		try {
			int len = -1;
			byte[] b = new byte[1024];
			while ((len = is.read(b)) != -1) {
				fos.write(b, 0, len);
				fos.flush();
			}
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 *  执行序列化和反序列化  进行深度拷贝 
	 */  
    public static <T> ArrayList<T> deepCopy(ArrayList<T> src) throws IOException, ClassNotFoundException {  
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream out = new ObjectOutputStream(byteOut);  
        out.writeObject(src);  
  
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
        ObjectInputStream in = new ObjectInputStream(byteIn);  
        @SuppressWarnings("unchecked")  
        ArrayList<T> dest = (ArrayList<T>) in.readObject();  
        return dest;  
    }
}
