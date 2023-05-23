package com.example.ambu.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambu.R;
import com.example.ambu.R.*;
import com.example.ambu.models.User;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;
import com.example.ambu.utils.BaseDeDatosLocal;
import com.example.ambu.view.Med.MedActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    Button bLogin;
    Button bRegister;

    TextView vUser;
    TextView vPass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    BaseDeDatosLocal BD;
    String estado = "";

    User usuario;
    ApiMedicService api;
    String ApimedicUserName = "mariadolorespersonal@gmail.com";
    String ApiMedicPassword = "e5XNa9f6P2LgAj4r7";
    String ApiMedicUrl = "https://sandbox-authservice.priaid.ch/login";

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // Inflate the layout for this fragment
        init(view);
        BD = new BaseDeDatosLocal(view.getContext());

        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        api = Apis.apiMedicServiceLogin();

        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View v) {    //metodo para poner todos los lisenter de la de golpe

        switch (v.getId()/*to get clicked view id**/) {
            case id.botonLogin:
                String txtUser = vUser.getText().toString();
                String txtPassword = vPass.getText().toString();
                boolean kapasao = BD.verifica(txtUser,txtPassword);
                if (kapasao) {
                    //llamamamos al metodo que va a crear el token
                    createToken(ApimedicUserName, ApiMedicPassword, ApiMedicUrl, v);
                    login(txtUser,txtPassword,v);
                    Toast.makeText(this.getActivity(), "Pa dentro", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this.getActivity(), MedActivity.class);
                    estado =  SharedPreferencesUtils.SacarDatos("usuario-estado",v);

                    startActivity(intent);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                    builder.setMessage("los datos son incorrectos")
                            .setTitle("Error");
                    builder.show();

                }


                break;
            case id.botonRegistar:
                createToken(ApimedicUserName, ApiMedicPassword, ApiMedicUrl, v);

                txtUser = vUser.getText().toString();
                txtPassword = vPass.getText().toString();
                if ((txtUser.equals("")) || (txtPassword.equals(""))) {
                    Toast.makeText(getActivity(), "Has de rellenar todos los campos", Toast.LENGTH_LONG + 2).show();
                    vUser.setText("");
                    vPass.setText("");
                    break;
                } else {
                    kapasao = BD.verifica(txtUser, txtPassword);
                    if (kapasao) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setMessage("Ya existen los datos que desea registrar")
                                .setTitle("Error")
                                .setIcon(android.R.drawable.ic_delete);
                        builder.show();
                    } else {
                        BD.insertUsuarios(txtUser, txtPassword);
                        Toast.makeText(getActivity(), "Su usario ha sido registrado tanto localmente como online", Toast.LENGTH_LONG + 2).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("usuario",txtUser);
                        bundle.putString("password",txtPassword); // Put anything what you want
                        Fragment fragment = new registerPaciente();

                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();                    }

                }
        }
    }


    public void init(View view) {
        bLogin = view.findViewById(id.botonLogin);
        bRegister = view.findViewById(id.botonRegistar);
        vPass = view.findViewById(id.txtUsuario);
        vUser = view.findViewById(id.txtPass);
        usuario = new User();

    }


    @RequiresApi(api = Build.VERSION_CODES.O)

    public void createToken(String user, String password, String url, View view) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(),
                "HmacMD5");
        String hash = "";
        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(secretKeySpec);
            byte[] resultado = mac.doFinal(url.getBytes());
            hash = Base64.getEncoder().encodeToString(resultado);
            Call<ResponseBody> call = api.loginApiMedic("Bearer " + user + ":" + hash);
            Log.e("HASH", hash);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        SharedPreferencesUtils.saveToke("ApiMedicToken", jsonObject.getString("Token".toString()), view);
                        Log.d("data", SharedPreferencesUtils.SacarDatos("ApiMedicToken", view));
                        Toast.makeText(getActivity(), "dentro", Toast.LENGTH_SHORT).show();
                    } catch (JSONException exception) {
                        exception.printStackTrace();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getActivity(), "ha ocurrido un error para recuperar la api", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {

        }


    }

    public void  login(String user, String password,View view) {
        DocumentReference rf = db.collection("Logins").document(user);
        rf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot docu = task.getResult();
                if(docu.exists()){
                    String xpassword = (String) docu.getData().get("password");

                    if(password.equals(xpassword)){
                        Toast.makeText(view.getContext(), "adentro", Toast.LENGTH_SHORT).show();
                        estado = (String) docu.getData().get("estado");
                        SharedPreferencesUtils.saveToke("usuario-estado",estado,view);
                        SharedPreferencesUtils.saveToke("usuario-nombre",user,view);


                    }else{
                        Toast.makeText(view.getContext(), "contraseña incorrecta", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(view.getContext(), "el usuario no existe", Toast.LENGTH_SHORT).show();
                }
                vPass.setText("");
                vUser.setText("");

            }
        });


    }
    public int register(String user, String pass) {
        Map<String, Object> muser = new HashMap<>();
        muser.put("user", user);
        muser.put("password", pass);
        muser.put("imagen","https://cdn-icons-png.flaticon.com/512/1467/1467464.png");
        muser.put("estado", "paciente");
        db.collection("Pacientes").document(user).set(muser);
        vPass.setText("");
        vUser.setText("");

        return 1;
    }
}