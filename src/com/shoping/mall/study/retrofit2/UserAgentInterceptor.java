package com.shoping.mall.study.retrofit2;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This interceptor put all the Cookies in Preferences in the Request.
 * Your implementation on how to get the Preferences MAY VARY.
 * <p>
 */
public class UserAgentInterceptor implements Interceptor{

	private final String USER_AGENT_HEADER_NAME = "User-Agent";
	private String userAgentHeaderValue;
	
	public UserAgentInterceptor(String userAgentHeaderValue) {
	    this.userAgentHeaderValue = userAgentHeaderValue;
	}
	
	@Override
	public Response intercept(Chain chain) throws IOException {
		 final Request originalRequest = chain.request();
		 final Request requestWithUserAgent = originalRequest.newBuilder()
		        .removeHeader(USER_AGENT_HEADER_NAME)
		        .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
		        .build();
		 return chain.proceed(requestWithUserAgent);
	}

}
