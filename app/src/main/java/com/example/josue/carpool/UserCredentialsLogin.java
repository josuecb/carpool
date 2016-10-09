package com.example.josue.carpool;

import android.util.Log;

/**
 * Created by Josue on 10/8/2016.
 */

public class UserCredentialsLogin {
    Long phoneNumber;
    String password;

    public UserCredentialsLogin(Long phoneNumber, String password) {
        this.password = password;
        this.phoneNumber = phoneNumber;
    }


}

