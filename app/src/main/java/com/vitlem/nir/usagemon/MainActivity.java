package com.vitlem.nir.usagemon;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button button;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE },
                MY_PERMISSIONS_REQUEST);
        */
        Intent intent = new Intent(context, ScreenMonService.class);
        startService(intent);
        button = (Button) findViewById(R.id.bRestart);
// add button listener
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        Log.i("Pass",userInput.getText().toString());
                                        if(userInput.getText().toString().equals("27321496"))
                                        {
                                            Log.i("PassOK","PassOK");
                                            MyBroadCastReciever.TimeUsage=0;
                                            MyBroadCastReciever.Timetemp= Calendar.getInstance().getTimeInMillis();
                                            saveUsageTime(context,mAppWidgetId,0);
                                            saveTempTime(context,mAppWidgetId,MyBroadCastReciever.Timetemp);
                                           UpdateText();
                                        };
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        UpdateText();
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });



        MyBroadCastReciever.TimeUsage=loadsaveUsageTime(this,mAppWidgetId);
        MyBroadCastReciever.Timetemp=loadsaveTempTime(this,mAppWidgetId);

        UpdateText();


    }

    protected  void OnDestroy(){
        super.onDestroy();
        Log.i("OnDestroy",(new SimpleDateFormat("mm:ss")).format(new Date(MyBroadCastReciever.TimeUsage)));
        saveUsageTime(this,mAppWidgetId,MyBroadCastReciever.TimeUsage);
        saveTempTime(this,mAppWidgetId,MyBroadCastReciever.Timetemp);

    }

    protected  void onStop(){
        super.onStop();
        Log.i("onStop",(new SimpleDateFormat("mm:ss")).format(new Date(MyBroadCastReciever.TimeUsage)));
        saveUsageTime(this,mAppWidgetId,MyBroadCastReciever.TimeUsage);
        saveTempTime(this,mAppWidgetId,MyBroadCastReciever.Timetemp);

    }

    protected void onResume () {
        super.onResume();
        Log.i("onResume",(new SimpleDateFormat("mm:ss")).format(new Date(MyBroadCastReciever.TimeUsage)));
        UpdateText();
}

    protected void onRestart () {
        super.onRestart();

        UpdateText();

    }

    private void UpdateText()
    {
        TextView tv = findViewById(R.id.UsageText);
        //if (MyBroadCastReciever.Timetemp>0)
        MyBroadCastReciever.TimeUsage += (Calendar.getInstance().getTimeInMillis()-MyBroadCastReciever.Timetemp);
        MyBroadCastReciever.Timetemp = Calendar.getInstance().getTimeInMillis();
        saveUsageTime(this,mAppWidgetId,MyBroadCastReciever.TimeUsage);
        saveTempTime(this,mAppWidgetId,MyBroadCastReciever.Timetemp);
        String t =( new SimpleDateFormat("mm:ss")).format(new Date(MyBroadCastReciever.TimeUsage));
        tv.setText(t);
        Log.i("UpdateText",(new SimpleDateFormat("mm:ss")).format(new Date(MyBroadCastReciever.TimeUsage)));
    }


    // Write the prefix to the SharedPreferences object for this widget
    static void saveUsageTime(Context context, int appWidgetId, long text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putLong(PREF_PREFIX_KEY + appWidgetId+ getCurrentDateinLong(0), text);
        prefs.apply();
        Log.i("saveUsageTime",(new SimpleDateFormat("mm:ss")).format(new Date(text)));
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static long loadsaveUsageTime(Context context, int appWidgetId) {
        //Log.d("getCurrentDateinLong ",String.valueOf(getCurrentDateinLong(0)));
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        Long titleValue = prefs.getLong(PREF_PREFIX_KEY + appWidgetId+ getCurrentDateinLong(0), 0);
        if (titleValue >0 ) {
            Log.i("loadsaveUsageTime",(new SimpleDateFormat("mm:ss")).format(new Date(titleValue)));
            return titleValue;
        } else {
            return 0;
        }
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTempTime(Context context, int appWidgetId, long text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putLong(PREF_PREFIX_KEY + appWidgetId+ getCurrentDateinLong(0)+1000, text);
        prefs.apply();
        Log.i("saveTempTime",(new SimpleDateFormat("mm:ss")).format(new Date(text)));
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static long loadsaveTempTime(Context context, int appWidgetId) {
        //Log.d("getCurrentDateinLong ",String.valueOf(getCurrentDateinLong(0)));
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        Long titleValue = prefs.getLong(PREF_PREFIX_KEY + appWidgetId+ getCurrentDateinLong(0)+1000, 0);
        if (titleValue >0 ) {
            Log.i("loadsaveTempTime",(new SimpleDateFormat("mm:ss")).format(new Date(titleValue)));
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
            Log.i("Delete Pref",String.valueOf(getCurrentDateinLong(-7)) );
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
