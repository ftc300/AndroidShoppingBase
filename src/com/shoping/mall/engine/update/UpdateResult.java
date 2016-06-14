package com.shoping.mall.engine.update;

public class UpdateResult<T>{

	public static final int UPDATE_DATA_SUCCESS = 0xcb096;
	public static final int UPDATE_DATA_FAIL = UPDATE_DATA_SUCCESS + 1;
	
	public static final int DOWNLOAD_APK_SUCC = UPDATE_DATA_FAIL + 1;
	public static final int DOWNLOAD_APK_FAIL = DOWNLOAD_APK_SUCC + 1;
	public static final int DOWNLOAD_APK_LOADING = DOWNLOAD_APK_FAIL + 1;
	
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
