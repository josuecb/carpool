package com.example.josue.carpool;

import java.util.ArrayList;

/**
 * Created by Josue on 10/8/2016.
 */

public class Cars {
    ArrayList<User> cars;

    public Cars() {
        cars = new ArrayList<>();
    }

    public void add(User driver) {
        if (driver.isDriver())
            cars.add(driver);
    }

    public ArrayList<User> getCars() {
        return cars;
    }
}
