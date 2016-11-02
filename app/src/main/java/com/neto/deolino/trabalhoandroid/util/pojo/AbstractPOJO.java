package com.neto.deolino.trabalhoandroid.util.pojo;

import com.google.gson.GsonBuilder;

import com.neto.deolino.trabalhoandroid.interfaces.Enviable;

/**
 * Created by deolino on 22/10/16.
 */
public class AbstractPOJO implements Enviable {
    @Override
    public String toJson(){
        return new GsonBuilder().create().toJson(this);
    }
}
