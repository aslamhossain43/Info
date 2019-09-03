package com.renu.info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodsDetails extends AppCompatActivity {
private WebView foodDetailsWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_details);
        initView();
        getBundleDataFromWeatherInfoAndLoadWeb();


    }

    private void initView() {
        foodDetailsWebview=findViewById(R.id.foodDetailsWebViewId);
    }

    public void getBundleDataFromWeatherInfoAndLoadWeb() {
        Bundle bundle = getIntent().getExtras();
        int foodPosition=bundle.getInt("foodPosition");
        String[]foodDetails=bundle.getStringArray("foodDetails");


        WebSettings webSettings=foodDetailsWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        foodDetailsWebview.setWebViewClient(new WebViewClient());
        foodDetailsWebview.loadUrl(foodDetails[foodPosition]);

    }

    @Override
    public void onBackPressed() {
        if (foodDetailsWebview.canGoBack()){
            foodDetailsWebview.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
