package com.neto.deolino.trabalhoandroid.dao;

import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deolino on 05/11/16.
 */
public class EventDAO {

    List<Event> eventsTemp = new ArrayList<Event>();

    public EventDAO(){

    }

    public void insert(Event event) {
        eventsTemp.add(event);
    }

    public void update(Event event) {
        int index = eventsTemp.indexOf(event);
        if(index >= 0){
            eventsTemp.remove(index);
            eventsTemp.add(event);
        }
    }

    public void remove(int id) {
        Event bye = new Event();
        bye.setId(id);

        int index = eventsTemp.indexOf(bye);
        if(index >= 0){
            eventsTemp.remove(index);
        }
    }

    public Event findById(int id) {
        Event bye = new Event();
        bye.setId(id);

        int index = eventsTemp.indexOf(bye);
        if(index >= 0){
            return eventsTemp.get(index);
        }
        return null;
    }

    public List<Event> listAll(){
        return eventsTemp;
    }
}
