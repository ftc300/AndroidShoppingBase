package com.shoping.mall.study.smallbang;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shoping.mall.R;
import com.shoping.mall.study.smallbang.lib.SmallBang;
import com.shoping.mall.study.smallbang.lib.SmallBangListener;

public class SmallBangActivity extends Activity {

    private SmallBang mSmallBang;
    private ImageView mImage;
    private Button mButton;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.small_bang_activity_main);

        mSmallBang = SmallBang.attach2Window(this);

        mImage = (ImageView)findViewById(R.id.image);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(v);
            }
        });


        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber(v);
            }
        });

        mText = (TextView) findViewById(R.id.text);
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redText(v);
            }
        });
    }

    public void addNumber(View view){
        mSmallBang.bang(view,new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
                toast("button +1");
            }
        });
    }

    public void redText(View view){
        mText.setTextColor(0xFFCD8BF8);
        mSmallBang.bang(view,50,new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
                toast("text+1");
            }
        });
    }

    public void like(View view){
        mImage.setImageResource(R.drawable.heart_red);
        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
                toast("heart+1");
            }
        });
    }

    private void toast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
