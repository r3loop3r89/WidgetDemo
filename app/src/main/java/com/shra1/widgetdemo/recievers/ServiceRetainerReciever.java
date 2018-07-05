package com.shra1.widgetdemo.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.shra1.widgetdemo.services.NotificationService;
import com.shra1.widgetdemo.utils.SharedPreferencesStorage;
import com.shra1.widgetdemo.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.shra1.widgetdemo.activities.MainActivity.TAG;

public class ServiceRetainerReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (SharedPreferencesStorage
                .getInstance(context)
                .getEnableDisableNotificationStatus()) {
            if (Utils.isMyServiceRunning(context, NotificationService.class)) {

            } else {
                Intent i = new Intent(context, NotificationService.class);
                context.startService(i);
            }
        }
    }
}
