package com.teamscorpion.youtubealarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class AlarmBroadcast extends BroadcastReceiver {

    private String CHANNEL_ID = "channelId";
    String channelId = CHANNEL_ID;
    Context context_global;
    Class destination;
    PendingIntent final_intent;
    boolean isScreenactive;

    public static final String ACTION_SNOOZE = "actionsnooze";
    public static final String ACTION_STOP = "actionstop";
    public static final String ACTION_WATCH = "actionwatch";

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    public void onReceive(Context context, Intent intent) {

        context_global = context;

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        isScreenactive = pm.isInteractive();

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            destination = Locked_alarm.class;
        }else{
            destination = Audio_alarm.class;
        }

        if (isScreenactive) {
            context.startService(new Intent(context, Audio_alarm_service.class));
        }
        create_notification();
    }

    void create_notification(){
        Intent intent2 = new Intent(context_global, destination);
        // flags and request code are 0 for the purpose of demonstration
        final_intent = PendingIntent.getActivity(context_global, 0, intent2, 0);

        Bitmap note_icon;
        String description;

        final Calendar[] rightNow = {Calendar.getInstance()};
        final int[] currentHourIn24Format = {rightNow[0].get(Calendar.HOUR)};

        final SharedPreferences clockSettings = context_global.getSharedPreferences("MyClockPreferences", 0);
        String Name = clockSettings.getString("UserName", "");
        String title = "Hi"+ Name;
        note_icon = BitmapFactory.decodeResource(context_global.getResources(),R.drawable.notification_icon);
        if(currentHourIn24Format[0] > 3 & currentHourIn24Format[0] < 12){
            description = "Good Morning.";
        }else if(currentHourIn24Format[0] > 11 & currentHourIn24Format[0] < 17){
            description = "Good Afternoon.";
        }else if(currentHourIn24Format[0] > 16 & currentHourIn24Format[0] < 21){
            description = "Good Evening.";
        }else {
            description = "Good Night.";
        }

        Intent intentSnooze = new Intent(context_global, NotificationActionService.class)
                .setAction(ACTION_SNOOZE);
        PendingIntent pendingIntentSnooze = PendingIntent.getBroadcast(context_global, 0,
                intentSnooze, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentStop = new Intent(context_global, NotificationActionService.class)
                .setAction(ACTION_STOP);
        PendingIntent pendingIntentStop = PendingIntent.getBroadcast(context_global, 0,
                intentStop, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentWatch = new Intent(context_global, NotificationActionService.class)
                .setAction(ACTION_WATCH);
        PendingIntent pendingIntentWatch = PendingIntent.getBroadcast(context_global, 0,
                intentWatch, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context_global);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context_global, channelId)
                .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                .setLargeIcon(note_icon)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_baseline_snooze_24, "snooze", pendingIntentSnooze)
                .addAction(R.drawable.ic_baseline_stop_24, "stop", pendingIntentStop)
                .addAction(R.drawable.ic_baseline_stop_24, "video", pendingIntentWatch)
                .setFullScreenIntent(final_intent, true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Example Notification Channel";
            String descriptionText = "This is used to demonstrate the Full Screen Intent";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(descriptionText);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, builder.build());
    }
}
