package com.shoping.mall.study.retrofit2;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

import com.shoping.mall.util.LogUtil;

public class ReceivedCookiesInterceptor implements Interceptor{

	@Override
	public Response intercept(Chain chain) throws IOException {
		Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<String>();

            for (String header : originalResponse.headers("Set-Cookie")) {
              cookies.add(header);
              LogUtil.i("header" + header);
            }
            LogUtil.i("cookies" + cookies);
            
//            Preferences.getDefaultPreferences().edit()
//                    .putStringSet(Preferences.PREF_COOKIES, cookies)
//                    .apply();
        }

        return originalResponse;
	}

}
