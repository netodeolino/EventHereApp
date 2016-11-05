package com.neto.deolino.trabalhoandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.neto.deolino.trabalhoandroid.dao.UserDAO;
import com.neto.deolino.trabalhoandroid.model.User;
import com.neto.deolino.trabalhoandroid.util.PasswordHelper;

/**
 * Created by deolino on 21/10/16.
 */
public class CreateAccountActivity extends AppCompatActivity {

    EditText etMail;
    EditText etName;
    EditText etPassword;
    EditText etRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etMail = (EditText) findViewById(R.id.etCreateMail);
        etName = (EditText) findViewById(R.id.etCreateName);
        etPassword = (EditText) findViewById(R.id.etCreatePassword);
        etRepeatPassword = (EditText) findViewById(R.id.etRepeatPassword);
    }

    public void createAccount(View view){
        final String mail = etMail.getText().toString();
        final String name = etName.getText().toString();
        final String password = etPassword.getText().toString();
        final String repeatPassword = etRepeatPassword.getText().toString();

        if(mail.isEmpty() || name.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()){
            Toast.makeText(CreateAccountActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_LONG).show();
        } else if(!password.equals(repeatPassword)){
            Toast.makeText(CreateAccountActivity.this, getString(R.string.error_password_not_match), Toast.LENGTH_LONG).show();
        }else{
            //Implementação aqui do cadastro
            //User user = new User(name, mail, PasswordHelper.md5(password), null);
            User user = new User(name, mail, password, null);
            UserDAO userDAO = new UserDAO();
            userDAO.insert(user);

            Toast.makeText(CreateAccountActivity.this, "CADASTRO REALIZADO COM SUCESSO", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
