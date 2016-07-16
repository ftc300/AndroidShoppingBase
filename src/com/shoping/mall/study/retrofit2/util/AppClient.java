package com.shoping.mall.study.retrofit2.util;

import java.io.File;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.shoping.mall.BuildConfig;
import com.shoping.mall.application.MallApp;
import com.shoping.mall.study.retrofit2.util.cache.CacheInterceptor;


public class AppClient {

	public static Retrofit retrofit = null;
	public static String API_SERVER_URL = "http://172.26.213.1:8080";
    public static Retrofit retrofit() {
        if (retrofit == null) {
	         OkHttpClient.Builder builder = new OkHttpClient.Builder();
            /**
             *设置缓存，代码略
             */
	         File cacheFile = new File(MallApp.getInstance().getExternalCacheDir(), "MallCache");
	         Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
             builder.cache(cache).addInterceptor(new CacheInterceptor());        
            /**
             *  公共参数，代码略
             */
             
            //公共参数
            builder.addInterceptor(new QueryParameterInterceptor());
            /**
             * 设置头，代码略
             */           
            //设置头
            builder.addInterceptor(new HeaderInterceptor());
			 /**
             * Log信息拦截器，代码略
             */
             if (BuildConfig.DEBUG) {
            	    // Log信息拦截器
            	    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            	    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            	    //设置 Debug Log 模式
            	    builder.addInterceptor(loggingInterceptor);
            	}
			 /**
             * 设置cookie，代码略
             */
             CookieManager cookieManager = new CookieManager();
             cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
             builder.cookieJar(new JavaNetCookieJar(cookieManager));

             /**
             * 设置超时和重连，代码略
             */
           //设置超时
             builder.connectTimeout(15, TimeUnit.SECONDS);
             builder.readTimeout(20, TimeUnit.SECONDS);
             builder.writeTimeout(20, TimeUnit.SECONDS);
             //错误重连
             builder.retryOnConnectionFailure(true);

             
            //以上设置结束，才能build(),不然设置白搭
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }
}
