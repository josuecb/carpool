package com.example.josue.carpool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

/**
 * Created by Josue on 10/8/2016.
 */

public class WelcomePage extends AppCompatActivity implements View.OnClickListener {
    Button sign_in_btn, register_btn;
    LocalStorage localStorage;
    Context context;
    EditText phone, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        sign_in_btn = (Button) findViewById(R.id.sign_in_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
        phone = (EditText) findViewById(R.id.user_phone_content_id);
        password = (EditText) findViewById(R.id.user_password_content_id);
        localStorage = new LocalStorage(CarPool.LOCAL_STORAGE_CRED);
        context = this;

        sign_in_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);

        checkCredentials();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_btn:
                Log.e("sign in ", "clicked");
                onSignInBtn();
                break;
            case R.id.register_btn:
                Log.e("register", "clicked");
                onRegisterBtn();
                break;
            default:
                break;
        }
    }

    /**
     * Store credentials in local data storage
     */
    public void onSignInBtn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UserPost data = ApiClient.locationApi.sendRequest(
                            new UserCredentialsLogin(Long.parseLong(phone.getText().toString()),
                                    password.getText().toString())).execute().body();
                    if (!data.authDetails.email.isEmpty()) {
                        localStorage.save(CarPool.USER_NAME_LABEL, data.userDetails.fullName, context);
                        localStorage.save(CarPool.USER_EMAIL_LABEL, data.authDetails.email, context);
                        localStorage.save(CarPool.USER_PHONE_LABEL, data.authDetails.phone, context);
                        localStorage.save(CarPool.USER_STATEID_LABEL, data.userDetails.stateId, context);
                        localStorage.save(CarPool.USER_ID, String.valueOf(data.userDetails.id), context);
                        localStorage.save(CarPool.USER_PASS_LABEL, data.accessTokenDetails.token, context);
                        localStorage.save(CarPool.USER_POINTS_LABEL, "100", context);
                        Log.i("data", data.authDetails.email);

                        Intent goToMap = new Intent(context, MapsActivity.class);
                        startActivity(goToMap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void onRegisterBtn() {
        Intent registrationIntent = new Intent(this, RegistrationPage.class);
        startActivity(registrationIntent);
    }

    public void checkCredentials() {
        try {
            final String username = localStorage.pull(CarPool.USER_PHONE_LABEL, context);
            final String password = localStorage.pull(CarPool.USER_PASS_LABEL, context);

            if (!Helpers.isEmpty(username) && !Helpers.isEmpty(password)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("USERNAME", "" + username);
                        Log.e("PASSWORD", "" + password);
                        Intent intent = new Intent(context, MapsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
