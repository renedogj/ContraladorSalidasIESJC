package com.example.controladorsalidasiesjc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityMostrarResultado extends AppCompatActivity {

    Context context = ActivityMostrarResultado.this;
    androidx.constraintlayout.widget.ConstraintLayout activityMostrarResultado;

    private ImageView imageView;
    private TextView tvResultado;
    private TextView tvMotivo;
    private String motivo = "No puedes salir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_resultado);

        imageView = findViewById(R.id.imageView);
        tvResultado = findViewById(R.id.tvResultado);
        tvMotivo = findViewById(R.id.tvMotivo);
        activityMostrarResultado = findViewById(R.id.activityMostrarResultado);

        Boolean puedeSalir = getIntent().getBooleanExtra("puedeSalir", false);
        String nombre = getIntent().getStringExtra("nombre");
        String motivo = getIntent().getStringExtra("motivo");

        if (puedeSalir) {
            activityMostrarResultado.setBackgroundResource(R.color.verde_fondo);
            imageView.setBackgroundResource(R.drawable.exito);
            tvResultado.setText("Tienes permiso para salir " + nombre);
        } else {
            activityMostrarResultado.setBackgroundResource(R.color.rojo_fondo);
            imageView.setBackgroundResource(R.drawable.error);
            tvResultado.setText("Lo siento " + nombre + " no puedes salir");
            tvMotivo.setText(motivo);
        }

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(FeedReaderContract.TablaRegistroSalida.SQL_DELETE_WEEKLY);

        Intent intent = new Intent(this, ActivityLectorQR.class);
        startActivity(intent);
    }
}