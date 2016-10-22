package com.neto.deolino.trabalhoandroid.util.pojo;

/**
 * Created by deolino on 22/10/16.
 */
public class IntPojo extends AbstractPOJO {

    private Integer[] ints;

    public IntPojo(int tam){
        this.ints = new Integer[tam];
    }

    public IntPojo(Integer ints[]){
        this.ints = ints;
    }

    public IntPojo(){}

    public IntPojo(Integer i){
        this.ints = new Integer[1];
        this.ints[0] = i;
    }

    public Integer getInt(){
        if(this.ints==null || this.ints.length<1) return 0;
        return this.ints[0];
    }

    public Integer[] getInts() {
        return ints;
    }

    public void setInts(Integer[] ints) {
        this.ints = ints;
    }
}
