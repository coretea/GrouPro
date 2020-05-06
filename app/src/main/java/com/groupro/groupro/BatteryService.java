package com.groupro.groupro;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

@SuppressLint("Registered")
public class BatteryService extends Service {

    BatteryChangeReceiver receiver = new BatteryChangeReceiver();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {

    }


    @Override
    public void onStart(Intent intent, int startid) {
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentfilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receiver, intentfilter);
    }
    @Override
    public void onDestroy() {
      unregisterReceiver(receiver);
    }


}
