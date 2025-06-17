package com.mini.asaas

import com.mini.asaas.base.BaseEntity
import com.mini.asaas.customer.Customer
import com.mini.asaas.payer.Payer
import com.mini.asaas.payment.BillingType
import com.mini.asaas.payment.PaymentStatus

class Payment extends BaseEntity {

    Payer payer

    Customer customer

    BillingType billingType

    BigDecimal value

    PaymentStatus status

    Date dueDate

    Date paymentDate

    String description

    static constraints = {
        paymentDate nullable: true
        description nullable: true
    }
}
