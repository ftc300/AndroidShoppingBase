package com.shoping.mall.study.retrofit2.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

import com.shoping.mall.study.retrofit2.model.RetrofitRes;

public interface RetrofitService {

	@Headers({"Cache-Control: max-age=640000",
		"Accept: application/vnd.github.v3.full+json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@GET("/ECService/retrofit?version=11")
	Call<RetrofitRes> getData(@Header("name") String name);
}
