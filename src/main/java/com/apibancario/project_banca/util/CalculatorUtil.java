package com.apibancario.project_banca.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorUtil {

    public static BigDecimal calcularCuotaMensual(BigDecimal interestRate, BigDecimal totalAmount, Integer monthsOfDeadline) {
        // Calculando el TEM = (1 + TEA) ^ (1/12) - 1
        double teaDouble = interestRate.doubleValue();
        double temDouble = Math.pow(1+teaDouble, 1.0/12) - 1;

        BigDecimal tem = BigDecimal.valueOf(temDouble);
        System.out.println(tem);
        // Calculando el denominador = 1 - (1 + TEM)^-n
        BigDecimal denominador = BigDecimal.valueOf(1 - Math.pow(1 + temDouble, -monthsOfDeadline));

        // CUOTA = totalAmount * TEM / (1 - (1+TEM)^-n)
        BigDecimal cuota = totalAmount.multiply(tem).divide(denominador, 10, RoundingMode.HALF_UP);

        return cuota.setScale(2, RoundingMode.HALF_UP);
    }
}
