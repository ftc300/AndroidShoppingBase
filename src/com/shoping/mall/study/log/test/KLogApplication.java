package com.shoping.mall.study.log.test;

import android.app.Application;

import com.shoping.mall.BuildConfig;
import com.shoping.mall.study.log.KLog;

/**
 * Created by zhaokaiqiang on 15/11/14.
 */
public class KLogApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.DEBUG);
    }
}
