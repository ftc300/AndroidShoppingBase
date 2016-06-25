package com.shoping.mall.study.fontdrawable;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.shoping.mall.R;
import com.shoping.mall.study.fontdrawable.lib.FontDrawable;
import com.shoping.mall.study.fontdrawable.lib.FontProgressDrawable;

public class FontDrawableActivity extends Activity {

    static final String CUSTOM_FONT_PATH = "fontawesome-webfont.ttf";

    static final char SPACE_SHUTTLE_CODE = '\uf197';

    static final int MATERIAL_BLUE = 0xff00a8f7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fontdrawable_activity_main);

        ImageView drawableImageView = (ImageView) findViewById(R.id.drawable_image_view);
        FontDrawable spaceShuttle = new FontDrawable.Builder(this, SPACE_SHUTTLE_CODE, CUSTOM_FONT_PATH)
                .setSizeDp(100)
                .setColor(MATERIAL_BLUE)
                .build();
        drawableImageView.setImageDrawable(spaceShuttle);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        FontProgressDrawable progressDrawable = new FontProgressDrawable.Builder(this, SPACE_SHUTTLE_CODE, CUSTOM_FONT_PATH)
                .setProgressColor(MATERIAL_BLUE)
                .setBackGroundColor(Color.LTGRAY)
                .setPaddingDp(4)
                .build();
        ratingBar.setProgressDrawable(progressDrawable);
    }

}
