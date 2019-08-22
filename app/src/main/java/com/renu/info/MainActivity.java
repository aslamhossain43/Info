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
import java.util.Random;

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
    String menuFor;

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
        if (((int) this.temperatureInCelciuas <=5)&&((int) this.temperatureInCelciuas <=8)) {
temp5_8_Down_Degree();
        } else if (((int) this.temperatureInCelciuas <=9)&&((int) this.temperatureInCelciuas <=12)){
            temp9_12Degree();
        }
        else if (((int) this.temperatureInCelciuas <=13)&&((int) this.temperatureInCelciuas <=16)){
           temp13_16Degree();
        }
        else if (((int) this.temperatureInCelciuas <=17)&&((int) this.temperatureInCelciuas <=20)){
            temp17_20Degree();
        }
        else if (((int) this.temperatureInCelciuas <=21)&&((int) this.temperatureInCelciuas <=24)){
            temp21_24Degree();
        }
        else if (((int) this.temperatureInCelciuas <=25)&&((int) this.temperatureInCelciuas <=28)){
            temp25_28Degree();
        }
        else if (((int) this.temperatureInCelciuas <=29)&&((int) this.temperatureInCelciuas <=32)){
            temp29_32Degree();
        }
        else if (((int) this.temperatureInCelciuas <=33)&&((int) this.temperatureInCelciuas <=36)){
            temp33_36Degree();
        }
        else if (((int) this.temperatureInCelciuas <=37)&&((int) this.temperatureInCelciuas <=40)){
            temp37_40Degree();
        }
        else if (((int) this.temperatureInCelciuas <=41)&&((int) this.temperatureInCelciuas <=44)){
            temp41_44_Up_Degree();
        }
        else {
            temp41_44_Up_Degree();

        }
        //for heading
        routinTextView.setText(menuFor);
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
        this.menuFor=bundle.getString("menuFor");
        Log.d("sss", "onCreate: " + this.description + ", " + this.sunrise + ", " + this.sunset + ", " + this.weatherType + " name : " + this.name);
    }

    public void convertTemperatureIntoCelciuas() {
        this.temperatureInDouble = Double.parseDouble(this.temperature);
        this.temperatureInCelciuas = this.temperatureInDouble - 273.15;
        Log.d("ttt", "onCreate: " + (int) this.temperatureInCelciuas);

    }


    public void temp5_8_Down_Degree() {
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

       generateRandomList(breakfast,breakfastImages);

    }



    public void temp9_12Degree() {
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


        generateRandomList(breakfast,breakfastImages);

    }

    public void temp13_16Degree() {
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


        generateRandomList(breakfast,breakfastImages);

    }

    public void temp17_20Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(2);
        breakfast.remove(6);
        breakfast.remove(12);
        breakfast.remove(15);
        breakfastImages.remove(2);
        breakfastImages.remove(6);
        breakfastImages.remove(12);
        breakfastImages.remove(15);


        generateRandomList(breakfast,breakfastImages);

    }

    public void temp21_24Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(2);
        breakfast.remove(12);
        breakfast.remove(15);
        breakfastImages.remove(2);
        breakfastImages.remove(12);
        breakfastImages.remove(15);


        generateRandomList(breakfast,breakfastImages);

    }

    public void temp25_28Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(18);
        breakfastImages.remove(18);

        generateRandomList(breakfast,breakfastImages);


    }

    public void temp29_32Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(18);
        breakfastImages.remove(18);


        generateRandomList(breakfast,breakfastImages);

    }
    public void temp33_36Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(1);
        breakfast.remove(12);
        breakfast.remove(14);
        breakfast.remove(17);
        breakfast.remove(18);
        breakfastImages.remove(1);
        breakfastImages.remove(12);
        breakfastImages.remove(14);
        breakfastImages.remove(17);
        breakfastImages.remove(18);


        generateRandomList(breakfast,breakfastImages);

    }
    public void temp37_40Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(1);
        breakfast.remove(7);
        breakfast.remove(12);
        breakfast.remove(14);
        breakfast.remove(16);
        breakfast.remove(17);
        breakfast.remove(18);
        breakfastImages.remove(1);
        breakfastImages.remove(7);
        breakfastImages.remove(12);
        breakfastImages.remove(14);
        breakfastImages.remove(16);
        breakfastImages.remove(17);
        breakfastImages.remove(18);

        generateRandomList(breakfast,breakfastImages);


    }
    public void temp41_44_Up_Degree() {
        List<String> breakfast = new ArrayList<>(Arrays.asList(this.breakfast));
        List<Integer> breakfastImages = new ArrayList<>(Arrays.asList(this.breakfastImages));

        breakfast.remove(1);
        breakfast.remove(7);
        breakfast.remove(12);
        breakfast.remove(14);
        breakfast.remove(16);
        breakfast.remove(17);
        breakfast.remove(18);
        breakfastImages.remove(1);
        breakfastImages.remove(7);
        breakfastImages.remove(12);
        breakfastImages.remove(14);
        breakfastImages.remove(16);
        breakfastImages.remove(17);
        breakfastImages.remove(18);


        generateRandomList(breakfast,breakfastImages);

    }
    private void generateRandomList(List<String> breakfastInner, List<Integer> breakfastImagesInner) {
        Random rand = new Random();

        // create a temporary list for storing
        // selected element
        List<String> newListForBreakFast = new ArrayList<>();
        List<Integer> newListForBreakFastImages = new ArrayList<>();
        for (int i = 0; i <10; i++) {

            // take a raundom index between 0 to size
            // of given List
            int randomIndex = rand.nextInt(breakfastInner.size());

            // add element in temporary list
            newListForBreakFast.add(breakfastInner.get(randomIndex));
            // remove for avoid repeat
            breakfastInner.remove(randomIndex);
            newListForBreakFastImages.add(breakfastImagesInner.get(randomIndex));
            // remove for avoid repeat
            breakfastImagesInner.remove(randomIndex);
        }

        this.finalBreakfast = newListForBreakFast.toArray(new String[newListForBreakFast.size()]);

        this.finalBreakfastImages = newListForBreakFastImages.stream().mapToInt(Integer::intValue).toArray();



    }
    public void handleCustomAdapter() {
        CustomFoodAdapter customFoodAdapter = new CustomFoodAdapter(this, this.finalBreakfast, this.finalBreakfastImages);
        mainListViewId.setAdapter(customFoodAdapter);

        mainListViewId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String food_names = finalBreakfast[position];
                Toast.makeText(MainActivity.this, food_names, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
