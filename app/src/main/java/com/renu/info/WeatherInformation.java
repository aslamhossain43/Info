package com.renu.info;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherInformation extends Activity {
    private String message = "Hello Developer ! You are granted for software development. Please check your email";
    private String title = "Title";
    private static final String CHANNEL_ID = "Message";

//---------------------------------------------------------------------------------------

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final String API_KEY = "320c5059b0c17fad40e0a87704494e73";
    private double currentLatitude;
    private double currentLongitude;
    String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + currentLatitude + "&lon=" + currentLongitude + "&appid=" + API_KEY;
    //----------------------------------------------------------------------------------------------
    //int valueLatitude;
    //int valueLongitude;
    String weatherType;
    String description;
    String temperature;
    String pressure;
    String humidity;
    String date;
    String sunrise;
    String sunset;
    String name;
    //------------------------------
    long longSunrise;
    long longAdditionalSunrise;
    long longSunset;
    long longAdditionalSunset;
    long longNoon;
    long longAdditionalNoon;

    //------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        if (Network.isNetworkAvailable(this)) {
            getDeviceCurrentLocation();

            getCurrentWeather();


        }


    }


    //---------------------------------------------------------------------------------------------
    private void getDeviceCurrentLocation() {
        if (checkLocationPermission()) {

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLatitude = location.getLatitude();
                        currentLongitude = location.getLongitude();
                    }
                }
            });
        } else {
            checkLocationPermission();
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

        if (grantResults.length > 0) {


            if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceCurrentLocation();
            }
        }
    }

    //--------------------------------------------------------------------------------------------
    private void getCurrentWeather() {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObjectForLatLon = response.getJSONObject("coord");
                            //WeatherInformation.this.valueLatitude = jsonObjectForLatLon.getInt("lat");
                            //WeatherInformation.this.valueLongitude = jsonObjectForLatLon.getInt("lon");
                            JSONArray jsonArrayForWeather = response.getJSONArray("weather");
                            JSONObject jsonObjectForWeather = jsonArrayForWeather.getJSONObject(0);
                            WeatherInformation.this.weatherType = jsonObjectForWeather.getString("main");
                            WeatherInformation.this.description = jsonObjectForWeather.getString("description");
                            JSONObject jsonObjectForTempPress = response.getJSONObject("main");
                            WeatherInformation.this.temperature = jsonObjectForTempPress.getString("temp");
                            WeatherInformation.this.pressure = jsonObjectForTempPress.getString("pressure");
                            WeatherInformation.this.humidity = jsonObjectForTempPress.getString("humidity");
                            WeatherInformation.this.date = response.getString("dt");
                            JSONObject jsonObjectForSys = response.getJSONObject("sys");
                            WeatherInformation.this.sunrise = jsonObjectForSys.getString("sunrise");
                            WeatherInformation.this.sunset = jsonObjectForSys.getString("sunset");
                            WeatherInformation.this.name = response.getString("name");
//------------------------------------------------------------------------------------------------
                            WeatherInformation.this.longSunrise = Long.parseLong(WeatherInformation.this.sunrise);
                            WeatherInformation.this.longAdditionalSunrise = WeatherInformation.this.longSunrise + 3600000;
                            WeatherInformation.this.longSunset = Long.parseLong(WeatherInformation.this.sunset);
                            WeatherInformation.this.longAdditionalSunset = WeatherInformation.this.longSunset + 3600000;
                            WeatherInformation.this.longNoon = WeatherInformation.this.longSunrise + 18000000;//5 hours
                            WeatherInformation.this.longAdditionalNoon = WeatherInformation.this.longNoon + 3600000;
                            long currentTime = System.currentTimeMillis();//long not contains it

//---------------------------------------------------------------------------------------------
                            String t = "1566761046434";
                            long testTime = Long.parseLong(t) + 60*60000;
                            long time = System.currentTimeMillis();
                            if ((time >= Long.parseLong(t)) && (time <= testTime)) {
                                breakFastNotification();
                            }
                            Log.d("ll", "onResponse: "+currentTime);
//-----------------------------------------------------------------------------------------------
                            /*if ((currentTime >= WeatherInformation.this.longSunrise) && (currentTime <= WeatherInformation.this.longAdditionalSunrise)) {
                                breakFastNotification();
                            }


                            if ((currentTime >= WeatherInformation.this.longNoon) && (currentTime <= WeatherInformation.this.longAdditionalNoon)) {
                                lunchNotification();
                            }

                            if ((currentTime >= WeatherInformation.this.longSunset) && (currentTime <= WeatherInformation.this.longAdditionalSunset)) {
                                dinnerNotification();
                            }*/
//----------------------------------------------------------------------------------------------

                            Log.d("l", "Lat : " + WeatherInformation.this.currentLatitude + ",  Lon : " + WeatherInformation.this.currentLongitude);
                            Log.d("weather", "weather type : " + WeatherInformation.this.weatherType + ", " + WeatherInformation.this.description);
                            Log.d("main", "temp : " + WeatherInformation.this.temperature + ", " + WeatherInformation.this.pressure + ", " + WeatherInformation.this.humidity);
                            Log.d("dt", "date : " + WeatherInformation.this.date);
                            Log.d("sys", "sunrise : " + WeatherInformation.this.sunrise + ", sunset : " + WeatherInformation.this.sunset);
                            Log.d("sys", "name : " + WeatherInformation.this.name);

                            Log.d("t", "onResponse: " + System.currentTimeMillis());

//---------------------------------------------------------------------------------------------


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }

    //------------------------------------------------------------------------------------------------

    private void breakFastNotification() {
        createNotificationChannel();

        Intent cintent = new Intent(WeatherInformation.this, MainActivity.class);
        cintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //sending data to another activity
        Bundle bundle = new Bundle();
        bundle.putString("weatherType", WeatherInformation.this.weatherType);
        bundle.putString("description", WeatherInformation.this.description);
        bundle.putString("temperature", WeatherInformation.this.temperature);
        bundle.putString("pressure", WeatherInformation.this.pressure);
        bundle.putString("humidity", WeatherInformation.this.humidity);
        bundle.putString("date", WeatherInformation.this.date);
        bundle.putString("sunrise", WeatherInformation.this.sunrise);
        bundle.putString("sunset", WeatherInformation.this.sunset);
        bundle.putString("name", WeatherInformation.this.name);
        bundle.putString("menuFor", "Breakfast");

        cintent.putExtras(bundle);
//--------------------------------------------

        PendingIntent pendingIntent = PendingIntent.getActivity(WeatherInformation.this, 0, cintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(WeatherInformation.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_splash_info)
                .setContentTitle("Breakfast")
                .setContentText("You Can Choose Your Breakfast Menu")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mBuilder.setSound(uri);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(WeatherInformation.this);
        int notificationId = (int) (System.currentTimeMillis() / 4);
        notificationManager.notify(notificationId, mBuilder.build());


    }

    //------------------------------------------------------------------------------------------------
    private void lunchNotification() {
        createNotificationChannel();

        Intent cintent = new Intent(WeatherInformation.this, MainActivity.class);
        cintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //sending data to another activity
        Bundle bundle = new Bundle();
        bundle.putString("weatherType", WeatherInformation.this.weatherType);
        bundle.putString("description", WeatherInformation.this.description);
        bundle.putString("temperature", WeatherInformation.this.temperature);
        bundle.putString("pressure", WeatherInformation.this.pressure);
        bundle.putString("humidity", WeatherInformation.this.humidity);
        bundle.putString("date", WeatherInformation.this.date);
        bundle.putString("sunrise", WeatherInformation.this.sunrise);
        bundle.putString("sunset", WeatherInformation.this.sunset);
        bundle.putString("name", WeatherInformation.this.name);
        bundle.putString("menuFor", "Lunch");
        cintent.putExtras(bundle);
//--------------------------------------------


        PendingIntent pendingIntent = PendingIntent.getActivity(WeatherInformation.this, 0, cintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(WeatherInformation.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_splash_info)
                .setContentTitle("Lunch")
                .setContentText("You Can Choose Your Lunch Menu")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mBuilder.setSound(uri);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(WeatherInformation.this);
        int notificationId = (int) (System.currentTimeMillis() / 4);
        notificationManager.notify(notificationId, mBuilder.build());


    }


    //------------------------------------------------------------------------------------------------
    private void dinnerNotification() {
        createNotificationChannel();

        Intent cintent = new Intent(WeatherInformation.this, MainActivity.class);
        cintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //sending data to another activity
        Bundle bundle = new Bundle();
        bundle.putString("weatherType", WeatherInformation.this.weatherType);
        bundle.putString("description", WeatherInformation.this.description);
        bundle.putString("temperature", WeatherInformation.this.temperature);
        bundle.putString("pressure", WeatherInformation.this.pressure);
        bundle.putString("humidity", WeatherInformation.this.humidity);
        bundle.putString("date", WeatherInformation.this.date);
        bundle.putString("sunrise", WeatherInformation.this.sunrise);
        bundle.putString("sunset", WeatherInformation.this.sunset);
        bundle.putString("name", WeatherInformation.this.name);
        bundle.putString("menuFor", "Dinner");
        cintent.putExtras(bundle);
//--------------------------------------------


        PendingIntent pendingIntent = PendingIntent.getActivity(WeatherInformation.this, 0, cintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(WeatherInformation.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_splash_info)
                .setContentTitle("Dinner")
                .setContentText("You Can Choose Your Dinner Menu")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mBuilder.setSound(uri);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(WeatherInformation.this);
        int notificationId = (int) (System.currentTimeMillis() / 4);
        notificationManager.notify(notificationId, mBuilder.build());


    }

//-------------------------------------------------------------------------------------

    private void createNotificationChannel() {

        CharSequence channelName = CHANNEL_ID;
        String channelDesc = "channelDesc";
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDesc);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            NotificationChannel currChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (currChannel == null)
                notificationManager.createNotificationChannel(channel);
        }

    }


}
