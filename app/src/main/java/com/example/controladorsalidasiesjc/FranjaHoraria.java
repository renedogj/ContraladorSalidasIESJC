package com.example.controladorsalidasiesjc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
}
