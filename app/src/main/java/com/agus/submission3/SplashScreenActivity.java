package com.agus.submission3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent moveToMain = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(moveToMain);
                finish();
            }
        }, 3000);
    }
}