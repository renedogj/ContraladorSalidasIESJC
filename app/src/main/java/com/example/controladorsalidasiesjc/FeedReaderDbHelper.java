package com.example.controladorsalidasiesjc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BBDD_Salidas_IESJC.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.TablaEtapasEducativas.SQL_CREATE_ENTRIES);
        db.execSQL(FeedReaderContract.TablaCursos.SQL_CREATE_ENTRIES);
        db.execSQL(FeedReaderContract.TablaFranjasHorarias.SQL_CREATE_ENTRIES);
        db.execSQL(FeedReaderContract.TablaFranjasHorariasCursosPermitidos.SQL_CREATE_ENTRIES);
        db.execSQL(FeedReaderContract.TablaAlumnos.SQL_CREATE_ENTRIES);
        db.execSQL(FeedReaderContract.TablaRegistroSalida.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FeedReaderContract.TablaEtapasEducativas.SQL_DELETE_ENTRIES);
        db.execSQL(FeedReaderContract.TablaCursos.SQL_DELETE_ENTRIES);
        db.execSQL(FeedReaderContract.TablaFranjasHorarias.SQL_DELETE_ENTRIES);
        db.execSQL(FeedReaderContract.TablaFranjasHorariasCursosPermitidos.SQL_DELETE_ENTRIES);
        db.execSQL(FeedReaderContract.TablaAlumnos.SQL_DELETE_ENTRIES);
        db.execSQL(FeedReaderContract.TablaRegistroSalida.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void truncateTable(SQLiteDatabase db, String nombreTabla){
        db.execSQL("DELETE FROM "+ nombreTabla);
    }
}
