package com.shoping.mall.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.http.Header;
import org.apache.http.impl.cookie.BasicClientCookie;

import android.content.Context;
import android.os.Environment;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.shoping.mall.ConstantValue;
import com.shoping.mall.application.MallApp;

public class NetUtil {
	private static final String BASE_URL = "http://lifeapp.sce.sohu.com/app/gateway.action";
	public static PersistentCookieStore myCookieStore;

	public static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, AsyncHttpResponseHandler responseHandler) {
		setMyCookieStore();
		client.get(url, responseHandler);
		client.setUserAgent("ICBCAndroidCSNew F-EMALL");
	}

	private static void setMyCookieStore() {
		if (myCookieStore == null) {
			String JSESSIONID = SharedPreUtil.get(MallApp.getInstance(),
					"JSESSIONID", "");
			String MALLID = SharedPreUtil.get(MallApp.getInstance(), "MALLID",
					"");
			myCookieStore = new PersistentCookieStore(MallApp.getInstance());
			// if(!"".equals(JSESSIONID)){
			// BasicClientCookie mallID = new
			// BasicClientCookie("MALLID",MALLID);
			// mallID.setDomain("m.mall.dccnet.com.cn");
			// mallID.setPath("/");
			// BasicClientCookie jsessionID = new
			// BasicClientCookie("JSESSIONID",JSESSIONID);
			// jsessionID.setDomain("m.mall.dccnet.com.cn");
			// jsessionID.setPath("/");
			// myCookieStore.addCookie(jsessionID);
			// myCookieStore.addCookie(mallID);
			// client.setCookieStore(myCookieStore);
			// }else{
			CookieSyncManager manager = CookieSyncManager
					.createInstance(MallApp.getInstance());
			manager.startSync();
			CookieManager cookieManager = CookieManager.getInstance();
			String cookie = cookieManager.getCookie(ConstantValue.SERVER_URL);
			if (cookie != null) {
				if (myCookieStore == null) {
					myCookieStore = new PersistentCookieStore(
							MallApp.getInstance());
				}
				client.setCookieStore(myCookieStore);
				String[] split = cookie.split(" ");
				for (int i = 0; i < split.length; i++) {
					// 获得cookie
					String[] cookieKV = split[i].split("=");
					if (cookieKV.length > 1) {
						// 保存cookie
						SharedPreUtil.put(MallApp.getInstance(), cookieKV[0],
								cookieKV[1]);

					}
				}
				JSESSIONID = SharedPreUtil.get(MallApp.getInstance(),
						"JSESSIONID", "");
				MALLID = SharedPreUtil.get(MallApp.getInstance(), "MALLID", "");
				BasicClientCookie mallID = new BasicClientCookie("MALLID",
						MALLID);
				mallID.setDomain("UrlStrs.URL_SERVICE_DOMIN");
				mallID.setPath("/");
				BasicClientCookie jsessionID = new BasicClientCookie(
						"JSESSIONID", JSESSIONID);
				jsessionID.setDomain("UrlStrs.URL_SERVICE_DOMIN");
				jsessionID.setPath("/");
				myCookieStore.addCookie(jsessionID);
				myCookieStore.addCookie(mallID);
			} else {// 表示还未登陆
				myCookieStore = new PersistentCookieStore(MallApp.getInstance());
				client.setCookieStore(myCookieStore);
			}
		}

		// }
	}

	// public static void setMyCookieStore(CookieStore cookieStore){
	// client.setCookieStore(cookieStore);
	// }
	public static void setMyCookieStore(String cookies) {
		if (cookies != null) {
			if (myCookieStore == null) {
				myCookieStore = new PersistentCookieStore(MallApp.getInstance());
			}
			client.setCookieStore(myCookieStore);
			String[] split = cookies.split(" ");
			for (int i = 0; i < split.length; i++) {
				// 获得cookie
				String[] cookieKV = split[i].split("=");
				if (cookieKV.length > 1) {
					// 保存cookie
					SharedPreUtil.put(MallApp.getInstance(), cookieKV[0],
							cookieKV[1]);
				}
			}
			String JSESSIONID = SharedPreUtil.get(MallApp.getInstance(),
					"JSESSIONID", "");
			String MALLID = SharedPreUtil.get(MallApp.getInstance(), "MALLID",
					"");
			BasicClientCookie mallID = new BasicClientCookie("MALLID", MALLID);
			mallID.setDomain("UrlStrs.URL_SERVICE_DOMIN");
			mallID.setPath("/");
			BasicClientCookie jsessionID = new BasicClientCookie("JSESSIONID",
					JSESSIONID);
			jsessionID.setDomain("UrlStrs.URL_SERVICE_DOMIN");
			jsessionID.setPath("/");
			myCookieStore.addCookie(jsessionID);
			myCookieStore.addCookie(mallID);
		}
	}

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		setMyCookieStore();
		client.get(url, params, responseHandler);
	}

	// public static void get(String url, RequestParams params,
	// AsyncHttpResponseHandler responseHandler,Context paramContext) {
	// String jsessionId = "";
	// CookieSyncManager manager =
	// CookieSyncManager.createInstance(paramContext);
	// manager.startSync();
	// CookieManager cookieManager = CookieManager.getInstance();
	// String cookies = cookieManager.getCookie(UrlStrs.URL_MEMBER);
	// // if(cookies!=null){
	// // jsessionId = "";
	// // if(cookies.indexOf("JSESSIONID") != -1){
	// // String tempStr[] = cookies.split(";");
	// // for(int i=0;i<tempStr.length;i++){
	// // String temp = tempStr[i];
	// // if(temp.indexOf("JSESSIONID=") != -1){
	// // jsessionId = temp.substring(12,temp.length());
	// // }
	// // }
	// // }
	// // }
	// //
	// //
	// PersistentCookieStore myCookieStore = new
	// PersistentCookieStore(paramContext);
	// // BasicClientCookie newCookie = new BasicClientCookie("JSESSIONID",
	// jsessionId);
	// // newCookie.setVersion(1);
	// // newCookie.setDomain(UrlStrs.URL_MEMBER);
	// //
	// newCookie.setPath("http://m.mall.icbc.com.cn:8010/mobile/member/member.jhtml");
	// // myCookieStore.addCookie(newCookie);
	// // //UrlStrs.URL_INDEX_MEMBER
	// client.setCookieStore(myCookieStore);
	//
	//
	// client.addHeader("Cookie", cookies);
	//
	// client.post(url, params, responseHandler);
	// }

	public static void post(String url, AsyncHttpResponseHandler responseHandler) {
		setMyCookieStore();
		client.post(url, responseHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		setMyCookieStore();
		client.post(url, params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL;
	}

	/**
	 * 同步一下cookie
	 */
	public static void synCookies(Context context, String url, String cookies) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		// cookieManager.removeSessionCookie();//移除
		cookieManager.setCookie(url, cookies);// cookies是在HttpClient中获得的cookie
		CookieSyncManager.getInstance().sync();
	}

	/**
	 * 删除cookie
	 */
	public static void deleteCookies(Context context) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		cookieManager.removeSessionCookie();// 移除
		CookieSyncManager.getInstance().sync();
	}

	public static void uploadLogToServer(String url,File file) {

		String path = "http://10.0.2.2:8080/jsontest/servlet/UploadServlet";

		File myFile = new File("/sdcard/cat/cat.jpg");
	
		RequestParams params = new RequestParams();
		try {
			params.put("filename", myFile);
			AsyncHttpClient client = new AsyncHttpClient();
			client.post(path, params, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {

				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

				}

			});

		} catch (FileNotFoundException e) {

		}

	}

	public static void downloadFileFromServer(String url) {

		client.get(url, new BinaryHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {

			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				File file = Environment.getExternalStorageDirectory();
				File file2 = new File(file, "cat");
				file2.mkdir();
				file2 = new File(file2, "cat.jpg");
				try {
					FileOutputStream oStream = new FileOutputStream(file2);
					oStream.write(arg0);
					oStream.flush();
					oStream.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}
}
