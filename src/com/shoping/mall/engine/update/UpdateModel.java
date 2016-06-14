package com.shoping.mall.engine.update;

import java.io.File;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shoping.mall.ConstantValue;
import com.shoping.mall.engine.listener.ResponseResultListener1;
import com.shoping.mall.util.LogUtil;
import com.shoping.mall.util.NetUtil;

public class UpdateModel {

	private Context mContext;
	private ResponseResultListener1<UpdateResult<Update>> mResponseResultListener1;
	
	public UpdateModel(Context context,ResponseResultListener1<UpdateResult<Update>> response){
		this.mContext = context;
		this.mResponseResultListener1 = response;
	}
	
	
	
	public void checkUpDate() {
		
		
		NetUtil.get(ConstantValue.SERVER_URL + ConstantValue.UPDATE,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						notifyUpdateDataException(UpdateResult.UPDATE_DATA_FAIL,"网络请求失败");
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// 得到服务器的版本信息
						String result = new String(arg2);
						// 3.数据处理：检查一下数据是否回复正常
						if (!TextUtils.isEmpty(result)) {
							try {
								JSONObject object = new JSONObject(result);
								if (null != object) {
									if (checkError(object)) {
										// 4.帮助列表数据处理
										String splashStr = object
												.getString("update");
										if (!TextUtils.isEmpty(splashStr)) {
											UpdateData updateData = JSON
													.parseObject(splashStr,
															UpdateData.class);
											String version = updateData
													.getVersion();
											Update update = new Update();
											// 校验是否有新版本
											if (getVersionName()
													.equals(version)) {
												update.setUpdateVersion(false);
												// 版本一致，没有新版本，进入主页面
											} else {
												update.setUpdateVersion(true);
												update.setApkurl(updateData.getApkurl());
												update.setDescription(updateData.getDescription());
												update.setVersion(updateData.getVersion());
												update.setTitle(updateData.getTitle());
												// 有新版本，弹出一升级对话框
												LogUtil.i("显示升级的对话框");
											}
											notifyUpdateDataSucc(UpdateResult.UPDATE_DATA_SUCCESS, update);
										}
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
								notifyUpdateDataException(UpdateResult.UPDATE_DATA_FAIL,e.getMessage());
							} catch (Exception e) {
								notifyUpdateDataException(UpdateResult.UPDATE_DATA_FAIL,e.getMessage());
							}
						}
						else {
							
							notifyUpdateDataException(UpdateResult.UPDATE_DATA_FAIL,"网络请求数据为空");
						}
					}

					@Override
					public void onFinish() {
						super.onFinish();
					}

				});

	}

	private void notifyUpdateDataException(int resultCode,String errMsg)
	{
		UpdateResult<Update> updateResult = new UpdateResult<Update>();
		Update update = new Update();
		updateResult.setSuccess(false);
		updateResult.setErrMsg(errMsg);
		updateResult.setResultCode(resultCode);
		if(null != mResponseResultListener1){
			updateResult.setContent(update);
			mResponseResultListener1.updateResult(updateResult);
		}
	}
	
	private void notifyUpdateDataSucc(int resultCode,Update update)
	{
		UpdateResult<Update> updateResult = new UpdateResult<Update>();
		updateResult.setContent(update);
		updateResult.setSuccess(true);
		updateResult.setResultCode(resultCode);
		if(null != mResponseResultListener1){
			updateResult.setContent(update);
			mResponseResultListener1.updateResult(updateResult);
		}
	}
	
	public boolean saveApkToSdcard(String url, String filePath) {
		// 下载APK，并且替换安装
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// sdcard存在
			// afnal
			FinalHttp finalhttp = new FinalHttp();
			finalhttp.download(url, Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/shen_emall_shopping.apk",
					new AjaxCallBack<File>() {

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							super.onFailure(t, errorNo, strMsg);
							t.printStackTrace();
							notifyUpdateDataException(UpdateResult.DOWNLOAD_APK_FAIL,"下载失败" + strMsg);
						}

						@Override
						public void onLoading(long count, long current) {
							// TODO Auto-generated method stub
							super.onLoading(count, current);
							// 当前下载百分比
							Update update = new Update();
							int progress = (int) (current * 100 / count);
							update.setProgress(progress);
							notifyUpdateDataSucc(UpdateResult.DOWNLOAD_APK_LOADING,update);
							// tv_update_info.setText("下载进度："+progress+"%");
						}

						@Override
						public void onSuccess(File t) {
							// TODO Auto-generated method stub
							super.onSuccess(t);
							Update update = new Update();
							update.setApkFile(t);
							notifyUpdateDataSucc(UpdateResult.DOWNLOAD_APK_SUCC,update);
						}
					});
		} else {
			notifyUpdateDataException(UpdateResult.DOWNLOAD_APK_FAIL,"没有sdcard，请安装上在试");
			return false;
		}
		return false;
	}

	private boolean checkError(JSONObject object) {
		try {
			String response = object.getString("response");
			if (ConstantValue.ERROR.equals(response)) {
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 得到应用程序的版本名称
	 */

	private String getVersionName() {
		// 用来管理手机的APK
		PackageManager pm = mContext.getPackageManager();
		try {
			// 得到知道APK的功能清单文件
			PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

}
