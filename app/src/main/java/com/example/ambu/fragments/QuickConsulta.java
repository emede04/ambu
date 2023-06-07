package com.example.ambu.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuickConsulta extends Fragment {

    ApiMedicService api = Apis.apiMedicServiceData();
    ArrayAdapter<Symptom> adaptador_nuevos_sintomas;
    ArrayAdapter<String> genresMenuAdapter;
    ListView vListaSeleccionada;
    ArrayList<Symptom> listaS;
    ArrayList<Symptom> listaSeleccionados;
    ArrayList<Symptom> aux;
    String sintomasdelusuario = "";
    FloatingActionButton baceptar;
    String documento;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public QuickConsulta() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          documento   = getArguments().getString("nombre");
            listaS = new ArrayList<>();
            listaSeleccionados = new ArrayList<>();
            aux = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quick_consulta, container, false);
        init(view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         api = Apis.apiMedicServiceData();

        String prueba = SharedPreferencesUtils.SacarDatos("ApiMedicToken", view);


        SacarSintomas(view);
        System.out.println("hola"+prueba);


    }


    public void init(View view) {
        vListaSeleccionada = view.findViewById(R.id.vListaSeleccionadaConsulta);
        baceptar = view.findViewById(R.id.bAceptar);
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
                    for(Symptom s :response.body()){
                        if(s.getID() ==56){
                            listaSintomas.remove(s);
                        }
                    }
                    adaptador_nuevos_sintomas = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_checked, listaSintomas);
                    vListaSeleccionada.setAdapter(adaptador_nuevos_sintomas);
                    adaptador_nuevos_sintomas.notifyDataSetChanged();

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


        baceptar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                SharedPreferencesUtils.saveToke("idSintomas", "", view);

                ArrayList<Symptom> symptomArraylist;
                if (!aux.isEmpty()) {
                    Snackbar.make(view, "Se han añadido sus sintomas nuevos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();



                    //limpio la lista de los duplicados
                    listaSeleccionados =   quitarduplicados(aux);

                    String SintomasQuery = "";


                    System.out.println();
                    SintomasQuery = parseSymtom(listaSeleccionados, view);
                    System.out.println(SintomasQuery);

                    sintomasdelusuario = parseNombre(listaSeleccionados,view);
                    System.out.println(SintomasQuery);
                    db.collection("Pacientes").document(documento).update("sintomas", SintomasQuery);


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
