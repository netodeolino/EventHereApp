package com.neto.deolino.trabalhoandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.neto.deolino.trabalhoandroid.MainActivity;

/**
 * Created by matheus on 10/11/16.
 */

public class CreateAccountService extends Service {

    private static final String TAG = "CreateAccountService";

    public CreateAccountService getSelf(){
        return this;
    }

    public IBinder onBind (Intent arg0){
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "Serviço Iniciado!");
        new ClientThread().run();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class ClientThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                getSelf().stopSelf();
            }
        }
    }
}
