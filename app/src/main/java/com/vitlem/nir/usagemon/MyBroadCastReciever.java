package com.vitlem.nir.usagemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadCastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.i("MyBroadCastReciever ","onReceive");
        if ((intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) || (intent.getAction().equals(Intent.ACTION_SCREEN_ON))) {

            Log.i("ACTION_SCREEN ",intent.getAction().toString() + " " +  Long.valueOf(MainActivity.t.getTimer()).toString());
            //Take count of the screen off position
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

                MainActivity.t.stop();
                MainActivity.saveUsageTime(context, MainActivity.mAppWidgetId, MainActivity.t.getTimer());
                              Log.i("ACTION_SCREEN_OFF ", Long.valueOf(MainActivity.t.getTimer()).toString());

            }
            else
            {
                Log.i("Load Timer after screen on ",Long.valueOf(MainActivity.loadsaveUsageTime(context,MainActivity.mAppWidgetId)).toString());
                MainActivity.t.startat(MainActivity.loadsaveUsageTime(context,MainActivity.mAppWidgetId));
                Log.i("ACTION_SCREEN_ON ", Long.valueOf(MainActivity.t.getTimer()).toString());
            }

        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    }
