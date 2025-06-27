package com.mini.asaas.payment

import com.mini.asaas.base.BasePaymentAdapter
import com.mini.asaas.utils.BigDecimalUtils
import com.mini.asaas.utils.DateFormatUtils
import com.mini.asaas.utils.Utils

class PaymentUpdateAdapter extends BasePaymentAdapter {

    Long id

    public PaymentUpdateAdapter(Map originalParams) {
        Map<String, String> params = Utils.normalizeParams(originalParams)
        if (!params) return

        this.id = params.id as Long
        this.payerId = params.payerId as Long
        this.value = BigDecimalUtils.fromFormattedString(params.value)
        this.description = params.description
        this.billingType = BillingType.convert(params.billingType)
        this.status = params.status as PaymentStatus
        this.dueDate = DateFormatUtils.parseDateFromString(params.dueDate)
    }
}
