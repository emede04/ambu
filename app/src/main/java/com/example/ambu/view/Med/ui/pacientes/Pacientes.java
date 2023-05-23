package com.example.ambu.view.Med.ui.pacientes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.*;

import com.example.ambu.R;
import com.example.ambu.models.Paciente;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Pacientes extends Fragment {
    PacientesAdapter  adapter;
    RecyclerView recyclerView;
    private FirebaseFirestore db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view  = inflater.inflate(R.layout.fragment_pacientes, container, false);
        recyclerView = view.findViewById(R.id.rListaPacientes);

        return view;
    }

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        init();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @SuppressLint("NotifyDataSetChanged")

    public void init(){
        db = FirebaseFirestore.getInstance();

        System.out.println("Hola");
        Query query = FirebaseFirestore.getInstance()
                .collection("Pacientes")
                .limit(50);
        FirestoreRecyclerOptions<Paciente> options = new FirestoreRecyclerOptions.Builder<Paciente>()
                .setQuery(query, Paciente.class)
                .build();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PacientesAdapter(options,getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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