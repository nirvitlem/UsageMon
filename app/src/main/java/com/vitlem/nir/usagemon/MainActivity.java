package com.vitlem.nir.usagemon;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public PendingIntent service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ScreenMonService.class);
        startService(intent);
        UpdateText();
    }


    protected void onResume () {
        super.onResume();

        UpdateText();
}

    protected void onRestart () {
        super.onRestart();
        UpdateText();

    }

    private void UpdateText()
    {
        TextView tv = findViewById(R.id.UsageText);
        if (MyBroadCastReciever.Timetemp>0) MyBroadCastReciever.TimeUsage += (Calendar.getInstance().getTimeInMillis()-MyBroadCastReciever.Timetemp);
        MyBroadCastReciever.Timetemp = Calendar.getInstance().getTimeInMillis();
        String t =( new SimpleDateFormat("mm:ss")).format(new Date(MyBroadCastReciever.TimeUsage));
        tv.setText(t);
    }
}
