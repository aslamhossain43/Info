package com.renu.info;

import android.content.Context;
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
    private static String api_keys;
    String url = "https://api.openweathermap.org/data/2.5/weather?lat=24&lon=88&appid=320c5059b0c17fad40e0a87704494e73";
     static String latitude;
    static String longitude;
    private static GetDataFromOpenWeather getDataFromOpenWeather;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromOpenWeather=this;

        if (Network.isNetworkAvailable(this)) {


            parseJson();
        }

    }

    public static GetDataFromOpenWeather getInstance(){
        return getDataFromOpenWeather;
    }

    private void parseJson() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                   JSONObject jsonObject=response.getJSONObject("coord");
                    latitude=jsonObject.getString("lat");
                    longitude=jsonObject.getString("lon");
                    Intent intent=new Intent(GetDataFromOpenWeather.this,SplashActivity.class);
                    intent.putExtra("lat",latitude);
                    intent.putExtra("lon",longitude);
                    setResult(1,intent);
                    finish();
                    Log.d("val", "Lat : " + latitude + ",  Lon : " + longitude);

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
