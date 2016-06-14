package com.shoping.mall.engine.splash;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.shoping.mall.bean.Splash;
import com.shoping.mall.engine.controller.BaseController;
import com.shoping.mall.engine.listener.ResponseResultListener;

public class SplashController extends BaseController implements SplashRequestInterface{
	
	private SplashModel mSplashModel;
	private SplashHandler mSplashHandler = new SplashHandler(this);
	private ResponseResultListener<SplashResult<Splash>> mResponseResultListener;
	
	public SplashController(Context context,ResponseResultListener<SplashResult<Splash>> resultListener){
		mSplashModel = new SplashModel(context, mSplashHandler);
		this.mResponseResultListener = resultListener;
	}

	private static class SplashHandler extends Handler{
		
		private WeakReference<SplashController> mControllerReference;
		
		public SplashHandler(SplashController controller){
			mControllerReference = new WeakReference<SplashController>(controller);
		}
		
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			final SplashController controller = mControllerReference.get();
			if(null != controller){
				SplashResult<Splash> splashResult = (SplashResult<Splash>) msg.obj;
				controller.updateSplashResult(splashResult);
			}
		}
		
	}
	
	private void updateSplashResult(SplashResult<Splash> splashResult)
	{
		if(null != mResponseResultListener){
			mResponseResultListener.updateVeriCodeResult(splashResult);
		}
	}

	@Override
	public void updateSplashInfo() {
		if(null != mSplashModel){
			mSplashModel.start();
		}
	}

	@Override
	public void stop() {
		if(null != mSplashModel){
			mSplashModel.stop();
		}
	}
}
