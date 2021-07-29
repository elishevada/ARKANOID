package com.elishevada.ex2;

import androidx.core.app.NotificationCompat;





import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static androidx.core.content.ContextCompat.getSystemService;

public class MyReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "appChanel2";
    private static final CharSequence CHANNEL_NAME = "App Chanel #2";
    private int notificationID=1;
    private NotificationManager notificationManager;



    @Override
    public void onReceive(Context context, Intent intent)
    {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;


        String action = intent.getAction();
        Log.d("mylog", ">> action="+action);

        if(Intent.ACTION_BATTERY_CHANGED.equals(action))
        {

            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            if(batteryLevel<=10 && batteryLevel>-1) {

                if(isCharging)
                    return;

                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                // 3. Create & show the Notification. on Build.VERSION < OREO notification avoid CHANEL_ID
                Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_batterylow)
                        .setContentTitle("BATTERY LOW " +batteryLevel+"%")
                        .setContentText("Please charge (notification from  game)")
                        .setContentIntent(pendingIntent)
                        .build();
                notificationManager.notify(notificationID, notification);


            }
        }

    }

}
