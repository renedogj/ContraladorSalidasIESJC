package com.example.controladorsalidasiesjc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegistroSalida {
    public int ID;
    public int NIA;
    public int ID_FranjaHoraria;
    public Fecha fechaSalida;

    public RegistroSalida() {}

    public RegistroSalida(int ID, int NIA, int ID_FranjaHoraria, Fecha fechaSalida) {
        this.ID = ID;
        this.NIA = NIA;
        this.ID_FranjaHoraria = ID_FranjaHoraria;
        this.fechaSalida = fechaSalida;
    }

    public static List getRegistrosSalidaNia(Context context, int NIA) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Registro,
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Franja_Horaria,
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_FECHA_SALIDA
        };
        String columnaWhere = FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_NIA + " = ?";
        String[] valorWhere = {NIA + ""};
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaRegistroSalida.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        List registrosSalida = new ArrayList<FranjaHoraria>();
        while (cursorConsulta.moveToNext()) {
            int id = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Registro));
            int id_franja = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Franja_Horaria));
            String fechaString = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_FECHA_SALIDA));

            Fecha fecha = new Fecha(
                    Integer.parseInt(fechaString.substring(8,9)),
                    Integer.parseInt(fechaString.substring(5,6)),
                    Integer.parseInt(fechaString.substring(0,3))
            );
            registrosSalida.add(new RegistroSalida(id,NIA,id_franja,fecha));
        }
        cursorConsulta.close();
        db.close();
        return registrosSalida;
    }

    public static void ActualizarRegistros(Context context) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Registro,
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_NIA,
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Franja_Horaria,
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_FECHA_SALIDA,
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_Registro_Guardado
        };
        String columnaWhere = FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_Registro_Guardado + " = ?";
        String[] valorWhere = {"0"};
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaRegistroSalida.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        while(cursorConsulta.moveToNext()) {
            int idRegistro = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Registro));
            String fecha = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_FECHA_SALIDA));
            String NIA = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_NIA));
            int idFranja = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Franja_Horaria));
            FirebaseDatabase.getInstance().getReference("Registros").child(fecha).child(NIA).child(idFranja+"").setValue(NIA)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_Registro_Guardado,"1");

                            String selection = FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Registro+ " LIKE ?";
                            String[] selectionArgs = { idRegistro+"" };

                            db.update(FeedReaderContract.TablaRegistroSalida.TABLE_NAME,values, selection, selectionArgs);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_Registro_Guardado,"0");

                            String selection = FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Registro+ " LIKE ?";
                            String[] selectionArgs = { idRegistro+"" };

                            db.update(FeedReaderContract.TablaRegistroSalida.TABLE_NAME,values, selection, selectionArgs);
                        }
                    });
        }
        cursorConsulta.close();
        db.close();
    }

    public static Boolean existeRegistroSalidaNiaIDFranja(Context context, int NIA, int ID_FranjaHoraria) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Registro,
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Franja_Horaria,
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_FECHA_SALIDA
        };
        String columnaWhere = FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_NIA + " = ? AND " +
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_ID_Franja_Horaria + " = " + ID_FranjaHoraria + " AND " +
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_FECHA_SALIDA + " = '" + Fecha.getFechaActual() + "'";
        String[] valorWhere = {NIA + ""};
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaRegistroSalida.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        boolean registroSalida = false;
        if (cursorConsulta.moveToNext()) {
            registroSalida = true;
        }
        cursorConsulta.close();
        db.close();
        return registroSalida;
    }
}
