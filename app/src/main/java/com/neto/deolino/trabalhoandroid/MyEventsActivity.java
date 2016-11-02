package com.neto.deolino.trabalhoandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import com.neto.deolino.trabalhoandroid.model.Event;

import java.util.ArrayList;

/**
 * Created by deolino on 30/10/16.
 */
public class MyEventsActivity extends AppCompatActivity {

    TabHost tabHost;
    ListView upcomingEventsListView;
    ListView recentEventsListView;
    ListView invitationsListView;

    Context context;
    int eventID;

    ArrayList<Event> arrayOfEvents = new ArrayList<>();

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

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
    }
}