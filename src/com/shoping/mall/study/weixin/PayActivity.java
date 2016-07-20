package com.shoping.mall.study.weixin;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shoping.mall.R;
import com.shoping.mall.util.LogUtil;
import com.shoping.mall.util.NetUtil;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class PayActivity extends Activity {

	private IWXAPI api;
	private Button appayBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);

		api = WXAPIFactory.createWXAPI(this, "wxb4ba3c02aa476ea1");

		appayBtn = (Button) findViewById(R.id.appay_btn);
		appayBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
				Button payBtn = (Button) findViewById(R.id.appay_btn);
				payBtn.setEnabled(false);
				Toast.makeText(PayActivity.this, "获取订单中...", Toast.LENGTH_SHORT)
						.show();
				NetUtil.get(url, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						LogUtil.i("请求预支付id失败" + arg3.getMessage());
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] buf) {

						try {
							if (buf != null && buf.length > 0) {
								String content = new String(buf);
								Log.e("get server pay params:", content);
								JSONObject json = new JSONObject(content);
								if (null != json && !json.has("retcode")) {
									PayReq req = new PayReq();
									// req.appId = "wxf8b4f85f3a794e77"; //
									// 测试用appId
									req.appId = json.getString("appid");
									req.partnerId = json.getString("partnerid");
									req.prepayId = json.getString("prepayid");
									req.nonceStr = json.getString("noncestr");
									req.timeStamp = json.getString("timestamp");
									req.packageValue = json
											.getString("package");
									req.sign = json.getString("sign");
									req.extData = "app data"; // optional
									Toast.makeText(PayActivity.this, "正常调起支付",
											Toast.LENGTH_SHORT).show();
									// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
									api.sendReq(req);
									appayBtn.setEnabled(true);
								} else {
									Log.d("PAY_GET",
											"返回错误" + json.getString("retmsg"));
									Toast.makeText(PayActivity.this,
											"返回错误" + json.getString("retmsg"),
											Toast.LENGTH_SHORT).show();
								}
							} else {
								Log.d("PAY_GET", "服务器请求错误");
								Toast.makeText(PayActivity.this, "服务器请求错误",
										Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							LogUtil.i("解析JSON时，数据异常");
						}

					}
				});

			}
		});
		// try{
		// byte[] buf = Util.httpGet(url);
		// }catch(Exception e){
		// Log.e("PAY_GET", "异常："+e.getMessage());
		// Toast.makeText(PayActivity.this, "异常："+e.getMessage(),
		// Toast.LENGTH_SHORT).show();
		// }
		
		Button checkPayBtn = (Button) findViewById(R.id.check_pay_btn);
		checkPayBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
				Toast.makeText(PayActivity.this,
						String.valueOf(isPaySupported), Toast.LENGTH_SHORT)
						.show();
			}
		});

	}

}
