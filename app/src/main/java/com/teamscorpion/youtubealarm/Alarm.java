package com.teamscorpion.youtubealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Alarm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Alarm extends Fragment {
    ConstraintLayout main_layout1;
    RelativeLayout main_layout3;
    LinearLayout main_layout2;
    IntentFilter s_intentFilter;
    FloatingActionButton add;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Alarm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Alarm.
     */
    // TODO: Rename and change types and number of parameters
    public static Alarm newInstance(String param1, String param2) {
        Alarm fragment = new Alarm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_alarm, container, false);
        main_layout1 = view.findViewById(R.id.alarm_layout1);
        main_layout2 = view.findViewById(R.id.alarm_layout2);
        main_layout3 = view.findViewById(R.id.alarm_layout3);
        add = view.findViewById(R.id.pink_icon);
        final Calendar[] rightNow = {Calendar.getInstance()};
        final int currentHourIn24Format = rightNow[0].get(Calendar.HOUR_OF_DAY);
        if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
            main_layout3.setBackgroundColor(Color.parseColor("#f3989d"));
            add.setBackgroundColor(Color.parseColor("#f3989d"));
        }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
            main_layout3.setBackgroundColor(Color.parseColor("#d63447"));
            add.setBackgroundColor(Color.parseColor("#d63447"));
        }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
            main_layout3.setBackgroundColor(Color.parseColor("#febc6e"));
            add.setBackgroundColor(Color.parseColor("#febc6e"));
        }else {
            main_layout3.setBackgroundColor(Color.parseColor("#202020"));
            add.setBackgroundColor(Color.parseColor("#202020"));
            add.setImageResource(R.drawable.n_add);
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
                        main_layout3.setBackgroundColor(Color.parseColor("#f3989d"));
                        add.setBackgroundColor(Color.parseColor("#f3989d"));
                    }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
                        main_layout3.setBackgroundColor(Color.parseColor("#d63447"));
                        add.setBackgroundColor(Color.parseColor("#d63447"));
                    }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
                        main_layout3.setBackgroundColor(Color.parseColor("#febc6e"));
                        add.setBackgroundColor(Color.parseColor("#febc6e"));
                    }else {
                        main_layout3.setBackgroundColor(Color.parseColor("#202020"));
                        add.setImageResource(R.drawable.n_add);
                    }
                }
            }
        };
        getActivity().registerReceiver(m_timeChangedReceiver, s_intentFilter);

        return view;
    }
}