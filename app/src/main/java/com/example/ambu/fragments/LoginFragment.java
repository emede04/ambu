package com.example.ambu.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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
import com.example.ambu.utils.localDB;
import com.example.ambu.view.Med.MedActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

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
    localDB bd;

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
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        api = Apis.apiMedicServiceLogin();
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View v) {    //metodo para poner todos los lisenter de la de golpe

        switch (v.getId()/*to get clicked view id**/) {
            case id.botonLogin:
                if (true) {
                    //llamamamos al metodo que va a crear el token
                    createToken(ApimedicUserName, ApiMedicPassword, ApiMedicUrl, v);

                    Toast.makeText(this.getActivity(), "Pa dentro", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this.getActivity(), MedActivity.class);

                    startActivity(intent);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                    builder.setMessage("los datos son incorrectos")
                            .setTitle("Error");
                    builder.show();

                }


                break;
            case id.botonRegistar:
                String texto1 = vUser.getText().toString();
                String texto2 = vPass.getText().toString();
                if ((texto1.equals("")) || (texto2.equals(""))) {
                    Toast.makeText(this.getActivity(), "Has de rellenar todos los campos", Toast.LENGTH_LONG + 2).show();
                    vUser.setText("");
                    vPass.setText("");
                    break;
                } else {


                }

            default:
                break;
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
}