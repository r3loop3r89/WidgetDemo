package com.shra1.widgetdemo.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.shra1.widgetdemo.R;
import com.shra1.widgetdemo.services.NotificationService;
import com.shra1.widgetdemo.utils.SharedPreferencesStorage;
import com.shra1.widgetdemo.utils.Utils;

import static com.shra1.widgetdemo.widgets.NewAppWidget.Fifty;
import static com.shra1.widgetdemo.widgets.NewAppWidget.Maximum;
import static com.shra1.widgetdemo.widgets.NewAppWidget.Minimum;
import static com.shra1.widgetdemo.widgets.NewAppWidget.SeventyFive;
import static com.shra1.widgetdemo.widgets.NewAppWidget.TwentyFive;
import static com.shra1.widgetdemo.widgets.NewAppWidget.getPendingIntent;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "ShraX";
    AppCompatCheckBox cbMAUseNotificationShortcuts;
    TextView tvMAServiceStarter;
    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCtx = this;

        initViews();

        checkStatus();


        cbMAUseNotificationShortcuts
                .setOnClickListener(v -> {
                    AppCompatCheckBox cb = (AppCompatCheckBox) v;
                    if (cb.isChecked()) {
                        SharedPreferencesStorage.getInstance(mCtx)
                                .setEnableDisableNotificationStatus(true);
                    } else {
                        SharedPreferencesStorage.getInstance(mCtx)
                                .setEnableDisableNotificationStatus(false);
                    }

                    checkStatus();

                });


        /*PowerManager powerManager = (PowerManager) mCtx.getSystemService(Context.POWER_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (powerManager.isIgnoringBatteryOptimizations(mCtx.getPackageName())){
                Spanned spanned = Html.fromHtml("<font color=\"#008141\"><b>Awesome!</b></font>, This application is allowed to run in the background ignoring battery optimization.");
                tvMAServiceStarter.setText(spanned);
                tvMAServiceStarter.setOnClickListener(null);
            }else{
                Spanned spanned = Html.fromHtml("<font color=\"RED\">Ohh ooh!</font>, This application requires battery optimization to be <b>Disabled</b> to work properly. Kindly <font color=\"BLUE\"><b>Click here</b></font> to <b>Disable it</b>. Select this app in the following screen and click on <b>Dont Optimize</b>");
                tvMAServiceStarter.setText(spanned);
                tvMAServiceStarter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                        startActivityForResult(intent, 321);
                    }
                });
            }
        }else{
            tvMAServiceStarter.setVisibility(View.GONE);
        }*/

        Log.d(TAG, "BRAND : " + Build.BRAND);
        Log.d(TAG, "BASE OS : " + Build.VERSION.BASE_OS);


        if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            Spanned spanned = Html.fromHtml("If app is not showing notifications consistently kindly allow the app to auto start in the background. You can do so by <font color='#0000ff'><b>Clicking here.</b></font>");

            tvMAServiceStarter.setText(spanned);

            tvMAServiceStarter.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                startActivity(intent);
            });
        }

    }

    private void checkStatus() {
        if (SharedPreferencesStorage.getInstance(mCtx)
                .getEnableDisableNotificationStatus()) {
            cbMAUseNotificationShortcuts.setChecked(true);
            if (Utils.isMyServiceRunning(mCtx, NotificationService.class)) {

            } else {
                startTheService();
            }
        } else {
            cbMAUseNotificationShortcuts.setChecked(false);
            if (Utils.isMyServiceRunning(mCtx, NotificationService.class)) {
                stopTheService();
            } else {
            }
        }
    }

    private void stopTheService() {
        Intent i = new Intent(mCtx, NotificationService.class);
        stopService(i);
    }

    private void startTheService() {
        Intent i = new Intent(mCtx, NotificationService.class);
        startService(i);
    }

    private void initViews() {
        cbMAUseNotificationShortcuts = findViewById(R.id.cbMAUseNotificationShortcuts);
        tvMAServiceStarter = findViewById(R.id.tvMAServiceStarter);
    }
}
