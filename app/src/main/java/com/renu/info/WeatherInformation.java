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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class WeatherInformation extends Activity {
    private static final String CHANNEL_ID = "Message";

//---------------------------------------------------------------------------------------

    private static final String API_KEY = "320c5059b0c17fad40e0a87704494e73";
    private double currentLatitude;
    private double currentLongitude;
    String url;
    //----------------------------------------------------------------------------------------------

    String weatherType;
    String description;
    String temperature;
    String pressure;
    String humidity;
    String date;
    String sunrise;
    String sunset;
    String name;
    String windSpeed;
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

        // to minimize activity
        this.moveTaskToBack(true);


        if (Network.isNetworkAvailable(this)) {
            getDeviceCurrentLocation();


        } else {
            offLineNotification();
        }


    }

    private void offLineNotification() {

        createNotificationChannel();

        Intent cintent = new Intent(WeatherInformation.this, MainActivity.class);
        cintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(WeatherInformation.this, 0, cintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(WeatherInformation.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.noti_icon9696)
                .setContentTitle("Your Foods Menu")
                .setContentText("You Can Choose Your Foods Menu")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mBuilder.setSound(uri);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(WeatherInformation.this);
        int notificationId = (int) (System.currentTimeMillis() / 4);
        notificationManager.notify(notificationId, mBuilder.build());


    }

    @Override
    protected void onStart() {
        super.onStart();
        // to minimize activity
        this.moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // to minimize activity
        this.moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), MyBroadCastReceiver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendBroadcast(intent);
    }

    //---------------------------------------------------------------------------------------------
    private void getDeviceCurrentLocation() {
        if (checkLocationPermission()) {

            LocationServices.getFusedLocationProviderClient(this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location == null) {
                        return;
                    }
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                    getCurrentWeather(currentLatitude, currentLongitude);


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

        if (grantResults.length >= 0) {


            if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceCurrentLocation();
            }
        }
    }

    //--------------------------------------------------------------------------------------------
    private void getCurrentWeather(double currentLatitude, double currentLongitude) {

        url = "https://api.openweathermap.org/data/2.5/weather?lat=" + currentLatitude + "&lon=" + currentLongitude + "&appid=" + API_KEY;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObjectForLatLon = response.getJSONObject("coord");
                            JSONArray jsonArrayForWeather = response.getJSONArray("weather");
                            JSONObject jsonObjectForWeather = jsonArrayForWeather.getJSONObject(0);
                            weatherType = jsonObjectForWeather.getString("main");
                            description = jsonObjectForWeather.getString("description");
                            JSONObject jsonObjectForTempPress = response.getJSONObject("main");
                            temperature = jsonObjectForTempPress.getString("temp");
                            pressure = jsonObjectForTempPress.getString("pressure");
                            JSONObject jsonObjectForWind = response.getJSONObject("wind");
                            windSpeed = jsonObjectForWind.getString("speed");
                            humidity = jsonObjectForTempPress.getString("humidity");
                            date = response.getString("dt");
                            JSONObject jsonObjectForSys = response.getJSONObject("sys");
                            sunrise = jsonObjectForSys.getString("sunrise");
                            sunset = jsonObjectForSys.getString("sunset");
                            name = response.getString("name");
//------------------------------------------------------------------------------------------------
                            longSunrise = Long.parseLong(sunrise) * 1000;
                            longAdditionalSunrise = longSunrise + 3600000;//add one hour
                            longSunset = Long.parseLong(sunset) * 1000;
                            longAdditionalSunset = longSunset + 3600000;//add one hour
                            longNoon = longSunrise + 18000000;//add 5 hours
                            longAdditionalNoon = longNoon + 3600000;//add one hour

//---------------------------------------------------------------------------------------------
                            Date currentDate = new Date(System.currentTimeMillis());
                            Date sunriseDate = new Date(longSunrise);
                            Date noonDate = new Date(longNoon);
                            Date sunsetDate = new Date(longSunset);

                            Date additionalSunriseDate = new Date(longAdditionalSunrise);
                            Date additionalNoonDate = new Date(longAdditionalNoon);
                            Date additionalSunsetDate = new Date(longAdditionalSunset);
//---------------------------------------------------------------------------------------------
                            //breakFastNotification();
//-----------------------------------------------------------------------------------------------
                            if ((currentDate.after(sunriseDate) || currentDate.equals(sunriseDate))
                                    && (currentDate.before(additionalSunriseDate) || currentDate.equals(additionalSunriseDate))) {
                                breakFastNotification();
                            }


                            if ((currentDate.after(noonDate) || currentDate.equals(noonDate))
                                    && (currentDate.before(additionalNoonDate) || currentDate.equals(additionalNoonDate))) {
                                lunchNotification();
                            }

                            if ((currentDate.after(sunsetDate) || currentDate.equals(sunsetDate))
                                    && (currentDate.before(additionalSunsetDate) || currentDate.equals(additionalSunsetDate))) {
                                dinnerNotification();
                            }
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
        bundle.putString("speed", WeatherInformation.this.windSpeed);
        bundle.putString("menuFor", "Breakfast Menu");

        cintent.putExtras(bundle);
//--------------------------------------------

        PendingIntent pendingIntent = PendingIntent.getActivity(WeatherInformation.this, 0, cintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(WeatherInformation.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.noti_icon9696)
                .setContentTitle("Breakfast Menu")
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
        bundle.putString("speed", WeatherInformation.this.windSpeed);
        bundle.putString("menuFor", "Lunch Menu");
        cintent.putExtras(bundle);
//--------------------------------------------


        PendingIntent pendingIntent = PendingIntent.getActivity(WeatherInformation.this, 0, cintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(WeatherInformation.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.noti_icon9696)
                .setContentTitle("Lunch Menu")
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
        bundle.putString("speed", WeatherInformation.this.windSpeed);
        bundle.putString("menuFor", "Dinner Menu");
        cintent.putExtras(bundle);
//--------------------------------------------


        PendingIntent pendingIntent = PendingIntent.getActivity(WeatherInformation.this, 0, cintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(WeatherInformation.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.noti_icon9696)
                .setContentTitle("Dinner Menu")
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
