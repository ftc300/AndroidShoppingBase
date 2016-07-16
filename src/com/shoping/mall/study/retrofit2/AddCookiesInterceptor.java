package com.shoping.mall.study.retrofit2;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Response;
import android.util.Log;

/**
 * This interceptor put all the Cookies in Preferences in the Request.
 * Your implementation on how to get the Preferences MAY VARY.
 * <p>
 */
public class AddCookiesInterceptor implements Interceptor{

	@Override
	public Response intercept(Chain chain) throws IOException {
		 Request.Builder builder = chain.request().newBuilder();
//	        HashSet<String> preferences = (HashSet) Preferences.getDefaultPreferences().getStringSet(Preferences.PREF_COOKIES, new HashSet<>());
	        HashSet<String> preferences = new HashSet<String>();
	        preferences.add("aaaaa=bbbbb");
	        preferences.add("ccccc=ddddd");
	        for (String cookie : preferences) {
	            builder.addHeader("Cookie", cookie);
	            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
	        }

	        return chain.proceed(builder.build());
	}

}
