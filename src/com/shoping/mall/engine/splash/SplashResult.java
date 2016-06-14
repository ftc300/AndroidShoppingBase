package com.shoping.mall.engine.splash;

public class SplashResult<T>{

	public static final int UPDATE_SPLASH_SUCCESS = 0xbb096;
	public static final int UPDATE_SPLASH_EQUAL = 0xbb097;
	public static final int UPDATE_SPLASH_FAIL = 0xbb098;
	
	private boolean isSuccess;
	private int mResultCode;
	private String mErrMsg;
	
	private T mContent;
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public int getResultCode() {
		return mResultCode;
	}
	public void setResultCode(int resultCode) {
		this.mResultCode = resultCode;
	}
	public T getContent() {
		return mContent;
	}
	public void setContent(T content) {
		mContent = content;
	}
	public String getErrMsg() {
		return mErrMsg;
	}
	public void setErrMsg(String mErrMsg) {
		this.mErrMsg = mErrMsg;
	}
	
	
}
