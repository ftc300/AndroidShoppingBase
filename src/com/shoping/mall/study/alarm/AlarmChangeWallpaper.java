package com.shoping.mall.study.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.shoping.mall.R;
import com.shoping.mall.base.BaseActivity;

public class AlarmChangeWallpaper extends BaseActivity {
	AlarmManager alarmManager;
	Button start, stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_activity_main);
		start = (Button) findViewById(R.id.start);
		stop = (Button) findViewById(R.id.stop);
		alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
		// 指定启动ChangeService组件
		Intent intent = new Intent(AlarmChangeWallpaper.this,
				ChangeService.class);
		final PendingIntent pi = PendingIntent.getService(
				AlarmChangeWallpaper.this, 0, intent, 0);
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
				// 5秒后通过PendingIntent pi对象发送广播
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 5000, pi);
				start.setEnabled(false);
				stop.setEnabled(true);
				Toast.makeText(AlarmChangeWallpaper.this, "壁纸定时更换成功",
						Toast.LENGTH_SHORT).show();
			}
		});

		stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				start.setEnabled(true);
				stop.setEnabled(false);
				// 取消对pi的调度
				alarmManager.cancel(pi);
			}
		});

	}
}
