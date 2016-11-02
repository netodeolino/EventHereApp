package com.neto.deolino.trabalhoandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.neto.deolino.trabalhoandroid.model.User;

/**
 * Created by deolino on 30/10/16.
 */
public class UserDerscriptionActivity extends AppCompatActivity {
    private static final int ACTIVITY_RESULT_UPDATE_ACCOUNT = 0;

    ImageView ivUser, ivGender;
    TextView tvName, tvDescription;
    User user;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_derscription);
        ivUser = (ImageView) findViewById(R.id.ivUser);
        ivGender = (ImageView) findViewById(R.id.ivGender);
        tvName = (TextView) findViewById(R.id.tvUserName);
        tvDescription = (TextView) findViewById(R.id.tvUserDescription);
        extras = getIntent().getExtras();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(extras==null || extras.getInt("id_person",0)==0) {
            getMenuInflater().inflate(R.menu.user_description_menu, menu);
            menu.findItem(R.id.menuEdit).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuEdit:
                intent = new Intent(this, EditAccountActivity.class);
                startActivityForResult(intent, ACTIVITY_RESULT_UPDATE_ACCOUNT);
                break;
            case R.id.menuAddFriends:
                intent = new Intent(this, AddFriendsActivity.class);
                startActivity(intent);
                break;
            case R.id.menuMyFriends:
                intent = new Intent(this, MyFriendsActivity.class);
                startActivity(intent);
                break;
            case R.id.menuMyEvents:
                intent = new Intent(this, MyEventsActivity.class);
                startActivity(intent);
            default:
                finish();
        }
        return true;
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
