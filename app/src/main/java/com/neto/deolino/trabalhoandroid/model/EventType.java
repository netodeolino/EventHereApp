package com.neto.deolino.trabalhoandroid.model;

/**
 * Created by deolino on 22/10/16.
 */
public class EventType {

    public enum Type{BIKE, HIKE, RUN, OTHER,UNDEFINED}

    private int id;
    private String specification;
    private Type type;


    public EventType(){
        this(Type.UNDEFINED);
    }

    public EventType(Type type){
        this(0, type, EventType.getType(type));
    }

    public EventType(Type type, String specification){
        this(0, type, specification);
    }

    public EventType(int id, Type type, String specification){
        this.id = id;
        this.type = type;
        this.specification = specification;
    }


    public String getSpecification() {
        return specification;
    }
    public int getId(){
        return this.id;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
    public void setId(int id){
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "id=" + id +
                ", specification='" + specification + '\'' +
                ", type=" + type +
                '}';
    }

    public static Type getType(String type){
        if(type.equals("BIKE")) return Type.BIKE;
        if(type.equals("HIKE")) return Type.HIKE;
        if(type.equals("RUN")) return Type.RUN;
        if(type.equals("OTHER")) return Type.OTHER;
        return Type.UNDEFINED;
    }

    public static String getType(Type type){
        if(type.equals(Type.BIKE)) return "BIKE";
        if(type.equals(Type.HIKE)) return "HIKE";
        if(type.equals(Type.RUN)) return "RUN";
        if(type.equals(Type.OTHER)) return "OTHER";
        return "UNDEFINED";
    }
}
