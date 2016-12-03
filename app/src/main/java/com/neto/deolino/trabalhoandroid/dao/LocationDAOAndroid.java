package com.neto.deolino.trabalhoandroid.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by deolino on 02/12/16.
 */
public class LocationDAOAndroid extends AbstractDAO<Location> {

    public LocationDAOAndroid(Context context) {
        super(context);
    }

    @Override
    public void insert(Location location) {

    }

    @Override
    public void update(Location location) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Location findById(int id) {
        return null;
    }

    @Override
    public ArrayList<Location> findAll() {
        return null;
    }

    public ArrayList<Location> find(){
        ArrayList<Location> locations = new ArrayList<>();
        SQLiteDatabase database = this.mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select latitude, longitude from location", null);
        Log.i("CURSOR", ""+cursor.toString());
        while(cursor.moveToNext()){
            Location location = new Location("");
            location.setLatitude(cursor.getFloat(0));
            location.setLongitude(cursor.getFloat(1));
            locations.add(location);
        }
        cursor.close();
        database.close();
        return locations;
    }
}
