package com.example.ambu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

public class SharedPreferencesUtils {
    public static void saveToke(String key, String data, View view){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,data);
        Log.e("SharedPreferencesNew", "Guardado con exito");
        editor.apply();
    }


    public static String sacarToken(String apiMedicToken, View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return sharedPreferences.getString(apiMedicToken, "");
    }
}
