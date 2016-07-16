package com.shoping.mall.study.retrofit2.api;

import retrofit2.Call;
import retrofit2.http.GET;

import com.shoping.mall.study.retrofit2.model.SlideRes;

public interface SlideService {

	@GET("/mobile/indexSlide.jhtml")
	Call<SlideRes> getSlideData();
}
