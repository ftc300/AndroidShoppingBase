package com.shoping.mall.engine.listener;

public interface ResponseResultListener<S> {

	public void updateVeriCodeResult(S s);
	
	public void notifyStartFinish();
	
}
