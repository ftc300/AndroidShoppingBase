package com.shoping.mall.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.shoping.mall.Constant;
import com.shoping.mall.application.MallApp;

public class BaseUtil {

	
	/**
     * 获取手机分辨率
     */
    public static String getDisplayMetrix(Context context)
    {
        if (Constant.Screen.SCREEN_WIDTH == 0 || Constant.Screen.SCREEN_HEIGHT == 0)
        {
            if (context != null)
            {
                int width = 0;
                int height = 0;
                SharedPreferences DiaplayMetrixInfo = context.getSharedPreferences("display_metrix_info", 0);
                if (context instanceof Activity)
                {
                    WindowManager windowManager = ((Activity)context).getWindowManager();
                    Display display = windowManager.getDefaultDisplay();
                    DisplayMetrics dm = new DisplayMetrics();
                    display.getMetrics(dm);
                    width = dm.widthPixels;
                    height = dm.heightPixels;
 
                    Editor editor = DiaplayMetrixInfo.edit();
                    editor.putInt("width", width);
                    editor.putInt("height", height);
                    editor.commit();
                }
                else
                {
                    width = DiaplayMetrixInfo.getInt("width", 0);
                    height = DiaplayMetrixInfo.getInt("height", 0);
                }
 
                Constant.Screen.SCREEN_WIDTH = width;
                Constant.Screen.SCREEN_HEIGHT = height;
            }
        }
        return Constant.Screen.SCREEN_WIDTH + "×" + Constant.Screen.SCREEN_HEIGHT;
    }
    
    /**
     * 检测某程序是否安装
     */
    public static boolean isInstalledApp(Context context, String packageName)
    {
        Boolean flag = false;
 
        try
        {
            PackageManager pm = context.getPackageManager();
            List<PackageInfo> pkgs = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
            for (PackageInfo pkg : pkgs)
            {
                // 当找到了名字和该包名相同的时候，返回
                if ((pkg.packageName).equals(packageName))
                {
                    return flag = true;
                }
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        return flag;
    }
    
    
    /**
     * 安装.apk文件
     *
     * @param context
     */
    public void install(Context context, String fileName)
    {
        if (TextUtils.isEmpty(fileName) || context == null)
        {
            return;
        }
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
 
    /**
     * 安装.apk文件
     *
     * @param context
     */
    public void install(Context context, File file)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @return 返回像素值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
 
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @return 返回dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    
    private static String DEVICEKEY = "";
    
    /**
     * 根据mac地址+deviceid
     * 获取设备唯一编码
     * @return
     */
    public static String getDeviceKey()
    {
        if ("".equals(DEVICEKEY))
        {
            String macAddress = "";
            WifiManager wifiMgr = (WifiManager)MallApp.getInstance().getSystemService(MallApp.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
            if (null != info)
            {
                macAddress = info.getMacAddress();
            }
            TelephonyManager telephonyManager =
                (TelephonyManager)MallApp.getInstance().getSystemService(MallApp.TELEPHONY_SERVICE);
            String deviceId = telephonyManager.getDeviceId();
            DEVICEKEY = MD5Util.encode("android" + Constant.APPKEY + Constant.APPPWD + macAddress + deviceId);
        }
        return DEVICEKEY;
    }
    
    /**
     * 获取手机及SIM卡相关信息
     * @param context
     * @return
     */
    public static Map<String, String> getPhoneInfo(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        String imsi = tm.getSubscriberId();
        String phoneMode = android.os.Build.MODEL;
        String phoneSDk = android.os.Build.VERSION.RELEASE;
        map.put("imei", imei);
        map.put("imsi", imsi);
        map.put("phoneMode", phoneMode+"##"+phoneSDk);
        map.put("model", phoneMode);
        map.put("sdk", phoneSDk);
        return map;
    }
}
