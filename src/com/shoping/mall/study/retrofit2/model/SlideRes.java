package com.shoping.mall.study.retrofit2.model;

import java.util.List;

public class SlideRes {

	private SlideData data;
	private String isReview;
	private String status;
	private String type;
	private String message;
	private List datas;
	
	public SlideData getData() {
		return data;
	}
	public void setData(SlideData data) {
		this.data = data;
	}
	public String getIsReview() {
		return isReview;
	}
	public void setIsReview(String isReview) {
		this.isReview = isReview;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List getDatas() {
		return datas;
	}
	public void setDatas(List datas) {
		this.datas = datas;
	}
}
