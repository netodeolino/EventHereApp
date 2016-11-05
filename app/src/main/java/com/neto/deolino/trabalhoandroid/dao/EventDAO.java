package com.neto.deolino.trabalhoandroid.dao;

import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deolino on 05/11/16.
 */
public class EventDAO {

    List<Event> usersTemp = new ArrayList<Event>();

    public EventDAO(){

    }

    public void insert(Event event) {
        usersTemp.add(event);
    }

    public void update(Event event) {
        int index = usersTemp.indexOf(event);
        if(index >= 0){
            usersTemp.remove(index);
            usersTemp.add(event);
        }
    }

    public void remove(int id) {
        Event bye = new Event();
        bye.setId(id);
        int index = usersTemp.indexOf(bye);
        if(index >= 0){
            usersTemp.remove(index);
        }
    }

    public Event findById(int id) {
        Event bye = new Event();
        bye.setId(id);

        int index = usersTemp.indexOf(bye);

        if(index >= 0){
            return usersTemp.get(index);
        }
        return null;
    }
}
