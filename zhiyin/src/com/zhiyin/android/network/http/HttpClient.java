package com.zhiyin.android.network.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;

import com.zhiyin.android.constant.NetworkType;
import com.zhiyin.android.im.R;
import com.zhiyin.android.im.application.BaseApplication;
import com.zhiyin.android.im.exception.HttpRequestException;
import com.zhiyin.android.im.exception.HttpResponseException;
import com.zhiyin.android.im.exception.HttpTimeoutException;
import com.zhiyin.android.im.exception.IllegalArgsException;
import com.zhiyin.android.im.exception.NetworkNotFoundException;
import com.zhiyin.android.im.exception.OutOfMemeryException;
import com.zhiyin.android.im.model.UploadFile;
import com.zhiyin.android.util.DebugUtils;
import com.zhiyin.android.util.DeviceUtils;
import com.zhiyin.android.util.StringUtils;

/**
 * 封装网络请求操作.
 * 
 * @version 1.0.0
 * @date 2012-9-12
 * @author DengZhaoyong
 */
public class HttpClient {

	// 设置连接请求超时时间
	private static final int CONNECTION_TIMEOUT_MS = 8 * 1000;
	private static final int SOCKET_TIMEOUT_MS = 24 * 1000;
	// 在发生异常时自动重试次数
	private static final int AUTO_RETRY_TIMES = 3;
	// 设置最大总连接数(暂时与线程池的最大个数一致)
	private static final int MAX_TOTAL_CONNECTIONS = 48;
	// 连接最大的空闲时间(单位: 秒)
	private static final Integer MAX_IDLE_TIME_OUT = 60;

	// 声明APACHE HttpClient实例
	private DefaultHttpClient mHttpClient;

	// HTTP Cookies
	private static HashMap<String, String> mCookies = new HashMap<String, String>();

	/** 服务器地址 */
	private static String mServerAddress;

	/** 手机客户端渠道号 */
	private static String mChannelId;
	/** Android子渠道号 */
	private static String mSubChannelId;

	/** CMWAP(移动, 联通)GPRS连接, 设置代理 */
	private HttpHost mCmwapProxy = new HttpHost("10.0.0.172", 80, "http");
	/** CTWAP(电信)GPRS连接, 设置代理 */
	private HttpHost mCtwapProxy = new HttpHost("10.0.0.200", 80, "http");

	private Context mContext = null;

	// 提交版本信息，用于动作统计
	private static String appVersion = "1.0.0";

	/**
	 * 构造HttpClient实例
	 */
	public HttpClient(Context context) {
		this.mContext = context;
		initConfig();
		initHttpClient();
	}

	/**
	 * 初始化配置信息
	 */
	private void initConfig() {
		// 初始化渠道号
		mChannelId = mContext.getResources().getString(R.string.channel_id);
		mSubChannelId = mContext.getResources().getString(
				R.string.sub_channel_id);
		try {
			appVersion = BaseApplication
					.getContext()
					.getPackageManager()
					.getPackageInfo(
							BaseApplication.getContext().getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// ignore
		}

		// 初始化服务器地址
		String publishEnvironment = mContext.getResources().getString(
				R.string.publish_environment);
		if ("formal".equals(publishEnvironment)) {
			mServerAddress = mContext.getResources().getString(
					R.string.server_address_formal);
		} else if ("test".equals(publishEnvironment)) {
			mServerAddress = mContext.getResources().getString(
					R.string.server_address_test);
		} else if ("personal".equals(publishEnvironment)) {
			mServerAddress = mContext.getResources().getString(
					R.string.server_address_personal);
		}
	}

	/**
	 * 初始化HttpClient基本配置
	 */
	private void initHttpClient() {
		// 初始化Http参数
		BasicHttpParams basicHttpParams = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(basicHttpParams,
				MAX_TOTAL_CONNECTIONS);
		HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);

		// 注册协议管理类型
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));

		// 创建带ThreadSafeClientConnManager的HttpClient实例
		ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(
				basicHttpParams, schemeRegistry);
		threadSafeClientConnManager.closeIdleConnections(MAX_IDLE_TIME_OUT,
				TimeUnit.SECONDS);
		// 初始化HttpClient实例
		mHttpClient = new DefaultHttpClient(threadSafeClientConnManager,
				basicHttpParams);

		/*
		 * 设置请求与响应拦截器. 使用GZIP压缩和解压请求, 可以更好的节省电池的电量.
		 */
		mHttpClient.addRequestInterceptor(gzipReqInterceptor);
		// Support GZIP
		mHttpClient.addResponseInterceptor(gzipResInterceptor);
	}

	/**
	 * 设置WAP网络请求代理服务器.
	 * <p>
	 * 移动,联通: httpClient.setProxy("10.0.0.172", 80, "http");<br>
	 * 电信: httpClient.setProxy("10.0.0.200", 80, "http");
	 * </p>
	 * 
	 * @param host
	 *            主机域名或IP地址
	 * @param port
	 *            端口号
	 * @param scheme
	 *            协议(http or https)
	 */
	public void setProxy(String host, int port, String scheme) {
		HttpHost proxy = new HttpHost(host, port, scheme);
		setProxy(proxy);
	}

	/**
	 * 设置WAP网络请求代理服务器.
	 * 
	 * @param proxy
	 */
	public void setProxy(HttpHost proxy) {
		mHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
	}

	/**
	 * 移除代理服务器.
	 */
	public void removeProxy() {
		mHttpClient.getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
	}

	/**
	 * 关闭网络连接.
	 */
	public void shutdown() {
		mHttpClient.getConnectionManager().shutdown();
	}

	/**
	 * 添加Cookie.
	 * 
	 * @param cookieName
	 * @param cookieValue
	 */
	public void addCookie(String cookieName, String cookieValue) {
		mCookies.put(cookieName, cookieValue);
	}

	/**
	 * 清空Cookie
	 */
	public void clearCookie() {
		mCookies.clear();
	}

	public Response get(String url) throws HttpRequestException,
			NetworkNotFoundException, IllegalArgsException,
			HttpTimeoutException, HttpResponseException, OutOfMemeryException {

		return get(url, null);
	}

	public Response get(String url, ArrayList<BasicNameValuePair> params)
			throws HttpRequestException, NetworkNotFoundException,
			IllegalArgsException, HttpTimeoutException, HttpResponseException,
			OutOfMemeryException {
		if (params == null) {
			params = new ArrayList<BasicNameValuePair>();
		}
		params.add(new BasicNameValuePair("channelId", mChannelId));
		params.add(new BasicNameValuePair("subChannelId", mSubChannelId));
		params.add(new BasicNameValuePair("versionNo", appVersion));

		if (url.indexOf("?") < 0) {
			url += "?" + encodeParams(params);
		} else {
			url += "&" + encodeParams(params);
		}

		return request(url, params, null, HttpGet.METHOD_NAME);
	}

	public Response post(String url) throws HttpRequestException,
			NetworkNotFoundException, HttpTimeoutException,
			HttpResponseException, NotFoundException, OutOfMemeryException {

		return post(url, null);
	}

	public Response post(String url, ArrayList<BasicNameValuePair> params)
			throws HttpRequestException, NetworkNotFoundException,
			HttpTimeoutException, HttpResponseException, OutOfMemeryException {

		return post(url, params, null);
	}

	public Response post(String url, ArrayList<BasicNameValuePair> params,
			UploadFile uploadFile) throws HttpRequestException,
			NetworkNotFoundException, HttpTimeoutException,
			HttpResponseException, OutOfMemeryException {
		if (params == null) {
			params = new ArrayList<BasicNameValuePair>();
		}

		params.add(new BasicNameValuePair("channelId", mChannelId));
		params.add(new BasicNameValuePair("subChannelId", mSubChannelId));
		params.add(new BasicNameValuePair("versionNo", appVersion));

		return request(url, params, uploadFile, HttpPost.METHOD_NAME);
	}

	/**
	 * 发送网络请求.
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param uploadFileVo
	 *            上传文件实体VO
	 * @param getOrPost
	 *            请求方式: GET or POST
	 * @return
	 * @throws NetworkNotFoundException
	 * @throws HttpRequestException
	 * @throws HttpTimeoutException
	 * @throws HttpResponseException
	 * @throws OutOfMemeryException
	 */
	public Response request(String url, ArrayList<BasicNameValuePair> params,
			UploadFile uploadFile, String getOrPost)
			throws NetworkNotFoundException, HttpRequestException,
			HttpTimeoutException, HttpResponseException, OutOfMemeryException {
		DebugUtils.debug("sending " + getOrPost + " request to [" + url
				+ "]...");

		// 设置网络代理
		setupNetworkProxy();

		HttpUriRequest uriRequest = null;
		HttpResponse httpResponse = null;
		Response result = null;

		URI uri = createURI(url);

		// 创建 GET or POST uriRequest
		uriRequest = createUriRequest(uri, params, uploadFile, getOrPost);
		// 设置HTTP连接请求参数
		setupHTTPConnectionParams(uriRequest);
		try {
			httpResponse = mHttpClient.execute(uriRequest);
			result = new Response(httpResponse);
		} catch (ConnectTimeoutException e) {
			uriRequest.abort();
			DebugUtils.error(e.getMessage(), e);
			throw new HttpTimeoutException(mContext.getResources().getString(
					R.string.http_request_timeout_exception));
		} catch (SocketTimeoutException e) {
			uriRequest.abort();
			DebugUtils.error(e.getMessage(), e);
			throw new HttpTimeoutException(mContext.getResources().getString(
					R.string.http_request_timeout_exception));
		} catch (Exception e) {
			uriRequest.abort();
			DebugUtils.error(e.getMessage(), e);
			throw new HttpResponseException(mContext.getResources().getString(
					R.string.server_no_response), e);
		} catch (OutOfMemoryError e) {
			uriRequest.abort();
			OutOfMemeryException exception = new OutOfMemeryException(mContext
					.getResources()
					.getString(R.string.server_request_exception));
			DebugUtils.error(e.getMessage(), exception);
			throw exception;
		} catch (AssertionError e) {
			uriRequest.abort();
			OutOfMemeryException exception = new OutOfMemeryException(mContext
					.getResources()
					.getString(R.string.server_request_exception));
			DebugUtils.error(e.getMessage(), exception);
			throw exception;
		}

		if (httpResponse == null
				|| httpResponse.getStatusLine().getStatusCode() != 200) {
			uriRequest.abort();
			DebugUtils.error("http response code from [" + url + "] is: "
					+ httpResponse.getStatusLine().getStatusCode(), null);
			throw new HttpResponseException(mContext.getResources().getString(
					R.string.server_no_response));
		}

		// 强制关闭连接
		mHttpClient.getConnectionManager().closeExpiredConnections();

		return result;
	}

	/**
	 * 请求拦截器, 设置请求支持GZIP.
	 */
	private static HttpRequestInterceptor gzipReqInterceptor = new HttpRequestInterceptor() {
		@Override
		public void process(HttpRequest request, HttpContext context)
				throws HttpException, IOException {
			String gzipHead = "Accept-Encoding";
			if (!request.containsHeader(gzipHead)) {
				request.addHeader(gzipHead, "gzip");
			}
		}
	};

	/**
	 * 响应拦截器, 解码经过GZIP压缩的响应结果.
	 */
	private static HttpResponseInterceptor gzipResInterceptor = new HttpResponseInterceptor() {
		@Override
		public void process(HttpResponse response, HttpContext context)
				throws HttpException, IOException {
			HttpEntity entity = response.getEntity();
			Header header = entity.getContentEncoding();
			if (header != null) {
				HeaderElement[] codecs = header.getElements();
				for (int i = 0; i < codecs.length; i++) {
					if (codecs[i].getName().indexOf("gzip") >= 0) {
						response.setEntity(new GzipDecompressingEntity(response
								.getEntity()));
						return;
					}
				}
			}
		}
	};

	/**
	 * 设置网络代理服务器
	 * 
	 * @throws NetworkNotFoundException
	 */
	private void setupNetworkProxy() throws NetworkNotFoundException {
		// 判断网络类型
		switch (DeviceUtils.checkNetWorkType(mContext)) {
		case NetworkType.NET:
			// NET连接时移除代理
			removeProxy();
			break;
		case NetworkType.CMWAP:
			// CMWAP(移动, 联通)GPRS连接, 设置代理
			setProxy(mCmwapProxy);
			break;
		case NetworkType.CTWAP:
			// CTWAP(电信)GPRS连接, 设置代理
			setProxy(mCtwapProxy);
			break;
		default:
			// 抛出无网络异常
			throw new NetworkNotFoundException(mContext.getResources()
					.getString(R.string.no_network_connected));
		}
	}

	private URI createURI(String url) throws HttpRequestException {
		URI uri = null;
		try {
			if (!url.startsWith("http://")) {
				url = mServerAddress + url;
			}
			uri = new URI(url);
		} catch (URISyntaxException e) {
			// 服务器请求失败
			throw new HttpRequestException(mContext.getResources().getString(
					R.string.server_request_exception));
		}
		return uri;
	}

	private HttpUriRequest createUriRequest(URI uri,
			ArrayList<BasicNameValuePair> params, UploadFile uploadFile,
			String getOrPost) throws HttpRequestException {
		HttpUriRequest uriRequest = null;

		if (HttpPost.METHOD_NAME.equalsIgnoreCase(getOrPost)) {
			HttpPost post = new HttpPost(uri);
			post.getParams().setBooleanParameter(
					"http.protocol.expect-continue", false);
			try {
				HttpEntity requestEntity = null;
				// 带文件上传POST请求
				if (uploadFile != null && uploadFile.file != null
						&& uploadFile.file.exists()) {
					requestEntity = (HttpEntity) createMultipartEntity(params,
							uploadFile);
				}
				// 普通文本POST请求
				else if (params != null) {
					requestEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				}
				post.setEntity(requestEntity);
			} catch (Exception e) {
				throw new HttpRequestException(e.getMessage());
			}
			uriRequest = post;
		} else {
			uriRequest = new HttpGet(uri);
		}

		return uriRequest;
	}

	private MultipartEntityBuilder createMultipartEntity(
			ArrayList<BasicNameValuePair> params, UploadFile uploadFile)
			throws UnsupportedEncodingException {
		MultipartEntityBuilder multipartEntity = MultipartEntityBuilder
				.create();
		multipartEntity.addPart(uploadFile.parameterName, new FileBody(
				uploadFile.file));
		if (params != null) {
			for (BasicNameValuePair param : params) {
				multipartEntity.addTextBody(
						param.getName(),
						param.getValue());
			}
		}
		return multipartEntity;
	}

	/**
	 * 设置HTTP连接请求参数
	 */
	private void setupHTTPConnectionParams(HttpUriRequest uriRequest) {
		HttpConnectionParams.setConnectionTimeout(uriRequest.getParams(),
				CONNECTION_TIMEOUT_MS);
		HttpConnectionParams.setSoTimeout(uriRequest.getParams(),
				SOCKET_TIMEOUT_MS);

		mHttpClient.setHttpRequestRetryHandler(mRequestRetryHandler);

		uriRequest.addHeader("Accept-Encoding", "gzip, deflate");
		uriRequest.addHeader("Accept-Charset", "UTF-8,*;q=0.5");
		uriRequest.addHeader("Cache-Control", "no-cache");

		addCookieToHeader(uriRequest);
	}

	// 添加cookies
	private void addCookieToHeader(HttpUriRequest uriRequest) {
		if (!mCookies.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> cookie : mCookies.entrySet()) {
				sb.append(cookie.getKey()).append("=")
						.append(cookie.getValue()).append(";");
			}
			if (!StringUtils.isEmpty(sb.toString())) {
				uriRequest.addHeader("Cookie", sb.toString());
			}
		} else {
			uriRequest.removeHeaders("Cookie");
		}
	}

	private String encodeParams(ArrayList<BasicNameValuePair> params)
			throws IllegalArgsException {
		if (params == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			if (i != 0) {
				sb.append("&");
			}
			try {
				sb.append(URLEncoder.encode(params.get(i).getName(), "UTF-8"));
				sb.append("=");
				sb.append(URLEncoder.encode(params.get(i).getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new IllegalArgsException(e.getMessage(), e);
			}
		}

		return sb.toString();
	}

	/**
	 * 请求异常自动恢复处理策略
	 */
	private static HttpRequestRetryHandler mRequestRetryHandler = new HttpRequestRetryHandler() {
		@Override
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			DebugUtils.warn("!!! occured [" + exception
					+ "], retryRequest times: " + executionCount);
			// Auto retry X times
			if (executionCount > AUTO_RETRY_TIMES) {
				return false;
			}
			// Retry if the server dropped connection on us
			if (exception instanceof NoHttpResponseException) {
				return true;
			}
			// fixed "java.net.SocketException: Broken pipe" bug
			else if (exception instanceof SocketException) {
				return true;
			} else if (exception instanceof ClientProtocolException) {
				return true;
			}
			// Do not retry on SSL handshake exception
			else if (exception instanceof SSLHandshakeException) {
				return false;
			}

			return false;
		}
	};

	/**
	 * GZIP实体解压缩实现类
	 */
	private static class GzipDecompressingEntity extends HttpEntityWrapper {

		public GzipDecompressingEntity(final HttpEntity entity) {
			super(entity);
		}

		@Override
		public InputStream getContent() throws IOException,
				IllegalStateException {
			InputStream wrappedInputStream = wrappedEntity.getContent();
			return new GZIPInputStream(wrappedInputStream);
		}

		@Override
		public long getContentLength() {
			return -1;
		}
	}

	public String getServerAddress() {
		return mServerAddress;
	}

	public HashMap<String, String> getCookies() {
		return mCookies;
	}

}
