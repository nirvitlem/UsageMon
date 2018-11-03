package com.vitlem.nir.usagemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyBroadCastReciever extends BroadcastReceiver {
    public static long TimeUsage = 0;
    public static long Timetemp=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.i("MyBroadCastReciever ","onReceive");
        if ((intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) || (intent.getAction().equals(Intent.ACTION_SCREEN_ON))) {

            Log.i("ACTION_SCREEN ",intent.getAction().toString() + " " + Calendar.getInstance().getTime().toString());
            //Take count of the screen off position
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
            {
                if (Timetemp>0)
                {
                    TimeUsage += (Calendar.getInstance().getTimeInMillis()-Timetemp);
                    MainActivity.saveUsageTime(context,MainActivity.mAppWidgetId,TimeUsage);
                    MainActivity.saveTempTime(context,MainActivity.mAppWidgetId,Timetemp);
                    Log.i("ACTION_SCREEN_OFF ",(new SimpleDateFormat("mm:ss")).format(new Date(MyBroadCastReciever.TimeUsage)));
                }
            }
            else
            {
                Timetemp = Calendar.getInstance().getTimeInMillis();
                MainActivity.saveTempTime(context,MainActivity.mAppWidgetId,Timetemp);
                Log.i("ACTION_SCREEN_ON ",(new SimpleDateFormat("mm:ss")).format(new Date(MyBroadCastReciever.Timetemp)));
            }

        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    }
