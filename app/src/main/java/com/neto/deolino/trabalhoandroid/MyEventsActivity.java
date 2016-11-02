package com.neto.deolino.trabalhoandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.neto.deolino.trabalhoandroid.R;

import java.util.ArrayList;
import java.util.List;

public class MyEventsActivity extends AppCompatActivity {

    TabHost tabHost;
    ListView upcomingEventsListView;
    ListView recentEventsListView;
    ListView invitationsListView;

    Context context;
    int eventID;

    String [] eventos1 = {"Evento 1", "Evento 2", "Evento 3"};
    String [] eventos2 = {"Evento 4", "Evento 5", "Evento 6"};
    String [] eventos3 = {"Evento 7", "Evento 8", "Evento 9"};
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
                Toast.makeText(getApplicationContext(), "Position:" + itemP+ "ListItem:" + itemV, Toast.LENGTH_LONG).show();
            }
        });

        recentEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemP = position;
                String itemV = (String) recentEventsListView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Position:" + itemP+ "ListItem:" + itemV, Toast.LENGTH_LONG).show();
            }
        });

        invitationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemP = position;
                String itemV = (String) invitationsListView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Position:" + itemP+ "ListItem:" + itemV, Toast.LENGTH_LONG).show();
            }
        });
    }

}