package com.groupro.groupro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * this Receiver class indicates the user if power is connected or not and if the battery is low.
 */
public class BatteryChangeReceiver extends BroadcastReceiver {


    /**
     *
     * @param context
     * @param intent
     * this function waits to receive a broadcast indicating the power status
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) {
            Toast.makeText(context, "Low Battery! Please Charge.", Toast.LENGTH_SHORT).show();
        }
        if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())) {
            Toast.makeText(context, "Power Connected :)", Toast.LENGTH_SHORT).show();
        }
        if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
            Toast.makeText(context, "Power Disconnected :(", Toast.LENGTH_SHORT).show();
        }
    }
}
