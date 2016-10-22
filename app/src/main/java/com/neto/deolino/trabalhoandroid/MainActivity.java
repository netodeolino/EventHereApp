package com.neto.deolino.trabalhoandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.neto.deolino.trabalhoandroid.dao.UserDAO;
import com.neto.deolino.trabalhoandroid.interfaces.AsyncExecutable;
import com.neto.deolino.trabalhoandroid.model.User;
import com.neto.deolino.trabalhoandroid.services.ServerInfo;
import com.neto.deolino.trabalhoandroid.services.UserService;
import com.neto.deolino.trabalhoandroid.util.PasswordHelper;


//import com.facebook.FacebookSdk; Ativar quando for ativar login com facebook


public class MainActivity extends AppCompatActivity {

    public static final User user = new User();
    LoginButton btLogin;
    CallbackManager callbackManager;
    EditText etMail, etPassword;
    ProgressBar pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
    }

    public void createAccount(View view) {
        startActivity(new Intent(this, CreateAccountActivity.class));
    }

    public void loginButtonClicked(View view){
        String mail = etMail.getText().toString();
        String password = etPassword.getText().toString();
        if(mail.isEmpty() || password.isEmpty()){
            Toast.makeText(MainActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_LONG).show();
        } else {
            final User user = new User();
            new UserService().findByLogin(mail, PasswordHelper.md5(password), user, new AsyncExecutable() {
                @Override
                public void postExecute(int option) {
                    if(ServerInfo.RESPONSE_CODE==UserService.ERROR_INCORRECT_DATA){
                        pbLogin.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, getString(R.string.error_incorrect_data_login), Toast.LENGTH_LONG).show();
                    } else {
                        MainActivity.user.copy(user);
                        login();
                    }
                }

                @Override
                public void preExecute(int option) {
                    pbLogin.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void login(){
        if(user.getMail()==null || user.getMail().isEmpty()) return;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("user_id", user.getId());
        editor.apply();
        UserDAO dao = new UserDAO(this);
        dao.insert(user);
        dao.close();
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        finish();
    }
}
