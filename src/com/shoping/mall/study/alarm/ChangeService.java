package com.shoping.mall.study.alarm;
 
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.IBinder;

import com.shoping.mall.R;
 
public class ChangeService extends Service {
     //定义定时更换的壁纸资源
     int[] wallpapers = new int[] { R.drawable.girl1, R.drawable.girl2, R.drawable.girl3, R.drawable.girl4 };
     //定义系统的壁纸管理服务
     WallpaperManager wallpaperManager;
     //定义当前所显示的壁纸
     int current = 0;
 
     @Override
     public int onStartCommand(Intent intent, int flags, int startId) {
          //如果到了最后一张，重新开始
          if (current >=wallpapers.length) {
                current = 0;
          }
          try {
                //更换壁纸
                wallpaperManager.setResource(wallpapers[current++]);
          } catch (Exception e) {
                e.printStackTrace();
          }
          return START_STICKY;
     }
 
     @Override
     public void onCreate() {
         // TODO Auto-generated method stub
         super.onCreate();
         //初始化WallpaperManager
         wallpaperManager=WallpaperManager.getInstance(this);
     }
 
     @Override
     public IBinder onBind(Intent intent) {
          // TODO Auto-generated method stub
          return null;
     }
 
}