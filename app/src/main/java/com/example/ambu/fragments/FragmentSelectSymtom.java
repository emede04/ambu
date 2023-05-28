package com.example.ambu.fragments;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ambu.R;
import com.example.ambu.models.Symptom;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;
import com.example.ambu.view.Med.ui.consulta.Consulta;
import com.example.ambu.view.Med.ui.pacientes.Pacientes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentSelectSymtom extends Fragment {


    ApiMedicService api = Apis.apiMedicServiceData();
    ArrayAdapter<Symptom> adaptador_sintomas;
    ArrayAdapter<String> genresMenuAdapter;
    ListView vListaSeleccionada;
    ArrayList<Symptom> listaS;
    ArrayList<Symptom> listaSeleccionados;
    ArrayList<Symptom> aux;
    String sintomasdelusuario = "";

    FloatingActionButton boton;

    public FragmentSelectSymtom() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaS = new ArrayList<>();
        listaSeleccionados = new ArrayList<>();
        aux = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_symtom, container, false);
        init(view);
        String prueba = SharedPreferencesUtils.SacarDatos("ApiMedicToken", view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String prueba = SharedPreferencesUtils.SacarDatos("ApiMedicToken", view);


        SacarSintomas(view);
        System.out.println(prueba);


    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        listaSeleccionados.clear();
        listaS.clear();
        adaptador_sintomas.clear();
    }

    public void init(View view) {
        vListaSeleccionada = view.findViewById(R.id.vListaSeleccionada);
        boton = view.findViewById(R.id.bAceptarSintomas);
    }
    public String parseSymtom(ArrayList<Symptom> l,View view){
        SharedPreferencesUtils.saveToke("nombre","",view);

        //para poder pasar los id de los sintomas como query
        //ejemplo query
        //diagnosis?symptoms=[14,20]&
        String sintomasNombre = "";

        int incr = 0;
        String parse = "[";
        int c = 0;

        for (Symptom symptom: listaSeleccionados
        ) {
            c++;
            if (listaSeleccionados.size() == c){
                parse += symptom.getID();

            }else{
                parse += symptom.getID() + ",";
            }
        }


        int x = 0;
        for (Symptom symptom: listaSeleccionados
        ) {
            x++;
            if (listaSeleccionados.size() == x){
                sintomasNombre += symptom.getName();

            }else{
                sintomasNombre += symptom.getName()+",";
            }
        }
        SharedPreferencesUtils.saveToke("nombre",sintomasNombre,view);

        parse += "]";
        System.out.println(parse);
   
        return parse;

    }

    public String parseNombre(ArrayList<Symptom> l,View view){
        String sintomasNombre = "";

        int x = 0;
        for (Symptom symptom: listaSeleccionados
        ) {
            x++;
            if (listaSeleccionados.size() == x){
                sintomasNombre += symptom.getName();

            }else{
                sintomasNombre += symptom.getName()+",";
            }
        }

        return sintomasNombre;
    }

    public void SacarSintomas(View view) {

        List<Symptom> listaSintomas = new ArrayList<Symptom>();

        Call<List<Symptom>> listCall = api.getAllSymptoms(SharedPreferencesUtils.SacarDatos("ApiMedicToken", view), "json", "es-es");
        listCall.enqueue(new Callback<List<Symptom>>() {
            @Override
            public void onResponse(Call<List<Symptom>> call, Response<List<Symptom>> response) {

                if(response.isSuccessful()) {
                    for (Symptom symptom : response.body()) {
                        if (!"".equals(symptom.getName())) ;
                        listaSintomas.add(symptom);
                        System.out.println(symptom.getName());

                    }
                    adaptador_sintomas = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_checked, listaSintomas);
                    vListaSeleccionada.setAdapter(adaptador_sintomas);
                    adaptador_sintomas.notifyDataSetChanged();

                    onclick();
                }

            }

            @Override
            public void onFailure(Call<List<Symptom>> call, Throwable t) {

            }
        });


    }

 public void onclick(){

     vListaSeleccionada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             ArrayList<Symptom> auxiliar = new ArrayList<>();
             ArrayList<Symptom> fin = new ArrayList<>();
             Symptom ultimoClick;
             ultimoClick= (Symptom) adapterView.getItemAtPosition(i);
             Toast.makeText(view.getContext(), ultimoClick.getName(), Toast.LENGTH_SHORT).show();

                     aux.add(ultimoClick);
                //añado todos los items
             System.out.println("lista final"+aux.size());

             System.out.println("lista final"+ultimoClick.getName());


         }

     });


     boton.setOnClickListener(new View.OnClickListener() {
         @SuppressLint("ResourceType")
         @Override
         public void onClick(View view) {
             SharedPreferencesUtils.saveToke("idSintomas", "", view);

             ArrayList<Symptom> symptomArraylist;
             if (!aux.isEmpty()) {
                 Snackbar.make(view, "de vuelta con los sintomas para terminar su registro", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();



                    //limpio la lista de los duplicados
                 listaSeleccionados =   quitarduplicados(aux);

                 String SintomasQuery = "";


                 System.out.println();
                 SintomasQuery = parseSymtom(listaSeleccionados, view);
                 System.out.println(SintomasQuery);

                 sintomasdelusuario = parseNombre(listaSeleccionados,view);
                 System.out.println(SintomasQuery);
                 SharedPreferencesUtils.saveToke("idSintomas", SintomasQuery, view);
                 SharedPreferencesUtils.saveToke("sintomasdelusuario", sintomasdelusuario, view);


             } else {

                 Snackbar.make(view, "no tienes ningun sintomas selecciado", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();

             }
         }


     });
 }


    public ArrayList<Symptom> quitarduplicados(ArrayList<Symptom> prueba) {
            ArrayList<Symptom> limpio = new ArrayList<>();

        Set<String> nombre = new HashSet<>();
         limpio = (ArrayList<Symptom>) prueba.stream()
                .filter(e -> nombre.add(e.getName()))
                .collect(Collectors.toList());
        return limpio;
    }


}



