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

import com.neto.deolino.trabalhoandroid.activies.MainActivity;
import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.util.NotificationUtil;

import java.util.Random;

/**
 * Created by deolino on 12/11/16.
 */
public class Services extends Service {

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

        new ClassThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    class ClassThread extends Thread {
        @Override
        public void run() {
            try {
                while (running && count < 25){
                    Thread.sleep( 500 + new Random( System.currentTimeMillis() ).nextInt( 500 )  );
                    Log.d(TAG, "Loading events");
                    count++;
                }
                if (count == 25){
                    Context context = Services.this;
                    Intent intent = new Intent(context, MainActivity.class);
                    NotificationUtil.create(context, intent, "Event Here", "Hi, you have a event!", 1);
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
}
