package com.example.controladorsalidasiesjc;

import android.provider.BaseColumns;

public class FeedReaderContract {
    private FeedReaderContract() {}

    public static class TablaEtapasEducativas implements BaseColumns {
        public static final String TABLE_NAME = "Etapas_educativas";
        public static final String COLUMN_NAME_ID_Etapa = "ID_Etapa";
        public static final String COLUMN_NAME_Nombre = "Nombre_etapa";
        public static final String COLUMN_NAME_Edad_minima_salir = "Edad_minima_salir";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID_Etapa + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_Nombre + " TEXT," +
                        COLUMN_NAME_Edad_minima_salir + " INTERGER)";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class TablaCursos implements BaseColumns {
        public static final String TABLE_NAME = "Cursos";
        public static final String COLUMN_NAME_Siglas_Curso = "Siglas";
        public static final String COLUMN_NAME_Nombre = "Nombre_curso";
        public static final String COLUMN_NAME_ID_Etapa = TablaEtapasEducativas.COLUMN_NAME_ID_Etapa;
        public static final String COLUMN_NAME_Numero_Curso = "Numero_Curso";
        public static final String COLUMN_NAME_Grupo = "Grupo";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_Siglas_Curso + " TEXT PRIMARY KEY," +
                        COLUMN_NAME_Nombre + " TEXT," +
                        COLUMN_NAME_ID_Etapa + " INTERGER," +
                        COLUMN_NAME_Numero_Curso + " INTERGER," +
                        COLUMN_NAME_Grupo + " TEXT)";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class TablaFranjasHorarias implements BaseColumns {
        public static final String TABLE_NAME = "Franjas_horarias";
        public static final String COLUMN_NAME_ID_Franja_Horaria = "ID_Franja_horaria";
        public static final String COLUMN_NAME_Dia_semana = "Dia_semana";
        public static final String COLUMN_NAME_Hora_Inicio = "Hora_inicio";
        public static final String COLUMN_NAME_Minuto_Inicio = "Minuto_inicio";
        public static final String COLUMN_NAME_Hora_Final = "Hora_final";
        public static final String COLUMN_NAME_Minuto_Final = "Minuto_final";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID_Franja_Horaria + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_Dia_semana + " TEXT," +
                        COLUMN_NAME_Hora_Inicio + " INTEGER," +
                        COLUMN_NAME_Minuto_Inicio + " INTEGER," +
                        COLUMN_NAME_Hora_Final + " INTEGER," +
                        COLUMN_NAME_Minuto_Final + " INTEGER)";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class TablaFranjasHorariasCursosPermitidos implements BaseColumns {
        public static final String TABLE_NAME = "Franjas_horarias_cursos_permitidos";
        public static final String COLUMN_NAME_Relacion_FC = "Relacion_FC";
        public static final String COLUMN_NAME_ID_Franja_Horaria = TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria;
        public static final String COLUMN_NAME_Siglas_Curso = TablaCursos.COLUMN_NAME_Siglas_Curso;

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_Relacion_FC + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_ID_Franja_Horaria + " INTERGER," +
                        COLUMN_NAME_Siglas_Curso + " INTERGER)";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class TablaAlumnos implements BaseColumns {
        public static final String TABLE_NAME = "Alumnos";
        public static final String COLUMN_NAME_NIA = "NIA";
        public static final String COLUMN_NAME_Nombre = "Nombre";
        public static final String COLUMN_NAME_Primer_Apellido = "Primer_Apellido";
        public static final String COLUMN_NAME_Segundo_Apellido = "Segundo_Apellido";
        public static final String COLUMN_NAME_Dia_Nacimiento = "Dia_de_nacimiento";
        public static final String COLUMN_NAME_Mes_Nacimiento = "Mes_de_nacimiento";
        public static final String COLUMN_NAME_Año_Nacimiento = "Año_de_nacimiento";
        public static final String COLUMN_NAME_Siglas_Curso = TablaCursos.COLUMN_NAME_Siglas_Curso;

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_NIA + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_Nombre + " TEXT," +
                        COLUMN_NAME_Primer_Apellido + " TEXT," +
                        COLUMN_NAME_Segundo_Apellido + " TEXT," +
                        COLUMN_NAME_Dia_Nacimiento + " INTERGER," +
                        COLUMN_NAME_Mes_Nacimiento + " INTERGER," +
                        COLUMN_NAME_Año_Nacimiento + " INTERGER," +
                        COLUMN_NAME_Siglas_Curso + " INTERGER)";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class TablaRegistroSalida implements BaseColumns {
        public static final String TABLE_NAME = "Registro_salida";
        public static final String COLUMN_NAME_ID_Registro = "ID_Registro";
        public static final String COLUMN_NAME_NIA = TablaAlumnos.COLUMN_NAME_NIA;
        public static final String COLUMN_NAME_ID_Franja_Horaria = TablaFranjasHorarias.COLUMN_NAME_ID_Franja_Horaria;
        public static final String COLUMN_NAME_FECHA_SALIDA ="Fecha_Salida";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID_Registro + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NIA + " INTERGER," +
                        COLUMN_NAME_ID_Franja_Horaria + " INTERGER," +
                        COLUMN_NAME_FECHA_SALIDA + " DATE)";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String SQL_DELETE_WEEKLY = "DELETE FROM "+TABLE_NAME+ " WHERE " + COLUMN_NAME_ID_Registro +" IN (SELECT " + COLUMN_NAME_ID_Registro +
                    " FROM "+TABLE_NAME+ " WHERE (julianday(" + Fecha.getFechaActual() + ") - julianday(" + COLUMN_NAME_FECHA_SALIDA + " ) ) >= 7)";
    }
}
