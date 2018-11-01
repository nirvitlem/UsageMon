package com.vitlem.nir.usagemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TimeUtils;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class MyBroadCastReciever extends BroadcastReceiver {
    public static long TimeUsage = 0;
    private long t;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        if ((intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) || (intent.getAction().equals(Intent.ACTION_SCREEN_ON))) {

            Log.i("ACTION_SCREEN ",intent.getAction().toString() + " " + Calendar.getInstance().getTime().toString());
            //Take count of the screen off position
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
            {
                if (t>0) TimeUsage += (Calendar.getInstance().getTimeInMillis()-t);
            }
            else
            {
                t = Calendar.getInstance().getTimeInMillis();
                Log.i("t ",String.valueOf(t));
            }
            Log.i("TimeUsage ",String.valueOf(TimeUsage));
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    }
