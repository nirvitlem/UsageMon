package com.vitlem.nir.usagemon;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public final int MY_PERMISSIONS_REQUEST=1;
    public PendingIntent service = null;
    private static final String PREFS_NAME = "com.vitlem.nir.usagemon";
    private static final String PREF_PREFIX_KEY = "app_";
    public static int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE },
                MY_PERMISSIONS_REQUEST);
        */
        deleteBackGroundPref(this,mAppWidgetId);
        MyBroadCastReciever.Timetemp=loadTitlePrefNum0(this,mAppWidgetId);
        Intent intent = new Intent(this, ScreenMonService.class);
        startService(intent);
        UpdateText();

    }


    protected void onResume () {
        super.onResume();
        deleteBackGroundPref(this,mAppWidgetId);
        UpdateText();
}

    protected void onRestart () {
        super.onRestart();
        deleteBackGroundPref(this,mAppWidgetId);
        UpdateText();

    }

    private void UpdateText()
    {
        TextView tv = findViewById(R.id.UsageText);
        if (MyBroadCastReciever.Timetemp>0) MyBroadCastReciever.TimeUsage += (Calendar.getInstance().getTimeInMillis()-MyBroadCastReciever.Timetemp);
        MyBroadCastReciever.Timetemp = Calendar.getInstance().getTimeInMillis();
        saveTitleNum0(this,mAppWidgetId,MyBroadCastReciever.Timetemp);
        String t =( new SimpleDateFormat("mm:ss")).format(new Date(MyBroadCastReciever.TimeUsage));
        tv.setText(t);
    }


    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitleNum0(Context context, int appWidgetId, long text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putLong(PREF_PREFIX_KEY + appWidgetId+ getCurrentDateinLong(0), text);
        prefs.apply();
        Log.d("S_titleValue",String.valueOf(text));
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static long loadTitlePrefNum0(Context context, int appWidgetId) {
        //Log.d("getCurrentDateinLong ",String.valueOf(getCurrentDateinLong(0)));
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        Long titleValue = prefs.getLong(PREF_PREFIX_KEY + appWidgetId+ getCurrentDateinLong(0), 0);
        if (titleValue >0 ) {
            Log.d("L_titleValue",String.valueOf(titleValue));
            return titleValue;
        } else {
            return 0;
        }
    }

    static void deleteBackGroundPref(Context context, int appWidgetId) {
        try {
            SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
            prefs.remove(PREF_PREFIX_KEY + appWidgetId+ getCurrentDateinLong(-7));
            prefs.apply();
        }
        catch (Exception e)
        {
            Log.i("Delete Pref",e.getMessage() );
        }
    }


    private static int getCurrentDateinLong(int offset)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date() );
        calendar.add(Calendar.DATE,offset);
        Log.i("getCurrentDateinLong ",String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)) );
        return calendar.get(Calendar.MILLISECOND);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
