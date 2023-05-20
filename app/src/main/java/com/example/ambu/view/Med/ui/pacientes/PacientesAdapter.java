package com.example.ambu.view.Med.ui.pacientes;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PacientesAdapter extends RecyclerView.Adapter<PacientesAdapter.RecyclerHolder> implements View.OnClickListener {

    private View.OnClickListener sensor;
    ArrayList<Pacientes> listaPacientes;
    public PacientesAdapter(ArrayList<Pacientes> l) {
        this.listaPacientes = l;
        System.out.println("tamaño" + l.size());
    }


    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if (sensor != null) {
            sensor.onClick(view);
        }
    }


    public class RecyclerHolder extends RecyclerView.ViewHolder {
        /*TextView nombre;
        TextView apellidos;
        TextView queja;
        TextView medicacion;
        TextView urlimagen; */


        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

           /* autor = itemView.findViewById(id.autor);
            titulo = itemView.findViewById(id.titulo);
            contenido = itemView.findViewById(id.contenido2);
            url = itemView.findViewById(id.url);
            fecha = itemView.findViewById(id.fecha);*/
        }


    }

}