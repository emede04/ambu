package com.example.ambu.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ambu.R;
import com.example.ambu.models.Diagnosis;
import com.example.ambu.models.DiagnosisFirestore;
import com.example.ambu.models.Paciente;
import com.example.ambu.utils.SharedPreferencesUtils;
import com.example.ambu.view.Med.ui.pacientes.PacientesAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PacienteProfile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PacienteProfile_Fragment extends Fragment {

    AdaptadorDiagnostico  adapter;
    RecyclerView recyclerViewDiagnostico;
    private FirebaseFirestore db;
    String documento;
    TextView tvapellido;
    TextView tvnombrepac;
    TextView tvaltura;
    TextView tvgenero;
    ImageView tvfoto_paciente;
    TextView tvedad;
    TextView tvpeso;
    public static PacienteProfile_Fragment newInstance() {
        PacienteProfile_Fragment fragment = new PacienteProfile_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        documento =  getArguments().getString("documento");

        System.out.println("hola wenas"+documento);
        if (getArguments() == null) {
            Toast.makeText(getContext(), "No se han podido sacar los datos respecto a su usuario", Toast.LENGTH_SHORT).show();
        }else{


        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       tvnombrepac = view.findViewById(R.id.nombre_paciente);
       tvfoto_paciente = view.findViewById(R.id.foto_paciente);
       tvapellido = view.findViewById(R.id.tv_apellido);
        tvedad = view.findViewById(R.id.edad_paciente);
        tvaltura = view.findViewById(R.id.rtvaltura);
        tvgenero = view.findViewById(R.id.Profile_genero);
        tvpeso = view.findViewById(R.id.tvpeso);

        tvnombrepac.setText("Nombre :"+documento);
        init(documento);
        cargarPac();
    }

    private void cargarPac() {

        DocumentReference rf = db.collection("Pacientes").document(documento);
        rf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot docu = task.getResult();
                if(docu.exists()) {
                    String apellido  = (String) docu.getData().get("apellido");
                   String  altura  = (String) docu.getData().get("altura");
                    String  imagen  = (String) docu.getData().get("imagen");
                    String  peso  = (String) docu.getData().get("peso");
                    String  genero  = (String) docu.getData().get("genero");
                    String  edad  = (String) docu.getData().get("edad");


                    tvapellido.setText("Apellidos: "+apellido);
                    tvedad.setText("Edad: "+edad+" años");
                    tvaltura.setText("Altura:"+altura+" cm");
                    tvgenero.setText("Genero: "+genero);
                    tvpeso.setText("peso: "+peso +"kg");
                    Glide.with(getContext()).
                            load(imagen)
                            .centerCrop()
                            .error(R.mipmap.ic_launcher)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                            .into(tvfoto_paciente);

                }

            }});
    }

    public void init(String documento){
        db = FirebaseFirestore.getInstance();
        recyclerViewDiagnostico.setLayoutManager(new LinearLayoutManager(getContext()));
        System.out.println("Hola");
        Query query = db.collection("Pacientes").document(documento).collection("Diagnosticos");



        FirestoreRecyclerOptions<DiagnosisFirestore> options = new FirestoreRecyclerOptions.Builder<DiagnosisFirestore>()
                .setQuery(query, DiagnosisFirestore.class)
                .build();

        adapter = new AdaptadorDiagnostico(options,getContext());
        recyclerViewDiagnostico.setHasFixedSize(true);
        recyclerViewDiagnostico.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_paciente_profile_, container, false);
        recyclerViewDiagnostico = view.findViewById(R.id.recycler_diagnosticos);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}