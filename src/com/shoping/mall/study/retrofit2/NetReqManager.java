package com.shoping.mall.study.retrofit2;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetReqManager {

	private static NetReqManager mNetReqManager = new NetReqManager();

	private OkHttpClient mOkHttpClient;
	private Builder mRetrofitBuilder;
	private Retrofit mGsonRetrofit;
	private Retrofit mDefaultRetrofit;

	private NetReqManager() {

	}

	public static synchronized NetReqManager getNetReqManager() {
		if (null == mNetReqManager) {
			mNetReqManager = new NetReqManager();
		}
		return mNetReqManager;
	}

	/**
	 * Retrofit retrofit = new Retrofit.Builder() .baseUrl(MY_API)
	 * .client(okHttpClient) .addConverterFactory(GsonConverterFactory.create())
	 * .build(); 主要功能是为所有的请求设置 同一个UserAgent 设置统一的域名访问不需要重复写
	 * 
	 * @return
	 */
	public Builder getRetrofitBuilder() {
		if (null == mRetrofitBuilder) {
			Builder builder = new Retrofit.Builder()
					.baseUrl(RetrofitMainActivity.MY_API);
			if (null == mOkHttpClient) {
				mRetrofitBuilder = builder.client(getNetReqManager()
						.getOkHttpClient());
			} else {
				mRetrofitBuilder = builder.client(mOkHttpClient);
			}
		}
		return mRetrofitBuilder;

	}

	public Retrofit getGsonRetrofit() {
		if (null == mGsonRetrofit) {
			if (null == mRetrofitBuilder) {
				mGsonRetrofit = getNetReqManager().getRetrofitBuilder()
						.addConverterFactory(GsonConverterFactory.create())
						.build();
			} else {
				mGsonRetrofit = mRetrofitBuilder.addConverterFactory(
						GsonConverterFactory.create()).build();
			}
		}
		return mGsonRetrofit;
	}
	
	
	public Retrofit getDefaultRetrofit() {
		if (null == mDefaultRetrofit) {
			if (null == mRetrofitBuilder) {
				mDefaultRetrofit = getNetReqManager().getRetrofitBuilder()
						.build();
			} else {
				mDefaultRetrofit = mRetrofitBuilder.build();
			}
		}
		return mDefaultRetrofit;
	}

	
	
	/**
	 * 向外提供OkhttpClient 方便直接使用这个对象进行请求
	 * 
	 * @return
	 */
	public OkHttpClient getOkHttpClient() {
		if (null == mOkHttpClient) {
			mOkHttpClient = new OkHttpClient();
			mOkHttpClient.interceptors().add(new AddCookiesInterceptor());
			mOkHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
			mOkHttpClient.interceptors().add(
					new UserAgentInterceptor("shenjianli version 1.1.0.9"));
		}
		return mOkHttpClient;
	}

	/*
	 * OkHttpClient okHttpClient = new OkHttpClient();
	 * okHttpClient.interceptors().add(new AddCookiesInterceptor());
	 * okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
	 * okHttpClient.interceptors().add(new
	 * UserAgentInterceptor("shenjianli version 1.1.0.9"));
	 */

}
