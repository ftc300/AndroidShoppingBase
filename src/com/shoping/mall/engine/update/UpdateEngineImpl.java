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
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shoping.mall.ConstantValue;
import com.shoping.mall.util.NetUtil;

public class UpdateEngineImpl implements UpdateEngine{

	private Context mContext;
	
	public UpdateEngineImpl(Context context)
	{
		this.mContext = context;
	}
	
	@Override
	public boolean saveApkToSdcard(String url, String filePath) {
		// 下载APK，并且替换安装
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// sdcard存在
			// afnal
			FinalHttp finalhttp = new FinalHttp();
			finalhttp.download(url, Environment
					.getExternalStorageDirectory().getAbsolutePath()+"/mobilesafe2.0.apk",
					new AjaxCallBack<File>() {

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							t.printStackTrace();
							Toast.makeText(mContext, "下载失败", 1).show();
							super.onFailure(t, errorNo, strMsg);
						}

						@Override
						public void onLoading(long count, long current) {
							// TODO Auto-generated method stub
							super.onLoading(count, current);
							//tv_update_info.setVisibility(View.VISIBLE);
							//当前下载百分比
							int progress = (int) (current * 100 / count);
							//tv_update_info.setText("下载进度："+progress+"%");
						}

						@Override
						public void onSuccess(File t) {
							// TODO Auto-generated method stub
							super.onSuccess(t);
							installAPK(t);
						}
						/**
						 * 安装APK
						 * @param t
						 */
						private void installAPK(File t) {
						  Intent intent = new Intent();
						  intent.setAction("android.intent.action.VIEW");
						  intent.addCategory("android.intent.category.DEFAULT");
						  intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
						  //startActivity(intent);
						}
						
					
					});
		} else {
			Toast.makeText(mContext, "没有sdcard，请安装上在试",
					0).show();
			return false;
		}
		return false;
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
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "";
	}
	}

}
