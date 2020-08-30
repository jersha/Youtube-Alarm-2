package com.teamscorpion.youtubealarm;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class Settings extends Fragment {
    ConstraintLayout main_layout;
    IntentFilter s_intentFilter;
    ImageView iv_dividor1, iv_dividor2, iv_dividor3;
    Bitmap bmp_dividor_m, bmp_dividor_a, bmp_dividor_e, bmp_dividor_n;
    String Name, kw1, kw2, kw3, kw4, kw5;
    EditText etxt_name, etxt_kw1, etxt_kw2, etxt_kw3, etxt_kw4, etxt_kw5;
    Button btn_ringtone;
    Uri audio;
    static final int SELECT_AUDIO_REQUEST = 1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        etxt_name = view.findViewById(R.id.editText_Name);
        etxt_kw1 = view.findViewById(R.id.editText_kw1);
        etxt_kw2 = view.findViewById(R.id.editText_kw2);
        etxt_kw3 = view.findViewById(R.id.editText_kw3);
        etxt_kw4 = view.findViewById(R.id.editText_kw4);
        etxt_kw5 = view.findViewById(R.id.editText_kw5);
        main_layout = view.findViewById(R.id.settings_layout);
        iv_dividor1 = view.findViewById(R.id.img_dividor1);
        iv_dividor2 = view.findViewById(R.id.img_dividor2);
        iv_dividor3 = view.findViewById(R.id.img_dividor3);
        btn_ringtone = view.findViewById(R.id.btn_ringtone);

        final SharedPreferences clockSettings = getActivity().getSharedPreferences("MyClockPreferences", 0);
        Name = clockSettings.getString("UserName", "");
        kw1 = clockSettings.getString("KeyWord1", "");
        kw2 = clockSettings.getString("KeyWord2", "");
        kw3 = clockSettings.getString("KeyWord3", "");
        kw4 = clockSettings.getString("KeyWord4", "");
        kw5 = clockSettings.getString("KeyWord5", "");

        etxt_name.setText(Name);
        etxt_kw1.setText(kw1);
        etxt_kw2.setText(kw2);
        etxt_kw3.setText(kw3);
        etxt_kw4.setText(kw4);
        etxt_kw5.setText(kw5);
        btn_ringtone.setText(Html.fromHtml("Alarm ringtone<br/><small>used when Phone is not connected to internet</small>"));

        bmp_dividor_m = BitmapFactory.decodeResource(getResources(),R.drawable.m_line);
        bmp_dividor_a = BitmapFactory.decodeResource(getResources(),R.drawable.a_line);
        bmp_dividor_e = BitmapFactory.decodeResource(getResources(),R.drawable.e_line);
        bmp_dividor_n = BitmapFactory.decodeResource(getResources(),R.drawable.n_line);

        final Calendar[] rightNow = {Calendar.getInstance()};
        final int currentHourIn24Format = rightNow[0].get(Calendar.HOUR_OF_DAY);
        if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
            main_layout.setBackgroundColor(Color.parseColor("#f3989d"));
            iv_dividor1.setImageBitmap(bmp_dividor_m);
            iv_dividor2.setImageBitmap(bmp_dividor_m);
            iv_dividor3.setImageBitmap(bmp_dividor_m);
            btn_ringtone.setBackgroundResource(R.drawable.m_ripple_effect);
        }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
            main_layout.setBackgroundColor(Color.parseColor("#d63447"));
            iv_dividor1.setImageBitmap(bmp_dividor_a);
            iv_dividor2.setImageBitmap(bmp_dividor_a);
            iv_dividor3.setImageBitmap(bmp_dividor_a);
            btn_ringtone.setBackgroundResource(R.drawable.a_ripple_effect);
        }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
            main_layout.setBackgroundColor(Color.parseColor("#febc6e"));
            iv_dividor1.setImageBitmap(bmp_dividor_e);
            iv_dividor2.setImageBitmap(bmp_dividor_e);
            iv_dividor3.setImageBitmap(bmp_dividor_e);
            btn_ringtone.setBackgroundResource(R.drawable.e_ripple_effect);
        }else {
            main_layout.setBackgroundColor(Color.parseColor("#202020"));
            iv_dividor1.setImageBitmap(bmp_dividor_n);
            iv_dividor2.setImageBitmap(bmp_dividor_n);
            iv_dividor3.setImageBitmap(bmp_dividor_n);
            btn_ringtone.setBackgroundResource(R.drawable.n_ripple_effect);
        }

        s_intentFilter = new IntentFilter();
        s_intentFilter.addAction(Intent.ACTION_TIME_TICK);
        s_intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        s_intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        final BroadcastReceiver m_timeChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                assert action != null;

                if (action.equals(Intent.ACTION_TIME_TICK)) {
                    rightNow[0] = Calendar.getInstance();
                    final int currentHourIn24Format = rightNow[0].get(Calendar.HOUR_OF_DAY);

                    if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
                        main_layout.setBackgroundColor(Color.parseColor("#f3989d"));
                        iv_dividor1.setImageBitmap(bmp_dividor_m);
                        iv_dividor2.setImageBitmap(bmp_dividor_m);
                        iv_dividor3.setImageBitmap(bmp_dividor_m);
                        btn_ringtone.setBackgroundResource(R.drawable.m_ripple_effect);
                    }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
                        main_layout.setBackgroundColor(Color.parseColor("#d63447"));
                        iv_dividor1.setImageBitmap(bmp_dividor_a);
                        iv_dividor2.setImageBitmap(bmp_dividor_a);
                        iv_dividor3.setImageBitmap(bmp_dividor_a);
                        btn_ringtone.setBackgroundResource(R.drawable.a_ripple_effect);
                    }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
                        main_layout.setBackgroundColor(Color.parseColor("#febc6e"));
                        iv_dividor1.setImageBitmap(bmp_dividor_e);
                        iv_dividor2.setImageBitmap(bmp_dividor_e);
                        iv_dividor3.setImageBitmap(bmp_dividor_e);
                        btn_ringtone.setBackgroundResource(R.drawable.e_ripple_effect);
                    }else {
                        main_layout.setBackgroundColor(Color.parseColor("#202020"));
                        iv_dividor1.setImageBitmap(bmp_dividor_n);
                        iv_dividor2.setImageBitmap(bmp_dividor_n);
                        iv_dividor3.setImageBitmap(bmp_dividor_n);
                        btn_ringtone.setBackgroundResource(R.drawable.n_ripple_effect);
                    }
                }
            }
        };
        getActivity().registerReceiver(m_timeChangedReceiver, s_intentFilter);

        btn_ringtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAudio();
            }
        });

        etxt_name.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                String name = etxt_name.getText().toString();
                final SharedPreferences clockSettings = getActivity().getSharedPreferences("MyClockPreferences", 0);
                SharedPreferences.Editor prefEditor = clockSettings.edit();
                prefEditor.putString("UserName", name);
                prefEditor.apply();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        etxt_kw1.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                String kw1 = etxt_kw1.getText().toString();
                final SharedPreferences clockSettings = getActivity().getSharedPreferences("MyClockPreferences", 0);
                SharedPreferences.Editor prefEditor = clockSettings.edit();
                prefEditor.putString("KeyWord1", kw1);
                prefEditor.apply();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        etxt_kw2.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                String kw2 = etxt_kw2.getText().toString();
                final SharedPreferences clockSettings = getActivity().getSharedPreferences("MyClockPreferences", 0);
                SharedPreferences.Editor prefEditor = clockSettings.edit();
                prefEditor.putString("KeyWord2", kw2);
                prefEditor.apply();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        etxt_kw3.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                String kw3 = etxt_kw3.getText().toString();
                final SharedPreferences clockSettings = getActivity().getSharedPreferences("MyClockPreferences", 0);
                SharedPreferences.Editor prefEditor = clockSettings.edit();
                prefEditor.putString("KeyWord3", kw3);
                prefEditor.apply();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        etxt_kw4.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                String kw4 = etxt_kw4.getText().toString();
                final SharedPreferences clockSettings = getActivity().getSharedPreferences("MyClockPreferences", 0);
                SharedPreferences.Editor prefEditor = clockSettings.edit();
                prefEditor.putString("KeyWord4", kw4);
                prefEditor.apply();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        etxt_kw5.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                String kw5 = etxt_kw5.getText().toString();
                final SharedPreferences clockSettings = getActivity().getSharedPreferences("MyClockPreferences", 0);
                SharedPreferences.Editor prefEditor = clockSettings.edit();
                prefEditor.putString("KeyWord5", kw5);
                prefEditor.apply();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String name = etxt_name.getText().toString();
        String kw1 = etxt_kw1.getText().toString();
        String kw2 = etxt_kw2.getText().toString();
        String kw3 = etxt_kw3.getText().toString();
        String kw4 = etxt_kw4.getText().toString();
        String kw5 = etxt_kw5.getText().toString();
        final SharedPreferences clockSettings = getActivity().getSharedPreferences("MyClockPreferences", 0);
        SharedPreferences.Editor prefEditor = clockSettings.edit();
        prefEditor.putString("UserName", name);
        prefEditor.putString("KeyWord1", kw1);
        prefEditor.putString("KeyWord2", kw2);
        prefEditor.putString("KeyWord3", kw3);
        prefEditor.putString("KeyWord4", kw4);
        prefEditor.putString("KeyWord5", kw5);
        prefEditor.apply();
        super.onSaveInstanceState(outState);
    }

    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*check whether you're working on correct request using requestCode , In this case 1*/

        if(requestCode == SELECT_AUDIO_REQUEST && resultCode == Activity.RESULT_OK){
            audio = data.getData(); //declared above Uri audio;
            Log.d("media", "onActivityResult: "+audio);

            MediaPlayer playerM = new MediaPlayer();
            try {
                playerM.setDataSource(getContext(), audio);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                playerM.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            playerM.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                @Override
                public void onPrepared(MediaPlayer playerM){
                    playerM.start();
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                selectAudio();
            }
            else{
                displayMessage(Objects.requireNonNull(getActivity()).getBaseContext(), "I can't ring the alarm without storage permission");
            }
        }
    }

    private void selectAudio() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            final Intent select_audio = new Intent(Intent.ACTION_GET_CONTENT);
            select_audio.setType("audio/*");
            startActivityForResult(select_audio,SELECT_AUDIO_REQUEST);
        } else {
            displayMessage(Objects.requireNonNull(getActivity()).getBaseContext(), "I can't ring the alarm without storage permission");
        }
    }
}