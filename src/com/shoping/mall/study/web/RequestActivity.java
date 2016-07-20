package com.shoping.mall.study.web;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shoping.mall.ConstantValue;
import com.shoping.mall.R;
import com.shoping.mall.application.MallApp;
import com.shoping.mall.base.BaseActivity;
import com.shoping.mall.util.LogUtil;
import com.shoping.mall.util.NetUtil;
import com.squareup.okhttp.internal.Util;

public class RequestActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	public void toLogin(View v)
	{
		CookieSyncManager manager = CookieSyncManager
				.createInstance(MallApp.getInstance());
		manager.startSync();
		CookieManager cookieManager = CookieManager.getInstance();
		String cookie = cookieManager.getCookie(ConstantValue.SERVER_URL);
		LogUtil.i("cookie:" + cookie);
		String url = ConstantValue.SERVER_URL + "/jsp/cookie_test";
		NetUtil.get(url, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
	}
}
