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
import java.util.Map;

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
    AutoCompleteTextView genresMenu;
    ListView vListaSintomas;
    ArrayList<Symptom> listaS;
    String sintomas_user;
    String sintomas_name;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_paciente, container, false);
        sintomas_user =  SharedPreferencesUtils.SacarDatos("idSintomas",view);
        sintomas_name =  SharedPreferencesUtils.SacarDatos("nombres",view);

        System.out.println(sintomas_name);
            setUpSintomas(view);
        sintomas_nombre_adapter.setNotifyOnChange(true);

        setupGenero(view);
            init(view);
        bConfirmar.setOnClickListener(this);
        bCancelar.setOnClickListener(this);
        bSintomas.setOnClickListener(this);
        sintomasMenu.setOnClickListener(this);

        tvnombre.setText(usuario);
        tvPass.setText(password);
        tvnombre.setEnabled(false);
        tvPass.setEnabled(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     //   SacarSintomas(view);


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


    //TODO QUE SE VEAN LOS SINTOMAS;
    private void setUpSintomas(View view) {
        System.out.println(sintomas_name);
        String[] nombres = sintomas_name.split(",");

        if (nombres.length == 0) {

        } else {
            sintomas_nombre_adapter = new ArrayAdapter<String>(
                    view.getContext(),
                    R.layout.sintomas_dropdown_menu,
                    nombres);
            sintomasMenu = (AutoCompleteTextView) view.findViewById(R.id.sintomasAutoCompleteTextView);
            sintomasMenu.setThreshold(1);
            sintomasMenu.setAdapter(sintomas_nombre_adapter);

        }

    }


    private void init(View view){
        tilPassword = view.findViewById(R.id.password_text_input);
        tvContrasenia = view.findViewById(R.id.password_edit_text);
        tilConfirmPass = view.findViewById(R.id.confirm_pass_text_input);
         tvPass = view.findViewById(R.id.confirm_pass_edit_text);
         tvnombre = view.findViewById(R.id.first_name_edit_text);
         tvapellido = view.findViewById(R.id.last_name_edit_text);
         tietAge = view.findViewById(R.id.age_edit_text);
         tvPeso = view.findViewById(R.id.weight_edit_text);
         tvAltura = view.findViewById(R.id.height_edit_text);
         tvedad = view.findViewById(R.id.age_edit_text);
         bCancelar = view.findViewById(R.id.bcancelar);
         bConfirmar = view.findViewById(R.id.bconfirmar);
        bSintomas = view.findViewById(R.id.bSintomas);
    }

    public int register(View v) {
        AlertDialog.Builder builder;
       String user = tvnombre.getText().toString();
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
            tvPass.setText("");
            tvContrasenia.setText("");
            builder.show();
            if(user.isEmpty() || pass.isEmpty() || apedillo.isEmpty() || edad.isEmpty()|| peso.isEmpty() || altura.isEmpty() || genero.isEmpty() ){
                builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("alguno de los campos esta vacio")
                        .setTitle("Error")
                        .setIcon(android.R.drawable.ic_delete);
                builder.show();
            }
        }else{
            Map<String, Object> muser = new HashMap<>();
            Log.d("USUARIO",pass + apedillo);
            muser.put("nombre", user);
            muser.put("password", pass);
            muser.put("apellido", apedillo);
            muser.put("edad", edad);
            muser.put("peso", peso);
            muser.put("altura", altura);
            muser.put("genero", genero);
            muser.put("imagen","https://cdn-icons-png.flaticon.com/512/1467/1467464.png");
            muser.put("estado", "paciente");
            muser.put("sintomas", sintomas_user);


            db.collection("Pacientes").document(user).set(muser);
            System.out.println(user);


        }







        return 1;
    }

    public void getText(){

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


                break;

            case R.id.bSintomas:
                Fragment fragment = new FragmentSelectSymtom();
                Bundle bundle = new Bundle();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case R.id.sintomasAutoCompleteTextView:
                    setUpSintomas(view);
                    break;
        }






}



}