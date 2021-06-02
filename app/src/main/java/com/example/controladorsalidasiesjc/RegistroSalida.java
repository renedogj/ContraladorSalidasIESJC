package com.example.controladorsalidasiesjc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegistroSalida {
    public int ID;
    public int NIA;
    public int ID_FranjaHoraria;
    public Fecha fechaSalida;

    public RegistroSalida() {
    }

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
        return registrosSalida;
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
                FeedReaderContract.TablaRegistroSalida.COLUMN_NAME_FECHA_SALIDA + " = '" + Fecha.getFechaActual()+"'";
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
        return registroSalida;
    }
}
