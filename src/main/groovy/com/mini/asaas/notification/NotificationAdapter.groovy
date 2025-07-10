package com.mini.asaas.notification

import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.NotificationType
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class NotificationAdapter {

    Object[] subject

    Object[] body

    Customer customer

    NotificationType type

    String status

    Long paymentId

    public NotificationAdapter(Object[] subject, Object[] body, Customer customer, NotificationType type, String status, Long paymentId) {

        this.subject = subject
        this.body = body
        this.customer = customer
        this.type = type
        this.status = status
        this.paymentId = paymentId
    }

}
