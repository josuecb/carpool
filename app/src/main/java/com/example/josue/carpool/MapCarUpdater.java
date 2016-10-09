package com.example.josue.carpool;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Josue on 10/8/2016.
 */

public class MapCarUpdater {
    private GoogleMap map;
    private Cars cars;

    public MapCarUpdater(GoogleMap map, Cars drivers) {
        this.map = map;
        this.cars = drivers;
    }

    public void setDrivers(Cars drivers) {
        this.cars = drivers;
    }

    public void updateMap() {
        map.clear();
        for (User driver : this.cars.getCars()) {
//            Log.e("Lat", "" + driver.getLocation().latitude);
//            Log.e("Long", "" + driver.getLocation().longitude);
            MarkerOptions marker =
                    new MarkerOptions()
                            .position(driver.getLocation())
                            .title(driver.getName());
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.car));
            map.addMarker(marker);
        }
    }
}
