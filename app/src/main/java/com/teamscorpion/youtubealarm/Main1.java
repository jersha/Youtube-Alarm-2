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

public class Main1 extends AppCompatActivity {
    ConstraintLayout main_layout;
    Button ok, skip;
    EditText name_edit, keyword1_edit, keyword2_edit, keyword3_edit, keyword4_edit, keyword5_edit;
    TextView name, title, description, keyword1, keyword2, keyword3, keyword4, keyword5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        final SharedPreferences clockSettings = getSharedPreferences("MyClockPreferences", 0);
        final Intent intent = new Intent(Main1.this, Main2.class);

        main_layout = findViewById(R.id.main_layout);
        ok = findViewById(R.id.fg_ok);
        skip = findViewById(R.id.fg_skip);
        title = findViewById(R.id.fg_title);
        description = findViewById(R.id.fg_description);
        keyword1 = findViewById(R.id.fg_kw1);
        keyword2 = findViewById(R.id.fg_kw2);
        keyword1_edit = findViewById(R.id.fg_kw1_edit);
        keyword2_edit = findViewById(R.id.fg_kw2_edit);
        name = findViewById(R.id.fg_name);
        name_edit = findViewById(R.id.fg_name_edit);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

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

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip_name = name_edit.getText().toString();
                if(!ip_name.isEmpty()){
                    ip_name = " " + ip_name;
                }
                String ip_kw1 = keyword1_edit.getText().toString();
                String ip_kw2 = keyword2_edit.getText().toString();
                SharedPreferences.Editor prefEditor = clockSettings.edit();
                prefEditor.putBoolean("my_first_time", false);
                prefEditor.putString("UserName", ip_name);
                prefEditor.putString("KeyWord1", ip_kw1);
                prefEditor.putString("KeyWord2", ip_kw2);
                prefEditor.putBoolean("notify", true);
                prefEditor.putInt("Total", 4);
                prefEditor.putBoolean("Fav0", true);
                prefEditor.putBoolean("Fav1", true);
                prefEditor.putBoolean("Fav2", true);
                prefEditor.putBoolean("Fav3", true);
                prefEditor.putString("Time0", "04 : 30");
                prefEditor.putString("Time1", "05 : 30");
                prefEditor.putString("Time2", "06 : 30");
                prefEditor.putString("Time3", "07 : 30");
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
                prefEditor.putBoolean("notify", true);
                prefEditor.putInt("Total", 4);
                prefEditor.putBoolean("Fav0", true);
                prefEditor.putBoolean("Fav1", true);
                prefEditor.putBoolean("Fav2", true);
                prefEditor.putBoolean("Fav3", true);
                prefEditor.putString("Time0", "04 : 30");
                prefEditor.putString("Time1", "05 : 30");
                prefEditor.putString("Time2", "06 : 30");
                prefEditor.putString("Time3", "07 : 30");
                prefEditor.apply();
                startActivity(intent);
                finish();
            }
        });
    }
}