package com.mini.asaas.notification

import com.mini.asaas.Payment.Payment
import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.NotificationType
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class NotificationAdapter {

    Object[] subject

    Object[] body

    Customer customer

    NotificationType type

    Long paymentId

    public NotificationAdapter(Payment payment, NotificationType type) {

        this.subject = [payment.id]
        this.body = [payment.value, payment.payer.name]
        this.customer = payment.customer
        this.type = type
        this.paymentId = payment.id
    }

}
