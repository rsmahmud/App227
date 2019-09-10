package com.bitbytelab.app_227;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class AnimActivity extends AppCompatActivity {

    ImageView imageView;
    Animation anim;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        context = this;
        imageView = findViewById(R.id.iv_anim_image);
        imageView.setBackgroundResource(R.drawable.pro_pic_fb);

        //anim = AnimationUtils.loadAnimation(this,R.anim.animation);
        //anim.setDuration(1000);

        findViewById(R.id.btn_animate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim = AnimationUtils.loadAnimation(context,R.anim.animation);
                imageView.startAnimation(anim);
            }
        });
        findViewById(R.id.btn_translate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim = AnimationUtils.loadAnimation(context,R.anim.anim_translate);
                imageView.startAnimation(anim);
            }
        });
        findViewById(R.id.btn_scale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim = AnimationUtils.loadAnimation(context,R.anim.anim_scale);
                imageView.startAnimation(anim);
            }
        });
        findViewById(R.id.btn_rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim = AnimationUtils.loadAnimation(context,R.anim.anim_rotate);
                imageView.startAnimation(anim);
            }
        });
        findViewById(R.id.btn_alpha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim = AnimationUtils.loadAnimation(context,R.anim.anim_alpha);
                imageView.startAnimation(anim);
            }
        });
    }
}
