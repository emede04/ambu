package com.example.ambu.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ambu.R;
import com.example.ambu.models.Diagnosis;
import com.example.ambu.models.DiagnosisFirestore;
import com.example.ambu.models.Paciente;
import com.example.ambu.view.Med.ui.pacientes.PacientesAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdaptadorDiagnostico extends FirestoreRecyclerAdapter<DiagnosisFirestore, AdaptadorDiagnostico.DiagnosisViewHolder> implements View.OnClickListener {

    Context context;
    public AdaptadorDiagnostico(FirestoreRecyclerOptions<DiagnosisFirestore> options, Context contex) {
        super(options);
        this.context = contex;
    }

    @Override
    public void onClick(View view) {

    }



    @NonNull
    @Override
    public AdaptadorDiagnostico.DiagnosisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_diagnostico_paciente,parent,false);
        return  new AdaptadorDiagnostico.DiagnosisViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull DiagnosisViewHolder holder, int position, @NonNull DiagnosisFirestore model) {
        holder.molestia.setText(model.getIdcName());
        holder.fecha.setText((model.getFecha()));
        holder.fiabilidad.setText(model.getAcurracy());
        holder.icdname.setText(model.getIssue());

        holder.fiabilidad.setEnabled(false);
        holder.icdname.setEnabled(false);
        holder.icdname.setElegantTextHeight(true);

    }


    public class DiagnosisViewHolder extends RecyclerView.ViewHolder{
        Spinner espcialidades;
        TextView icdname, fecha, fiabilidad, molestia;





        public DiagnosisViewHolder(@NonNull View itemView) {
            super(itemView);

            espcialidades = itemView.findViewById(R.id.especialidadesSpinner);
            icdname = (TextView) itemView.findViewById(R.id.icdNameTextView);
            fecha =  (TextView)itemView.findViewById(R.id.fechaTextView);
            fiabilidad =  (TextView)itemView.findViewById(R.id.fiabilidadTextView);
            molestia =  (TextView)itemView.findViewById(R.id.molestiaTextView);




        }
}}
