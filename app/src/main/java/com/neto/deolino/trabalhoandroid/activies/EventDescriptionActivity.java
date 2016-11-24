package com.neto.deolino.trabalhoandroid.activies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.adapters.PersonAdapter;
import com.neto.deolino.trabalhoandroid.dao.EventDAO;
import com.neto.deolino.trabalhoandroid.interfaces.AsyncExecutable;
import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.EventType;
import com.neto.deolino.trabalhoandroid.model.User;
import com.neto.deolino.trabalhoandroid.service.web.EventService;
import com.neto.deolino.trabalhoandroid.service.web.Server;
import com.neto.deolino.trabalhoandroid.util.DateHelper;
import com.neto.deolino.trabalhoandroid.util.async.PostExecute;

import java.util.ArrayList;

/**
 * Created by deolino on 27/10/16.
 */
public class EventDescriptionActivity extends AppCompatActivity {

    User user;
    int userID;

    ListView personsParticipatingListView;
    TextView tvEventDate;
    TextView tvEventTime;
    ImageView ivEventType;
    TextView tvEventStart;
    TextView tvEventEnd;
    TextView tvEventDescription;

    Context context;
    Intent intent;

    Event event;
    int eventID;

    ProgressBar pbEventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);

        context = this;
        intent = getIntent();
        //get from intent
        eventID = intent.getIntExtra("eventID", 0 );
        event = new Event();

        new EventService().findById(eventID, event, new PostExecute() {
            @Override
            public void postExecute(int option) {
                personsParticipatingListView = (ListView) findViewById(R.id.lvPersonsInEvent);
                tvEventDate = (TextView) findViewById(R.id.tvEventTime);
                tvEventTime = (TextView) findViewById(R.id.tvEventDate);
                ivEventType = (ImageView) findViewById(R.id.ivEventType);
                tvEventStart = (TextView) findViewById(R.id.tvEventStart);
                tvEventEnd = (TextView) findViewById(R.id.tvEventEnd);
                tvEventDescription = (TextView) findViewById(R.id.tvEventDescription);
                tvEventDescription.setMovementMethod(new ScrollingMovementMethod());
                populatePersonsList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        event = new Event();

        new EventService().findById(eventID, event, new PostExecute() {
            @Override
            public void postExecute(int option) {
                if(Server.RESPONSE_CODE == Server.RESPONSE_OK){
                    //ok
                    tvEventDate.setText(DateHelper.dateToString(event.getDate()));
                    tvEventTime.setText(DateHelper.timeToString(event.getDate()));
                    EventType.Type type = event.getType().getType();
                    int img = (type==EventType.Type.HIKE) ? R.drawable.hike : (type==EventType.Type.RUN) ? R.drawable.running : R.drawable.bike;
                    ivEventType.setImageResource(img);
                    tvEventStart.setText(event.getDeparture().getAddress());
                    tvEventEnd.setText(event.getArrival().getAddress());
                    tvEventDescription.setText(event.getDescription());
                } else{
                    //not ok
                    Log.d("EDescriptionActivity", "Error searching event!");
                    Toast.makeText(context, R.string.error_searching_event, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void populatePersonsList(){
        EventService service = new EventService();

        service.findConfirmedUsers(event, new AsyncExecutable() {
            @Override
            public void postExecute(int option) {
                //pbEventDescription.setVisibility(View.GONE);
                User organizer = event.getOrganizer();

                //Construct data source
                ArrayList<User> participants = new ArrayList<>();
                ArrayList<User> arrayOfUsers = event.getConfirmedUsers();

                // add organizer first to list
                participants.add(organizer);
                arrayOfUsers.remove(organizer);

                Log.d("EventDescriptionActivit", organizer.getName());

                // if user is attending add user second
                if (event.getConfirmedUsers().contains(user) && user.equals(organizer)) {
                    participants.add(1, user);
                    arrayOfUsers.remove(user);
                }
                // add rest of participants
                if (arrayOfUsers.size() > 0) {
                    participants.addAll(arrayOfUsers);
                }

                //Create the adapter to convert the array to views
                PersonAdapter adapter = new PersonAdapter(getApplicationContext(), event.getConfirmedUsers(), PersonAdapter.LIST_USERS_EVENT, event.getOrganizer().getId(), eventID);
                //attach the adapter to the listview
                personsParticipatingListView.setAdapter(adapter);
            }

            @Override
            public void preExecute(int option) {
                //pbEventDescription.setVisibility(View.VISIBLE);
            }
        });
    }

    /*
This method is executed when the activity is created to populate the ActionBar with actions
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (event.getConfirmedUsers().contains(user)) {
            MenuItem participate = menu.findItem(R.id.menuParticipate);
            if (participate != null) {
                participate.setVisible(false);
            }
        }
        if (event.isSecret() && !user.equals(event.getOrganizer())) {
            MenuItem invite = menu.findItem(R.id.menuInvite);
            if (invite != null) {
                invite.setVisible(false);
            }
        }
        getMenuInflater().inflate(R.menu.event_desc_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuInvite:
                Log.d("EvenDescriptionActivity", "Invite Pressed!");
                intent = new Intent(context, InviteActivity.class);
                intent.putExtra("eventID", eventID );
                startActivity(intent);
                return true;

            case R.id.menuShowRoute:
                Log.d("EvenDescriptionActivity", "Show Route Pressed!");
                return true;

            case R.id.menuParticipate:
                Log.d("EvenDescriptionActivity", "Participate Pressed!");
                EventService service = new EventService();
                service.confirmAttendance(eventID, userID, new AsyncExecutable() {
                    @Override
                    public void postExecute(int option) {
                        Toast.makeText(context, R.string.participated, Toast.LENGTH_SHORT).show();
                        findViewById(R.id.menuParticipate).setVisibility(View.INVISIBLE);
                        populatePersonsList();
                    }

                    @Override
                    public void preExecute(int option) {

                    }
                });
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void eventDescMapButtonPressed(View view){
        Log.d("EvenDescriptionActivity", "Map Button Pressed!");

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("eventID", eventID );
        startActivity(intent);
    }
}
