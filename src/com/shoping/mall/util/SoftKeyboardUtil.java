package com.shoping.mall.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftKeyboardUtil {

	    /**
	     * 关闭系统的软键盘
	     * @param activity
	     */
	    public static void dismissSoftKeyboard(Activity activity)
	    {
	        View view = activity.getWindow().peekDecorView();
	        if (view != null)
	        {
	            InputMethodManager inputmanger = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
	            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
	        }
	    }
}
