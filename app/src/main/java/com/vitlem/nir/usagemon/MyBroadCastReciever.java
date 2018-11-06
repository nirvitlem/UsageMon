package com.vitlem.nir.usagemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.vitlem.nir.usagemon.MainActivity.mAppWidgetId;

public class MyBroadCastReciever extends BroadcastReceiver {




    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.i("MyBroadCastReciever ","onReceive");
        if ((intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) || (intent.getAction().equals(Intent.ACTION_SCREEN_ON))) {

            Log.i("ACTION_SCREEN ",intent.getAction().toString() + " " +  Long.valueOf(ScreenMonService.t.getTimer()).toString());
            //Take count of the screen off position
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

                ScreenMonService.t.stop();
                MainActivity.saveUsageTime(context, mAppWidgetId, ScreenMonService.t.getTimer());
                Log.i("ACTION_SCREEN_OFF ", Long.valueOf(ScreenMonService.t.getTimer()).toString());

            }
            else
            {
                Log.i("Load Timer after screen on ",Long.valueOf(MainActivity.loadsaveUsageTime(context,mAppWidgetId)).toString());
                ScreenMonService.t.startat(MainActivity.loadsaveUsageTime(context,mAppWidgetId));
                Log.i("ACTION_SCREEN_ON ", Long.valueOf(ScreenMonService.t.getTimer()).toString());
            }

        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    }
