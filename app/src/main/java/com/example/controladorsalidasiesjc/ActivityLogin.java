package com.example.controladorsalidasiesjc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {
    Context context = ActivityLogin.this;

    private EditText etPassword;
    private Button bttnAcceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPassword = findViewById(R.id.etPassword);
        bttnAcceder = findViewById(R.id.bttnAcceder);

        int activity = getIntent().getIntExtra("activity", 1);
        SharedPreferences sharedPreferences = context.getSharedPreferences("seguridad", Context.MODE_PRIVATE);
        String password = sharedPreferences.getString("password", "admin");

        bttnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().toString().equals(password)) {
                    switch (activity) {
                        case ActivityConfiguracion.ACTIVITY_CONFIGURACION:
                            startActivity(new Intent(context, ActivityConfiguracion.class));
                            break;
                        case ActivityImportarCsv.ACTIVITY_IMPORTAR_CSV:
                            startActivity(new Intent(context, ActivityImportarCsv.class));
                            break;
                        case ActivityCambiarPassword.ACTIVITY_CAMBIAR_PASSWORD:
                            startActivity(new Intent(context, ActivityCambiarPassword.class));
                            break;
                    }
                } else {
                    Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}