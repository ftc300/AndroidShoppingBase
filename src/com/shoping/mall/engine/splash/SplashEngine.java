package com.shoping.mall.engine.splash;

import com.shoping.mall.bean.Splash;

/**
 * 欢迎界面闪屏广告请求
 * @author shen
 *
 */
public interface SplashEngine {	
	/**
	 * 获取闪屏广告数据
	 */
	public Splash getSplashData();
	
	/**
	 * 根据url地址保存图片到sd卡中
	 * @param url
	 */
	public boolean saveSplashImgToSdcard(String url,String filePath);
}
