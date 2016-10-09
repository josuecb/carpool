package com.example.josue.carpool;

/**
 * Created by Josue on 10/8/2016.
 */

public class UserRegistrationDetails {
    private String name;
    private String email;
    private String phoneNumber;
    private String stateId;
    private String password;

    public UserRegistrationDetails(String name, String email,
                                   String phoneNumber,
                                   String stateId,
                                   String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.stateId = stateId;
        this.password = password;
    }

}
