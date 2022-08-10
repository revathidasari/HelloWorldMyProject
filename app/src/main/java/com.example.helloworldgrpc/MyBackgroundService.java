package com.example.helloworldgrpc;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyBackgroundService extends Service {
    Runnable runnable = null;
    Handler handler = null;

    public MyBackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"service created", Toast.LENGTH_SHORT).show();
        handler = new Handler();
        /*runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyBackgroundService.this,"Service is ongoing",Toast.LENGTH_SHORT).show();

                handler.postDelayed(runnable,1000);
            }
        };
        handler.postDelayed(runnable,1500);*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // to remove notification
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //            stopForeground(STOP_FOREGROUND_REMOVE);
        //        }

        // to close/stop the service on app close
        //handler.removeCallbacks(runnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //to start as foreground service
        startAsAForegroundService();

        //the system will try to re-create your service after it is killed
        return START_STICKY;
        //the system will not try to re-create your service after it is killed
        //return START_NOT_STICKY;
    }

    public void startAsAForegroundService() {
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        //launching foreground services from API 26+
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = "com.example.helloworldgrpc";
            String channelName = "My Background Service";
            NotificationChannel channel = null;
            channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            channel.setLightColor(Color.BLUE);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.ic_menu_camera)
                    .setContentTitle("Notification Check")
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setContentIntent(pendingIntent)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            //Expandable notification
/*            Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera)
            .setLargeIcon(imgBitmap)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(imgBitmap).bigLargeIcon(null))
            .addAction(0, "Act on click", pendingIntent) */
            //Notifying the notification
/*            NotificationManagerCompat.from(this).notify(1,notification);*/

            startForeground(2, notification);

            // Intent dialogIntent = new Intent(this, Adscreen.class);
            //            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //            startActivity(dialogIntent);
            //            Log.d("sk", "After startforeground executed");
        } else {
            notificationIntent = new Intent(this, MainActivity.class);
            pendingIntent =
                    PendingIntent.getActivity(this, 0, notificationIntent, 0);

            notification =
                    new Notification.Builder(this)
                            .setContentTitle("Notification check 1")
                            .setContentText("checking ...")
                            .setSmallIcon(R.drawable.ic_menu_camera)
                            .setContentIntent(pendingIntent)
                            .setTicker("")
                            .build();

            startForeground(2, notification);

            // Intent dialogIntent = new Intent(this, MainActivity.class);
            //            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //            startActivity(dialogIntent);
        }
    }
}