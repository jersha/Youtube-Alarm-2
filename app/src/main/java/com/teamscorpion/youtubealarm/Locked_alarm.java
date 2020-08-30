package com.teamscorpion.youtubealarm;

import androidx.annotation.RequiresApi;

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
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Locked_alarm extends YouTubeBaseActivity {

    String keyword;
    MediaPlayer player;
    String str_uri;
    Uri uri;
    int total;
    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    YouTubePlayer youTubePlayer_final;
    Button btn_snooze, btn_stop;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locked_alarm);

        btn_snooze = findViewById(R.id.btnSnooze);
        btn_stop = findViewById(R.id.btnStop);

        btn_snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.cancel(0);

                Intent intentSnooze = new Intent(Locked_alarm.this, AlarmBroadcast.class);
                PendingIntent pendingIntentSnooze = PendingIntent.getBroadcast(Locked_alarm.this, 0, intentSnooze, 0);

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

        //Stop audio if playing
        this.stopService(new Intent(this, Audio_alarm_service.class));
        turnScreenOnAndKeyguardOff();

        final SharedPreferences clockSettings = getSharedPreferences("MyClockPreferences", 0);
        keyword = clockSettings.getString("Keyword", "null");
        keyword.replace(' ', '+');

        str_uri = clockSettings.getString("Uri", "null");
        File file = new File(str_uri);
        if (!file.exists()) {
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarm);
        }else{
            uri = Uri.parse(str_uri);
        }

        player = MediaPlayer.create(this, uri);
        player.setLooping(true);

        total = -1;
        String videoId = null;
        String search = "https://www.youtube.com/results?search_query=" + keyword;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final String[] finaloutput = new String[11];

        if (keyword != "null") {
            try {
                Document doc  = Jsoup.connect(search).timeout(60000).get();
                String input = doc.toString();
                int count;
                for(count = 0; count < 10; count ++){
                    int firstindex = input.indexOf("videoRenderer");
                    if(firstindex != -1){
                        String output = input.substring(firstindex + 27);
                        firstindex = output.indexOf("\"");
                        finaloutput[count] = output.substring(0, firstindex);
                        input = output.substring(firstindex + 1);
                    }else{
                        continue;
                    }
                }
                total = count - 1;
                if(total != -1){
                    Random rand = new Random();
                    int rand_int1 = rand.nextInt(total);
                    videoId = finaloutput[rand_int1];
                    mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlay);
                    final String finalVideoId = videoId;
                    mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            youTubePlayer_final = youTubePlayer;
                            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                            youTubePlayer.loadVideo(finalVideoId);
                            youTubePlayer.play();
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                            player.start();
                        }
                    };
                    mYouTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
                }else{
                    player.start();
                }
            } catch (IOException e) {
                player.start();
            }
        } else {
            player.start();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            youTubePlayer_final.pause();
            player.stop();
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            youTubePlayer_final.pause();
            player.stop();
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_POWER)) {
            youTubePlayer_final.pause();
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