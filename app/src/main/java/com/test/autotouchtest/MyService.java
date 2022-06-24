package com.test.autotouchtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.test.autotouchtest.MainActivity;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final String strId = getString(R.string.noti_channel_id);
            final String strTitle = getString(R.string.app_name);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = notificationManager.getNotificationChannel(strId);
            if (channel == null) {
                channel = new NotificationChannel(strId, strTitle, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }

            Notification notification = new NotificationCompat.Builder(this, strId).build();
            startForeground(1, notification);
        }
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    for(int i = 0; i <= 100; i++){
                        Thread.sleep(1000);
                        Log.d("test", "i: " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }




            }
        });
        thread.start();
        return START_STICKY;
    }
}