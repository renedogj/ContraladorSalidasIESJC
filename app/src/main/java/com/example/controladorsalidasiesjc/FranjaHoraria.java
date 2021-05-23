package com.example.controladorsalidasiesjc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FranjaHoraria {
    public int ID;
    public String diaSemana;
    public Fecha horaIncio;
    public Fecha horaFinal;
    public FranjaHoraria(){}

    public FranjaHoraria(int ID, String diaSemana, Fecha horaIncio, Fecha horaFinal) {
        this.ID = ID;
        this.diaSemana = diaSemana;
        this.horaIncio = horaIncio;
        this.horaFinal = horaFinal;
    }

    public FranjaHoraria(Fecha horaIncio, Fecha horaFinal) {
        this.horaIncio = horaIncio;
        this.horaFinal = horaFinal;
    }

    public void getFranjaHoraria(Context context, int ID_franjaHoraria){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria,
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana,
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio,
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio,
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Final,
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Final
        };
        String columnaWhere = FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria + " = ?";
        String[] valorWhere = { ID_franjaHoraria + "" };
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
            this.ID = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria));
            this.diaSemana = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana));
            this.horaIncio = new Fecha(
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio)),
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio))
            );
            this.horaFinal = new Fecha(
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Final)),
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Final))
            );
        }
        cursorConsulta.close();
    }

    public static List getFranjasIniciales1(Context context, String dia_semana){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio,
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio
        };
        String columnaWhere = FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana + " = ?";
        String[] valorWhere = { dia_semana + "" };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaFranjasHorarias.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        List HorasIniciales = new ArrayList<Fecha>();
        while(cursorConsulta.moveToNext()){

            int horasInicial = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio));
            int minutosInicial = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio));
            HorasIniciales.add(new Fecha(horasInicial,minutosInicial));

        }
        cursorConsulta.close();
        return HorasIniciales;
    }

    public static List getFranjasIniciales2(Context context,String siglas){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio,
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio
        };
        String columnaWhere = FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria + " IN (SELECT ID_Franja_horaria FROM Franjas_horarias_cursos_permitidos WHERE Siglas= ?"+");";
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaFranjasHorarias.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                new String[]{siglas},
                null,
                null,
                null
        );


        List HorasIniciales = new ArrayList<Fecha>();
        while(cursorConsulta.moveToNext()){

            int hI = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio));
            int mF = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio));
            HorasIniciales.add(new Fecha(hI,mF));
        }
        cursorConsulta.close();
        return HorasIniciales;
    }
}
