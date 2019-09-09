package com.renu.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WeatherDetails extends AppCompatActivity {
       private TextView weatherDetailsTextView;
       private String name;
       private String description;
       private int temp;
       private String humidity;
       private String speed;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        initView();
        getBundleData();
        weatherDetailsTextView.setText(">>Weather Information<<" + "\n\n" + "Area : " + this.name + "\n\n" + "Condition : " + this.description + "\n\n"
                + "Temperature : " + this.temp + "\u00B0C\n\n" + "Humidity : " + this.humidity + "%" + "\n\n"
                + "Wind : " + this.speed + " Km/h");


    }

    private void initView() {

        weatherDetailsTextView=findViewById(R.id.weatherDetailsTextViewId);
    }

    private void getBundleData() {
    Bundle bundle=getIntent().getExtras();
    this.name=bundle.getString("name");
    this.description=bundle.getString("description");
    this.humidity=bundle.getString("humidity");
    this.speed=bundle.getString("speed");
    this.temp=bundle.getInt("temp");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), MyBroadCastReceiver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendBroadcast(intent);
    }

}
