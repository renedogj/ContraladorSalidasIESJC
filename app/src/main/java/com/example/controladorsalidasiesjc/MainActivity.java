package com.example.controladorsalidasiesjc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                irAlLogin(ActivityConfiguracion.CODIO_ACTIVITY)
        );

        bttnImportarCSV.setOnClickListener(v ->
                irAlLogin(ActivityImportarCsv.CODIO_ACTIVITY)
        );

        bttnCambiarPassword.setOnClickListener(v ->
                irAlLogin(ActivityCambiarPassword.CODIO_ACTIVITY)
        );

        RegistroSalida.ActualizarRegistros(context);

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(FeedReaderContract.TablaRegistroSalida.SQL_DELETE_WEEKLY);
        db.close();
    }

    public void irAlLogin(int activity) {
        intent = new Intent(context, ActivityLogin.class);
        intent.putExtra("activity", activity);
        startActivity(intent);
    }
}