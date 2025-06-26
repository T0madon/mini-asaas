package com.mini.asaas.payment

import com.mini.asaas.utils.BigDecimalUtils
import com.mini.asaas.utils.DateFormatUtils
import com.mini.asaas.utils.Utils

class PaymentSaveAdapter {

    Long payerId

    BigDecimal value

    String description

    BillingType billingType

    PaymentStatus status

    Date dueDate

    public PaymentSaveAdapter(Map originalParams) {
        Map<String, String> params = Utils.normalizeParams(originalParams)
        if (!params) return
        this.payerId = params.payerId as Long
        this.value = BigDecimalUtils.fromFormattedString(params.value)
        this.description = params.description
        this.billingType = BillingType.convert(params.billingType)
        this.status = PaymentStatus.PENDING
        this.dueDate = DateFormatUtils.parseDateFromString(params.dueDate)
    }
}
