package com.shoping.mall.study.retrofit2.api;

import retrofit2.Call;
import retrofit2.http.GET;

import com.shoping.mall.study.retrofit2.model.ProjectRes;

public interface ProjectService {

	@GET("/mobile/indexTheme.jhtml")
	Call<ProjectRes> getProjectData();
}
