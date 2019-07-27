package com.renu.info;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView mainListViewId;
    private String[] foodNames;
    private String[] finalFoodNames;
    private int[] foodImages = {R.drawable.egg, R.drawable.banana};
    private TextView routinTextView;
//----------------------------------------------------------------------------------------------
String weatherType;
    String description;
    String temperature;
    String pressure;
    String humidity;
    String date;
    String sunrise;
    String sunset;
    //---------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainListViewId = findViewById(R.id.mainListViewId);
        routinTextView = findViewById(R.id.routinTextViewId);
        foodNames = getResources().getStringArray(R.array.food_names);

        //-------------------------------------------------------------------------------------------
 //GET FROM WeatherINformation Activity
       Bundle bundle = getIntent().getExtras();
        this.weatherType=bundle.getString("weatherType");
        this.description=bundle.getString("description");
        this.temperature=bundle.getString("temperature");
        this.pressure=bundle.getString("pressure");
        this.humidity=bundle.getString("humidity");
        this.date=bundle.getString("date");
        this.sunrise=bundle.getString("sunrise");
        this.sunset=bundle.getString("sunset");
        Log.d("sss", "onCreate: "+this.description+", "+this.sunrise+", "+this.sunset+", "+this.weatherType);
   //-----------------------------------------------------------------------------------------------
       // Data adding by condition






 //--------------------------------------------------------------------------------------------
        CustomFoodAdapter customFoodAdapter = new CustomFoodAdapter(this, foodNames, foodImages);
        mainListViewId.setAdapter(customFoodAdapter);

        mainListViewId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String food_names = foodNames[position];
                Toast.makeText(MainActivity.this, food_names, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
