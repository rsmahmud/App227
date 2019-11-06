package com.bitbytelab.app_227;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor proximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager != null){
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }else{
            Toast.makeText(this,"Sensor Manager Null!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager != null){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(proximitySensor != null){
            sensorManager.registerListener(this,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()){
            case Sensor.TYPE_PROXIMITY:
                float[] values = sensorEvent.values;
                if(values[0] < 1){
                    setActivityBackgroundColor(Color.RED);
                }else{
                    setActivityBackgroundColor(Color.WHITE);
                }
                break;
        }
    }

    public void setActivityBackgroundColor(int color){
        View view = getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
