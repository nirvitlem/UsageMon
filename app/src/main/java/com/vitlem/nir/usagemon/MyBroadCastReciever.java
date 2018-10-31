package com.vitlem.nir.usagemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class MyBroadCastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        if ((intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) || (intent.getAction().equals(Intent.ACTION_SCREEN_ON))) {

            Log.i("ACTION_SCREEN ",intent.getAction().toString() + " " + Calendar.getInstance().getTime().toString());
            //Take count of the screen off position
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    }
