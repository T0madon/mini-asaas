package com.mini.asaas.utils

import grails.compiler.GrailsCompileStatic

import java.text.SimpleDateFormat

@GrailsCompileStatic
class DateFormatUtils {

    private static final String DEFAULT_FORMAT = "dd/MM/yyyy"
    private static final String TIME_FORMAT = "dd/MM/yyyy HH:mm"

    public static Date parseDateFromString(String date, String format) {
        if (!date || !format) return null
        return new SimpleDateFormat(format).parse(date)
    }

    public static Date parseDateFromString(String date) {
        return parseDateFromString(date, DEFAULT_FORMAT)
    }

    public static String format(Date date) {
        if (!date) return null

        return new SimpleDateFormat(DEFAULT_FORMAT).format(date)
    }

    public static String formatWithTime(Date date) {
        if (!date) return null

        return new SimpleDateFormat(TIME_FORMAT).format(date)
    }

    public static Date getDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
