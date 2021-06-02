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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class ActivityLectorQR extends AppCompatActivity {

    Context context = ActivityLectorQR.this;
    androidx.constraintlayout.widget.ConstraintLayout activityLeerQR;

    private String motivo = "No puedes salir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_qr);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException exception) {

        }
        leerQR();
    }

    public void leerQR(){
        new IntentIntegrator(this).setCameraId(1).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

            int NIA = Integer.parseInt(result.getContents());
            String nombre = Alumno.getNombreAlumno(context,NIA);
            boolean puedeSalir = permisoParaSalir(NIA);

            Intent intent = new Intent(this,ActivityMostrarResultado.class);

            intent.putExtra("puedeSalir",puedeSalir);
            intent.putExtra("nombre",nombre);
            intent.putExtra("motivo",motivo);
            startActivity(intent);
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
                    if(RegistroSalida.existeRegistroSalidaNiaIDFranja(context,NIA,franja.ID)){
                        motivo = "Ya has salido en esta franja horaria";
                    }else{
                        salir = true;
                        addRegistroSalida(context,NIA,franja.ID);
                    }
                }else{
                    motivo = "No puedes salir en esta franja horaria";
                }
            }
        }else{
            motivo = "No tienes edad suficiente para salir";
        }
        return salir;
    }

    public static void addRegistroSalida(Context context,int NIA,int IDFranja){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_NIA, NIA);
        registro.put(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Franja_Horaria, IDFranja);
        registro.put(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_FECHA_SALIDA, Fecha.getFechaActual());

        db.insert(FeedReaderContract.TablaRegistroSalida.TABLE_NAME, null, registro);
        db.close();
    }
}