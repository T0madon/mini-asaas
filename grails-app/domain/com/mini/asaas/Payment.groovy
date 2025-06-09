package com.mini.asaas

import payment.BillingType
import payment.PaymentStatus
import utils.BaseEntity

class Payment extends BaseEntity {

    String receiptId

    Payer payer

    Customer customer

    BillingType billingType

    BigDecimal value

    PaymentStatus status

    Date dueDate

    Date paymentDate

    String description

    static constraints = {
        receiptId nullable: true
        value scale: 2
        paymentDate nullable: true
        description nullable: true, maxSize: 255
    }
}
