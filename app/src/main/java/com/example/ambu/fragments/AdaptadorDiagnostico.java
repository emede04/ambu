package com.example.ambu.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.R;
import com.example.ambu.models.DiagnosisFirestore;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class AdaptadorDiagnostico extends FirestoreRecyclerAdapter<DiagnosisFirestore, AdaptadorDiagnostico.DiagnosisViewHolder> implements View.OnClickListener {
    ArrayList<String> spc;
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

        holder.espcialidades.setAdapter(null);
        String especialidad = model.getSpecialidad();
        System.out.println(especialidad);
        String output1 = especialidad.replace("[", "");
        String output2 = output1.replace("]", "");

        String[] output = output2.split(",");

        setUpSpecialidad(output,holder,model);


        holder.sintomaspresentados.setText(model.getSintomas_presentado());
        holder.molestia.setText(model.getIssue());
        holder.fecha.setText((model.getFecha()));
        holder.fiabilidad.setText(model.getAcurracy());
        holder.tratamiento.setText(model.getTratamiento());
        holder.descripcion.setText(model.getDescripcion_larga());

        holder.fiabilidad.setEnabled(false);
        holder.fecha.setEnabled(false);
        holder.molestia.setEnabled(false);
        holder.sintomaspresentados.setEnabled(false);
        holder.tratamiento.setEnabled(false);
        holder.descripcion.setEnabled(false);

        holder.tratamiento.setElegantTextHeight(true);

    }

    private void setUpSpecialidad(String[] output, DiagnosisViewHolder holder, DiagnosisFirestore model) {
        spc = new ArrayList<>();
        spc.clear();
        System.out.println(output.length);

        for(int i = 0; i < output.length;){
            spc.add(output[i]);
            i++;
        }




        ArrayAdapter adapter = new ArrayAdapter<>(holder.espcialidades.getContext(), android.R.layout.simple_list_item_1, spc);
        holder.espcialidades.setAdapter(adapter);

    }



    public class DiagnosisViewHolder extends RecyclerView.ViewHolder{
        Spinner espcialidades;
        TextView tratamiento, fecha, fiabilidad, molestia; TextView descripcion,sintomaspresentados;





        public DiagnosisViewHolder(@NonNull View itemView) {
            super(itemView);

            espcialidades = itemView.findViewById(R.id.especialidadesSpinner);
            tratamiento = (TextView) itemView.findViewById(R.id.tratamiento);
            fecha =  (TextView)itemView.findViewById(R.id.fechaTextView);
            descripcion = (TextView) itemView.findViewById(R.id.descripcion);
            sintomaspresentados = (TextView) itemView.findViewById(R.id.sintomasTextView);
            fiabilidad =  (TextView)itemView.findViewById(R.id.fiabilidadTextView);
            molestia =  (TextView)itemView.findViewById(R.id.nombre);




        }
}}
