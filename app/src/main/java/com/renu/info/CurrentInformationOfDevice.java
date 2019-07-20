package com.renu.info;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CurrentInformationOfDevice  extends AppCompatActivity{
    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
getDeviceCurrentLocation();



    }

    private boolean checkLocationPermission(){


        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},111);
        return  false;
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            getDeviceCurrentLocation();
        }
    }

    private void getDeviceCurrentLocation() {
        if (checkLocationPermission()){

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
               if (location!=null){

                   Log.d("lt", "onSuccess: "+location.getLatitude()+" "+location.getLongitude());
               }
                }
            });
        }else {
            checkLocationPermission();
        }
    }
}
