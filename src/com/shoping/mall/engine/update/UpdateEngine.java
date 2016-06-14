package com.shoping.mall.engine.update;


/**
 * 欢迎界面闪屏广告请求
 * @author shen
 *
 */
public interface UpdateEngine {	
	/**
	 * 根据url地址保存apk到sd卡中
	 * @param url
	 */
	public boolean saveApkToSdcard(String url,String filePath);
}
