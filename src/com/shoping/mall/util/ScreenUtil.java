package com.shoping.mall.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenUtil {

	/**
	 * density值表示每英寸有多少个显示点，与分辨率是两个不同的概念：
	 * 
	 * 
	 * 
	 * Android主要有以下几种屏：
	 * 
	 * QVGA和WQVGA屏density=120；
	 * 
	 * HVGA屏density=160；
	 * 
	 * WVGA屏density=240；
	 * 
	 * 下面以480dip*800dip的WVGA(density=240)为例，详细列出不同density下屏幕分辨率信息：
	 * 
	 * 当density=120时 屏幕实际分辨率为240px*400px （两个点对应一个分辨率） 状态栏和标题栏高各19px或者25dip
	 * 横屏是屏幕宽度400px 或者800dip,工作区域高度211px或者480dip
	 * 竖屏时屏幕宽度240px或者480dip,工作区域高度381px或者775dip
	 * 
	 * density=160时 屏幕实际分辨率为320px*533px （3个点对应两个分辨率） 状态栏和标题栏高个25px或者25dip
	 * 横屏是屏幕宽度533px 或者800dip,工作区域高度295px或者480dip
	 * 竖屏时屏幕宽度320px或者480dip,工作区域高度508px或者775dip
	 * 
	 * density=240时 屏幕实际分辨率为480px*800px （一个点对于一个分辨率） 状态栏和标题栏高个38px或者25dip
	 * 横屏是屏幕宽度800px 或者800dip,工作区域高度442px或者480dip
	 * 竖屏时屏幕宽度480px或者480dip,工作区域高度762px或者775dip
	 * 
	 * apk的资源包中，当屏幕density=240时使用hdpi标签的资源 当屏幕density=160时，使用mdpi标签的资源
	 * 当屏幕density=120时，使用ldpi标签的资源。 不加任何标签的资源是各种分辨率情况下共用的。
	 * 建议：布局时尽量使用单位dip，少使用px。
	 * 
	 * device independent pixels(设备独立像素).
	 * 不同设备有不同的显示效果,这个和设备硬件有关，一般我们为了支持WVGA、HVGA和QVGA 推荐使用这个，不依赖像素。
	 * 
	 * 
	 * @param activity
	 */
	public static void initScreenInfo(Activity activity) {
		// 获取屏幕密度（方法1）
		int screenWidth = activity.getWindowManager().getDefaultDisplay()
				.getWidth(); // 屏幕宽（像素，如：480px）
		int screenHeight = activity.getWindowManager().getDefaultDisplay()
				.getHeight(); // 屏幕高（像素，如：800p）

		LogUtil.e("  getDefaultDisplay", "screenWidth=" + screenWidth
				+ "; screenHeight=" + screenHeight);

		// 获取屏幕密度（方法2）
		DisplayMetrics dm = new DisplayMetrics();
		dm = activity.getResources().getDisplayMetrics();

		float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;

		LogUtil.e("  DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
		LogUtil.e("  DisplayMetrics", "density=" + density + "; densityDPI="
				+ densityDPI);

		screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
		screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）

		LogUtil.e("  DisplayMetrics(111)", "screenWidth=" + screenWidth
				+ "; screenHeight=" + screenHeight);

		// 获取屏幕密度（方法3）
		dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;

		LogUtil.e("  DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
		LogUtil.e("  DisplayMetrics", "density=" + density + "; densityDPI="
				+ densityDPI);

		int screenWidthDip = dm.widthPixels; // 屏幕宽（dip，如：320dip）
		int screenHeightDip = dm.heightPixels; // 屏幕宽（dip，如：533dip）

		LogUtil.e("  DisplayMetrics(222)", "screenWidthDip=" + screenWidthDip
				+ "; screenHeightDip=" + screenHeightDip);

		screenWidth = (int) (dm.widthPixels * density + 0.5f); // 屏幕宽（px，如：480px）
		screenHeight = (int) (dm.heightPixels * density + 0.5f); // 屏幕高（px，如：800px）

		LogUtil.e("  DisplayMetrics(222)", "screenWidth=" + screenWidth
				+ "; screenHeight=" + screenHeight);
	}
}
