package com.example.josue.carpool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Josue on 10/8/2016.
 */

public class LocalStorage {
    private String preferenceName;

    public LocalStorage(String preferenceName) {
        this.preferenceName = preferenceName;
    }

    public void save(String preferenceLabel, String preferenceContent, Context myContext) {
        SharedPreferences custom = myContext.getSharedPreferences(this.preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor submit = custom.edit();

        submit.putString(preferenceLabel, preferenceContent);
        submit.apply();
    }

    public String pull(String preferenceLabel, Context myContext) {
        SharedPreferences custom = myContext.getSharedPreferences(this.preferenceName, Context.MODE_PRIVATE);

        return custom.getString(preferenceLabel, null);
    }

}
