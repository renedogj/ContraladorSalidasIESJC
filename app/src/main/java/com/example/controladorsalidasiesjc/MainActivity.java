package com.example.controladorsalidasiesjc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Context context = MainActivity.this;

    private Button bttnConfiguracion;
    private Button bttnImportarCSV;
    private Button bttonLectorQR;
    private Button bttnCambiarPassword;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttnConfiguracion = findViewById(R.id.bttnConfiguracion);
        bttnImportarCSV = findViewById(R.id.bttnImportarCSV);
        bttonLectorQR = findViewById(R.id.bttonLectorQR);
        bttnCambiarPassword = findViewById(R.id.bttnCambiarPassword);

        bttonLectorQR.setOnClickListener(v ->
                startActivity(new Intent(context, ActivityLectorQR.class))
        );

        bttnConfiguracion.setOnClickListener((View v) ->
                irAlLogin(ActivityConfiguracion.ACTIVITY_CONFIGURACION)
        );

        bttnImportarCSV.setOnClickListener(v ->
                irAlLogin(ActivityImportarCsv.ACTIVITY_IMPORTAR_CSV)
        );

        bttnCambiarPassword.setOnClickListener(v ->
                irAlLogin( ActivityCambiarPassword.ACTIVITY_CAMBIAR_PASSWORD)
        );

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(FeedReaderContract.TablaRegistroSalida.SQL_DELETE_WEEKLY);
        db.close();
    }

    public void irAlLogin(int activity){
        intent = new Intent(context, ActivityLogin.class);
        intent.putExtra("activity",activity);
        startActivity(intent);
    }
}