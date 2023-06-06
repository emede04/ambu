package com.example.ambu.view.Med.ui.pacientes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.R;
import com.example.ambu.models.Paciente;
import com.example.ambu.utils.BaseDeDatosLocal;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Pacientes extends Fragment {
    PacientesAdapter  adapter;
    RecyclerView recyclerView;

    private FirebaseFirestore db;
    BaseDeDatosLocal BD;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view  = inflater.inflate(R.layout.fragment_pacientes, container, false);
        recyclerView = view.findViewById(R.id.rListaPacientes);
        BD = new BaseDeDatosLocal(view.getContext());
        return view;
    }

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        init();
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

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
            System.out.println(nombre_paciente);
            BD.borrar(nombre_paciente);
        }
    };


    }


