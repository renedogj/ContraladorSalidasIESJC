package com.example.controladorsalidasiesjc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EtapaEducativa {
    public int ID;
    public String nombre;
    public int edadMinimaSalir;

    public EtapaEducativa(){}

    public EtapaEducativa(int ID, String nombre, int edadMinimaSalir) {
        this.ID = ID;
        this.nombre = nombre;
        this.edadMinimaSalir = edadMinimaSalir;
    }

    public void getEtapaEducativa(Context context, int ID_etapa){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_ID_Etapa,
                FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Nombre,
                FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Edad_minima_salir
        };
        String columnaWhere = FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_ID_Etapa + " = ?";
        String[] valorWhere = { ID_etapa + "" };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaEtapasEducativas.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        if(cursorConsulta.moveToNext()){
            this.ID = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_ID_Etapa));
            this.nombre = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Nombre));
            this.edadMinimaSalir = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Edad_minima_salir));
        }
        cursorConsulta.close();
        db.close();
    }

    public void getEtapaEducativa(Context context, String nombreEtapa){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_ID_Etapa,
                FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Nombre,
                FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Edad_minima_salir
        };
        String columnaWhere = FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Nombre + " = ?";
        String[] valorWhere = { nombreEtapa + "" };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaEtapasEducativas.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        if(cursorConsulta.moveToNext()){
            this.ID = cursorConsulta.getInt(
                    cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_ID_Etapa));
            this.nombre = cursorConsulta.getString(
                    cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Nombre));
            this.edadMinimaSalir = cursorConsulta.getInt(
                    cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Edad_minima_salir));
        }
        cursorConsulta.close();
        db.close();
    }

    public static List getEtapasEducativas(Context context){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_ID_Etapa,
                FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Nombre,
                FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Edad_minima_salir
        };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaEtapasEducativas.TABLE_NAME,
                columnasARetornar,
                null,
                null,
                null,
                null,
                null
        );
        List etapasEducativas = new ArrayList<EtapaEducativa>();
        while(cursorConsulta.moveToNext()){
            EtapaEducativa etapaEducativa = new EtapaEducativa();
            etapaEducativa.ID = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_ID_Etapa));
            etapaEducativa.nombre = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Nombre));
            etapaEducativa.edadMinimaSalir = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Edad_minima_salir));
            etapasEducativas.add(etapaEducativa);
        }
        cursorConsulta.close();
        db.close();
        return etapasEducativas;
    }

    public static List getNombreEtapaEducativas(Context context){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Nombre
        };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaEtapasEducativas.TABLE_NAME,
                columnasARetornar,
                null,
                null,
                null,
                null,
                null
        );
        List nombreEtapasEducativas = new ArrayList<String>();
        while(cursorConsulta.moveToNext()){
            String nombre = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Nombre));
            nombreEtapasEducativas.add(nombre);
        }
        cursorConsulta.close();
        db.close();
        return nombreEtapasEducativas;
    }

    public void actualizarEdadMinima(Context context,int edadMinimaSalir){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_Edad_minima_salir, edadMinimaSalir);

        String selection = FeedReaderContract.TablaEtapasEducativas.COLUMN_NAME_ID_Etapa+ " LIKE ?";
        String[] selectionArgs = { this.ID+"" };

        int count = db.update(FeedReaderContract.TablaEtapasEducativas.TABLE_NAME,values, selection, selectionArgs);

        db.close();
    }
}
