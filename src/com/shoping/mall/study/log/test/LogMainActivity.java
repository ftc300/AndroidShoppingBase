package com.shoping.mall.study.log.test;

import org.apache.http.Header;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.shoping.mall.R;
import com.shoping.mall.base.BaseActivity;
import com.shoping.mall.study.log.KLog;

public class LogMainActivity extends BaseActivity {

    private static final String LOG_MSG = "KLog is a so cool Log Tool!";
    private static final String TAG = "KLog";
    private static final String URL_XML = "https://raw.githubusercontent.com/ZhaoKaiQiang/KLog/master/app/src/main/AndroidManifest.xml";
    private static String XML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><!--  Copyright w3school.com.cn --><note><to>George</to><from>John</from><heading>Reminder</heading><body>Don't forget the meeting!</body></note>";
    private static String JSON;
    private static String JSON_LONG;
    private AsyncHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity_main);
        init();
    }

    private void init() {
        httpClient = new AsyncHttpClient();
        JSON_LONG = getResources().getString(R.string.json_long);
        JSON = getResources().getString(R.string.json);
    }

    public void log(View view) {
        KLog.v();
        KLog.d();
        KLog.i();
        KLog.w();
        KLog.e();
        KLog.a();
    }

    public void logWithMsg(View view) {
        KLog.v(LOG_MSG);
        KLog.d(LOG_MSG);
        KLog.i(LOG_MSG);
        KLog.w(LOG_MSG);
        KLog.e(LOG_MSG);
        KLog.a(LOG_MSG);
    }

    public void logWithTag(View view) {
        KLog.v(TAG, LOG_MSG);
        KLog.d(TAG, LOG_MSG);
        KLog.i(TAG, LOG_MSG);
        KLog.w(TAG, LOG_MSG);
        KLog.e(TAG, LOG_MSG);
        KLog.a(TAG, LOG_MSG);
    }

    public void logWithNull(View view) {
        KLog.v(null);
        KLog.d(null);
        KLog.i(null);
        KLog.w(null);
        KLog.e(null);
        KLog.a(null);
    }

    public void logWithJson(View view) {
        KLog.json("12345");
        KLog.json(null);
        KLog.json(JSON);
    }

    public void logWithLongJson(View view) {
        KLog.json(JSON_LONG);
    }

    public void logWithJsonTag(View view) {
        KLog.json(TAG, JSON);
    }

    public void logWithFile(View view) {
        KLog.file(Environment.getExternalStorageDirectory(), JSON_LONG);
        KLog.file(TAG, Environment.getExternalStorageDirectory(), JSON_LONG);
        KLog.file(TAG, Environment.getExternalStorageDirectory(), "test.txt", JSON_LONG);
    }

    public void logWithXml(View view) {
        KLog.xml("12345");
        KLog.xml(null);
        KLog.xml(XML);
    }

    public void logWithXmlFromNet(View view) {
        httpClient.get(this, URL_XML, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				KLog.e(arg2);
				
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				KLog.xml(arg2);
				
			}
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // MENU
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ZhaoKaiQiang/KLog")));
                break;
            case R.id.action_csdn:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/zhaokaiqiang1992")));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
