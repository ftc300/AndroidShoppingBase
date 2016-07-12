package com.shoping.mall.study.okhttp.volley;

import java.util.Map;

import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

public class VolleyGetRequest extends VolleyRequest {

	protected VolleyGetRequest(String url, Object tag,
			Map<String, String> params, Map<String, String> headers) {
		super(url, tag, params, headers);
	}

	@Override
	protected void buildRequest() {
		 if (TextUtils.isEmpty(url))
	        {
	            throw new IllegalArgumentException("url can not be empty!");
	        }
	        //append params , if necessary
	        url = appendParams(url, params);
	}

	@Override
	protected String buildRequestBody() {
		// TODO Auto-generated method stub
		return null;
	}
	
	 private String appendParams(String url, Map<String, String> params)
	    {
	        StringBuilder sb = new StringBuilder();
	        sb.append(url + "?");
	        if (params != null && !params.isEmpty()){
	            for (String key : params.keySet()){
	                sb.append(key).append("=").append(params.get(key)).append("&");
	            }
	        }

	        sb = sb.deleteCharAt(sb.length() - 1);
	        return sb.toString();
	  }

	@Override
	protected int getRequestMethod() {
		return Method.GET;
	}
	 
	 

}
