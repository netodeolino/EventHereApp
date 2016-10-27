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

//import com.facebook.FacebookSdk; Ativar quando for ativar login com facebook

public class MainActivity extends AppCompatActivity {

    //public static final User user = new User();
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
            login();
        }
    }

    public void login(){
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        finish();
    }
}
