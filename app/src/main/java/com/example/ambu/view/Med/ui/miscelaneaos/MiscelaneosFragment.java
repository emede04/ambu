package com.example.ambu.view.Med.ui.miscelaneaos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ambu.R;
import com.example.ambu.models.Issue;
import com.example.ambu.models.Symptom;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MiscelaneosFragment extends Fragment {
    public ArrayList<Symptom> getlSimptomas() {
        return lSimptomas;
    }

    public void setlSimptomas(ArrayList<Symptom> lSimptomas) {
        this.lSimptomas = lSimptomas;
    }

    public ArrayList<Issue> getlMolestias() {
        return lMolestias;
    }

    public void setlMolestias(ArrayList<Issue> lMolestias) {
        this.lMolestias = lMolestias;
    }

    ArrayList<Symptom> lSimptomas = new ArrayList<>();

    ArrayList<Issue> lMolestias = new ArrayList<>();
    ApiMedicService api = Apis.apiMedicServiceData();
    ApiCallback callback;
    Symptom simtomasSeleccionado;

    private String language ="";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_miscelaneos, container, false);
        if(language.isEmpty()){
            language = "es-es";
        }


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //metodo para sacar todos los simptomas
  /*  public void sacarSintomas(View view) {

        //showProgressDialog();
       // Call<ArrayList<Symptom>> call = api.getAllSymptoms(SharedPreferencesUtils.SacarDatos("ApiMedicToken",view), "json", "es-es");
        final ArrayList<Symptom> lSimptomas = new ArrayList<>();


        ArrayList<Symptom> AUX;
        call.enqueue(new Callback<ArrayList<Symptom>>() {

            @Override
            public void onResponse(Call<ArrayList<Symptom>> call, Response<ArrayList<Symptom>> response) {
                if(response.isSuccessful()){
                    lSimptomas.add(response.body().get(1));
                    System.out.println(lSimptomas.size());
                }else{
                    Log.d("no data","en el metodo de respnse no hay conexion");
                }



            }

            @Override
            public void onFailure(Call<ArrayList<Symptom>> call, Throwable t) {

            }
        });

    } */

    //metodo para sacar los simtomas x id

    public void SacarMolestias(View view){
        Call<ArrayList<Issue>> call = api.getAllIssues(SharedPreferencesUtils.SacarDatos("ApiMedicToken", view), "es-es", "json");
        call.enqueue(new Callback<ArrayList<Issue>>() {
            @Override
            public void onResponse(Call<ArrayList<Issue>> call, Response<ArrayList<Issue>> response) {
                    if(response.isSuccessful()){
                        CargarInformacionDeCadaSintoma(response.body());
                    }
            }

            @Override
            public void onFailure(Call<ArrayList<Issue>> call, Throwable t) {
                Log.d("no data","en el metodo de responde a la conexion");

            }
        });
    }

    public void SacarMolestiabyId(){

    }


    public void CargarTodosLosSintomas(ArrayList<Symptom> symptomArrayList, View view){



        System.out.println("fuera del succes"+symptomArrayList.size());






    }

    public void CargarInformacionDeCadaSintoma(ArrayList<Issue> issueArrayList){


    }

    public interface ApiCallback{
        void onSuccess(ArrayList<Symptom> call, Response<ArrayList<Symptom>> response);
    }


    public void run() {
        throw new RuntimeException("Stub!");
    }

}


