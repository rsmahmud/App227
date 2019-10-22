package com.bitbytelab.app_227;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class AnimActivity extends AppCompatActivity {

    ImageView imageView;
    Animation anim;
    Drawable frameAnimation;

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
    public void startAnimation(View view){
        if(frameAnimation instanceof Animatable){
            ((Animatable)frameAnimation).start();
        }
    }
    public void stopAnimation(View view){
        if(((Animatable)frameAnimation).isRunning()){
            ((Animatable)frameAnimation).stop();
        }
    }
    public void dothis(View view){
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,1.0f,
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,0.0f);
        translateAnimation.setDuration(5000);

        RotateAnimation rotateAnimation = new RotateAnimation(0,360,
                Animation.RELATIVE_TO_SELF,.5f,
                Animation.RELATIVE_TO_SELF,.5f);
        rotateAnimation.setDuration(5000);

        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotateAnimation);

        imageView.startAnimation(animationSet);
    }
}
