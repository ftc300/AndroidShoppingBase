package com.shoping.mall;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.shoping.mall.base.BaseFragmentActivity;
import com.shoping.mall.bean.Splash;
import com.shoping.mall.engine.listener.ResponseResultListener;
import com.shoping.mall.engine.listener.ResponseResultListener1;
import com.shoping.mall.engine.main.MainFragment;
import com.shoping.mall.engine.map.drag.GetAddressByMap;
import com.shoping.mall.engine.splash.SplashController;
import com.shoping.mall.engine.splash.SplashRequestInterface;
import com.shoping.mall.engine.splash.SplashResult;
import com.shoping.mall.engine.update.Update;
import com.shoping.mall.engine.update.UpdateController;
import com.shoping.mall.engine.update.UpdateRequestInterface;
import com.shoping.mall.engine.update.UpdateResult;
import com.shoping.mall.receiver.NetBroadcastReceiver;
import com.shoping.mall.receiver.NetBroadcastReceiver.NetStateChangeListener;
import com.shoping.mall.study.alarm.AlarmChangeWallpaper;
import com.shoping.mall.study.dialogfragment.activity.FriendActivity;
import com.shoping.mall.study.explosion.Explosion2MainActivity;
import com.shoping.mall.study.fontdrawable.FontDrawableActivity;
import com.shoping.mall.study.guide.SplashActivity;
import com.shoping.mall.study.headimg.HeadImgActivity;
import com.shoping.mall.study.highlight.HighLightActivity;
import com.shoping.mall.study.htextview.HTextViewActivity;
import com.shoping.mall.study.jsweb.WebJSActivity;
import com.shoping.mall.study.log.test.LogMainActivity;
import com.shoping.mall.study.mvp.view.MVPActivity;
import com.shoping.mall.study.percent.example.PercentActivity;
import com.shoping.mall.study.pickerview.demo.PickerViewMainActivity;
import com.shoping.mall.study.pullpushlayout.PullPushActivity;
import com.shoping.mall.study.smallbang.SmallBangActivity;
import com.shoping.mall.study.state.SamplesListActivity;
import com.shoping.mall.study.web.RequestActivity;
import com.shoping.mall.study.web.WebViewActivity;
import com.shoping.mall.study.welanim.activity.WelcomeActivity;
import com.shoping.mall.study.wheelview.WheelViewMainActivity;
import com.shoping.mall.ui.dialog.SampleActivity;
import com.shoping.mall.ui.dialog.SweetAlertDialog;
import com.shoping.mall.ui.dialog.UpdateAppDialogFragment;
import com.shoping.mall.ui.dialog.UpdateAppDialogFragment.DialogClickListener;
import com.shoping.mall.util.CustomToast;
import com.shoping.mall.util.LogUtil;
import com.shoping.mall.util.ScreenUtil;

public class MainActivity extends BaseFragmentActivity implements
		ResponseResultListener<SplashResult<Splash>>,
		ResponseResultListener1<UpdateResult<Update>>, DialogClickListener,NetStateChangeListener {

	private SplashRequestInterface mSplashRequestInterface;
	private UpdateRequestInterface mUpdateRequestInterface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScreenUtil.initScreenInfo(this);
		setContentView(R.layout.activity_main);
		NetBroadcastReceiver.addNetStateListener(this);
		showDefaultFragment();
		mSplashRequestInterface = new SplashController(MainActivity.this, this);
		mUpdateRequestInterface = new UpdateController(MainActivity.this, this);
		if (null != mSplashRequestInterface) {
			mSplashRequestInterface.updateSplashInfo();
		}
		if (null != mUpdateRequestInterface) {
			mUpdateRequestInterface.updateAppVersion();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
//		switch (id) {
//		case R.id.action_percent:
//			intent = new Intent(this,PercentActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_dialog:
//			intent = new Intent(this,SampleActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_head:
//			intent = new Intent(this,HeadImgActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_baidu_map:
//			intent = new Intent(this,GetAddressByMap.class);
//			startActivity(intent);
//			break;
//		case R.id.action_pull_push:
//			intent = new Intent(this,PullPushActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_web_js:
//			intent = new Intent(this,WebJSActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_colorful_bar:
////			intent = new Intent(this,ColorfulStatusBar.class);
//			intent = new Intent(this,SamplesListActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_splash:
//			intent = new Intent(this,SplashActivity.class);
//			intent = new Intent(this,WelcomeActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_webview:
//			intent = new Intent(this,WebViewActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_cookie:
//			intent = new Intent(this,RequestActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_alarm:
//			intent = new Intent(this,AlarmChangeWallpaper.class);
//			startActivity(intent);
//			break;
//		case R.id.action_font:
//			//intent = new Intent(this,FontAweSomeActivity.class);
////			intent = new Intent(this,ImageLetterIconActivity.class);
//			intent = new Intent(this,FontDrawableActivity.class);
//			intent = new Intent(this,HTextViewActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_log:
//			intent = new Intent(this,LogMainActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_highlight:
//			intent = new Intent(this,HighLightActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_loadinganim:
////			intent = new Intent(this,LoadingAnimActivity.class);
////			intent = new Intent(this,LikeAnimMainActivity.class);
//			intent = new Intent(this,SmallBangActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_explosion:
//			intent = new Intent(this,Explosion2MainActivity.class);
//			intent = new Intent(this,Explosion2MainActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.action_wheelview:
//			intent = new Intent(this,WheelViewMainActivity.class);//刻度
//			intent = new Intent(this,PickerViewMainActivity.class);//选择器
//			startActivity(intent);
//			break;
//		case R.id.action_framework:
//			intent = new Intent(this,MVPActivity.class);//mvp
//			startActivity(intent);
//			break;
//		case R.id.action_weixin:
//			//intent = new Intent(this,WXEntryActivity.class);
////			intent = new Intent(this,BmobAcitvity.class);
//			intent = new Intent(this,FriendActivity.class);
//			startActivity(intent);
//			break;
//		default:
//			break;
//		}
		return super.onOptionsItemSelected(item);
	}

	private MainFragment mMainFragment;
	private final int MAIN_INDEX = 0x1108;

	private void showDefaultFragment() {
		mMainFragment = (MainFragment) getSupportFragmentManager()
				.findFragmentByTag(String.valueOf(MAIN_INDEX));
		if (null == mMainFragment) {
			mMainFragment = MainFragment.newInstance(MAIN_INDEX);
		}
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.main_fragment_layout, mMainFragment,
						String.valueOf(MAIN_INDEX)).commit();

	}

	@Override
	public void updateVeriCodeResult(SplashResult<Splash> result) {
		if (result.isSuccess()) {
			int resultCode = result.getResultCode();
			Splash splash = result.getContent();
			switch (resultCode) {
			case SplashResult.UPDATE_SPLASH_SUCCESS:
				LogUtil.i("更新成功");
				LogUtil.i(splash.getName() + ":" + splash.getDesc());
				break;
			case SplashResult.UPDATE_SPLASH_FAIL:
				LogUtil.i("更新失败：" + result.getErrMsg());
				break;
			case SplashResult.UPDATE_SPLASH_EQUAL:
				LogUtil.i("相同不需要更新");
				LogUtil.i(splash.getName() + ":" + splash.getDesc());
				break;
			default:
				break;
			}
		} else {
			LogUtil.i("数据异常:" + result.getErrMsg());
		}
	}

	@Override
	public void notifyStartFinish() {

	}

	private String mApkUrl;

	@Override
	public void updateResult(UpdateResult<Update> result) {

		if (null != result) {
			int resultCode = result.getResultCode();
			boolean success = result.isSuccess();
			Update update = result.getContent();
			switch (resultCode) {
			case UpdateResult.UPDATE_DATA_SUCCESS:
				if (success) {
					if (null != update) {
						LogUtil.i("更新数据获取成功：" + update.toString());
						boolean isUpdate = update.isUpdateVersion();
						if (isUpdate) {
							String title = update.getTitle();
							String content = update.getDescription();
							mApkUrl = update.getApkurl();
							LogUtil.i("有高版本需要更新，显示对话框");
							showUpdateAppDialog(title, content);
							// showUpdateHintDialog(title,content);
						} else {
							LogUtil.i("版本相同不需要更新");
						}

					}
				}
				break;
			case UpdateResult.UPDATE_DATA_FAIL:
				if (null != update) {
					LogUtil.i("更新数据获取失败：" + update.toString());
				}
				break;
			case UpdateResult.DOWNLOAD_APK_SUCC:
				if (null != update) {
					LogUtil.i("下载最新apk成功：" + update.toString());
					if (null != mUpdateAppDialogFragment) {
						mUpdateAppDialogFragment.dismissAllowingStateLoss();
					}
					File apkFile = update.getApkFile();
					if (null != apkFile) {
						installAPK(apkFile);
					}
				}
				break;
			case UpdateResult.DOWNLOAD_APK_LOADING:
				if (null != update) {
					LogUtil.i("正在下载最新apk：" + update.toString());
					int percentage = update.getProgress();
					if (null != mUpdateAppDialogFragment) {
						mUpdateAppDialogFragment
								.updateDownloadProgress(percentage);
					}
				}
				break;
			case UpdateResult.DOWNLOAD_APK_FAIL:
				if (null != update) {
					LogUtil.i("下载最新apk失败：" + update.toString());
					if (null != mUpdateAppDialogFragment) {
						if(!success){
							mUpdateAppDialogFragment.updateDownloadFail(result.getErrMsg());
						}
					}
				}
				break;
			default:
				break;
			}
		}

	}

	private UpdateAppDialogFragment mUpdateAppDialogFragment;

	private void showUpdateAppDialog(String title, String content) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		mUpdateAppDialogFragment = UpdateAppDialogFragment.newInstance(title,
				content);
		if (null != mUpdateAppDialogFragment) {
			mUpdateAppDialogFragment.setDialogClickListener(this);
			mUpdateAppDialogFragment.show(fragmentManager, "update");
		}

	}

	/**
	 * 安装APK
	 * 
	 * @param t
	 */
	private void installAPK(File t) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(t),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	private void showUpdateHintDialog(String title, String content) {
		String dialogTitle = title;
		String dialogContent = content;
		if (TextUtils.isEmpty(dialogTitle)) {
			dialogTitle = "版本更新";
		}
		if (TextUtils.isEmpty(dialogContent)) {
			dialogContent = "修改若干bug,相关功能优化";
		}
		new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
				.setTitleText(dialogTitle)
				.setContentText(dialogContent)
				.setCancelText("暂不升级")
				.setConfirmText("确定升级")
				.showCancelButton(true)
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								// reuse previous dialog instance, keep widget
								// user state, reset them if you need
								sDialog.setTitleText("Cancelled!")
										.setContentText(
												"Your imaginary file is safe :)")
										.setConfirmText("OK")
										.showCancelButton(false)
										.setCancelClickListener(null)
										.setConfirmClickListener(null)
										.changeAlertType(
												SweetAlertDialog.ERROR_TYPE);

								// or you can new a SweetAlertDialog to show
								/*
								 * sDialog.dismiss(); new
								 * SweetAlertDialog(SampleActivity.this,
								 * SweetAlertDialog.ERROR_TYPE)
								 * .setTitleText("Cancelled!")
								 * .setContentText("Your imaginary file is safe :)"
								 * ) .setConfirmText("OK") .show();
								 */
							}
						})
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								// if(!TextUtils.isEmpty(apkUrl)){
								// mUpdateRequestInterface.downloadApk(apkUrl,
								// "");
								// }

							}
						}).show();
	}

	@Override
	public void onOkBtnClick() {

	}

	@Override
	public void onCancelBtnClick() {

	}

	@Override
	public void startDownloadApk() {
		if (!TextUtils.isEmpty(mApkUrl)&&mApkUrl.contains(".apk")) {
			if (null != mUpdateRequestInterface) {
				mUpdateRequestInterface.downloadApk(mApkUrl, "");
			}
		}
	}

	@Override
	public void onNetChange(boolean connect) {
		if(connect){
			CustomToast.show(this, "网络连接恢复正常");
		}
		else{
			CustomToast.show(this, "没有网络连接");
		}
	}
}
