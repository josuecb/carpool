package com.example.josue.carpool;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Handler timer;
    private MapCarUpdater mapCarUpdater;
    private SpinnerAdapter spinnerAdapter;
    private Spinner schoolSpinner;
    private Button okBtn, cancelBtn;
    private Button yesBtn, noBtn;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        schoolSpinner = (Spinner) findViewById(R.id.school_spinner);
        // Create an instance of GoogleAPIClient.
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        spinnerAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item);
        okBtn = (Button) findViewById(R.id.ok_btn);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);

        /**
         * Setting up dialog
         */
        dialog = new Dialog(MapsActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(R.string.dialog_title);
        TextView text = (TextView) dialog.findViewById(R.id.text_view);
        text.setText(R.string.dialog_msg);


        yesBtn = (Button) dialog.findViewById(R.id.accept_dia_btn);
        noBtn = (Button) dialog.findViewById(R.id.cancel_dia_btn);

        /**
         * Ending setting dialog
         */


        /**
         * Adding Schools
         */
        spinnerAdapter.add(new School("LaGuardia Community college", new LatLng(42.60108400014901, -83.18480399985098)));
        spinnerAdapter.add(new School("NYU", new LatLng(43.60108400014901, -84.18480399985098)));

        schoolSpinner.setAdapter(spinnerAdapter);
        /**
         * Setting adapter for list
         */
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        yesBtn.setOnClickListener(this);
        noBtn.setOnClickListener(this);


    }


    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    /**
     * Simulates drivers
     *
     * @param times: number of steps to simulate cars driving
     * @return Cars: driver list so the map can update
     */
    public Cars createDrivers(float times) {
        String[] names = {"Jhon", "Paul", "Sean", "Michael", "Pink"};
        System.out.println(times);

        LatLng[] latLngs = {
                new LatLng(42.566084 + times, -83.219804 + times),
                new LatLng(42.466084 + times, -82.320804 + times),
                new LatLng(42.766084 + times, -83.269804 + times),
                new LatLng(43.966084 + times, -82.229004 + times),
                new LatLng(44.86084 + times, -81.229004 + times)};

        System.out.println(Arrays.toString(latLngs));
        Cars drivers = new Cars();

        for (int index = 0; index < names.length; index++)
            drivers.add(new User(names[index], null, null, latLngs[index], true));

        return drivers;
    }

    /**
     * This function simulates the driver driving on the map on real time
     *
     * @param times
     */
    public void loopTimer(final int times) {
        if (times != 0) {
            timer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("updating", "" + times);
                    loopTimer(times - 1);
                    mapCarUpdater.setDrivers(createDrivers((float) times / 1000));
                    mapCarUpdater.updateMap();
                }
            }, 500);
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng detroit = new LatLng(49.566084, -84.229804);
//        mMap.addMarker(new MarkerOptions().position(detroit).title("Marker in Sydney"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(31.566084, -32.229804)).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(detroit));
    }


    public boolean gpsIsEnabled() {
        LocationManager lm = null;
        boolean gps_enabled = false, network_enabled = false;
        if (lm == null)
            lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        return gps_enabled && network_enabled;
    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        requestPermisionOrZoomIn(gpsIsEnabled());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestPermisionOrZoomIn(gpsIsEnabled());

        Cars drivers = createDrivers(0);
        timer = new Handler();

        mapCarUpdater = new MapCarUpdater(this.mMap, drivers);
        mapCarUpdater.updateMap();


        loopTimer(100);
    }

    public void requestPermisionOrZoomIn(boolean gpsIsEnabled) {
        if (!gpsIsEnabled) {
            Intent gpsOptionsIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMapClickListener(this);
            Log.e("connecting", "yes");

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }

            //Zooming
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mLastLocation != null) {
                        // Goes to my last connection if there is any
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 13));
                    }
                }
            }, 1000);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("request code", "" + requestCode);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ok_btn:
                Log.e("ok", "clicked");
                dialog.show();
                break;
            case R.id.cancel_btn:
                Log.e("CANCEL", "clicked");
                break;
            case R.id.accept_dia_btn:
                dialog.hide();
                break;
            case R.id.cancel_dia_btn:
                dialog.hide();
                break;
            default:
                break;
        }
    }
}


