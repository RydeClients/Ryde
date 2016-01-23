package com.ryde.chris.ryde;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends TouchActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = (Button)findViewById(R.id.btnReg);
        final EditText passwordField = (EditText)findViewById(R.id.pwd);
        final EditText emailField = (EditText) findViewById(R.id.email);
        final EditText usernameField = (EditText) findViewById(R.id.username);
        setTouchNClick(registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordField.getText().toString();
                String email = emailField.getText().toString();
                String username = usernameField.getText().toString();
                if (email.length() == 0 || password.length() == 0 || username.length() == 0) {
                    Utils.showDialog(RegisterActivity.this, R.string.err_fields_empty);
                    return;
                }
                final ProgressDialog dia = ProgressDialog.show(RegisterActivity.this, null,
                        getString(R.string.alert_wait));

                final ParseUser pu = new ParseUser();
                pu.setEmail(email);
                pu.setPassword(password);
                pu.setUsername(username);
                pu.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {
                        dia.dismiss();
                        if (e == null) {
                            setResult(RESULT_OK);
                            Intent startMainActivity = new Intent(RegisterActivity.this, MainActivity.class);
                            RegisterActivity.this.startActivity(startMainActivity);
                            finish();
                        } else {
                            Utils.showDialog(
                                    RegisterActivity.this,
                                    getString(R.string.err_signup) + " "
                                            + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
