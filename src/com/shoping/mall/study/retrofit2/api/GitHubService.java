package com.shoping.mall.study.retrofit2.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.shoping.mall.study.retrofit2.model.User;



public interface GitHubService{
    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);
}
