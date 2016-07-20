package com.shoping.mall.study.state;

import android.os.Bundle;

import com.shoping.mall.R;
import com.shoping.mall.base.BaseActivity;
/**
 * 这个特性是andorid4.4支持的，最少要api19才可以使用
 * @author shen
 */
public class ColorfulStatusBar extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_colorful_status_layout);  
		//透明状态栏  
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
		//透明导航栏  
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);  
		
		
		// 创建状态栏的管理实例
//	    SystemBarTintManager tintManager = new SystemBarTintManager(this);
//	       // 激活状态栏设置
//	    tintManager.setStatusBarTintEnabled(true);
//	       // 激活导航栏设置
//	    tintManager.setNavigationBarTintEnabled(true);

		
	}
	
	
	
}
