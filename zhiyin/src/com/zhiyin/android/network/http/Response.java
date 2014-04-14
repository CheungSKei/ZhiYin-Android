package com.zhiyin.android.network.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhiyin.android.im.R;
import com.zhiyin.android.im.application.BaseApplication;
import com.zhiyin.android.im.exception.HttpResponseException;
import com.zhiyin.android.util.DebugUtils;
import com.zhiyin.android.util.StreamUtils;
import com.zhiyin.android.util.StringUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 封装网络请求响应结果.
 * 
 * @version 1.0.0
 * @date 2012-9-12
 * @author DengZhaoyong
 */
public class Response {
	
	private HttpResponse mResponse = null;

	/**
	 * 构造Response实例.
	 * 
	 * @param response
	 */
	public Response(HttpResponse response) {
		this.mResponse = response;
	}
	
	/**
	 * 将请求结果转换为输入流.
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public InputStream asStream() throws HttpResponseException {
		try {
			final HttpEntity entity = getHttpEntity();
			if (entity != null) {
				return entity.getContent();
			}
			return null;
		} catch (IllegalStateException e) {
			throw new HttpResponseException(e.getMessage());
		} catch (IOException e) {
			throw new HttpResponseException(e.getMessage());
		}
	}
	
	/**
	 * 将请求结果转换为字符串.
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public String asString() throws HttpResponseException {
		InputStream is = null;
		try {
			is = asStream();
			String result = StreamUtils.convertStreamToString(is);
			if (is == null || StringUtils.isEmpty(result)) {
				throw new HttpResponseException(BaseApplication.getContext().getString(R.string.server_no_response));
			}
			return result;
		} catch (IllegalStateException e) {
			throw new HttpResponseException(e.getMessage());
		} finally {
			try {
				getHttpEntity().consumeContent();
			} catch (IOException e) {}
			
			StreamUtils.closeInputStream(is);
			mResponse = null;
		}
	}
	
	/**
	 * 将请求结果转换为JSONObject.
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public JSONObject asJSONObject() throws HttpResponseException {
		String result = asString();
		try {
			return new JSONObject(result);
		} catch (JSONException jsonException) {
			DebugUtils.error("JSON格式有误: " + result, jsonException);
			throw new HttpResponseException(jsonException.getMessage());
		} finally {
			mResponse = null;
		}
	}
	
	/**
	 * 将请求结果转换为JSONArray.
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public JSONArray asJSONArray() throws HttpResponseException {
		String result = asString();
		try {
			return new JSONArray(asString());
		} catch (JSONException jsonException) {
			DebugUtils.error("JSON格式有误: " + result, jsonException);
			throw new HttpResponseException(jsonException.getMessage());
		} finally {
			mResponse = null;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public Bitmap asBitmap() throws HttpResponseException {
		try {
			return BitmapFactory.decodeStream(asStream());
		} catch (HttpResponseException e) {
			throw e;
		} finally {
			try {
				getHttpEntity().consumeContent();
			} catch (IOException e) {}
			
			mResponse = null;
		}
	}
	
	/**
	 * 获取org.apache.http.HttpEntity.
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public HttpEntity getHttpEntity() throws HttpResponseException {
		try {
			HttpEntity entity = mResponse.getEntity();
			if (entity == null) {
				throw new HttpResponseException("获取数据失败");
			}
			return entity;
		} catch (Exception e) {
			throw new HttpResponseException(e.getMessage());
		}
	}
	
}
