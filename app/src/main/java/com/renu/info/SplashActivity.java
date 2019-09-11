package com.renu.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private View splashFullScreen;
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


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();


//----------------------------------------------------------
                Intent intent = new Intent(getApplicationContext(), MyBroadCastReceiver.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendBroadcast(intent);


            }
        });
        thread.start();

    }


    private void intiViews() {
        progressBar = findViewById(R.id.progressBarId);
        splashFullScreen = findViewById(R.id.splashFullScreenId);

    }

    public void doWork() {
        for (progress = 1; progress <= 100; progress = progress + 1) {
            try {
                Thread.sleep(50);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

// to minimize activity
        this.moveTaskToBack(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        splashFullScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP) {
                    // to minimize activity
                    SplashActivity.this.moveTaskToBack(true);
                }
                if (action == MotionEvent.ACTION_DOWN) {
                    // to minimize activity
                    SplashActivity.this.moveTaskToBack(true);
                }
                if (action == MotionEvent.ACTION_MOVE) {
                    // to minimize activity
                    SplashActivity.this.moveTaskToBack(true);
                }
                return true;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SplashActivity.this.progressBar.setVisibility(View.INVISIBLE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), MyBroadCastReceiver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendBroadcast(intent);
    }
}
