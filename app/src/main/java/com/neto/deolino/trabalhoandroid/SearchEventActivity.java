package com.neto.deolino.trabalhoandroid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.EventType;
import com.neto.deolino.trabalhoandroid.util.Constants;
import com.neto.deolino.trabalhoandroid.util.DateHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by deolino on 30/10/16.
 */
public class SearchEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    ListView searchResultListView;
    Context context;

    TextView tvAddress;
    Button btnSEventDate;
    Button btnSEventTime;
    RadioGroup rgEventType;
    RadioButton rbChecked;
    EditText etKm;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    String searchLocationStr;
    String dateString;
    String timeString;

    Date searchDate = new Date();

    private EventType mType;

    ArrayList<Event> arrayOfEvents;

    int eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        context = this;
        searchResultListView = (ListView) findViewById(R.id.lvSearchResults);

        tvAddress = (TextView) findViewById(R.id.tvEventAddress);
        btnSEventDate = (Button) findViewById(R.id.btnSEventDate);
        btnSEventTime = (Button) findViewById(R.id.btnSEventTime);
        rgEventType = (RadioGroup) findViewById(R.id.rgSEventType);
        rbChecked = (RadioButton) findViewById(R.id.rbSBike);
        etKm = (EditText) findViewById(R.id.etKm);

        mType = new EventType();

        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SearchEventActivity", "Item " + position + " clicked");

                eventID = arrayOfEvents.get(position).getId();
                Log.d("SearchEventActivity", "eventID 1: " + eventID);
                Intent intent = new Intent(context, EventDescriptionActivity.class);
                intent.putExtra("eventID", eventID );
                startActivity(intent);
            }
        });

    }

    protected void onPause() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.putInt("eventType", rgEventType.getCheckedRadioButtonId());
        editor.putString("date", btnSEventDate.getText().toString());
        editor.putString("time", btnSEventTime.getText().toString());
        editor.putString("km", etKm.getText().toString());
        editor.putString("location", searchLocationStr);
        editor.apply();
        super.onPause();
    }


    @Override
    protected void onResume() {
        int typeInt = prefs.getInt("eventType", R.id.rbSBike);
        rgEventType.check(typeInt);

        int t = rgEventType.getCheckedRadioButtonId();
        mType.setType(t==R.id.rbSBike ? EventType.Type.BIKE : t==R.id.rbSRun ? EventType.Type.RUN : EventType.Type.HIKE);

        btnSEventDate.setText(prefs.getString("date", "Date: " + DateHelper.dateToString(Calendar.getInstance().getTime())));
        btnSEventTime.setText(prefs.getString("time", "Time: " + DateHelper.timeToString(Calendar.getInstance().getTime())));
        etKm.setText(prefs.getString("km", "1"));
        searchLocationStr = prefs.getString("location", getString(R.string.choose_a_location));
        tvAddress.setText(searchLocationStr);
        super.onResume();
    }

    private void populateEventsList() {

    }

    public void showTimePickerDialog(View v) {

    }

    public void showDatePickerDialog(View v) {

    }

    public void selectLocationButtonPressed(View view){

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        searchDate.setDate(dayOfMonth);
        searchDate.setMonth(monthOfYear);
        searchDate.setYear(year);

        Log.w("CreateEventActivity","SearchDate = " + searchDate.toString());

        dateString = DateHelper.dateToMySQLFormat(searchDate);

        btnSEventDate.setText(String.format(getString(R.string.date), dateString));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.w("CreateEventActivity","SearchTime = " + hourOfDay + ":" + minute);

        searchDate.setHours(hourOfDay);
        searchDate.setMinutes(minute);

        timeString = DateHelper.timeToMySQLFormat(searchDate);

        btnSEventTime.setText(String.format(getString(R.string.time), timeString));
    }

    public void searchButtonClicked(View view) {

    }
}
