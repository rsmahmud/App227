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
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    FusedLocationProviderClient myClient;
    double dlatitude, dlongitude;
    Location lastLocation;

    SupportMapFragment supportMapFragment;
    GoogleMap gMap;
    boolean flag = false;
    EditText edtSearch;

    private static final int M_MAXENTRIES = 10;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddress;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;
    ListView lstPlaces;
    PlacesClient placesClient;
    String KEY = "AIzaSyAIwnbfnuDY9XMHOYNtDUlTsGS4cZ0bN1g";
    AutocompleteSupportFragment autocompleteSupportFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        edtSearch = findViewById(R.id.edt_search_location);

        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mymap_fragment);

        myClient = LocationServices.getFusedLocationProviderClient(this);

        lstPlaces = findViewById(R.id.places_list_view);

        checkLocationPermission();
        if(!Places.isInitialized())
            Places.initialize(getApplicationContext(),KEY);
        placesClient = Places.createClient(this);

        autocompleteSupportFragment =
                (AutocompleteSupportFragment)
                        getSupportFragmentManager().
                                findFragmentById(R.id.autocomplete_fragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.NAME));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                if(place != null){
                    LatLng latLng = place.getLatLng();
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    gMap.setMyLocationEnabled(true);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Your selected place is this");
                    Toast.makeText(MapActivity.this,place.getName(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

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

    public void getCurrentPlace(View view){
        Toast.makeText(this,"in onClick",Toast.LENGTH_SHORT).show();

        List<Place.Field> placeField = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeField).build();

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);

        placeResponse.addOnCompleteListener(this, new OnCompleteListener<FindCurrentPlaceResponse>() {
            @Override
            public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {

            }
        });
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
