package com.example.controladorsalidasiesjc;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Fecha {
    //ATRIBUTOS
    int dia;
    Meses mes;
    int año;
    int hora;
    int minuto;
    int segundo;

    //CONSTRUCTORES
    //Constructor vacio
    public Fecha() {}

    /**
     * Constructor para inicializar con dia, mes y año
     *
     * @param dia
     * @param mes
     * @param año
     */
    public Fecha(int dia, Meses mes, int año) {
        this.dia = dia;
        this.mes = mes;
        this.año = año;
    }

    /**
     * Constructor para inicializar con hora, minuto y segundo
     *
     * @param hora
     * @param minuto
     * @param segundo
     */
    public Fecha(int hora, int minuto, int segundo) {
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }

    /**
     * Constructor para inicializar con hora y minuto
     *
     * @param hora
     * @param minuto
     */
    public Fecha(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = 0;
    }

    /**
     * Constructor para inicializar con todos los atributos
     *
     * @param dia
     * @param mes
     * @param año
     * @param hora
     * @param minuto
     * @param segundo
     */
    public Fecha(int dia, Meses mes, int año, int hora, int minuto, int segundo) {
        this.dia = dia;
        this.mes = mes;
        this.año = año;
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }

    //METODOS

    /**
     * Metodo para que la fecha pase a ser la fecha actual del sistema
     */
    public void convertirAFechaActual() {
        long msTime = System.currentTimeMillis();
        Date curDateTime = new Date(msTime);
        this.dia = curDateTime.getDay();
        this.mes = Meses.values()[curDateTime.getMonth()];
        this.año = curDateTime.getYear();
    }

    /**
     * Fecha que devuelve la fecha actual del sistema
     *
     * @return
     */
    public static Fecha FechaActual() {
        long msTime = System.currentTimeMillis();
        Date curDateTime = new Date(msTime);
        return new Fecha(curDateTime.getDay(), Meses.values()[curDateTime.getMonth()], curDateTime.getYear(),curDateTime.getHours(),curDateTime.getMinutes(),0);
    }

    public static String getDiaActual(){
        Calendar hoy = Calendar.getInstance();
        int dia = hoy.get(Calendar.DAY_OF_WEEK);
        String dia_semana = "";
        switch (dia){
            case 2:
                dia_semana = "Lunes";
                break;
            case 3:
                dia_semana = "Martes";
                break;
            case 4:
                dia_semana = "Miercoles";
                break;
            case 5:
                dia_semana ="Jueves";
                break;
            case 6:
                dia_semana = "Viernes";
                break;
            case 7:
                dia_semana = "Domingo";
                break;
            case 1:
                dia_semana = "Domingo";
                break;
        }
        return dia_semana;
    }

    public static String getFechaActual(){
        Locale esLocale = new Locale("es", "ES");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM", esLocale);
        Date date = new Date();
        String fecha = dateFormat.format(date);

        return fecha;
    }

    public static String getHoraActual(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh", new Locale("es_ES"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        Date date = new Date();
        String hora = dateFormat.format(date);

        return hora;
    }

    public static String getMinutoActual(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm", new Locale("es_ES"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        Date date = new Date();
        String minuto = dateFormat.format(date);

        return minuto;
    }

    /**
     * Metodo booleano que determina si un año es o no bisiesto
     * <p>Un año es bisiesto si:
     * Es divisible entre 4 y no lo es de 100
     * Es divisible entre 4, entre 100 y entre 400
     * En cualquier otro caso el año no es bisiesto</p>
     *
     * @return {@code true} Si el año es bisiesto
     * {@code false} Si el año no es bisiesto
     */
    public boolean bisiesto() {
        if (año % 4 == 0) {
            if (año % 100 == 0) {
                return año % 400 == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Metodo booleano estatico que determina si un año pasado por parametro es o no bisiesto
     * <p>La lógica del metodo es
     * la misma que la del metodo bisiesto no estatico que no recibe ningún parametro
     * Un año es bisiesto si:
     * Es divisible entre 4 y no lo es de 100
     * Es divisible entre 4, entre 100 y entre 400
     * En cualquier otro caso el año no es bisiesto</p>
     *
     * @param año int que representa un año
     * @return {@code true} Si el año es bisiesto
     * {@code false} Si el año no es bisiesto
     */
    public static boolean bisiesto(int año) {
        if (año % 4 == 0) {
            if (año % 100 == 0) {
                return año % 400 == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Metodo estatico booleano que comprueba si es posible que exista un numero de dia
     * del mes pasado por parametro
     * El dia existe si:
     * Es mayor a 0 y es menor que 31
     *
     * @param dia int que equivale al dia que se va a comprobar si existe o no
     * @return {@code true} Si el día existe
     * {@code false} Si el día no existe
     */
    public static boolean existeDia(int dia) {
        return dia > 0 || dia < 32;
    }

    /**
     * Metodo estatico y booleano que compruba si es posible que exista un mes cuyo
     * numero se pasa por parametro
     * El mes existe si:
     * Es mayor a 0 a y menor a 13
     *
     * @param mes int que equivale al mes que se va a comprobar si existe o no
     * @return {@code true} Si el mes existe
     * {@code false} Si el mes no existe
     */
    public static boolean existeMes(int mes) {
        return mes > 0 && mes <= 12;
    }

    /**
     * Metodo booleano para comprobar si el dia es posible en el mes
     * Se da por sentado que los valores del dia y el mes son posibles
     * Se pasa el numero que la variable mes de la clase Meses tiene asignada
     * para así poder trabajar con el numero del mes y no con el nombre del mismo
     * El dia es posible si:
     * Si el mes es 4,6,9 u 11 y el dia es menor que 31
     * Siendo lo anterior y el mes distinto a 2
     * Siendo el mes 2 y el dia menor que 30
     *
     * @param dia int que equivale al mes que se va a comprobar si existe o no
     * @param mes int que equivale al mes que se va a comprobar si existe o no
     * @return {@code true} Si el dia del mes es posible
     * {@code false} Si el dia del mes no es posible
     */
    public static boolean mes_diaValido(int dia, int mes) {
        if (dia == 31 && (mes == 4 || mes == 6 || mes == 9 || mes == 11)) {
            return false;
        } else {
            return !(dia >= 30 && mes == 2);
        }
    }

    /**
     * Metodo estatico y booleano que comprueba si el año pasado por parametro existe
     * Se considera que el año existe si:
     * Si es mayor a 1900 y menor que 3000
     * Esto se debe a que años anteriores y posterios a dichas fechas respectivamente no son
     * utiles
     *
     * @param año int que equivale al año que se va a comprobar si existe o no
     * @return {@code true} Si el año existe
     * {@code false} Si el año no existe
     */
    public static boolean existeAño(int año) {
        return año > 1900 || año < 3000;
    }

    /**
     * Metodo booleano que comprueba si la fecha es posible y existe
     * Se comprueba si existe el dia, si existe el mes y si existe el año
     * tambien se comprueba si el dia del mes es valido.
     * En caso de lo anterior sea cierto comprueba que
     * si el dia es 29 de Febrero(mes 2) el año debe ser bisiesto
     *
     * @return {@code true} Si la fecha existe y es correcta
     * {@code false} Si no existe y/o no es correcta
     */
    public boolean fechaValida() {
        if (Fecha.existeDia(dia)
                && Fecha.existeMes(mes.getNumeroMes())
                && Fecha.existeAño(año)
                && Fecha.mes_diaValido(dia, mes.getNumeroMes())) {
            return !(dia == 29 && mes.getNumeroMes() == 2 && !bisiesto());
        } else {
            return false;
        }
    }

    /**
     * Metodo estatico y booleano que comprueba si la hora pasada por parametro existe
     * La hora existe si:
     * Es mayor o igual a 0 y menor de 60
     *
     * @param hora int que equivale a la hora que se va a comprobar si existe o no
     * @return {@code true} Si la hora existe
     * {@code false} Si la hora no existe
     */
    public static boolean existeHora(int hora) {
        return (hora >= 0 || hora < 24);
    }

    /**
     * Metodo estatico y booleano que comprueba si el minuto pasado por parametro existe
     * El minuto existe si:
     * Es mayor o igual a 0 y menor de 60
     *
     * @param minuto int que equivale a la hora que se va a comprobar si existe o no
     * @return {@code true} Si el minuto existe
     * {@code false} Si el minuto no existe
     */
    public static boolean existeMinuto(int minuto) {
        return (minuto >= 0 || minuto < 60);
    }

    /**
     * Metodo estatico y booleano que comprueba si el segundo pasado por parametro existe
     * El segundo existe si:
     * Es mayor o igual a 0 y menor de 60
     *
     * @param segundo int que equivale a la hora que se va a comprobar si existe o no
     * @return {@code true} Si el segundo existe
     * {@code false} Si el segundo no existe
     */
    public static boolean existeSegundo(int segundo) {
        return (segundo >= 0 || segundo < 60);
    }

    /**
     * método estático  que devuelve un entero que es la diferencia de dias entre dos fechas
     * El método no hace caso de las horas,minutos y segundos
     * Variables locales:
     * variables int día1,día2,mes1,mes2,año1,año2 que se igualaran y servirán
     * como variables de las dos fechas pasados por parámetros
     * Array int diaMeses que indica el numero de días que tiene cada uno de los meses
     * (Enero esta en la posición 1 del array)
     * variable int diferencia que servirá para ir acumulando la diferencia de días entre las dos fechas
     * <p>
     * Algoritmo del método:
     * Primero comprobamos si las dos fechas están en el mismo año. En caso de que las fechas
     * estén en el mismo año comprobamos si está en el mismo mes. Si la fechas están en el mismo mes
     * y año comprobamos si el día es el mismo en cuyo caso la diferencia seria igual a 0
     * en caso contrario restamos los días
     * Si el año es el mismo pero el mes distinto sumamos los días de los meses que hay
     * entre las dos fechas. Si el mes es febrero(2) y el año es bisiesto sumamos uno a la diferencia.
     * Si el mes de la fecha1 (mes1) es febrero sumamos 1. Después sumamos los días que quedan
     * para completa el mes1 y los días (día2) que llevan transcurridos del mes de la fecha2 (mes2)
     * <p>
     * En caso de que los años sean diferentes, con un bucle for sumamos 365 días a la diferencia
     * por cada año de diferencia entre el año1 y el año2 y vamos comprobando si alguno de esos
     * años es bisiesto en cuyo caso sumamos un día más por cada año bisiesto. A continuación
     * sumamos los días de los meses que quedan para completar el año1 y si el año es
     * bisiesto y uno de los meses a sumar es febrero(2) sumamos 1. Sumamos los días del mes1
     * y si el mes es febrero(2) y el año bisiesto sumamos 1. Sumamos los días de los meses
     * del año2 hasta el mes2. Si el año2 es bisiesto y uno de los meses de los que se suman
     * los días es febrero se suma 1. Por último se suman los días del mes2 (día2):
     *
     * @param fecha1 parámetro tipo fecha que hace referencia a la primera fecha de entre las cuales
     *               se quiere conocer la diferencia de fecha.
     * @param fecha2 parámetro tipo fecha que hace referencia a la segunda fecha de entre las cuales
     *               se quiere conocer la diferencia de fecha. Se recomienda que la segunda fecha sea
     *               posterior que la primera fecha
     * @return diferencia
     * variable local de tipo int que irá acumulando a lo largo del método la diferencia
     * días que hay entre las dos fechas, en caso de que fecha1>fecha2 tendrá valor negativo
     */
    public static int difereciaFecha(Fecha fecha1, Fecha fecha2) {
        int dia1, dia2, mes1, mes2, año1, año2;
        int diasMeses[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int diferencia = 0;
        dia1 = fecha1.dia;
        mes1 = fecha1.mes.getNumeroMes();
        año1 = fecha1.año;
        dia2 = fecha2.dia;
        mes2 = fecha2.mes.getNumeroMes();
        año2 = fecha2.año;
        if (año1 == año2) {
            if (mes1 == mes2) {
                if (dia1 == dia2) {
                    return 0;
                } else {
                    diferencia = dia2 - dia1;
                }
            } else {
                for (int i = (mes2 - 1); i > mes1; i--) {
                    diferencia = diferencia + diasMeses[i];
                    if (i == 2) {
                        if (bisiesto(año2)) {
                            diferencia = diferencia + 1;
                        }
                    }
                }
                if (mes1 == 2) {
                    if (bisiesto(año2)) {
                        diferencia = diferencia + 1;
                    }
                }
                diferencia = diferencia + (diasMeses[mes1] - dia1) + dia2;
            }
        } else {
            for (int i = (año2 - 1); i > (año1 + 1); i--) {
                diferencia = diferencia + 365;
                if (bisiesto(i)) {
                    diferencia = diferencia + 1;
                }
            }
            for (int i = 12; i > mes1; i--) {
                diferencia = diferencia + diasMeses[i];
                if (i == 2) {
                    if (bisiesto(año1)) {
                        diferencia = diferencia + 1;
                    }
                }
            }
            diferencia = diferencia + (diasMeses[mes1] - dia1);
            if (mes1 == 2) {
                if (bisiesto(año1)) {
                    diferencia = diferencia + 1;
                }
            }
            for (int i = (mes2 - 1); i >= 1; i--) {
                diferencia = diferencia + diasMeses[i];
                if (i == 2) {
                    if (bisiesto(año2)) {
                        diferencia = diferencia + 1;
                    }
                }
            }
            diferencia = diferencia + dia2;
        }
        return diferencia;
    }

    /**
     * Metodo booleano para saber si la diferencia entre dos fechas introducidas es mayor que el numero de años
     * introducidos
     *
     * @param fecha1
     * @param fecha2
     * @param numAños
     * @return
     */
    public static boolean diferenciaFechaMayorQueAños(Fecha fecha1, Fecha fecha2, int numAños) {
        if (fecha2.año - fecha1.año > numAños) {
            return true;
        } else if (fecha2.año - fecha1.año == numAños) {
            if (fecha2.mes.getNumeroMes() > fecha1.mes.getNumeroMes()) {
                return true;
            } else if (fecha2.mes.getNumeroMes() == fecha1.mes.getNumeroMes()) {
                if (fecha2.dia < fecha1.dia) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isFechaEntreDosfechas(Fecha fecha1, Fecha fecha2) {
        if (this.hora < fecha1.hora) {
            return false;
        } else if (this.hora == fecha1.hora) {
            if (fecha1.hora == fecha2.hora) {
                if (fecha1.minuto < this.minuto && this.minuto < fecha2.minuto) {
                    return true;
                } else {
                    return false;
                }
            } else if (this.minuto < fecha1.minuto) {
                return false;
            } else {
                return true;
            }
        } else {
            if (this.hora < fecha2.hora) {
                return true;
            } else if (this.hora == fecha2.hora) {
                if (this.minuto <= fecha2.minuto) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        if (String.valueOf(minuto).length() != 2) {
            String minutoStr = "0" + String.valueOf(minuto);
            return String.valueOf(hora) + ":" + minutoStr;
        } else if (String.valueOf(hora).length() != 2 && String.valueOf(minuto).length() == 2) {
            String horaStr = "0" + String.valueOf(hora);
            return horaStr + ":" + String.valueOf(minuto);
        } else {
            return String.valueOf(hora) + ":" + String.valueOf(minuto);
        }

    }
}