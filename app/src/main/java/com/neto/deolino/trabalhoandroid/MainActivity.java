package com.neto.deolino.trabalhoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

public class MainActivity extends AppCompatActivity {

    EditText etMail, etPassword;
    ProgressBar pbLogin;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void createAccount(View view) {
        startActivity(new Intent(this, CreateAccountActivity.class));
    }

    public void loginButtonClicked(View view){

        // RECUPERAR CADASTRO DO USUARIO NO ARRAYLIST, COMPARAR E FAZER LOGIN

        //String mail = etMail.getText().toString();
        //String password = etPassword.getText().toString();
        //if(mail.isEmpty() || password.isEmpty()){
        //    Toast.makeText(MainActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_LONG).show();
        //} else {
            login();
        //}
    }

    public void login(){
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
    }

    public void resetPassword(View view) {
        startActivity(new Intent(this, ResetPasswordActivity.class));
    }
}
