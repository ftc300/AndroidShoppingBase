package com.shoping.mall.study.retrofit2.util.cache;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import com.shoping.mall.application.MallApp;
import com.shoping.mall.study.util.AppUtils;

public class CacheInterceptor implements Interceptor{

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
        if (!AppUtils.networkIsAvailable(MallApp.getInstance())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (AppUtils.networkIsAvailable(MallApp.getInstance())) {
            int maxAge = 0;
            // 有网络时 设置缓存超时时间0个小时
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("WuXiaolong")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            // 无网络时，设置超时为4周
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("nyn")
                    .build();
        }
        return response;
	}

}
