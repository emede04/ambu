package com.example.ambu.view.Med.ui.consulta;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.R;
import com.example.ambu.databinding.*;
import com.example.ambu.models.Diagnosis;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

  String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1hcmlhZG9sb3Jlc3BlcnNvbmFsQGdtYWlsLmNvbSIsInJvbGUiOiJVc2VyIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc2lkIjoiMTE0NzIiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3ZlcnNpb24iOiIyMDAiLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xpbWl0IjoiOTk5OTk5OTk5IiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9tZW1iZXJzaGlwIjoiUHJlbWl1bSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbGFuZ3VhZ2UiOiJlbi1nYiIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvZXhwaXJhdGlvbiI6IjIwOTktMTItMzEiLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL21lbWJlcnNoaXBzdGFydCI6IjIwMjItMTEtMjgiLCJpc3MiOiJodHRwczovL3NhbmRib3gtYXV0aHNlcnZpY2UucHJpYWlkLmNoIiwiYXVkIjoiaHR0cHM6Ly9oZWFsdGhzZXJ2aWNlLnByaWFpZC5jaCIsImV4cCI6MTY4NTA3MjgzOCwibmJmIjoxNjg1MDY1NjM4fQ.CS8tl0knhqna1fkAgOX4_b8EMN2sIhf1QId_qoxt1AY";
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  TextView tvpaciente;
  TextView tvedad;
  Spinner spSintomas;

  ApiMedicService api;
  ArrayList<Diagnosis> listaDiagnostico;
  String nombrePaciente ="";
  String sintomas ="";
  String edad ="";
  RecyclerView rvdiagnositco;

  public Consulta() {
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    api = Apis.apiMedicServiceData();
    db = FirebaseFirestore.getInstance();
    View view = inflater.inflate(R.layout.fragment_consulta, container, false);
    listaDiagnostico = new ArrayList<>();
    init(view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    if(getArguments() == null || getArguments().isEmpty()) {
      Toast.makeText(getContext(), "no se han podido conseguir los datos del paciente", Toast.LENGTH_SHORT).show();
    }
    else{
      cargarDatosPac();
      tvedad.setText(edad);
      tvpaciente.setText(nombrePaciente);
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
            generarDiagnostico();
          }

        }
      }
    });
  }

  private void cargarDatosPac() {
    this.nombrePaciente = getArguments().getString("nombre");
    this.edad = getArguments().getString("edad");
    this.sintomas = getArguments().getString("sintomas");
  }

  public void generarDiagnostico(){
    Call<List<Diagnosis>> call = api.getDiagnosis(token, "json","es-es", "female", "1982","[9,10]");
    call.enqueue(new Callback<List<Diagnosis>>() {
      @Override
      public void onResponse(Call<List<Diagnosis>> call, Response<List<Diagnosis>> response) {
            if(response.isSuccessful()){

            }



      }

      @Override
      public void onFailure(Call<List<Diagnosis>> call, Throwable t) {

      }
    });

  }

}

