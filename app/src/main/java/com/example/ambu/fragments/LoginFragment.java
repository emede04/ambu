package com.example.ambu.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambu.R;
import com.example.ambu.R.*;
import com.example.ambu.utils.localDB;
import com.example.ambu.view.MainActivity;
import com.example.ambu.view.Med.MedActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    Button bLogin;
    Button bRegister;
    String user;
    String password;
    TextView vUser;
    TextView vPass;
    localDB bd;
    // TODO: Rename and change types of parameters


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
        bLogin.setOnClickListener( this);
        bRegister.setOnClickListener(this);

        return view;
    }

    public void login(){

    }
    public void register(){

    }

    public void onClick(View v) {    //metodo para poner todos los lisenter de la de golpe

        switch (v.getId()/*to get clicked view id**/) {
            case id.botonLogin:

                //boolean kapasao=   bd.verifica(vUser.getText().toString(), vPass.getText().toString());
             //   if(kapasao){
                    Toast.makeText(this.getActivity(), "Pa dentro", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this.getActivity(), MedActivity.class);


                    startActivity(intent);

             /*  }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                    builder.setMessage("los datos son incorrectos")
                            .setTitle("Error");
                    builder.show();

                }*/


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
                  /*  kapasao = bd.verifica(texto1,texto2);
                    if (kapasao){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                        builder.setMessage("Ya existen los datos que desea registrar")
                                .setTitle("Error")
                                .setIcon(android.R.drawable.ic_delete);
                        builder.show();
                    } else {
                        Intent intent = new Intent(this.getActivity(), MainActivity.class);
                        intent.putExtra("user",vUser.getText());
                        startActivity(intent);
                        finalize();



                    } */
                }
            default:
                break;
        }
    }

    public int checkmedorpac(){
        //este metodo checkea si el id introducido es de un medico o un paciente y carga los datos correspondientes para paciente la informacion base y el medico sintomas horario etc
        return 1;
    }



    public void init(View view){
        bLogin = view.findViewById(id.botonLogin);
        bRegister = view.findViewById(id.botonRegistar);
        vPass = view.findViewById(id.txtUsuario);
        vUser = view.findViewById(id.txtPass);
    }
}