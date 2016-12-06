package com.neto.deolino.trabalhoandroid.service.local;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.activies.MainActivity;
import com.neto.deolino.trabalhoandroid.dao.LocationDAOAndroid;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
/**
 * Created by juniorf on 05/11/16.
 */
public class servicemap extends Service {
    public List<Worker> threads = new ArrayList<Worker>();
    private NotificationManager mNM;
    public List<Location> locationsMarkers;
    Bundle b;
    Intent notificationIntent;
    Location myLocation;
    Notification notf;private LocationListener listener;
    private LocationManager locationManager;
    private GoogleApiClient apiClient;
    private LocationDAOAndroid dao;
    private final IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        servicemap getService() {
            return servicemap.this;
        }
    }
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public void onCreate() {
        myLocation = new Location("");
        locationsMarkers = new ArrayList<Location>();
        dao = new LocationDAOAndroid(this);
        if(locationsMarkers!=null) {
            locationsMarkers = dao.find();
            for (int i = 0; i < locationsMarkers.size(); i++) {
                Log.i("kkkkk", "" + locationsMarkers.get(i).getLatitude() + " + " +
                        locationsMarkers.get(i).getLongitude());
            }
        }
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                myLocation=location;
                locationsMarkers.add(location);
                Log.i("kkkLOCATION", "_"+myLocation.toString());
                i.putExtra("coordinates",location.getLatitude()+" "+location.getLongitude());
                sendBroadcast(i);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        locationManager = (LocationManager)
                getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
// TODO: Consider calling
// ActivityCompat#requestPermissions
// here to request the missing permissions, and then overriding
// public void onRequestPermissionsResult(int requestCode, String[] permissions,
//
            //int[] grantResults
// to handle the case where the user grants the permission. See the documentation
// for ActivityCompat#requestPermissions for more details.
            return;
        }
/*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0,
listener);*/
        Toast.makeText(this, LocationManager.GPS_PROVIDER, Toast.LENGTH_SHORT).show();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0,
                listener);
        super.onCreate();
    }
    public void verificaDistancia(Location location){
        Location targetLocation = new Location("");//provider name is unecessary
        targetLocation.setLatitude(0.0d);//your coords of course
        targetLocation.setLongitude(0.0d);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationsMarkers = new ArrayList<Location>();
        dao = new LocationDAOAndroid(this);
        if(locationsMarkers!=null) {
            locationsMarkers = dao.find();
            for (int i = 0; i < locationsMarkers.size(); i++) {
                Log.i("kkkkk", "" + locationsMarkers.get(i).getLatitude() + " + " +
                        locationsMarkers.get(i).getLongitude());
                Log.i("DISTANCIA my", locationsMarkers.get(i).distanceTo(myLocation)+"");
            }
        }
        String n = "";
        Worker worker = new Worker(startId, n);
        worker.start();threads.add(worker);
        return (super.onStartCommand(intent, flags, startId));
    }
    class Worker extends Thread {
        public int count = 0;
        public int startId;
        public boolean ativo = true;
        public String ns="";
        public Worker(int startId, String n) {
            this.ns = n;
            this.startId = startId;
        }
        public void run() {
            while (ativo && count < 10) {
                for(Location l : locationsMarkers) {
                    if (myLocation.distanceTo(l) <= 100000) {
                        String sss = String.valueOf(l.distanceTo(myLocation));
                        Intent i = new Intent("RECEIVER");
//Log.i("MULOCATION", myLocation.toString()+"");
                        Log.i("DISTANCIAAA", "" + sss);
                        float d = Float.parseFloat(sss);
                        Bundle bundle = new Bundle();
                        bundle.putString("link", "BEM Pertooo" + l.getLatitude() + " - " +
                                l.getLongitude());
                        Log.i("DISTANCE", "BEM Pertooo" + l.getLatitude() + " - " + l.getLongitude());
                        i.putExtras(bundle);
                        sendBroadcast(i);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                if (count == 7) {
// showNotification("Você está próximo" ," O lugar de devolução 7 não está tão longe"0, 1);
                    Intent i = new Intent("RECEIVER");
                    Bundle bundle = new Bundle();
                    bundle.putString("link", "O lugar de devolução 7 não está tão longe");i.putExtras(bundle);
                    sendBroadcast(i);
                }
                Log.i("script", "COUNT: " + count);
            }
            stopSelf(startId);
        }
    }
    public void onDestroy() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
// TODO: Consider calling
// ActivityCompat#requestPermissions
// here to request the missing permissions, and then overriding
// public void onRequestPermissionsResult(int requestCode, String[] permissions,
//
                //int[] grantResults)
// to handle the case where the user grants the permission. See the documentation
// for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(listener);
        }
        super.onDestroy();
        for(int i = 0, tam = threads.size();i < tam; i++){
            threads.get(i).ativo = false;
        }
    }
    public void showNotification(String title, String content, int id, int tipoNotificacao) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        if (tipoNotificacao == 1) {
            mBuilder.setSmallIcon(R.drawable.ic_action_add_friends);
            Intent resultIntent = new Intent(this, MainActivity.class);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
        }
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
        long[] vibrate = {0,100,200,300};
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setVibrate(vibrate);
        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }
}