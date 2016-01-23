package com.ryde.chris.ryde;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends TouchActivity {
    public static int REGISTER_USER = 1;
    private EditText usernameTextField;
    private EditText passwordTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameTextField = (EditText) findViewById(R.id.username);
        passwordTextField = (EditText) findViewById(R.id.password);
        setUpLoginButtonClickListener();
        Button registerButton = (Button) findViewById(R.id.btnReg);
        setTouchNClick(registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivityForResult(startRegisterActivity, REGISTER_USER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_USER && resultCode == RESULT_OK) {
            finish();
        }
    }

    private void setUpLoginButtonClickListener() {
        Button loginButton = (Button) findViewById(R.id.btnLogin);
        setTouchNClick(loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = usernameTextField.getText().toString();
                String password = passwordTextField.getText().toString();
                if (userName.length() == 0 || password.length() == 0) {
                    Utils.showDialog(LoginActivity.this, R.string.err_fields_empty);
                    return;
                }
                final ProgressDialog dia = ProgressDialog.show(LoginActivity.this, null,
                        getString(R.string.alert_wait));
                ParseUser.logInInBackground(userName, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        dia.dismiss();
                        if (parseUser != null) {
                            Intent startMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(startMainActivity);
                        } else {
                            Utils.showDialog(LoginActivity.this, getString(R.string.err_login) + " "
                                    + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}