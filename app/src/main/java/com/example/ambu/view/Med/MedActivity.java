package com.example.ambu.view.Med;

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
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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


        med = (ImageView) header.findViewById(R.id.idImagenMedico);
        nombremed = header.findViewById(R.id.txtnameMed);
        nombremed.setText("Vista medico");
        especialidad = header.findViewById(R.id.especialidad);
        especialidad.setText("opciones: ");
        Glide
                .with(this.getBaseContext())
                .load("https://cdn-icons-png.flaticon.com/512/3063/3063176.png")
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




