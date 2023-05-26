package com.example.ambu.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ambu.R;
import com.example.ambu.fragments.LoginFragment;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.LoginService;
import com.example.ambu.utils.BaseDeDatosLocal;
import com.example.ambu.utils.SharedPreferencesUtils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class MainActivity extends AppCompatActivity implements  Navigation {
    LoginService Ilogin;
    Apis api;
    BaseDeDatosLocal DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferencias.edit();


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }

    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


    public void init(View view){

    }


    public BaseDeDatosLocal getDB() {
        return DB;
    }

    public void setDB(BaseDeDatosLocal DB) {
        this.DB = DB;
    }


}