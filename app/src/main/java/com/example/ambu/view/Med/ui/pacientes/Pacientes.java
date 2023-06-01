package com.example.ambu.view.Med.ui.pacientes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.*;

import com.example.ambu.R;
import com.example.ambu.fragments.registerPaciente;
import com.example.ambu.models.Paciente;
import com.example.ambu.view.Med.ui.consulta.Consulta;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.checkerframework.checker.units.qual.A;

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
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @SuppressLint("NotifyDataSetChanged")

    public void init(){
        db = FirebaseFirestore.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        System.out.println("Hola");
        Query query = FirebaseFirestore.getInstance()
                .collection("Pacientes")
                .limit(50);
        FirestoreRecyclerOptions<Paciente> options = new FirestoreRecyclerOptions.Builder<Paciente>()
                .setQuery(query, Paciente.class)
                .build();

        adapter = new PacientesAdapter(options,getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int pos =  recyclerView.getChildAdapterPosition(view);
                setUpWarinig(view,pos);

            }
        });
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



    public void setUpWarinig(View view, int posicion) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Bundle args = new Bundle();
                        args.putString("nombre", adapter.getItem(posicion).getNombre());
                        args.putString("sintomas",adapter.getItem(posicion).getSintomas());
                        args.putString("edad",adapter.getItem(posicion).getEdad());
                        args.putString("genero",adapter.getItem(posicion).getGenero());

                        Navigation.findNavController(view).navigate(R.id.nav_consulta, args);



                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        String nombre = adapter.getItem(posicion).getNombre();
                      //  Toast.makeText(view.getContext(), nombre, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Quieres realizar el diagnosito de este paciente?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.deletePaciente(viewHolder.getBindingAdapterPosition());
            DocumentSnapshot nombre = adapter.getName(viewHolder.getBindingAdapterPosition());
            String nombre_paciente = nombre.getString("nombre");
        }
    };


    }


