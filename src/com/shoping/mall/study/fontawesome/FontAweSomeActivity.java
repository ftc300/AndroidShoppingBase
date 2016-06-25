package com.shoping.mall.study.fontawesome;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.shoping.mall.R;
import com.shoping.mall.base.BaseActivity;

public class FontAweSomeActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_font_awe_some_layout);
		Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
		((TextView)findViewById(R.id.test_view)).setTypeface(font);
		((TextView)findViewById(R.id.fa_google_plus)).setTypeface(font);
		((TextView)findViewById(R.id.fa_thumbs_o_up)).setTypeface(font);
		((TextView)findViewById(R.id.fa_save)).setTypeface(font);
		((TextView)findViewById(R.id.fa_toggle_on)).setTypeface(font);
		((TextView)findViewById(R.id.fa_toggle_on)).setTypeface(font);
		((TextView)findViewById(R.id.fa_modx)).setTypeface(font);
		
		Typeface font1 = Typeface.createFromAsset(getAssets(),"iconfont.ttf");
		((TextView)findViewById(R.id.deletecircle)).setTypeface(font1);
		((TextView)findViewById(R.id.person_file)).setTypeface(font1);
		((TextView)findViewById(R.id.un_register)).setTypeface(font1);
		
	}
	
	
}
