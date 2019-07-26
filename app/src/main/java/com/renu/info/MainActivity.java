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
    private int[] foodImages = {R.drawable.egg, R.drawable.banana};
    private TextView routinTextViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainListViewId = findViewById(R.id.mainListViewId);
        routinTextViewId = findViewById(R.id.routinTextViewId);
        foodNames = getResources().getStringArray(R.array.food_names);
 //-------------------------------------------------------------------------------------------
 //GET FROM WeatherINformation Activity
       Bundle bundle = getIntent().getExtras();
        String sunrise=bundle.getString("sunrise");
        Log.d("sss", "onCreate: "+sunrise);
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
