package com.neto.deolino.trabalhoandroid.activies;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.adapters.PersonAdapter;
import com.neto.deolino.trabalhoandroid.dao.UserDAO;
import com.neto.deolino.trabalhoandroid.interfaces.AsyncExecutable;
import com.neto.deolino.trabalhoandroid.model.User;
import com.neto.deolino.trabalhoandroid.service.web.Server;
import com.neto.deolino.trabalhoandroid.service.web.UserService;

import java.util.ArrayList;

/**
 * Created by deolino on 30/10/16.
 */
public class AddFriendsActivity extends AppCompatActivity {

    ListView friendsSearchListView;
    Context context;
    EditText etSearchFriend;
    User user;
    ProgressBar pbAddFriends;
    ArrayList<User> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        context = this;
        friendsSearchListView = (ListView) findViewById(R.id.lvFriendsSearch);
        etSearchFriend = (EditText) findViewById(R.id.etSearchFriend);

        UserDAO dao = new UserDAO(this);
        user = dao.findById(PreferenceManager.getDefaultSharedPreferences(this).getInt("user_id",0));
        dao.close();

        pbAddFriends = (ProgressBar) findViewById(R.id.pbAddFriends);
        searchResults = new ArrayList<>();
    }

    public void searchFriends(View view) {
        searchResults.clear();
        UserService us = new UserService();
        if (etSearchFriend.getText().length() == 0) {
            Toast.makeText(AddFriendsActivity.this, getString(R.string.search_field_empty), Toast.LENGTH_LONG).show();
        } else {
            us.findByMailOrName(etSearchFriend.getText().toString(), searchResults, new AsyncExecutable() {
                @Override
                public void postExecute(int option) {
                    pbAddFriends.setVisibility(View.GONE);
                    if(Server.RESPONSE_CODE==UserService.ERROR_INCORRECT_DATA){
                        Toast.makeText(AddFriendsActivity.this, getString(R.string.error_incorrect_search_input), Toast.LENGTH_LONG).show();
                    } else {
                        //Cria um adapter para converter os arrays em visões
                        PersonAdapter adapter = new PersonAdapter(context, searchResults, PersonAdapter.FIND_FRIENDS);
                        //anexa o adapter ao listview
                        friendsSearchListView.setAdapter(adapter);
                        //ib.setVisibility(View.GONE);
                    }
                    if(searchResults.isEmpty()) {
                        Toast.makeText(AddFriendsActivity.this, getString(R.string.no_results_found), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void preExecute(int option) {
                    pbAddFriends.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
