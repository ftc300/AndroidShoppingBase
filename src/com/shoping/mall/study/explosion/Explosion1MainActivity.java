package com.shoping.mall.study.explosion;

import android.app.Activity;
import android.os.Bundle;
import com.shoping.mall.study.explosion.explosion.ExplosionField;
import com.shoping.mall.R;

public class Explosion1MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.explosion1_activity_main_1);
//        setContentView(R.layout.explosion1_activity_main_az);
        setContentView(R.layout.explosion1_activity_main);

        ExplosionField explosionField = new ExplosionField(this);

        explosionField.addListener(findViewById(R.id.root));
    }


}
