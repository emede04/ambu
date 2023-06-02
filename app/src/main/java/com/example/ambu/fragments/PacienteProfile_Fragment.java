package com.example.ambu.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ambu.R;
import com.example.ambu.models.Diagnosis;
import com.example.ambu.models.DiagnosisFirestore;
import com.example.ambu.models.Paciente;
import com.example.ambu.view.Med.ui.pacientes.PacientesAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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
        init(documento);

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