package com.bitbytelab.app_227;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity {

    FusedLocationProviderClient myClient;
    TextView tvlocation, tvaddress;
    double dlatitude, dlongitude;
    Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        tvlocation = findViewById(R.id.map_location);
        tvaddress  = findViewById(R.id.map_address);

        myClient = LocationServices.getFusedLocationProviderClient(this);

        findViewById(R.id.btn_get_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLocationPermission();
            }
        });
        findViewById(R.id.btn_open_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this,MapActivity.class));
            }
        });

    }

    private void checkLocationPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
            Toast.makeText(this,"Location Permission Granted.",Toast.LENGTH_SHORT).show();
            myClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        lastLocation = location;
                        dlatitude = lastLocation.getLatitude();
                        dlongitude= lastLocation.getLongitude();

                        tvlocation.setText("Latitude : "+dlatitude+"\nLongitude : "+dlongitude);

                        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                        try{
                            List<Address> locationList = geocoder.getFromLocation(dlatitude,dlongitude,1);
                            if(locationList.size() > 0){
                                Address address = locationList.get(0);
                                tvaddress.setText(""+address.getAddressLine(0));
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                checkLocationPermission();
            }else{
                Toast.makeText(this,"Location Permission Denied.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
