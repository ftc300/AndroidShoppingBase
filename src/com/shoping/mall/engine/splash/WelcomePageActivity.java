package com.shoping.mall.engine.splash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.shoping.mall.MainActivity;
import com.shoping.mall.R;
import com.shoping.mall.base.BaseActivity;
import com.shoping.mall.server.BackgroundMonitorService;
import com.shoping.mall.util.LogUtil;
import com.shoping.mall.util.SharedPreUtil;
import com.shoping.mall.util.SharedPrefUtil;

public class WelcomePageActivity extends BaseActivity {

	private ImageView mSplashBg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		startService(new Intent(this, BackgroundMonitorService.class));
		mSplashBg = (ImageView) findViewById(R.id.splash_bg);
		showAnim();
	}

	public void showAnim() {
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(2000);
		mSplashBg.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				boolean isShowGuidePage = SharedPrefUtil.readBooleanFromSharedPref(
						WelcomePageActivity.this, "showGuidePage", true);

				String fileName = SharedPreUtil.get(WelcomePageActivity.this, "splash_file_name", "");
				
				if (isShowGuidePage) {
					//跳转到引导页面，且显示广告页面
					if (!TextUtils.isEmpty(fileName)) {
						LogUtil.i("跳转到引导页面，且显示广告页面");
						goToGuideActivity(true);
					} else {
						//跳转到引导页面，不显示广告页面
						LogUtil.i("跳转到引导页面，不显示广告页面");
						goToGuideActivity(false);
					}
				} else {
					//进入广告页面
					if (!TextUtils.isEmpty(fileName)) {
						LogUtil.i("进入广告页面");
						goToAdActivity();
					} else {
					//进入主界面
						LogUtil.i("进入主界面");
						goToMainActivity();
					}
				}
			}

		});
	}

	/**
	 * 跳转到广告页面
	 */
	private void goToAdActivity() {
		Intent intent = new Intent(WelcomePageActivity.this, AdActivity.class);
		startActivity(intent);
		this.finish();
	}

	/**
	 * 跳转到主界面
	 */
	private void goToMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}

	/**
	 * 跳转到引导页面
	 * @param showAd 表示是否显示广告页面
	 */
	private void goToGuideActivity(boolean showAd) {
		Intent intent = new Intent(this, SplashScreenADActivity.class);
		intent.putExtra("showAd", showAd);
		startActivity(intent);
		this.finish();
	}
}
