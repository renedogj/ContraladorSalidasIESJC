package com.example.controladorsalidasiesjc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class ActivityLectorQR extends AppCompatActivity {

    Context context = ActivityLectorQR.this;
    androidx.constraintlayout.widget.ConstraintLayout activityLeerQR;

    private ImageView imageView;
    private TextView tvResultado;
    private TextView tvMotivo;
    private String motivo = "No puedes salir";

    //Handler handler = new Handler();
    //private final int TIEMPO = 5000;


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
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        new IntentIntegrator(this).setCameraId(1).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

            int NIA = Integer.parseInt(result.getContents());
            String nombre = Alumno.getNombreAlumno(context,NIA);

            if (permisoParaSalir(NIA)){
                activityLeerQR.setBackgroundResource(R.color.verde_fondo);
                imageView.setBackgroundResource(R.drawable.exito);
                tvResultado.setText("Tienes permiso para salir " +nombre);
            }else{
                activityLeerQR.setBackgroundResource(R.color.rojo_fondo);
                imageView.setBackgroundResource(R.drawable.error);
                tvResultado.setText("Lo siento " +nombre + " no puedes salir");
                tvMotivo.setText(motivo);
            }

            FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(FeedReaderContract.TablaRegistroSalida.SQL_DELETE_WEEKLY);

            /*handler.postDelayed(new Runnable() {
                public void run() {
                    handler.postDelayed(this, TIEMPO);
                    //leerQR();
                }
            }/*, TIEMPO)*/;
            //leerQR();
        }
    }

    public boolean permisoParaSalir(int NIA){
        String dia = Fecha.getDiaActual();
        Fecha fechaActual = Fecha.FechaActual();
        Alumno alumno = new Alumno();
        alumno.getAlumno(context,NIA);
        boolean salir = false;
        if(Fecha.diferenciaFechaMayorQueAños(fechaActual, alumno.fechaNacimiento,alumno.curso.numeroCurso)){
            List<FranjaHoraria> FranjasPermitidas = FranjaHoraria.get_Franjas_Permitidas(this, alumno.curso.siglas, dia);

            for (int i=0;i<FranjasPermitidas.size();i++) {
                FranjaHoraria franja = FranjasPermitidas.get(i);
                if(fechaActual.isFechaEntreDosfechas(franja.horaInicio,franja.horaFinal)){
                    salir = true;
                }else{
                    motivo = "No puedes salir en esta franja horaria";
                }
            }
        }else{
            motivo = "No tienes edad suficiente para salir";
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