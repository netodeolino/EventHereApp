package com.neto.deolino.trabalhoandroid.activies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.User;

import java.util.ArrayList;

/**
 * Created by deolino on 27/10/16.
 */
public class InviteActivity extends AppCompatActivity {

    ListView inviteListView;
    Context context;
    EditText etSearchUser;
    User user;
    int eventID;
    ProgressBar pbInvite;
    ArrayList<User> searchResults;
    ImageButton ib;
    ViewGroup viewGroup;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        context = this;
        inviteListView = (ListView) findViewById(R.id.lvInviteSearch);
        etSearchUser = (EditText) findViewById(R.id.etSearchUser);
        eventID = getIntent().getIntExtra("eventID", 0);
        pbInvite = (ProgressBar) findViewById(R.id.pbInvite);
        searchResults = new ArrayList<>();
        viewGroup = (ViewGroup) findViewById(R.id.list_item_person);

        event = new Event();
    }
}
