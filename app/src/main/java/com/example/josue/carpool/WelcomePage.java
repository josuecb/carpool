package com.example.josue.carpool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Josue on 10/8/2016.
 */

public class WelcomePage extends AppCompatActivity implements View.OnClickListener {
    Button sign_in_btn, register_btn;
    LocalStorage localStorage;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        sign_in_btn = (Button) findViewById(R.id.sign_in_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
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
