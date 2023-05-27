package com.example.ambu.view.Med.ui.consulta;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.R;
import com.example.ambu.databinding.*;
import com.example.ambu.models.Diagnosis;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Consulta extends Fragment {

  //  private FragmentGalleryBinding binding;

  FirebaseFirestore db = FirebaseFirestore.getInstance();
  EditText tvpaciente;
  EditText tvedad;
  EditText tvgenero;
  Spinner spSintomas;
  Context contexto;
  ApiMedicService api;
  ArrayList<Diagnosis> listaDiagnostico;
  String nombrePaciente ="";
  String sintomas ="";
  String genero = "";
  String edad ="";
  RecyclerView rvdiagnositco;
  ConsultaAdapter adapter;

  public Consulta() {
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {


    db = FirebaseFirestore.getInstance();
    View view = inflater.inflate(R.layout.fragment_consulta, container, false);
     this.contexto = view.getContext();
   listaDiagnostico = new ArrayList<Diagnosis>();
    init(view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    if(getArguments() == null || getArguments().isEmpty()) {
      Snackbar.make(view, "Realiza una consulta manual", Snackbar.LENGTH_LONG)
              .setAction("Action", null).show();


    }
    else{
      cargarDatosPac();
      tvedad.setEnabled(false);
      tvpaciente.setEnabled(false);
      tvgenero.setEnabled(false);
      tvedad.setText("edad: "+edad);
      tvpaciente.setText("paciente: "+nombrePaciente);
      tvgenero.setText("genero:");
      generarConsulta(nombrePaciente,view);
      Toast.makeText(view.getContext(), nombrePaciente, Toast.LENGTH_SHORT).show();
    }




  }



  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

  public void init(View view){
    rvdiagnositco = view.findViewById(R.id.RvDiagnostico);
    spSintomas = view.findViewById(R.id.spinnerSintomas);
    tvpaciente = view.findViewById(R.id.tvNombrePaciente);
    tvedad = view.findViewById(R.id.tvedad);
    tvgenero = view.findViewById(R.id.tvGenero);
  }

  public void generarConsulta(String nombreUsuario,View view) {

    DocumentReference rf = db.collection("Pacientes").document(nombreUsuario);
    rf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        DocumentSnapshot docu = task.getResult();
        if (docu.exists()) {
          ProgressDialog pd = new ProgressDialog(view.getContext());
          pd.setMessage("loading");
          pd.show();

          //sacamos los datos que se encuentrar en la base de datos necesarios para poder generar una consulta
          String edad = docu.getString("edad");
          String sintomas = docu.getString("sintomas");
          String genero = docu.getString("genero");

          //metdoo para poner los datos del paciente


          if (genero.equals("Masculino")) {
            genero = "male";
          } else if (genero.equals("Femenino")) {
            genero = "female";
          }
          System.out.println(sintomas);
          System.out.println(genero);
          System.out.println(edad);

          if (task.isComplete() && task.isSuccessful()) {
            pd.dismiss();
            System.out.println("voy por aqui");
            generarDiagnostico(view,edad,sintomas,genero);
          }

        }
      }
    });
  }

  private void cargarDatosPac() {
    this.nombrePaciente = getArguments().getString("nombre");
    this.edad = getArguments().getString("edad");
    this.sintomas = getArguments().getString("sintomas");
    this.genero = (String) getArguments().get("genero");
  }

  public void generarDiagnostico(View view,String edad,String sintomas,String genero){
    ProgressDialog pd = new ProgressDialog(view.getContext());
    pd.setMessage("cargando diagnosticos");
    Apis aqui = new Apis();
    api = aqui.apiMedicServiceData();
    System.out.println("por aqui ");
    pd.show();
    Call<List<Diagnosis>> call = api.getDiagnosis(SharedPreferencesUtils.SacarDatos("ApiMedicToken",view), "json","es-es", genero, edad,sintomas);
    call.enqueue(new Callback<List<Diagnosis>>() {
      @Override
      public void onResponse(Call<List<Diagnosis>> call, Response<List<Diagnosis>> response) {
          if(response.body().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Su sintomologia no ha generado ningun diagnostico");


          }else{
           ProgressDialog pd = ProgressDialog.show(view.getContext(), "loading", "estamos generando su diagnostico");
            if(response.isSuccessful()){
              pd.cancel();
            }

          if (!response.body().isEmpty()) {

            for(int i = 0; i< response.body().size();){

              listaDiagnostico.add(response.body().get(i));
              i++;
            }
            adapter = new ConsultaAdapter(contexto, listaDiagnostico);
            rvdiagnositco.setLayoutManager(new LinearLayoutManager(contexto,LinearLayoutManager.HORIZONTAL, false));
            rvdiagnositco.setAdapter(adapter);
            adapter.notifyDataSetChanged();



          }

          else{
            Toast.makeText(view.getContext(), "no se ha podido generar consulta", Toast.LENGTH_SHORT).show();
            pd.dismiss();

          }





      }}




    @Override
      public void onFailure(Call<List<Diagnosis>> call, Throwable t) {

      }
    });

  }

}

