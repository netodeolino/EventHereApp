package com.neto.deolino.trabalhoandroid.activies;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.adapters.PersonAdapter;
import com.neto.deolino.trabalhoandroid.interfaces.AsyncExecutable;
import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.User;
import com.neto.deolino.trabalhoandroid.service.web.EventService;
import com.neto.deolino.trabalhoandroid.service.web.Server;
import com.neto.deolino.trabalhoandroid.service.web.UserService;

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

        new EventService().findById(eventID, event);

        // When an item in the list is clicked
        inviteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("InviteActivity", "Item " + position + " clicked");
                EventService es = new EventService();
                final User userInvited = (User) inviteListView.getAdapter().getItem(position);
                es.invite(eventID, userInvited.getId());
                if(Server.RESPONSE_CODE== UserService.ERROR_ALREADY_FRIENDS) {
                    Toast.makeText(InviteActivity.this, userInvited.getName() + " " + getString(R.string.already_invited), Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog show = new AlertDialog.Builder(context)
                            .setTitle(getString(R.string.invite))
                            .setMessage(getString(R.string.do_you_really_want_to_invite) + " " + userInvited.getName() + "?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Toast.makeText(InviteActivity.this, userInvited.getName() + " " + getString(R.string.invited), Toast.LENGTH_LONG).show();

                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });
    }

    public void btnSearchUsers(View view) {
        searchResults.clear();
        UserService us = new UserService();
        if (etSearchUser.getText().length() == 0) {
            Toast.makeText(InviteActivity.this, getString(R.string.search_field_empty), Toast.LENGTH_LONG).show();
        } else {
            us.findByMailOrName(etSearchUser.getText().toString(), searchResults, new AsyncExecutable() {
                @Override
                public void postExecute(int option) {
                    pbInvite.setVisibility(View.GONE);
                    if(Server.RESPONSE_CODE==UserService.ERROR_INCORRECT_DATA){
                        Toast.makeText(InviteActivity.this, getString(R.string.error_incorrect_search_input), Toast.LENGTH_LONG).show();
                    } else {
                        // set image button visible
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.list_item_person, viewGroup);
                        ib = (ImageButton) view.findViewById(R.id.ivInviteFriend);
                        ib.setVisibility(View.VISIBLE);

                        //Create the adapter to convert the array to views
                        PersonAdapter adapter = new PersonAdapter(context, searchResults, PersonAdapter.INVITE_FRIENDS, event.getOrganizer().getId(), eventID);
                        //attach the adapter to the listview
                        inviteListView.setAdapter(adapter);
                        //ib.setVisibility(View.GONE);
                    }

                    if(searchResults.isEmpty()) {
                        Toast.makeText(InviteActivity.this, getString(R.string.no_results_found), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void preExecute(int option) {
                    pbInvite.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
