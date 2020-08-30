package com.teamscorpion.youtubealarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;

public class Audio_alarm extends AppCompatActivity {

    String str_uri;
    Uri uri;
    MediaPlayer player;
    Button btn_snooze, btn_stop;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_alarm);

        //Stop audio if playing
        this.stopService(new Intent(this, Audio_alarm_service.class));
        turnScreenOnAndKeyguardOff();

        final SharedPreferences clockSettings = getSharedPreferences("MyClockPreferences", 0);
        str_uri = clockSettings.getString("Uri", "null");
        File file = new File(str_uri);
        if (!file.exists()) {
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm);
        }else{
            uri = Uri.parse(str_uri);
        }
        player = MediaPlayer.create(this, uri);
        player.setLooping(true);
        player.start();

        btn_snooze = findViewById(R.id.btnSnooze);
        btn_stop = findViewById(R.id.btnStop);

        btn_snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.cancel(0);

                Intent intentSnooze = new Intent(Audio_alarm.this, AlarmBroadcast.class);
                PendingIntent pendingIntentSnooze = PendingIntent.getBroadcast(Audio_alarm.this, 0, intentSnooze, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();
                long tenSecondsInMillis = 1000 * 15;

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + tenSecondsInMillis, pendingIntentSnooze);

                System.exit(0);
                turnScreenOffAndKeyguardOn();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.cancel(0);
                finish();
                System.exit(0);
                turnScreenOffAndKeyguardOn();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            player.stop();
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            player.stop();
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_POWER)) {
            player.stop();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        turnScreenOffAndKeyguardOn();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void turnScreenOnAndKeyguardOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
            getWindow().setAttributes(params);
        }

        KeyguardManager kgm = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        Activity activity = (Activity) this;
        kgm.requestDismissKeyguard(activity, null);
    }

    void turnScreenOffAndKeyguardOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(false);
            setTurnScreenOn(false);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        }
    }
}