package com.example.ambu.view.Med.ui.miscelaneaos;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SymbolTable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ambu.R;
import com.example.ambu.models.Symptom;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.RetrofitController;
import com.example.ambu.utils.SharedPreferencesUtils;
import com.google.android.gms.common.api.Api;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.grpc.internal.JsonUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MiscelaneosFragment extends Fragment {

    ArrayList<Symptom> lSimptomas = new ArrayList<>();
    ApiMedicService api = Apis.apiMedicServiceData();

    Symptom simtomasSeleccionado;
   ;
    private String language ="";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_miscelaneos, container, false);
        if(language.isEmpty()){
            language = "es-es";
        }
        sacarSintomas(view);
        System.out.println(lSimptomas.size());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //metodo para sacar todos los simptomas
    public void sacarSintomas(View view){
        //showProgressDialog();
        Call<ArrayList<Symptom>> call = api.getAllSymptoms(SharedPreferencesUtils.SacarDatos("ApiMedicToken", view), "json", "es-es");
        call.enqueue(new Callback<ArrayList<Symptom>>() {
            @Override
            public void onResponse(Call<ArrayList<Symptom>> call, Response<ArrayList<Symptom>> response) {
                if(response.isSuccessful()){
                   generarSimtomas(response.body());
                }else{
                    Log.d("no data","en el metodo de respnse no hay conexion");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Symptom>> call, Throwable t) {

            }
        });



    };

    //metodo para sacar los simtomas x id

    public void generarSimtomas(ArrayList<Symptom> symptomArrayList){
        this.lSimptomas = symptomArrayList;
        System.out.println(lSimptomas.size());

    }






}


