package com.teamscorpion.youtubealarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences clockSettings = getSharedPreferences("MyClockPreferences", 0);
        final Intent intent1 = new Intent(MainActivity.this, Main1.class);
        final Intent intent2 = new Intent(MainActivity.this, Main2.class);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        if(clockSettings.getBoolean("my_first_time", true)){
            startActivity(intent1);
            finish();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent2);
                    finish();
                }
            },SPLASH_SCREEN);
        }
    }
}