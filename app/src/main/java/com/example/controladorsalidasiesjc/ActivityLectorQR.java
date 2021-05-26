package com.example.controladorsalidasiesjc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ActivityLectorQR extends AppCompatActivity {

    Context context = ActivityLectorQR.this;
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

        leerQR();

        /*boolean puedeSalir = true;
        String nombre = "Javier";
        if(puedeSalir){
            activityLeerQR.setBackgroundResource(R.color.verde_fondo);
            imageView.setBackgroundResource(R.drawable.exito);
            tvResultado.setText("Lo siento " +nombre + " no puedes salir");
            tvMotivo.setText("");
        }else{
            activityLeerQR.setBackgroundResource(R.color.rojo_fondo);
            imageView.setBackgroundResource(R.drawable.error);
            tvResultado.setText("Lo siento " +nombre + " no puedes salir");
        }*/
    }

    public void leerQR(){
        new IntentIntegrator(this).setCameraId(1).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        String NIA = result.getContents();
        String nombre = Alumno.getNombreAlumno(this,Integer.parseInt(NIA));;

        if (Permiso_para_salir(NIA)){
            activityLeerQR.setBackgroundResource(R.color.verde_fondo);
            imageView.setBackgroundResource(R.drawable.exito);
            tvResultado.setText("Tienes permiso para salir " +nombre);
        }else{
            activityLeerQR.setBackgroundResource(R.color.rojo_fondo);
            imageView.setBackgroundResource(R.drawable.error);
            tvResultado.setText("Lo siento " +nombre + " no puedes salir");
        }
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(FeedReaderContract.TablaRegistroSalida.SQL_DELETE_WEEKLY);
    }

    public static List get_Franjas_Permitidas(Context context,String siglas, String dia){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio,
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio,
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Final,
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Final };

        String columnaWhere = FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria + " IN (SELECT ID_Franja_horaria FROM Franjas_horarias_cursos_permitidos WHERE Siglas= ?"+") AND Dia_semana = " + dia +  ";";
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaFranjasHorarias.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                new String[]{siglas},
                null,
                null,
                null
        );

        List FranjasPermitidas = new ArrayList<FranjaHoraria>();
        while(cursorConsulta.moveToNext()){

            int hI = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio));
            int mI = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio));
            int hf = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Final));
            int mf = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Final));

            Fecha FechaInicio = new Fecha();
            FechaInicio.hora= hI;
            FechaInicio.minuto= mI;

            Fecha FechaFinal = new Fecha();
            FechaFinal.hora= hf;
            FechaFinal.minuto = mf;

            FranjaHoraria franja = new FranjaHoraria();
            franja.horaIncio= FechaInicio;
            franja.horaFinal = FechaFinal;

            FranjasPermitidas.add(franja);
        }
        cursorConsulta.close();

        return FranjasPermitidas;
    }

    public boolean Permiso_para_salir(String NIA){
        String dia = get_Dia();
        int hora = Integer.parseInt(hora_actual());
        int minuto = Integer.parseInt(minuto_actual());
        String curso = getCurso(this, NIA);
        boolean salir = false;

        List HorasPermitidas = get_Franjas_Permitidas(this, NIA, dia);

        for (int i=0;i<HorasPermitidas.size();i++) {
            FranjaHoraria franja = (FranjaHoraria) HorasPermitidas.get(i);
            if (franja.horaIncio.hora == hora){
                if (franja.horaIncio.minuto < minuto)
                    salir = true;
            }
        }
        return salir;
    }

    public static String getCurso(Context context, String datos){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM Alumnos WHERE NIA = " + datos, null);
        String siglas = null;

        if (c.moveToFirst()) {
            do {
                siglas = c.getString(7);
            } while(c.moveToNext());
        }
        db.close();
        return siglas;
    }

    public String get_Dia(){
        Calendar hoy = Calendar.getInstance();
        int dia = hoy.get(Calendar.DAY_OF_WEEK);
        String dia_semana = "";
        switch (dia){
            case 2:
                dia_semana = "Lunes";
                break;
            case 3:
                dia_semana = "Martes";
                break;
            case 4:
                dia_semana = "Miercoles";
                break;
            case 5:
                dia_semana ="Jueves";
                break;
            case 6:
                dia_semana = "Viernes";
                break;
            case 7:
                dia_semana = "Domingo";
                break;
            case 1:
                dia_semana = "Domingo";
                break;
        }
        return dia_semana;
    }

    public String fecha_actual( ){
        Locale esLocale = new Locale("es", "ES");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM", esLocale);
        Date date = new Date();
        String fecha = dateFormat.format(date);

        return fecha;
    }

    public String hora_actual( ){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh", new Locale("es_ES"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        Date date = new Date();
        String hora = dateFormat.format(date);

        return hora;
    }

    public String minuto_actual( ){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm", new Locale("es_ES"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        Date date = new Date();
        String minuto = dateFormat.format(date);

        return minuto;
    }
}