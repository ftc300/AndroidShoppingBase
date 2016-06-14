package com.shoping.mall.receiver;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shoping.mall.application.MallApp;
import com.shoping.mall.util.net.NetUtil;

public class NetBroadcastReceiver extends BroadcastReceiver {

	private static ArrayList<NetStateChangeListener> mListeners = new ArrayList<NetStateChangeListener>();
	private String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(NET_CHANGE_ACTION)) {
			boolean connect = false;
			if (NetUtil.NETWORN_NONE == NetUtil.getNetworkState(context)) {
				MallApp.setConnect(false);
				connect = false;
			}
			else{
				connect = true;
			}
			if (mListeners.size() > 0)//通知接口完成加载
			{
				for (NetStateChangeListener handler:mListeners){
					handler.onNetChange(connect);
				}
			}
		}
	}

	public interface NetStateChangeListener {
		public abstract void onNetChange(boolean connect);
	}

	public static void addNetStateListener(NetStateChangeListener listener) {
		if (null != mListeners) {
			mListeners.add(listener);
		}
	}

	public static void clearNetStateListener() {
		if (null != mListeners) {
			mListeners.clear();
		}
	}

}
