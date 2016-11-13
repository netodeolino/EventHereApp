package com.neto.deolino.trabalhoandroid.activies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.model.Event;

/**
 * Created by deolino on 27/10/16.
 */
public class MapsActivity extends AppCompatActivity {
    ProgressBar progressBar;

    TextView tvDistance;
    TextView tvDuration;

    Intent intent;

    Event event;
    Context context;
    int eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        context = this;

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvDuration = (TextView) findViewById(R.id.tvDuration);

        intent = getIntent();

        eventID = intent.getIntExtra("eventID", 0 );
        Log.d("MapsActivity", "EvID: " + Integer.toString(eventID));

        event = new Event();
    }
}
