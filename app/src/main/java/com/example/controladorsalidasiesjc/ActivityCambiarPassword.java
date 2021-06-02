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

public class ActivityCambiarPassword extends AppCompatActivity {

    public static final int ACTIVITY_CAMBIAR_PASSWORD = 3;

    Context context = ActivityCambiarPassword.this;

    private EditText etPassword1;
    private EditText etPassword2;
    private Button bttnAcceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);

        etPassword1 = findViewById(R.id.etPassword1);
        etPassword2 = findViewById(R.id.etPassword2);
        bttnAcceder = findViewById(R.id.bttnAcceder);

        bttnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword1.getText().toString().equals(etPassword2.getText().toString())){
                    SharedPreferences sharedPreferences = context.getSharedPreferences("seguridad", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password", etPassword1.getText().toString());
                    editor.commit();

                    Toast.makeText(context,"Contraseña actualizada con éxito", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, MainActivity.class));
                }else{
                    Toast.makeText(context,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}