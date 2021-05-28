package com.example.controladorsalidasiesjc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ActivityConfiguracion extends AppCompatActivity {

    Context context = ActivityConfiguracion.this;
    private Spinner spinEtapas;
    private Spinner spinCursos;
    private Spinner spinAlumnos;
    private Spinner spinDiasSemana;
    private TextView tvNombreCurso;
    private EditText etEdadMinima;

    private RecyclerView recyclerView;
    List listFranjasHorarias;


    EtapaEducativa etapaEducativa = new EtapaEducativa();
    Curso curso = new Curso();
    String diaSemanaSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        spinEtapas = findViewById(R.id.spinEtapas);
        spinCursos = findViewById(R.id.spinCursos);
        spinAlumnos = findViewById(R.id.spinAlumnos);
        spinDiasSemana = findViewById(R.id.spinDiasSemana);
        tvNombreCurso = findViewById(R.id.tvNombreCurso);
        etEdadMinima = findViewById(R.id.etEdadMinima);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayAdapter <String> adapterEtapas = new ArrayAdapter<String>(context, R.layout.spinner_item_etapas_cursos,EtapaEducativa.getNombreEtapaEducativas(this));
        spinEtapas.setAdapter(adapterEtapas);
        spinEtapas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etapaEducativa.getEtapaEducativa(context,spinEtapas.getSelectedItem().toString());
                ArrayAdapter <String> adapterCursos = new ArrayAdapter<String>(context, R.layout.spinner_item_etapas_cursos, Curso.getSiglasCursos(context,etapaEducativa));
                spinCursos.setAdapter(adapterCursos);
                etEdadMinima.setText(etapaEducativa.edadMinimaSalir+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ActivityConfiguracion.this, "Se ha producido un error al seleccionar la etapa", Toast.LENGTH_LONG).show();
            }
        });

        spinCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curso.getCurso(context,spinCursos.getSelectedItem().toString());
                tvNombreCurso.setText(curso.nombre);

                List nombreAlumnosPorCurso = Alumno.getNombreAlumnosPorCurso(context,curso);
                nombreAlumnosPorCurso.add(0,"Todos");
                ArrayAdapter <String> adapterAlumnos = new ArrayAdapter<String>(context, R.layout.spinner_item_etapas_cursos, nombreAlumnosPorCurso);
                spinAlumnos.setAdapter(adapterAlumnos);

                listFranjasHorarias = FranjaHoraria.getFranjasDiaSemana(context,diaSemanaSelect);
                SwichAdapter adapter = new SwichAdapter(listFranjasHorarias,curso,context);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ActivityConfiguracion.this, "Se ha producido un error al seleccionar el curso", Toast.LENGTH_LONG).show();
            }
        });

        spinAlumnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etEdadMinima.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                String edad = etEdadMinima.getText().toString();
                if(keyCode >=7 && keyCode <=16){
                    if(edad.equals("")){
                        edad = (keyCode-7)+"";
                    }
                    etapaEducativa.actualizarEdadMinima(context,Integer.parseInt(edad));
                }
                return false;
            }
        });

        String diasSemana[] = {"Lunes","Martes","Miercoles","Jueves","Viernes"};
        ArrayAdapter <String> adapterSpinDiasSemana = new ArrayAdapter<String>(this, R.layout.spinner_item_etapas_cursos, diasSemana);
        spinDiasSemana.setAdapter(adapterSpinDiasSemana);
        spinDiasSemana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diaSemanaSelect = spinDiasSemana.getSelectedItem().toString();
                listFranjasHorarias = FranjaHoraria.getFranjasDiaSemana(context,diaSemanaSelect);

                SwichAdapter adapter = new SwichAdapter(listFranjasHorarias,curso,context);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ActivityConfiguracion.this, "Se ha producido un error al seleccionar el dia de la semana", Toast.LENGTH_LONG).show();
            }
        });
    }
}