package com.shoping.mall.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;

import com.shoping.mall.ConstantValue;
import com.shoping.mall.GlobalParams;
import com.shoping.mall.bean.Help;
import com.shoping.mall.engine.HelpEngine;
import com.shoping.mall.engine.HelpEngineImpl;
import com.shoping.mall.util.FileUtil;
import com.shoping.mall.util.UploadUtil;
import com.shoping.mall.util.net.HttpClientUtil;

public class HelpTest extends AndroidTestCase {

	private final String TAG = this.getClass().getSimpleName();

	public void testNet() {
		HttpClientUtil util = new HttpClientUtil();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("version", "1");
		String json = util.sendPost(ConstantValue.SERVER_URL
				+ ConstantValue.HELP, params);
		System.out.println(json);
		Log.i(TAG, json);
	}

	public void testGetHelpList() {
		GlobalParams.CONTEXT = getContext();
		HelpEngine engine = new HelpEngineImpl();
		List<Help> result = engine.getServiceHelpList();
		System.out.println("result size:" + result.size());

	}

	public void testUploadFile() {
		HttpClientUtil util = new HttpClientUtil();
		File dir = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "shen");
		File myFile = new File(dir.getAbsolutePath() + File.separator
				+ "shen.log");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("file", myFile);
		params.put("name", "crash-2015-0821.log");
		String json = UploadUtil.uploadFile(myFile, ConstantValue.SERVER_URL
				+ ConstantValue.UPLOAD_FILE);
		// String json = util.sendPost(ConstantValue.COMMON_URI +
		// ConstantValue.UPLOAD_FILE , params);
		System.out.println(json);
		Log.i(TAG, json);
	}

	public void testUploadFile1() {
		File dir = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "shen");
		File myFile = new File(dir.getAbsolutePath() + File.separator
				+ "shen.log");
		try {
			UploadUtil.uploadFile1(myFile.getAbsolutePath(),
					ConstantValue.SERVER_URL + ConstantValue.UPLOAD_FILE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String json = util.sendPost(ConstantValue.COMMON_URI +
		// ConstantValue.UPLOAD_FILE , params);
	}

	public void testSplash() {
		HttpClientUtil util = new HttpClientUtil();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("version", "1");
		String json = util.sendPost(ConstantValue.SERVER_URL
				+ ConstantValue.SPLASH, params);

		System.out.println(json);
	}

	public void testDownImg() {
		String downloadurl = "http://d.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f41348cf6a596211f95cad0c85e10.jpg";
		// final String filePath =
		// FileUtil.getAppCache(getContext().getCacheDir() + "/shen/");
		final String filePath = getFilePath()+ "/shen/";
		final String fileName = FileUtil.getFileName(downloadurl);

		try {
			String encodedUrl = Uri.encode(downloadurl, "@#&=*+-_.,:!?()/~'%");
			HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl)
					.openConnection();
			conn.setConnectTimeout(20 * 1000);
			conn.setReadTimeout(20 * 1000);
			conn.setRequestProperty("Referer", "");
			conn.connect();
			InputStream inputStream = conn.getInputStream();
			if (inputStream != null) {
				// 获取资源图片

				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				inputStream.close();
				File f = new File(filePath, fileName);
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(f));
				bitmap.compress(CompressFormat.JPEG, 100, bos);
				bos.flush();
				bos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFilePath() {
		String externalStorageState = Environment.getExternalStorageState();
		// Environment.MEDIA_MOUNTED代表着外部存储设备存在，并且是可读写的
		if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
			// getExternalStorageDirectory获取外部存储设备的路径
			Log.i("SDcard路径", Environment.getExternalStorageDirectory()
					.getAbsolutePath());
			return Environment.getExternalStorageDirectory().toString();
		}
		return Environment.getExternalStorageDirectory().toString();//获取手机根目录

	}
}
