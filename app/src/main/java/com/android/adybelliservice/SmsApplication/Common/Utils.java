package com.android.adybelliservice.SmsApplication.Common;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    public static void setPreference(String name, String value, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(name, MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getSharedPreference(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(name, MODE_PRIVATE);
        String value = prefs.getString(name, "");
        return value;
    }
}
