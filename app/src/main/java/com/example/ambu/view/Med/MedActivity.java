package com.example.ambu.view.Med;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ambu.R;
import com.example.ambu.databinding.*;
import com.example.ambu.models.Symptom;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MedActivity extends AppCompatActivity{
    private ApiMedicService apimedic;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMedBinding binding;
    private Apis api;
    ImageView med;
    TextView nombremed;
    TextView especialidad;
    //Private ArrayList<Pacientes> listaPacientes;
    private  ArrayList<Symptom> listaSintomas;
    //Private ArrayList<Diagnostico> listaDiagnostico
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String nombre = savedInstanceState.getString("nombremedio");
        String especialidad = savedInstanceState.getString("especialidad");

        binding = ActivityMedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMed.toolbar);
        binding.appBarMed.toolbar.hideOverflowMenu();
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_pacientes, R.id.nav_consulta, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_med);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //metodo para sacar los datos del medico logeado
        //para tener los iconos coloridos
        navigationView.setItemIconTintList(null);



        //NavigationHeader
        View header = navigationView.getHeaderView(0);
      //  ColorDrawable colorDrawable
      //          = new ColorDrawable(Color.parseColor("#DCF0ED"));
      //  binding.appBarMed.toolbar.setBackgroundColor(colorDrawable.getColor()); //<-- metodo para dejar los colores bonitos;

        cargarMed();
        med = (ImageView) header.findViewById(R.id.idImagenMedico);
        nombremed = header.findViewById(R.id.txtnameMed);
        nombremed.setText(nombre);
        Glide
                .with(this.getBaseContext())
                .load("https://static.vecteezy.com/system/resources/previews/017/196/552/non_2x/doctor-icon-on-transparent-background-free-png.png")
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(med);


    }

    private void cargarMed() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.med, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_med);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }






    }




