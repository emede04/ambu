package com.example.ambu.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambu.R;
import com.example.ambu.models.Paciente;
import com.example.ambu.models.Symptom;
import com.example.ambu.models.User;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.BaseDeDatosLocal;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;
import com.example.ambu.view.Med.ui.miscelaneaos.SintomasAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentSelectSymtom extends Fragment {

    ;
    ApiMedicService api = Apis.apiMedicServiceData();
    ArrayAdapter<Symptom> adaptador_sintomas;
    ArrayAdapter<String> genresMenuAdapter;
    ListView vListaSeleccionada;
    ArrayList<Symptom> listaS;
    FloatingActionButton boton;

    public FragmentSelectSymtom() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaS = new ArrayList<>();


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


    public void init(View view) {
        vListaSeleccionada = view.findViewById(R.id.vListaSeleccionada);
        boton = view.findViewById(R.id.bAceptarSintomas);
    }
    public String parseSymtom(ArrayList<Symptom> l,View view){
        //para poder pasar los id de los sintomas como query
        //ejemplo query
        //diagnosis?symptoms=[14,20]&
        String parse = "[";
        for(int i = 0; i< listaS.size();i++) {
            if (listaS.size() == i) {
                parse += listaS.get(i).getID();
            }
            else{

                parse += listaS.get(i).getID()+",";

            }

            //todo esta logica la tengo que recargar enfin
            }

        parse += "]";
        return parse;
    }


    public void SacarSintomas(View view) {

        List<Symptom> listaSintomas = new ArrayList<Symptom>();

        Call<List<Symptom>> listCall = api.getAllSymptoms(SharedPreferencesUtils.SacarDatos("ApiMedicToken", view), "json", "es-es");
        listCall.enqueue(new Callback<List<Symptom>>() {
            @Override
            public void onResponse(Call<List<Symptom>> call, Response<List<Symptom>> response) {

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

            @Override
            public void onFailure(Call<List<Symptom>> call, Throwable t) {

            }
        });


    }

 public void onclick(){

     vListaSeleccionada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             Symptom miSintoma;
             miSintoma= (Symptom) adapterView.getItemAtPosition(i);
             Toast.makeText(view.getContext(), miSintoma.getName(), Toast.LENGTH_SHORT).show();
             //todo que cuanto toque la lista y este vacia no pete xd
             if(miSintoma!=null){
                 listaS.add(miSintoma);
             }else{
                 System.out.println("auch");
             }

         }

     });


     boton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if (listaS.size() != 0) {
                 Snackbar.make(view, "de vuelta con los sintomas para terminar su registro", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();

                 String SintomasQuery = parseSymtom(listaS, view);
                 System.out.println(SintomasQuery);
                 SharedPreferencesUtils.saveData("idSintomas", SintomasQuery, view);

             } else {

                 Snackbar.make(view, "no tienes ningun sintomas selecciado", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();

             }
         }


     });
 }


}