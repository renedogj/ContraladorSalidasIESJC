package com.example.controladorsalidasiesjc;

/**
 * Clase enum para la utilización de los meses con nombres y no con numeros
 * Cada mes tiene asignado un numero (Según el mes del año que sean)
 */
public enum Meses {
    Enero       (1),
    Febrero     (2),
    Marzo       (3),
    Abril       (4),
    Mayo        (5),
    Junio       (6),
    Julio       (7),
    Agosto      (8),
    Septiembre  (9),
    Octubre    (10),
    Noviembre  (11),
    Diciembre  (12);

    private final int num_mes;

    Meses(int num_mes){
        this.num_mes = num_mes;
    }

    public int getNumeroMes(){
        return num_mes;
    }
}
