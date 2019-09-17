package com.bitbytelab.app_227;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

public class CustomViewActivity extends AppCompatActivity {

    CustomView cv;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cv = new CustomView(this);
        setContentView(cv);
    }
}
