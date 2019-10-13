package com.example.bloodbankapp.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bloodbankapp.R;
import com.example.bloodbankapp.helper.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private GPSTracker gps;
    public static double latitude, longitude, latitudePoint, longitudePoint;
    private Button directions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        directions = findViewById(R.id.directions);
        Clicked();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gps = new GPSTracker(this, this);

        // Check if GPS enabled
        if (gps.getIsGPSTrackingEnabled()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

        } else {
            gps.showSettingsAlert();
        }

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.setMyLocationEnabled(true);
        latitudePoint = latitude;
        longitudePoint = longitude;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("MyLocation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("MyLocation"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                latitude = latLng.latitude;
                longitude = latLng.longitude;

            }
        });


    }

    public void Clicked() {
        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.addPolyline(new PolylineOptions().add(new LatLng(latitude, longitude)
                        , new LatLng(latitudePoint, longitudePoint)).width(15).color(getResources().getColor(R.color.blue)).geodesic(true));
            }
        });

    }
}
