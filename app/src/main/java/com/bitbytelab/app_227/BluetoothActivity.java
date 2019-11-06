package com.bitbytelab.app_227;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class BluetoothActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    ConnectivityManager conn;
    static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);

        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver1, filter1);

        IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver2, filter2);

        imageView = findViewById(R.id.iv_bt_img);
        findViewById(R.id.btn_turn_on_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOnBluetooth();
            }
        });
        findViewById(R.id.btn_turn_off_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnOffBluetooth();
            }
        });
        findViewById(R.id.btn_discover_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discoverBTDevices();
            }
        });
        findViewById(R.id.btn_make_discoverable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDiscoverable();
            }
        });
        findViewById(R.id.btn_paired_devices).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPairedDevices();
            }
        });
        findViewById(R.id.btn_network_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkStatus();
            }
        });
    }

    private void networkStatus() {
        String imgpath = "https://i.imgur.com/NOuamqv.jpg";
        String txtpath = "https://dropbox.com/";

        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                Toast.makeText(BluetoothActivity.this,"WI-FI", Toast.LENGTH_SHORT).show();
            }
            else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(BluetoothActivity.this,"Mobile Data", Toast.LENGTH_SHORT).show();
                new MyImageTask().execute(imgpath);
            }
        }
    }

    public void turnOnBluetooth(){
        if(bluetoothAdapter == null){
            Toast.makeText(BluetoothActivity.this,"Device doesn't support bluetooth",Toast.LENGTH_SHORT).show();
        }else{
            if(!bluetoothAdapter.isEnabled()){
                Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(i,1);
            }
            if(bluetoothAdapter.isEnabled()){
                Toast.makeText(BluetoothActivity.this,"Bluetooth is enabled",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void turnOffBluetooth(){
        if(bluetoothAdapter != null){
            bluetoothAdapter.disable();
            Toast.makeText(BluetoothActivity.this,"Bluetooth Turned Off",Toast.LENGTH_SHORT).show();
        }
    }
    public void discoverBTDevices(){
        if(bluetoothAdapter != null){
            bluetoothAdapter.startDiscovery();
            Toast.makeText(BluetoothActivity.this,"Start Discovery "+bluetoothAdapter.startDiscovery(),Toast.LENGTH_SHORT).show();
        }
    }
    public void makeDiscoverable(){
        if(bluetoothAdapter != null){
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
            startActivityForResult(discoverableIntent,2);
        }else{
            Toast.makeText(BluetoothActivity.this,"make discoverable bt adapter null!",Toast.LENGTH_SHORT).show();
        }
    }

    public void getPairedDevices(){
        if(bluetoothAdapter != null){
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if(pairedDevices.size() > 0 ){
                for(BluetoothDevice device : pairedDevices){
                    String deviceName = device.getName();
                    String deviceHWad = device.getAddress();

                    Toast.makeText(BluetoothActivity.this,"Paired Device : "+deviceName,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Toast.makeText(BluetoothActivity.this,"Bluetooth Turned On",Toast.LENGTH_SHORT).show();
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(BluetoothActivity.this,"Bluetooth turning on failed!",Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == 3){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver1);
        unregisterReceiver(mReceiver2);
        bluetoothAdapter.cancelDiscovery();
    }

    private final BroadcastReceiver mReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(BluetoothActivity.this,"Inside on Receive of mReceiver2",Toast.LENGTH_SHORT).show();
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHWAddress = device.getAddress();
                Toast.makeText(BluetoothActivity.this,deviceName,Toast.LENGTH_SHORT).show();
            }
        }
    };
    private final BroadcastReceiver mReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Toast.makeText(context, "Inside on Receive of mReceiver1",Toast.LENGTH_SHORT).show();

            if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state){
                    case BluetoothAdapter.STATE_OFF:
                        Toast.makeText(BluetoothActivity.this,"Bluetooth Off",Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Toast.makeText(BluetoothActivity.this,"Bluetooth Turning Off",Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Toast.makeText(BluetoothActivity.this,"Bluetooth Turning On",Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Toast.makeText(BluetoothActivity.this,"Bluetooth ON",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    class MyImageTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            return downloadImage(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null){
                BluetoothActivity.imageView.setImageBitmap(bitmap);
                BluetoothActivity.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }

        private Bitmap downloadImage(String path) {
            Bitmap bitmap = null;
            try{
                URL url = new URL(path);

                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setReadTimeout(5000);
                httpsURLConnection.setConnectTimeout(5000);
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.connect();

                int code = httpsURLConnection.getResponseCode();
                if(code == HttpURLConnection.HTTP_OK){
                    InputStream stream = httpsURLConnection.getInputStream();
                    if(stream != null){
                        bitmap = BitmapFactory.decodeStream(stream);
                    }
                }
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
