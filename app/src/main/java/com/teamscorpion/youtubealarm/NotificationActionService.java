package com.teamscorpion.youtubealarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationActionService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        switch (action) {
            case AlarmBroadcast.ACTION_SNOOZE:
                context.stopService(new Intent(context, Audio_alarm_service.class));
                notificationManager.cancel(0);

                Intent intentSnooze = new Intent(context, AlarmBroadcast.class);
                PendingIntent pendingIntentSnooze = PendingIntent.getBroadcast(context, 0, intentSnooze, 0);

                AlarmManager alarmManager_snooze = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();
                long tenSecondsInMillis = 1000 * 15;

                alarmManager_snooze.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + tenSecondsInMillis, pendingIntentSnooze);
                break;
            case AlarmBroadcast.ACTION_STOP:
                notificationManager.cancel(0);

                context.stopService(new Intent(context, Audio_alarm_service.class));
                break;
            case AlarmBroadcast.ACTION_WATCH:
                notificationManager.cancel(0);

                context.stopService(new Intent(context, Audio_alarm_service.class));

                Intent intent_video = new Intent(context, Locked_alarm.class);
                intent_video.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent_video);
                break;
        }
    }
}