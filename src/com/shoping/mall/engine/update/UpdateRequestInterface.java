package com.shoping.mall.engine.update;

public interface UpdateRequestInterface {

	public void updateAppVersion();
	
	public void downloadApk(String url,String filePath);
}
