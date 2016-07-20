package com.shoping.mall.study.volley;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shoping.mall.R;
import com.squareup.okhttp.OkHttpClient;

public class VolleyActivity extends Activity implements OnClickListener {
	private RequestQueue mQueue;
	String url="http://www.baidu.com";

	private OkHttpStack mOkHttpStack;
	private OkHttpClient mOkHttpClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley_main);
		mQueue=Volley.newRequestQueue(this);
		
		//mOkHttpClient.networkInterceptors().add(new StethoInterceptor());
		
		mOkHttpStack = new OkHttpStack(mOkHttpClient);
		mQueue = Volley.newRequestQueue(this, mOkHttpStack);
//       mRequestQueue = Volley.newRequestQueue(context, new OkHttpStack(mOkHttpClient));
		
		initView();
	}
	
	private void initView(){
		final Button btnGet=(Button) findViewById(R.id.btn_get);
		final Button btnPost=(Button) findViewById(R.id.btn_post);
		btnGet.setOnClickListener(this);
		btnPost.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get:
			initGet();
			break;
		case R.id.btn_post:
			initPost();
			break;
		default:
			break;
		}
		
	}
	private void initGet(){//Get请求   Method为GET
		mQueue.add(new StringRequest(Method.GET,url, new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				Toast.makeText(VolleyActivity.this, "Get请求成功"+arg0, 2).show();
				//成功，在这里写处理内容的代码
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(VolleyActivity.this, "Get请求失败", 2).show();
				//失败
			}
		
		
		}));
	}
	private void initPost(){//Post请求
		//这写你自己的内部类PostResquest。。。。Method改成POST
		mQueue.add(new PostResquest(Method.POST,url, new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				Toast.makeText(VolleyActivity.this, "Post请求成功" + arg0, 2).show();
				//成功，在这里写处理内容的代码
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(VolleyActivity.this, "Post请求失败", 2).show();
				//失败
			}
		
		
		}));
	}
	//写个内部类，Post里面放一些服务器需要的参数
	class PostResquest extends StringRequest {

		public PostResquest(int method, String url,
				Listener<String> listener, ErrorListener errorListener) {
			super(method, url, listener, errorListener);
		}

		protected Map<String, String> getParams() throws AuthFailureError {
			Map<String, String> params = new HashMap<String, String>();
			params.put("Name", "小源");//参数
			params.put("Age",22+"");//参数
			return params;
		}
	}
	
	
}
