package com.shoping.mall.engine.splash;

import java.io.File;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.shoping.mall.bean.Splash;
import com.shoping.mall.engine.model.BaseModel;
import com.shoping.mall.util.FileUtil;
import com.shoping.mall.util.SharedPreUtil;

public class SplashModel extends BaseModel{

	private SplashEngine mSplashEngine;
	private Handler mResponseHandler;
	private Context mContext;

	public SplashModel(Context context, Handler responseHandler) {
		this.mContext = context;
		this.mResponseHandler = responseHandler;
	}

	@Override
	public void run() {
		if (null == mSplashEngine) {
			mSplashEngine = new SplashEngineImpl();
		}
		Splash result = mSplashEngine.getSplashData();
		SplashResult<Splash> splashResult = new SplashResult<Splash>();
		if (null != result) {
			String url = result.getImgUrl();
			if (!TextUtils.isEmpty(url) && URLUtil.isValidUrl(url)) {
				String filePath = FileUtil.getAppCache(mContext.getCacheDir()
						+ "/emall/");
				String fileName = FileUtil.getFileName(url);
				String beforeFileName = SharedPreUtil.get(mContext,
						"splash_file_name", "");
				File file = new File(filePath);
				if (!TextUtils.isEmpty(beforeFileName)) {
					if (fileName.equals(beforeFileName)&&file.listFiles().length<=0) {
						splashResult.setSuccess(true);
						splashResult
								.setResultCode(SplashResult.UPDATE_SPLASH_EQUAL);
						splashResult.setContent(result);
						sendSplashResult(splashResult);
						return;
					} else {
						if (file.isDirectory()) {
							File[] files = file.listFiles();
							for (File f : files) {
								if (f.getName().equals(beforeFileName)) {
									f.delete();
									break;
								}
							}
						}
					}
				}
				boolean success = mSplashEngine.saveSplashImgToSdcard(url,
						filePath);
				if (success) {
					SharedPreUtil.put(mContext, "splash_file_name", fileName);
					splashResult
							.setResultCode(SplashResult.UPDATE_SPLASH_SUCCESS);
				} else {
					splashResult.setResultCode(SplashResult.UPDATE_SPLASH_FAIL);
					splashResult.setErrMsg("根据url地址下载图片到sd卡时出现异常");
				}
				splashResult.setSuccess(success);
				splashResult.setContent(result);
				sendSplashResult(splashResult);
			} else {
				splashResult.setSuccess(false);
				splashResult.setContent(result);
				splashResult.setErrMsg("欢迎界面的图片url地址为空或不合法");
				sendSplashResult(splashResult);
			}
		}
		else {
			splashResult.setSuccess(false);
			splashResult.setContent(result);
			splashResult.setErrMsg("请求的欢迎界面数据为空");
			sendSplashResult(splashResult);
		}

	}

	private void sendSplashResult(SplashResult<Splash> splashResult) {
		if (null != mResponseHandler) {
			Message msg = mResponseHandler.obtainMessage();
			msg.obj = splashResult;
			mResponseHandler.sendMessage(msg);
		}
	}

	@Override
	public void stop() {
		
	}

}
