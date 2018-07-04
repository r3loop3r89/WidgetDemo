package com.shra1.widgetdemo;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String Minimum = "Minimum";
    private static final String TwentyFive = "TwentyFive";
    private static final String Fifty = "Fifty";
    private static final String SeventyFive = "SeventyFive";
    private static final String Maximum = "Maximum";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setOnClickPendingIntent(R.id.bMinimum, getPendingIntent(context, 1, Minimum));
        views.setOnClickPendingIntent(R.id.bTwentyFive, getPendingIntent(context, 1, TwentyFive));
        views.setOnClickPendingIntent(R.id.bFifty, getPendingIntent(context, 1, Fifty));
        views.setOnClickPendingIntent(R.id.bSeventyFive, getPendingIntent(context, 1, SeventyFive));
        views.setOnClickPendingIntent(R.id.bMaximum, getPendingIntent(context, 1, Maximum));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
    }

    private static PendingIntent getPendingIntent(Context context, int requestCode, String action) {
        Intent i = new Intent(context, NewAppWidget.class);
        i.setAction(action);
        return PendingIntent.getBroadcast(context, requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        switch (intent.getAction()) {
            case Minimum:
                ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);
                takePermissionAndApplyBrightness(context, 1);
                break;
            case TwentyFive:
                ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(40);
                takePermissionAndApplyBrightness(context, 64);
                break;
            case Fifty:
                ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(60);
                takePermissionAndApplyBrightness(context, 128);
                break;
            case SeventyFive:
                ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(80);
                takePermissionAndApplyBrightness(context, 192);
                break;
            case Maximum:
                ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);
                takePermissionAndApplyBrightness(context, 255);
                break;
        }
    }

    private void takePermissionAndApplyBrightness(final Context context, int value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(context)) {
                // Do stuff here
                Settings.System.putInt(context.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, value); //<-- 1-225
            } else {

                Toast.makeText(context, "Allow to change system settings!", Toast.LENGTH_SHORT).show();

                Intent inten = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                inten.setData(Uri.parse("package:" + context.getPackageName()));
                inten.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(inten);

            }
        } else {
            Settings.System.putInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, value); //<-- 1-225
        }
    }
}

