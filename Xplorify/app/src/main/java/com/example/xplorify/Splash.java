package com.example.xplorify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class Splash extends Activity {

    LinearLayout LL;
    ConstraintLayout CL;
    Animation downtoup, uptodown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LL = findViewById(R.id.top_splash);
        CL = findViewById(R.id.bot_splash);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        LL.setAnimation(uptodown);
        CL.setAnimation(downtoup);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle lanim = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_left,R.anim.go_out_left).toBundle();
                Intent l = new Intent(Splash.this,Login.class);
                startActivity(l,lanim);
                Splash.this.finish();
            }
        },2000);
    }
}
