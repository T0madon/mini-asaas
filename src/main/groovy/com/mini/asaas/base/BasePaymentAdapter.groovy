package com.mini.asaas.base

import com.mini.asaas.payment.BillingType
import com.mini.asaas.payment.PaymentStatus

abstract class BasePaymentAdapter {

    Long payerId

    BigDecimal value

    String description

    BillingType billingType

    PaymentStatus status

    Date dueDate
}
