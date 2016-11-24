package com.neto.deolino.trabalhoandroid.util;

import com.google.gson.GsonBuilder;
import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.User;
import com.neto.deolino.trabalhoandroid.util.pojo.EventPOJO;
import com.neto.deolino.trabalhoandroid.util.pojo.IntPojo;
import com.neto.deolino.trabalhoandroid.util.pojo.UserPOJO;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by deolino on 22/10/16.
 */
public class JsonParser {

    public static User toUser(InputStream in){
        UserPOJO u = new GsonBuilder().create().fromJson(new InputStreamReader(in), UserPOJO.class);
        return u.getUser();
    }

    public static Event toEvent(InputStream in){
        EventPOJO e =  new GsonBuilder().create().fromJson(new InputStreamReader(in), EventPOJO.class);
        return e.getEvent();

        /*try {
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while((s=br.readLine())!=null){
                Log.i("", s);
            }
            br.close();
            isr.close();
        } catch (IOException p) {
            p.printStackTrace();
        }

        return new Event();*/
    }

    public static int toInt(InputStream in){
        InputStreamReader is = new InputStreamReader(in);
        IntPojo i = new GsonBuilder().create().fromJson(is, IntPojo.class);
        return i.getInt();
    }

    public static Integer[] toInts(InputStream in){
        InputStreamReader is = new InputStreamReader(in);
        IntPojo i = new GsonBuilder().create().fromJson(is, IntPojo.class);
        return i.getInts();
    }

    public static User toUser(String json){
        UserPOJO u = new GsonBuilder().create().fromJson(json, UserPOJO.class);
        return u.getUser();
    }

    public static ArrayList<User> toUsers(InputStream in){
        InputStreamReader isr = new InputStreamReader(in);
        UserPOJO u =  new GsonBuilder().create().fromJson(isr, UserPOJO.class);
        return u.toUsers();
    }

    public static ArrayList<User> toUsers(String json){
        UserPOJO u =  new GsonBuilder().create().fromJson(json, UserPOJO.class);
        return u.toUsers();
    }

    public static Event toEvent(String json){
        EventPOJO e =  new GsonBuilder().create().fromJson(json, EventPOJO.class);
        return e.getEvent();
    }

    public static ArrayList<Event> toEvents(InputStream in){
        InputStreamReader is = new InputStreamReader(in);
        EventPOJO e =  new GsonBuilder().create().fromJson(is, EventPOJO.class);
        return e.toEvents();
    }

    public static ArrayList<Event> toEvents(String json){
        EventPOJO e =  new GsonBuilder().create().fromJson(json, EventPOJO.class);
        return e.toEvents();
    }
}
