package com.example.ambu.view.Med.ui.miscelaneaos;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SintomasAdapter extends RecyclerView.Adapter<SintomasAdapter.RecyclerHolder> {


    public SintomasAdapter() {


    }

    @NonNull
    @Override
    public SintomasAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SintomasAdapter.RecyclerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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