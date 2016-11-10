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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.neto.deolino.trabalhoandroid.dao.EventDAO;
import com.neto.deolino.trabalhoandroid.dao.UserDAO;
import com.neto.deolino.trabalhoandroid.model.Event;
import com.neto.deolino.trabalhoandroid.model.EventType;
import com.neto.deolino.trabalhoandroid.model.Location;
import com.neto.deolino.trabalhoandroid.model.User;
import com.neto.deolino.trabalhoandroid.util.DateHelper;
import com.neto.deolino.trabalhoandroid.util.DatePickerFragment;
import com.neto.deolino.trabalhoandroid.util.TimePickerFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by deolino on 27/10/16.
 */
public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Event event;

    TextView tvStart;
    TextView tvEnd;

    Context context;

    Button btnEventDate;
    Button btnEventTime;

    RadioGroup rgEventType;
    TextView tvDescription;

    CheckBox cbSecret;

    Date date = new Date();

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    String endLocationStr;
    String startLocationStr;

    private EventType mType;

    double startLat, startLong;
    double endLat, endLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        tvStart = (TextView) findViewById(R.id.tvEventStart);
        tvEnd = (TextView) findViewById(R.id.tvEventEnd);
        btnEventDate = (Button) findViewById(R.id.btnEventDate);
        btnEventTime = (Button) findViewById(R.id.btnEventTime);
        rgEventType = (RadioGroup) findViewById(R.id.rgEventType);
        tvDescription = (TextView) findViewById(R.id.etEventDescription);
        cbSecret = (CheckBox) findViewById(R.id.cbSecretEvent);

        context = this;

        mType = new EventType();
    }

    @Override
    protected void onPause() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.putInt("eventType", rgEventType.getCheckedRadioButtonId());
        editor.putString("date", btnEventDate.getText().toString());
        editor.putString("time", btnEventTime.getText().toString());
        editor.putString("start", startLocationStr);
        editor.putString("end", endLocationStr);
        editor.putString("description", tvDescription.getText().toString());
        editor.putBoolean("secret", cbSecret.isChecked());
        editor.putString("startLatStr", String.valueOf(startLat));
        editor.putString("startLongStr", String.valueOf(startLong));
        editor.putString("endLatStr", String.valueOf(endLat));
        editor.putString("endLongStr", String.valueOf(startLong));
        editor.apply();

        super.onPause();
    }


    @Override
    protected void onResume() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        int typeInt = prefs.getInt("eventType", R.id.rbBike);
        rgEventType.check(typeInt);

        int t = rgEventType.getCheckedRadioButtonId();
        mType.setType(t == R.id.rbBike ? EventType.Type.BIKE : t == R.id.rbRun ? EventType.Type.RUN : EventType.Type.HIKE);

        startLocationStr = prefs.getString("start", "Choose a location");
        tvStart.setText(startLocationStr);
        endLocationStr = prefs.getString("end", "Choose a location");
        tvEnd.setText(endLocationStr);
        btnEventDate.setText(prefs.getString("date", "Date: " + DateHelper.dateToString(Calendar.getInstance().getTime())));
        btnEventTime.setText(prefs.getString("time", "Time: " + DateHelper.timeToString(Calendar.getInstance().getTime())));
        tvDescription.setText(prefs.getString("description", ""));
        cbSecret.setChecked(prefs.getBoolean("secret", false));

        startLat = Double.parseDouble(prefs.getString("startLatStr", "0.0"));
        startLong = Double.parseDouble(prefs.getString("startLongStr", "0.0"));
        endLat = Double.parseDouble(prefs.getString("endLatStr", "0.0"));
        endLong = Double.parseDouble(prefs.getString("endLongStr", "1.0"));

        super.onResume();
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        date.setDate(dayOfMonth);
        date.setMonth(monthOfYear);
        date.setYear(year);

        Log.w("CreateEventActivity", "Date = " + date.toString());

        String dateString = DateHelper.dateToString(date);

        btnEventDate.setText(String.format(getString(R.string.date), dateString));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.w("CreateEventActivity", "Time = " + hourOfDay + ":" + minute);

        date.setHours(hourOfDay);
        date.setMinutes(minute);

        String timeString = DateHelper.timeToString(date);

        btnEventTime.setText(String.format(getString(R.string.time), timeString));
    }

    public void createEventButtonPressed(View view) {
        Log.d("CreateEventActivity", "Create Button pressed");
        Toast.makeText(context, R.string.creating_event, Toast.LENGTH_LONG).show();
        event = new Event();

        event.setType(mType);
//        event.setDeparture(new Location(startLocationStr,this));
//        event.setArrival(new Location(endLocationStr,this));
        Log.d("CreateEventActivity", "StartLL: " + startLat + "," + startLong);
        Log.d("CreateEventActivity", "EndLL: " + endLat + ":" + endLong);
        event.setDeparture(Location.getLocationFromCoordinates(startLat, startLong, context));
        event.setArrival(Location.getLocationFromCoordinates(endLat, endLong, context));

        event.setSecret(cbSecret.isChecked());
        event.setDate(date);
        event.setDescription(tvDescription.getText().toString());
        event.setOver(false);

        UserDAO dao = new UserDAO(this);
        event.setOrganizer(dao.findById(prefs.getInt("user_id", 0)));
        dao.close();

        EventDAO eventDAO = new EventDAO(this);
        eventDAO.insert(event);

        Log.d("CreateEventActivity", "Event created!");
        Toast.makeText(context, getString(R.string.event_created_successfully), Toast.LENGTH_LONG).show();
        int eventID = event.getId();
        Intent intent = new Intent(context, EventDescriptionActivity.class);
        intent.putExtra("eventID", eventID);
        startActivity(intent);

        //startActivity(new Intent(this, EventDescriptionActivity.class));
    }
}
