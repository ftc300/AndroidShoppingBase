package com.shoping.mall.study.retrofit2.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.shoping.mall.study.retrofit2.model.ChoicenessRes;

public interface ChoicenessService {

	@GET("/mobile/indexChoicenessNew.jhtml")
	Call<ChoicenessRes> getSlideData(@Query("indexpage") String indexpage);
}
