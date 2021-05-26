package com.example.controladorsalidasiesjc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Context context = MainActivity.this;

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

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(FeedReaderContract.TablaRegistroSalida.SQL_DELETE_WEEKLY);
    }
}