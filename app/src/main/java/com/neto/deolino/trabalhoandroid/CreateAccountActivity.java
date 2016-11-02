package com.neto.deolino.trabalhoandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by deolino on 21/10/16.
 */
public class CreateAccountActivity extends AppCompatActivity {

    TextView etMail;
    TextView etName;
    TextView etPassword;
    TextView etRepeatPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void createAccount(View view){
        /*final String mail = etMail.getText().toString();
        final String name = etName.getText().toString();
        final String password = etPassword.getText().toString();
        final String repeatPassword = etRepeatPassword.getText().toString();
        if(mail.isEmpty() || name.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()){
            Toast.makeText(CreateAccountActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(CreateAccountActivity.this, "LOGIN REALIZADO COM SUCESSO", Toast.LENGTH_LONG).show();
        }*/
        Toast.makeText(CreateAccountActivity.this, "CADASTRO REALIZADO COM SUCESSO", Toast.LENGTH_LONG).show();
        //Implementação aqui do cadastro
    }
}
