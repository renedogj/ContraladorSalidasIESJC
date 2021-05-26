package com.example.controladorsalidasiesjc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

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
    }

    public void leerQR(){
        new IntentIntegrator(this).setCameraId(1).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        int NIA = Integer.parseInt(result.getContents());
        Toast.makeText(this,NIA,Toast.LENGTH_SHORT).show();
        String nombre = Alumno.getNombreAlumno(context,NIA);

        if (permisoParaSalir(NIA)){
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

    public boolean permisoParaSalir(int NIA){
        String dia = Fecha.getDiaActual();
        //Fecha fecha = new Fecha(Integer.parseInt(hora_actual()),Integer.parseInt(minuto_actual()));
        int hora = Integer.parseInt(Fecha.getHoraActual());
        int minuto = Integer.parseInt(Fecha.getMinutoActual());
        String siglasCurso = Alumno.getCursoAlumno(context, NIA);
        boolean salir = false;

        List<FranjaHoraria> FranjasPermitidas = FranjaHoraria.get_Franjas_Permitidas(this, siglasCurso, dia);

        for (int i=0;i<FranjasPermitidas.size();i++) {
            FranjaHoraria franja = FranjasPermitidas.get(i);
            /*if(fecha.isFechaEntreDosfechas(franja.horaInicio,franja.horaFinal)){
                salir = true;
            }*/
            if (franja.horaInicio.hora >= hora) {
                if (franja.horaInicio.minuto < minuto)
                    salir = true;
            }
        }
        if(salir){
            addRegistroSalida(context,NIA);
        }
        return salir;
    }

    public static void addRegistroSalida(Context context,int NIA){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_NIA, NIA);
        registro.put(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_FECHA_SALIDA, Fecha.getFechaActual());

        db.insert(FeedReaderContract.TablaRegistroSalida.TABLE_NAME, null, registro);
        db.close();
    }
}