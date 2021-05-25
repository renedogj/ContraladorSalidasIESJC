package com.example.controladorsalidasiesjc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class ActivityImportarCsv extends AppCompatActivity {

    public static final int requestCodeAlumnos = 0;
    public static final int requestCodeCursos = 1;
    public static final int requestCodeEtapas = 3;
    public static final int requestCodeFranjasHorarias = 4;
    Context context = ActivityImportarCsv.this;

    private Button bttnImportarAlumnos;
    private Button bttnImportarCursos;
    private Button bttnImportarEtapasEducativas;
    private Button bttnImportarFranjasHorarias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar_csv);

        bttnImportarAlumnos = findViewById(R.id.bttnImportarAlumnos);
        bttnImportarCursos = findViewById(R.id.bttnImportarCursos);
        bttnImportarEtapasEducativas = findViewById(R.id.bttnImportarEtapasEducativas);
        bttnImportarFranjasHorarias = findViewById(R.id.bttnImportarFranjasHorarias);

        pedirPermisos();

        bttnImportarAlumnos.setOnClickListener(v -> {
            iniciarIntentArchivos(requestCodeAlumnos);
        });

        bttnImportarCursos.setOnClickListener(v -> {
            iniciarIntentArchivos(requestCodeCursos);
        });

        bttnImportarEtapasEducativas.setOnClickListener(v -> {
            iniciarIntentArchivos(requestCodeEtapas);
        });

        bttnImportarFranjasHorarias.setOnClickListener(v -> {
            iniciarIntentArchivos(requestCodeFranjasHorarias);
        });
    }

    public void iniciarIntentArchivos(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/*");
        try {
            startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String cadena;
        String[] array;
        Uri uri = data.getData();
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(inputStream))
            );

            FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            switch (requestCode) {
                case requestCodeAlumnos:
                    limpiarTablas(FeedReaderContract.TablaCursos.TABLE_NAME);
                    while ((cadena = bufferedReader.readLine()) != null) {
                        array = cadena.split(";");

                        ContentValues registro = new ContentValues();
                        registro.put(FeedReaderContract.TablaAlumnos.COLUMN_NAME_NIA, array[0]);
                        registro.put(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Nombre, array[1]);
                        registro.put(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Primer_Apellido, array[2]);
                        registro.put(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Segundo_Apellido, array[3]);
                        registro.put(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Dia_Nacimiento, array[4]);
                        registro.put(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Mes_Nacimiento, array[5]);
                        registro.put(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Año_Nacimiento, array[6]);
                        registro.put(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Siglas_Curso, array[7]);

                        db.insert(FeedReaderContract.TablaAlumnos.TABLE_NAME, null, registro);
                    }
                    Toast.makeText(this, "Alumnos importados correctamente", Toast.LENGTH_SHORT).show();
                    break;
                case requestCodeCursos:
                    limpiarTablas(FeedReaderContract.TablaCursos.TABLE_NAME);
                    while ((cadena = bufferedReader.readLine()) != null) {
                        array = cadena.split(";");
                        ContentValues registro = new ContentValues();
                        registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_Nombre, array[1]);

                        String[] arrayNombre = array[1].split(" ");
                        switch (arrayNombre[2]) {
                            case "E.S.O":
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa, 1);
                                break;
                            case "F.P.B.":
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa, 3);
                                break;
                            case "C.F.G.M.":
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa, 4);
                                break;
                            case "C.F.G.S.":
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa, 5);
                                break;
                            case "Bachillerato":
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa, 2);
                                break;
                            default:
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa, 1);
                        }
                        String[] arraySiglas = array[0].split(" ");
                        if (arraySiglas.length > 1) {
                            if (arraySiglas[1].startsWith("PMAR")) {
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_Siglas_Curso, array[0]);
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_Numero_Curso, arraySiglas[0].charAt(3) + "");
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_Grupo, arraySiglas[1]);
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa, 1);
                            } else {
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_Siglas_Curso, arraySiglas[0]);
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_Numero_Curso, arraySiglas[0].charAt(arraySiglas[0].length() - 2) + "");
                                registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_Grupo, arraySiglas[0].charAt(arraySiglas[0].length() - 1) + "");
                            }
                        } else {
                            registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_Siglas_Curso, array[0]);
                            registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_Numero_Curso, arraySiglas[0].charAt(arraySiglas[0].length() - 2) + "");
                            registro.put(FeedReaderContract.TablaCursos.COLUMN_NAME_Grupo, arraySiglas[0].charAt(arraySiglas[0].length() - 1) + "");
                        }
                        db.insert(FeedReaderContract.TablaCursos.TABLE_NAME, null, registro);
                    }
                    Toast.makeText(this, "Cursos importados correctamente", Toast.LENGTH_SHORT).show();
                    break;
                case requestCodeEtapas:
                    limpiarTablas(FeedReaderContract.TablaEtapasEducativas.TABLE_NAME);
                    while ((cadena = bufferedReader.readLine()) != null) {

                        array = cadena.split(";");

                        ContentValues registro = new ContentValues();
                        registro.put(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_ID_Etapa, array[0]);
                        registro.put(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Nombre, array[1]);
                        registro.put(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Edad_minima_salir, array[2]);

                        db.insert(FeedReaderContract.TablaEtapasEducativas.TABLE_NAME, null, registro);
                    }
                    Toast.makeText(this, "Etapas importadas correctamente", Toast.LENGTH_SHORT).show();
                    break;
                case requestCodeFranjasHorarias:
                    limpiarTablas(FeedReaderContract.TablaFranjasHorarias.TABLE_NAME);
                    while ((cadena = bufferedReader.readLine()) != null) {

                        array = cadena.split(";");

                        ContentValues registro = new ContentValues();
                        registro.put(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria, array[0]);
                        registro.put(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana, array[1]);
                        registro.put(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio, array[2]);
                        registro.put(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio, array[3]);
                        registro.put(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Final, array[4]);
                        registro.put(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Final, array[5]);

                        db.insert(FeedReaderContract.TablaFranjasHorarias.TABLE_NAME, null, registro);
                    }
                    Toast.makeText(this, "Franjas horarias importadas correctamente", Toast.LENGTH_SHORT).show();
                    break;
            }
            db.close();
        } catch (IOException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarTablas(String nombreTabla) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.truncateTable(db, nombreTabla);
        db.close();
    }

    public void pedirPermisos() {
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityImportarCsv.this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);

        }
    }
}