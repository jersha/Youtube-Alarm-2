package com.teamscorpion.youtubealarm;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.File;

public class Audio_alarm_service extends Service  {

    MediaPlayer player;
    String str_uri;
    Uri uri;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        final SharedPreferences clockSettings = this.getSharedPreferences("MyClockPreferences", 0);
        str_uri = clockSettings.getString("Uri", "null");
        File file = new File(str_uri);
        if (!file.exists()) {
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.alarm);
        }else{
            uri = Uri.parse(str_uri);
        }
        player = MediaPlayer.create(this, uri);
        player.setLooping(true);
        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        player.stop();
    }
}
