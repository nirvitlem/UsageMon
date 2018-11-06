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

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public final int MY_PERMISSIONS_REQUEST=1;
    public PendingIntent service = null;
    private static final String PREFS_NAME_Usage = "com.vitlem.nir.usagemon";
    private static final String PREF_PREFIX_KEY = "app_";
    public static int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Button button;
    final Context context = this;
    public static SimplePrefs sp;
    public static TextView tv;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.UsageText);
      //  ActivityCompat.requestPermissions(this,
      //          new String[]{Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE },
     //          MY_PERMISSIONS_REQUEST);

        Intent intent = new Intent(getApplicationContext(), ScreenMonService.class);
        startService(intent);
        Button b = (Button)findViewById(R.id.bUpdateTime) ;
        b.setOnClickListener(new View.OnClickListener() {

                                 @Override
                                 public void onClick(View arg0) {
                                     UpdateText();
                                 }
                             });


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
                                            ScreenMonService.t.reset();
                                            saveUsageTime(context,mAppWidgetId,ScreenMonService.t.getTimer());
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



        if (ScreenMonService.t !=null)  UpdateText();


    }

    protected  void OnDestroy(){
        super.onDestroy();
        Log.i("OnDestroy", Long.valueOf(ScreenMonService.t.getTimer()).toString());

        saveUsageTime(this,mAppWidgetId,ScreenMonService.t.getTimer());

    }

    protected  void onStop(){
        super.onStop();
        Log.i("onStop",Long.valueOf(ScreenMonService.t.getTimer()).toString());

        saveUsageTime(this,mAppWidgetId,ScreenMonService.t.getTimer());
    }

    protected void onResume () {
        super.onResume();

      if (ScreenMonService.t !=null)  UpdateText();
}

    protected void onRestart () {
        super.onRestart();

         UpdateText();

    }

    private static void UpdateText()
    {
        //loadsaveUsageTime(context,mAppWidgetId);
        long te = ScreenMonService.t.getTimer();
        long p1 = te % 60;
        long p2 = te / 60;
        long p3 = p2 % 60;

        p2 = p2 / 60;

        tv.setText(" " +p2 + ":" + p3 + ":" + p1);
        Log.i("UpdateText", Long.valueOf(ScreenMonService.t.getTimer()).toString());
    }


    // Write the prefix to the SharedPreferences object for this widget
    static void saveUsageTime(Context context, int appWidgetId, long text) {

        SimplePrefs.putLong(PREF_PREFIX_KEY ,text);
        Log.i("saveUsageTime", Long.valueOf(text).toString());
       // SimplePrefs.remove(PREF_PREFIX_KEY + getCurrentDateinLong(-1));
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static long loadsaveUsageTime(Context context, int appWidgetId) {
        Long titleValue  = SimplePrefs.getLong(PREF_PREFIX_KEY);
        if (titleValue >0 ) {
            Log.i("loadsaveUsageTime", Long.valueOf(titleValue).toString());
            return titleValue;
        } else {
            return 0;
        }
    }

    static void deleteBackGroundPref(Context context, int appWidgetId,String name) {
        try {
            Log.i("Delete Pref", "deleteBackGroundPref");
            SharedPreferences sharedPrefs = context.getSharedPreferences(name, MODE_PRIVATE);
            if (sharedPrefs.contains(name)) {
                SharedPreferences.Editor prefs = context.getSharedPreferences(name, 0).edit();
                prefs.remove(PREF_PREFIX_KEY + appWidgetId + getCurrentDateinLong(-1));
                prefs.apply();
                Log.i("Delete Pref",  Long.valueOf(ScreenMonService.t.getTimer()).toString());
            }
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
