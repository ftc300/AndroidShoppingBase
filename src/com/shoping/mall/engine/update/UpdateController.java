package com.shoping.mall.engine.update;

import android.content.Context;

import com.shoping.mall.engine.controller.BaseController;
import com.shoping.mall.engine.listener.ResponseResultListener1;

public class UpdateController extends BaseController implements UpdateRequestInterface{

	private UpdateModel mUpdateModel;
	
	public UpdateController(Context context,ResponseResultListener1<UpdateResult<Update>> resultListener){
		mUpdateModel = new UpdateModel(context, resultListener);
	}

	@Override
	public void stop() {

	}

	@Override
	public void updateAppVersion() {
	 if(null != mUpdateModel){
		 mUpdateModel.checkUpDate();
	 }
	}

	@Override
	public void downloadApk(String url, String filePath) {
		if(null != mUpdateModel){
			mUpdateModel.saveApkToSdcard(url, filePath);
		}
		
	}
}
