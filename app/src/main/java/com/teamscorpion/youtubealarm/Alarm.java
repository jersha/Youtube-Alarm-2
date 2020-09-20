package com.teamscorpion.youtubealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Alarm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Alarm extends Fragment implements popupActivity.OnInputSelected{
    ConstraintLayout main_layout1;
    RelativeLayout main_layout3;
    LinearLayout main_layout2;
    IntentFilter s_intentFilter;
    FloatingActionButton add;
    final Calendar[] rightNow = {Calendar.getInstance()};
    final int currentHourIn24Format = rightNow[0].get(Calendar.HOUR_OF_DAY);
    int total;

    public class AlarmTimes {
        public String m_time;
        public int m_value;
        public Boolean m_fav;

        public void findValue(){
            int one = Integer.parseInt(m_time.substring(0, 2)) * 100;
            int two = Integer.parseInt(m_time.substring(5, 7));
            this.m_value = one + two;
        }

        public AlarmTimes(String input, boolean favourite){
            this.m_time = input;
            this.m_fav = favourite;
        }
    }
    ArrayList<AlarmTimes> alarmtimes;
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
        main_layout1 = (ConstraintLayout) view.findViewById(R.id.alarm_layout1);
        main_layout2 = view.findViewById(R.id.alarm_layout2);
        main_layout3 = view.findViewById(R.id.alarm_layout3);
        add = view.findViewById(R.id.btn_add);
        if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
            main_layout3.setBackgroundColor(Color.parseColor("#f3989d"));
            add.setImageResource(R.drawable.m_add);
        }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
            main_layout3.setBackgroundColor(Color.parseColor("#d63447"));
            add.setImageResource(R.drawable.a_add);
        }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
            main_layout3.setBackgroundColor(Color.parseColor("#febc6e"));
            add.setImageResource(R.drawable.e_add);
        }else {
            main_layout3.setBackgroundColor(Color.parseColor("#202020"));
            add.setImageResource(R.drawable.n_add);
        }

        final SharedPreferences clockSettings = Objects.requireNonNull(getActivity()).getSharedPreferences("MyClockPreferences", 0);
        total = clockSettings.getInt("Total", 0);
        alarmtimes = new ArrayList<AlarmTimes>();
        if(total > 0){
            for(int loop = 0; loop < total; loop++) {
                String int_str = String.valueOf(loop);
                boolean fav = clockSettings.getBoolean("Fav" + int_str, false);
                String final_str = "Time" + int_str;
                String time = clockSettings.getString(final_str, "null");
                AlarmTimes temporary = new AlarmTimes(time, fav);
                temporary.findValue();
                alarmtimes.add(temporary);

                createAlarm(temporary.m_time, temporary.m_fav);
            }
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
                        add.setImageResource(R.drawable.m_add);
                    }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
                        main_layout3.setBackgroundColor(Color.parseColor("#d63447"));
                        add.setImageResource(R.drawable.a_add);
                    }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
                        main_layout3.setBackgroundColor(Color.parseColor("#febc6e"));
                        add.setImageResource(R.drawable.e_add);
                    }else {
                        main_layout3.setBackgroundColor(Color.parseColor("#202020"));
                        add.setImageResource(R.drawable.n_add);
                    }
                }
            }
        };

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupActivity dialog = new popupActivity();
                dialog.setTargetFragment(Alarm.this, 1);
                assert getFragmentManager() != null;
                dialog.show(getFragmentManager(), "popupActivity");
            }
        });

        Objects.requireNonNull(getActivity()).registerReceiver(m_timeChangedReceiver, s_intentFilter);

        return view;
    }

    int text1_id = 0;
    int sw_id = 100;
    int btn_id = 1000;
    int text2_id = 10000;
    int line_id = 100000;

    @Override
    public void sendInput(String input)  {
        AlarmTimes temporary = new AlarmTimes(input, false);
        temporary.findValue();
        alarmtimes.add(temporary);
        total++;

        Collections.sort(alarmtimes, new Comparator<AlarmTimes>() {
            @Override
            public int compare(AlarmTimes time1, AlarmTimes time2) {
                if(time1.m_value > time2.m_value) return 1;
                else if(time1.m_value < time2.m_value) return -1;
                else return 0;
            }
        });

        main_layout1.removeAllViews();
        main_layout1.invalidate();

        for(int loop = 0; loop < total; loop++){
            createAlarm(alarmtimes.get(loop).m_time, alarmtimes.get(loop).m_fav);
        }
    }

    void createAlarm(String input, Boolean favourite){
        final TextView text1 = new TextView(getContext());
        ConstraintLayout.LayoutParams txt1_params = new ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        text1_id += 1;
        text1.setId(text1_id);
        text1.setText("");
        if(text1_id != 1){
            txt1_params.topToBottom = line_id;
        }

        final Button btn_fav = new Button(getContext());
        if(favourite){
            btn_fav.setBackgroundResource(R.drawable.favourite_sel);
        }else{
            btn_fav.setBackgroundResource(R.drawable.favourite);
        }
        btn_id += 1;
        ConstraintLayout.LayoutParams btn_params = new ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btn_params.setMargins(32,0,32,0);
        btn_params.topToBottom = text1_id;
        btn_params.startToStart = 0;
        btn_params.height = 50;
        btn_params.width = 50;
        btn_fav.setId(btn_id);

        final TextView text2 = new TextView(getContext());
        ConstraintLayout.LayoutParams txt2_params = new ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        text2_id += 1;
        text2.setId(text2_id);
        txt2_params.topToBottom = btn_id;
        text2.setText("");

        final Switch sw_alarm = new Switch(getContext());
        sw_alarm.setText(input + "                                           ");
        sw_id += 1;
        ConstraintLayout.LayoutParams sw_params = new ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        sw_params.setMargins(32,0,32,0);
        sw_params.topToBottom = text1_id;
        sw_params.bottomToTop = text2_id;
        sw_params.endToEnd = 0;
        sw_params.startToEnd = btn_id;
        sw_alarm.setId(sw_id);
        sw_alarm.setTextColor(Color.parseColor("#ffffff"));
        sw_alarm.setTextSize(TypedValue.COMPLEX_UNIT_PT, 10);

        final ImageView dividor = new ImageView(getContext());
        if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
            dividor.setImageResource(R.drawable.m_line);
        }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
            dividor.setImageResource(R.drawable.a_line);
        }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
            dividor.setImageResource(R.drawable.e_line);
        }else {
            dividor.setImageResource(R.drawable.n_line);
        }
        ConstraintLayout.LayoutParams line_params = new ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        line_params.topToBottom = text2_id;
        line_id += 1;
        dividor.setId(line_id);

        main_layout1.addView(text1, txt1_params);
        main_layout1.addView(btn_fav, btn_params);
        main_layout1.addView(text2, txt2_params);
        main_layout1.addView(sw_alarm, sw_params);
        main_layout1.addView(dividor, line_params);
    }
}