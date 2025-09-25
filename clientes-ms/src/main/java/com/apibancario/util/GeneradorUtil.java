package com.apibancario.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GeneradorUtil {

    public static String generarNumeroAleatorio(Integer numDigits) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numDigits; i++) {
            int valor = random.nextInt(0,10);
            result.append(String.valueOf(valor));
        }
        return result.toString();
    }

    public static String ocultarNumeroUID(String numeroUID) {
        int cantidadOcultado = numeroUID.length()-4;
        return ("*".repeat(cantidadOcultado))+numeroUID.substring(cantidadOcultado);
    }

    public static String generarFechaVencimientoTarjeta() {
        return LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("MM/yy"));
    }
}
