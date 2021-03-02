package com.example.blood4u;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    //var

    ImageView ivTop,ivlogo,ivBeat,ivBottom;
    TextView text;
    CharSequence charSequence;
    int splashDuration = 3000;
    int index;
    long delay = 200;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //assigning variable
         ivlogo = findViewById(R.id.logo);
         ivTop = findViewById(R.id.iv_top);
         ivBeat = findViewById(R.id.iv_beat);
         ivBottom = findViewById(R.id.iv_bottom);
         text = findViewById(R.id.text_view);
        // Initialize object animator
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                ivlogo,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        );

        //set duration
        objectAnimator.setDuration(500);

        //Set repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);

        //Set repeat mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);

        //Start animator
        objectAnimator.start();

        //set animate text
        animText("Blood4U");
        //initialize handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, splashDuration);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //when runnable is running
            //set text
            text.setText(charSequence.subSequence(0, index++));
            //check condition
            if (index <= charSequence.length()) {
                //when index is equal to text length
                //Run handler
                handler.postDelayed(runnable, delay);
            }
        }
    };


        //Create animated text method
    public void animText(CharSequence cs){
        //set text
        charSequence = cs;
        //clear index
        index = 0;
        //Remove callback
        handler.removeCallbacks(runnable);
        //Run handler
        handler.postDelayed(runnable,delay);
    }
}