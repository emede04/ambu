package com.example.ambu.view.Med.ui.consulta;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.view.Med.ui.miscelaneaos.SintomasAdapter;

public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.AdaptadorDiagnostico> implements View.OnClickListener {


    private View.OnClickListener sensor;





    @NonNull
    @Override
    public AdaptadorDiagnostico onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDiagnostico holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if(sensor!=null){
            sensor.onClick(view);
        }
    }

    public class AdaptadorDiagnostico extends RecyclerView.ViewHolder{


        public AdaptadorDiagnostico(@NonNull View itemView) {
            super(itemView);
        }
    }
}