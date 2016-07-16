package com.shoping.mall.study.retrofit2.model;

public class RetrofitRes {

	private RetrofitData retrofitData;
	private String response;
	private String version;
	
	
	public RetrofitData getRetrofitData() {
		return retrofitData;
	}
	public void setRetrofitData(RetrofitData retrofitData) {
		this.retrofitData = retrofitData;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
