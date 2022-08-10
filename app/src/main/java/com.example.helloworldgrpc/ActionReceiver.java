package com.example.helloworldgrpc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class ActionReceiver extends BroadcastReceiver {

    private static final String TAG = ActionReceiver.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (intent.getAction().equals("android.intent.action.AIRPLANE_MODE")){
            Toast.makeText(context, "airplane", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setPackage("com.android.dialer");
            context.startActivity(i);
        } else {
            Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
                Log.d(TAG,"on received");
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
                Log.d(TAG,"off received");
            }
            //idle mode = doze mode
            if (intent.getAction().equals(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED)) {
                Log.d(TAG,"idle mode change action received");
                if (powerManager !=null) {
                    Log.d(TAG,"current idle mode state : "+ powerManager.isDeviceIdleMode()
                    +" inter "+powerManager.isInteractive()+" power "+powerManager.isPowerSaveMode());
                }
            }
        }
    }
}
