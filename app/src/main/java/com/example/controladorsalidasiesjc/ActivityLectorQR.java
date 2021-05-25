package com.example.controladorsalidasiesjc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ActivityLectorQR extends AppCompatActivity {

    androidx.constraintlayout.widget.ConstraintLayout activityLeerQR;

    private ImageView imageView;
    private TextView tvResultado;
    private TextView tvMotivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_qr);

        imageView = findViewById(R.id.imageView);
        tvResultado = findViewById(R.id.tvResultado);
        tvMotivo = findViewById(R.id.tvMotivo);
        activityLeerQR = findViewById(R.id.activityLeerQR);

        //leerQR();

        boolean puedeSalir = true;
        String nombre = "Javier";
        if(puedeSalir){
            activityLeerQR.setBackgroundResource(R.color.verde_fondo);
            imageView.setBackgroundResource(R.drawable.exito);
            tvResultado.setText("Lo siento " +nombre + " no puedes salir");
        }else{
            activityLeerQR.setBackgroundResource(R.color.rojo_fondo);
            imageView.setBackgroundResource(R.drawable.error);
            tvResultado.setText("Lo siento " +nombre + " no puedes salir");
        }
    }

    public void leerQR(){
        new IntentIntegrator(this).setCameraId(1).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        String NIA = result.getContents();
        String nombre = Alumno.getNombreAlumno(this,Integer.parseInt(NIA));
        tvResultado.setText(NIA);
    }
}