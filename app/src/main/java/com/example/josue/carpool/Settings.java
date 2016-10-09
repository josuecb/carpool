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
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Josue on 10/9/2016.
 */

public class Settings extends AppCompatActivity implements View.OnClickListener {
    Button logoutButton, saveBtn;
    Context context;
    EditText name, email, phone;
    TextView id, points;
    LocalStorage localStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        logoutButton = (Button) findViewById(R.id.log_out_action);
        saveBtn = (Button) findViewById(R.id.save_btn_id);
        name = (EditText) findViewById(R.id.user_name_content);
        email = (EditText) findViewById(R.id.user_email_content);
        phone = (EditText) findViewById(R.id.user_phone_content);
        id = (TextView) findViewById(R.id.user_state_content);
        points = (TextView) findViewById(R.id.user_point_id);

        context = this;
        logoutButton.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        localStorage = new LocalStorage(CarPool.LOCAL_STORAGE_CRED);

        points.setText("You have " + localStorage.pull(CarPool.USER_POINTS_LABEL, this) + " Points");
        setTextViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_out_action:
                onLogoutAction();
                break;
            case R.id.save_btn_id:
                saveRequest();
                break;
            default:
                break;
        }
    }

    public void saveRequest() {
        if (!Helpers.isEmpty(name) && !Helpers.isEmpty(email)
                && !Helpers.isEmpty(phone)) {
            if (!name.getText().toString().equals(localStorage.pull(CarPool.USER_NAME_LABEL, context))) {
                doRequest("name");
            }

            if (!phone.getText().toString().equals(localStorage.pull(CarPool.USER_PHONE_LABEL, context))) {
                doRequest("phone");
            }

            if (!email.getText().toString().equals(localStorage.pull(CarPool.USER_EMAIL_LABEL, context))) {
                doRequest("email");
            }

        }

        Intent goToMap = new Intent(context, MapsActivity.class);
        startActivity(goToMap);
    }

    public void doRequest(final String key) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    switch (key) {
                        case "name":
                            UserPost data = ApiClient.locationApi.updateRequest(new UserProfileData(
                                    name.getText().toString(), context)).execute().body();
                            if (!data.authDetails.email.isEmpty()) {
                                localStorage.save(CarPool.USER_NAME_LABEL, data.userDetails.fullName, context);
                            }
                            break;
                        case "phone":
                            UserPost data2 = ApiClient.locationApi.updateRequest(new UserProfileData2(
                                    phone.getText().toString(), context)).execute().body();
                            if (!data2.authDetails.email.isEmpty())
                                localStorage.save(CarPool.USER_PHONE_LABEL, data2.authDetails.phone, context);
                            break;
                        case "email":
                            UserPost data3 = ApiClient.locationApi.updateRequest(new UserProfileData3(
                                    email.getText().toString(), context)).execute().body();
                            if (!data3.authDetails.email.isEmpty())
                                localStorage.save(CarPool.USER_EMAIL_LABEL, data3.authDetails.email, context);
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void setTextViews() {
        name.setText(localStorage.pull(CarPool.USER_NAME_LABEL, this));
        email.setText(localStorage.pull(CarPool.USER_EMAIL_LABEL, this));
        phone.setText(localStorage.pull(CarPool.USER_PHONE_LABEL, this));
        id.setText(localStorage.pull(CarPool.USER_STATEID_LABEL, this));
        Log.e("NAME", localStorage.pull(CarPool.USER_NAME_LABEL, this));
        Log.e("TOKEN", localStorage.pull(CarPool.USER_PASS_LABEL, this));
        Log.e("ID", localStorage.pull(CarPool.USER_ID, this));
    }

    public void onLogoutAction() {
        localStorage.save(CarPool.USER_NAME_LABEL, "", context);
        localStorage.save(CarPool.USER_EMAIL_LABEL, "", context);
        localStorage.save(CarPool.USER_PHONE_LABEL, "", context);
        localStorage.save(CarPool.USER_STATEID_LABEL, "", context);
        localStorage.save(CarPool.USER_ID, "", context);
        localStorage.save(CarPool.USER_PASS_LABEL, "", context);

        Log.e("user", "" + localStorage.pull(CarPool.USER_NAME_LABEL, context));
        Intent goSetting = new Intent(context, WelcomePage.class);
        startActivity(goSetting);
        finish();
    }
}
