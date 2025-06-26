package com.mini.asaas.utils

class BigDecimalUtils {

    public static BigDecimal fromFormattedString(String value) {
        try {
            return value ? new BigDecimal(value.replaceAll('\\.', "").replaceAll(',', ".")) : null
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Unparseable bigDecimal: \"${value}\" ")
        }
    }
}