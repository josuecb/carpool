package com.example.josue.carpool;

import android.content.Context;
import android.util.Log;

/**
 * Created by Josue on 10/9/2016.
 */

public class UserProfileData {
    public String fullName;
    public Long userId;
    public String token;

    public UserProfileData(String name, Context context) {
        this.fullName = name;
        LocalStorage localStorage = new LocalStorage(CarPool.LOCAL_STORAGE_CRED);
        this.userId = Long.parseLong(localStorage.pull(CarPool.USER_ID, context));
        this.token = localStorage.pull(CarPool.USER_PASS_LABEL, context);
    }
}
