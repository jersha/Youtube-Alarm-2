package com.teamscorpion.youtubealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class Main2 extends AppCompatActivity {
    IntentFilter s_intentFilter;
    int tab_selected = 1;
    int currentHourIn24Format = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        //Get reference to your Tablayout
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        PageAdapter pageAdapter = new
                PageAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        final int[] Tab_bg = new int[]{
                R.drawable.m_alarm_sel,
                R.drawable.m_clock_sel,
                R.drawable.m_settings_sel,
                R.drawable.a_alarm_sel,
                R.drawable.a_clock_sel,
                R.drawable.a_settings_sel,
                R.drawable.e_alarm_sel,
                R.drawable.e_clock_sel,
                R.drawable.e_settings_sel,
                R.drawable.n_alarm_sel,
                R.drawable.n_clock_sel,
                R.drawable.n_settings_sel
        };

        final Calendar[] rightNow = {Calendar.getInstance()};
        currentHourIn24Format = rightNow[0].get(Calendar.HOUR_OF_DAY);
        if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
            tabLayout.setBackgroundResource(Tab_bg[1]);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f3989d"));
        }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
            tabLayout.setBackgroundResource(Tab_bg[4]);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#d63447"));
        }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
            tabLayout.setBackgroundResource(Tab_bg[7]);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#febc6e"));
        }else {
            tabLayout.setBackgroundResource(Tab_bg[10]);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#202020"));
        }

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab_selected = position;
                if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f3989d"));
                    switch (tab_selected) {
                        case 0:
                            tabLayout.setBackgroundResource(Tab_bg[0]);
                            break;
                        case 2:
                            tabLayout.setBackgroundResource(Tab_bg[2]);
                            break;
                        default:
                            tabLayout.setBackgroundResource(Tab_bg[1]);
                            break;
                    }
                }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#d63447"));
                    switch (tab_selected) {
                        case 0:
                            tabLayout.setBackgroundResource(Tab_bg[3]);
                            break;
                        case 2:
                            tabLayout.setBackgroundResource(Tab_bg[5]);
                            break;
                        default:
                            tabLayout.setBackgroundResource(Tab_bg[4]);
                            break;
                    }
                }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#febc6e"));
                    switch (tab_selected) {
                        case 0:
                            tabLayout.setBackgroundResource(Tab_bg[6]);
                            break;
                        case 2:
                            tabLayout.setBackgroundResource(Tab_bg[8]);
                            break;
                        default:
                            tabLayout.setBackgroundResource(Tab_bg[7]);
                            break;
                    }
                }else {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#202020"));
                    switch (tab_selected) {
                        case 0:
                            tabLayout.setBackgroundResource(Tab_bg[9]);
                            break;
                        case 2:
                            tabLayout.setBackgroundResource(Tab_bg[11]);
                            break;
                        default:
                            tabLayout.setBackgroundResource(Tab_bg[10]);
                            break;
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
                    currentHourIn24Format = rightNow[0].get(Calendar.HOUR_OF_DAY);

                    if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#202020"));
                        switch (tab_selected) {
                            case 0:
                                tabLayout.setBackgroundResource(Tab_bg[0]);
                                break;
                            case 2:
                                tabLayout.setBackgroundResource(Tab_bg[2]);
                                break;
                            default:
                                tabLayout.setBackgroundResource(Tab_bg[1]);
                                break;
                        }
                    }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
                        switch (tab_selected) {
                            case 0:
                                tabLayout.setBackgroundResource(Tab_bg[3]);
                                break;
                            case 2:
                                tabLayout.setBackgroundResource(Tab_bg[5]);
                                break;
                            default:
                                tabLayout.setBackgroundResource(Tab_bg[4]);
                                break;
                        }
                    }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
                        switch (tab_selected) {
                            case 0:
                                tabLayout.setBackgroundResource(Tab_bg[6]);
                                break;
                            case 2:
                                tabLayout.setBackgroundResource(Tab_bg[8]);
                                break;
                            default:
                                tabLayout.setBackgroundResource(Tab_bg[7]);
                                break;
                        }
                    }else {
                        switch (tab_selected) {
                            case 0:
                                tabLayout.setBackgroundResource(Tab_bg[9]);
                                break;
                            case 2:
                                tabLayout.setBackgroundResource(Tab_bg[11]);
                                break;
                            default:
                                tabLayout.setBackgroundResource(Tab_bg[10]);
                                break;
                        }
                    }
                }
            }
        };
        registerReceiver(m_timeChangedReceiver, s_intentFilter);

        viewPager.setCurrentItem(1);
    }

}

