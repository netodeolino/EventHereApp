package com.neto.deolino.trabalhoandroid.service.local;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.activies.MainActivity;

import java.util.Random;

/**
 * Created by deolino on 13/11/16.
 */
public class FriendsRequestServices extends Service {

    private static final String TAG = "Services";
    private boolean running;
    private int count = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Service start");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"Find Events");

        running = true;

        new ClassFriendsThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    class ClassFriendsThread extends Thread {
        @Override
        public void run() {
            try {
                while (running && count < 10){
                    Thread.sleep( 500 + new Random( System.currentTimeMillis() ).nextInt( 500 )  );
                    Log.d(TAG, "Loading friend requests");
                    count++;
                }
                if (count == 10){
                    showNotification("Event Here", "Hi, you have friends requests!", 0, 1);
                }
            }catch (InterruptedException e){
                Log.d(TAG, "Interrupted: " + e.toString());
            }finally {
                stopSelf();

            }
            super.run();
        }
    }

    @Override
    public void onDestroy() {
        running = false;
        Log.d(TAG, "Service destroyed");
    }

    public void showNotification(String title, String content, int id, int tipoNotificacao) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title).setContentText(content).setAutoCancel(true);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        if (tipoNotificacao == 1) {
            mBuilder.setSmallIcon(R.drawable.ic_action_add_friends);

            Intent resultIntent = new Intent(this, MainActivity.class);

            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
        }

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);

        long[] vibrate = {0,100,200,300};

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setVibrate(vibrate);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }
}
