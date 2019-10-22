package com.bitbytelab.app_227;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    FusedLocationProviderClient myClient;
    double dlatitude, dlongitude;
    Location lastLocation;

    SupportMapFragment supportMapFragment;
    GoogleMap gMap;
    boolean flag = false;
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        edtSearch = findViewById(R.id.edt_search_location);

        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mymap_fragment);

        myClient = LocationServices.getFusedLocationProviderClient(this);

        checkLocationPermission();
    }

    private void checkLocationPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
            flag = true;
            Toast.makeText(this,"Location Permission Granted.",Toast.LENGTH_SHORT).show();
            myClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        lastLocation = location;
                        dlatitude = lastLocation.getLatitude();
                        dlongitude= lastLocation.getLongitude();

                        initMap();
                    }
                }
            });
        }
    }

    private void initMap() {

        Toast.makeText(this,"Initialize Map", Toast.LENGTH_SHORT ).show();

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                if(flag){
                    LatLng mylatlng = new LatLng(dlatitude,dlongitude);
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(mylatlng));
                    gMap.setMyLocationEnabled(true);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(mylatlng);
                    markerOptions.title("You are here");
                    gMap.addMarker(markerOptions);

                    gMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                        @Override
                        public void onMyLocationChange(Location location) {
                            gMap.clear();
                            LatLng mylatlng = new LatLng(location.getLatitude(),location.getLongitude());
                            gMap.moveCamera(CameraUpdateFactory.newLatLng(mylatlng));

                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(mylatlng);
                            markerOptions.title("Updated Location");
                            gMap.addMarker(markerOptions);
                        }
                    });
                }
            }
        });
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

    public void doSearch(View view){
        String search = edtSearch.getText().toString().trim();
        Geocoder geocoder = new Geocoder(MapActivity.this);
        try{
            List<Address> locationList = geocoder.getFromLocationName(search,1);
            if(locationList.size() > 0){
                Address address = locationList.get(0);

                LatLng mylatlng = new LatLng(address.getLatitude(),address.getLongitude());
                gMap.moveCamera(CameraUpdateFactory.newLatLng(mylatlng));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(mylatlng);
                markerOptions.title("Updated Location");
                gMap.addMarker(markerOptions);

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
