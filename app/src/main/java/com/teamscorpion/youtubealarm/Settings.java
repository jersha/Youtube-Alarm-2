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

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment {
    ConstraintLayout main_layout;
    IntentFilter s_intentFilter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
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
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        main_layout = view.findViewById(R.id.settings_layout);
        final Calendar[] rightNow = {Calendar.getInstance()};
        final int currentHourIn24Format = rightNow[0].get(Calendar.HOUR_OF_DAY);
        if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
            main_layout.setBackgroundColor(Color.parseColor("#f3989d"));
        }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
            main_layout.setBackgroundColor(Color.parseColor("#d63447"));
        }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
            main_layout.setBackgroundColor(Color.parseColor("#febc6e"));
        }else {
            main_layout.setBackgroundColor(Color.parseColor("#202020"));
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
                    }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
                        main_layout.setBackgroundColor(Color.parseColor("#d63447"));
                    }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
                        main_layout.setBackgroundColor(Color.parseColor("#febc6e"));
                    }else {
                        main_layout.setBackgroundColor(Color.parseColor("#202020"));
                    }
                }
            }
        };
        getActivity().registerReceiver(m_timeChangedReceiver, s_intentFilter);

        return view;
    }
}