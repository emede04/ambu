package com.example.ambu.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ambu.R;
import com.example.ambu.models.Paciente;
import com.example.ambu.models.Symptom;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.BaseDeDatosLocal;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registerPaciente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registerPaciente extends Fragment implements View.OnClickListener {

    String[] genres = new String[] {"Masculino", "Femenino"};
    String usuario;
    String password;
    Paciente paciente;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    BaseDeDatosLocal BD;
     TextInputLayout tilPassword ;
     TextInputEditText tvContrasenia;
     TextInputLayout tilConfirmPass;
     TextInputEditText tvPass;
     TextInputEditText tvnombre;
     TextInputEditText tvapellido;
     TextInputEditText tietAge;
     TextInputEditText tvPeso;
     TextInputEditText tvAltura;
     TextInputEditText tvedad;
    ApiMedicService api = Apis.apiMedicServiceData();
    ArrayAdapter<Symptom> adaptador_sintomas;
    ArrayAdapter<String> genresMenuAdapter;
    int posgenero;
    Button bConfirmar;
    Button bCancelar;
    ArrayAdapter<String> sintomas_nombre_adapter;
    AutoCompleteTextView sintomasMenu;
    Button bSintomas;
    String user;
    AutoCompleteTextView genresMenu;
    ListView vListaSintomas;
    ArrayList<Symptom> listaS;
    String sintomasraw;
    String sintomas_name;
    ArrayList listaSintomas_seleccionados;
    String sintomasNombre;
    Spinner spSintomasSeleccionados;
    public registerPaciente() {
        // Required empty public constructor
    }

    public static registerPaciente newInstance(String param1, String param2) {
        registerPaciente fragment = new registerPaciente();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        usuario =  bundle.getString("usuario");
       password = bundle.getString("password");
        sintomas_name = "";
      BD = new BaseDeDatosLocal(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_paciente, container, false);

        setupGenero(view);
            init(view);
        bConfirmar.setOnClickListener(this);
        bCancelar.setOnClickListener(this);
        bSintomas.setOnClickListener(this);

        tvnombre.setText(usuario);
        tvPass.setText(password);
        tvnombre.setEnabled(false);
        tvPass.setEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



         sintomasraw = SharedPreferencesUtils.SacarDatos("idSintomas",view);
        String sintomasparseao = sintomasraw.replace("[", "");
        String sintomasparseao2 = sintomasparseao.replace("]", "");

        String[] sint = sintomasparseao2.split(",");
        System.out.println(sint.length);
        setUpSintomasRegister(sint, view);
    }





    private void setUpSintomasRegister(String[] ids, View view) {

        listaSintomas_seleccionados = new ArrayList<Symptom>();
        ArrayList<Symptom> aux = new ArrayList<>();

        Call<List<Symptom>> listCall = api.getAllSymptoms(SharedPreferencesUtils.SacarDatos("ApiMedicToken", view), "json", "es-es");
        listCall.enqueue(new Callback<List<Symptom>>() {
            @Override
            public void onResponse(Call<List<Symptom>> call, Response<List<Symptom>> response) {
                if (response.isSuccessful()) {


                    int i = 0;
                    while (i < ids.length) {
                        for (Symptom s : response.body()) {

                            if (String.valueOf(s.getID()).equals(ids[i])) {
                                aux.add(s);

                            }

                        }
                        i++;

                    }

                    listaSintomas_seleccionados = quitarduplicados(aux);

                    ArrayAdapter adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, listaSintomas_seleccionados);

                    String sintomas_nombre = parseSymtom(listaSintomas_seleccionados, view);

                    spSintomasSeleccionados.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<List<Symptom>> call, Throwable t) {

            }
        });
    }



    private void setupGenero(View view) {
        genresMenuAdapter = new ArrayAdapter<String>(
                view.getContext(),
                R.layout.genre_dropdown_menu,
                genres);
         genresMenu = (AutoCompleteTextView) view.findViewById(R.id.tvGenero);
        genresMenu.setThreshold(1);
        genresMenu.setAdapter(genresMenuAdapter);
    }





    private void init(View view){
        tilPassword = view.findViewById(R.id.rpassword);
        tvContrasenia = view.findViewById(R.id.rpassword2);
        tilConfirmPass = view.findViewById(R.id.confirm_pass_text_input);
         tvPass = view.findViewById(R.id.confirm_pass_edit_text);
         tvnombre = view.findViewById(R.id.Rusername);
         tvapellido = view.findViewById(R.id.rApellido);
         tietAge = view.findViewById(R.id.rEdad);
         tvPeso = view.findViewById(R.id.rPeso);
         tvAltura = view.findViewById(R.id.rAltura);
         tvedad = view.findViewById(R.id.rEdad);
         bCancelar = view.findViewById(R.id.bcancelar);
         bConfirmar = view.findViewById(R.id.bconfirmar);
        spSintomasSeleccionados = view.findViewById(R.id.spinner_registerSintomas);
        bSintomas = view.findViewById(R.id.bSintomas);
    }

    public int register(View v) {
        AlertDialog.Builder builder;
        usuario = tvnombre.getText().toString();
        String  pass = tvPass.getText().toString();
        String  password = tvContrasenia.getText().toString();
        String apedillo = tvapellido.getText().toString();
        String edad = tvedad.getText().toString();
        String peso = tvPeso.getText().toString();
        String altura = tvAltura.getText().toString();
       String genero = genresMenu.getText().toString();
        if(!pass.equals(password)){
            System.out.println(pass);
            System.out.println(password);
             builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("las contraseñas no son iguales")
                    .setTitle("Error")
                    .setIcon(android.R.drawable.ic_delete);
            tvContrasenia.setText("");
            builder.show();
            if(usuario.isEmpty() || pass.isEmpty() || apedillo.isEmpty() || edad.isEmpty()|| peso.isEmpty() || altura.isEmpty() || genero.isEmpty() ){
                builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("alguno de los campos esta vacio")
                        .setTitle("Error")
                        .setIcon(android.R.drawable.ic_delete);
                builder.show();
            }
        }else{
            Map<String, Object> muser = new HashMap<>();
            Log.d("USUARIO",pass + apedillo);
            muser.put("nombre", usuario);
            muser.put("password", pass);
            muser.put("apellido", apedillo);
            muser.put("edad", edad);
            muser.put("peso", peso);
            muser.put("altura", altura);
            muser.put("genero", genero);
            muser.put("imagen","https://cdn-icons-png.flaticon.com/512/1467/1467464.png");
            muser.put("estado", "paciente");
            muser.put("sintomas", sintomasraw);


            db.collection("Pacientes").document(usuario).set(muser);
            System.out.println(usuario);
            BD.insertUsuarios(usuario, pass);

            System.out.println(usuario);


        }







        return 1;
    }



    public void borrardocu(String user){
         user = tvnombre.getText().toString();
        db.collection("Pacientes").document(user).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Su usuario no ha sido registrado", Toast.LENGTH_SHORT).show();
            }
        });

    }







    @Override
    public void onClick(View view) {
        switch (view.getId()/*to get clicked view id**/) {
            case R.id.bcancelar:
                borrardocu(usuario);
               break;


            case R.id.bconfirmar:

                Toast.makeText(getActivity(), "Su usario ha sido registrado tanto localmente como online", Toast.LENGTH_LONG + 2).show();
                register(view);
                Fragment fragment = new PacienteProfile_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("documento",usuario);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


                break;

            case R.id.bSintomas:
                Fragment fragment1 = new FragmentSelectSymtom();
                Bundle bundle1 = new Bundle();
                fragment1.setArguments(bundle1);
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.container, fragment1);
                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.commit();

                break;


        }






}

    public ArrayList<Symptom> quitarduplicados(ArrayList<Symptom> prueba) {
        ArrayList<Symptom> limpio = new ArrayList<>();

        Set<String> nombre = new HashSet<>();
        limpio = (ArrayList<Symptom>) prueba.stream()
                .filter(e -> nombre.add(e.getName()))
                .collect(Collectors.toList());
        return limpio;
    }


    public String parseSymtom(ArrayList<Symptom> l,View view){
        SharedPreferencesUtils.saveToke("nombre","",view);

        //para poder pasar los id de los sintomas como query
        //ejemplo query
        //diagnosis?symptoms=[14,20]&
        int incr = 0;
        String parse = "[";
        int c = 0;

        for (Symptom symptom: l
        ) {
            c++;
            if (l.size() == c){
                parse += symptom.getID();

            }else{
                parse += symptom.getID() + ",";
            }
        }


        int x = 0;
        for (Symptom symptom: l
        ) {
            x++;
            if (l.size() == x){
                this.sintomasNombre += symptom.getName();

            }else{
                this.sintomasNombre += symptom.getName()+",";
            }
        }

        System.out.println("mis sintomas"+ sintomasNombre);

        parse += "]";
        System.out.println(parse);

        return parse;

    }
}