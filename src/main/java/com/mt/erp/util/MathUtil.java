package com.mt.erp.util;

import java.math.BigDecimal;

public class MathUtil {
    public static double round(double input, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(input));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
