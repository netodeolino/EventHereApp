package com.neto.deolino.trabalhoandroid.activies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.model.User;

/**
 * Created by deolino on 30/10/16.
 */
public class MyFriendsActivity extends AppCompatActivity {

    ListView friendsListView;
    Context context;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        context = this;

        final ListView listaDeCursos = (ListView) findViewById(R.id.lvMyFriends);

        String[] cursos = new String[]{"Raimundo Matheus", "Neto Deolino", "Marcio Maia", "Android Studio"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, cursos);
        listaDeCursos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_friends_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuAddFriends:
                intent = new Intent(this, AddFriendsActivity.class);
                startActivity(intent);
                break;
            case (android.R.id.home):
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
