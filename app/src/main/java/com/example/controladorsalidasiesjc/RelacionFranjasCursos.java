package com.example.controladorsalidasiesjc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RelacionFranjasCursos {
    public int ID;
    public int ID_franjaHoraria;
    public String siglasCurso;
    public String alumno;

    public RelacionFranjasCursos(){}

    public RelacionFranjasCursos(int ID, int ID_franjaHoraria, String siglasCurso) {
        this.ID = ID;
        this.ID_franjaHoraria = ID_franjaHoraria;
        this.siglasCurso = siglasCurso;
    }

    public static List getRelacionesFranjasCursos(Context context, String diaSemana) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Relacion_FC,
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_ID_Franja_Horaria,
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Siglas_Curso
        };
        String columnaWhere = FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_ID_Franja_Horaria +
                " IN (SELECT ID_Franja_horaria FROM "+ FeedReaderContract.TablaFranjasHorarias.TABLE_NAME+ " WHERE "+ FeedReaderContract.TablaFranjasHorarias.COLUMN_NAME_Dia_semana + " = ? )";
        String[] valorWhere = { diaSemana };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        List RelacionesFranjasCursos = new ArrayList<RelacionFranjasCursos>();
        while(cursorConsulta.moveToNext()){
            RelacionesFranjasCursos.add(
                new RelacionFranjasCursos(
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Relacion_FC)),
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_ID_Franja_Horaria)),
                    cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Siglas_Curso))
                )
            );
        }
        cursorConsulta.close();
        db.close();
        return RelacionesFranjasCursos;
    }

    public static boolean getRelacionFranjasCursos(Context context, FranjaHoraria franjaHoraria, Curso curso,String alumno) {
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Relacion_FC,
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_ID_Franja_Horaria,
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Siglas_Curso,
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Alumno
        };
        String columnaWhere = FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_ID_Franja_Horaria + " = " + franjaHoraria.ID +
                " AND " + FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Siglas_Curso + " = '" + curso.siglas+"' AND "+
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Alumno + " IN ('"+alumno+"','Todos')";
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                null,
                null,
                null,
                null
        );
        if(cursorConsulta.moveToNext()){
            cursorConsulta.close();
            return true;
        }
        cursorConsulta.close();
        db.close();
        return false;
    }

    public static void addRelacionFranjasCursos(Context context,FranjaHoraria franjaHoraria, Curso curso, String alumno){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put(FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_ID_Franja_Horaria, franjaHoraria.ID);
        registro.put(FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Siglas_Curso, curso.siglas);
        registro.put(FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Alumno, alumno);

        db.insert(FeedReaderContract.TablaFranjasHorariasCursosPermitidos.TABLE_NAME, null, registro);
        db.close();
    }

    public static void deleteRelacionFranjasCursos(Context context,FranjaHoraria franjaHoraria, Curso curso, String alumno){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String consulta = "DELETE FROM " + FeedReaderContract.TablaFranjasHorariasCursosPermitidos.TABLE_NAME +
                " WHERE " + FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_ID_Franja_Horaria + " = " + franjaHoraria.ID +
                " AND " + FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Siglas_Curso + " = '" + curso.siglas + "' AND " +
                FeedReaderContract.TablaFranjasHorariasCursosPermitidos.COLUMN_NAME_Alumno + " = '" + alumno + "'";
        db.execSQL(consulta);
        db.close();
    }
}
