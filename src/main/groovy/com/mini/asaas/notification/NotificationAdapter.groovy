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

    public NotificationAdapter(Map args) {

        this.subject = args.subject as Object[]
        this.body = args.body as Object[]
        this.customer = args.customer as Customer
        this.type = args.type as NotificationType
        this.status = args.status
        this.paymentId = args.paymentId as Long
    }

}
