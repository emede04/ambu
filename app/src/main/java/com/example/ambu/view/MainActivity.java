package com.example.ambu.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ambu.R;
import com.example.ambu.fragments.LoginFragment;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.BaseDeDatosLocal;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements  Navigation {
    Apis api;
    BaseDeDatosLocal DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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


    public BaseDeDatosLocal getDB() {
        return DB;
    }

    public void setDB(BaseDeDatosLocal DB) {
        this.DB = DB;
    }


}