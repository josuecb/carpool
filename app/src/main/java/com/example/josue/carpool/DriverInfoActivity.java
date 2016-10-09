package com.example.josue.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Josue on 10/8/2016.
 */

public class DriverInfoActivity extends AppCompatActivity implements View.OnClickListener {
    TextView name, email, phone, points, away, minutes;
    Button notificationBtn;
    int pointsGiven = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        Intent intent = getIntent();

        notificationBtn = (Button) findViewById(R.id.notifyBtn);
        name = (TextView) findViewById(R.id.user_name_content);
        email = (TextView) findViewById(R.id.user_email_content);
        phone = (TextView) findViewById(R.id.user_phone_content);
        points = (TextView) findViewById(R.id.user_point_content);
        away = (TextView) findViewById(R.id.user_away_content);
        minutes = (TextView) findViewById(R.id.user_minutes_away_content);

        name.setText(intent.getStringExtra("name"));
        email.setText(intent.getStringExtra("name") + "@email.com");
        phone.setText("212-234-3488");

        double lat1 = Double.parseDouble(intent.getStringExtra("lat"));
        double lon1 = Double.parseDouble(intent.getStringExtra("lon"));

        double lat2 = Double.parseDouble(intent.getStringExtra("s_lat"));
        double lon2 = Double.parseDouble(intent.getStringExtra("s_lon"));

        int miles = Calculations.calculateMiles(lat1, lon1, lat2, lon2);
        away.setText(miles + " miles");
        pointsGiven = Calculations.calculatePoints(miles);
        points.setText(pointsGiven + " could get.");
        int min = (int) (((double) miles / 10.0) * 100);
        minutes.setText(min + " minutes away from you");

//        Log.e("name", intent.getStringExtra("name"));
//        Log.e("lat", intent.getStringExtra("lat"));
//        Log.e("lon", intent.getStringExtra("lon"));
//        Log.e("s_lat", intent.getStringExtra("s_lat"));
//        Log.e("s_lon", intent.getStringExtra("s_lon"));
//
        notificationBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notifyBtn:
                goBack();
                break;
            default:
                break;
        }
    }

    public void goBack() {
        LocalStorage localStorage = new LocalStorage(CarPool.LOCAL_STORAGE_CRED);
        int currentPoints = Integer.parseInt(localStorage.pull(CarPool.USER_POINTS_LABEL, this));
        localStorage.save(CarPool.USER_POINTS_LABEL, (currentPoints - pointsGiven) + "", this);

        Intent goBack = new Intent(this, MapsActivity.class);
        startActivity(goBack);
        finish();
    }
}
