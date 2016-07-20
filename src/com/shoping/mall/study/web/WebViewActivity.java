package com.shoping.mall.study.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.shoping.mall.ConstantValue;
import com.shoping.mall.R;
import com.shoping.mall.base.BaseActivity;
import com.shoping.mall.util.CustomToast;
import com.shoping.mall.util.LogUtil;

public class WebViewActivity extends BaseActivity {

	private WebView myWebView = null;

	private final String JSPromptName = "getSearchFilter";

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);

		// 注册监听搜索按钮变化的广播

		myWebView = (WebView) findViewById(R.id.myWebView);

		/**
		 * WebSettings提供的一些常用的设置WebView的属性和状态的方法如下：
		 * 　　（1）setAllowFileAccess(boolean allow); 　　　　　　//设置启用或禁止访问文件数据
		 * 　　（2）setBuiltInZoomControls(boolean enabled); 　　//设置是否支持缩放
		 * 　　（3）setDefaultFontSize(int size); 　　　　　　　　 //设置默认的字体大小
		 * 　　（4）setJavaScriptEnabled(boolean flag); 　　　　　 //设置是否支持JavaScript
		 * 　　（5）setSupportZoom(boolean support); 　　　　　　//设置是否支持变焦
		 */

		// 得到设置属性的对象
		WebSettings webSettings = myWebView.getSettings();

		// 使能JavaScript
		webSettings.setJavaScriptEnabled(true);

		// 支持中文，否则页面中中文显示乱码
		webSettings.setDefaultTextEncodingName("gbk");
		//webSettings.setUserAgentString("android");
		// 限制在WebView中打开网页，而不用默认浏览器
		myWebView.setWebViewClient(new MyWebViewClient());

		// 如果不设置这个，JS代码中的按钮会显示，但是按下去却不弹出对话框
		// Sets the chrome handler. This is an implementation of WebChromeClient
		// for use in handling JavaScript dialogs, favicons, titles, and the
		// progress. This will replace the current handler.
		myWebView.setWebChromeClient(
		/**
		 * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
		 * 在WebChromeClient中，当网页的加载进度发生变化时，onProgressChanged(WebView view, int
		 * newProgress)方法会被调用；当网页的图标发生改变时，onReceivedIcon(WebView view, Bitmap
		 * icon)方法会被调用；当网页的标题发生改变时，onReceivedTitle(WebView view, String
		 * title)方法会被调用。利用这些方法，我们便可以很容易的获得网页的加载进度、网页的标题和图标等信息了
		 */
		new WebChromeClient() {

			
			/**
			 * 你需要调用result.confirm()或者result.cancel()来处理jsResult,否则会出问题.
			 */
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				LogUtil.i(url + result.toString());
/**
 * 				为什么onJsAlert只调用了一次
				如果你没有参考上述部分或者没有留意,有时候你会发现onJsAlert只调用了一次,为什么呢,实际上,你可能忽略了一句调用.就是处理JsResult.
 */
//				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//			    result.confirm();
//			    return true;
				return super.onJsAlert(view, url, message, result);
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message,
					String defaultValue, JsPromptResult result) {
				CustomToast.show(WebViewActivity.this, "C端接收到B端Prompt方法的调用");
				if (JSPromptName.equals(message)) {// 筛选
					CustomToast.show(WebViewActivity.this, "C端接收到B端Prompt方法的调用");
				}
				return super.onJsPrompt(view, url, message, defaultValue,
						result);
			}

			@Override
			public void onCloseWindow(WebView window) {
				// TODO Auto-generated method stub
				super.onCloseWindow(window);
			}

			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog,
					boolean isUserGesture, Message resultMsg) {
				// TODO Auto-generated method stub
				return super.onCreateWindow(view, isDialog, isUserGesture,
						resultMsg);
			}

			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message, JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsConfirm(view, url, message, result);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				// TODO Auto-generated method stub
				super.onReceivedIcon(view, icon);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
			}

		});

		// 用JavaScript调用Android函数：
		// 先建立桥梁类，将要调用的Android代码写入桥梁类的public函数
		// 绑定桥梁类和WebView中运行的JavaScript代码
		// 将一个对象起一个别名传入，在JS代码中用这个别名代替这个对象，可以调用这个对象的一些方法
		myWebView.addJavascriptInterface(new WebAppInterface(this),
				"myInterfaceName");

		// 载入页面：本地html资源文件
		myWebView.loadUrl(ConstantValue.SERVER_URL + "/jsp/cookie_test.html");
		LogUtil.i(ConstantValue.SERVER_URL + "/jsp/startApp1.html");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@SuppressLint("NewApi")
	/**
	 * WebViewClient主要帮助WebView处理各种通知、请求事件的
	 * @author shen
	 * （1）doUpdateVisitedHistory(WebView view, String url, boolean isReload);　　　　　　　　   //更新历史记录
	（2）onFormResubmission(WebView view, Message dontResend, Message resend);　　　　  //重新请求网页数据
	 *
	 */
	private class MyWebViewClient extends WebViewClient {

		@Override
		public void doUpdateVisitedHistory(WebView view, String url,
				boolean isReload) {
			// TODO Auto-generated method stub
			super.doUpdateVisitedHistory(view, url, isReload);
		}

		@Override
		public void onFormResubmission(WebView view, Message dontResend,
				Message resend) {
			// TODO Auto-generated method stub
			super.onFormResubmission(view, dontResend, resend);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			LogUtil.i("拦截到的url地址：" + url);
			if (url.contains("native://shen")) {
				CustomToast.show(WebViewActivity.this, "B端调用native方法，C端进行拦截");
				return true;
			}
			else if(url.contains("native://goback")){
				if (myWebView.canGoBack()) {
					myWebView.goBack();
				} else {
					myWebView.stopLoading();
					finish();
				}
				return true;
			}
			myWebView.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public WebResourceResponse shouldInterceptRequest(WebView view,
				String url) {
			// TODO Auto-generated method stub
			return super.shouldInterceptRequest(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public void onLoadResource(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onLoadResource(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

		@Override
		public void onReceivedHttpAuthRequest(WebView view,
				HttpAuthHandler handler, String host, String realm) {
			// TODO Auto-generated method stub
			super.onReceivedHttpAuthRequest(view, handler, host, realm);
		}

		@Override
		public void onReceivedLoginRequest(WebView view, String realm,
				String account, String args) {
			// TODO Auto-generated method stub
			super.onReceivedLoginRequest(view, realm, account, args);
		}

	}

	/**
	 * 自定义的Android代码和JavaScript代码之间的桥梁类
	 * 
	 * @author 1
	 * 
	 */
	public class WebAppInterface {
		Context mContext;

		/** Instantiate the interface and set the context */
		WebAppInterface(Context c) {
			mContext = c;
		}

		/** Show a toast from the web page */
		// 如果target 大于等于API 17，则需要加上如下注解
		// @JavascriptInterface
		public void showToast(String toast) {
			// Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
			Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
			LogUtil.i("B端调用C端显示提示信息：" + toast);
		}
	}
}