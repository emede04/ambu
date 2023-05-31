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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.R;
import com.example.ambu.fragments.FragmentSelectSymtom;
import com.example.ambu.fragments.internetoFragment;
import com.example.ambu.models.Diagnosis;
import com.example.ambu.models.Paciente;
import com.example.ambu.models.Specialisation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.AdaptadorDiagnostico>  implements View.OnClickListener {


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

        holder.tvDiagnostico.setText("Diagnositco: "+ position);
        holder.tvprofname.setText("Nombre clinico : "+ listaDiagnosis.get(position).getIssue().getProfName());
        holder.tvissue.setText("Molestia : "+ listaDiagnosis.get(position).getIssue().getName());
        holder.tvicdName.setText("Descripcion: "+ listaDiagnosis.get(position).getIssue().getIcdName());
        holder.tvAcurracy.setText("fiabilidad: "+ listaDiagnosis.get(position).getIssue().getAccuracy());

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


    public class AdaptadorDiagnostico extends RecyclerView.ViewHolder{
        TextView tvissue;
        TextView tvicdName;
        TextView tvprofname;
        TextView tvAcurracy;
        TextView tvDiagnostico;
        Spinner spspecialization;

        Button vermas;


        public AdaptadorDiagnostico(@NonNull View itemView) {
            super(itemView);
            tvDiagnostico = itemView.findViewById(R.id.tvDiagnostico);
            tvissue = itemView.findViewById(R.id.tvIssue);
            tvicdName = itemView.findViewById(R.id.IcdName);
            tvprofname = itemView.findViewById(R.id.tvprofname);
            spspecialization = itemView.findViewById(R.id.spSpecialisation);
            tvAcurracy = itemView.findViewById(R.id.tvacurracy);
            vermas = itemView.findViewById(R.id.botonVerMas);
        }



    }



}