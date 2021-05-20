package com.example.controladorsalidasiesjc;

import androidx.appcompat.app.AppCompatActivity;

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

public class ActivityConfiguracion extends AppCompatActivity {

    Context context = ActivityConfiguracion.this;
    private Spinner spinEtapas;
    private Spinner spinCursos;
    private Spinner spinDiasSemana;
    private TextView tvNombreCurso;
    private EditText etEdadMinima;

    EtapaEducativa etapaEducativa = new EtapaEducativa();
    Curso curso = new Curso();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        spinEtapas = findViewById(R.id.spinEtapas);
        spinCursos = findViewById(R.id.spinCursos);
        spinDiasSemana = findViewById(R.id.spinDiasSemana);
        tvNombreCurso = findViewById(R.id.tvNombreCurso);
        etEdadMinima = findViewById(R.id.etEdadMinima);

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

            }
        });

        spinCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curso.getCurso(context,spinCursos.getSelectedItem().toString());
                tvNombreCurso.setText(curso.nombre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etEdadMinima.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if(keyCode >=7 && keyCode <=16){
                    etapaEducativa.actualizarEdadMinima(context,Integer.parseInt(etEdadMinima.getText().toString()));
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ActivityConfiguracion.this, "Se ha producido un error al seleccionar el dia de la semana", Toast.LENGTH_LONG).show();
            }
        });
    }
}