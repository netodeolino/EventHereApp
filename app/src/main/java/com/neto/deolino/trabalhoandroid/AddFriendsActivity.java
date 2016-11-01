package com.neto.deolino.trabalhoandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.neto.deolino.trabalhoandroid.model.User;

import java.util.ArrayList;

/**
 * Created by deolino on 30/10/16.
 */
public class AddFriendsActivity extends AppCompatActivity {

    ListView friendsSearchListView;
    Context context;
    EditText etSearchFriend;
    User user;
    ProgressBar pbAddFriends;
    ArrayList<User> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        context = this;
        friendsSearchListView = (ListView) findViewById(R.id.lvFriendsSearch);
        etSearchFriend = (EditText) findViewById(R.id.etSearchFriend);
    }

    public void searchFriends(View view) {

    }
}
