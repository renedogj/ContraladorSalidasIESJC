package com.example.controladorsalidasiesjc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Alumno {
    public int NIA;
    public String nombre;
    public String primerApellido;
    public String segundoApellido;
    public Fecha fechaNacimiento = new Fecha();
    public Curso curso = new Curso();

    public Alumno(int NIA, String nombre, String primerApellido, String segundoApellido, Fecha fechaNacimiento, Curso curso) {
        this.NIA = NIA;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.curso = curso;
    }

    public Alumno(){

    };

    public void getAlumno(Context context, int NIA){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaAlumnos.COLUMN_NAME_NIA,
                FeedReaderContract.TablaAlumnos.COLUMN_NAME_Nombre,
                FeedReaderContract.TablaAlumnos.COLUMN_NAME_Primer_Apellido,
                FeedReaderContract.TablaAlumnos.COLUMN_NAME_Segundo_Apellido,
                FeedReaderContract.TablaAlumnos.COLUMN_NAME_Dia_Nacimiento,
                FeedReaderContract.TablaAlumnos.COLUMN_NAME_Mes_Nacimiento,
                FeedReaderContract.TablaAlumnos.COLUMN_NAME_Año_Nacimiento,
                FeedReaderContract.TablaAlumnos.COLUMN_NAME_Siglas_Curso
        };
        String columnaWhere = FeedReaderContract.TablaAlumnos.COLUMN_NAME_NIA + " = ?";
        String[] valorWhere = { NIA + "" };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaAlumnos.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        if(cursorConsulta.moveToNext()){
            this.NIA = cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaAlumnos.COLUMN_NAME_NIA));
            this.nombre = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Nombre));
            this.primerApellido = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Primer_Apellido));
            this.segundoApellido = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Segundo_Apellido));
            this.fechaNacimiento = new Fecha(
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Dia_Nacimiento)),
                    Meses.values()[cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Mes_Nacimiento))],
                    cursorConsulta.getInt(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Año_Nacimiento))
            );
            this.curso.getCurso(
                    context,
                    cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Siglas_Curso))
            );
        }
        cursorConsulta.close();
    }
    
    public static String getNombreAlumno(Context context, int NIA){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaAlumnos.COLUMN_NAME_Nombre,
        };
        String columnaWhere = FeedReaderContract.TablaAlumnos.COLUMN_NAME_Siglas_Curso + " = ?";
        String[] valorWhere = { NIA + "" };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaAlumnos.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        String nombre = null;
        if(cursorConsulta.moveToNext()){
            nombre = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Nombre));
        }
        cursorConsulta.close();
        return nombre;
    }

    public static List getNombreAlumnosPorCurso(Context context, Curso curso){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnasARetornar = {
                FeedReaderContract.TablaAlumnos.COLUMN_NAME_Nombre
        };
        String columnaWhere = FeedReaderContract.TablaAlumnos.COLUMN_NAME_Siglas_Curso + " = ?";
        String[] valorWhere = { curso.siglas + "" };
        Cursor cursorConsulta = db.query(
                FeedReaderContract.TablaAlumnos.TABLE_NAME,
                columnasARetornar,
                columnaWhere,
                valorWhere,
                null,
                null,
                null
        );
        List nombreAlumnos = new ArrayList<String>();
        while(cursorConsulta.moveToNext()){
            String nombre = cursorConsulta.getString(cursorConsulta.getColumnIndexOrThrow(FeedReaderContract.TablaAlumnos.COLUMN_NAME_Nombre));
            nombreAlumnos.add(nombre);
        }
        cursorConsulta.close();
        return nombreAlumnos;
    }
}
