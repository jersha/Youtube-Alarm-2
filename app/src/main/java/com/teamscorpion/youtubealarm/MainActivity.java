package com.teamscorpion.youtubealarm;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        final Calendar[] rightNow = {Calendar.getInstance()};
        final int currentHourIn24Format = rightNow[0].get(Calendar.HOUR_OF_DAY);
        if(currentHourIn24Format > 3 & currentHourIn24Format < 12){
            tabLayout.setBackgroundColor(Color.parseColor("#f3989d"));
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f3989d"));
        }else if(currentHourIn24Format > 11 & currentHourIn24Format < 17){
            tabLayout.setBackgroundColor(Color.parseColor("#d63447"));
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#d63447"));
        }else if(currentHourIn24Format > 16 & currentHourIn24Format < 21){
            tabLayout.setBackgroundColor(Color.parseColor("#febc6e"));
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#febc6e"));
        }else {
            tabLayout.setBackgroundColor(Color.parseColor("#202020"));
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#202020"));
        }

        final int[] ICONS = new int[]{
                R.drawable.a_add_btn,
                R.drawable.m_add_btn,
                R.drawable.e_add_btn
        };

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                if (tabLayout.getTabAt(0).isSelected()) {
//                    tabLayout.getTabAt(0).setIcon(ICONS[1]);
//                } else if (tabLayout.getTabAt(1).isSelected()) {
//                    tabLayout.getTabAt(1).setIcon(ICONS[2]);
//                } else if (tabLayout.getTabAt(2).isSelected()) {
//                    tabLayout.getTabAt(2).setIcon(ICONS[0]);
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                if (tabLayout.getTabAt(0).isSelected()) {
//                    tabLayout.getTabAt(0).setIcon(ICONS[0]);
//                    tabLayout.getTabAt(1).setIcon(ICONS[1]);
//                    tabLayout.getTabAt(2).setIcon(ICONS[2]);
//                } else if (tabLayout.getTabAt(1).isSelected()) {
//                    tabLayout.getTabAt(0).setIcon(ICONS[0]);
//                    tabLayout.getTabAt(1).setIcon(ICONS[1]);
//                    tabLayout.getTabAt(2).setIcon(ICONS[2]);
//                } else if (tabLayout.getTabAt(2).isSelected()) {
//                    tabLayout.getTabAt(0).setIcon(ICONS[0]);
//                    tabLayout.getTabAt(1).setIcon(ICONS[1]);
//                    tabLayout.getTabAt(2).setIcon(ICONS[2]);
//                }
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
                switch (position) {
                    case 0:
                        tabLayout.getTabAt(0).setIcon(ICONS[1]);
                        tabLayout.getTabAt(1).setIcon(ICONS[1]);
                        tabLayout.getTabAt(2).setIcon(ICONS[2]);
                        break;
                    case 1:
                        tabLayout.getTabAt(0).setIcon(ICONS[0]);
                        tabLayout.getTabAt(1).setIcon(ICONS[2]);
                        tabLayout.getTabAt(2).setIcon(ICONS[2]);
                        break;
                    case 2:
                        tabLayout.getTabAt(0).setIcon(ICONS[0]);
                        tabLayout.getTabAt(1).setIcon(ICONS[1]);
                        tabLayout.getTabAt(2).setIcon(ICONS[0]);
                        break;
                    default:
                        tabLayout.getTabAt(0).setIcon(ICONS[0]);
                        tabLayout.getTabAt(1).setIcon(ICONS[1]);
                        tabLayout.getTabAt(2).setIcon(ICONS[2]);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(1);
    }

}

