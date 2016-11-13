package com.neto.deolino.trabalhoandroid.activies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.dao.UserDAO;
import com.neto.deolino.trabalhoandroid.enumerations.Gender;
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

        getChoosenAccount();
    }

    private void getChoosenAccount(){
        int id = 0;
        if(extras!=null) id = extras.getInt("id_person", 0);
        if(id==0) {
            UserDAO dao = new UserDAO(this);
            user = dao.findById(PreferenceManager.getDefaultSharedPreferences(this).getInt("user_id", 0));
            dao.close();
            updateComponents();
        } else {
            /*user = new User();
            new UserService().findById(id, user, new PostExecute() {
                @Override
                public void postExecute(int option) {
                    updateComponents();
                }
            });*/
            Toast.makeText(UserDerscriptionActivity.this, "NÃO POSSUI ESSE CARA", Toast.LENGTH_LONG).show();
        }
    }

    private void updateComponents(){
        if(user.hasImage()){
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int x = metrics.widthPixels;
            if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) x/=2;
            ivUser.setVisibility(View.VISIBLE);
            Bitmap b = Bitmap.createScaledBitmap(user.getImage(), x, x* user.getImage().getHeight()/user.getImage().getWidth(), true);
            ivUser.setMinimumHeight(b.getHeight());
            ivUser.setMinimumWidth(b.getWidth());
            ivUser.setImageBitmap(b);
        } else {
            ivUser.setVisibility(View.GONE);
        }

        if(user.getGender()== Gender.UNINFORMED) ivGender.setVisibility(View.GONE);
        else {
            ivGender.setVisibility(View.VISIBLE);
            ivGender.setImageResource(user.getGender()==Gender.FEMALE ? R.drawable.female : R.drawable.male);
        }
        tvName.setText(user.getName());
        tvDescription.setText(user.getDescription());
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

            getChoosenAccount();
        }
    }
}
