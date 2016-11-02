package com.neto.deolino.trabalhoandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by deolino on 02/11/16.
 */
public class ResetPasswordActivity extends AppCompatActivity {

    EditText etResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etResetPassword = (EditText) findViewById(R.id.etResetPassword);
    }

    public void resetPassword(View view){
        String mail = etResetPassword.getText().toString();
        if(mail.isEmpty()){
            Toast.makeText(ResetPasswordActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ResetPasswordActivity.this, getString(R.string.reset_password), Toast.LENGTH_LONG).show();
        }
    }
}
