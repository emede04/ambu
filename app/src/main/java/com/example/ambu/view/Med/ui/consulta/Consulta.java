package com.example.ambu.view.Med.ui.consulta;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambu.R;
import com.example.ambu.models.Diagnosis;
import com.example.ambu.models.FullIssue;
import com.example.ambu.models.Specialisation;
import com.example.ambu.models.Symptom;
import com.example.ambu.utils.Apis;
import com.example.ambu.utils.Interfaces.ApiMedicService;
import com.example.ambu.utils.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Consulta extends Fragment{

    //  private FragmentGalleryBinding binding;
    String[] genres = new String[]{"Masculino", "Femenino"};
    String sintomasNombre = "";
    String nombres_sintomas;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean enable = false;
    EditText tvpaciente;
    EditText tvedad;
    EditText tvgenero;
    Spinner spSintomas;
    Context contexto;
    ApiMedicService api = Apis.apiMedicServiceData();
    ArrayAdapter<String> genresMenuAdapter;
    AutoCompleteTextView genresMenu;
    ArrayList<Diagnosis> listaDiagnostico;
    ArrayList<Symptom> listaSintomas;
    ArrayList<Symptom> ListaSintomasConsultaManual = new ArrayList<>();
    ArrayList<Symptom> listaSintomas_consulta;
    String nombrePaciente = "";
    String sintomas = "";
    String genero = "";
    String edad = "";
    Toolbar toolbar;
    FloatingActionButton button;
    RecyclerView rvdiagnositco;
    ConsultaAdapter adapter;
    ArrayList<Specialisation> listaEspecialidades;
    Button vermas;
    FullIssue miIssue;
    ArrayList<FullIssue> listaMiFullIssues  = new ArrayList<>();

    public Consulta() {
    }

    @SuppressLint("MissingInflatedId")
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



        if (getArguments() == null || getArguments().isEmpty()) {
            Toast.makeText(contexto, "Haz una conulta manual", Toast.LENGTH_SHORT).show();


            setupGenero(view);
            setUpSpinnerCall(view);
            spSintomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                    Symptom s = new Symptom();
                     s = (Symptom) adapterView.getItemAtPosition(i);
                    if(s!= null && i !=0) {
                        ListaSintomasConsultaManual.add(s);
                        System.out.println(ListaSintomasConsultaManual.size());
                         enable = true;

                    }
                    if(enable){
                        ListaSintomasConsultaManual.add(s);
                        System.out.println(ListaSintomasConsultaManual.size());

                    }

                    Toast.makeText(contexto, "s:"+s.getID(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            } );


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sintomas = "";
                    String edad = String.valueOf(tvedad.getText());
                    String genero = String.valueOf(tvgenero.getText());
                     sintomas = parseSymtom(ListaSintomasConsultaManual,view);
                    System.out.println(ListaSintomasConsultaManual.size());
                    if (genero.equals("Masculino")) {
                        genero = "male";
                    } else if (genero.equals("Femenino")) {
                        genero = "female";
                    }
                    System.out.println(sintomas);
                    if(sintomas.equals("")){
                        Toast.makeText(contexto, "no has seleccionado ningun sintoma", Toast.LENGTH_SHORT).show();
                    }else {
                        spSintomas.setAdapter(null);
                        generarDiagnostico(view, edad, sintomas, genero);

                    }
                    ListaSintomasConsultaManual.clear();

                    //TODO bloquear el boton para ejecutar el diagnostico hasta que no se hayan rellenado todo los campos


                }
            });









        } else {
            setupGenero(view);
            cargarDatosPac();
            tvedad.setEnabled(false);
            tvpaciente.setEnabled(false);
            tvedad.setText(edad);
            tvpaciente.setText(nombrePaciente);
            genresMenu.setText(genero);
            generarConsulta(nombrePaciente, view);
            Toast.makeText(view.getContext(), nombrePaciente, Toast.LENGTH_SHORT).show();

        }





    }

    public void setUpSpinnerCall(View view) {

        listaSintomas = new ArrayList<Symptom>();

        Call<List<Symptom>> listCall = api.getAllSymptoms(SharedPreferencesUtils.SacarDatos("ApiMedicToken", view), "json", "es-es");
        listCall.enqueue(new Callback<List<Symptom>>() {
            @Override
            public void onResponse(Call<List<Symptom>> call, Response<List<Symptom>> response) {

                if (response.isSuccessful()) {
                    for (Symptom symptom : response.body()) {
                        if (symptom.getID()!=56) ;
                        listaSintomas.add(symptom);
                        System.out.println(symptom.getName());
                    }

                    for(Symptom s :response.body()){
                        if(s.getID() ==56){
                            listaSintomas.remove(s);
                        }
                    }

                    ArrayAdapter adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_checked, listaSintomas);
                    spSintomas.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onFailure(Call<List<Symptom>> call, Throwable t) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void init(View view) {
        rvdiagnositco = view.findViewById(R.id.RvDiagnostico);
        spSintomas = view.findViewById(R.id.spinnerSintomas);
        tvpaciente = view.findViewById(R.id.tvNombre);
        tvgenero = view.findViewById(R.id.txtGeneroConsulta);
        tvedad = view.findViewById(R.id.tvedad);
        button = view.findViewById(R.id.floatingActionButton);

    }


    //metodo para generar consulta desde la base de datos
    public void generarConsulta(String nombreUsuario, View view) {

        DocumentReference rf = db.collection("Pacientes").document(nombreUsuario);
        rf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot docu = task.getResult();
                if (docu.exists() && task.isSuccessful()) {
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
                        generarDiagnostico(view, edad, sintomas, genero);
                    }


                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                    builder.setMessage("Ha habido un error sacando los datos ");
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
        //metodo para generar la consulta desde la base de datos o pasar los datos desde la consulta directa
    public void generarDiagnostico(View view, String edad, String sintomas, String genero) {
        Apis aqui = new Apis();
        api = aqui.apiMedicServiceData();

        System.out.println("por aqui ");
        Call<List<Diagnosis>> call = api.getDiagnosis(SharedPreferencesUtils.SacarDatos("ApiMedicToken", view), "json", "es-es", genero, edad, sintomas);
        call.enqueue(new Callback<List<Diagnosis>>() {
            @Override
            public void onResponse(Call<List<Diagnosis>> call, Response<List<Diagnosis>> response) {

                if (response.body() ==null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                    builder.setMessage("no se ha podido conectar con la API ");

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    String sintomasparseao = sintomas.replace("[", "");
                    String sintomasparseao2 = sintomasparseao.replace("]", "");

                    String[] sint = sintomasparseao2.split(",");
                    System.out.println(sint.length);
                    setupSintomasConsulta(sint, view);


                } else {
                    ProgressDialog pd = ProgressDialog.show(view.getContext(), "loading", "estamos generando su diagnostico");
                    if (response.isSuccessful()) {
                        pd.cancel();
                    }

                    if (!response.body().isEmpty()) {

                        for (int i = 0; i < response.body().size(); ) {

                            listaDiagnostico.add(response.body().get(i));
                            i++;
                        }
                        if(listaDiagnostico.isEmpty()){
                            Toast.makeText(contexto, "NO HAY DIAGNOSTICO", Toast.LENGTH_SHORT).show();
                        }





                        adapter = new ConsultaAdapter(contexto, listaDiagnostico,listaEspecialidades);
                        rvdiagnositco.setLayoutManager(new LinearLayoutManager(contexto, LinearLayoutManager.HORIZONTAL, false));
                        rvdiagnositco.setAdapter(adapter);
                        adapter.notifyDataSetChanged();



                        String sintomasparseao = sintomas.replace("[", "");
                        String sintomasparseao2 = sintomasparseao.replace("]", "");

                        String[] sint = sintomasparseao2.split(",");
                        System.out.println("tamaño de sintomas"+sint.length);
                        setupSintomasConsulta(sint, view);

                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int pos = rvdiagnositco.getChildAdapterPosition(view);
                                setUpOnClickDiagnostico(view,pos);
                            }
                        });



                    } else {
                        System.out.println();
                        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                        builder.setMessage("no se ha podido generar su diagnostico con los sintomas establecidos");
                        AlertDialog dialog = builder.create();
                        dialog.show();


                    }


                }
            }


            @Override
            public void onFailure(Call<List<Diagnosis>> call, Throwable t) {

            }
        });

    }





    private void setupGenero(View view) {
        genresMenuAdapter = new ArrayAdapter<String>(
                view.getContext(),
                R.layout.genre_dropdown_menu,
                genres);
        genresMenu = (AutoCompleteTextView) view.findViewById(R.id.txtGeneroConsulta);
        genresMenu.setThreshold(1);
        genresMenu.setAdapter(genresMenuAdapter);

    }

    private void setupSintomasConsulta(String[] ids, View view) {

        listaSintomas_consulta = new ArrayList<Symptom>();
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

                    listaSintomas_consulta = quitarduplicados(aux);

                    ArrayAdapter adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, listaSintomas_consulta);

                    nombres_sintomas = parseSymtom(listaSintomas_consulta,view);

                    spSintomas.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }}


            @Override
            public void onFailure(Call<List<Symptom>> call, Throwable t) {

            }


        });
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
    public ArrayList<Symptom> quitarduplicados(ArrayList<Symptom> prueba) {
        ArrayList<Symptom> limpio = new ArrayList<>();

        Set<String> nombre = new HashSet<>();
        limpio = (ArrayList<Symptom>) prueba.stream()
                .filter(e -> nombre.add(e.getName()))
                .collect(Collectors.toList());
        return limpio;
    }

    /*todo esto es un string, o por lo menos tiene que cargarse como uno si no peta jaja */
    public void setUpOnClickDiagnostico(View view, int posicion) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        System.out.println(sintomasNombre);
                            Date currentTime = Calendar.getInstance().getTime();
                        Map<String, Object> diagnostico = new HashMap<>();
                        diagnostico.put("Issue", listaDiagnostico.get(posicion).getIssue2().getName());
                        diagnostico.put("Acurracy", String.valueOf(listaDiagnostico.get(posicion).getIssue().getAccuracy()));
                        diagnostico.put("medical_condition", listaDiagnostico.get(posicion).getIssue2().getMedicalCondition());
                        diagnostico.put("fecha",currentTime.toString().toString());
                        diagnostico.put("sintomas_presentado", sintomasNombre);
                        diagnostico.put("specialidad", listaDiagnostico.get(posicion).getSpecialisation().toString());
                        diagnostico.put("descripcion_larga", listaDiagnostico.get(posicion).getIssue2().getDescription().toString());
                        diagnostico.put("tratamiento", listaDiagnostico.get(posicion).getIssue2().getTreatmentDescription());



                        //generar una subcollecion para el la base de datos donde añado el diagnositco al nombre del usuario
                            db.collection("Pacientes").document(nombrePaciente).collection("Diagnosticos").document(listaDiagnostico.get(posicion).getIssue().getName()+": "+currentTime.toString()).set(diagnostico);
                       // Navigation.findNavController(view).navigate(R.id.nav_consulta, args);

                        Toast.makeText(contexto, "se ha añadido su diagnostico para el paciente: "+nombrePaciente, Toast.LENGTH_LONG).show();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(view.getContext(), "no se ha generado el diagnostico", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Quieres realizar el diagnosito de este paciente?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

}

