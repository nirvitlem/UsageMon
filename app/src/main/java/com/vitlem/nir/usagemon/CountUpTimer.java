package com.vitlem.nir.usagemon;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

/**
 * Simple timer class which count up until stopped.
 * Inspired by {@link android.os.CountDownTimer}
 */
public abstract class CountUpTimer {

    private final long interval;
    private long base;

    public CountUpTimer(long interval) {
        this.interval = interval;
    }

    public void start() {
        base = SystemClock.elapsedRealtime();
        handler.sendMessage(handler.obtainMessage(MSG));
        Log.i("start ", "start");
    }

    public void startat(long t) {
        Log.i("Startat stytem ", Long.valueOf(SystemClock.elapsedRealtime()).toString());
        Log.i("Startat t ", Long.valueOf(t*1000).toString());
        base = SystemClock.elapsedRealtime()- (t*1000);
        Log.i("Startat base ", Long.valueOf(base).toString());
        handler.sendMessage(handler.obtainMessage(MSG));
    }

    public void stop() {
        handler.removeMessages(MSG);
    }

    public long getTimer()
    {
        Log.i("getTimer stytem ", Long.valueOf(SystemClock.elapsedRealtime()).toString());
        Log.i("getTimer base ", Long.valueOf(base).toString());
        return  (SystemClock.elapsedRealtime() - base)/1000;
    }

    public void reset() {
        synchronized (this) {
            base = SystemClock.elapsedRealtime();
        }
    }

    abstract public void onTick(long elapsedTime);

    private static final int MSG = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (CountUpTimer.this) {
                long elapsedTime = SystemClock.elapsedRealtime() - base;
                onTick(elapsedTime);
                sendMessageDelayed(obtainMessage(MSG), interval);
            }
        }
    };
}