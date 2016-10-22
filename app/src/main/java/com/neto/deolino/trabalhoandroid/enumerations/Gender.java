package com.neto.deolino.trabalhoandroid.enumerations;

/**
 * Created by deolino on 22/10/16.
 */
public enum Gender {

    MALE, FEMALE, UNINFORMED;

    public static Gender getGender(String gender){
        if(gender.equals("MALE")) return MALE;
        if(gender.equals("FEMALE")) return FEMALE;
        return UNINFORMED;
    }
}
