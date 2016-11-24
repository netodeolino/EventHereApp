package com.neto.deolino.trabalhoandroid.activies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.neto.deolino.trabalhoandroid.R;
import com.neto.deolino.trabalhoandroid.service.web.Server;
import com.neto.deolino.trabalhoandroid.service.web.UserService;
import com.neto.deolino.trabalhoandroid.util.async.PostExecute;

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
            new UserService().retrievePassword(mail, new PostExecute() {
                @Override
                public void postExecute(int option) {
                    if (Server.RESPONSE_CODE==Server.RESPONSE_OK) {
                        Toast.makeText(ResetPasswordActivity.this, getString(R.string.reset_password), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, getString(R.string.error_user_not_found), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
