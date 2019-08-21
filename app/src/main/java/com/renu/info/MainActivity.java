package com.renu.info;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mainListViewId;
    private String[] breakfast;
    private String[] finalBreakfast;
    private Integer[] breakfastImages = {R.drawable.egg, R.drawable.banana, R.drawable.greek_yogurt
            , R.drawable.coffee, R.drawable.oats, R.drawable.chia_seeds, R.drawable.berries
            , R.drawable.nuts, R.drawable.green_tea, R.drawable.cooked_chicken, R.drawable.cooked_fish
            , R.drawable.cooked_meat, R.drawable.cottage_cheese, R.drawable.flax_seeds, R.drawable.paratha
            , R.drawable.kachchi_biryani, R.drawable.chicken_pilaf, R.drawable.grilled_chicken, R.drawable.sheek_kabab
            , R.drawable.mixed_vegetables, R.drawable.shrimp_with_vegetable, R.drawable.milk};
    private int[] finalBreakfastImages;
    private TextView routinTextView;
    //----------------------------------------------------------------------------------------------
    String weatherType;
    String description;
    String temperature;
    double temperatureInDouble;
    double temperatureInCelciuas;
    String pressure;
    String humidity;
    String date;
    String sunrise;
    String sunset;
    String name;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialization view properties
        initView();


        //Get from WeatherINformation Activity
        getBundleDataFromWeatherInfo();
        //Convert temp into Celciuas
        convertTemperatureIntoCelciuas();
        // Data adding by condition
        if ((int) this.temperatureInCelciuas < 25) {

            breakFastDataUnder25Degree();
        } else if (((int) this.temperatureInCelciuas >= 25) && ((int) this.temperatureInCelciuas <= 30)) {

            breakFastDataBetween25To30Degree();
        } else {

            breakFastDataUpTo30Degree();
        }
        //Handle Custom adapter
        handleCustomAdapter();


    }

    public void initView() {

        mainListViewId = findViewById(R.id.mainListViewId);
        routinTextView = findViewById(R.id.routinTextViewId);
        breakfast = getResources().getStringArray(R.array.foods);
    }

    public void getBundleDataFromWeatherInfo() {
        Bundle bundle = getIntent().getExtras();
        this.weatherType = bundle.getString("weatherType");
        this.description = bundle.getString("description");
        this.temperature = bundle.getString("temperature");
        this.pressure = bundle.getString("pressure");
        this.humidity = bundle.getString("humidity");
        this.date = bundle.getString("date");
        this.sunrise = bundle.getString("sunrise");
        this.sunset = bundle.getString("sunset");
        this.name = bundle.getString("name");
        Log.d("sss", "onCreate: " + this.description + ", " + this.sunrise + ", " + this.sunset + ", " + this.weatherType + " name : " + this.name);
    }

    public void convertTemperatureIntoCelciuas() {
        this.temperatureInDouble = Double.parseDouble(this.temperature);
        this.temperatureInCelciuas = this.temperatureInDouble - 273.15;
        Log.d("ttt", "onCreate: " + (int) this.temperatureInCelciuas);

    }


    public void breakFastData5_8Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(1);
        breakfast.remove(2);
        breakfast.remove(4);
        breakfast.remove(5);
        breakfast.remove(6);
        breakfast.remove(12);
        breakfast.remove(13);
        breakfast.remove(15);
        breakfastImages.remove(1);
        breakfastImages.remove(2);
        breakfastImages.remove(4);
        breakfastImages.remove(5);
        breakfastImages.remove(6);
        breakfastImages.remove(12);
        breakfastImages.remove(13);
        breakfastImages.remove(15);

        this.finalBreakfast = breakfast.toArray(new String[breakfast.size()]);

        this.finalBreakfastImages = breakfastImages.stream().mapToInt(Integer::intValue).toArray();


    }

    public void breakFastData9_11Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(1);
        breakfast.remove(2);
        breakfast.remove(4);
        breakfast.remove(5);
        breakfast.remove(6);
        breakfast.remove(12);
        breakfast.remove(13);
        breakfast.remove(15);
        breakfastImages.remove(1);
        breakfastImages.remove(2);
        breakfastImages.remove(4);
        breakfastImages.remove(5);
        breakfastImages.remove(6);
        breakfastImages.remove(12);
        breakfastImages.remove(13);
        breakfastImages.remove(15);

        this.finalBreakfast = breakfast.toArray(new String[breakfast.size()]);

        this.finalBreakfastImages = breakfastImages.stream().mapToInt(Integer::intValue).toArray();


    }



    public void breakFastDataUnder25Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(1);
        breakfast.remove(2);
        breakfast.remove(6);
        breakfastImages.remove(1);
        breakfastImages.remove(2);
        breakfastImages.remove(6);

        this.finalBreakfast = breakfast.toArray(new String[breakfast.size()]);

        this.finalBreakfastImages = breakfastImages.stream().mapToInt(Integer::intValue).toArray();


    }

    public void breakFastDataBetween25To30Degree() {

        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));
        this.finalBreakfast = breakfast.toArray(new String[breakfast.size()]);

        this.finalBreakfastImages = breakfastImages.stream().mapToInt(Integer::intValue).toArray();


    }

    public void breakFastDataUpTo30Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(0);

        breakfastImages.remove(0);


        this.finalBreakfast = breakfast.toArray(new String[breakfast.size()]);

        this.finalBreakfastImages = breakfastImages.stream().mapToInt(Integer::intValue).toArray();


    }

    public void handleCustomAdapter() {
        CustomFoodAdapter customFoodAdapter = new CustomFoodAdapter(this, this.finalBreakfast, this.finalBreakfastImages);
        mainListViewId.setAdapter(customFoodAdapter);

        mainListViewId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String food_names = breakfast[position];
                Toast.makeText(MainActivity.this, food_names, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
