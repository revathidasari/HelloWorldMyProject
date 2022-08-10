package com.example.helloworldgrpc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LaunchReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            Toast.makeText(context, "received   1", Toast.LENGTH_SHORT).show();
            String res = getResultData();
            String num = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            if (res!= null) {
                if (res.contains("#12345#")) {
                    setResultData(null);
                    Intent i = new Intent(context,MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                    Toast.makeText(context.getApplicationContext(), "triggered    1", Toast.LENGTH_SHORT).show();
                }
            }
            if (num.equals("96485")) {
                setResultData(null);
                Intent i = new Intent(context,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                Toast.makeText(context, "triggered num    1", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
