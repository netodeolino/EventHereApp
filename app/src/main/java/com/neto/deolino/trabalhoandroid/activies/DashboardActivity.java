package com.neto.deolino.trabalhoandroid.activies;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.adapters.EventAdapter2;
import com.neto.deolino.trabalhoandroid.dao.UserDAO;
import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.User;
import com.neto.deolino.trabalhoandroid.service.web.EventService;
import com.neto.deolino.trabalhoandroid.service.web.Server;
import com.neto.deolino.trabalhoandroid.util.async.PostExecute;

import java.util.ArrayList;


/**
 * Created by deolino on 22/10/16.
 */
public class DashboardActivity extends AppCompatActivity {

    protected static final String TAG = "DashboardActivity";
    User user;
    ListView recentEventsListView;
    Context context;
    int eventID;
    final ArrayList<Event> arrayOfEvents = new ArrayList<>();
    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recentEventsListView = (ListView) findViewById(R.id.lvRecentEvents);

        UserDAO dao = new UserDAO(this);
        this.user = dao.findById(PreferenceManager.getDefaultSharedPreferences(this).getInt("user_id", 0));
        dao.close();

        context = this;

        // When an item in the list is clicked
        recentEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DashboardActivity", "Item " + position + " clicked");

                eventID = arrayOfEvents.get(position).getId();
                Intent intent = new Intent(context, EventDescriptionActivity.class);
                intent.putExtra("eventID", eventID);
                startActivity(intent);
            }
        });
//        populateEventsList();
    }

    private void populateEventsList() {
        //Construct data source
        arrayOfEvents.clear();
        new EventService().findNearbyEvents(PreferenceManager.getDefaultSharedPreferences(this).getInt("user_id", 0), com.neto.deolino.trabalhoandroid.model.Location.getLocationFromAndroidLocation(mLastLocation, context), arrayOfEvents, new PostExecute() {
            @Override
            public void postExecute(int option) {
                if (Server.RESPONSE_CODE == Server.RESPONSE_OK) {

                    Log.d("DashboardActivity", arrayOfEvents.size()+"");

                    //Create the adapter to convert the array to views
                    EventAdapter2 adapter = new EventAdapter2(getApplicationContext(), arrayOfEvents);
                    //recentEventsListView = (ListView) findViewById(R.id.lvRecentEvents);
                    //attach the adapter to the listview
                    recentEventsListView.setAdapter(adapter);

                    //ok
                    Log.d("DashboardActivity", "Event searched location based!");


                } else {
                    //not ok
                    Log.d("DashboardActivity", "Error searching event location based!");
                    Toast.makeText(context, R.string.error_searching_event, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void populateAllEventsList() {
        //Construct data source
        arrayOfEvents.clear();
        new EventService().findAvailable(PreferenceManager.getDefaultSharedPreferences(this).getInt("user_id", 0),arrayOfEvents, new PostExecute() {
            @Override
            public void postExecute(int option) {
                if (Server.RESPONSE_CODE == Server.RESPONSE_OK) {
                    //Create the adapter to convert the array to views
                    EventAdapter2 adapter = new EventAdapter2(getApplicationContext(), arrayOfEvents);
                    //recentEventsListView = (ListView) findViewById(R.id.lvRecentEvents);
                    //attach the adapter to the listview
                    recentEventsListView.setAdapter(adapter);

                    //ok
                    Log.d("DashboardActivity", "All Events searched!");


                } else {
                    //not ok
                    Log.d("DashboardActivity", "Error searching all events!");
                    Toast.makeText(context, R.string.error_searching_event, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void dashboardButtonClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_create_route:
                intent = new Intent(this, CreateEventActivity.class);
                break;
            case R.id.btn_search_route:
                intent = new Intent(this, SearchEventActivity.class);
                break;
        }
        startActivity(intent);
    }

    /*
        This method is executed when the activity is created to populate the ActionBar with actions
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        menu.findItem(R.id.menuMyAccount).setVisible(true);
        //if (user.getImage() != null) /* User getImage está null */
        //    menu.findItem(R.id.menuMyAccount).setIcon(new BitmapDrawable(getResources(), user.getImage()));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuMyAccount:
                intent = new Intent(this, UserDerscriptionActivity.class);
                startActivity(intent);
                break;
            case R.id.menuMyEvents:
                startActivity(new Intent(this, MyEventsActivity.class));
                break;
            case R.id.menuSettings:
                startActivity(new Intent(this, MySettingsActivity.class));
                break;
            case R.id.menuLogOut:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.menuCredits:
                intent = new Intent(this, CreditActivity.class);
                startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
