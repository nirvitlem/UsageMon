package com.vitlem.nir.usagemon;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.vitlem.nir.usagemon.MainActivity.mAppWidgetId;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ScreenMonService extends Service {

    private MyBroadCastReciever screenOnOffReceiver = null;
    public static CountUpTimer t;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("ScreenOnOfService", "Service onCreate:.");

        new SimplePrefs.Builder()
                .setPrefsName("myapppreference")
                .setContext(getApplicationContext())
                .setMode(MODE_PRIVATE)
                .setDefaultUse(false)
                .build();

        //setContentView(R.layout.activity_main);
        t = new CountUpTimer(10000) {
            @Override
            public void onTick(long elapsedTime) {
                // MainActivity.UpdateText();
                //  TextView tv = findViewById(R.id.UsageText);
                //  tv.setText(Long.valueOf(t.getTimer()).toString());
            }
        };
        t.startat(MainActivity.loadsaveUsageTime(getApplicationContext(),mAppWidgetId));

        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();

        // Add network connectivity change action.
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");

        // Set broadcast receiver priority.
        intentFilter.setPriority(100);

        // Create a network change broadcast receiver.
        screenOnOffReceiver = new MyBroadCastReciever();

        // Register the broadcast receiver with the intent filter object.
        registerReceiver(screenOnOffReceiver, intentFilter);


        Log.i("MyBroadCastReciever.SCREEN_TOGGLE_TAG", "Service onCreate: MyBroadCastReciever is registered.");
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }

  /*  @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("onHandleIntent", "onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }

            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            mReceiver = new MyBroadCastReciever();
            registerReceiver(mReceiver, filter);
            Log.i("onHandleIntent", "not null");
        }
        try {

            ACTION_STATUS="Working";
            while (true) {
            }
        }catch(Exception e) {
            ACTION_STATUS="Exception";
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_STATUS));}
        ACTION_STATUS="NotWorking";
    }*/


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy", "onDestroy");
        if(screenOnOffReceiver!=null) {
           // Intent intent = new Intent(this, ScreenMonService.class);
            //startService(intent);
            Log.i("onDestroy", "unregisterReceiver");
            unregisterReceiver(screenOnOffReceiver);

        }
    }
}
