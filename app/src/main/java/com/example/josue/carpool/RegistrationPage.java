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

public class RegistrationPage extends AppCompatActivity implements View.OnClickListener {
    Button submitDataBtn;
    EditText nameText, phoneText, passwordText, emailText, stateId;
    LocalStorage localStorage;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        submitDataBtn = (Button) findViewById(R.id.submit_registration_btn);
        nameText = (EditText) findViewById(R.id.user_name_content);
        emailText = (EditText) findViewById(R.id.user_email_content);
        phoneText = (EditText) findViewById(R.id.user_phone_content);
        stateId = (EditText) findViewById(R.id.user_state_content);
        passwordText = (EditText) findViewById(R.id.user_password_content);
        localStorage = new LocalStorage(CarPool.LOCAL_STORAGE_CRED);

        context = this;
        submitDataBtn.setOnClickListener(this);
        Log.i("Name", "" + localStorage.pull(CarPool.USER_NAME_LABEL, this));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_registration_btn:
                submitData();
                break;
            default:
                break;
        }
    }

    public void submitData() {
        Log.e("Submiting Data", "yes");
        if (!Helpers.isEmpty(nameText) && !Helpers.isEmpty(emailText)
                && !Helpers.isEmpty(phoneText) && !Helpers.isEmpty(stateId)
                && !Helpers.isEmpty(passwordText)) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    localStorage.save(CarPool.USER_NAME_LABEL, nameText.getText().toString(), context);
                    localStorage.save(CarPool.USER_EMAIL_LABEL, emailText.getText().toString(), context);
                    localStorage.save(CarPool.USER_PHONE_LABEL, phoneText.getText().toString(), context);
                    localStorage.save(CarPool.USER_STATEID_LABEL, nameText.getText().toString(), context);
                    localStorage.save(CarPool.USER_ID, nameText.getText().toString(), context);
                    localStorage.save(CarPool.USER_PASS_LABEL, nameText.getText().toString(), context);
                    localStorage.save(CarPool.USER_POINTS_LABEL, "100", context);

                    Intent goToMap = new Intent(context, MapsActivity.class);
                    startActivity(goToMap);
                }
            }).start();

            Log.e("Name", "" + nameText.getText());
            Log.e("Email", "" + emailText.getText());
            Log.e("phone", "" + phoneText.getText());
            Log.e("state id", "" + stateId.getText());
            Log.e("Password", "" + passwordText.getText());
        }
    }


}
