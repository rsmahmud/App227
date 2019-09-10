package com.bitbytelab.app_227;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimationActivity extends AppCompatActivity {

    ImageView imageView;
    Animation anim;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        context = this;
        imageView = findViewById(R.id.iv_anim_tortoise);
        imageView.setBackgroundResource(R.drawable.skateboarding_tortoise);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim = AnimationUtils.loadAnimation(context,R.anim.anim_tortoise);
                imageView.startAnimation(anim);
            }
        });


    }
}
