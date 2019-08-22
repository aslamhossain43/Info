package com.renu.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private boolean isNetworkOk;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private ProgressBar progressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        intiViews();
        initAll();


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();

                 startService(new Intent(SplashActivity.this, MyFoodService.class));
                /*Intent intent = new Intent(SplashActivity.this, WeatherInformation.class);
                startActivity(intent);
*/

            }
        });
        thread.start();
    }

    private void initAll() {

        isNetworkOk = Network.isNetworkAvailable(this);
        alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this);
        alertDialog = alertDialogBuilder.create();
    }


    private void intiViews() {
        progressBar = findViewById(R.id.progressBarId);


    }

    public void doWork() {
        for (progress = 10; progress <= 100; progress = progress + 10) {
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

// to minimize activity
         this.moveTaskToBack(true);
    }


}
