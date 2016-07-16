package com.shoping.mall.study.retrofit2.api;

import retrofit2.Call;
import retrofit2.http.GET;

import com.shoping.mall.study.retrofit2.model.ItemRes;

public interface ItemService {

	@GET("/mobile/indexIndustry.jhtml")
	Call<ItemRes> getItemData();
}
