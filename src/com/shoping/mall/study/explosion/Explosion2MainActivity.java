package com.shoping.mall.study.explosion;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.shoping.mall.R;
import com.shoping.mall.study.explosion.factory.ExplodeParticleFactory;
import com.shoping.mall.study.explosion.factory.FallingParticleFactory;
import com.shoping.mall.study.explosion.factory.FlyawayFactory;
import com.shoping.mall.study.explosion.factory.VerticalAscentFactory;


public class Explosion2MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explosion_activity_main);
       
        ExplosionField explosionField = new ExplosionField(this,new FallingParticleFactory());
        explosionField.addListener(findViewById(R.id.text));
        explosionField.addListener(findViewById(R.id.layout1));

        ExplosionField explosionField2 = new ExplosionField(this,new FlyawayFactory());
        explosionField2.addListener(findViewById(R.id.text2));
        explosionField2.addListener(findViewById(R.id.layout2));
        
        ExplosionField explosionField4 = new ExplosionField(this,new ExplodeParticleFactory());
        explosionField4.addListener(findViewById(R.id.text3));
        explosionField4.addListener(findViewById(R.id.layout3));
        
        ExplosionField explosionField5 = new ExplosionField(this,new VerticalAscentFactory());
        explosionField5.addListener(findViewById(R.id.text4));
        explosionField5.addListener(findViewById(R.id.layout4));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
