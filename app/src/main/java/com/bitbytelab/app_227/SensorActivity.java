package com.bitbytelab.app_227;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    Sensor lightSensor;
    Sensor proxiSensor;
    Sensor gyroSensor;
    Sensor accSensor;
    Sensor magSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager != null){
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            proxiSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            gyroSensor  = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            accSensor   = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magSensor   = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }else{
            Toast.makeText(getApplicationContext(),"Sensor Manager Null!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(lightSensor != null){
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(proxiSensor != null){
            sensorManager.registerListener(this,proxiSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(gyroSensor != null){
            sensorManager.registerListener(this,gyroSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(accSensor != null){
            sensorManager.registerListener(this,accSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(magSensor != null){
            sensorManager.registerListener(this,magSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensorType = sensorEvent.sensor.getType();
        float[] accarr = new float[3];
        float[] magarr = new float[3];

        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                float lux = sensorEvent.values[0];
                TextView tvl = findViewById(R.id.tv_sensor_light);
                tvl.setText("Brightness is "+lux);
                break;
            case Sensor.TYPE_PROXIMITY:
                float[] values = sensorEvent.values;
                TextView tvp = findViewById(R.id.tv_sensor_proxi);
                tvp.setText("Distance is "+values[0]);
                break;
            case Sensor.TYPE_GYROSCOPE:
                float val = sensorEvent.values[0];
                TextView tvg = findViewById(R.id.tv_sensor_gyro);
                tvg.setText("Gyroscope "+val);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magarr = sensorEvent.values.clone();
                TextView tvm = findViewById(R.id.tv_sensor_mag);
                tvm.setText("Magnetic Field "+magarr[0]+" "+magarr[1]+" "+magarr[2]);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accarr = sensorEvent.values.clone();
                TextView tva = findViewById(R.id.tv_sensor_acc);
                tva.setText("Accelerometer "+accarr[0]+" "+accarr[1]+" "+accarr[2]);

                float[] rotationMatrix = new float[9];
                boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix,null,accarr,magarr);
                if(rotationOK){
                    float[] orientation = new float[3];
                    SensorManager.getOrientation(rotationMatrix,orientation);
                    float azimut = orientation[0]; //degrees of rotation about the -z axis
                    float pitch  = orientation[1]; //degrees of rotation about the x axis
                    float roll   = orientation[2]; //degrees of rotation about the y axis
                    TextView tvr = findViewById(R.id.tv_sensor_rotation);
                    tvr.setText("azimut is : "+ azimut+"\n pitch is : "+pitch+"\n roll is : "+roll);
                }

                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
