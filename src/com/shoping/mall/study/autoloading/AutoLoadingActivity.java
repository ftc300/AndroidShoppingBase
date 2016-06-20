package com.shoping.mall.study.autoloading;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.shoping.mall.R;

public class AutoLoadingActivity extends Activity {

	private RelativeLayout ll;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto_loading_activity_main);
		ll = (RelativeLayout) findViewById(R.id.ll);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void click(View v)
	{
		clickLoading();
	}
	private AutoLoading box;
	private void clickLoading()
	{

		if(null == box)
		box = new AutoLoading(this,ll); // or new AutoLoading(this,R.id.listView)

        
		//box.setLoadingMessage("我们正在努力加载，请等待~");

//        View emptyCollectionView = View.inflate(this,
//
//                R.layout.exception_failure, null);
//
//        box.addCustomView(emptyCollectionView, "failure");// 表示自定义的失败或者重试界面
//        box.showLoadingLayout();
//        box.setClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				box.hideAll();
//			}
//		});


        //box.showCustomView("failure");// 加载失败时调用
        //box.showExceptionLayout();//异常时调用和加载失败一样的
        //box.showInternetOffLayout();//类似

        new Thread(){
            public void run() {
            	try {
            		handler.sendEmptyMessage(1);
					Thread.sleep(5000);
					handler.sendEmptyMessage(2);
					Thread.sleep(5000);
					handler.sendEmptyMessage(3);
					Thread.sleep(5000);
					handler.sendEmptyMessage(4);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            };
        }.start();

	}
	
	private Handler handler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				box.showLoadingLayout();
				break;
			case 2:
				box.showInternetOffLayout();
				break;
			case 3:
				box.showExceptionLayout();
				break;
			case 4:
				box.hideAll();
			default:
				break;
			}
		};
	};
}
