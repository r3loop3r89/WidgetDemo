package com.shra1.widgetdemo.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.shra1.widgetdemo.R;
import com.shra1.widgetdemo.widgets.NewAppWidget;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.shra1.widgetdemo.activities.MainActivity.TAG;

public class NotificationService extends Service {

    public static SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa");
    private String CHANNEL_ID = "myChannelId";
    private CharSequence CHANNEL_NAME = "shrawansChannel";
    private Context mCtx;
    private NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mCtx = getApplicationContext();

        Log.d(TAG, "Service was started on " + sdf.format(new Date()));


        //STEP #1
        //<editor-fold desc="SETUP NOTIFICATION MANAGER">
        if (notificationManager == null) {
            notificationManager =
                    (NotificationManager)
                            mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //</editor-fold>

        //STEP #2
        //<editor-fold desc="SETUP NOTOFICATION BUILDER">
        NotificationCompat.Builder
                builder = new NotificationCompat.Builder(mCtx, CHANNEL_ID);

        RemoteViews views = new RemoteViews(mCtx.getPackageName(), R.layout.new_app_widget);

        views.setOnClickPendingIntent(R.id.bMinimum, NewAppWidget.getPendingIntent(mCtx, 1, NewAppWidget.Minimum));
        views.setOnClickPendingIntent(R.id.bTwentyFive, NewAppWidget.getPendingIntent(mCtx, 1, NewAppWidget.TwentyFive));
        views.setOnClickPendingIntent(R.id.bFifty, NewAppWidget.getPendingIntent(mCtx, 1, NewAppWidget.Fifty));
        views.setOnClickPendingIntent(R.id.bSeventyFive, NewAppWidget.getPendingIntent(mCtx, 1, NewAppWidget.SeventyFive));
        views.setOnClickPendingIntent(R.id.bMaximum, NewAppWidget.getPendingIntent(mCtx, 1, NewAppWidget.Maximum));


        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification")
                //.setAutoCancel(true)
                //.setContentIntent(pendingIntent)
                //.setContentText("This is notification")
                .setSound(null)
                .setVibrate(new long[]{100, 200})
                .setCustomContentView(views)
                .setPriority(NotificationManager.IMPORTANCE_DEFAULT);
        //</editor-fold>

        //STEP #3
        //notificationManager.notify(45, builder.build());
        startForeground(65, builder.build());

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Intent serviceRetainerRecieverIntent = new Intent();
        serviceRetainerRecieverIntent.setAction("IAMUNKILLABLE");
        sendBroadcast(serviceRetainerRecieverIntent);
        super.onDestroy();
    }
}
