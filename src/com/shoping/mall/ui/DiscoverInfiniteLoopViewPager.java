package com.shoping.mall.ui;





import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.shoping.mall.adapter.DiscoverInfiniteLoopViewPagerAdapter;
import com.shoping.mall.adapter.DiscoverMPagerAdapter;
import com.shoping.mall.application.MallApp;

public class DiscoverInfiniteLoopViewPager extends DiscoverMViewPager {
	
	private MallApp mApplication;
	private Handler handler;
	
	public DiscoverInfiniteLoopViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mApplication = (MallApp)context.getApplicationContext();
	}

	public DiscoverInfiniteLoopViewPager(Context context) {
		super(context);
		mApplication = (MallApp)context.getApplicationContext();
	}

	@Override
	public void setAdapter(DiscoverMPagerAdapter adapter) {
		super.setAdapter(adapter);
		//设置当前展示的位置
		setCurrentItem(0);
	}
	
	public void setInfinateAdapter(Handler handler,DiscoverMPagerAdapter adapter){
		this.handler = handler;
		setAdapter(adapter);
	}
	
	@Override
	public void setCurrentItem(int item) {
		item = getOffsetAmount() + (item % getAdapter().getCount());
		super.setCurrentItem(item);
	}
	/**
	 * 从0开始向右(负向滑动)可以滑动的次数
	 * @return
	 */
	private int getOffsetAmount() {
		if (getAdapter() instanceof DiscoverInfiniteLoopViewPagerAdapter) {
			DiscoverInfiniteLoopViewPagerAdapter infiniteAdapter = (DiscoverInfiniteLoopViewPagerAdapter) getAdapter();
			// 允许向前滚动400000次
			return infiniteAdapter.getRealCount() * 100000;
		} else {
			return 0;
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		if (action == MotionEvent.ACTION_DOWN ||action == MotionEvent.ACTION_MOVE) {
			mApplication.isRun = false;
			mApplication.isDown = true;
			if(handler!=null)
			handler.removeCallbacksAndMessages(null);
//			System.out.println("InfiniteLoopViewPager  dispatchTouchEvent =====>>> ACTION_DOWN");
		} else  {
			mApplication.isRun = true;
			mApplication.isDown = false;
			if(handler!=null){
				handler.removeCallbacksAndMessages(null);
				handler.sendEmptyMessage(1);
			}
//			System.out.println("InfiniteLoopViewPager  dispatchTouchEvent =====>>> ACTION_UP");
		}
		return super.dispatchTouchEvent(ev);
	}
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		boolean ret = super.onInterceptTouchEvent(ev);
//		if(ret){
//			requestDisallowInterceptTouchEvent(true);
//		}
//		return ret;
//	}
	@Override
	public void setOffscreenPageLimit(int limit) {
		super.setOffscreenPageLimit(limit);
	}
}
