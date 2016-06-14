package com.shoping.mall.engine.splash;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.shoping.mall.MainActivity;
import com.shoping.mall.R;
import com.shoping.mall.base.BaseActivity;
import com.shoping.mall.util.SharedPrefUtil;

/**
 *
 */
public class SplashScreenADActivity extends BaseActivity {

	private ViewPager mViewPager;

	// private int currIndex = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_whatsnew_viewpager);
		mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);
		SharedPrefUtil.writeBooleanSharedPref(this, "showGuidePage", false);

		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.whats_new_1, null);
		View view2 = mLi.inflate(R.layout.whats_new_2, null);
		View view3 = mLi.inflate(R.layout.whats_new_3, null);
		View view4 = mLi.inflate(R.layout.whats_new_4, null);

		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		// views.add(view5);
		// views.add(view6);

		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mViewPager.setAdapter(mPagerAdapter);
	}

	/**
	 * 跳转到闪屏广告页
	 * @param v
	 */                           
	public void startbutton(View v) {
		boolean showAd = getIntent().getBooleanExtra("showAd",false);
		Intent intent = new Intent();
		if(showAd){
			intent.setClass(this, AdActivity.class);
		}else{
			intent.setClass(this, MainActivity.class);
		}
		startActivity(intent);
		finish();
	}

}
