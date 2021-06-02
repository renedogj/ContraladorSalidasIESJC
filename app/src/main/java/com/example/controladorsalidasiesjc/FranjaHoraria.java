package com.example.controladorsalidasiesjc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FranjaHoraria {
    public int ID;
    public String diaSemana;
    public Fecha horaInicio;
    public Fecha horaFinal;

    public FranjaHoraria() {
    }

    public FranjaHoraria(int ID, String diaSemana, Fecha horaInicio, Fecha horaFinal) {
        this.ID = ID;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public FranjaHoraria(Fecha horaInicio, Fecha horaFinal) {
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public static List getFranjasDiaSemana(Context context, String diaSemana) {
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
        String columnaWhere = FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana + " = ?";
        String[] valorWhere = {diaSemana + ""};
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaFranjasHorarias.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        List FranjasHorarias = new ArrayList<FranjaHoraria>();
        while (cursorConsulta.moveToNext()) {
            FranjasHorarias.add(new FranjaHoraria(
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria)),
                    cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana)),
                    new Fecha(
                            cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio)),
                            cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio))
                    ),
                    new Fecha(
                            cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Final)),
                            cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Final))
                    )
            ));
        }
        cursorConsulta.close();
        return FranjasHorarias;
    }

    public static List getIDFranjasDiaSemana(Context context, String diaSemana) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria
        };
        String columnaWhere = FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana + " = ?";
        String[] valorWhere = {diaSemana + ""};
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaFranjasHorarias.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        List IdFranjasHorarias = new ArrayList<String>();
        while (cursorConsulta.moveToNext()) {
            IdFranjasHorarias.add(
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria)) + ""
            );
        }
        cursorConsulta.close();
        return IdFranjasHorarias;
    }

    public void getFranjaHoraria(Context context, int ID_franjaHoraria) {
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
        String[] valorWhere = {ID_franjaHoraria + ""};
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaEtapasEducativas.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        if (cursorConsulta.moveToNext()) {
            this.ID = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria));
            this.diaSemana = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana));
            this.horaInicio = new Fecha(
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

    public static List get_Franjas_Permitidas(Context context, String siglas, String dia,String alumno) {
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
        String columnaWhere = FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria + " IN (SELECT " +
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria + " FROM " +
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.TABLE_NAME + " WHERE " +
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Siglas_Curso + " = '" + siglas + "') AND " +
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Alumno + " = ? " + ") AND " +
                FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana + " = '" + dia + "'";
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaFranjasHorarias.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                new String[]{alumno,"Todos"},
                null,
                null,
                null
        );

        List FranjasPermitidas = new ArrayList<FranjaHoraria>();
        while (cursorConsulta.moveToNext()) {
            FranjaHoraria franja = new FranjaHoraria(
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria)),
                    cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana)),
                    new Fecha(
                            cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Inicio)),
                            cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Inicio))
                    ),
                    new Fecha(
                            cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Hora_Final)),
                            cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Minuto_Final))
                    )
            );
            FranjasPermitidas.add(franja);
        }
        cursorConsulta.close();

        return FranjasPermitidas;
    }
}
