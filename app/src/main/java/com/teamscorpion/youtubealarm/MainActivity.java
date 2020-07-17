package com.teamscorpion.youtubealarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout main_layout;
    Button ok, skip;
    EditText name_edit, keyword1_edit, keyword2_edit, keyword3_edit, keyword4_edit, keyword5_edit;
    TextView name, title, description, keyword1, keyword2, keyword3, keyword4, keyword5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences clockSettings = getSharedPreferences("MyClockPreferences", 0);
        final Intent intent = new Intent(MainActivity.this, Main2.class);

        main_layout = findViewById(R.id.main_layout);
        ok = findViewById(R.id.fg_ok);
        skip = findViewById(R.id.fg_skip);
        title = findViewById(R.id.fg_title);
        description = findViewById(R.id.fg_description);
        keyword1 = findViewById(R.id.fg_kw1);
        keyword2 = findViewById(R.id.fg_kw2);
        keyword3 = findViewById(R.id.fg_kw3);
        keyword4 = findViewById(R.id.fg_kw4);
        keyword5 = findViewById(R.id.fg_kw5);
        keyword1_edit = findViewById(R.id.fg_kw1_edit);
        keyword2_edit = findViewById(R.id.fg_kw2_edit);
        keyword3_edit = findViewById(R.id.fg_kw3_edit);
        keyword4_edit = findViewById(R.id.fg_kw4_edit);
        keyword5_edit = findViewById(R.id.fg_kw5_edit);
        name = findViewById(R.id.fg_name);
        name_edit = findViewById(R.id.fg_name_edit);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        if(!clockSettings.getBoolean("my_first_time", true)){
            startActivity(intent);
            finish();
        }else{
            final Calendar[] rightNow = {Calendar.getInstance()};
            final int currentHourIn24Format = rightNow[0].get(Calendar.HOUR_OF_DAY);

            if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
                main_layout.setBackgroundResource(R.drawable.m_glass);
            }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
                main_layout.setBackgroundResource(R.drawable.a_glass);
            }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
                main_layout.setBackgroundResource(R.drawable.e_glass);
            }else {
                main_layout.setBackgroundResource(R.drawable.n_glass);
            }

            SharedPreferences.Editor prefEditor = clockSettings.edit();
            prefEditor.putInt("FavHr1", 4);
            prefEditor.putInt("FavMin1", 30);
            prefEditor.putInt("FavHr2", 5);
            prefEditor.putInt("FavMin2", 30);
            prefEditor.putInt("FavHr3", 6);
            prefEditor.putInt("FavMin3", 30);
            prefEditor.putInt("FavHr4", 7);
            prefEditor.putInt("FavMin4", 30);
            prefEditor.apply();
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip_name = name_edit.getText().toString();
                if(!ip_name.isEmpty()){
                    ip_name = " " + ip_name;
                }
                String ip_kw1 = keyword1_edit.getText().toString();
                String ip_kw2 = keyword2_edit.getText().toString();
                String ip_kw3 = keyword3_edit.getText().toString();
                String ip_kw4 = keyword4_edit.getText().toString();
                String ip_kw5 = keyword5_edit.getText().toString();
                SharedPreferences.Editor prefEditor = clockSettings.edit();
                prefEditor.putBoolean("my_first_time", false);
                prefEditor.putString("UserName", ip_name);
                prefEditor.putString("KeyWord1", ip_kw1);
                prefEditor.putString("KeyWord2", ip_kw2);
                prefEditor.putString("KeyWord3", ip_kw3);
                prefEditor.putString("KeyWord4", ip_kw4);
                prefEditor.putString("KeyWord5", ip_kw5);
                prefEditor.putBoolean("my_first_time", false);
                prefEditor.apply();
                startActivity(intent);
                finish();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor prefEditor = clockSettings.edit();
                prefEditor.putBoolean("my_first_time", false);
                prefEditor.apply();
                startActivity(intent);
                finish();
            }
        });
    }
}