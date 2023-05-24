package com.example.ambu.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import java.util.List;
import java.util.Map;

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
     TextInputEditText tietPassword;
     TextInputLayout tilConfirmPass;
     TextInputEditText tietConfirmPass;
     TextInputEditText tietFirstName;
     TextInputEditText tietLastName;
     TextInputEditText tietAge;
     TextInputEditText tietWeight;
     TextInputEditText tietHeight;
     TextInputEditText tietBirthDate;
    ApiMedicService api = Apis.apiMedicServiceData();
    ArrayAdapter<Symptom> adaptador_sintomas;
    ArrayAdapter<String> genresMenuAdapter;
    int posgenero;
    Button bConfirmar;
    Button bCancelar;
    AutoCompleteTextView genresMenu;
    ListView vListaSintomas;
    ArrayList<Symptom> listaS;
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
       // usuario =  bundle.getString("usuario");
       // password =  bundle.getString("password");

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_paciente, container, false);
            setupGenero(view);
            init(view);
        bConfirmar.setOnClickListener(this);
        bCancelar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     //   SacarSintomas(view);


    }

    public String parseSintomas(){
        String sintomas ="";
        //con este metodo coloco los sintomas para que se puedan usar en la llamada a la api
    return sintomas;
    }

    private void setupGenero(View view) {
        genresMenuAdapter = new ArrayAdapter<String>(
                view.getContext(),
                R.layout.genre_dropdown_menu,
                genres);
         genresMenu = (AutoCompleteTextView) view.findViewById(R.id.genre_dropdown);
        genresMenu.setThreshold(1);
        genresMenu.setAdapter(genresMenuAdapter);
    }
    private void init(View view){
        tilPassword = view.findViewById(R.id.password_text_input);
        tietPassword = view.findViewById(R.id.password_edit_text);
        tilConfirmPass = view.findViewById(R.id.confirm_pass_text_input);
         tietConfirmPass = view.findViewById(R.id.confirm_pass_edit_text);
         tietFirstName = view.findViewById(R.id.first_name_edit_text);
         tietLastName = view.findViewById(R.id.last_name_edit_text);
         tietAge = view.findViewById(R.id.age_edit_text);
         tietWeight = view.findViewById(R.id.weight_edit_text);
         tietHeight = view.findViewById(R.id.height_edit_text);
         tietBirthDate = view.findViewById(R.id.birth_date_edit_text);
         bCancelar = view.findViewById(R.id.bcancelar);
         bConfirmar = view.findViewById(R.id.bconfirmar);
    }

    public int register(View v) {
        AlertDialog.Builder builder;
       String user = tietFirstName.getText().toString();
        String  pass = tietConfirmPass.getText().toString();
        String  password = tietPassword.getText().toString();
        String apedillo = tietLastName.getText().toString();
        String edad = tietBirthDate.getText().toString();
        String peso = tietWeight.getText().toString();
        String altura = tietHeight.getText().toString();
       String genero = genresMenu.getText().toString();
        if(!pass.equals(password)){
            System.out.println(pass);
            System.out.println(password);
             builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("las contraseñas no son iguales")
                    .setTitle("Error")
                    .setIcon(android.R.drawable.ic_delete);
            tietConfirmPass.setText("");
            tietPassword.setText("");
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
            muser.put("user", user);
            muser.put("password", pass);
            muser.put("apellido", apedillo);
            muser.put("edad", edad);
            muser.put("peso", peso);
            muser.put("altura", altura);
            muser.put("genero", genero);
            muser.put("imagen","https://cdn-icons-png.flaticon.com/512/1467/1467464.png");
            muser.put("estado", "paciente");
            muser.put("sintomas", "10");


            db.collection("Pacientes").document(user).set(muser);
        }







        return 1;
    }

    public void getText(){

    }

    public void borrardocu(String user){
         user = tietFirstName.getText().toString();
        db.collection("Pacientes").document(user).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Su usuario no ha sido registrado", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void SacarSintomas(View view){

        List<Symptom> listaSintomas = new ArrayList<Symptom>();

        Call<List<Symptom>> listCall = api.getAllSymptoms(SharedPreferencesUtils.SacarDatos("ApiMedicToken", view), "json", "es-es");
        listCall.enqueue(new Callback<List<Symptom>>() {
            @Override
            public void onResponse(Call<List<Symptom>> call, Response<List<Symptom>> response) {

                        for(Symptom symptom : response.body()) {
                            if (!"".equals(symptom.getName())) ;
                            listaSintomas.add(symptom);
                            System.out.println(symptom.getName());

                        }
                adaptador_sintomas = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_expandable_list_item_1,listaSintomas);
                        vListaSintomas.setAdapter(adaptador_sintomas);
                adaptador_sintomas.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<List<Symptom>> call, Throwable t) {

            }
        });


    }




    @Override
    public void onClick(View view) {
        switch (view.getId()/*to get clicked view id**/) {
            case R.id.bcancelar:
                String aux = tietFirstName.getText().toString();

               break;


            case R.id.bconfirmar:

                                Toast.makeText(getActivity(), "Su usario ha sido registrado tanto localmente como online", Toast.LENGTH_LONG + 2).show();
                Fragment fragment = new Fragment();
                Bundle bundle = new Bundle();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;



        }






}







}