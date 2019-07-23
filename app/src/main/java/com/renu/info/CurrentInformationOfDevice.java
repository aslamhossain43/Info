package com.renu.info;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CurrentInformationOfDevice extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                } else {
                    for (Location location : locationResult.getLocations()) {
                        Intent forLatLon = new Intent(CurrentInformationOfDevice.this, GetDataFromOpenWeather.class);
                        forLatLon.putExtra("lat", location.getLatitude());
                        forLatLon.putExtra("lon", location.getLongitude());
                        setResult(11, forLatLon);
                        //finish();
                        Log.d("ltlt", "Latitude : " + location.getLatitude() + " ,Longitude : " + location.getLongitude());
                    }
                }
            }
        };

        getDeviceCurrentLocation();
        getLocationUpdate();


    }

    private void getLocationUpdate() {
        if (checkLocationPermission()) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    private boolean checkLocationPermission() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
            return false;
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceCurrentLocation();
        }
    }

    private void getDeviceCurrentLocation() {
        if (checkLocationPermission()) {

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        Log.d("lt", "onSuccess: " + location.getLatitude() + " " + location.getLongitude());
                    }
                }
            });
        } else {
            checkLocationPermission();
        }
    }
}
