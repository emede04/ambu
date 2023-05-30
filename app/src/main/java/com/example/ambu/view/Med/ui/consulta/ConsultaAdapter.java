package com.example.ambu.view.Med.ui.consulta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.R;
import com.example.ambu.models.Diagnosis;
import com.example.ambu.models.Specialisation;

import java.util.ArrayList;

public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.AdaptadorDiagnostico> implements View.OnClickListener {


    private View.OnClickListener sensor;
    Context context;

    ArrayList<Diagnosis> listaDiagnosis;
    ArrayList<Specialisation> listaEspecialidades;


    public ConsultaAdapter(Context contex, ArrayList<Diagnosis> listaDiagnosis){
        this.listaDiagnosis = listaDiagnosis;
        this.context = contex;
    }


    @NonNull
    @Override
    public AdaptadorDiagnostico onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_diagnostico,parent,false);
        AdaptadorDiagnostico holder = new AdaptadorDiagnostico(vista);
        vista.setOnClickListener(this::onClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDiagnostico holder, int position) {
        holder.tvDiagnostico.setText("Diagnositco: "+ position);
        holder.tvprofname.setText("Nombre clinico : "+ listaDiagnosis.get(position).getIssue().getProfName());
        holder.tvissue.setText("Molestia : "+ listaDiagnosis.get(position).getIssue().getName());
        holder.tvicdName.setText("Descripcion: "+ listaDiagnosis.get(position).getIssue().getIcdName());
        holder.tvAcurracy.setText("fiabilidad: "+ listaDiagnosis.get(position).getIssue().getAccuracy());
        holder.spspecialization.setPrompt("Especializaciones");


    }

    @Override
    public int getItemCount() {
        return listaDiagnosis.size();
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




        public AdaptadorDiagnostico(@NonNull View itemView) {
            super(itemView);
            tvDiagnostico = itemView.findViewById(R.id.tvDiagnostico);
            tvissue = itemView.findViewById(R.id.tvIssue);
            tvicdName = itemView.findViewById(R.id.IcdName);
            tvprofname = itemView.findViewById(R.id.tvprofname);
            spspecialization = itemView.findViewById(R.id.spSpecialisation);
            tvAcurracy = itemView.findViewById(R.id.tvacurracy);

        }



    }

    public void setSpinnerUp(ArrayList<Specialisation> l){

    }

}