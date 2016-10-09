package com.example.josue.carpool;

import android.widget.EditText;

/**
 * Created by Josue on 10/8/2016.
 */

public class Helpers {
    public static boolean isEmpty(EditText text) {
        return text.getText().toString().isEmpty();
    }

    public static boolean isEmpty(String text) {
        return text.isEmpty();
    }
}
