package com.neto.deolino.trabalhoandroid.dao;

import android.content.Context;

import com.neto.deolino.trabalhoandroid.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deolino on 05/11/16.
 */
public class UserDAO {

    private Context context;
    List<User> usersTemp = new ArrayList<User>();

    public UserDAO(){
    }

    public UserDAO(Context context){
        this.context = context;
    }

    public void insert(User user) {
        usersTemp.add(user);
    }

    public void update(User user) {
        int index = usersTemp.indexOf(user);
        if(index >= 0){
            usersTemp.remove(index);
            usersTemp.add(user);
        }
    }

    public void remove(int id) {
        User bye = new User();
        bye.setId(id);
        int index = usersTemp.indexOf(bye);
        if(index >= 0){
            usersTemp.remove(index);
        }
    }

    public User findById(int id) {
        User bye = new User();
        bye.setId(id);

        int index = usersTemp.indexOf(bye);

        if(index >= 0){
            return usersTemp.get(index);
        }
        return null;
    }

    public List<User> listAll(){
        return usersTemp;
    }
}
