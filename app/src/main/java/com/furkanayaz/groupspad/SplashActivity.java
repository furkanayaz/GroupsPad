package com.furkanayaz.groupspad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageViewSplashScreen;
    private ProgressBar progressBarSplashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        imageViewSplashScreen = findViewById(R.id.imageViewSplashScreen);
        progressBarSplashScreen = findViewById(R.id.progressBarSplashScreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //AppCompatDelegate arka planda gereksizce aynı sayfayı açıyordu. Onu engelledim.
                startActivity(intent);
                finish();
            }
        },3000);

    }
}