package com.bitbytelab.app_227;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_read_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ReadWriteActivity.class));
            }
        });
        findViewById(R.id.btn_basic_animation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AnimActivity.class));
            }
        });
        findViewById(R.id.btn_animation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AnimationActivity.class));
            }
        });
        findViewById(R.id.btn_signup_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });
        findViewById(R.id.btn_custom_view_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CustomViewActivity.class));
            }
        });
        findViewById(R.id.btn_surface_drawing_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SurfaceDrawingActivity.class));
            }
        });
        findViewById(R.id.btn_property_animation_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PropertyAnimationActivity.class));
            }
        });
        findViewById(R.id.btn_maps_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
            }
        });
        findViewById(R.id.btn_sensor_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SensorActivity.class));
            }
        });
        findViewById(R.id.btn_proximity_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProximityActivity.class));
            }
        });
        findViewById(R.id.btn_bluetooth_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BluetoothActivity.class));
            }
        });
        findViewById(R.id.btn_web_view_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
            }
        });
    }
}
