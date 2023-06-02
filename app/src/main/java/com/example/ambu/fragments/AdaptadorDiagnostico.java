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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_pacientes,parent,false);
        return  new AdaptadorDiagnostico.DiagnosisViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull DiagnosisViewHolder holder, int position, @NonNull DiagnosisFirestore model) {
        holder.username.setText(model.getIssue());
       holder.fecha.setText((model.getFecha()));


    }


    public class DiagnosisViewHolder extends RecyclerView.ViewHolder{
        Spinner espcialidades;
        TextView username, fecha, fiabilidad, molestia;





        public DiagnosisViewHolder(@NonNull View itemView) {
            super(itemView);

            //espcialidades = itemView.findViewById(R.id.spinnerEspecialidades);
            username = (TextView) itemView.findViewById(R.id.textViewNombre);
            fecha =  (TextView)itemView.findViewById(R.id.textViewFecha);
            fiabilidad =  (TextView)itemView.findViewById(R.id.textViewFiabilidad);
            molestia =  (TextView)itemView.findViewById(R.id.textViewMolestia);




        }
}}
