package com.example.josue.carpool;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josue on 10/8/2016.
 */

public class School {
    private String name;
    private String id;
    private LatLng location;

    public School(String name, String id, LatLng location) {
        this.name = name;
        this.id = id;
        this.location = location;
    }

    public School(String name, LatLng location) {
        this.name = name;
        this.id = null;
        this.location = location;
    }

    public LatLng getLocation() {
        return this.location;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
