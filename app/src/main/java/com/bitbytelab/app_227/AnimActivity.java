package com.bitbytelab.app_227;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class AnimActivity extends AppCompatActivity {

    ImageView imageView;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        imageView = findViewById(R.id.iv_anim_image);
        imageView.setBackgroundResource(R.drawable.pro_pic_fb);

        anim = AnimationUtils.loadAnimation(this,R.anim.animation);
        anim.setDuration(1000);

        findViewById(R.id.btn_do_animation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(anim);
            }
        });
    }
}
