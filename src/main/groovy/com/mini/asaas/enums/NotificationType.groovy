package com.mini.asaas.enums

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
enum NotificationType {

    PAYMENT_CREATED,
    PAYMENT_RECEIVED,
    PAYMENT_DELETED,
    PAYMENT_OVERDUE

    public static NotificationType convert(String notification) {
        try {
            if (notification instanceof String)
        } catch (Exception exception) {
            return null
        }
    }

}