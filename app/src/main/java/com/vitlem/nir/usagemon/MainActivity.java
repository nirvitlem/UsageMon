package com.vitlem.nir.usagemon;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public PendingIntent service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ScreenMonService.class);
        startService(intent);
        TextView tv = findViewById(R.id.UsageText);
        tv.setText(String.valueOf(MyBroadCastReciever.TimeUsage));
    }


}
