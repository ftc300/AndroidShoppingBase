package com.shoping.mall.study.retrofit2.api;

import retrofit2.Call;
import retrofit2.http.GET;

import com.shoping.mall.study.retrofit2.model.PlatformRes;

public interface PlatformService {

	@GET("/mobile/indexPlatformNew.jhtml?flag=1")
	Call<PlatformRes> getPlatformData();
}
