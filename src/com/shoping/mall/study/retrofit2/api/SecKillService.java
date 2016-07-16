package com.shoping.mall.study.retrofit2.api;

import retrofit2.Call;
import retrofit2.http.GET;

import com.shoping.mall.study.retrofit2.model.SecKillRes;

public interface SecKillService {

	@GET("/mobile/indexSeckill.jhtml")
	Call<SecKillRes> getSlideData();
}
