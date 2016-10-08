package com.example.josue.carpool;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josue on 10/8/2016.
 */

public class User {
    private String name;
    private String id;
    private String phoneNumber;
    private LatLng location;
    private boolean type; // 0 user, 1 driver
    public User(String name, String id, String phoneNumber, LatLng location, boolean type) {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public LatLng getLocation() {
        return this.location;
    }

    public boolean isDriver() {
        return this.type;
    }
}
