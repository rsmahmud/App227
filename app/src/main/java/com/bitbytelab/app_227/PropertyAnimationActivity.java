package com.bitbytelab.app_227;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class PropertyAnimationActivity extends AppCompatActivity {

    ImageView imageView;
    Drawable frameAnimation;
    Animation anim;
    PropertyAnimation propertyAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        propertyAnimation = new PropertyAnimation(this);

        setContentView(R.layout.activity_property_animation);
        imageView = findViewById(R.id.image_view);
        imageView.setBackgroundResource(R.drawable.skateboarding_tortoise);
        frameAnimation = imageView.getBackground();
        anim = AnimationUtils.loadAnimation(this,R.anim.animation);
    }

    public void startAnimation(View view){
        if(frameAnimation instanceof Animatable){
            ((Animatable)frameAnimation).start();
        }
    }
    public void stopAnimation(View view){

    }
//    public void dothis(View view){
//        AnimationSet animationSet = new AnimationSet(this);
//        TranslateAnimation tm;
//
//        tm.setDuration(5000);
//        animationSet.addAnimation(tm);
//
//        RotateAnimation ra;
//    }
}
