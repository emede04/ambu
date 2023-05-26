package com.example.ambu.view.Med.ui.consulta;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ambu.R;
import com.example.ambu.databinding.*;
import com.example.ambu.models.Diagnosis;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Consulta extends Fragment {

  //  private FragmentGalleryBinding binding;
    String nombreUsuario;
    String edad;
    String sintomas;
    ApiMedicService api = Apis.apiMedicServiceData();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_consulta, container, false);
        Bundle bundle =getArguments();
      String nombre_user =  bundle.getString("nombreusuario");
        generarConsulta("Maria","20","female",view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    public void generarConsulta(String nombreUsuario,String edad, String genero ,View view){










    }


}