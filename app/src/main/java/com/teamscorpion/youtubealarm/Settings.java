package com.teamscorpion.youtubealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Settings extends Fragment {
    ConstraintLayout main_layout;
    IntentFilter s_intentFilter;
    ImageView iv_dividor1, iv_dividor2, iv_dividor3;
    Bitmap bmp_dividor_m, bmp_dividor_a, bmp_dividor_e, bmp_dividor_n;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        main_layout = view.findViewById(R.id.settings_layout);
        iv_dividor1 = view.findViewById(R.id.img_dividor1);
        iv_dividor2 = view.findViewById(R.id.img_dividor2);
        iv_dividor3 = view.findViewById(R.id.img_dividor3);;

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
        }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
            main_layout.setBackgroundColor(Color.parseColor("#d63447"));
            iv_dividor1.setImageBitmap(bmp_dividor_a);
            iv_dividor2.setImageBitmap(bmp_dividor_a);
            iv_dividor3.setImageBitmap(bmp_dividor_a);
        }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
            main_layout.setBackgroundColor(Color.parseColor("#febc6e"));
            iv_dividor1.setImageBitmap(bmp_dividor_e);
            iv_dividor2.setImageBitmap(bmp_dividor_e);
            iv_dividor3.setImageBitmap(bmp_dividor_e);
        }else {
            main_layout.setBackgroundColor(Color.parseColor("#202020"));
            iv_dividor1.setImageBitmap(bmp_dividor_n);
            iv_dividor2.setImageBitmap(bmp_dividor_n);
            iv_dividor3.setImageBitmap(bmp_dividor_n);
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
                    }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
                        main_layout.setBackgroundColor(Color.parseColor("#d63447"));
                        iv_dividor1.setImageBitmap(bmp_dividor_a);
                        iv_dividor2.setImageBitmap(bmp_dividor_a);
                        iv_dividor3.setImageBitmap(bmp_dividor_a);
                    }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
                        main_layout.setBackgroundColor(Color.parseColor("#febc6e"));
                        iv_dividor1.setImageBitmap(bmp_dividor_e);
                        iv_dividor2.setImageBitmap(bmp_dividor_e);
                        iv_dividor3.setImageBitmap(bmp_dividor_e);
                    }else {
                        main_layout.setBackgroundColor(Color.parseColor("#202020"));
                        iv_dividor1.setImageBitmap(bmp_dividor_n);
                        iv_dividor2.setImageBitmap(bmp_dividor_n);
                        iv_dividor3.setImageBitmap(bmp_dividor_n);
                    }
                }
            }
        };
        getActivity().registerReceiver(m_timeChangedReceiver, s_intentFilter);

        return view;
    }
}