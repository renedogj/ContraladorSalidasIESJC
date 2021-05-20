package com.example.controladorsalidasiesjc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    public String siglas;
    public String nombre;
    public EtapaEducativa etapaEducativa = new EtapaEducativa();
    public int numeroCurso;
    public String grupo;

    public Curso(){}

    public Curso(String siglas, String nombre, EtapaEducativa etapaEducativa, int numeroCurso, String grupo) {
        this.siglas = siglas;
        this.nombre = nombre;
        this.etapaEducativa = etapaEducativa;
        this.numeroCurso = numeroCurso;
        this.grupo = grupo;
    }

    public void getCurso(Context context, String siglasCurso){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaCursos.COLUMN_NAME_Siglas_Curso,
                FeedReaderContract.TablaCursos.COLUMN_NAME_Nombre,
                FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa,
                FeedReaderContract.TablaCursos.COLUMN_NAME_Numero_Curso,
                FeedReaderContract.TablaCursos.COLUMN_NAME_Grupo
        };
        String columnaWhere = FeedReaderContract.TablaCursos.COLUMN_NAME_Siglas_Curso + " = ?";
        String[] valorWhere = { siglasCurso + "" };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaCursos.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        if(cursorConsulta.moveToNext()){
            this.siglas = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaCursos.COLUMN_NAME_Siglas_Curso));
            this.nombre = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaCursos.COLUMN_NAME_Nombre));
            this.etapaEducativa.getEtapaEducativa(
                    context,
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa))
            );
            this.numeroCurso = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaCursos.COLUMN_NAME_Numero_Curso));
            this.grupo = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaCursos.COLUMN_NAME_Grupo));
        }
        cursorConsulta.close();
    }

    public static List getCursos(Context context, EtapaEducativa etapaEducativa){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaCursos.COLUMN_NAME_Siglas_Curso,
                FeedReaderContract.TablaCursos.COLUMN_NAME_Nombre,
                FeedReaderContract.TablaCursos.COLUMN_NAME_Numero_Curso,
                FeedReaderContract.TablaCursos.COLUMN_NAME_Grupo
        };
        String columnaWhere = FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa + " = ?";
        String[] valorWhere = { etapaEducativa.ID + "" };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaCursos.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        List cursos = new ArrayList<Curso>();
        while(cursorConsulta.moveToNext()){
            Curso curso = new Curso();
            curso.siglas = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaCursos.COLUMN_NAME_Siglas_Curso));
            curso.nombre = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaCursos.COLUMN_NAME_Nombre));
            curso.etapaEducativa = etapaEducativa;
            curso.numeroCurso = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaCursos.COLUMN_NAME_Numero_Curso));
            curso.grupo = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaCursos.COLUMN_NAME_Grupo));
            cursos.add(curso);
        }
        cursorConsulta.close();
        return cursos;
    }

    public static List getSiglasCursos(Context context, EtapaEducativa etapaEducativa){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaCursos.COLUMN_NAME_Siglas_Curso
        };
        String columnaWhere = FeedReaderContract.TablaCursos.COLUMN_NAME_ID_Etapa + " = ?";
        String[] valorWhere = { etapaEducativa.ID + "" };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaCursos.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        List siglasCursos = new ArrayList<String>();
        while(cursorConsulta.moveToNext()){
            String siglas = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaCursos.COLUMN_NAME_Siglas_Curso));
            siglasCursos.add(siglas);
        }
        cursorConsulta.close();
        return siglasCursos;
    }
}
