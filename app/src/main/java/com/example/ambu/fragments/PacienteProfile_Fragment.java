package com.example.ambu.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    EditText tvapellido;
    TextView tvnombrepac;
    EditText tvaltura;
    EditText tvgenero;
    ImageView tvfoto_paciente;
    EditText tvedad;
    EditText tvpeso;
    FloatingActionButton modificar;
    FloatingActionButton bSintomas;
    private String imagen;

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
        modificar = view.findViewById(R.id.bmodificarPerfil);
        bSintomas = view.findViewById(R.id.bNuevosSintomas);
        tvnombrepac.setText("Nombre :"+documento);
        init(documento);
        cargarPac();
        tvedad.setEnabled(false);
        tvaltura.setEnabled(false);
        tvgenero.setEnabled(false);
        tvpeso.setEnabled(false);
        tvapellido.setEnabled(false);

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarPaciente(view);
                Toast.makeText(view.getContext(), "modifica", Toast.LENGTH_SHORT).show();

            }
        });

        modificar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                    guardarPaciente(view);
                Toast.makeText(view.getContext(), "guardado", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        bSintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpConsulta(view);
            }
        });



    }

    private void guardarPaciente(View view) {
        String apellido = tvapellido.getText().toString();
        String altura = tvaltura.getText().toString();
        String peso = tvpeso.getText().toString();
        String genero = tvgenero.getText().toString();
        String edad = tvedad.getText().toString();

        if (esunnumero(edad)) {
            if (genero.equals("Femenino") || genero.equals("Masculino")) {


                Map<String, Object> update = new HashMap<>();
                update.put("edad", edad);
                update.put("altura", altura);
                update.put("genero", genero);
                update.put("apellido", apellido);
                update.put("peso", peso);

                db.collection("Pacientes").document(documento).update("edad", edad, "altura", altura, "genero", genero, "apellido", apellido, "peso", peso);

                tvedad.setEnabled(false);
                tvaltura.setEnabled(false);
                tvgenero.setEnabled(false);
                tvpeso.setEnabled(false);
                tvapellido.setEnabled(false);


            } else {
                Toast.makeText(view.getContext(), "el genero o el numero no es valido no es valido", Toast.LENGTH_LONG);
                tvgenero.setText("");
            }


        }else{
            Toast.makeText(view.getContext(), "la edad no es valida", Toast.LENGTH_LONG);
            tvedad.setText("");
        }}

        private void cargarPac () {

            DocumentReference rf = db.collection("Pacientes").document(documento);
            rf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot docu = task.getResult();
                    if (docu.exists()) {
                        String apellido = (String) docu.getData().get("apellido");
                        String altura = (String) docu.getData().get("altura");
                        String imagen = (String) docu.getData().get("imagen");
                        String peso = (String) docu.getData().get("peso");
                        String genero = (String) docu.getData().get("genero");
                        String edad = (String) docu.getData().get("edad");


                        tvapellido.setText("Apellidos: " + apellido);
                        tvedad.setText("Edad: " + edad + " años");
                        tvaltura.setText("Altura:" + altura + " cm");
                        tvgenero.setText("Genero: " + genero);
                        tvpeso.setText("peso: " + peso + "kg");
                        Glide.with(getContext()).
                                load(imagen)
                                .centerCrop()
                                .error(R.mipmap.ic_launcher)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                                .into(tvfoto_paciente);

                    }

                }
            });
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


    public void modificarPaciente(View view){
        tvedad.setEnabled(true);
        tvaltura.setEnabled(true);
        tvgenero.setEnabled(true);
        tvpeso.setEnabled(true);
        tvapellido.setEnabled(true);
        tvedad.setText("");
        tvaltura.setText("");
        tvgenero.setText("");
        tvpeso.setText("");
        tvapellido.setText("");
        Toast.makeText(view.getContext(), "Ingrese los datos que quiera modificar", Toast.LENGTH_SHORT).show();

    }

    public static boolean esunnumero(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void setUpConsulta(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Bundle args = new Bundle();
                        args.putString("nombre", documento);

                        Fragment fragment = new QuickConsulta();
                        fragment.setArguments(args);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        Toast.makeText(view.getContext(), "no se han alterado sus sintomas", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Quieres pedir cita con nuevos sintomas").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }







}