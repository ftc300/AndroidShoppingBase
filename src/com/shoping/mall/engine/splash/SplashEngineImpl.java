package com.shoping.mall.engine.splash;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.shoping.mall.ConstantValue;
import com.shoping.mall.bean.Splash;
import com.shoping.mall.util.FileUtil;
import com.shoping.mall.util.net.HttpClientUtil;

public class SplashEngineImpl implements SplashEngine {

	@Override
	public Splash getSplashData() {
		Splash result = null;
		HttpClientUtil util = new HttpClientUtil();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("version", "1");
		String json = util.sendPost(ConstantValue.SERVER_URL + ConstantValue.SPLASH , params);
		// 3.数据处理：检查一下数据是否回复正常
		if(!TextUtils.isEmpty(json)){
			try {
				JSONObject object = new JSONObject(json);
				if(null != object)
				{
					if(checkError(object)){
						// 4.帮助列表数据处理
						String splashStr = object.getString("splash");
						if(!TextUtils.isEmpty(splashStr)){
							result = JSON.parseObject(splashStr, Splash.class);
						}
						//持久化操作，如果数据量过大：开启子线程完成数据的操作
						return result;
					}
					else {
						
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		return result;
	}

	private boolean checkError(JSONObject object) {
		try {
			String response = object.getString("response");
			if(ConstantValue.ERROR.equals(response)){
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean saveSplashImgToSdcard(String url,String filePath) {
//		String downloadurl = "http://d.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f41348cf6a596211f95cad0c85e10.jpg";
		 final String savePath = FileUtil.getAppCache(filePath);
		//final String filePath = getFilePath()+ "/shen/";
		final String fileName = FileUtil.getFileName(url);

		try {
			String encodedUrl = Uri.encode(url, "@#&=*+-_.,:!?()/~'%");
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
				File f = new File(savePath, fileName);
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(f));
				bitmap.compress(CompressFormat.JPEG, 100, bos);
				bos.flush();
				bos.close();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
}
