package com.example.ambu.view.Med.ui.miscelaneaos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ambu.R;
import com.example.ambu.models.FullIssue;
import com.example.ambu.models.Issue;
import com.example.ambu.models.Symptom;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MiscelaneosFragment extends Fragment {

    ArrayList<Issue> lEnfermedad;
    Spinner enfermedades;

    TextView texto;
    ApiMedicService api = Apis.apiMedicServiceData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public MiscelaneosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_miscelaneos, container, false);

        enfermedades = view.findViewById(R.id.spinnerEnfermedades);
        texto = view.findViewById(R.id.tvTexto);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargartodaslasissues(view);




    }

    public void cargartodaslasissues(View v){

        lEnfermedad  = new ArrayList<>();
    Call<List<Issue>> call = api.getAllIssues(SharedPreferencesUtils.SacarDatos("ApiMedicToken",v),"json","es-es");
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {

                for(Issue i : response.body()){
                    lEnfermedad.add(i);
                }

                ArrayAdapter adapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_list_item_1,lEnfermedad);
                enfermedades.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                enfermedades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        Issue s = new Issue();
                        s = (Issue) adapterView.getItemAtPosition(i);

                        System.out.println(s.getName());
                        cargarTexto(s.getID(), v);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {

            }
        });
    }




    public void cargarTexto(int id, View view){


        Call<FullIssue> call = api.getIssuesbyid(id, SharedPreferencesUtils.SacarDatos("ApiMedicToken", view), "json", "es-es");
            call.enqueue(new Callback<FullIssue>() {
                @Override
                public void onResponse(Call<FullIssue> call, Response<FullIssue> response) {

                    FullIssue i = response.body();

                    texto.setText(i.getName()+"\n" + i.getDescription()+"\n" + i.getMedicalCondition());

                }

                @Override
                public void onFailure(Call<FullIssue> call, Throwable t) {

                }
            });


    }



}





