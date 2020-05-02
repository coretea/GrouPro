package com.example.groupro;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
