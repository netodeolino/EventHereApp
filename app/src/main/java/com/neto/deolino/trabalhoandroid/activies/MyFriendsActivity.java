package com.neto.deolino.trabalhoandroid.activies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.adapters.PersonAdapter;
import com.neto.deolino.trabalhoandroid.dao.UserDAO;
import com.neto.deolino.trabalhoandroid.model.User;
import com.neto.deolino.trabalhoandroid.service.web.UserService;
import com.neto.deolino.trabalhoandroid.util.async.PostExecute;

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

        UserDAO dao = new UserDAO(this);
        user = dao.findById(PreferenceManager.getDefaultSharedPreferences(this).getInt("user_id",0));
        friendsListView = (ListView) findViewById(R.id.lvMyFriends);
        dao.close();

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
        new UserService().findFriends(user, new PostExecute() {
            @Override
            public void postExecute(int option) {
                if(user.getFriends().size() > 0) {
                    Log.d("MyFriendsActivity", user.getFriends().get(0).toString());
                    //Create the adapter to convert the array to views
                    PersonAdapter adapter = new PersonAdapter(getApplication(), user.getFriends(), PersonAdapter.LIST_FRIENDS);
                    //attach the adapter to the listview
                    friendsListView.setAdapter(adapter);
                } else {
                    Toast.makeText(MyFriendsActivity.this, "You have no friends", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
