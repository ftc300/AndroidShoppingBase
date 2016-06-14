package com.shoping.mall.engine.model;


public abstract class BaseModel implements Runnable {
	
	protected final String TAG = getClass().getSimpleName();
	private String mUrlStr;
	
	
	public void start(){
		new Thread(this).start();
	}

	public abstract void stop();

	public String getUrlStr() {
		return mUrlStr;
	}

	public void setUrlStr(String mUrlStr) {
		this.mUrlStr = mUrlStr;
	}
}
