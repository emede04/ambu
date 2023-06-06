package com.example.ambu.view.Med.ui.pacientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ambu.R;
import com.example.ambu.models.Paciente;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class PacientesAdapter extends FirestoreRecyclerAdapter<Paciente,PacientesAdapter.PacientesViewHolder> implements View.OnClickListener {

    Context context;
    private View.OnClickListener sensor;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PacientesAdapter(@NonNull FirestoreRecyclerOptions<Paciente> options,Context context) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull PacientesAdapter.PacientesViewHolder holder, int position, @NonNull Paciente model) {
        holder.username.setText(model.getNombre());
        holder.useredad.setText((model.getEdad()));
        holder.apellidos.setText(model.getApellido());
        holder.genero.setText(model.getGenero());

        Glide.with(context).
                load(model.getImagen())
                .error(R.mipmap.ic_launcher)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                .into(holder.profile_img);


    }

    @NonNull
    @Override
    public PacientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_pacientes,parent,false);
        view.setOnClickListener(this);

        return  new PacientesViewHolder(view);
    }

    @Override
    public void onClick(View view) {
        if(sensor!=null){
            sensor.onClick(view);


        }
    }

    public void setOnClickListener(View.OnClickListener sensor) {
        this.sensor = sensor;
    }
    public void deletePaciente(int position){
        getSnapshots().getSnapshot(position).getReference().delete();

    }
    public DocumentSnapshot getName(int position){
        return getSnapshots().getSnapshot(position);
    }



    public class PacientesViewHolder extends RecyclerView.ViewHolder{
        ImageView profile_img;
        TextView username, useredad, genero, sintomas,apellidos;





        public PacientesViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_img = itemView.findViewById(R.id.image_paciente);
            username = (TextView) itemView.findViewById(R.id.text_nombre_paciente);
            useredad =  (TextView)itemView.findViewById(R.id.text_edad_paciente);
            genero =  (TextView)itemView.findViewById(R.id.text_genero_paciente);
            apellidos =  (TextView)itemView.findViewById(R.id.txtLastName);




        }





    }}