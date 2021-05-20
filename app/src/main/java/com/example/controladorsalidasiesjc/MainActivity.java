package com.example.controladorsalidasiesjc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button bttnConfiguracion;
    private Button bttnImportarCSV;
    private Button bttonLectorQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttnConfiguracion = findViewById(R.id.bttnConfiguracion);
        bttnImportarCSV = findViewById(R.id.bttnImportarCSV);
        bttonLectorQR = findViewById(R.id.bttonLectorQR);

        bttonLectorQR.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ActivityLectorQR.class))
        );

        bttnConfiguracion.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ActivityConfiguracion.class))
        );

        bttnImportarCSV.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ActivityImportarCsv.class))
        );
    }
}