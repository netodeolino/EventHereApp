package com.neto.deolino.trabalhoandroid.activies;

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
import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.dao.UserDAO;
import com.neto.deolino.trabalhoandroid.model.User;
import com.neto.deolino.trabalhoandroid.service.local.Services;
import com.neto.deolino.trabalhoandroid.util.PasswordHelper;

public class MainActivity extends AppCompatActivity {

    public static final User user = new User();
    EditText etMail, etPassword;
    ProgressBar pbLogin;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMail = (EditText) findViewById(R.id.etMail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        //btLogin = (LoginButton) findViewById(R.id.btnFb);
        pbLogin = (ProgressBar) findViewById(R.id.pbLogin);
        pbLogin.setVisibility(View.GONE);
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

        Intent start = new Intent(this, Services.class);
        startService(start);

        String mail = etMail.getText().toString();
        String password = etPassword.getText().toString();
        /*
        if(mail.isEmpty() || password.isEmpty()){
            Toast.makeText(MainActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_LONG).show();
        } else {
            final User user = new User();
            new UserService().findByLogin(mail, PasswordHelper.md5(password), user, new AsyncExecutable() {
                @Override
                public void postExecute(int option) {
                    if(Server.RESPONSE_CODE==UserService.ERROR_INCORRECT_DATA){
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
        */

        UserDAO uDao = new UserDAO(this);
        User teste = uDao.findByLogin(PasswordHelper.md5(password));
        boolean err = false;

        //debug
        System.out.println(teste.getMail());
        System.out.println(teste.getPassword());
        System.out.println(mail);
        System.out.println(PasswordHelper.md5(password));

        if (mail.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_LONG).show();
        }
        if ((teste.getMail().equals(mail)) && (teste.getPassword().equals(PasswordHelper.md5(password)))) {
            err = true;
            MainActivity.user.copy(teste);
            login();
        }
        if(err == false){
            Toast.makeText(MainActivity.this,getString(R.string.error_incorrect_data_login), Toast.LENGTH_LONG).show();
        }
    }

    public void login() {
        if(user.getMail()==null || user.getMail().isEmpty()) return;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("user_id", user.getId());
        editor.apply();

        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        finish();
    }

    public void resetPassword(View view) {
        startActivity(new Intent(this, ResetPasswordActivity.class));
    }

    public void broadcastCustomIntent(View view) {
        Intent intent = new Intent("MyCustomIntent");
//        EditText et = (EditText)findViewById(R.id.extraIntent);

        intent.putExtra("message", (CharSequence)"Null");
        intent.setAction("com.deolino.android.A_CUSTOM_INTENT");
        sendBroadcast(intent);
    }
}
