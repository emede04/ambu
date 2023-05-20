package com.example.ambu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.example.ambu.models.Symptom;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferencesUtils {
    public static void saveToke(String key, String data, View view){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,data);
        Log.e("SharedPreferencesNew", "Guardado con exito");
        editor.apply();
    }


    public static String SacarDatos(String token, View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return sharedPreferences.getString(token, "");
    }

    public static void saveData(String data,String key,View view){
        SharedPreferences shared = view.getContext().getSharedPreferences("sharedprefereces", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString(key,json);
        editor.apply();
        Log.e("SharedPreferencesNew", data);

    }
    public static void loadDataSimtomas(String key,View view,ArrayList listaSymtomas ){
        SharedPreferences shared = view.getContext().getSharedPreferences("sharedprefereces", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = shared.getString(key,null);
        Type type = new TypeToken<ArrayList<Symptom>>() {}.getType();
        listaSymtomas = gson.fromJson(json, type);
        Log.e("SharedPreferencesNew", "Guardado con exito");

    }



}
