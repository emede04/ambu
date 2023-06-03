package com.example.ambu.view.Med.ui.consulta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.R;
import com.example.ambu.fragments.internetoFragment;
import com.example.ambu.models.Diagnosis;
import com.example.ambu.models.FullIssue;
import com.example.ambu.models.Specialisation;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.AdaptadorDiagnostico>  implements View.OnClickListener {

    ApiMedicService api = Apis.apiMedicServiceData();

    private View.OnClickListener sensor;
    Context context;

    ArrayList<Diagnosis> listaDiagnosis;
    ArrayList<Specialisation> listaEspecialidades;
    Button vermas;

    public ConsultaAdapter(Context contex, ArrayList<Diagnosis> listaDiagnosis,ArrayList<Specialisation> listaEspecialidades){
        this.listaDiagnosis = listaDiagnosis;
        this.listaEspecialidades = listaEspecialidades;
        this.context = contex;
    }


    @NonNull
    @Override
    public AdaptadorDiagnostico onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_diagnostico,parent,false);
        AdaptadorDiagnostico holder = new AdaptadorDiagnostico(vista);
        vista.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDiagnostico holder, @SuppressLint("RecyclerView") int position) {
        ArrayList<Specialisation> spc = new ArrayList<>();
        View view = holder.name.getRootView();
        cargarIssue(listaDiagnosis, view,holder, position);


        spc.addAll(listaDiagnosis.get(position).getSpecialisation());
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, spc);
        holder.spspecialization.setAdapter(arrayAdapter);
        holder.vermas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String name = listaDiagnosis.get(position).getIssue().getName();
               String output = generateUrl(name);


                Bundle bundle = new Bundle();
                bundle.putString("url",output);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fr = new internetoFragment();
                fr.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.cardView2,fr).addToBackStack(null).commit();

            }
        });

    }


    public String generateUrl(String issue_name){
        String issue_url  ="https://vsearch.nlm.nih.gov/vivisimo/cgi-bin/query-meta?v%3Aproject=medlineplus-spanish&v%3Asources=medlineplus-spanish-bundle&query=";
        //si es solo una palabra no se modifica el nombre, en el caso que sean dos se cambiar el espacio por un + para generar la
        String query = "";
        //primero hay que mirar si la string tiene una o mas palabras
        String[] sint = issue_name.split(" ");
        boolean itera = true;
        if(sint.length >1){

            query = issue_name.replace(" ", "+");


        }else{

            query = issue_name;

        }

        return issue_url+query;
    }

    @Override
    public int getItemCount() {
        return listaDiagnosis.size();
    }

    public void setOnClickListener(View.OnClickListener sensor){
        this.sensor = sensor;
    }

    @Override
    public void onClick(View view) {
        if(sensor!=null){
            sensor.onClick(view);


        }
    }

    private void cargarIssue(ArrayList<Diagnosis> listaDiagnostico,View view,AdaptadorDiagnostico holder,int position) {
             int id = listaDiagnostico.get(position).getIssue().getID();
            Call<FullIssue> call = api.getIssuesbyid(id, SharedPreferencesUtils.SacarDatos("ApiMedicToken", view), "json", "es-es");

            call.enqueue(new Callback<FullIssue>() {
                FullIssue aux;

                @Override
                public void onResponse(Call<FullIssue> call, Response<FullIssue> response) {

                    if (response == null) {

                    } else {

                        aux = response.body();
                        System.out.println(aux.getDescription());

                        listaDiagnostico.get(position).setIssue2(aux);
                        holder.descripcionCorta.setText("descripcion: "+ listaDiagnosis.get(position).getIssue2().getDescriptionShort());
                        holder.MedicalContion.setText("condicion medica : "+ listaDiagnosis.get(position).getIssue2().getMedicalCondition());
                        holder.name.setText("nombre : "+ listaDiagnosis.get(position).getIssue2().getName());
                        holder.acurracy.setText("fiabilidad : "+ listaDiagnosis.get(position).getIssue().getAccuracy());
                        holder.TreatmentDescription.setText("tratamiento : "+ listaDiagnosis.get(position).getIssue2().getTreatmentDescription());
                        holder.name.setText("nombre : "+ listaDiagnosis.get(position).getIssue2().getProfName());


                    }

                    System.out.println(listaDiagnostico.get(position).getIssue2().getMedicalCondition());

                }

                @Override
                public void onFailure(Call<FullIssue> call, Throwable t) {

                }
            });
        }







    public class AdaptadorDiagnostico extends RecyclerView.ViewHolder{

        TextView descripcionCorta;
        TextView descripcionLarga;
        TextView acurracy;
        TextView MedicalContion;
        TextView name;
        TextView TreatmentDescription;
        Spinner spspecialization;

        Button vermas;


        public AdaptadorDiagnostico(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvname);
            acurracy = itemView.findViewById(R.id.tvAcurracy);
            descripcionCorta = itemView.findViewById(R.id.tvDescripcionCorta);
            MedicalContion = itemView.findViewById(R.id.tvMedicalContion);
            TreatmentDescription = itemView.findViewById(R.id.tvTreatmentDescription);
            spspecialization = itemView.findViewById(R.id.spSpecialisation);
            vermas =  itemView.findViewById(R.id.botonVerMas);

        }



    }



}