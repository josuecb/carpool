package com.example.josue.carpool;

import android.content.Context;

/**
 * Created by Josue on 10/9/2016.
 */

public class UserProfileData3  {
    public String email;
    public Long userId;
    public String token;
    public UserProfileData3(String email, Context context) {

        this.email = email;
        LocalStorage localStorage = new LocalStorage(CarPool.LOCAL_STORAGE_CRED);
        this.userId = Long.parseLong(localStorage.pull(CarPool.USER_ID, context));
        this.token = localStorage.pull(CarPool.USER_PASS_LABEL, context);
    }
}
