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
            return (notification instanceof String ? notification.toUpperCase() : notification) as NotificationType
        } catch (Exception exception) {
            return null
        }
    }

}