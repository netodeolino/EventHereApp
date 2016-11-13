package com.neto.deolino.trabalhoandroid.activies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.adapters.EventAdapter2;
import com.neto.deolino.trabalhoandroid.dao.UserDAO;
import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.User;

import java.util.ArrayList;

public class MyEventsActivity extends AppCompatActivity {

    TabHost tabHost;
    ListView upcomingEventsListView;
    ListView recentEventsListView;
    ListView invitationsListView;

    ArrayList<Event> arrayOfEvents = new ArrayList<>();

    Context context;
    int eventID;

    String [] eventos1 = {"Evento 1", "Evento 2", "Evento 3"};
    String [] eventos2 = {"Evento 4", "Evento 5", "Evento 6"};
    String [] eventos3 = {"Evento 7", "Evento 8", "Evento 9"};
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        context = this;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec(("TAB1"));
        //Tab indicator specified as Label and Icon
        spec.setIndicator(getString(R.string.tabUpcomingEvents), getResources().getDrawable(R.mipmap.ic_launcher));
        spec.setContent(R.id.layoutUpcomingEvents);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(("TAB2"));

        //Tab indicator specified as Label and Icon
        spec.setIndicator(getString(R.string.tabRecentEvents), getResources().getDrawable(R.mipmap.ic_launcher));
        spec.setContent(R.id.layoutRecentEvents);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(("TAB3"));

        //Tab indicator specified as Label and Icon
        spec.setIndicator(getString(R.string.tabEventInvitations), getResources().getDrawable(R.mipmap.ic_launcher));
        spec.setContent(R.id.layoutInvitations);
        tabHost.addTab(spec);

        final ListView upcomingEventsListView = (ListView) findViewById(R.id.lvMyUpcomingEvents);
        final ListView recentEventsListView = (ListView) findViewById(R.id.lvMyRecentEvents);
        final ListView invitationsListView = (ListView) findViewById(R.id.lvMyInvitations);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, eventos1);
        upcomingEventsListView.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, eventos2);
        recentEventsListView.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, eventos3);
        invitationsListView.setAdapter(adapter3);

        upcomingEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemP = position;
                String itemV = (String) upcomingEventsListView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Position:" + itemP + "ListItem:" + itemV, Toast.LENGTH_LONG).show();
            }
        });

        recentEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemP = position;
                String itemV = (String) recentEventsListView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Position:" + itemP + "ListItem:" + itemV, Toast.LENGTH_LONG).show();
            }
        });

        invitationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemP = position;
                String itemV = (String) invitationsListView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Position:" + itemP + "ListItem:" + itemV, Toast.LENGTH_LONG).show();
            }
        });
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        context = this;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec(("TAB1"));
        //Tab indicator specified as Label and Icon
        spec.setIndicator(getString(R.string.tabUpcomingEvents), getResources().getDrawable(R.mipmap.ic_launcher));
        spec.setContent(R.id.layoutUpcomingEvents);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(("TAB2"));

        //Tab indicator specified as Label and Icon
        spec.setIndicator(getString(R.string.tabRecentEvents), getResources().getDrawable(R.mipmap.ic_launcher));
        spec.setContent(R.id.layoutRecentEvents);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(("TAB3"));

        //Tab indicator specified as Label and Icon
        spec.setIndicator(getString(R.string.tabEventInvitations), getResources().getDrawable(R.mipmap.ic_launcher));
        spec.setContent(R.id.layoutInvitations);
        tabHost.addTab(spec);

        upcomingEventsListView = (ListView) findViewById(R.id.lvMyUpcomingEvents);
        recentEventsListView = (ListView) findViewById(R.id.lvMyRecentEvents);
        invitationsListView = (ListView) findViewById(R.id.lvMyInvitations);

        upcomingEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MyEventsActivity", "Item " + position + " clicked");
                eventID = arrayOfEvents.get(position).getId();
                Intent intent = new Intent(context, EventDescriptionActivity.class);
                intent.putExtra("eventID", eventID);
                startActivity(intent);
            }
        });

        recentEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MyEventsActivity", "Item " + position + " clicked");
                eventID = arrayOfEvents.get(position).getId();
                Intent intent = new Intent(context, EventDescriptionActivity.class);
                intent.putExtra("eventID", eventID);
                startActivity(intent);
            }
        });

        invitationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MyEventsActivity", "Item " + position + " clicked");
                eventID = arrayOfEvents.get(position).getId();
                Intent intent = new Intent(context, EventDescriptionActivity.class);
                intent.putExtra("eventID", eventID);
                startActivity(intent);
            }
        });


        tabHost.setCurrentTab(0);

        final UserDAO dao = new UserDAO(context);
        final User user = dao.findById(prefs.getInt("user_id", 0));


        arrayOfEvents = user.getConfirmedEvents();

        Log.d("MyEventsActivity", arrayOfEvents.size()+"");
        //Create the adapter to convert the array to views
        EventAdapter2 adapter = new EventAdapter2(context, arrayOfEvents);
        recentEventsListView = (ListView) findViewById(R.id.lvMyUpcomingEvents);
        //attach the adapter to the listview
        recentEventsListView.setAdapter(adapter);
        //ok
        Log.d("MyEventsActivity", "Confirmed Events Searched");



        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                //upcoming events
                if (tabHost.getCurrentTab() == 0) {
                    Log.d("MyEventsActivity", "Tab 0 selected");

                    arrayOfEvents = user.getConfirmedEvents();

                    Log.d("MyEventsActivity", arrayOfEvents.size()+"");
                    //Create the adapter to convert the array to views
                    EventAdapter2 adapter = new EventAdapter2(context, arrayOfEvents);
                    recentEventsListView = (ListView) findViewById(R.id.lvMyUpcomingEvents);
                    //attach the adapter to the listview
                    recentEventsListView.setAdapter(adapter);
                    //ok
                    Log.d("MyEventsActivity", "Confirmed Events Searched");

                }
                //recent events
                if (tabHost.getCurrentTab() == 1) {
                    Log.d("MyEventsActivity", "Tab 1 selected");

                    arrayOfEvents = user.getHistoric();

                    Log.d("MyEventsActivity", arrayOfEvents.size()+"");
                    //Create the adapter to convert the array to views
                    EventAdapter2 adapter = new EventAdapter2(context, arrayOfEvents);
                    recentEventsListView = (ListView) findViewById(R.id.lvMyRecentEvents);
                    //attach the adapter to the listview
                    recentEventsListView.setAdapter(adapter);
                    //ok
                    Log.d("MyEventsActivity", "Recent Events Searched");

                }
                //invited events
                if (tabHost.getCurrentTab() == 2) {
                    Log.d("MyEventsActivity", "Tab 2 selected");

                    arrayOfEvents = user.getInvited();

                    Log.d("MyEventsActivity", arrayOfEvents.size()+"");
                    //Create the adapter to convert the array to views
                    EventAdapter2 adapter = new EventAdapter2(context, arrayOfEvents);
                    recentEventsListView = (ListView) findViewById(R.id.lvMyInvitations);
                    //attach the adapter to the listview
                    recentEventsListView.setAdapter(adapter);
                    //ok
                    Log.d("MyEventsActivity", "Invited Events Searched");
                }
            }
        });

        dao.close();
    }
}