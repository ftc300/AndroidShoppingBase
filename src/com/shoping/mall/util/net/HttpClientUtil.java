package com.shoping.mall.util.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shoping.mall.ConstantValue;
import com.shoping.mall.GlobalParams;
import com.shoping.mall.engine.MyError;


public class HttpClientUtil {
	
	private HttpClient client;
	
	private HttpPost post;
	private HttpGet get;

	private HttpParams httpParams;
	
	
	public static Header[] headers;
	
	static {
		GlobalParams.myError = new MyError();
		headers = new Header[10];
		headers[0] = new BasicHeader("appkey", "12343");
		headers[1] = new BasicHeader("imei", "");
		headers[2] = new BasicHeader("os", "android");
		headers[3] = new BasicHeader("osversion", "");
		headers[4] = new BasicHeader("appversion", "");
		headers[5] = new BasicHeader("sourceid", "");
		headers[6] = new BasicHeader("ver", "");
		headers[7] = new BasicHeader("userid", "");
		//2537a732-dccf-49c5-b562-82fe8095b2e3
		headers[8] = new BasicHeader("usersession", "");
		headers[9] = new BasicHeader("unique", "");
	}
	
	
	
	
	public HttpClientUtil() {
		client = new DefaultHttpClient();
		if(StringUtils.isNotBlank(GlobalParams.PROXY)) {
			//设置代理信息
			HttpHost host = new HttpHost(GlobalParams.PROXY,GlobalParams.PORT);
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
		}
		
		httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
		HttpConnectionParams.setSoTimeout(httpParams, 8000);
		
	}
	
	/**
	 * 发�?xml信息给服务器
	 * @param uri 服务器的uri
	 * @param xml 发�?的xml信息
	 * @return 返回连接服务器的输入�?, 失败时返回null
	 */
	public InputStream sendXml(String uri,String xml) {
		post = new HttpPost(uri);
		try {
			HttpEntity entity = new StringEntity(xml,ConstantValue.ENCODING);
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			
			if(response.getStatusLine().getStatusCode() == 200) {
				return response.getEntity().getContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/***************************商城**********************************/
	
	//采用短链接的http协议进行通讯,提交方式为post和get两种方式，返回的数据类型为JSON
	
	/**
	 * 发送post请求
	 * @param uri 服务器地址
	 * @param params 键值对参数
	 * @return 服务器返回的json字符串
	 */
	public String sendPost1(String uri,Map<String,String> params) {
		post = new HttpPost(uri);
		post.setHeaders(headers);
		post.setParams(httpParams);
		if(params != null && params.size()>0) {
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			for(Map.Entry<String, String> item: params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(item.getKey(),item.getValue());
				pairs.add(pair);
			}
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs,ConstantValue.ENCODING);
				post.setEntity(entity);
			} catch (Exception e) {
				GlobalParams.myError.setText("服务器正忙..");
				e.printStackTrace();
			}
		}
		
		try {
			HttpResponse response = client.execute(post);
			
			if(response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), ConstantValue.ENCODING);
			}
		} catch (Exception e) {
			GlobalParams.myError.setText("服务器正忙..");
			e.printStackTrace();
		} finally {
			if(client != null) {
				client.getConnectionManager().shutdown();
			}
		}
		return null;
	}
	
	/**
	 * 发送get请求
	 * @param uri 服务器地址
	 * @param params 请求参数
	 * @return 服务器返回的json字符串,失败则返回null
	 */
	public String sendGet1(String uri,Map<String,String> params){
		String data="";
		if(params != null) {
			StringBuffer buffer = new StringBuffer();
			for(Map.Entry<String, String> item : params.entrySet()) {
				buffer.append("&").append(item.getKey()+"=").append(item.getValue());
			}
			data = "?"+buffer.substring(1);
		}else {
			System.out.println("get params null");
		}
		
//		String data = "username=123&password=123";
		get = new HttpGet(uri+data);
		get.setHeaders(headers);
		get.setParams(httpParams);

		try {
			HttpResponse response = client.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("statusCode:"+statusCode);
			if(statusCode == 200) {
				return EntityUtils.toString(response.getEntity(), ConstantValue.ENCODING);
			}
		} catch (Exception e) {
			GlobalParams.myError.setText("服务器正忙..");
			e.printStackTrace();
		} finally {
			if(client != null) {
				client.getConnectionManager().shutdown();
			}
		}
		return null;
	}
	
	
	//发送post和get
	public String sendPost(String uri,Map<String, Object> params)
	{
		post = new HttpPost(uri);
		post.setHeaders(headers);
		post.setParams(httpParams);
		
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		if(null != params && params.size() > 0)
		{
			for(Map.Entry<String, Object> item:params.entrySet())
			{
				BasicNameValuePair pair = new BasicNameValuePair(item.getKey(), item.getValue().toString());
				parameters.add(pair);
			}
		}
		//编码
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,ConstantValue.ENCODING);
			post.setEntity(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			HttpResponse response = client.execute(post);
			
			if(response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), ConstantValue.ENCODING);
			}
		} catch (Exception e) {
			GlobalParams.myError.setText("服务器正忙..");
			e.printStackTrace();
		} finally {
			if(client != null) {
				client.getConnectionManager().shutdown();
			}
		}
		
		return null;
	}
	
	//发送post和get
	public String sendGet(String uri,Map<String, Object> params)
	{
			return null;
	}
	
	public static void uploadLogToServer(){

    	String path="http://10.0.2.2:8080/jsontest/servlet/UploadServlet";

    	File myFile = new File("/sdcard/cat/cat.jpg");
    	RequestParams params = new RequestParams();
    	try {
    	    params.put("filename", myFile);
    	    AsyncHttpClient client = new AsyncHttpClient();
    	    client.post(path, params, new AsyncHttpResponseHandler(){

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					
				}
    	   	
    	    });

    	   
    	} catch(FileNotFoundException e) {

    		
    	}

    }
}
