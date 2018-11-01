package com.vitlem.nir.usagemon;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Calendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ScreenMonService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.vitlem.nir.usagemon.action.FOO";
    private static final String ACTION_BAZ = "com.vitlem.nir.usagemon.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.vitlem.nir.usagemon.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.vitlem.nir.usagemon.extra.PARAM2";

    private BroadcastReceiver mReceiver=null;
    public static String ACTION_STATUS =null;

    public ScreenMonService() {
        super("ScreenMonService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }


    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ScreenMonService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
        MyBroadCastReciever.Timetemp = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ScreenMonService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
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
            Log.i("mReceiver", "create");
        }
        try {

            ACTION_STATUS="Working";
            while (true) {
            }
        }catch(Exception e) {
            ACTION_STATUS="Exception";
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_STATUS));}
        ACTION_STATUS="NotWorking";
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onDestroy() {

        Log.i("ScreenOnOff", "Service  distroy");
        ACTION_STATUS="onDestroy";
        if(mReceiver!=null)
            unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
