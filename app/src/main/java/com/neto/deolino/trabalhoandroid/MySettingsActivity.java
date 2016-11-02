package com.neto.deolino.trabalhoandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by deolino on 30/10/16.
 */
public class MySettingsActivity extends AppCompatActivity {

    private static final int ACTIVITY_RESULT_UPDATE_ACCOUNT = 0;
    Spinner spLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private int localeToInt(String locale){
        switch (locale){
            case "es":
            case "es_ES":
            case "es_US": return 1;
            case "pt_PT":
            case "pt":
            case "pt_BR": return 2;
            case "pl":
            case "pl_PL": return 3;
            case "fi":
            case "fi_FI": return 4;
            case "de":
            case "de_AT":
            case "de_DE":
            case "de_LI":
            case "de_CH": return 5;
            default: return 0;
        }
    }

    private void changeLanguage(int code){
    }

    public void changePasswordButtonClicked(View view){

    }


    public void changeMailButtonClicked(View view){

    }


    public void editAccountButtonClicked(View view){
        Intent intent = new Intent(this, EditAccountActivity.class);
        startActivityForResult(intent, ACTIVITY_RESULT_UPDATE_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTIVITY_RESULT_UPDATE_ACCOUNT){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("name");
            editor.remove("sex");
            editor.remove("description");
            editor.apply();
        }
    }
}
