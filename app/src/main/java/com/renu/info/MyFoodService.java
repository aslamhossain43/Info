package com.renu.info;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MyFoodService extends Service {
    private String message = "Hello Developer ! You are granted for software development. Please check your email";
    private static final String CHANNEL_ID = "Message";
    //DATA
    String latitude;
    String longitude;
    String weatherType;
    String description;
    String temparature;
    String pressure;
    String humidity;
    String date;
    String sunrise;
    String sunset;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getWeatherInfo(intent);


        createNotificationChannel();

        Intent cintent = new Intent(MyFoodService.this, MainActivity.class);
        cintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(MyFoodService.this, 0, cintent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyFoodService.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_splash_info)
                .setContentTitle(weatherType)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mBuilder.setSound(uri);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MyFoodService.this);
        int notificationId = (int) (System.currentTimeMillis() / 4);
        notificationManager.notify(notificationId, mBuilder.build());

        return START_STICKY;


    }


    private void getWeatherInfo(Intent intent) {
        if (intent != null) {
            latitude = intent.getStringExtra("lat");
            longitude = intent.getStringExtra("lon");
            weatherType = intent.getStringExtra("weathertype");
            description = intent.getStringExtra("description");
            temparature = intent.getStringExtra("temp");
            pressure = intent.getStringExtra("pressure");
            humidity = intent.getStringExtra("humidity");
            date = intent.getStringExtra("date");
            sunrise = intent.getStringExtra("sunrise");
            sunset = intent.getStringExtra("sunset");
            Log.d("latitude", "getWeatherInfo: " + latitude + ", " + longitude + ", " + weatherType + ", " + description + ", " + temparature + ", " + pressure + ", " + humidity
                    + ", " + date + ", " + sunrise + ", " + sunset);
        }
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
