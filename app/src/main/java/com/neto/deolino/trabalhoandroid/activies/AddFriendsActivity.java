package com.neto.deolino.trabalhoandroid.activies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.neto.deolino.trabalhoandroid.R;

import java.util.ArrayList;

/**
 * Created by deolino on 30/10/16.
 */
public class AddFriendsActivity extends AppCompatActivity {

    ListView friendsSearchListView;
    Context context;
    AutoCompleteTextView etSearchFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        String str[] = {"Matheus", "João", "Maria", "José"};
        AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.etSearchFriend);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, str);

        auto.setThreshold(1);
        auto.setAdapter(adp);
    }
}
