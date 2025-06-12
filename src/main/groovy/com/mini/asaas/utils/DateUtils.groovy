package com.mini.asaas.utils

import grails.compiler.GrailsCompileStatic

import java.text.SimpleDateFormat

@GrailsCompileStatic
class DateUtils {

    private static final String DEFAULT_FORMAT = "dd/MM/yyyy"

    public static Date parseDateFromString(String date, String format) {
        if (!date || !format) return null
        return new SimpleDateFormat(format).parse(date)
    }

    public static Date parseDateFromString(String date) {
        return parseDateFromString(date, DEFAULT_FORMAT)
    }

}