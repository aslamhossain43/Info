package com.renu.info;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetDataFromOpenWeather extends AppCompatActivity {
    private static final String API_KEY = "320c5059b0c17fad40e0a87704494e73";
    double latitude;
    double longitude;
    String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Network.isNetworkAvailable(this)) {
            Intent forLatLon = new Intent(GetDataFromOpenWeather.this, CurrentInformationOfDevice.class);
            startActivityForResult(forLatLon, 11);

            parseJson();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            latitude = data.getDoubleExtra("lat", 0.00);
            longitude = data.getDoubleExtra("lon", 0.00);

        }


    }

    private void parseJson() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObjectForLatLon = response.getJSONObject("coord");
                            String lat = jsonObjectForLatLon.getString("lat");
                            String lon = jsonObjectForLatLon.getString("lon");
                            JSONArray jsonArrayForWeather = response.getJSONArray("weather");
                            JSONObject jsonObjectForWeather = jsonArrayForWeather.getJSONObject(0);
                            String weatherType = jsonObjectForWeather.getString("main");
                            String description = jsonObjectForWeather.getString("description");
                            JSONObject jsonObjectForTempPress = response.getJSONObject("main");
                            String tempareture = jsonObjectForTempPress.getString("temp");
                            String pressure = jsonObjectForTempPress.getString("pressure");
                            String humidity = jsonObjectForTempPress.getString("humidity");
                            String date = response.getString("dt");
                            JSONObject jsonObjectForSys = response.getJSONObject("sys");
                            String sunrise = jsonObjectForSys.getString("sunrise");
                            String sunset = jsonObjectForSys.getString("sunset");


                            Intent intent = new Intent(GetDataFromOpenWeather.this, MyFoodService.class);


                            intent.putExtra("lat", lat);
                            intent.putExtra("lon", lon);
                            intent.putExtra("weathertype", weatherType);
                            intent.putExtra("description", description);
                            intent.putExtra("temp", tempareture);
                            intent.putExtra("pressure", pressure);
                            intent.putExtra("humidity", humidity);
                            intent.putExtra("date", date);
                            intent.putExtra("sunrise", sunrise);
                            intent.putExtra("sunset", sunset);
                            startService(intent);

                            Log.d("ll", "Lat : " + lat + ",  Lon : " + lon);
                            Log.d("weather", "weather : " + weatherType + ", " + description);
                            Log.d("main", "temp : " + tempareture + ", " + pressure + ", " + humidity);
                            Log.d("dt", "date : " + date);
                            Log.d("sys", "sunrise : " + sunrise + ", sunset : " + sunset);

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


}
