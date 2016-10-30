package com.neto.deolino.trabalhoandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

        populateFriendsList();

        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User person = (User)friendsListView.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), UserDerscriptionActivity.class);
                intent.putExtra("id_person", person.getId());
                startActivity(intent);
            }
        });
    }

    private void populateFriendsList() {

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
